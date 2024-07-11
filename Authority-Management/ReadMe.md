基于角色的权限系统

基于spring的软件系统的设计过程

常见的权限管理框架（单体应用）：

- spring security
- shiro



本系统：基于微服务体系结构的权限管理系统



两个月：

- 需求分析
- 数据库设计
- API设计
- 体系结构设计
- 详细设计

两点：

1. 软件设计依赖于特定的软件架构
2. 软件设计的过程不是一蹴而就的



## 设计目标

- 支持jwt认证方式
  - 对比服务器为每个用户留session，不用受到内存空间的限制
  - 加密
  - 防伪
  - 防篡改
- 支持唯一登录，即一个账号只能在一台设备上登录
- 支持基于角色的权限管理
- 支持用户的权限代理
- 支持用的正常和失败的访问日志
- 支持多个网关并行工作
- 支持关键数据的加密以及防篡改
- 支持性能的度量

## 设计原则

采用分层的结构，系统分为controller、service、DAO和mapper层

- controller 层负责
  1. 传入参数的检验
  2. 返回值的组装
  3. 所有Exception的处理
- service 层负责协调的作用
- Dao层负责
  1. 对象模型的组装
  2. Redis缓存的处理
- M层负责SQL语句

## 数据表设计-ER图

- 用户
- 角色
- 权限
- 多对多

### user 表

- id
- username 登录账号
- password 登录密码
- email 邮箱
- email_verified 邮箱已验证
- name 用户名称 - 唯一索引
- avatar 头像
- last_login_time 最后登录时间
- last_login_ip 最后登录ip
- open_id 存储第三方登录的openid
- state 状态 0正常 1禁止访问
- signature 签名



实体表

关系表

![image-20240124004512708](C:\Users\Lacia\AppData\Roaming\Typora\typora-user-images\image-20240124004512708.png)

![image-20240124004532064](C:\Users\Lacia\AppData\Roaming\Typora\typora-user-images\image-20240124004532064.png)

![image-20240124004558695](C:\Users\Lacia\AppData\Roaming\Typora\typora-user-images\image-20240124004558695.png)

## RestFulAPI设计

## 对象状态分析

## 瓶颈分析

## 体系结构之组件设计

- 权限服务
- 网关

## 体系结构之部署设计

![image-20240124082643940](C:\Users\Lacia\AppData\Roaming\Typora\typora-user-images\image-20240124082643940.png)



## 前端功能

1. 权限信息录入
2. 用户的注册和登录
3. 权限AOP编写
4. 测试