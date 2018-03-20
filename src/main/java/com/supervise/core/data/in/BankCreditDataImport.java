package com.supervise.core.data.in;

import com.google.common.collect.Lists;
import com.supervise.cache.FiedRoleCache;
import com.supervise.common.CellUtil;
import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.config.role.DataType;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.middleDao.BankCreditDao;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
public class BankCreditDataImport extends AbstractDataImport {
    @Autowired
    private BankCreditDao bankCreditDao;

    private List<BankCreditEntity> bankCreditEntities = Lists.newArrayList();

    @Override
    public void resolve(Workbook wb) throws Exception {
        Sheet sheet = wb.getSheetAt(0);//获取第一个表格
        if (null == sheet) {
            return;
        }
        BankCreditEntity bankCreditEntity = null;
        Map<String,FiedRoleCache.DepRoleRef> filedRoles = FiedRoleCache.mapDepRoleRefs(DataType.SUPERVISE_BANK_DATA.getDataLevel());
        for (Row row : sheet) {
            if (null == row) {
                continue;
            }
            if (row.getRowNum() == 0) {
                continue;
            }
            if (null==row.getCell(1)) {
                break;
            }
            bankCreditEntity = new BankCreditEntity();
            for (Cell cell : row) {
                if (cell == null) {
                    continue;
                }
                String value = getValue(cell);
                switch (cell.getColumnIndex()) {
                    case 0://主键ID号
                            value = CellUtil.trimValue(value);
                            bankCreditEntity.setId(Long.parseLong(value));
                        break;
                    case 1://银行授信记录ID
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("primary_id"))) {
                            value = CellUtil.trimValue(value);
                            bankCreditEntity.setPrimaryId(value);
                        }
                        break;
                    case 2://机构编码
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("org_id"))) {
                            value = CellUtil.trimValue(value);
                            bankCreditEntity.setOrgId(value);
                        }
                        break;
                    case 3://银行编号
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("bank_id"))) {
                            value = CellUtil.trimValue(value);
                            bankCreditEntity.setBankId(value);
                        }
                        break;
                    case 4://主办行编码
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("main_bank_id"))) {
                            value = CellUtil.trimValue(value);
                            bankCreditEntity.setMainBankId(value);
                        }
                        break;
                    case 5://授信类型
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("credit_type_id"))) {
                            value = CellUtil.trimValue(value);
                            bankCreditEntity.setCreditTypeId(value);
                        }
                        break;
                    case 6://授信额度
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("credit_money"))) {
                            Double money = CellUtil.transfValuetoDouble(value);
                            bankCreditEntity.setCreditMoney(new BigDecimal(money));
                        }
                        break;
                    case 7://授信余额
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("leave_money"))) {
                            Double money = CellUtil.transfValuetoDouble(value);
                            bankCreditEntity.setLeaveMoney(new BigDecimal(money));
                        }
                        break;
                    case 8://放大倍数
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("blowup_mulpitle"))) {
                            Double money = CellUtil.transfValuetoDouble(value);
                            bankCreditEntity.setLeaveMoney(new BigDecimal(money));
                        }
                        break;
                    case 9://初始保证金额
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("bail_money"))) {
                            Double money = CellUtil.transfValuetoDouble(value);
                            bankCreditEntity.setLeaveMoney(new BigDecimal(money));
                        }
                        break;
                    case 10://保证金比例（%）
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("bail_scale"))) {
                            Double money = CellUtil.transfValuetoDouble(value);
                            bankCreditEntity.setBailScale(new BigDecimal(money));
                        }
                        break;
                    case 11://授信开始日期
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("credit_start_date"))) {
                            bankCreditEntity.setCreditStartDate(DateUtils.parseStringDate(value, Constants.YYYY_MM_DD));
                        }
                        break;
                    case 12://授信结束日期
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("credit_end_date"))) {
                            bankCreditEntity.setCreditEndDate(DateUtils.parseStringDate(value, Constants.YYYY_MM_DD));
                        }
                        break;
                    case 13://单笔限额
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("single_money_limit"))) {
                            Double money = CellUtil.transfValuetoDouble(value);
                            bankCreditEntity.setSingleMoneyLimit(new BigDecimal(money));
                        }
                        break;
                    case 14://是否循环授信:1 是 2 否
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("is_for_credit"))) {
                            value = CellUtil.trimValue(value);
                            bankCreditEntity.setIsForCredit(value);
                        }
                        break;
                    case 15://状态：1 使用 2 解除
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("credit_status"))) {
                            value = CellUtil.trimValue(value);
                            bankCreditEntity.setCreditStatus(value);
                        }
                        break;
                    case 16://项目偏好
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("item_lean"))) {
                            value = CellUtil.trimValue(value);
                            bankCreditEntity.setItemLean(value);
                        }
                        break;
                    case 17://备注
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("remark"))) {
                            value = CellUtil.trimValue(value);
                            bankCreditEntity.setRemark(value);
                        }
                        break;
                    case 18://批次
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("batch_date"))) {
                            String batchDate = new SimpleDateFormat(Constants.YYYY_MM_DD).format(new Date());
                            bankCreditEntity.setBatchDate(batchDate);
                        }
                        break;
                    default:
                        break;
                }
            }
            bankCreditEntities.add(bankCreditEntity);
        }
    }

    @Override
    public void save() throws Exception {
        if (CollectionUtils.isEmpty(bankCreditEntities)) {
            return;
        }

        /**
         * 先删除当天批次的所有记录，
         */
//        String batchDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        this.bankCreditDao.deleteBankCreditByBatchDate(batchDate);
        //然后保存导入的EXCEL记录
        for (final BankCreditEntity bankCreditEntity : bankCreditEntities) {
            //bankCreditEntity.setId(0L);//重新设置主键，避免主键重复
            //String batchdate = bankCreditEntity.getBatchDate();
            long id = bankCreditEntity.getId();
            if(0!=id){
                BankCreditEntity  exBankCreditEntity = this.bankCreditDao.queryBankCreditByKey(id);
                if(null!=exBankCreditEntity){
                    //更新
                    this.bankCreditDao.updateBankCredit(bankCreditEntity);
                }
                else{
                    //新增
                    bankCreditEntity.setId(0L);
                    this.bankCreditDao.insertBankCreditToMiddleDB(bankCreditEntity);
                }
            }else{
                //新增
                bankCreditEntity.setId(0L);
                this.bankCreditDao.insertBankCreditToMiddleDB(bankCreditEntity);
            }



        }
        bankCreditEntities.clear();




        /**
         * 增加判断逻辑
         * 1、如果主键ID号>=0，则根据主键ID作为查询条件，查询是否存在记录
         * 2、如果不存在记录，则直接保存
         * 3、如果存在记录，则更新该条记录
         * 4、否则保存该记录
         */


//        for (final BankCreditEntity bankCreditEntity : bankCreditEntities) {
//            //根据主键ID作为查询条件
//            Long id  = bankCreditEntity.getId();
//            if(id>0){
//                //根据ID查询数据库
//                BankCreditEntity ret = this.bankCreditDao.queryBankCreditByKey(id);
//                //如果能查询到记录，则表示更新数据
//                if(null!=ret){
//                    this.bankCreditDao.updateBankCredit(bankCreditEntity);
//                }else {
//                    //否则作为新的数据保存到数据库
//                    this.bankCreditDao.insertBankCreditToMiddleDB(bankCreditEntity);
//                }
//            }else{
//                //ID无效，作为新的数据保存到数据库
//                this.bankCreditDao.insertBankCreditToMiddleDB(bankCreditEntity);
//            }
//        }


    }

    private QueryCondition createQueryCondition(String orgid,String primary_id,String batchDate){
        QueryCondition queryCondition = new QueryCondition();
        //设置查询条件
        queryCondition.getColumnList().add("org_id");
        queryCondition.getColumnList().add("primary_id");
        queryCondition.getColumnList().add("batch_date");

        queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
        queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
        queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);

        queryCondition.getValueList().add(orgid);
        queryCondition.getValueList().add(primary_id);
        queryCondition.getValueList().add(batchDate);
        return queryCondition;
    }
}
