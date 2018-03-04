package com.supervise.core.data.in;

import com.google.common.collect.Lists;
import com.supervise.cache.FiedRoleCache;
import com.supervise.common.DateUtils;
import com.supervise.config.role.DataType;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.mapper.BusinessDataMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
    private BusinessDataMapper businessDataMapper;

    private List<BusinessDataEntity> businessDataEntitys = Lists.newArrayList();

    @Override
    public void resolve(Workbook wb) throws Exception {
        Sheet sheet = wb.getSheetAt(0);//获取第一个表格
        if (null == sheet) {
            return;
        }
        BusinessDataEntity businessDataEntity = null;
        Map<String,FiedRoleCache.DepRoleRef> filedRoles = FiedRoleCache.mapDepRoleRefs(DataType.SUPERVISE_BANK_DATA.getDataLevel());
        int userDepId = Integer.valueOf(getUserEntity().getDepId());
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
                    case 0://机构编码
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("org_id"))) {
                            businessDataEntity.setOrgId((String) getCellValue(cell));
                        }
                        break;
                    case 1://项目编码
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("proj_id"))) {
                            businessDataEntity.setProjId((String) getCellValue(cell));
                        }
                        break;
                    case 2://客户类型
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("client_type"))) {
                            businessDataEntity.setClientType((String) getCellValue(cell));
                        }
                        break;
                    case 3://客户编码
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("client_id"))) {
                            businessDataEntity.setClientId((String) getCellValue(cell));
                        }
                        break;
                    case 4://客户名称
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("client_name"))) {
                            businessDataEntity.setClientName((String) getCellValue(cell));
                        }
                        break;
                    case 5://证件类型
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("id_card_type"))) {
                            businessDataEntity.setIDCardType((String) getCellValue(cell));
                        }
                        break;
                    case 6://证件编码
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("id_card"))) {
                            businessDataEntity.setIDCard((String) getCellValue(cell));
                        }
                        break;
                    case 7://所属行业编号（一级）
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("calling_first"))) {
                            businessDataEntity.setCallingFirst((String) getCellValue(cell));
                        }
                        break;
                    case 8://所属行业编号（二级）
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("calling_second"))) {
                            businessDataEntity.setCallingSecond((String) getCellValue(cell));
                        }
                        break;
                    case 9://所属地区编号（一级）
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("area_first"))) {
                            businessDataEntity.setAreaFirst((String) getCellValue(cell));
                        }
                        break;
                    case 10://所属地区编号（二级）
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("area_second"))) {
                            businessDataEntity.setAreaSecond((String) getCellValue(cell));
                        }
                        break;
                    case 11://所属地区编号（三级）
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("area_third"))) {
                            businessDataEntity.setAreaThird((String) getCellValue(cell));
                        }
                        break;
                    case 12://客户规模编码
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("company_scale"))) {
                            businessDataEntity.setCompanyScale((String) getCellValue(cell));
                        }
                        break;
                    case 13://是否涉农
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("is_farming"))) {
                            businessDataEntity.setIsFarming((String) getCellValue(cell));
                        }
                        break;
                    case 14://业务类型
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("business_type"))) {
                            businessDataEntity.setBusinessType((String) getCellValue(cell));
                        }
                        break;
                    case 15://合同金额
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("contract_money"))) {
                            businessDataEntity.setContractMoney(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 16://已放款金额
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("loan_money"))) {
                            businessDataEntity.setLoanMoney(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 17://贷款年利率
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("loan_rate"))) {
                            businessDataEntity.setLoanRate(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 18://担保综合费率
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("assure_rate"))) {
                            businessDataEntity.setAssureRate(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 19://放款日期
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("loan_date"))) {
                            businessDataEntity.setLoanDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        }
                        break;
                    case 20://合同截止日期
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("contract_end_date"))) {
                            businessDataEntity.setContractEndDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        }
                        break;
                    case 21://还款方式
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("repay_type"))) {
                            businessDataEntity.setRepayType((String) getCellValue(cell));
                        }
                        break;
                    case 22://反担保措施
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("pledge_type"))) {
                            businessDataEntity.setPledgeType((String) getCellValue(cell));
                        }
                        break;
                    case 23://反担保备注
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("approve_option"))) {
                            businessDataEntity.setApproveOption((String) getCellValue(cell));
                        }
                        break;
                    case 24://银行授信记录标示ID
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("bank_credit_primary_id"))) {
                            businessDataEntity.setBankCreditPrimaryId((String) getCellValue(cell));
                        }
                        break;
                    case 25://合作银行ID
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("co_bank_id"))) {
                            businessDataEntity.setCoBankId((String) getCellValue(cell));
                        }
                        break;
                    case 26://项目状态
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("proj_status"))) {
                            businessDataEntity.setProjSatus((String) getCellValue(cell));
                        }
                        break;
                    case 27://担保权人
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("assure_person"))) {
                            businessDataEntity.setAssurePerson((String) getCellValue(cell));
                        }
                        break;
                    case 28://反担保物价值
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("pledge_worth"))) {
                            businessDataEntity.setPledgeWorth(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 29://存单质押
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("is_impawn"))) {
                            businessDataEntity.setIsImpawn((String) getCellValue(cell));
                        }
                        break;
                    case 30://受理时间
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("accept_date"))) {
                            businessDataEntity.setAcceptDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        }
                        break;
                    case 31://合同编码
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("contract_id"))) {
                            businessDataEntity.setContractId((String) getCellValue(cell));
                        }
                        break;
                    case 32://客户存入保证金
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("client_bail_money"))) {
                            businessDataEntity.setClientBailMoney(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 33://存出保证金
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("out_bail_money"))) {
                            businessDataEntity.setOutBailMoney(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 34://资本属性
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("capital_belong"))) {
                            businessDataEntity.setCapitalBelong((String) getCellValue(cell));
                        }
                        break;
                    case 35://项目结束时间
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("proj_end_date"))) {
                            businessDataEntity.setProjEndDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        }
                        break;
                    case 36://批次
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("batch_date"))) {
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
        	businessDataMapper.insert(businessDataEntity);
        }
    }
}
