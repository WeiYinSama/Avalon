package net.avalon.aopdemo.util.iml;

import net.avalon.aopdemo.domain.OperateLog;
import net.avalon.aopdemo.domain.UpdateOrder;
import net.avalon.aopdemo.util.Convert;

/**
 * @author weiyin
 * @time 2022/12/3 - 22:24
 */
public class UpdateOrderConvert implements Convert<UpdateOrder> {
    @Override
    public OperateLog convert(UpdateOrder updateOrder) {
        OperateLog operateLog = new OperateLog();
        operateLog.setOrderId(updateOrder.getOrderId());
        return operateLog;
    }
}
