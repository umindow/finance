/*
Navicat MySQL Data Transfer

Source Server         : uchoice-ucenter
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : finance

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-02-03 15:50:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for finance_config
-- ----------------------------
DROP TABLE IF EXISTS `finance_config`;
CREATE TABLE `finance_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `config_type` int(11) DEFAULT NULL,
  `create_user_id` bigint(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_user_id` bigint(20) DEFAULT NULL,
  `config_content` longtext COLLATE utf8_bin,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
