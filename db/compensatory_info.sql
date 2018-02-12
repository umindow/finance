/*
Navicat MySQL Data Transfer

Source Server         : myDB
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : middle

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-02-11 21:38:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for compensatory_info
-- ----------------------------
DROP TABLE IF EXISTS `compensatory_info`;
CREATE TABLE `compensatory_info` (
  `org_id` varchar(40) NOT NULL COMMENT '机构编码，默认：渝061001L',
  `contract_id` varchar(40) DEFAULT NULL COMMENT '合同编号',
  `replace_date` date DEFAULT NULL COMMENT '代偿日期',
  `replace_money` decimal(14,2) DEFAULT NULL COMMENT '代偿金额',
  `batch_date` varchar(40) NOT NULL COMMENT '批次，yyyyMMdd',
  `proj_id` varchar(40) NOT NULL COMMENT '项目编码',
  `id` bigint(40) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=400 DEFAULT CHARSET=utf8;
