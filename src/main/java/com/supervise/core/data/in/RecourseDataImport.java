package com.supervise.core.data.in;

import com.google.common.collect.Lists;
import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.dao.mysql.entity.RecourseEntity;
import com.supervise.dao.mysql.middleDao.RecourseDao;
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
            for (Cell cell : row) {
                if (cell == null) {
                    continue;
                }
                switch (cell.getColumnIndex()) {
                    case 0://机构编码
                        recourseEntity.setOrgId((String) getCellValue(cell));
                        break;
                    case 1://项目编码
                        recourseEntity.setProjId((String) getCellValue(cell));
                        break;
                    case 2://合同编号
                        recourseEntity.setContractId((String) getCellValue(cell));
                        break;
                    case 3://追偿类型
                        recourseEntity.setReplevyType((String) getCellValue(cell));
                        break;
                    case 4://追偿日期
                        recourseEntity.setReplevyDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        break;
                    case 5://追偿金额
                        recourseEntity.setReplevyMoney(new BigDecimal((Double) getCellValue(cell)));
                        break;
                    case 6:
                        recourseEntity.setBatchDate((String) getCellValue(cell));
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
            //根据ORIGID PROJID DATE batchdate 作为查询条件
            String batchDate = recourseEntity.getBatchDate();
            if (StringUtils.isEmpty(batchDate)) {
                batchDate = new SimpleDateFormat(Constants.YYYY_MM_DD).format(new Date());
            }
            Date date = recourseEntity.getReplevyDate();
            String proj_id = recourseEntity.getProjId();
            String org_id = recourseEntity.getOrgId();
            //构建查询条件
            QueryCondition queryCondition = createQueryCondition(org_id,proj_id,date,batchDate);

            //根据查询条件查询是否存在记录
            List<RecourseEntity> resListToDB  = this.recourseDao.queryRecourseByCondition(queryCondition);
            //如果没有查询到记录，则保存当前新记录
            if (CollectionUtils.isEmpty(resListToDB)) {
                this.recourseDao.insertRecourseToMiddleDB(recourseEntity);
            }else{
                //否则更新当前记录
                for ( RecourseEntity recourse : resListToDB){
                    recourseEntity.setId(recourse.getId());
                    this.recourseDao.updateRecourse(recourseEntity);
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
