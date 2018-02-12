/*
Navicat MySQL Data Transfer

Source Server         : myDB
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : middle

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-02-11 21:37:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for business_data_info
-- ----------------------------
DROP TABLE IF EXISTS `business_data_info`;
CREATE TABLE `business_data_info` (
  `org_id` varchar(40) NOT NULL COMMENT '机构编码',
  `proj_id` varchar(40) NOT NULL COMMENT '项目编码',
  `client_type` varchar(10) NOT NULL COMMENT '客户类型：1 企业客户  2 个人客户',
  `client_id` varchar(40) NOT NULL COMMENT '客户编号',
  `client_name` varchar(180) NOT NULL COMMENT '客户名称',
  `id_card_type` varchar(10) NOT NULL COMMENT '证件类型：1 军官证 2 护照 3 身份证 4 其它 ',
  `id_card` varchar(40) DEFAULT NULL COMMENT '证件编码（企业填法人代表）',
  `calling_first` varchar(40) DEFAULT NULL COMMENT '所属行业编号（一级）',
  `calling_second` varchar(40) DEFAULT NULL COMMENT '所属行业编号（二级）',
  `area_first` varchar(40) NOT NULL COMMENT '所属地区编号（一级）',
  `area_second` varchar(40) NOT NULL COMMENT '所属地区编号（二级）',
  `area_third` varchar(40) NOT NULL COMMENT '所属地区编号（三级）',
  `company_scale` varchar(10) DEFAULT NULL COMMENT '客户规模编码：1 大型企业 2 中型企业 3 小型企业 4 微型企业',
  `is_farming` varchar(10) NOT NULL COMMENT '是否涉农：1 是 2 否',
  `contract_money` decimal(14,2) NOT NULL COMMENT '合同金额',
  `loan_money` decimal(14,2) DEFAULT NULL COMMENT '已放款金额',
  `loan_rate` decimal(14,2) DEFAULT NULL COMMENT '贷款年利率（%），传入数字',
  `assure_rate` decimal(14,2) DEFAULT NULL COMMENT '担保综合费率（%），传入数字',
  `loan_date` date DEFAULT NULL COMMENT '放款日期,实际放款日期',
  `contract_end_date` date DEFAULT NULL COMMENT '合同截止日期，合同登记时的结束时间',
  `repay_type` varchar(10) DEFAULT NULL COMMENT '还款方式（指客户与银行之间的还款方式）：1、按月等额本金 2 按月等额本息 3 按季等额本金 4 按季等额本息 5 按月付息一次还本 6 到期一次性还本付息 7 不规则 8 其它',
  `pledge_type` varchar(10) DEFAULT NULL COMMENT '反担保措施（如存在多项请加，分隔符）：9 抵押 4 质押 5 保证 10 信用 11 其它 ',
  `approve_option` varchar(900) DEFAULT NULL COMMENT '反担保备注',
  `bank_credit_primary_id` varchar(40) DEFAULT NULL COMMENT '银行授信记录标示ID（32位UUID）',
  `co_bank_id` varchar(40) DEFAULT NULL COMMENT '合作银行ID（32位UUID）',
  `proj_status` varchar(40) NOT NULL COMMENT '项目状态：60 保后监管 80 项目代偿 85 项目追偿 90 项目解除',
  `assure_person` varchar(900) DEFAULT NULL COMMENT '担保权人',
  `pledge_worth` decimal(14,2) DEFAULT NULL COMMENT '反担保物价值',
  `is_impawn` varchar(10) DEFAULT NULL COMMENT '存单质押',
  `accept_date` date DEFAULT NULL COMMENT '受理时间',
  `contract_id` varchar(40) DEFAULT NULL COMMENT '合同编码',
  `client_bail_money` decimal(14,2) DEFAULT NULL COMMENT '客户存入保证金',
  `out_bail_money` decimal(14,2) DEFAULT NULL COMMENT '存出保证金',
  `capital_belong` varchar(10) DEFAULT NULL COMMENT '资本属性:1 国有控股 2 民营控股 3 外资控股',
  `proj_end_date` date DEFAULT NULL COMMENT '项目结束时间（实际解除时间）',
  `batch_date` varchar(40) NOT NULL COMMENT '批次（为当前传输日期，格式为yyyyMMdd）',
  `business_type` varchar(40) NOT NULL COMMENT '业务类型：',
  `id` bigint(40) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `initial_balance` decimal(14,0) DEFAULT NULL COMMENT '期初余额',
  `first_loan_date` date DEFAULT NULL COMMENT '首次放款时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
