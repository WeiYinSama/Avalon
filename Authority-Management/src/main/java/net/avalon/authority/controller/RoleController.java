package net.avalon.authority.controller;

import lombok.extern.slf4j.Slf4j;
import net.avalon.authority.controller.vo.AssociateForm;
import net.avalon.authority.controller.vo.PrivilegeForm;
import net.avalon.authority.controller.vo.RoleForm;
import net.avalon.authority.dao.bo.Privilege;
import net.avalon.authority.dao.bo.Role;
import net.avalon.authority.service.RoleService;
import net.avalon.generic.core.util.R;
import net.avalon.generic.core.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Weiyin
 * @Create: 2024/1/29 - 17:16
 */
@RestController
@Slf4j
public class RoleController {

    @Autowired
    private RoleService service;

    /**
     * 添加角色
     * @param form
     * @return
     */
    @PostMapping("/role/add")
    public R addRole(@RequestBody RoleForm form) {

        Role bo = form.toBo();
        service.addRole(bo);

        return ResponseUtil.created();
    }

    /**
     * 给角色关联权限
     * @param form
     * @return
     */
    @PostMapping("/role/privs/associate")
    public R associate(@RequestBody AssociateForm form) {

        log.debug("关联表单：{}",form);
        service.associatePrivs(form);

        return ResponseUtil.created();
    }

    /**
     * 查看全部角色
     * @return
     */
    @GetMapping("/role/all")
    public R retrieveAll() {

        List<Role> ret = service.retrieveAll();
        return ResponseUtil.ok(ret);
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @DeleteMapping("/role/delete/{id}")
    public R deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseUtil.ok();
    }
}
