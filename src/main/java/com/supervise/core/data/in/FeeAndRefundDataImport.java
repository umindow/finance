package com.supervise.core.data.in;

import com.google.common.collect.Lists;
import com.supervise.cache.FiedRoleCache;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.config.role.DataType;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.entity.FeeAndRefundEntity;
import com.supervise.dao.mysql.middleDao.BusinessDataDao;
import com.supervise.dao.mysql.middleDao.FeeAndRefundDao;
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
public class FeeAndRefundDataImport extends AbstractDataImport {
    private final Logger logger = LoggerFactory.getLogger(FeeAndRefundDataImport.class);
    @Autowired
    private BusinessDataDao businessDataDao;
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
            Map<String,FiedRoleCache.DepRoleRef> filedRoles = FiedRoleCache.mapDepRoleRefs(DataType.SUPERVISE_FEE_DATA.getDataLevel());
            feeAndRefundEntity = new FeeAndRefundEntity();
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
                            feeAndRefundEntity.setId(Long.parseLong(value));
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
                            String batchDate = (String) getCellValue(cell);//YYYY-MM-DD YYYY-M-D
                            if(batchDate.length()<10&&batchDate.length()==8){
                                String date = batchDate.substring(7);
                                String moth = batchDate.substring(5,6);
                                String year = batchDate.substring(0,4);
                                date = "0"+date;
                                moth = "0"+moth;
                                batchDate = year+"-"+moth+"-"+date;
                            }
                            feeAndRefundEntity.setBatchDate(batchDate);
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
         * 先删除当天批次的所有记录，
         */
        String batchDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.feeAndRefundDao.deleteFeeAndRefundByBatchDate(batchDate);
        //然后保存导入的EXCEL记录
        for (final FeeAndRefundEntity feeAndRefundEntity : feeAndRefundEntitys) {
            feeAndRefundEntity.setId(0L);//重新设置主键，避免主键重复
            String orgid = feeAndRefundEntity.getOrgId();
            String projid = feeAndRefundEntity.getProjId();
            String batchdate = feeAndRefundEntity.getBatchDate();
            QueryCondition queryCondition = createQueryCondition(orgid,projid,batchdate);
            List<BusinessDataEntity> resList  = this.businessDataDao.queryBusinessDataByCondition(queryCondition);
            if(!CollectionUtils.isEmpty(resList)){
                this.feeAndRefundDao.insertFeeAndRefundToMiddleDB(feeAndRefundEntity);
            }else{
                logger.info("orgid :"+orgid +" projid:"+projid +" batchdate:"+batchdate);
                logger.info("Not Exist in BusinessData!");
                logger.info("Can not save feeAndRefund with new projid in this batchdate!");
            }
        }
        feeAndRefundEntitys.clear();
        /**
         * 增加判断逻辑
         * 1、先根据ORIGID PROJID charge_time batchdate作为查询条件，查询是否存在记录
         * 2、如果不存在记录，则直接保存
         * 3、如果存在记录，则更新该条记录
         */
//        for (final FeeAndRefundEntity feeAndRefundEntity : feeAndRefundEntitys) {
//            Long id  = feeAndRefundEntity.getId();
//            if(id>0){
//                //根据ID查询数据库
//                FeeAndRefundEntity ret = this.feeAndRefundDao.queryFeeAndRefundByKey(id);
//                //如果能查询到记录，则表示更新数据
//                if(null!=ret){
//                    this.feeAndRefundDao.updateFeeAndRefund(feeAndRefundEntity);
//                }else {
//                    //否则作为新的数据保存到数据库
//                    this.feeAndRefundDao.insertFeeAndRefundToMiddleDB(feeAndRefundEntity);
//                }
//            }else{
//                //ID无效，作为新的数据保存到数据库
//                this.feeAndRefundDao.insertFeeAndRefundToMiddleDB(feeAndRefundEntity);
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
