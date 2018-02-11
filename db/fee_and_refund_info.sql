/*
Navicat MySQL Data Transfer

Source Server         : myDB
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : middle

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-02-11 21:38:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for fee_and_refund_info
-- ----------------------------
DROP TABLE IF EXISTS `fee_and_refund_info`;
CREATE TABLE `fee_and_refund_info` (
  `org_id` varchar(40) NOT NULL COMMENT '机构编码,有默认值:渝061001L',
  `proj_id` varchar(40) NOT NULL COMMENT '项目编码',
  `contract_id` varchar(90) DEFAULT NULL,
  `charge_way` varchar(10) DEFAULT NULL COMMENT '收退费标示：1 收费 2 退费',
  `charge_type` varchar(10) DEFAULT NULL COMMENT '费用类型编码：1 担保费 2 保证金 3 其它',
  `charge_time` date DEFAULT NULL COMMENT '实际缴费时间',
  `charge_money` decimal(14,2) DEFAULT NULL COMMENT '实际缴费金额',
  `batch_date` varchar(40) NOT NULL COMMENT '批次（当前传输日期，格式yyyyMMdd）',
  `id` bigint(40) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=500 DEFAULT CHARSET=utf8;
