/*
Navicat MySQL Data Transfer

Source Server         : myDB
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : middle

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-02-05 23:06:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for depment_access_control
-- ----------------------------
DROP TABLE IF EXISTS `depment_access_control`;
CREATE TABLE `depment_access_control` (
  `depmentId` int(5) NOT NULL COMMENT '部门ID号',
  `dataName` varchar(40) NOT NULL COMMENT '数据名称',
  `operate` varchar(5) DEFAULT '1' COMMENT '数据操作方式，0为不可修改,1为可修改',
  `datatype` varchar(10) DEFAULT '0' COMMENT '数据信息类型，分类为：\r\n0：通用类型，如项目编号，机构编号，合同编号等\r\n1：业务数据信息\r\n2：银行授信信息\r\n3：还款信息\r\n4：收费退费信息\r\n5：代偿信息\r\n6：追偿信息',
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`dataName`,`depmentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of depment_access_control
-- ----------------------------
INSERT INTO `depment_access_control` VALUES ('2', 'accept_date', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('3', 'approve_option', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'area_first', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'area_second', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'area_third', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('3', 'assure_person', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'assure_rate', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'bail_money', '1', '2', null);
INSERT INTO `depment_access_control` VALUES ('2', 'bail_scale', '1', '2', null);
INSERT INTO `depment_access_control` VALUES ('2', 'bank_credit_primary_id', '1', '0', null);
INSERT INTO `depment_access_control` VALUES ('2', 'bank_id', '1', '2', null);
INSERT INTO `depment_access_control` VALUES ('1', 'batch_date', '0', '0', null);
INSERT INTO `depment_access_control` VALUES ('2', 'batch_date', '0', '0', null);
INSERT INTO `depment_access_control` VALUES ('3', 'batch_date', '0', '0', null);
INSERT INTO `depment_access_control` VALUES ('4', 'batch_date', '0', '0', null);
INSERT INTO `depment_access_control` VALUES ('2', 'blowup_mulpitle', '1', '2', null);
INSERT INTO `depment_access_control` VALUES ('2', 'business_type', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'calling_first', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'calling_second', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'capital_belong', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('1', 'charge_money', '1', '4', null);
INSERT INTO `depment_access_control` VALUES ('1', 'charge_time', '1', '4', null);
INSERT INTO `depment_access_control` VALUES ('1', 'charge_type', '1', '4', null);
INSERT INTO `depment_access_control` VALUES ('1', 'charge_way', '1', '4', null);
INSERT INTO `depment_access_control` VALUES ('1', 'client_bail_money', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'client_id', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('1', 'client_name', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'client_name', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('3', 'client_name', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'client_type', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'company_scale', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'contract_end_date', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('1', 'contract_id', '1', '0', null);
INSERT INTO `depment_access_control` VALUES ('2', 'contract_id', '1', '0', null);
INSERT INTO `depment_access_control` VALUES ('3', 'contract_id', '1', '0', null);
INSERT INTO `depment_access_control` VALUES ('4', 'contract_id', '1', '0', null);
INSERT INTO `depment_access_control` VALUES ('2', 'contract_money', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'co_bank_id', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'credit_end_date', '1', '2', null);
INSERT INTO `depment_access_control` VALUES ('2', 'credit_money', '1', '2', null);
INSERT INTO `depment_access_control` VALUES ('2', 'credit_start_date', '1', '2', null);
INSERT INTO `depment_access_control` VALUES ('2', 'credit_status', '1', '2', null);
INSERT INTO `depment_access_control` VALUES ('2', 'credit_type', '1', '2', null);
INSERT INTO `depment_access_control` VALUES ('2', 'id_card', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'id_card_type', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('1', 'interest', '1', '3', null);
INSERT INTO `depment_access_control` VALUES ('2', 'is_farming', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'is_for_credit', '1', '2', null);
INSERT INTO `depment_access_control` VALUES ('3', 'is_impawn', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'item_lean', '1', '2', null);
INSERT INTO `depment_access_control` VALUES ('2', 'leave_money', '1', '2', null);
INSERT INTO `depment_access_control` VALUES ('1', 'loan_date', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'loan_money', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'loan_rate', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'main_bank_id', '1', '2', null);
INSERT INTO `depment_access_control` VALUES ('1', 'org_id', '0', '0', null);
INSERT INTO `depment_access_control` VALUES ('2', 'org_id', '0', '0', null);
INSERT INTO `depment_access_control` VALUES ('4', 'org_id', '0', '0', null);
INSERT INTO `depment_access_control` VALUES ('3', 'orig_id', '0', '0', null);
INSERT INTO `depment_access_control` VALUES ('1', 'out_bail_money', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('2', 'pledge_type', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('3', 'pledge_worth', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('1', 'principal', '1', '3', null);
INSERT INTO `depment_access_control` VALUES ('2', 'proj_end_date', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('1', 'proj_id', '0', '0', null);
INSERT INTO `depment_access_control` VALUES ('2', 'proj_id', '0', '0', null);
INSERT INTO `depment_access_control` VALUES ('3', 'proj_id', '0', '0', null);
INSERT INTO `depment_access_control` VALUES ('4', 'proj_id', '0', '0', null);
INSERT INTO `depment_access_control` VALUES ('3', 'proj_status', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('1', 'punish_money', '1', '3', null);
INSERT INTO `depment_access_control` VALUES ('2', 'remark', '1', '2', null);
INSERT INTO `depment_access_control` VALUES ('1', 'repay_date', '1', '3', null);
INSERT INTO `depment_access_control` VALUES ('1', 'repay_type', '1', '1', null);
INSERT INTO `depment_access_control` VALUES ('3', 'replace_date', '1', '5', null);
INSERT INTO `depment_access_control` VALUES ('3', 'replace_money', '1', '5', null);
INSERT INTO `depment_access_control` VALUES ('4', 'replevy_date', '1', '6', null);
INSERT INTO `depment_access_control` VALUES ('4', 'replevy_money', '1', '6', null);
INSERT INTO `depment_access_control` VALUES ('4', 'replevy_type', '1', '6', null);
INSERT INTO `depment_access_control` VALUES ('2', 'single_money_limit', '1', '2', null);
