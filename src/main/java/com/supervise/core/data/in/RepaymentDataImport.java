package com.supervise.core.data.in;

import com.google.common.collect.Lists;
import com.supervise.cache.FiedRoleCache;
import com.supervise.common.CellUtil;
import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.config.role.DataType;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.entity.RepaymentEntity;
import com.supervise.dao.mysql.middleDao.BusinessDataDao;
import com.supervise.dao.mysql.middleDao.RepaymentDao;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
public class RepaymentDataImport extends AbstractDataImport {
    private final Logger logger = LoggerFactory.getLogger(RepaymentDataImport.class);
    @Autowired
    private BusinessDataDao businessDataDao;
    @Autowired
    private RepaymentDao repaymentDao;

    private List<RepaymentEntity> repaymentEntitys = Lists.newArrayList();

    @Override
    public void resolve(Workbook wb) throws Exception {
        logger.info("Repaymentfile start import!");
        Sheet sheet = wb.getSheetAt(0);//获取第一个表格
        if (null == sheet) {
            return;
        }
        RepaymentEntity repaymentEntity = null;
        try {
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
                Map<String,FiedRoleCache.DepRoleRef> filedRoles = FiedRoleCache.mapDepRoleRefs(DataType.SUPERVISE_REBACK_DATA.getDataLevel());
                repaymentEntity = new RepaymentEntity();
                for (Cell cell : row) {
                    if (cell == null) {
                        continue;
                    }
                    String value = getValue(cell);
                    switch (cell.getColumnIndex()) {
                        case 0://主键ID号
                            value = CellUtil.trimValue(value);
                            if(!StringUtils.isEmpty(value)){
                                repaymentEntity.setId(Long.parseLong(value));
                            }
                            break;
                        case 1://机构编码
//                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("orgId"))) {
//                                value = CellUtil.trimValue(value);
//                                repaymentEntity.setOrgId(value);
//                            }
                            repaymentEntity.setOrgId(Constants.ORG_ID);
                            break;
                        case 2://项目编码
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("projId"))) {
                                value = CellUtil.trimValue(value);
                                repaymentEntity.setProjId(value);
                            }
                            break;
                        case 3://合同编号
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("contractId"))) {
                                value = CellUtil.trimValue(value);
                                repaymentEntity.setContractId(value);
                            }
                            break;
                        case 4://实际还款日期
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("repayDate"))) {
                                repaymentEntity.setRepayDate(DateUtils.parseStringDate(value, Constants.YYYY_MM_DD));
                            }
                            break;
                        case 5://实际归还本金
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("principal"))) {
                                Double money = CellUtil.transfValuetoDouble(value);
                                if(null!=money){
                                    repaymentEntity.setPrincipal(new BigDecimal(money));
                                }
                            }
                            break;
                        case 6://实际归还利息
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("interest"))) {
                                Double money = CellUtil.transfValuetoDouble(value);
                                if(null!=money){
                                    repaymentEntity.setInterest(new BigDecimal(money));
                                }
                            }
                            break;
                        case 7://收取罚息
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("punishMoney"))) {
                                Double money = CellUtil.transfValuetoDouble(value);
                                if(null!=money){
                                    repaymentEntity.setPunishMoney(new BigDecimal(money));
                                }
                            }
                            break;
                        case 8:
                            if(FiedRoleCache.checkFieldRole(getUserEntity(),filedRoles.get("batchDate"))) {
                                String batchDate = new SimpleDateFormat(Constants.YYYY_MM_DD).format(new Date());
                                repaymentEntity.setBatchDate(batchDate);
                            }
                            break;
                        default:
                            break;
                    }
                }
                repaymentEntitys.add(repaymentEntity);
            }
        }catch (Exception e){
            repaymentEntitys.clear();
            logger.info("Repaymentfile import error!");
            e.printStackTrace();
        }
        logger.info("Repaymentfile import end,size:"+repaymentEntitys.size());

    }

    @Override
    public void save() throws Exception {
        if (CollectionUtils.isEmpty(repaymentEntitys)) {
            return;
        }
        /**
         * 先删除当天批次的所有记录，
         */
//        String batchDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        this.repaymentDao.deleteRepaymentByBatchDate(batchDate);
        //然后保存导入的EXCEL记录
        try {
            for (final RepaymentEntity repaymentEntity : repaymentEntitys) {
                String orgid = repaymentEntity.getOrgId();
                String projid = repaymentEntity.getProjId();
                String batchdate = repaymentEntity.getBatchDate();
                QueryCondition queryCondition = createQueryCondition(orgid,projid,batchdate);
                List<RepaymentEntity> repayEntityList = this.repaymentDao.queryRepaymentByCondition(queryCondition);
                if (!CollectionUtils.isEmpty(repayEntityList)) {
                    //不为空，同时主键ID号相同，则表示做更新;否则表示新增
                    boolean hasID = false;
                    for(RepaymentEntity repay :repayEntityList){
                        if(null!=repaymentEntity.getId()&&repaymentEntity.getId()== repay.getId()){
                            repaymentEntity.setSendStatus(repay.getSendStatus());
                            repaymentEntity.setCreateDate(repay.getCreateDate());
                            this.repaymentDao.updateRepayment(repaymentEntity);
                            hasID = true;
                            break;
                        }
                    }
                    if(!hasID){
                        //标明没有查询到相同的主键ID
                        repaymentEntity.setId(0L);//重新设置主键，避免主键重复
                        this.repaymentDao.insertRepaymentToMiddleDB(repaymentEntity);
                    }
                }else{
                    //否则表示新增，同时查询业务数据，查看是否立项，如果有立项则新增，否则丢弃
                    List<BusinessDataEntity> resList  = this.businessDataDao.queryBusinessDataByCondition(queryCondition);
                    if(!CollectionUtils.isEmpty(resList)){
                        repaymentEntity.setId(0L);//重新设置主键，避免主键重复
                        this.repaymentDao.insertRepaymentToMiddleDB(repaymentEntity);
                    }else{
                        logger.info("orgid :"+orgid +" projid:"+projid +" batchdate:"+batchdate);
                        logger.info("Not Exist in BusinessData!");
                        logger.info("Can not save repayment with new projid in this batchdate!");
                    }
                }
            }
        }catch (Exception e){
            logger.error("repaymentfile import updateDB error!");
            e.printStackTrace();
        }finally {
            repaymentEntitys.clear();
        }


        /**
         * 增加判断逻辑
         * 1、先根据ID作为查询条件，查询是否存在记录
         * 2、如果不存在记录，则直接保存
         * 3、如果存在记录，则更新该条记录
         */
//        for (final RepaymentEntity repaymentEntity : repaymentEntitys) {
//            Long id  = repaymentEntity.getId();
//            if(id>0){
//                //根据ID查询数据库
//                RepaymentEntity ret = this.repaymentDao.queryRepaymentByKey(id);
//                //如果能查询到记录，则表示更新数据
//                if(null!=ret){
//                    this.repaymentDao.updateRepayment(repaymentEntity);
//                }else {
//                    //否则作为新的数据保存到数据库
//                    this.repaymentDao.insertRepaymentToMiddleDB(repaymentEntity);
//                }
//            }else{
//                //ID无效，作为新的数据保存到数据库
//                this.repaymentDao.insertRepaymentToMiddleDB(repaymentEntity);
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
