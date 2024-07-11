package net.avalon.authority.controller;

import net.avalon.authority.controller.vo.PrivilegeForm;
import net.avalon.authority.dao.bo.Privilege;
import net.avalon.authority.service.PrivilegeService;
import net.avalon.generic.core.util.R;
import net.avalon.generic.core.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Weiyin
 * @Create: 2024/1/28 - 10:25
 */
@RestController
public class PrivilegeController {

    @Autowired
    private PrivilegeService service;


    /**
     * 添加权限
     *
     * @param form
     * @return
     */
    @PostMapping("/priv/add")
    public R addPrivilege(@RequestBody PrivilegeForm form) {

        Privilege bo = form.toBo();
        service.addPrivilege(bo);

        return ResponseUtil.created();
    }

    /**
     * 查看所有权限
     * @return
     */
    @GetMapping("/priv/all")
    public R retrieveAll() {

        List<Privilege> ret = service.retrieveAll();
        return ResponseUtil.ok(ret);
    }

    /**
     * 查看角色尚未拥有的权限
     * @param id 角色id
     * @return
     */
    @GetMapping("/privs/{id}")
    public R doesNotYetHave(@PathVariable Long id) {

        List<Privilege> ret = service.retrieveDoesNotYetHave(id);
        return ResponseUtil.ok(ret);
    }



    /**
     * 删除权限
     * @param id
     * @return
     */
    @DeleteMapping("/priv/delete/{id}")
    public R deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseUtil.ok();
    }
}
