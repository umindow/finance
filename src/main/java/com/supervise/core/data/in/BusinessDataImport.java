package com.supervise.core.data.in;

import com.google.common.collect.Lists;
import com.supervise.cache.FiedRoleCache;
import com.supervise.common.DateUtils;
import com.supervise.config.role.DataType;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.middleDao.BusinessDataDao;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by xishui.hb on 2018/2/12 下午3:59.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Service
public class BusinessDataImport extends AbstractDataImport {
    @Autowired
    private BusinessDataDao businessDataDao;

    private List<BusinessDataEntity> businessDataEntitys = Lists.newArrayList();

    @Override
    public void resolve(Workbook wb) throws Exception {
        Sheet sheet = wb.getSheetAt(0);//获取第一个表格
        if (null == sheet) {
            return;
        }
        BusinessDataEntity businessDataEntity = null;
        Map<String,FiedRoleCache.DepRoleRef> filedRoles = FiedRoleCache.mapDepRoleRefs(DataType.SUPERVISE_BANK_DATA.getDataLevel());
        for (Row row : sheet) {
            if (null == row) {
                continue;
            }
            if (row.getRowNum() == 0) {
                continue;
            }
            if (org.apache.commons.lang3.StringUtils.isBlank((String)getCellValue(row.getCell(0)))) {
                break;
            }
            businessDataEntity = new BusinessDataEntity();
            for (Cell cell : row) {
                if (cell == null) {
                    continue;
                }
                switch (cell.getColumnIndex()) {
                    case 0://主键ID号
                        if(Cell.CELL_TYPE_NUMERIC==cell.getCellType()){
                            businessDataEntity.setId((Long) getCellValue(cell));
                        };
                        break;
                    case 1://机构编码
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("org_id"))) {
                            businessDataEntity.setOrgId((String) getCellValue(cell));
                        }
                        break;
                    case 2://项目编码
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("proj_id"))) {
                            businessDataEntity.setProjId((String) getCellValue(cell));
                        }
                        break;
                    case 3://客户类型
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("client_type"))) {
                            businessDataEntity.setClientType((String) getCellValue(cell));
                        }
                        break;
                    case 4://客户编码
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("client_id"))) {
                            businessDataEntity.setClientId((String) getCellValue(cell));
                        }
                        break;
                    case 5://客户名称
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("client_name"))) {
                            businessDataEntity.setClientName((String) getCellValue(cell));
                        }
                        break;
                    case 6://证件类型
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("id_card_type"))) {
                            businessDataEntity.setIDCardType((String) getCellValue(cell));
                        }
                        break;
                    case 7://证件编码
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("id_card"))) {
                            businessDataEntity.setIDCard((String) getCellValue(cell));
                        }
                        break;
                    case 8://所属行业编号（一级）
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("calling_first"))) {
                            businessDataEntity.setCallingFirst((String) getCellValue(cell));
                        }
                        break;
                    case 9://所属行业编号（二级）
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("calling_second"))) {
                            businessDataEntity.setCallingSecond((String) getCellValue(cell));
                        }
                        break;
                    case 10://所属地区编号（一级）
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("area_first"))) {
                            businessDataEntity.setAreaFirst((String) getCellValue(cell));
                        }
                        break;
                    case 11://所属地区编号（二级）
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("area_second"))) {
                            businessDataEntity.setAreaSecond((String) getCellValue(cell));
                        }
                        break;
                    case 12://所属地区编号（三级）
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("area_third"))) {
                            businessDataEntity.setAreaThird((String) getCellValue(cell));
                        }
                        break;
                    case 13://客户规模编码
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("company_scale"))) {
                            businessDataEntity.setCompanyScale((String) getCellValue(cell));
                        }
                        break;
                    case 14://是否涉农
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("is_farming"))) {
                            businessDataEntity.setIsFarming((String) getCellValue(cell));
                        }
                        break;
                    case 15://业务类型
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("business_type"))) {
                            businessDataEntity.setBusinessType((String) getCellValue(cell));
                        }
                        break;
                    case 16://合同金额
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("contract_money"))) {
                            businessDataEntity.setContractMoney(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 17://已放款金额
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("loan_money"))) {
                            businessDataEntity.setLoanMoney(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 18://贷款年利率
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("loan_rate"))) {
                            businessDataEntity.setLoanRate(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 19://担保综合费率
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("assure_rate"))) {
                            businessDataEntity.setAssureRate(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 20://放款日期
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("loan_date"))) {
                            businessDataEntity.setLoanDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        }
                        break;
                    case 21://合同截止日期
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("contract_end_date"))) {
                            businessDataEntity.setContractEndDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        }
                        break;
                    case 22://还款方式
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("repay_type_id"))) {
                            businessDataEntity.setRepayType((String) getCellValue(cell));
                        }
                        break;
                    case 23://反担保措施
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("pledge_type_id"))) {
                            businessDataEntity.setPledgeType((String) getCellValue(cell));
                        }
                        break;
                    case 24://反担保备注
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("approve_option"))) {
                            businessDataEntity.setApproveOption((String) getCellValue(cell));
                        }
                        break;
                    case 25://银行授信记录标示ID
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("bank_credit_primary_id"))) {
                            businessDataEntity.setBankCreditPrimaryId((String) getCellValue(cell));
                        }
                        break;
                    case 26://合作银行ID
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("co_bank_id"))) {
                            businessDataEntity.setCoBankId((String) getCellValue(cell));
                        }
                        break;
                    case 27://项目状态
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("proj_status"))) {
                            businessDataEntity.setProjSatus((String) getCellValue(cell));
                        }
                        break;
                    case 28://担保权人
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("assure_person"))) {
                            businessDataEntity.setAssurePerson((String) getCellValue(cell));
                        }
                        break;
                    case 29://反担保物价值
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("pledge_worth"))) {
                            businessDataEntity.setPledgeWorth(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 30://存单质押
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("is_impawn"))) {
                            businessDataEntity.setIsImpawn((String) getCellValue(cell));
                        }
                        break;
                    case 31://受理时间
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("accept_date"))) {
                            businessDataEntity.setAcceptDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        }
                        break;
                    case 32://合同编码
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("contract_id"))) {
                            businessDataEntity.setContractId((String) getCellValue(cell));
                        }
                        break;
                    case 33://客户存入保证金
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("client_bail_money"))) {
                            businessDataEntity.setClientBailMoney(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 34://存出保证金
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("out_bail_money"))) {
                            businessDataEntity.setOutBailMoney(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 35://资本属性
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("capital_belong"))) {
                            businessDataEntity.setCapitalBelong((String) getCellValue(cell));
                        }
                        break;
                    case 36://项目结束时间
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("proj_end_date"))) {
                            businessDataEntity.setProjEndDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        }
                        break;
                    case 37://批次
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("batch_date"))) {
                            businessDataEntity.setBatchDate((String) getCellValue(cell));
                        }
                        break;
                    default:
                        break;
                }
            }
            businessDataEntitys.add(businessDataEntity);
        }
    }

    @Override
    public void save() throws Exception {
        if (CollectionUtils.isEmpty(businessDataEntitys)) {
            return;
        }
        for (final BusinessDataEntity businessDataEntity : businessDataEntitys) {
            Long id  = businessDataEntity.getId();
            if(id>0){
                //根据ID查询数据库
                BusinessDataEntity ret = this.businessDataDao.queryBusinessDataByKey(id);
                //如果能查询到记录，则表示更新数据
                if(null!=ret){
                    BeanUtils.copyProperties(ret,businessDataEntity);//合并
                    this.businessDataDao.updateBankCredit(businessDataEntity);
                }else {
                    //否则作为新的数据保存到数据库
                    this.businessDataDao.insertBusinessDataToMiddleDB(businessDataEntity);
                }
            }else{
                //ID无效，作为新的数据保存到数据库
                this.businessDataDao.insertBusinessDataToMiddleDB(businessDataEntity);
            }
        }
    }
}
