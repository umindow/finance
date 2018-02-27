package com.supervise.core.data.in;

import com.google.common.collect.Lists;
import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.dao.mysql.entity.CompensatoryEntity;
import com.supervise.dao.mysql.middleDao.CompensatoryDao;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
            for (Cell cell : row) {
                if (cell == null) {
                    continue;
                }
                switch (cell.getColumnIndex()) {
                    case 0://机构编码
                        compensatoryEntity.setOrgId((String) getCellValue(cell));
                        break;
                    case 1://项目编码
                        compensatoryEntity.setProjId((String) getCellValue(cell));
                        break;
                    case 2://合同编号
                        compensatoryEntity.setContractId((String) getCellValue(cell));
                        break;
                    case 3://代偿日期
                        compensatoryEntity.setReplaceDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        break;
                    case 4://代偿金额
                        compensatoryEntity.setReplaceMoney(new BigDecimal((Double) getCellValue(cell)));
                        break;
                    case 5:
                        compensatoryEntity.setBatchDate((String) getCellValue(cell));
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
            //根据ORIGID PROJID DATE batchdate 作为查询条件
            String batchDate = compensatoryEntity.getBatchDate();
            if (StringUtils.isEmpty(batchDate)) {
                batchDate = new SimpleDateFormat(Constants.YYYY_MM_DD).format(new Date());
            }
            Date date = compensatoryEntity.getReplaceDate();
            String proj_id = compensatoryEntity.getProjId();
            String org_id = compensatoryEntity.getOrgId();
            //构建查询条件
            QueryCondition queryCondition = createQueryCondition(org_id,proj_id,date,batchDate);

            //根据查询条件查询是否存在记录
            List<CompensatoryEntity> resListToDB  = this.compensatoryDao.queryCompensatoryByCondition(queryCondition);
            //如果没有查询到记录，则保存当前新记录
            if (CollectionUtils.isEmpty(resListToDB)) {
                this.compensatoryDao.insertCompensatoryToMiddleDB(compensatoryEntity);
            }else{
                //否则更新当前记录
                for ( CompensatoryEntity compensatory : resListToDB){
                    compensatoryEntity.setId(compensatory.getId());
                    this.compensatoryDao.updateCompensatory(compensatoryEntity);
                }

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
