/*
Navicat MySQL Data Transfer

Source Server         : myDB
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : middle

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-02-11 21:37:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bank_credit_info
-- ----------------------------
DROP TABLE IF EXISTS `bank_credit_info`;
CREATE TABLE `bank_credit_info` (
  `primary_id` varchar(40) NOT NULL COMMENT '银行授信记录标示ID',
  `org_id` varchar(40) NOT NULL COMMENT '机构编号（金融办分配的公司编号）,默认为：渝061001L',
  `bank_id` varchar(40) DEFAULT NULL COMMENT '银行编号',
  `main_bank_id` varchar(40) DEFAULT NULL COMMENT '主办行编码',
  `credit_type` varchar(40) NOT NULL COMMENT '授信类型：1 综合授信 2 单笔单议',
  `credit_money` decimal(14,2) NOT NULL COMMENT '授信额度',
  `leave_money` decimal(14,2) DEFAULT NULL COMMENT '授信余额',
  `blowup_mulpitle` decimal(14,2) DEFAULT NULL COMMENT '放大倍数',
  `bail_money` decimal(14,2) DEFAULT NULL COMMENT '初始保证金额',
  `bail_scale` decimal(14,0) NOT NULL COMMENT '保证金比例（%）',
  `credit_start_date` date NOT NULL COMMENT '授信开始日期',
  `credit_end_date` date NOT NULL COMMENT '授信结束日期',
  `single_money_limit` decimal(14,2) NOT NULL COMMENT '单笔限额',
  `is_for_credit` varchar(10) NOT NULL COMMENT '是否循环授信:1 是 2 否',
  `credit_status` varchar(10) DEFAULT NULL COMMENT '状态：1 使用 2 解除',
  `item_lean` varchar(900) DEFAULT NULL COMMENT '项目偏好',
  `remark` varchar(900) DEFAULT NULL COMMENT '备注',
  `batch_date` varchar(40) NOT NULL COMMENT '批次（当前传输日期，格式yyyyMMdd）',
  `id` bigint(40) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
