/*
Navicat MySQL Data Transfer

Source Server         : myDB
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : middle

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-03-17 22:36:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for finance_task_status
-- ----------------------------
DROP TABLE IF EXISTS `finance_task_status`;
CREATE TABLE `finance_task_status` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `data_type` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '6大类数据类型 ：1 银行授信  2 业务信息 3 还款信息 4 收退费信息 5 代偿信息 6 追偿信息',
  `data_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '数据类型的名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `result` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '执行结果：0, 成功  -1 失败',
  `op_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '执行类型：0 同步数据 1 上报数据',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `op_time` datetime DEFAULT NULL COMMENT '执行时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
