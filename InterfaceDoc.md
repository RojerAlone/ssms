# 接口文档

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

### 长期订单
长期订单是团体长期预订的，比如每周几某个时间段使用

| 字段 | 类型 | 含义 |
| :--- | :--- | :--- |
| id | int | 订单ID |
| gid | int | 场地ID|
| startDate | long | 开始日期 |
| endDate | long | 结束日期|
| startTime | long | 开始时间，如果为空表示占用该场地一整天 |
| endTime | long | 结束时间，如果为空表示占用该场地一整天 |
| weekday | int | 每周几 |
| stat | int | 0 表示正常，1 表示已删除 |

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