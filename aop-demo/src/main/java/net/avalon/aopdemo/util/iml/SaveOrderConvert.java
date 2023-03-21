package net.avalon.aopdemo.util.iml;

import net.avalon.aopdemo.domain.OperateLog;
import net.avalon.aopdemo.domain.SaveOrder;
import net.avalon.aopdemo.util.Convert;

/**
 * @author weiyin
 * @time 2022/12/3 - 22:22
 */
public class SaveOrderConvert implements Convert<SaveOrder> {
    @Override
    public OperateLog convert(SaveOrder saveOrder) {
        OperateLog operateLog = new OperateLog();
        operateLog.setOrderId(saveOrder.getId());
        return operateLog;
    }
}
