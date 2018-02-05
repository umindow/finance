/*
Navicat MySQL Data Transfer

Source Server         : myDB
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : middle

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-02-05 23:06:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `usename` varchar(100) NOT NULL COMMENT '用户名',
  `psd` varchar(100) NOT NULL COMMENT '密码',
  `belong_depment` varchar(10) NOT NULL DEFAULT '11111' COMMENT '所属部门，按位数配置，\r\n超级管理员：11111\r\n第一位：财务部\r\n第二位：综合营运部\r\n第三位：风险管理部\r\n第四位：资产管理及法律事务部\r\n第五位：其它',
  `remark` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('admin', '123456', '11111', null);
INSERT INTO `user_info` VALUES ('userF', '123456', '10000', null);
INSERT INTO `user_info` VALUES ('userS', '123456', '01000', null);
INSERT INTO `user_info` VALUES ('userR', '123456', '00100', null);
INSERT INTO `user_info` VALUES ('userA', '123456', '00010', null);
