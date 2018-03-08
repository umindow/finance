package com.supervise.core.data.in;

import com.google.common.collect.Lists;
import com.supervise.cache.FiedRoleCache;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.config.role.DataType;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.entity.RecourseEntity;
import com.supervise.dao.mysql.middleDao.BusinessDataDao;
import com.supervise.dao.mysql.middleDao.RecourseDao;
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
public class RecourseDataImport extends AbstractDataImport {
    private final Logger logger = LoggerFactory.getLogger(RecourseDataImport.class);
    @Autowired
    private BusinessDataDao businessDataDao;
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
            if (StringUtils.isBlank((String) getCellValue(row.getCell(1)))) {
                break;
            }
            
            Map<String,FiedRoleCache.DepRoleRef> filedRoles = FiedRoleCache.mapDepRoleRefs(DataType.SUPERVISE_TRACE_DATA.getDataLevel());
			recourseEntity = new RecourseEntity();
            for (Cell cell : row) {
                if (cell == null) {
                    continue;
                }
                switch (cell.getColumnIndex()) {
                    case 0://主键ID号
                        if(Cell.CELL_TYPE_NUMERIC==cell.getCellType()){
                            BigDecimal big=new BigDecimal(cell.getNumericCellValue());
                            String value = big.toString();
                            if(null != value && !"".equals(value.trim())){
                                String[] item = value.split("[.]");
                                if(1<item.length&&"0".equals(item[1])){
                                    value=item[0];
                                }
                            }
                            recourseEntity.setId(Long.parseLong(value));
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
                            String batchDate = (String) getCellValue(cell);//YYYY-MM-DD YYYY-M-D
                            if(batchDate.length()<10&&batchDate.length()==8){
                                String date = batchDate.substring(7);
                                String moth = batchDate.substring(5,6);
                                String year = batchDate.substring(0,4);
                                date = "0"+date;
                                moth = "0"+moth;
                                batchDate = year+"-"+moth+"-"+date;
                            }
                            recourseEntity.setBatchDate(batchDate);
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
         * 先删除当天批次的所有记录，
         */
        String batchDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.recourseDao.deleteRecourseByBatchDate(batchDate);
        //然后保存导入的EXCEL记录
        for (final RecourseEntity recourseEntity : recourseEntitys) {
            recourseEntity.setId(0L);//重新设置主键，避免主键重复
            String orgid = recourseEntity.getOrgId();
            String projid = recourseEntity.getProjId();
            String batchdate = recourseEntity.getBatchDate();
            QueryCondition queryCondition = createQueryCondition(orgid,projid,batchdate);
            List<BusinessDataEntity> resList  = this.businessDataDao.queryBusinessDataByCondition(queryCondition);
            if(!CollectionUtils.isEmpty(resList)){
                this.recourseDao.insertRecourseToMiddleDB(recourseEntity);
            }else{
                logger.info("orgid :"+orgid +" projid:"+projid +" batchdate:"+batchdate);
                logger.info("Not Exist in BusinessData!");
                logger.info("Can not save recourse with new projid in this batchdate!");
            }
        }
        recourseEntitys.clear();
        /**
         * 增加判断逻辑
         * 1、先根据ORIGID PROJID REPAYDATE batchdate作为查询条件，查询是否存在记录
         * 2、如果不存在记录，则直接保存
         * 3、如果存在记录，则更新该条记录
         */
//        for (final RecourseEntity recourseEntity : recourseEntitys) {
//            Long id  = recourseEntity.getId();
//            if(id>0){
//                //根据ID查询数据库
//                RecourseEntity ret = this.recourseDao.queryRecourseByKey(id);
//                //如果能查询到记录，则表示更新数据
//                if(null!=ret){
//                    this.recourseDao.updateRecourse(recourseEntity);
//                }else {
//                    //否则作为新的数据保存到数据库
//                    this.recourseDao.insertRecourseToMiddleDB(recourseEntity);
//                }
//            }else{
//                //ID无效，作为新的数据保存到数据库
//                this.recourseDao.insertRecourseToMiddleDB(recourseEntity);
//            }
//        }
    }


    private QueryCondition createQueryCondition(String orgid,String projid,String batchDate){
        QueryCondition queryCondition = new QueryCondition();
        //设置查询条件
        queryCondition.getColumnList().add("org_id");
        queryCondition.getColumnList().add("proj_id");
        queryCondition.getColumnList().add("batch_date");

        queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
        queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
        queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);

        queryCondition.getValueList().add(orgid);
        queryCondition.getValueList().add(projid);
        queryCondition.getValueList().add(batchDate);
        return queryCondition;
    }

}
