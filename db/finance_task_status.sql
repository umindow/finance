/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost
 Source Database       : finance

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : utf-8

 Date: 02/02/2018 20:19:47 PM
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `finance_task_status`
-- ----------------------------
DROP TABLE IF EXISTS `finance_task_status`;
CREATE TABLE `finance_task_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_type` int(11) DEFAULT NULL COMMENT '数据类型,6大类',
  `data_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '数据类型,不必填',
  `create_time` timestamp NULL DEFAULT NULL,
  `result` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;
