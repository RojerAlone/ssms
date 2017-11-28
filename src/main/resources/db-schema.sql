CREATE DATABASE IF NOT EXISTS `ssms` DEFAULT CHARACTER SET UTF8;

CREATE TABLE IF NOT EXISTS `ssms`.`ground` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '场地ID',
  `name` VARCHAR(20) NOT NULL COMMENT '场地名称',
  `type` INT NOT NULL COMMENT '场地类型，1 表示羽毛球馆，2 表示健美操室，3 表示健身房，4 表示乒乓球馆',
  PRIMARY KEY (`id`)
) ENGINE = MyISAM DEFAULT CHARSET = UTF8 COMMENT '场地信息';

CREATE TABLE IF NOT EXISTS `ssms`.`closeinfo` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` INT COMMENT '场馆类型',
  `start_time` DATETIME NOT NULL COMMENT '不开放开始时间',
  `end_time` DATETIME DEFAULT NULL COMMENT '不开放结束时间，如果为空表示整天都不开放',
  `reason` VARCHAR(100) DEFAULT NULL COMMENT '不开放原因',
  PRIMARY KEY (`id`)
) ENGINE = MyISAM DEFAULT CHARSET = UTF8 COMMENT '场馆不开放信息';

CREATE TABLE IF NOT EXISTS `ssms`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `gid` INT NOT NULL COMMENT '场地ID',
  `uid` LONG NOT NULL COMMENT '学工号',
  `start_time` DATETIME NOT NULL COMMENT '开始时间',
  `end_time` DATETIME NOT NULL COMMENT '结束时间',
  `total` INT NOT NULL DEFAULT 0 COMMENT '订单价钱',
  `pay_type` INT NOT NULL DEFAULT 0 COMMENT '支付方式，0 表示一卡通支付，1 表示支付宝支付，2 表示微信支付，默认为校园卡支付',
  `stat` INT NOT NULL DEFAULT 0 COMMENT '订单状态，0 表示正常，1 表示已使用，2 表示已取消',
  `ctime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建订单时间',
  PRIMARY KEY (`id`),
  INDEX `time`(`start_time`),
  INDEX `group`(`gid`)
) ENGINE = InnoDB DEFAULT CHARSET = UTF8 COMMENT '订单信息';

CREATE TABLE IF NOT EXISTS `ssms`.`longorder` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `gid` INT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = MyISAM DEFAULT CHARSET = UTF8 COMMENT '长期预订信息';