package net.avalon.authority.service;

import net.avalon.authority.controller.vo.AssociateForm;
import net.avalon.authority.dao.RoleDao;
import net.avalon.authority.dao.bo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Weiyin
 * @Create: 2024/1/29 - 17:16
 */
@Service
public class RoleService {

    @Autowired
    private RoleDao dao;


    public void addRole(Role bo) {
        dao.addRole(bo);
    }

    public List<Role> retrieveAll() {
        return dao.retrieveAll();
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    public void associatePrivs(AssociateForm form) {
        dao.associatePrivs(form);
    }
}
