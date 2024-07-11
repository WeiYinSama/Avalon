package net.avalon.authority.service;

import net.avalon.authority.dao.InviteCodeDao;
import net.avalon.authority.dao.bo.InviteCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Weiyin
 * @Create: 2024/1/31 - 18:51
 */
@Service
public class InviteCodeService {

    @Autowired
    private InviteCodeDao dao;
    public InviteCode generateCode() {
        return dao.generateCode();
    }

    public Boolean checkCodeAvailable(String code) {

        return dao.checkCodeAvailable(code);
    }
}
