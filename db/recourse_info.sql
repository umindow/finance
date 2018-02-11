/*
Navicat MySQL Data Transfer

Source Server         : myDB
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : middle

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-02-11 21:38:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for recourse_info
-- ----------------------------
DROP TABLE IF EXISTS `recourse_info`;
CREATE TABLE `recourse_info` (
  `org_id` varchar(40) NOT NULL,
  `proj_id` varchar(40) NOT NULL,
  `contract_id` varchar(40) DEFAULT NULL COMMENT '合同编号',
  `replevy_type` varchar(10) DEFAULT NULL COMMENT '追偿类型：1 普通追偿 2 挽回损失',
  `replevy_date` date DEFAULT NULL COMMENT '追偿日期',
  `replevy_money` decimal(14,2) DEFAULT NULL COMMENT '追偿金额',
  `batch_date` varchar(40) NOT NULL COMMENT '批次，yyyyMMdd',
  `id` bigint(40) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=600 DEFAULT CHARSET=utf8;
