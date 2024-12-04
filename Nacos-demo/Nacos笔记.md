# Nacos

## 1. 快速开始

### 1.1 下载地址

[Nacos（2.4.3）](https://github.com/alibaba/nacos/releases/tag/2.4.3)

### 1.2 解压缩

```bash
unzip nacos-server-$version.zip
# 或者 tar -xvf nacos-server-$version.tar.gz
cd nacos/bin
```

### 1.3 启动服务器

```shell
# Linux/Unix/Mac
bash startup.sh -m standalone
# Windows
startup.cmd -m standalone
```

### 1.4 pom

```xml
<!-- Nacos-discovery -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
<!-- loadbalancer -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-alibaba-dependencies</artifactId>
            <version>2022.0.0.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>2022.0.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### 1.5 application.yml

```yaml
server:
  # 随机端口号
  port: 0

spring:
  application:
  	# 服务注册名
    name: nacos-demo
  cloud:
    nacos:
      discovery:
      	# Nacos 服务地址
        server-addr: 127.0.0.1:8848
```

## 2. 开启鉴权

### 2.1 背景

将nacos部署在公网，由此引入权限控制

### 2.2 编辑Nacos配置

```properties
# 开启鉴权
nacos.core.auth.enabled=true
# 配置私钥
nacos.core.auth.plugin.nacos.token.secret.key=Q2lhbGxv772eKOKIoOODu8+JPCAp4oyS4piGQ2lhbGxv772eKOKIoOODu8+JPCAp4oyS4piG
# nacos（集群）服务间认证
nacos.core.auth.server.identity.key=ciallo~
nacos.core.auth.server.identity.value=ciallo~
```

> Note : 首次访问 Nacos 控制台会提示初始化密码
>
> Q2lhbGxv772eKOKIoOODu8+JPCAp4oyS4piG

### 2.3 权限管理

1. 创建普通用户

```properties
# 在 spring boot 中配置
username=user
password=Ciallo～(∠・ω< )⌒☆
```

2. 角色管理
3. 权限管理

### 2.4 在 spring boot 中配置

```yaml
spring:
  cloud:
    nacos:
      discovery:
        username: user
        password: Ciallo～(∠・ω< )⌒☆
```

