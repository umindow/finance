package com.supervise.core.data.in;

import com.google.common.collect.Lists;
import com.supervise.cache.FiedRoleCache;
import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.config.role.DataType;
import com.supervise.dao.mysql.entity.FeeAndRefundEntity;
import com.supervise.dao.mysql.middleDao.FeeAndRefundDao;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
public class FeeAndRefundDataImport extends AbstractDataImport {
    @Autowired
    private FeeAndRefundDao feeAndRefundDao;

    private List<FeeAndRefundEntity> feeAndRefundEntitys = Lists.newArrayList();

    @Override
    public void resolve(Workbook wb) throws Exception {
        Sheet sheet = wb.getSheetAt(0);//获取第一个表格
        if (null == sheet) {
            return;
        }
        FeeAndRefundEntity feeAndRefundEntity = null;
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
            feeAndRefundEntity = new FeeAndRefundEntity();
            feeAndRefundEntity.setSendStatus(Constants.DATA_READY_SEND);
            for (Cell cell : row) {
                if (cell == null) {
                    continue;
                }
                switch (cell.getColumnIndex()) {
                    case 0://主键ID号
                        if(Cell.CELL_TYPE_NUMERIC==cell.getCellType()){
                            feeAndRefundEntity.setId((Long) getCellValue(cell));
                        };
                        break;
                    case 1://机构编码
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("org_id"))) {
                            feeAndRefundEntity.setOrgId((String) getCellValue(cell));
                        }
                        break;
                    case 2://项目编码
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("proj_id"))) {
                            feeAndRefundEntity.setProjId((String) getCellValue(cell));
                        }
                        break;
                    case 3://合同编号
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("contract_id"))) {
                            feeAndRefundEntity.setContractId((String) getCellValue(cell));
                        }
                        break;
                    case 4://收退费标示
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("charge_may"))) {
                            feeAndRefundEntity.setChargeWay((String) getCellValue(cell));
                        }
                        break;
                    case 5://费用类型编码
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("charge_type"))) {
                            feeAndRefundEntity.setChargeType((String) getCellValue(cell));
                        }
                        break;
                    case 6://实际缴费时间
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("charge_time"))) {
                            feeAndRefundEntity.setChargeTime(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        }
                        break;
                    case 7://实际缴费金额
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("charge_money"))) {
                            feeAndRefundEntity.setChargeMoney(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 8:
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("batch_date"))) {
                            feeAndRefundEntity.setBatchDate((String) getCellValue(cell));
                        }
                        break;
                    default:
                        break;
                }
            }
            feeAndRefundEntitys.add(feeAndRefundEntity);
        }
    }

    @Override
    public void save() throws Exception {
        if (CollectionUtils.isEmpty(feeAndRefundEntitys)) {
            return;
        }
        /**
         * 增加判断逻辑
         * 1、先根据ORIGID PROJID charge_time batchdate作为查询条件，查询是否存在记录
         * 2、如果不存在记录，则直接保存
         * 3、如果存在记录，则更新该条记录
         */
        for (final FeeAndRefundEntity feeAndRefundEntity : feeAndRefundEntitys) {
            Long id  = feeAndRefundEntity.getId();
            if(id>0){
                //根据ID查询数据库
                FeeAndRefundEntity ret = this.feeAndRefundDao.queryFeeAndRefundByKey(id);
                //如果能查询到记录，则表示更新数据
                if(null!=ret){
                    this.feeAndRefundDao.updateFeeAndRefund(feeAndRefundEntity);
                }else {
                    //否则作为新的数据保存到数据库
                    this.feeAndRefundDao.insertFeeAndRefundToMiddleDB(feeAndRefundEntity);
                }
            }else{
                //ID无效，作为新的数据保存到数据库
                this.feeAndRefundDao.insertFeeAndRefundToMiddleDB(feeAndRefundEntity);
            }
        }
    }

        private QueryCondition createQueryCondition(String orgid,String projid,Date chargeTime,String batchDate){
            String chargetimeStr = DateUtils.formatDate(chargeTime, Constants.YYYY_MM_DD, Locale.ENGLISH);
            QueryCondition queryCondition = new QueryCondition();
            //设置查询条件
            queryCondition.getColumnList().add("org_id");
            queryCondition.getColumnList().add("proj_id");
            queryCondition.getColumnList().add("charge_time");
            queryCondition.getColumnList().add("batch_date");

            queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
            queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
            queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
            queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);

            queryCondition.getValueList().add(orgid);
            queryCondition.getValueList().add(projid);
            queryCondition.getValueList().add(chargetimeStr);
            queryCondition.getValueList().add(batchDate);
            return queryCondition;
    }

}
