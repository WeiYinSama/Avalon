package net.avalon.authority.dao;


import lombok.extern.slf4j.Slf4j;
import net.avalon.authority.dao.bo.Privilege;
import net.avalon.authority.mapper.generator.PrivilegePoMapper;
import net.avalon.authority.mapper.generator.RolePrivilegePoMapper;
import net.avalon.authority.mapper.generator.po.PrivilegePo;
import net.avalon.authority.mapper.generator.po.PrivilegePoExample;
import net.avalon.authority.mapper.generator.po.RolePrivilegePo;
import net.avalon.authority.mapper.generator.po.RolePrivilegePoExample;
import net.avalon.generic.core.exception.AvalonException;
import net.avalon.generic.core.exception.AvalonStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Weiyin
 * @Create: 2024/1/24 - 4:52
 */
@Repository
@Slf4j
public class PrivilegeDao implements InitializingBean {
    @Autowired
    private PrivilegePoMapper mapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RolePrivilegePoMapper rolePrivilegePoMapper;

    /**
     * 将权限载入到本地缓存中
     * 如果未初始化，则初始话数据中的数据
     *
     * @throws Exception createdBy: Ming Qiu 2020-11-01 23:44
     *                   modifiedBy: Ming Qiu 2020-11-03 11:44
     *                   将签名的认证改到Privilege对象中去完成
     *                   Ming Qiu 2020-12-03 9:44
     *                   将缓存放到redis中
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        loadPrivileges();
    }

    /**
     * 将权限信息缓存到 Redis 中
     * <p>
     * Hash：
     * Key：Priv
     * hashKey：url-requestType     /priv/add-POST
     * hashValue：id                1
     */
    private void loadPrivileges() {
        PrivilegePoExample example = new PrivilegePoExample();
        example.or().andDeletedEqualTo((byte) 1);
        List<PrivilegePo> privilegePos = mapper.selectByExample(example);
        for (PrivilegePo po : privilegePos) {
            String key = String.format("%s-%s", po.getUrl(), po.getRequestType());
            log.debug("Key：{}\tValue：{}", key, po.getId());
            try {
                redisTemplate.opsForHash().putIfAbsent("Priv", key, po.getId());
            } catch (Exception e) {
                throw new AvalonException(AvalonStatus.INTERNAL_SERVER_ERR, "Redis 访问错误");
            }
        }
    }

    /**
     * 添加权限
     *
     * @param bo
     */
    public void addPriv(Privilege bo) {
        PrivilegePo po = bo.toPo();
        po.setDeleted((byte) 1);
        po.setCreateTime(LocalDateTime.now());

        try {
            int i = mapper.insertSelective(po);
            if (i == 0) {
                throw new AvalonException(AvalonStatus.INSERT_FAIL);
            }
        } catch (DataAccessException e) {
            throw new AvalonException(AvalonStatus.INTERNAL_SERVER_ERR, "数据库访问失败");
        }
    }

    public List<Privilege> findAll() {

        List<Privilege> ret;

        PrivilegePoExample example = new PrivilegePoExample();
        example.or().andDeletedEqualTo((byte) 1);
        try {
            List<PrivilegePo> pos = mapper.selectByExample(example);
            ret = new ArrayList<>(pos.size());
            for (PrivilegePo po : pos) {
                ret.add(new Privilege(po));
            }
        } catch (DataAccessException e) {
            throw new AvalonException(AvalonStatus.INTERNAL_SERVER_ERR, "数据库访问错误");
        }

        return ret;
    }

    public void deleteById(Long id) {

        PrivilegePo po = new PrivilegePo();
        po.setId(id);
        po.setDeleted((byte) 0);
        try {
            mapper.updateByPrimaryKeySelective(po);
        }catch (DataAccessException e) {
            throw new AvalonException(AvalonStatus.DELETE_FAIL);
        }
    }

    public List<Privilege> retrieveDoesNotYetHave(Long id) {

        RolePrivilegePoExample rolePrivilegePoExample = new RolePrivilegePoExample();
        rolePrivilegePoExample.or().andRoleIdEqualTo(id).andDeletedEqualTo((byte) 1);
        List<RolePrivilegePo> rolePrivilegePos = rolePrivilegePoMapper.selectByExample(rolePrivilegePoExample);
        List<Long> pids = new ArrayList<>(rolePrivilegePos.size());
        for(RolePrivilegePo po :rolePrivilegePos){
            pids.add(po.getPrivilegeId());
        }

        PrivilegePoExample example = new PrivilegePoExample();
        PrivilegePoExample.Criteria criteria = example.createCriteria();

        criteria.andDeletedEqualTo((byte) 1);
        if(pids.size()>0){
            criteria.andIdNotIn(pids);
        }
        List<PrivilegePo> pos = mapper.selectByExample(example);
        List<Privilege> ret = new ArrayList<>(pos.size());
        for(PrivilegePo po : pos){
            ret.add(new Privilege(po));
        }
        return ret;
    }
}
