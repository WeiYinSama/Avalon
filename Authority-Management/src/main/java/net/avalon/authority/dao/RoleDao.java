package net.avalon.authority.dao;


import lombok.extern.slf4j.Slf4j;
import net.avalon.authority.controller.vo.AssociateForm;
import net.avalon.authority.dao.bo.Privilege;
import net.avalon.authority.dao.bo.Role;
import net.avalon.authority.mapper.generator.RolePoMapper;
import net.avalon.authority.mapper.generator.RolePrivilegePoMapper;
import net.avalon.authority.mapper.generator.po.*;
import net.avalon.generic.core.exception.AvalonException;
import net.avalon.generic.core.exception.AvalonStatus;
import net.avalon.generic.core.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Weiyin
 * @Create: 2024/1/24 - 5:10
 */
@Repository
@Slf4j
public class RoleDao {

    @Autowired
    private RolePrivilegePoMapper rolePrivilegePoMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RolePoMapper mapper;

    /**
     * 将一个角色的所有权限id载入到Redis
     *
     * @param id 角色id
     * @return void
     * <p>
     * createdBy: Ming Qiu 2020-11-02 11:44
     * ModifiedBy: Ming Qiu 2020-11-03 12:24
     * 将读取权限id的代码独立为getPrivIdsByRoleId. 增加redis值的有效期
     * Ming Qiu 2020-11-07 8:00
     * 集合里强制加“0”
     */
    public void loadPrivsByRoleId(Long id) {

        List<Long> pids = getPrivIdsByRoleId(id);
        String key = "r_" + id;
        for (Long pid : pids) {
            redisTemplate.opsForSet().add(key, pid);
        }
//        redisTemplate.opsForSet().add(key, 0);
        long randTimeout = Common.generateRandomTimeForRedis(600, 700);
        redisTemplate.expire(key, randTimeout, TimeUnit.SECONDS);
    }

    /**
     * 由Role Id 获得 Privilege Id 列表
     *
     * @param id: Role id
     * @return Privilege Id 列表
     * created by Ming Qiu in 2020/11/3 11:48
     */
    private List<Long> getPrivIdsByRoleId(Long id) {

        RolePrivilegePoExample example = new RolePrivilegePoExample();
        example.or().andRoleIdEqualTo(id).andDeletedEqualTo((byte) 1);
        List<RolePrivilegePo> rolePrivilegePos = rolePrivilegePoMapper.selectByExample(example);
        List<Long> retIds = new ArrayList<>(rolePrivilegePos.size());
        for (RolePrivilegePo po : rolePrivilegePos) {
            retIds.add(po.getPrivilegeId());
        }
        return retIds;
    }

    public void addRole(Role bo) {
        RolePo po = bo.toPo();
        po.setDeleted((byte) 1);
        po.setCreateTime(LocalDateTime.now());

        try {
            mapper.insertSelective(po);
        } catch (DataAccessException e) {
            throw new AvalonException(AvalonStatus.INTERNAL_SERVER_ERR, "数据库访问错误");
        }
    }

    public List<Role> retrieveAll() {
        List<Role> ret;

        RolePoExample example = new RolePoExample();
        example.or().andDeletedEqualTo((byte) 1);
        try {
            List<RolePo> pos = mapper.selectByExample(example);
            ret = new ArrayList<>(pos.size());
            for (RolePo po : pos) {
                ret.add(new Role(po));
            }
        } catch (DataAccessException e) {
            throw new AvalonException(AvalonStatus.INTERNAL_SERVER_ERR, "数据库访问错误");
        }
        return ret;
    }

    public void deleteById(Long id) {
        RolePo po = new RolePo();
        po.setId(id);
        po.setDeleted((byte) 0);
        try {
            mapper.updateByPrimaryKeySelective(po);
        }catch (DataAccessException e) {
            throw new AvalonException(AvalonStatus.DELETE_FAIL);
        }
    }

    public void associatePrivs(AssociateForm form) {
        RolePrivilegePo po = new RolePrivilegePo();
        po.setRoleId(form.getRoleId());
        po.setDeleted((byte) 1);

        for (Long pid:form.getPids()){
            po.setPrivilegeId(pid);
            po.setCreateTime(LocalDateTime.now());
            rolePrivilegePoMapper.insertSelective(po);
        }
    }
}
