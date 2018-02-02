/*
Navicat MySQL Data Transfer

Source Server         : myDB
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : middle

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-02-01 22:35:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for repayment_info
-- ----------------------------
DROP TABLE IF EXISTS `repayment_info`;
CREATE TABLE `repayment_info` (
  `org_id` varchar(40) NOT NULL COMMENT '机构编码，默认：渝061001L',
  `proj_id` varchar(40) NOT NULL COMMENT '项目编码',
  `contract_id` varchar(40) NOT NULL COMMENT '合同编号',
  `repay_date` date NOT NULL COMMENT '实际还款日期',
  `principal` decimal(14,2) NOT NULL COMMENT '实际归还本金',
  `interest` decimal(14,2) DEFAULT NULL COMMENT '实际归还利息',
  `punish_money` decimal(14,2) DEFAULT NULL COMMENT '收取罚息',
  `batch_date` varchar(40) NOT NULL COMMENT '批次（当前传输日期，格式：yyyyMMdd）',
  `id` bigint(40) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8;
