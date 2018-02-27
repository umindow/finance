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
   `dep_id` VARCHAR (200) DEFAULT null COMMENT '部门ID',
  PRIMARY KEY (`id`),
  KEY `login_index` (`user_name`,`password`) COMMENT '组合登录索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
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
-- ----------------------------
--  Table structure for `finance_schedule_status`
-- ----------------------------
DROP TABLE IF EXISTS `finance_schedule_status`;
CREATE TABLE `finance_schedule_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dup_key` varchar(80) COLLATE utf8_bin NOT NULL COMMENT '唯一key，',
  `schedule_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '任务名称',
  `schedule_status` int(11) DEFAULT NULL COMMENT '任务状态 1:初始化 2:失败 3:完成',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_dup_key` (`dup_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ----------------------------
-- Table structure for bank_credit_info
-- ----------------------------
DROP TABLE IF EXISTS `finance_bank_credit`;
CREATE TABLE `finance_bank_credit` (
  `primary_id` varchar(40)  COMMENT '银行授信记录标示ID',
  `org_id` varchar(40)  COMMENT '机构编号（金融办分配的公司编号）,默认为：渝061001L',
  `bank_id` varchar(40)  COMMENT '银行编号',
  `main_bank_id` varchar(40)  COMMENT '主办行编码',
  `credit_type_id` varchar(40)  COMMENT '授信类型：1 综合授信 2 单笔单议',
  `credit_money` decimal(14,2)  COMMENT '授信额度',
  `leave_money` decimal(14,2) DEFAULT NULL COMMENT '授信余额',
  `blowup_mulpitle` decimal(14,2) DEFAULT NULL COMMENT '放大倍数',
  `bail_money` decimal(14,2) DEFAULT NULL COMMENT '初始保证金额',
  `bail_scale` decimal(14,0) DEFAULT NULL COMMENT '保证金比例（%）',
  `credit_start_date` date DEFAULT NULL COMMENT '授信开始日期',
  `credit_end_date` date DEFAULT NULL COMMENT '授信结束日期',
  `single_money_limit` decimal(14,2) DEFAULT NULL COMMENT '单笔限额',
  `is_for_credit` varchar(10) DEFAULT NULL COMMENT '是否循环授信:1 是 2 否',
  `credit_status` varchar(10) DEFAULT NULL COMMENT '状态：1 使用 2 解除',
  `item_lean` varchar(900) DEFAULT NULL COMMENT '项目偏好',
  `remark` varchar(900) DEFAULT NULL COMMENT '备注',
  `batch_date` varchar(40) DEFAULT NULL COMMENT '批次（当前传输日期，格式yyyyMMdd）',
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `id` bigint(40) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=300 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for business_data_info
-- ----------------------------
DROP TABLE IF EXISTS `finance_business_data_info`;
CREATE TABLE `finance_business_data_info` (
  `org_id` varchar(40) NOT NULL COMMENT '机构编码',
  `proj_id` varchar(40) NOT NULL COMMENT '项目编码',
  `client_type` varchar(10) NOT NULL COMMENT '客户类型：1 企业客户  2 个人客户',
  `client_id` varchar(40) NOT NULL COMMENT '客户编号',
  `client_name` varchar(180) NOT NULL COMMENT '客户名称',
  `id_card_type` varchar(10) NOT NULL COMMENT '证件类型：1 军官证 2 护照 3 身份证 4 其它 ',
  `id_card` varchar(40) NOT NULL COMMENT '证件编码（企业填法人代表）',
  `calling_first` varchar(40) NOT NULL COMMENT '所属行业编号（一级）',
  `calling_second` varchar(40) NOT NULL COMMENT '所属行业编号（二级）',
  `area_first` varchar(40) NOT NULL COMMENT '所属地区编号（一级）',
  `area_second` varchar(40) NOT NULL COMMENT '所属地区编号（二级）',
  `area_third` varchar(40) NOT NULL COMMENT '所属地区编号（三级）',
  `company_scale` varchar(10) NOT NULL COMMENT '客户规模编码：1 大型企业 2 中型企业 3 小型企业 4 微型企业',
  `is_farming` varchar(10) NOT NULL COMMENT '是否涉农：1 是 2 否',
  `business_type` varchar(40) NOT NULL COMMENT '业务类型：',
  `contract_money` decimal(14,2) NOT NULL COMMENT '合同金额',
  `loan_money` decimal(14,2) NOT NULL COMMENT '已放款金额',
  `loan_rate` decimal(14,2) NOT NULL COMMENT '贷款年利率（%），传入数字',
  `assure_rate` decimal(14,2) NOT NULL COMMENT '担保综合费率（%），传入数字',
  `loan_date` date NOT NULL COMMENT '放款日期,实际放款日期',
  `contract_end_date` date NOT NULL COMMENT '合同截止日期，合同登记时的结束时间',
  `repay_type_id` varchar(10) NOT NULL COMMENT '还款方式（指客户与银行之间的还款方式）：1、按月等额本金 2 按月等额本息 3 按季等额本金 4 按季等额本息 5 按月付息一次还本 6 到期一次性还本付息 7 不规则 8 其它',
  `pledge_type_id` varchar(10) NOT NULL COMMENT '反担保措施（如存在多项请加，分隔符）：9 抵押 4 质押 5 保证 10 信用 11 其它 ',
  `approve_option` varchar(900) DEFAULT NULL COMMENT '反担保备注',
  `bank_credit_primary_id` varchar(40) DEFAULT NULL COMMENT '银行授信记录标示ID（32位UUID）',
  `co_bank_id` varchar(40) DEFAULT NULL COMMENT '合作银行ID（32位UUID）',
  `proj_status` varchar(40) NOT NULL COMMENT '项目状态：60 保后监管 80 项目代偿 85 项目追偿 90 项目解除',
  `assure_person` varchar(900) DEFAULT NULL COMMENT '担保权人',
  `pledge_worth` decimal(14,2) DEFAULT NULL COMMENT '反担保物价值',
  `is_impawn` varchar(10) DEFAULT NULL COMMENT '存单质押',
  `accept_date` date NOT NULL COMMENT '受理时间',
  `contract_id` varchar(40) NOT NULL COMMENT '合同编号',
  `client_bail_money` decimal(14,2) DEFAULT NULL COMMENT '客户存入保证金',
  `out_bail_money` decimal(14,2) DEFAULT NULL COMMENT '存出保证金',
  `capital_belong` varchar(10) NOT NULL COMMENT '资本属性:1 国有控股 2 民营控股 3 外资控股',
  `proj_end_date` date DEFAULT NULL COMMENT '项目结束时间（实际解除时间）',
    `initial_balance` decimal(14,0) DEFAULT NULL COMMENT '期初余额，页面不展示',
  `first_loan_date` date DEFAULT NULL COMMENT '首次放款时间，页面不展示',
  `batch_date` varchar(40) NOT NULL COMMENT '批次（为当前传输日期，格式为yyyyMMdd）',
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `id` bigint(40) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;
-- ----------------------------
-- Table structure for repayment_info
-- ----------------------------
DROP TABLE IF EXISTS `finance_repayment_info`;
CREATE TABLE `finance_repayment_info` (
  `org_id` varchar(40) NOT NULL COMMENT '机构编码，默认：渝061001L',
  `proj_id` varchar(40) NOT NULL COMMENT '项目编码',
  `contract_id` varchar(40) NOT NULL COMMENT '合同编号',
  `repay_date` date NOT NULL COMMENT '实际还款日期',
  `principal` decimal(14,2) NOT NULL COMMENT '实际归还本金',
  `interest` decimal(14,2) DEFAULT NULL COMMENT '实际归还利息',
  `punish_money` decimal(14,2) DEFAULT NULL COMMENT '收取罚息',
  `batch_date` varchar(40) NOT NULL COMMENT '批次（当前传输日期，格式：yyyyMMdd）',
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `id` bigint(40) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `finance_compensatory_info`;
CREATE TABLE `finance_compensatory_info` (
  `org_id` varchar(40) NOT NULL COMMENT '机构编码，默认：渝061001L',
  `contract_id` varchar(40) DEFAULT NULL COMMENT '合同编号',
  `replace_date` date DEFAULT NULL COMMENT '代偿日期',
  `replace_money` decimal(14,2) DEFAULT NULL COMMENT '代偿金额',
  `batch_date` varchar(40) NOT NULL COMMENT '批次，yyyyMMdd',
  `proj_id` varchar(40) NOT NULL COMMENT '项目编码',
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `id` bigint(40) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=400 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `finance_fee_and_refund_info`;
CREATE TABLE `finance_fee_and_refund_info` (
  `org_id` varchar(40) NOT NULL COMMENT '机构编码,有默认值:渝061001L',
  `proj_id` varchar(40) NOT NULL COMMENT '项目编码',
  `contract_id` varchar(90) DEFAULT NULL,
  `charge_way` varchar(10) DEFAULT NULL COMMENT '收退费标示：1 收费 2 退费',
  `charge_type` varchar(10) DEFAULT NULL COMMENT '费用类型编码：1 担保费 2 保证金 3 其它',
  `charge_time` date DEFAULT NULL COMMENT '实际缴费时间',
  `charge_money` decimal(14,2) DEFAULT NULL COMMENT '实际缴费金额',
  `batch_date` varchar(40) NOT NULL COMMENT '批次（当前传输日期，格式yyyyMMdd）',
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `id` bigint(40) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=500 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `finance_recourse_info`;
CREATE TABLE `finance_recourse_info` (
  `org_id` varchar(40) NOT NULL,
  `proj_id` varchar(40) NOT NULL,
  `contract_id` varchar(40) DEFAULT NULL COMMENT '合同编号',
  `replevy_type` varchar(10) DEFAULT NULL COMMENT '追偿类型：1 普通追偿 2 挽回损失',
  `replevy_date` date DEFAULT NULL COMMENT '追偿日期',
  `replevy_money` decimal(14,2) DEFAULT NULL COMMENT '追偿金额',
  `batch_date` varchar(40) NOT NULL COMMENT '批次，yyyyMMdd',
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `id` bigint(40) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=600 DEFAULT CHARSET=utf8;


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
