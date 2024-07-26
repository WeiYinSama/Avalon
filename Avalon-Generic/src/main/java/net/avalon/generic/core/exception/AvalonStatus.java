package net.avalon.generic.core.exception;

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

    /*           信息响应           */
    // 102 Processing
    // 服务器已收到并正在处理该请求，但当前没有响应可用。



    /*           成功响应           */
    // 200 OK
    // 请求成功。
    OK(0,"成功"),

    // 201 Created
    // 该请求已成功，并因此创建了一个新的资源。这通常是在 POST 请求。
    CREATED(201, "创建成功"),
    // 202 Accepted
    // 请求已经接收到，但还未响应，没有结果。意味着不会有一个异步的响应去表明当前请求的结果，预期另外的进程和服务去处理请求，或者批处理。
    // 204 No Content
    // 对于该请求没有的内容可发送，但头部字段可能有用。用户代理可能会用此时请求头部信息来更新原来资源的头部缓存字段。
    // 206 Partial Content
    // 当从客户端发送Range范围标头以只请求资源的一部分时，将使用此响应代码。



    /*           重定向消息           */
    // 301 Moved Permanently
    // 请求资源的 URL 已永久更改。在响应中给出了新的 URL。
    // 303 See Other
    // 服务器发送此响应，以指示客户端通过一个 GET 请求在另一个 URI 中获取所请求的资源。
    // 304 Not Modified
    // 这是用于缓存的目的。它告诉客户端响应还没有被修改，因此客户端可以继续使用相同的缓存版本的响应。



    /*           客户端错误响应           */

    // 400 Bad Request
    // 由于被认为是客户端错误，服务器不会处理该请求。
    FILE_UPLOAD_FAIL(500006,"文件上传失败"),
    FILE_ACCESS_FAIL(500007,"文件无法访问，请检查路径"),
    FIELD_NOTVALID(40001,"字段不合法"),
    FORM_HAS_BEEN_SUBMITTED(40002,"表单已经提交过了，请勿重复提交"),
    CODE_60(560,"注册账号时，验证码获取频率过高"),
    CODE_NOT_VALID(561,"验证码验证失败"),


    PARAMETER_MISSED(510, "缺少必要参数"),
    LATE_BEGINTIME(947, "开始时间不能晚于结束时间"),

    INSERT_FAIL(400003,"插入失败"),
    DELETE_FAIL(400004,"删除失败"),

    FILE_COPY_FAIL(400005,"文件复制失败"),
    FILE_NOT_EXIST(400006,"文件不存在"),

    // 401 Unauthorized
    // 未认证，即用户没有必要的凭据。用户需要重新登陆
    AUTH_INVALID_JWT(501,"JWT不合法"),
    AUTH_JWT_EXPIRED(502,"JWT过期"),

    AUTH_INVALID_ACCOUNT(700, "用户名不存在或者密码错误"),
    AUTH_ID_NOTEXIST(701,"登录用户id不存在"),
    AUTH_USER_FORBIDDEN(702,"用户被禁止登录"),
    AUTH_NEED_LOGIN(704, "需要先登录"),



    // 402 Payment Required
    // 此响应代码保留供将来使用。创建此代码的最初目的是将其用于数字支付系统，但是此状态代码很少使用，并且不存在标准约定。

    // 403 Forbidden
    // 客户端没有访问内容的权限；也就是说，它是未经授权的，因此服务器拒绝提供请求的资源。与 401 Unauthorized 不同，服务器知道客户端的身份。
    AUTH_NOT_ALLOW(705,"无权限访问"),
    RESOURCE_ID_OUTSCOPE(505,"操作的资源id不是自己的对象"),
    FILE_NO_WRITE_PERMISSION(506,"目录文件夹没有写入的权限"),

    // 404 Not Found
    // 服务器找不到请求的资源。在浏览器中，这意味着无法识别 URL。在 API 中，这也可能意味着端点有效，但资源本身不存在。
    ALIPAY_TRADE_NOT_EXIST(500005,"交易不存在"),
    RESOURCE_ID_NOTEXIST(504,"操作的资源id不存在"),

    // 405 Method Not Allowed
    // 服务器知道请求方法，但目标资源不支持该方法。例如，API 可能不允许调用DELETE来删除资源。

    // 406 Not Acceptable
    // 请求的资源的内容特性无法满足请求头中的条件，因而无法生成响应实体，该请求不可接受。

    // 407 Proxy Authentication Required
    // 与401响应类似，只不过客户端必须在代理服务器上进行身份验证。

    // 408 Request Timeout
    // 此响应由一些服务器在空闲连接上发送，即使客户端之前没有任何请求。这意味着服务器想关闭这个未使用的连接。

    // 409 Conflict
    // 表示因为请求存在冲突无法处理该请求，例如多个同步更新之间的编辑冲突。

    // 410 Gone
    // 表示所请求的资源不再可用。HTTP 规范打算将此状态代码用于“有限时间的促销服务”。但大多数服务端不会使用此状态码，而是直接使用404状态码。

    // 411 Length Required
    // 服务端拒绝该请求因为 Content-Length 头部字段未定义但是服务端需要它。

    // 412 Precondition Failed
    // 服务器在验证请求头字段中给出先决条件时，没能满足其中的一个或多个。这个状态码允许客户端在获取资源时在请求头字段数据中设置先决条件，以此避免该请求方法被应用到其希望的内容以外的资源上。

    // 413 Payload Too Large
    // 请求实体大于服务器定义的限制。服务器可能会关闭连接，或在标头字段后返回重试 Retry-After。
    IMG_SIZE_EXCEED(509,"图片大小超限"),

    // 414 URI Too Long
    // 请求的URI长度超过了服务器能够解释的长度，因此服务器拒绝对该请求提供服务。

    // 415 Unsupported Media Type
    // 服务器不支持请求数据的媒体格式，因此服务器拒绝请求。wiki 搜索 "互联网媒体类型"
    IMG_FORMAT_ERROR(508,"图片格式不正确"),


    // 416 Range Not Satisfiable
    // 无法满足请求中 Range 标头字段指定的范围。该范围可能超出了目标 URI 数据的大小。

    // 417 Expectation Failed
    // 服务器无法满足 Expect 请求标头字段所指示的期望。

    // 418 I'm a teapot
    // 服务端拒绝用茶壶煮咖啡。

    // 421 Misdirected Request
    // 该请求针对的是无法产生响应的服务器（例如因为连接重用）。

    // 422 Unprocessable Entity
    // 请求格式正确，但是由于含有语义错误，无法响应。

    // 423 Locked
    // 正在访问的资源已锁定。

    // 424 Failed Dependency
    // 由于之前的某个请求发生的错误，导致当前请求失败，例如PROPPATCH。

    // 425 Too Early
    // 表示服务器不愿意冒险处理可能被重播的请求。wiki 搜索 "重放攻击"

    // 426 Upgrade Required
    // 服务器拒绝处理当前协议执行的请求，客户端应使用新版协议，如TLS/1.0。

    // 428 Precondition Required - 乐观锁
    // 服务器要求请求是有条件的。这是为了防止“未更新”问题，即客户端读取（GET）一个资源的状态，更改它，并将它写（PUT）回服务器，但这期间第三方已经在服务器上更改了该资源的状态，因此导致了冲突。

    // 429 Too Many Requests
    // 用户在给定的时间内发送了太多请求

    // 431 Request Header Fields Too Large
    // 服务器不愿处理请求，因为一个或多个头字段过大。

    // 440 Login Time-out
    // 客户端session超时失效，需要重新登录。

    // 451 Unavailable For Legal Reasons
    // 该访问因法律的要求而被拒绝，由IETF在2015核准后新增加。


    /*           服务端错误响应           */

    // 500 Internal Server Error
    // 通用错误消息，服务器遇到了一个未曾预料的状况，导致了它无法完成对请求的处理。没有给出具体错误信息。
    //支付宝
    ALIPAY_QRCODE_CREATE_FAIL(500001,"支付宝预下单接口调用失败"),
    ALIPAY_TRADE_QUERY_FAIL(500002,"支付宝交易查询接口调用失败"),
    ALIPAY_TRADE_CANCEL_FAIL(500003,"支付宝交易取消接口调用失败"),
    ALIPAY_TRADE_REFUND_FAIL(500004,"支付宝退款接口调用失败"),

    MESSAGE_SEND_FAIL(500009,"消息发送失败"),

    INTERNAL_SERVER_ERR(500,"服务器内部错误"),

    // 501 Not Implemented
    // 服务器不支持请求方法，因此无法处理。

    // 502 Bad Gateway
    // 作为网关或者代理工作的服务器尝试执行请求时，得到一个错误的响应。

    // 503 Service Unavailable
    // 服务器没有准备好处理请求。常见原因是服务器因维护或重载而停机。如果可能的话，HTTP 标头 Retry-After 字段应该包含恢复服务之前的估计时间。

    // 504 Gateway Timeout
    // 作为网关或者代理工作的服务器尝试执行请求时，无法及时获得响应。

    // 505 HTTP Version Not Supported
    // 服务器不支持请求中使用的 HTTP 版本。

    // 506 Variant Also Negotiates
    // 服务器存在内部配置错误
    //RocketMQ
    ROCKETMQ_CLIENT_EXCEPTION(500008,"Rocketmq客户端初始化失败"),

    // 507 Insufficient Storage
    // 存储空间不足。这个状况被认为是临时的。

    // 508 Loop Detected
    // 服务器在处理请求时检测到无限循环。

    // 510 Not Extended
    // 客户端可以发送一个包含扩展声明的请求，该声明描述了要使用的扩展。如果服务器接收到这样的请求，但是请求不支持任何所描述的扩展，那么服务器将使用 510 状态码进行响应。

    // 511 Network Authentication Required
    // 指示客户端需要进行身份验证才能获得网络访问权限。










    /* *********** 自定义错误码 *********** */
    //客户端错误 6xx



    /***************************************************
     *    系统级返回码
     **************************************************/

    //状态码 200

    STATENOTALLOW(507,"当前状态禁止此操作"),
    RESOURCE_FALSIFY(511, "信息签名不正确"),

    //所有需要登录才能访问的API都可能会返回以下错误
    //状态码 400






    //状态码 401

    //以下错误码提示可以自行修改
    //--------------------------------------------

    //状态码 403


    //所有路径带id的API都可能返回此错误
    //状态码 404


    //状态码 500


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
