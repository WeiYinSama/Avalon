package net.avalon.authority.service;

import net.avalon.authority.dao.PrivilegeDao;
import net.avalon.authority.dao.bo.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Weiyin
 * @Create: 2024/1/28 - 10:26
 */
@Service
public class PrivilegeService {

    @Autowired
    private PrivilegeDao dao;
    public void addPrivilege(Privilege bo) {
        dao.addPriv(bo);
    }

    public List<Privilege> retrieveAll() {
        return dao.findAll();
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    public List<Privilege> retrieveDoesNotYetHave(Long id) {
        return dao.retrieveDoesNotYetHave(id);
    }
}
