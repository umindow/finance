package com.supervise.core.data.in;

import com.google.common.collect.Lists;
import com.supervise.cache.FiedRoleCache;
import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.config.role.DataType;
import com.supervise.dao.mysql.entity.CompensatoryEntity;
import com.supervise.dao.mysql.middleDao.CompensatoryDao;
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
public class CompensatoryDataImport extends AbstractDataImport {
    @Autowired
    private CompensatoryDao compensatoryDao;

    private List<CompensatoryEntity> compensatoryEntitys = Lists.newArrayList();

    @Override
    public void resolve(Workbook wb) throws Exception {
        Sheet sheet = wb.getSheetAt(0);//获取第一个表格
        if (null == sheet) {
            return;
        }
        CompensatoryEntity compensatoryEntity = null;
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
            compensatoryEntity = new CompensatoryEntity();
            compensatoryEntity.setSendStatus(Constants.DATA_READY_SEND);
            for (Cell cell : row) {
                if (cell == null) {
                    continue;
                }
                switch (cell.getColumnIndex()) {
                    case 0://主键ID号
                        if(Cell.CELL_TYPE_NUMERIC==cell.getCellType()){
                            compensatoryEntity.setId((Long) getCellValue(cell));
                        };
                        break;
                    case 1://机构编码
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("org_id"))) {
                            compensatoryEntity.setOrgId((String) getCellValue(cell));
                        }
                        break;
                    case 2://项目编码
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("proj_id"))) {
                            compensatoryEntity.setProjId((String) getCellValue(cell));
                        }
                        break;
                    case 3://合同编号
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("contract_id"))) {
                            compensatoryEntity.setContractId((String) getCellValue(cell));
                        }
                        break;
                    case 4://代偿日期
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("replace_date"))) {
                            compensatoryEntity.setReplaceDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        }
                        break;
                    case 5://代偿金额
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("replace_money"))) {
                            compensatoryEntity.setReplaceMoney(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 6:
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("batch_date"))) {
                            compensatoryEntity.setBatchDate((String) getCellValue(cell));
                        }
                        break;
                    default:
                        break;
                }
            }
            compensatoryEntitys.add(compensatoryEntity);
        }
    }

    @Override
    public void save() throws Exception {
        if (CollectionUtils.isEmpty(compensatoryEntitys)) {
            return;
        }
        /**
         * 增加判断逻辑
         * 1、先根据ORIGID PROJID REPAYDATE batchdate作为查询条件，查询是否存在记录
         * 2、如果不存在记录，则直接保存
         * 3、如果存在记录，则更新该条记录
         */
        for (final CompensatoryEntity compensatoryEntity : compensatoryEntitys) {
            Long id  = compensatoryEntity.getId();
        if(id>0){
            //根据ID查询数据库
            CompensatoryEntity ret = this.compensatoryDao.queryCompensatoryByKey(id);
            //如果能查询到记录，则表示更新数据
            if(null!=ret){
                this.compensatoryDao.updateCompensatory(compensatoryEntity);
            }else {
                //否则作为新的数据保存到数据库
                this.compensatoryDao.insertCompensatoryToMiddleDB(compensatoryEntity);
            }
        }else{
            //ID无效，作为新的数据保存到数据库
            this.compensatoryDao.insertCompensatoryToMiddleDB(compensatoryEntity);
        }
    }
    }

        private QueryCondition createQueryCondition(String orgid,String projid,Date date,String batchDate){
            String dateStr = DateUtils.formatDate(date, Constants.YYYY_MM_DD, Locale.ENGLISH);
            QueryCondition queryCondition = new QueryCondition();
            //设置查询条件
            queryCondition.getColumnList().add("org_id");
            queryCondition.getColumnList().add("proj_id");
            queryCondition.getColumnList().add("replace_date");
            queryCondition.getColumnList().add("batch_date");

            queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
            queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
            queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
            queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);

            queryCondition.getValueList().add(orgid);
            queryCondition.getValueList().add(projid);
            queryCondition.getValueList().add(dateStr);
            queryCondition.getValueList().add(batchDate);
            return queryCondition;
    }

}
