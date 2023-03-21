package net.avalon.aopdemo.service;

import net.avalon.aopdemo.annotation.Weiyin;
import net.avalon.aopdemo.domain.SaveOrder;
import net.avalon.aopdemo.domain.UpdateOrder;
import net.avalon.aopdemo.util.iml.SaveOrderConvert;
import net.avalon.aopdemo.util.iml.UpdateOrderConvert;
import org.springframework.stereotype.Service;

/**
 * @author weiyin
 * @time 2022/12/3 - 21:45
 */
@Service
public class OrderService {

    @Weiyin(desc = "保存订单",convert = SaveOrderConvert.class)
    public Boolean saveOrder(SaveOrder saveOrder){
        System.out.println("save order, orderId : "+saveOrder.getId());
        return true;
    }

    @Weiyin(desc = "更新订单",convert = UpdateOrderConvert.class)
    public Boolean updateOrder(UpdateOrder updateOrder){
        System.out.println("update order, orderId : "+updateOrder.getOrderId());
        return true;
    }
}
