package net.avalon.goodsdemo.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 状态码
 * 功能类似 响应码，做了扩充
 *
 * @Author: Weiyin
 * @Create: 2023/2/26 - 15:21
 */
public enum AvalonStatus {

    /* *********** 自定义错误码 *********** */
    //客户端错误 6xx



    /***************************************************
     *    系统级返回码
     **************************************************/

    //状态码 200
    OK(0,"成功"),
    CREATED(1, "创建成功"),
    STATENOTALLOW(507,"当前状态禁止此操作"),
    RESOURCE_FALSIFY(511, "信息签名不正确"),

    //所有需要登录才能访问的API都可能会返回以下错误
    //状态码 400
    FIELD_NOTVALID(503,"字段不合法"),
    IMG_FORMAT_ERROR(508,"图片格式不正确"),
    IMG_SIZE_EXCEED(509,"图片大小超限"),
    PARAMETER_MISSED(510, "缺少必要参数"),
    LATE_BEGINTIME(947, "开始时间不能晚于结束时间"),


    //状态码 401
    AUTH_INVALID_JWT(501,"JWT不合法"),
    AUTH_JWT_EXPIRED(502,"JWT过期"),

    AUTH_INVALID_ACCOUNT(700, "用户名不存在或者密码错误"),
    AUTH_ID_NOTEXIST(701,"登录用户id不存在"),
    AUTH_USER_FORBIDDEN(702,"用户被禁止登录"),
    AUTH_NEED_LOGIN(704, "需要先登录"),
    //以下错误码提示可以自行修改
    //--------------------------------------------

    //状态码 403
    AUTH_NO_RIGHT(705, "无权限"),
    RESOURCE_ID_OUTSCOPE(505,"操作的资源id不是自己的对象"),
    FILE_NO_WRITE_PERMISSION(506,"目录文件夹没有写入的权限"),

    //所有路径带id的API都可能返回此错误
    //状态码 404
    RESOURCE_ID_NOTEXIST(504,"操作的资源id不存在"),

    //状态码 500
    INTERNAL_SERVER_ERR(500,"服务器内部错误"),

    /***************************************************
     *    其他模块错误码
     **************************************************/
    ADDRESS_OUTLIMIT(601,"达到地址簿上限"),
    REGION_OBSOLETE(602,"地区已废弃"),
    ADVERTISEMENT_OUTLIMIT(603,"达到时段广告上限"),
    TIMESEG_CONFLICT(604,"时段冲突"),
    SHAREACT_CONFLICT(605,"分享活动时段冲突"),
    ORDERITEM_NOTSHARED(606,"订单明细无分享记录"),
    FLASHSALE_OUTLIMIT(607,"达到时段秒杀上限"),
    ADVERTISEMENT_STATENOTALLOW(608,"广告状态禁止"),
    AFTERSALE_STATENOTALLOW(609,"售后单状态禁止"),
    /***************************************************
     *    权限模块错误码
     **************************************************/

    AUTH_NOT_ALLOW(705,"无权限访问"),
    USER_NAME_REGISTERED( 731,"用户名已被注册"),
    EMAIL_REGISTERED(732, "邮箱已被注册"),
    MOBILE_REGISTERED(733,"电话已被注册"),
    ROLE_REGISTERED(736, "角色名已存在"),
    PASSWORD_SAME(741,"不能与旧密码相同"),
    URL_SAME(742,"权限url与RequestType重复"),
    PRIVILEGE_SAME(743,"权限名称重复"),
    PRIVILEGE_BIT_SAME(744,"权限位重复"),
    EMAIL_WRONG(745,"与系统预留的邮箱不一致"),
    MOBILE_WRONG(746,"与系统预留的电话不一致"),

    /***************************************************
     *    订单模块错误码
     **************************************************/
    ORDER_STATENOTALLOW(801,"订单状态禁止"),
    FREIGHTNAME_SAME(802,"运费模板名重复"),
    REGION_SAME(803,"运费模板中该地区已经定义"),
    REFUND_MORE(804,"退款金额超过支付金额"),
    /***************************************************
     *    商品模块错误码
     **************************************************/
    SKU_NOTENOUGH(900,"商品规格库存不够"),
    SKUSN_SAME(901,"商品规格重复"),
    SKUPRICE_CONFLICT(902,"商品浮动价格时间冲突"),
    USER_NOTBUY(903,"用户没有购买此商品"),
    COUPONACT_STATENOTALLOW(904,"优惠活动状态禁止"),
    COUPON_STATENOTALLOW(905,"优惠卷状态禁止"),
    PRESALE_STATENOTALLOW(906,"预售活动状态禁止"),
    GROUPON_STATENOTALLOW(907,"团购活动状态禁止"),
    USER_HASSHOP(908,"用户已经有店铺"),
    COUPON_NOTBEGIN(909,"未到优惠卷领取时间"),
    COUPON_FINISH(910,"优惠卷领罄"),
    COUPON_END(911,"优惠卷活动终止");


    private int code;
    private String message;

    private static final Map<Integer,AvalonStatus> map;

    static {
        map = new HashMap<>(AvalonStatus.values().length);
        for (AvalonStatus status : AvalonStatus.values()) {
            map.put(status.getCode(), status);
        }
    }

    AvalonStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static AvalonStatus getByCode(int code) {
        return map.get(code);
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
