package com.supervise.core.data.in;

import com.google.common.collect.Lists;
import com.supervise.cache.FiedRoleCache;
import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.config.role.DataType;
import com.supervise.dao.mysql.entity.RecourseEntity;
import com.supervise.dao.mysql.middleDao.RecourseDao;
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
public class RecourseDataImport extends AbstractDataImport {
    @Autowired
    private RecourseDao recourseDao;

    private List<RecourseEntity> recourseEntitys = Lists.newArrayList();

    @Override
    public void resolve(Workbook wb) throws Exception {
        Sheet sheet = wb.getSheetAt(0);//获取第一个表格
        if (null == sheet) {
            return;
        }
        RecourseEntity recourseEntity = null;
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
            recourseEntity = new RecourseEntity();
            recourseEntity.setSendStatus(Constants.DATA_READY_SEND);
            for (Cell cell : row) {
                if (cell == null) {
                    continue;
                }
                switch (cell.getColumnIndex()) {
                    case 0://主键ID号
                        if(Cell.CELL_TYPE_NUMERIC==cell.getCellType()){
                            recourseEntity.setId((Long) getCellValue(cell));
                        };
                        break;
                    case 1://机构编码
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("org_id"))) {
                            recourseEntity.setOrgId((String) getCellValue(cell));
                        }
                        break;
                    case 2://项目编码
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("proj_id"))) {
                            recourseEntity.setProjId((String) getCellValue(cell));
                        }
                        break;
                    case 3://合同编号
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("contract_id"))) {
                            recourseEntity.setContractId((String) getCellValue(cell));
                        }
                        break;
                    case 4://追偿类型
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("replevy_type"))) {
                            recourseEntity.setReplevyType((String) getCellValue(cell));
                        }
                        break;
                    case 5://追偿日期
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("replevy_date"))) {
                            recourseEntity.setReplevyDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        }
                        break;
                    case 6://追偿金额
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("replevy_money"))) {
                            recourseEntity.setReplevyMoney(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 7:
                        if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("batch_date"))) {
                            recourseEntity.setBatchDate((String) getCellValue(cell));
                        }
                        break;
                    default:
                        break;
                }
            }
            recourseEntitys.add(recourseEntity);
        }
    }

    @Override
    public void save() throws Exception {
        if (CollectionUtils.isEmpty(recourseEntitys)) {
            return;
        }
        /**
         * 增加判断逻辑
         * 1、先根据ORIGID PROJID REPAYDATE batchdate作为查询条件，查询是否存在记录
         * 2、如果不存在记录，则直接保存
         * 3、如果存在记录，则更新该条记录
         */
        for (final RecourseEntity recourseEntity : recourseEntitys) {
            Long id  = recourseEntity.getId();
            if(id>0){
                //根据ID查询数据库
                RecourseEntity ret = this.recourseDao.queryRecourseByKey(id);
                //如果能查询到记录，则表示更新数据
                if(null!=ret){
                    this.recourseDao.updateRecourse(recourseEntity);
                }else {
                    //否则作为新的数据保存到数据库
                    this.recourseDao.insertRecourseToMiddleDB(recourseEntity);
                }
            }else{
                //ID无效，作为新的数据保存到数据库
                this.recourseDao.insertRecourseToMiddleDB(recourseEntity);
            }
        }
    }

        private QueryCondition createQueryCondition(String orgid,String projid,Date date,String batchDate){
            String dateStr = DateUtils.formatDate(date, Constants.YYYY_MM_DD, Locale.ENGLISH);
            QueryCondition queryCondition = new QueryCondition();
            //设置查询条件
            queryCondition.getColumnList().add("org_id");
            queryCondition.getColumnList().add("proj_id");
            queryCondition.getColumnList().add("replevy_date");
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
