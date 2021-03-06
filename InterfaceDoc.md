# 接口文档
[TOC]
## 实体

### 场地信息
每个场地都有自己的 id，但是场地类型可能相同

| 字段 | 类型 | 含义 |
| :--- | :--- | :--- |
| id | int | 编号 |
| name | string | 场地名称|
| type | int | 场地类型，1 表示羽毛球馆，2 表示健美操室，3 表示健身房，4 表示乒乓球馆 |
| used | bool | 场地是否已使用 |

### 用户订单

| 字段 | 类型 | 含义 |
| :--- | :--- | :--- |
| id | int | 订单ID |
| gid | int | 场地ID|
| uid | string | 用户学工号 |
| startTime | long | 开始时间 |
| endTime | long | 结束时间 |
| total | int | 订单价钱 |
| payType | int | 支付方式，0 表示一卡通支付，1 表示支付宝支付，2 表示微信支付，默认为校园卡支付 |
| stat | int | 订单状态，0 表示未支付，1 表示已支付，2 表示已取消 |
| ctime | long | 创建订单时间 |

### 场地关闭
闭馆信息，也存储特殊占用的订单

| 字段 | 类型 | 含义 |
| :--- | :--- | :--- |
| id | int | ID |
| gid | int | 场地ID|
| startDate | long | 闭馆日期 |
| endDate | long | 开放日期|
| startTime | long | 闭馆开始时间，如果为空表示整天都不开放 |
| endTime | long | 开放时间，如果为空表示整天都不开放 |
| reason | string | 不开放原因 |
| stat | int | 0 表示正常，1 表示已删除 |

### 返回信息
接口返回格式为 json

| 字段 | 类型 | 含义 |
| :--- | :--- | :--- |
| success | bool | 是否请求成功 |
| msg | string | 返回的提示信息 |
| result | json | 请求结果 |

## 接口
接口存在名字相同的情况，用 HTTP METHOD 表示接口是干什么的（REST 接口）。

文档中接口格式为 interface  method，interface 为接口名字，method 为 HTTP 方法。如果 interface 中含有 {}，则 {} 中的信息是参数中的。
### 用户使用

用户可以使用的功能有如下：

- 登录、登出（使用学工号登录，登录成功后向后端请求登录接口）
- 查看要预订时间段的场地信息
- 订单相关：预订、取消、获取订单信息

#### /user/login    POST
用户登录

| 参数 | 类型 | 必需 | 含义 |
| :--- | :--- | :--- | :--- |
| uid | string | true | 学工号 |
| authToken | string | true | 网络中心提供的登录接口登录成功后返回的验证字段 |

#### /user/sport GET
获取用户最近 7 天的运动情况

#### /logout   POST
退出登录

#### /ground/all GET
获取指定时间段的场地使用情况
| 参数 | 类型 | 必需 | 含义 |
| :--- | :--- | :--- | :--- |
| type | int | true | 场地类型 |
| startTime | string | true | 开始时间 |
| endTime | string | true | 结束时间 |

#### /ground/badminton GET
获取某一天的羽毛球场地预订信息，如果没有传 date 字段就返回当天的信息

| 参数 | 类型 | 必需 | 含义 |
| :--- | :--- | :--- | :--- |
| date | string | false | 时间 |

#### /ground/gymnastics GET
获取最近一周的健美操室预订信息

#### /order/order POST
预订场地

| 参数 | 类型 | 必需 | 含义 |
| :--- | :--- | :--- | :--- |
| gid | int | true | 场地ID |
| startTime | string | true | 开始时间 |
| endTime | string | true | 结束时间 |

#### /order/cancel PUT
取消订单

| 参数 | 类型 | 必需 | 含义 |
| :--- | :--- | :--- | :--- |
| orderId | int | true | 订单ID |

#### /order/{id} GET
获取订单信息

| 参数 | 类型 | 必需 | 含义 |
| :--- | :--- | :--- | :--- |
| id | int | true | 订单ID |

