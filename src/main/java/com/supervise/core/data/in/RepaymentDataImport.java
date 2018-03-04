package com.supervise.core.data.in;

import com.google.common.collect.Lists;
import com.supervise.cache.FiedRoleCache;
import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.config.role.DataType;
import com.supervise.dao.mysql.entity.RepaymentEntity;
import com.supervise.dao.mysql.middleDao.RepaymentDao;
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
    @Autowired
    private RepaymentDao repaymentDao;

    private List<RepaymentEntity> repaymentEntitys = Lists.newArrayList();

    @Override
    public void resolve(Workbook wb) throws Exception {
        Sheet sheet = wb.getSheetAt(0);//获取第一个表格
        if (null == sheet) {
            return;
        }
        RepaymentEntity repaymentEntity = null;
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
            Map<String,FiedRoleCache.DepRoleRef> filedRoles = FiedRoleCache.mapDepRoleRefs(DataType.SUPERVISE_BANK_DATA.getDataLevel());
            int userDepId = Integer.valueOf(getUserEntity().getDepId());
            repaymentEntity = new RepaymentEntity();
            for (Cell cell : row) {
                if (cell == null) {
                    continue;
                }
                switch (cell.getColumnIndex()) {
                    case 0://机构编码
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("org_id"))) {
                            repaymentEntity.setOrgId((String) getCellValue(cell));
                        }
                        break;
                    case 1://项目编码
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("proj_id"))) {
                            repaymentEntity.setProjId((String) getCellValue(cell));
                        }
                        break;
                    case 2://合同编号
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("contract_id"))) {
                            repaymentEntity.setContractId((String) getCellValue(cell));
                        }
                        break;
                    case 3://实际还款日期
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("repay_date"))) {
                            repaymentEntity.setRepayDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        }
                        break;
                    case 4://实际归还本金
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("principal"))) {
                            repaymentEntity.setPrincipal(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 5://实际归还利息
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("interest"))) {
                            repaymentEntity.setInterest(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 6://收取罚息
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("punish_money"))) {
                            repaymentEntity.setPunishMoney(new BigDecimal((Double) getCellValue(cell)));
                        }
                        break;
                    case 7:
                        if(FiedRoleCache.checkFieldRole(userDepId,filedRoles.get("batch_date"))) {
                            repaymentEntity.setBatchDate((String) getCellValue(cell));
                        }
                        break;
                    default:
                        break;
                }
            }
            repaymentEntitys.add(repaymentEntity);
        }
    }

    @Override
    public void save() throws Exception {
        if (CollectionUtils.isEmpty(repaymentEntitys)) {
            return;
        }
        /**
         * 增加判断逻辑
         * 1、先根据ORIGID PROJID REPAYDATE batchdate作为查询条件，查询是否存在记录
         * 2、如果不存在记录，则直接保存
         * 3、如果存在记录，则更新该条记录
         */
        for (final RepaymentEntity repaymentEntity : repaymentEntitys) {
            //根据ORIGID PROJID REPAYDATE batchdate 作为查询条件
            String batchDate = repaymentEntity.getBatchDate();
            if (StringUtils.isEmpty(batchDate)) {
                batchDate = new SimpleDateFormat(Constants.YYYY_MM_DD).format(new Date());
            }
            Date repayDate = repaymentEntity.getRepayDate();
            String proj_id = repaymentEntity.getProjId();
            String org_id = repaymentEntity.getOrgId();
            //构建查询条件
            QueryCondition queryCondition = createQueryCondition(org_id,proj_id,repayDate,batchDate);

            //根据查询条件查询是否存在记录
            List<RepaymentEntity> resListToDB  = this.repaymentDao.queryRepaymentByCondition(queryCondition);
            //如果没有查询到记录，则保存当前新记录
            if (CollectionUtils.isEmpty(resListToDB)) {
                this.repaymentDao.insertRepaymentToMiddleDB(repaymentEntity);
            }else{
                //否则更新当前记录
                for ( RepaymentEntity repayment : resListToDB){
                    repaymentEntity.setId(repayment.getId());
                    this.repaymentDao.updateRepayment(repaymentEntity);
                }

            }

        }
    }

        private QueryCondition createQueryCondition(String orgid,String projid,Date repayDate,String batchDate){
            String repayDateStr = DateUtils.formatDate(repayDate, Constants.YYYY_MM_DD, Locale.ENGLISH);
            QueryCondition queryCondition = new QueryCondition();
            //设置查询条件
            queryCondition.getColumnList().add("org_id");
            queryCondition.getColumnList().add("proj_id");
            queryCondition.getColumnList().add("repay_date");
            queryCondition.getColumnList().add("batch_date");

            queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
            queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
            queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
            queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);

            queryCondition.getValueList().add(orgid);
            queryCondition.getValueList().add(projid);
            queryCondition.getValueList().add(repayDateStr);
            queryCondition.getValueList().add(batchDate);
            return queryCondition;
    }

}
