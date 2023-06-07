# Http请求Dubbo接口

### 概述

背景: 由于项目中需要做跨机房同步, 公司主要技术栈基于dubbo, 所以在实现时想着模仿者 `Feign` 做dubbo接口代理. 
本项目为学习项目, 通过学习 `Feign` 源码做一些练习

### 依赖

```xml
<dependency>
    <groupId>cn.idea360</groupId>
    <artifactId>dubbo-rest-client</artifactId>
    <version>0.0.1</version>
</dependency>
```

### 项目说明

1. 为了无缝切换dubbo为http调用, 所以在 `@EnableRestProxy` 增加代理类型, `CONSUMER` 作为消费方, 需要将接口注入Bean, 然后通过 `@Resource` 直接引用. `PROVIDER` 作为服务提供方, 需要将 `dubbo` 接口通过 `http` 暴露出去.
2. `@RestClient` 支持通过 `@RestClient(url = "http://localhost:8080/proxy/api")` 直接传入url, 或者通过 `@RestClient(url = "${provider-user.url}")` 传入变量.


