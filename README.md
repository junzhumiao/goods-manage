# 基于区块链技术的农产品供销管理系统
## 项目介绍

基于农产品技术的供销管理系统。采用SpringBoot作为基础框架，数据层框架为Mybatis、Spring Sercurity作安全框架、用Redis做缓存、部分业务数据通过fisco-bcos(区块链网络)做持久化化处理，Solidity编写智能合约、主要业务数据用MySQL做持久化处理。前端：采用Vue作为基础框架。使用vue-router做路由控制、vuex做状态管理、echars做图表渲染、axios负责前后端交互。

## 视频效果展示
该视频效果展示,为之前测试写的,因此标题并没有改过来。大部分功能已经实现。


https://github.com/junzhumio/BAP/assets/119744044/346fddd1-25fb-4a59-80f4-438f04e2c7c8


## 项目安装以及定义
### 区块链网络搭建
fisco-bcos介绍：https://fisco-bcos-documentation.readthedocs.io/zh-cn/latest/index.html
<br>
fisco-bcos 4节点区块链网络搭建：https://blog.csdn.net/qq_63235624/article/details/130910189
<br>
webase-front 节点前置服务搭建：https://blog.csdn.net/dyw_666666/article/details/124577214
### 后端配置更改
用IDEA打开back-me项目。将application-druid.yml主库数据源中的url、username、password改成用户自己mysql数据库的配置。如下面所示:
![image](https://github.com/junzhumio/BAP/assets/119744044/44c304e5-f6d8-468f-bcc6-7647529fa2d6)
将application-redis.yml中的host、port、password、database改成自己redis服务器的配置。
![image](https://github.com/junzhumio/BAP/assets/119744044/cb60c07d-9630-4f58-8d49-d5a3c7ca9eda)

将application.yml中的配置改成上述fisco网络搭建时候的配置。
![image](https://github.com/junzhumio/BAP/assets/119744044/7aeb23a4-4e15-4bb0-a8ca-76c05c77c224)

数据监控页面账号。我们可以在common模块下面如下文件修改账号密码。

OOS对象存储服务,可找到相应工具类,将替换成自己的配置。

### 前端配置更改
用VSCode打开front-me项目。
<br>
在终端先输入:
npm i
<br>
完成后,输入:
npm run serve
