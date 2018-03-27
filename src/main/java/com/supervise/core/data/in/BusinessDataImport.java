package com.supervise.core.data.in;

import com.google.common.collect.Lists;
import com.supervise.cache.FiedRoleCache;
import com.supervise.common.CellUtil;
import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.config.role.DataType;
import com.supervise.config.role.DepType;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.entity.UserEntity;
import com.supervise.dao.mysql.middleDao.BusinessDataDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private final Logger logger = LoggerFactory.getLogger(BusinessDataImport.class);

    @Override
    public void resolve(Workbook wb) throws Exception {
        logger.info("BusinessDatafile start import!");
        Sheet sheet = wb.getSheetAt(0);//获取第一个表格
        if (null == sheet) {
            return;
        }
        BusinessDataEntity businessDataEntity = null;
        Map<String,FiedRoleCache.DepRoleRef> filedRoles = FiedRoleCache.mapDepRoleRefs(DataType.SUPERVISE_BIZ_DATA.getDataLevel());
        List<Integer> indexList = new ArrayList<Integer>();
        try{
            for (Row row : sheet) {
                int i = 0;
                if (null == row) {
                    continue;
                }
                if (row.getRowNum() == 0) {
                    //映射EXCEL列与entity的列号
                    initColumIndex(row,indexList,filedRoles);
                    continue;
                }
                if (null==row.getCell(1)) {
                    break;
                }
                businessDataEntity = new BusinessDataEntity();
                for (int j =0;j<indexList.size();j++ ) {
                //Iterator<Cell> cellItr =row.iterator();
               // while (cellItr.hasNext()) {
                    Cell cell = row.getCell(j);
                    String value="";
                    if (cell != null) {
                        value = getValue(cell);
                    }
                    int index = indexList.get(i).intValue();
                    switch (index) {
                        case 0://主键ID号
                            value = CellUtil.trimValue(value);
                            if(!org.springframework.util.StringUtils.isEmpty(value)){
                                businessDataEntity.setId(Long.parseLong(value));
                            }
                            break;
                        case 1://机构编码
//                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("orgId"))) {
//                                value = CellUtil.trimValue(value);
//                                businessDataEntity.setOrgId(value);
//                            }
                            businessDataEntity.setOrgId(Constants.ORG_ID);
                            break;
                        case 2://项目编码
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("projId"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setProjId(value);
                            }
                            break;
                        case 3://客户类型
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("clientType"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setClientType(value);
                            }
                            break;
                        case 4://客户编码
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("clientId"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setClientId(value);
                            }
                            break;
                        case 5://客户名称
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("clientName"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setClientName(value);
                            }
                            break;
                        case 6://证件类型
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("iDCardType"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setIDCardType(value);
                            }
                            break;
                        case 7://证件编码
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("iDCard"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setIDCard(value);
                            }
                            break;
                        case 8://所属行业编号（一级）
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("callingFirst"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setCallingFirst(value);
                            }
                            break;
                        case 9://所属行业编号（二级）
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("callingSecond"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setCallingSecond(value);
                            }
                            break;
                        case 10://所属地区编号（一级）
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("areaFirst"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setAreaFirst(value);
                            }
                            break;
                        case 11://所属地区编号（二级）
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("areaSecond"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setAreaSecond(value);
                            }
                            break;
                        case 12://所属地区编号（三级）
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("areaThird"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setAreaThird(value);
                            }
                            break;
                        case 13://客户规模编码
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("companyScale"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setCompanyScale(value);
                            }
                            break;
                        case 14://是否涉农
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("isFarming"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setIsFarming(value);
                            }
                            break;
                        case 15://业务类型
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("businessType"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setBusinessType(value);
                            }
                            break;
                        case 16://合同金额
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("contractMoney"))) {
                                Double money = CellUtil.transfValuetoDouble(value);
                                if(null!=money){
                                    businessDataEntity.setContractMoney(new BigDecimal(money));
                                }

                            }
                            break;
                        case 17://已放款金额
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("loanMoney"))) {
                                Double money = CellUtil.transfValuetoDouble(value);
                                if(null!=money){
                                    businessDataEntity.setLoanMoney(new BigDecimal(money));
                                }

                            }
                            break;
                        case 18://贷款年利率
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("loanRate"))) {
                                Double money = CellUtil.transfValuetoDouble(value);
                                if(null!=money){
                                    businessDataEntity.setLoanRate(new BigDecimal(money));
                                }
                            }
                            break;
                        case 19://担保综合费率
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("assureRate"))) {
                                Double money = CellUtil.transfValuetoDouble(value);
                                if(null!=money){
                                    businessDataEntity.setAssureRate(new BigDecimal(money));
                                }
                            }
                            break;
                        case 20://放款日期
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("loanDate"))) {
                                businessDataEntity.setLoanDate(DateUtils.parseStringDate(value, Constants.YYYY_MM_DD));
                            }
                            break;
                        case 21://合同截止日期
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("contractEndDate"))) {
                                businessDataEntity.setContractEndDate(DateUtils.parseStringDate(value, Constants.YYYY_MM_DD));
                            }
                            break;
                        case 22://还款方式
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("repayType"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setRepayType(value);
                            }
                            break;
                        case 23://反担保措施
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("pledgeType"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setPledgeType(value);
                            }
                            break;
                        case 24://反担保备注
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("approveOption"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setApproveOption(value);
                            }
                            break;
                        case 25://银行授信记录标示ID
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("bankCreditPrimaryId"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setBankCreditPrimaryId(value);
                            }
                            break;
                        case 26://合作银行ID
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("coBankId"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setCoBankId(value);
                            }
                            break;
                        case 27://项目状态
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("projSatus"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setProjSatus(value);
                            }
                            break;
                        case 28://担保权人
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("assurePerson"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setAssurePerson(value);
                            }
                            break;
                        case 29://反担保物价值
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("pledgeWorth"))) {
                                Double money = CellUtil.transfValuetoDouble(value);
                                if(null!=money){
                                    businessDataEntity.setPledgeWorth(new BigDecimal(money));
                                }
                            }
                            break;
                        case 30://存单质押
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("isImpawn"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setIsImpawn(value);
                            }
                            break;
                        case 31://受理时间
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("acceptDate"))) {
                                businessDataEntity.setAcceptDate(DateUtils.parseStringDate(value, Constants.YYYY_MM_DD));
                            }
                            break;
                        case 32://合同编码
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("contractId"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setContractId(value);
                            }
                            break;
                        case 33://客户存入保证金
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("clientBailMoney"))) {
                                Double money = CellUtil.transfValuetoDouble(value);
                                if(null!=money){
                                    businessDataEntity.setClientBailMoney(new BigDecimal(money));
                                }
                            }
                            break;
                        case 34://存出保证金
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("outBailMoney"))) {
                                Double money = CellUtil.transfValuetoDouble(value);
                                if(null!=money){
                                    businessDataEntity.setOutBailMoney(new BigDecimal(money));
                                }
                            }
                            break;
                        case 35://资本属性
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("capitalBelong"))) {
                                value = CellUtil.trimValue(value);
                                businessDataEntity.setCapitalBelong(value);
                            }
                            break;
                        case 36://项目结束时间
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("projEndDate"))) {
                                businessDataEntity.setProjEndDate(DateUtils.parseStringDate(value, Constants.YYYY_MM_DD));
                            }
                            break;
                        case 37://批次
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("batchDate"))) {
                                String batchDate = new SimpleDateFormat(Constants.YYYY_MM_DD).format(new Date());
                                businessDataEntity.setBatchDate(batchDate);
                            }
                            break;
                        default:
                            break;
                    }
                    i++;
                }
                businessDataEntitys.add(businessDataEntity);
            }
        }catch (Exception e){
            businessDataEntitys.clear();
            logger.info("BusinessDatafile import error!");
            e.printStackTrace();
        }
        logger.info("BusinessDatafile import end,size:"+businessDataEntitys.size());

    }

    @Override
    public synchronized void save() throws Exception {
        if (CollectionUtils.isEmpty(businessDataEntitys)) {
            return;
        }
        try {
            Map<String,FiedRoleCache.DepRoleRef> filedRoles = FiedRoleCache.mapDepRoleRefs(DataType.SUPERVISE_BIZ_DATA.getDataLevel());
            //查询当天批次的所有记录
            String batchDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            List<BusinessDataEntity> resList = this.businessDataDao.queryBusinessDataFormMiddleDB(batchDate);
            boolean isMatch = false;
            boolean isComdep = isCompdep(getUserEntity());
            //删除不在导入EXCEL中的记录
            for(final BusinessDataEntity businessExsit : resList){
//                Long exsitId = businessExsit.getId();
//                String exsitclientId = businessExsit.getClientId();
//                String exsitclientName = businessExsit.getClientName();
                String exsitorgid = businessExsit.getOrgId();
                String exsitprojid = businessExsit.getProjId();
                isMatch = false;
                for(final BusinessDataEntity businessDataEntity : businessDataEntitys){
                    String orgid = businessDataEntity.getOrgId();
                    String projid = businessDataEntity.getProjId();
                    String batch = StringUtils.isEmpty(businessDataEntity.getBatchDate()) ? batchDate:businessDataEntity.getBatchDate();
                    if(orgid.equalsIgnoreCase(exsitorgid)
                            &&exsitprojid.equalsIgnoreCase(projid)
                            &&batch.equalsIgnoreCase(batchDate)){
                        //如果找到，则更新记录
                        isMatch = true;
//                        Date tempd = businessExsit.getCreateDate();
//                        BeanUtils.copyProperties(businessExsit,businessDataEntity);//合并
                        updateBusinessDataEntity4Role(businessExsit, businessDataEntity, filedRoles, getUserEntity());
//                        if(!isComdep){
//                            businessDataEntity.setClientId(exsitclientId);
//                            businessDataEntity.setClientName(exsitclientName);
//                        }
//                        businessDataEntity.setId(exsitId);
//                        businessDataEntity.setCreateDate(tempd);
//                        businessDataEntity.setSendStatus(businessExsit.getSendStatus());
                        this.businessDataDao.updateBusinessData(businessExsit);
                        break;
                    }
                }
            }
            //将新的ID号或者没有ID号的记录保存到数据库
            for (final BusinessDataEntity businessDataEntity : businessDataEntitys) {
                String imorgId = businessDataEntity.getOrgId();
                String improjId = businessDataEntity.getProjId();
                String imbatchDate = StringUtils.isEmpty(businessDataEntity.getBatchDate()) ? batchDate:businessDataEntity.getBatchDate();
                isMatch = false;
                //遍历所有已有ID
                for(final BusinessDataEntity businessExsit : resList){
                    String exprojId = businessExsit.getProjId();
                    String exorgid = businessExsit.getOrgId();
                    String exbatchDate = businessExsit.getBatchDate();
                    if(exprojId.equalsIgnoreCase(improjId)
                            &&exbatchDate.equalsIgnoreCase(imbatchDate)
                            &&imorgId.equalsIgnoreCase(exorgid)){
                        //如果找到记录，则跳过
                        isMatch = true;
                        break;
                    }
                }
                //没有找到匹配的ID，作为新的数据保存到数据库
                if(!isMatch){
                    if(isComdep){
                        businessDataEntity.setId(0L);//重新设置主键，避免主键重复
                        if(StringUtils.isEmpty(businessDataEntity.getBatchDate())){
                            businessDataEntity.setBatchDate(batchDate);
                        }
                        businessDataEntity.setProjSatus(Constants.PROJ_STATUS_REINS);
                        this.businessDataDao.insertBusinessDataToMiddleDB(businessDataEntity);
                    }else{
                        logger.info("authority less ，deptID is ："+getUserEntity().getDepId());
                    }
                }

            }
        }catch (Exception e){
            logger.error("businessDatafile import updateDB error!");
            e.printStackTrace();
        }finally {
            businessDataEntitys.clear();
        }




    }

    /**
     * 构建新的业务信息对象，把权限范围内的字段删除
     * @param businessDataEntity
     * @param filedRoles
     * @param userEntity
     * @return
     */
    private void deleteBusinessDataEntity4Role(BusinessDataEntity businessDataEntity,Map<String,FiedRoleCache.DepRoleRef> filedRoles,UserEntity userEntity){
        //如果有该字段的权限，则清除
        //客户类型
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("client_type"))) {
            businessDataEntity.setClientType(Constants.NULLSTR);
        }
        //客户编码

        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("client_id"))) {
            businessDataEntity.setClientId(Constants.NULLSTR);
        }
        //客户名称
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("client_name"))) {
            businessDataEntity.setClientName(Constants.NULLSTR);
        }
        //证件类型
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("id_card_type"))) {
            businessDataEntity.setIDCardType(Constants.NULLSTR);
        }
        //证件编码
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("id_card"))) {
            businessDataEntity.setIDCard(Constants.NULLSTR);
        }
        //所属行业编号（一级）
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("calling_first"))) {
            businessDataEntity.setCallingFirst(Constants.NULLSTR);
        }
        //所属行业编号（二级）
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("calling_second"))) {
            businessDataEntity.setCallingSecond(Constants.NULLSTR);
        }
        //所属地区编号（一级）
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("area_first"))) {
            businessDataEntity.setAreaFirst(Constants.NULLSTR);
        }
        //所属地区编号（二级）
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("area_second"))) {
            businessDataEntity.setAreaSecond(Constants.NULLSTR);
        }
        //所属地区编号（三级）
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("area_third"))) {
            businessDataEntity.setAreaThird(Constants.NULLSTR);
        }
        //客户规模编码
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("company_scale"))) {
            businessDataEntity.setCompanyScale(Constants.NULLSTR);
        }
        //是否涉农
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("is_farming"))) {
            businessDataEntity.setIsFarming(Constants.NULLSTR);
        }
        //业务类型
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("business_type"))) {
            businessDataEntity.setBusinessType(Constants.NULLSTR);
        }
        //合同金额
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("contract_money"))) {
            businessDataEntity.setContractMoney(null);
        }
        //已放款金额
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("loan_money"))) {
            businessDataEntity.setLoanMoney(null);
        }
        //贷款年利率
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("loan_rate"))) {
            businessDataEntity.setLoanRate(null);
        }
        //担保综合费率
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("assure_rate"))) {
            businessDataEntity.setAssureRate(null);
        }
        //放款日期
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("loan_date"))) {
            businessDataEntity.setLoanDate(null);
        }
        //合同截止日期
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("contract_end_date"))) {
            businessDataEntity.setContractEndDate(null);
        }
        //还款方式
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("repay_type_id"))) {
            businessDataEntity.setRepayType(Constants.NULLSTR);
        }
        //反担保措施
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("pledge_type"))) {
            businessDataEntity.setPledgeType(Constants.NULLSTR);
        }
        //反担保备注
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("approve_option"))) {
            businessDataEntity.setApproveOption(Constants.NULLSTR);
        }
        //银行授信记录标示ID
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("bank_credit_primary_id"))) {
            businessDataEntity.setBankCreditPrimaryId(Constants.NULLSTR);
        }
        //合作银行ID
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("co_bank_id"))) {
            businessDataEntity.setCoBankId(Constants.NULLSTR);
        }
        //项目状态
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("proj_status"))) {
            businessDataEntity.setProjSatus(Constants.NULLSTR);
        }
        //担保权人
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("assure_person"))) {
            businessDataEntity.setAssurePerson(Constants.NULLSTR);
        }
        //反担保物价值
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("pledge_worth"))) {
            businessDataEntity.setPledgeWorth(null);
        }
        //存单质押
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("is_impawn"))) {
            businessDataEntity.setIsImpawn(Constants.NULLSTR);
        }
        //受理时间
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("accept_date"))) {
            businessDataEntity.setAcceptDate(null);
        }
        //合同编码
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("contract_id"))) {
            businessDataEntity.setContractId(Constants.NULLSTR);
        }
        //客户存入保证金
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("client_bail_money"))) {
            businessDataEntity.setClientBailMoney(null);
        }
        //存出保证金
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("out_bail_money"))) {
            businessDataEntity.setOutBailMoney(null);
        }
        //资本属性
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("capital_belong"))) {
            businessDataEntity.setCapitalBelong(Constants.NULLSTR);
        }
        //项目结束时间
        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("proj_end_date"))) {
            businessDataEntity.setProjEndDate(null);
        }
    }

    /**
     * 构建新的业务信息对象，把权限范围内的字段删除
     *
     * @param businessDataExist
     * @param businessDataUpdate
     * @param filedRoles
     * @param userEntity
     * @return BusinessDataEntity
     */
    private BusinessDataEntity updateBusinessDataEntity4Role(BusinessDataEntity businessDataExist, BusinessDataEntity businessDataUpdate, Map<String, FiedRoleCache.DepRoleRef> filedRoles, UserEntity userEntity) {
        //如果有该字段的权限，则清除
        //客户类型
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("clientType"))) {
            businessDataExist.setClientType(businessDataUpdate.getClientType());
        }
        //客户编码
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("clientId"))||isCompdep(userEntity)) {
            businessDataExist.setClientId(businessDataUpdate.getClientId());
        }
        //客户名称
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("clientName"))||isCompdep(userEntity)) {
            businessDataExist.setClientName(businessDataUpdate.getClientName());
        }
        //证件类型
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("iDCardType"))) {
            businessDataExist.setIDCardType(businessDataUpdate.getIDCardType());
        }
        //证件编码
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("iDCard"))) {
            businessDataExist.setIDCard(businessDataUpdate.getIDCard());
        }
        //所属行业编号（一级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("callingFirst"))) {
            businessDataExist.setCallingFirst(businessDataUpdate.getCallingFirst());
        }
        //所属行业编号（二级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("callingSecond"))) {
            businessDataExist.setCallingSecond(businessDataUpdate.getCallingSecond());
        }
        //所属地区编号（一级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("areaFirst"))) {
            businessDataExist.setAreaFirst(businessDataUpdate.getAreaFirst());
        }
        //所属地区编号（二级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("areaSecond"))) {
            businessDataExist.setAreaSecond(businessDataUpdate.getAreaSecond());
        }
        //所属地区编号（三级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("areaThird"))) {
            businessDataExist.setAreaThird(businessDataUpdate.getAreaThird());
        }
        //客户规模编码
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("companyScale"))) {
            businessDataExist.setCompanyScale(businessDataUpdate.getCompanyScale());
        }
        //是否涉农
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("isFarming"))) {
            businessDataExist.setIsFarming(businessDataUpdate.getIsFarming());
        }
        //业务类型
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("businessType"))) {
            businessDataExist.setBusinessType(businessDataUpdate.getBusinessType());
        }
        //合同金额
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("contractMoney"))) {
            businessDataExist.setContractMoney(businessDataUpdate.getContractMoney());
        }
        //已放款金额
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("loanMoney"))) {
            businessDataExist.setLoanMoney(businessDataUpdate.getLoanMoney());
        }
        //贷款年利率
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("loanRate"))) {
            businessDataExist.setLoanRate(businessDataUpdate.getLoanRate());
        }
        //担保综合费率
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("assureRate"))) {
            businessDataExist.setAssureRate(businessDataUpdate.getAssureRate());
        }
        //放款日期
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("loanDate"))) {
            businessDataExist.setLoanDate(businessDataUpdate.getLoanDate());
        }
        //合同截止日期
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("contractEndDate"))) {
            businessDataExist.setContractEndDate(businessDataUpdate.getContractEndDate());
        }
        //还款方式
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("repayType"))) {
            businessDataExist.setRepayType(businessDataUpdate.getRepayType());
        }
        //反担保措施
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("pledgeType"))) {
            businessDataExist.setPledgeType(businessDataUpdate.getPledgeType());
        }
        //反担保备注
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("approveOption"))) {
            businessDataExist.setApproveOption(businessDataUpdate.getApproveOption());
        }
        //银行授信记录标示ID
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("bankCreditPrimaryId"))) {
            businessDataExist.setBankCreditPrimaryId(businessDataUpdate.getBankCreditPrimaryId());
        }
        //合作银行ID
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("coBankId"))) {
            businessDataExist.setCoBankId(businessDataUpdate.getCoBankId());
        }
        //项目状态
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("projSatus"))) {
            businessDataExist.setProjSatus(businessDataUpdate.getProjSatus());
        }
        //担保权人
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("assurePerson"))) {
            businessDataExist.setAssurePerson(businessDataUpdate.getAssurePerson());
        }
        //反担保物价值
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("pledgeWorth"))) {
            businessDataExist.setPledgeWorth(businessDataUpdate.getPledgeWorth());
        }
        //存单质押
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("isImpawn"))) {
            businessDataExist.setIsImpawn(businessDataUpdate.getIsImpawn());
        }
        //受理时间
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("acceptDate"))) {
            businessDataExist.setAcceptDate(businessDataUpdate.getAcceptDate());
        }
        //合同编码
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("contractId"))) {
            businessDataExist.setContractId(businessDataUpdate.getContractId());
        }
        //客户存入保证金
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("clientBailMoney"))) {
            businessDataExist.setClientBailMoney(businessDataUpdate.getClientBailMoney());
        }
        //存出保证金
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("outBailMoney"))) {
            businessDataExist.setOutBailMoney(businessDataUpdate.getOutBailMoney());
        }
        //资本属性
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("capitalBelong"))) {
            businessDataExist.setCapitalBelong(businessDataUpdate.getCapitalBelong());
        }
        //项目结束时间
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("projEndDate"))) {
            businessDataExist.setProjEndDate(businessDataUpdate.getProjEndDate());
        }
        return businessDataExist;
    }


    /**
     * 判断是否综合运行部
     * @return
     */
    private boolean isCompdep(UserEntity userEntity){
        String depId = userEntity.getDepId();
        Long dep = -1L;
        if(!StringUtils.isEmpty(depId)){
            dep = Long.parseLong(depId);
        }
        if(DepType.COMPREHENSIVE_DEP.getDepId()==dep||20==userEntity.getLevel()){
            return true;
        }
        return false;
    }

    /**
     * 初始化列序号
     * @param row 第一行  列中文名称
     * @param indexList 各类所在的index
     */
    private void initColumIndex(Row row,List<Integer> indexList,Map<String,FiedRoleCache.DepRoleRef> filedRoles){
        int index = 0;
        for (Cell cell : row) {
            if (0==cell.getColumnIndex()) {
                indexList.add(index);
                continue;
            }
            String value = getValue(cell);
            for (final Map.Entry<String, FiedRoleCache.DepRoleRef> entry : filedRoles.entrySet()) {
                FiedRoleCache.DepRoleRef depRoleRef = entry.getValue();
                //根据中文名获取列序号
                if(depRoleRef.getFieldCnName().equalsIgnoreCase(value)) {
                    index = depRoleRef.getIndex();
                    indexList.add(index);
                    break;
                }
            }
        }
    }
}
