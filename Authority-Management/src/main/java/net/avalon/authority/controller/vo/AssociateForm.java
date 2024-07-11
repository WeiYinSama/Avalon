package net.avalon.authority.controller.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: Weiyin
 * @Create: 2024/1/29 - 19:22
 */
@Data
public class AssociateForm {

    private Long roleId;
    private List<Long> pids;
}
