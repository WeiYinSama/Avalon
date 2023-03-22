# Goods-demo 项目笔记

## 项目流程

1. 确定项目目录结构

   - vo在controller层
   - bo在dao层
   - po在mapper层

2. 需求分析

   1. 商品管理 - CRUD
   2. 商品销售管理 - 上架、下架
   3. 相关商品推荐 - 商品相关信息

3. 领域建模

   - product表
   - onsale表
   - goods表

4. 引入依赖

5. application.yaml

6. mybatis-generaotor-config

   - 自动生成mapper层
   - 补充@Mapper注解
   - xml中的元素有顺序

7. Dao层编写及测试 - 数据访问层

   - ProductDao

     1. 创建商品
     2. 根据商品id获得商品
     3. 根据商品名获得商品
     4. 修改商品
     5. 删除商品

   - OnSaleDao

     1. 获取商品最近上架信息

   - bo模型构建

     - 负责获取数据，组装成对象模型

     - 通过单表查询数据拼成完整对象

   - 自定义通用异常类，向上层抛出

8. Service层编写及测试

   - ProductService
     1. 创建商品
     2. 根据商品id获得商品
     3. 根据商品名获得商品
     4. 修改商品
     5. 删除商品
   - 在该层使用注解 @Transactional
   - 向上抛异常

9. Controller层测试及编写

   - 接口实现
   - 异常处理
   - 将从客户端拿到的vo转换成bo给服务层
   - 从服务层拿到的bo转换成vo交给客户端

10. 通用工具类

    1. BusinessException
       - 用于统一封装异常信息
    2. Common.returnWithStatus()
       - 根据不同的异常返回对应的 HttpStatus
    3. ResponseUtil
       - 封装响应体
    4. ReturnNo
       - 自定义的各种异常信息

11. 跨域访问配置

## 总结

### 多层体系结构的优点：

1. 结构简单，便于不同技能的程序员分工负责不同的层
2. 便于测试，每一层都可以独立测试
3. 变更可控，可以把代码的变更控制在一层之内，不会影响其他层



### 通用工具类的优化

针对controller层，出现异常时的响应、成功时的响应



### 由vo产生bo，由bo产生vo只能在vo代码里

### 由po产生bo，由bo产生po只能在bo代码里



## 项目阶段二

AOP引入

1. 新增了request body中的参数合法性检查
1. page、pageSize和controller层异常处理
1. 新增了基于jwt的身份验证
1. 引入了日志框架



# 项目总结

## 需求分析

1. 商品管理
2. 商品上架管理
3. 相关商品推荐

## 领域建模

- product
- onsale
- goods

## Mybatis框架访问数据库

利用mybatis generator生成mapper层及po类

## 增加分页

## 引入日志框架

## 自定义AvalonException通用异常处理类

## 自定义AvalonStatus枚举各种情况（异常、成功）

## 使用AOP对controller层进行统一的异常处理，根据不同的异常返回不同的状态码

## 使用AOP对page、pageSize进行检查

- 检测到异常之后直接返回 - 待优化

## 引入validation校验request body中的参数

## 基于jwt的身份验证及参数赋值
