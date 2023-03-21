package net.avalon.aopdemo.util;

import net.avalon.aopdemo.domain.OperateLog;

/**
 * @author weiyin
 * @time 2022/12/3 - 22:19
 */
public interface Convert<PARAM> {

    OperateLog convert(PARAM param);
}
