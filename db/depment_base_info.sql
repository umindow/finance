/*
Navicat MySQL Data Transfer

Source Server         : myDB
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : middle

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-02-05 23:06:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for depment_base_info
-- ----------------------------
DROP TABLE IF EXISTS `depment_base_info`;
CREATE TABLE `depment_base_info` (
  `depMentId` int(5) NOT NULL COMMENT '部门ID',
  `depMentName` varchar(100) DEFAULT NULL COMMENT '部门名称',
  `viewData` varchar(10) DEFAULT '11111111' COMMENT '数据类型：1为不属于，0为属于\r\n例如：11111100\r\n第一位：业务数据信息\r\n第二位：银行授信信息\r\n第三位：还款信息\r\n第四位：收费退费信息\r\n第五位：代偿信息\r\n第六位：追偿信息\r\n第七位：其它信息1\r\n第八位：其它信息2',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`depMentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of depment_base_info
-- ----------------------------
INSERT INTO `depment_base_info` VALUES ('1', '财务部', '01001111', null);
INSERT INTO `depment_base_info` VALUES ('2', '综合营运部', '00111111', null);
INSERT INTO `depment_base_info` VALUES ('3', '风险管理部', '01110111', null);
INSERT INTO `depment_base_info` VALUES ('4', '资产管理以及法律事务部', '11111011', null);
