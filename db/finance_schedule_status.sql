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

 Date: 02/02/2018 20:18:50 PM
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `finance_schedule_status`
-- ----------------------------
DROP TABLE IF EXISTS `finance_schedule_status`;
CREATE TABLE `finance_schedule_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dup_key` varchar(80) COLLATE utf8_bin NOT NULL COMMENT '唯一key，',
  `schedule_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '任务名称',
  `schedule_status` int(11) DEFAULT NULL COMMENT '任务状态 1:初始化 2:失败 3:完成',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_dup_key` (`dup_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;
