package net.avalon.authority.controller;

import net.avalon.authority.aop.annotation.OpenAPI;
import net.avalon.authority.dao.bo.InviteCode;
import net.avalon.authority.service.InviteCodeService;
import net.avalon.generic.core.util.R;
import net.avalon.generic.core.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Weiyin
 * @Create: 2024/1/31 - 18:47
 */
@RestController
public class InviteCodeController {

    @Autowired
    private InviteCodeService service;


    @GetMapping("/code/generate")
    public R generateInviteCode(){
        InviteCode code =  service.generateCode();

        return ResponseUtil.created(code);
    }

    /**
     * 校验邀请码是否有效
     * @param code 邀请码
     * @return true：有效、false：无效
     */
    @GetMapping("/code/available")
    @OpenAPI
    public R checkCodeAvailable(@RequestParam String code){

        Boolean valid = service.checkCodeAvailable(code);

        return ResponseUtil.ok(valid);
    }
}