#### /order/paying GET
获取未支付订单信息

| 参数 | 类型 | 必需 | 含义 |
| :--- | :--- | :--- | :--- |
| page | int | false | 页码 |
| nums | int | false | 每页显示的个数 |

#### /order/orders GET
获取已支付订单信息

| 参数 | 类型 | 必需 | 含义 |
| :--- | :--- | :--- | :--- |
| page | int | false | 页码 |
| nums | int | false | 每页显示的个数 |

#### /order/canceled GET
获取已取消订单信息

| 参数 | 类型 | 必需 | 含义 |
| :--- | :--- | :--- | :--- |
| page | int | false | 页码 |
| nums | int | false | 每页显示的个数 |

#### /message/{id} GET
获取一条通知

#### /message/messages GET
| 参数 | 类型 | 必需 | 含义 |
| :--- | :--- | :--- | :--- |
| page | int | false | 页码 |
| nums | int | false | 每页显示的个数 |

### root 使用

#### /root/login POST
root 用户登录

| 参数 | 类型 | 必需 | 含义 |
| :--- | :--- | :--- | :--- |
| username | string | true | 用户名 |
| password | string | true | 密码 |

#### /root/admin/{uid} PUT
添加管理员

| 参数 | 类型 | 必需 | 含义 |
| :--- | :--- | :--- | :--- |
| uid | string | true | 学工号 |

#### /root/admin/{uid} DELETE
删除管理员

| 参数 | 类型 | 必需 | 含义 |
| :--- | :--- | :--- | :--- |
| uid | string | true | 学工号 |

#### /root/admin/all GET
获取管理员列表

| 参数 | 类型 | 必需 | 含义 |
| :--- | :--- | :--- | :--- |
| page | int | false | 页码 |
| nums | int | false | 每页显示的个数 |

### 管理员使用

管理员对场地信息进行管理，包括长期订单、闭馆信息、用户普通订单，还可以添加管理员（root 用户才可以）。

#### /admin/login POST
管理员登录

| 参数 | 类型 | 必需 | 含义 |
| :--- | :--- | :--- | :--- |
| uid | string | true | 学工号 |
| authToken | string | true | 网络中心提供的登录接口登录成功后返回的验证字段 |

### root 和管理员都可以使用

#### /admin/payment/{id} PUT
管理员改变订单状态为已支付

#### /admin/gymnastics POST
管理员预订体操室
| 参数 | 类型 | 必需 | 含义 |
| :--- | :--- | :--- | :--- |
| date | string | true | 预订日期，格式为 yyyy-MM-dd |
| time | int | true | 预订时间段，0 为课余时间，1 为晚自习时间 |

#### /admin/gymnastics/{id} DELETE
管理员取消体操室订单

#### /closeinfo/all GET
获取所有的闭馆信息

| 参数 | 类型 | 必需 | 含义 |
| :--- | :--- | :--- | :--- |
| page | int | false | 页码 |
| nums | int | false | 每页显示的个数 |

#### /closeinfo/add PUT
添加闭馆信息

| 参数 | 类型 | 必需 | 含义 |
| :--- | :--- | :--- | :--- |
| gid | int | true | 场地ID |
| date | string | true | 闭馆开始日期 |
| startTime | string | false | 闭馆开始时间，如果为空表示闭馆日期整天都关闭 |
| endDate | string | false | 闭馆结束日期，如果为空表示只在闭馆当前关闭 |
| endTime | string | false | 闭馆结束时间，如果为空表示闭馆结束当天都关闭 |
| reason | string | false | 闭馆原因 |

#### /closeinfo/{id} DELETE
删除闭馆信息

#### /admin/message POST
添加通知
| 参数 | 类型 | 必需 | 含义 |
| :--- | :--- | :--- | :--- |
| title | string | false | 通知标题，可为空，为空时默认标题为 “通知” |
| content | string | true | 通知内容 |

#### /admin/message/{id} DELETE
删除通知