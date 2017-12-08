CREATE DATABASE IF NOT EXISTS `ssms` DEFAULT CHARACTER SET UTF8;

CREATE TABLE IF NOT EXISTS `ssms`.`ground` (
  `id` INT NOT NULL COMMENT '场地ID',
  `name` VARCHAR(20) NOT NULL COMMENT '场地名称',
  `gid` INT NOT NULL COMMENT '场地类型，1 表示羽毛球馆，2 表示健美操室，3 表示健身房，4 表示乒乓球馆',
  PRIMARY KEY (`id`)
) ENGINE = MyISAM DEFAULT CHARSET = UTF8 COMMENT '场地信息';

CREATE TABLE IF NOT EXISTS `ssms`.`closeinfo` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` INT COMMENT '场馆ID',
  `start_date` DATE NOT NULL COMMENT '闭馆日期',
  `end_date` DATE NOT NULL COMMENT '开放日期',
  `start_time` TIME DEFAULT NULL COMMENT '闭馆开始时间，如果为空表示整天都不开放',
  `end_time` TIME DEFAULT NULL COMMENT '开放时间，如果为空表示整天都不开放',
  `reason` VARCHAR(100) DEFAULT NULL COMMENT '不开放原因',
  `stat` INT NOT NULL DEFAULT 0 COMMENT '0 表示正常，表示已删除',
  PRIMARY KEY (`id`)
) ENGINE = MyISAM DEFAULT CHARSET = UTF8 COMMENT '闭馆信息，也存储特殊占用的订单';

CREATE TABLE IF NOT EXISTS `ssms`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `gid` INT NOT NULL COMMENT '场地ID',
  `uid` VARCHAR(10) NOT NULL COMMENT '学工号',
  `start_time` DATETIME NOT NULL COMMENT '开始时间',
  `end_time` DATETIME NOT NULL COMMENT '结束时间',
  `total` INT NOT NULL DEFAULT 0 COMMENT '订单价钱',
  `pay_type` INT NOT NULL DEFAULT 0 COMMENT '支付方式，0 表示一卡通支付，1 表示支付宝支付，2 表示微信支付，默认为校园卡支付',
  `stat` INT NOT NULL DEFAULT 0 COMMENT '订单状态，0 表示未支付，1 表示已支付，2 表示已取消',
  `ctime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建订单时间',
  PRIMARY KEY (`id`),
  INDEX `group`(`gid`),
  INDEX `time`(`start_time`, `end_time`)
) ENGINE = InnoDB DEFAULT CHARSET = UTF8 COMMENT '订单信息';

CREATE TABLE IF NOT EXISTS `ssms`.`longorder` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `gid` INT NOT NULL COMMENT '场地ID',
  `start_date` DATE NOT NULL COMMENT '开始日期',
  `end_date` DATE NOT NULL COMMENT '结束日期',
  `start_time` TIME DEFAULT NULL COMMENT '开始时间，如果为空表示占用该场地一整天',
  `end_time` TIME DEFAULT NULL COMMENT '结束时间，如果为空表示占用该场地一整天',
  `weekday` INT NOT NULL COMMENT '每周几',
  `stat` INT NOT NULL DEFAULT 0 COMMENT '0 表示正常，1 表示已删除',
  PRIMARY KEY (`id`)
) ENGINE = MyISAM DEFAULT CHARSET = UTF8 COMMENT '长期预订信息';