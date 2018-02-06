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

 Date: 02/02/2018 20:21:40 PM
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `finance_user`
-- ----------------------------
DROP TABLE IF EXISTS `finance_user`;
CREATE TABLE `finance_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '用户密码',
  `user_cn_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '用户中文名',
  `level` int(2) DEFAULT '1' COMMENT '用户级别',
  `email` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '用户电话',
  `data_levels` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '用户数据权限',
  `user_status` int(11) DEFAULT '1' COMMENT '用户状态',
  `dep_id` int(11) DEFAULT null COMMENT '部门ID',
  PRIMARY KEY (`id`),
  KEY `login_index` (`user_name`,`password`) COMMENT '组合登录索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;
