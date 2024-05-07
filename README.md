# 农产品供销管理系统

先说一下。该项目是一个大部分可复用的管理系统模版,我觉得。本来是一个完整系统,但是前台不想继续写了,就这样吧。大部分功能写上并已经实现。

## 涉及用到的技术栈

(浅浅说一下,我们拿了Fisco-bcos的数据，但是并没有部署合约,就是只实现了区块链网络监控的效果。但是封装了2连接工具类,开箱即用，看下面图片。

再提一句,Druid连接池跟fisco-java-sdk冲突,我合约交互用的WeFrontService来实现,因此数据库配置那里就没改,如果想用ContractService,就是将数据库连接池配置改成其他连接池配置)

![image](https://github.com/junzhumio/goods-manage/assets/119744044/4acfda6c-d226-4b06-8318-2c67d735031a)

服务端：SpringBoot、SpringSecurity、Mybatis-plus、Redis、Fisco-Bcos、Druid、Hutool、阿里云OSS

客户端：Vue、Vuex、Vue-router、Element-UI、Echars

## 安装使用

先说一下,本系统,用到了Fisco-bcos的中间件平台webase的子系统webase-front。这里不做赘述。
<br>
服务端:
<br>

1.Fisco配置更改：

将下图fisco配置更改为自己搭建的fisco链、webase-front配置。如果要进行合约部署,并且要用WeFrontService合约交互，将合约配置里面填上

![image](https://github.com/junzhumio/goods-manage/assets/119744044/bc9f2b2d-1790-4986-9a60-f53c2b523666)


2.更改MySQL、Redis服务器的连接配置

3.德鲁伊监控配置更改：

在common模块下面,该位置。默认是账号：admin,密码: 123456

![image](https://github.com/junzhumio/goods-manage/assets/119744044/48e9d17e-eb24-4592-856b-049f72ffcc05)


4.swagger配置修改

登录系统之后,访问<span>http://localhost:8110/swagger-ui.html</span>进入以下页面。

![image](https://github.com/junzhumio/goods-manage/assets/119744044/59012f6f-eae9-458e-9b77-ed91e4ec2f87)


默认无法接口调用,需要：随便一个点击接口调用,点击红色叹号、填写Bearer+登录token、点击Authorize之后,即可请求携带token，才能进行接口调用。

![image](https://github.com/junzhumio/goods-manage/assets/119744044/e1967b17-aa5b-4657-b4f0-94678c1b9cf1)
<br>
客户端:
<br>

用控制台打开front-me里面front-admin目录
```
# 先执行
npm i
# 之后执行
npm run serve
```

## 项目演示

这里只演示部分页面
### 演示视频
我这里只是改了名字,还是那个项目:
<video src="https://github.com/junzhumio/BAP/assets/119744044/346fddd1-25fb-4a59-80f4-438f04e2c7c8"></video>

### 登录

![image](https://github.com/junzhumio/goods-manage/assets/119744044/3f775455-b6f8-49ea-859c-df7113cd6ee3)


### 注册

![1715074960389](https://github.com/junzhumio/goods-manage/assets/119744044/9bb336a7-55f6-4704-855b-edbcea21eeca)


### 首页

![1715075616524](https://github.com/junzhumio/goods-manage/assets/119744044/85b42969-b37e-4e33-9ac2-4599911ca1ef)


### 用户管理

就是展示一些前台用户信息,还有对用户的增删改查。

![1715075279497](https://github.com/junzhumio/goods-manage/assets/119744044/155a769b-85e7-446c-87ec-bc5ed6235de5)


### 服务监控

![1715075796103](https://github.com/junzhumio/goods-manage/assets/119744044/589b7d94-f940-4fb8-86fb-9ec905a28202)


### 区块链监控

输入块高,点击查询,可显示区块信息

![1715076209454](https://github.com/junzhumio/goods-manage/assets/119744044/7e64e161-5b8c-4476-8c65-7819ba0f6c2a)


### 缓存监控

![1715076152054](https://github.com/junzhumio/goods-manage/assets/119744044/6437d561-b494-4c40-ad61-700cab03174b)


### 个人中心

![1715076237946](https://github.com/junzhumio/goods-manage/assets/119744044/afd5383d-dc50-4d57-9138-69ff73e9fbc5)
