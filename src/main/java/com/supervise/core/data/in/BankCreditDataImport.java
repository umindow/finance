package com.supervise.core.data.in;

import com.google.common.collect.Interner;
import com.google.common.collect.Lists;
import com.supervise.cache.FiedRoleCache;
import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.common.SessionUser;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.config.role.DataType;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.entity.UserEntity;
import com.supervise.dao.mysql.middleDao.BankCreditDao;
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
        Map<String,FiedRoleCache.DepRoleRef> filedRoles = FiedRoleCache.mapDepRoleRefs(DataType.SUPERVISE_BANK_DATA.getDataType());
        int userDepId = Integer.valueOf(getUserEntity().getDepId());
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
            bankCreditEntity = new BankCreditEntity();
            for (Cell cell : row) {
                if (cell == null) {
                    continue;
                }
                switch (cell.getColumnIndex()) {
                    case 0://结构编码
                        if(FiedRoleCache.checkFiledRole(userDepId,filedRoles.get(""))) {
                            bankCreditEntity.setPrimaryId((String) getCellValue(cell));
                        }
                        break;
                    case 1://结构编码
                        bankCreditEntity.setOrgId((String) getCellValue(cell));
                        break;
                    case 2:
                        bankCreditEntity.setBankId((String) getCellValue(cell));
                        break;
                    case 3:
                        bankCreditEntity.setCreditTypeId((String) getCellValue(cell));
                        break;
                    case 4:
                        bankCreditEntity.setCreditMoney(new BigDecimal((Double) getCellValue(cell)));
                        break;
                    case 5:
                        bankCreditEntity.setBailScale(new BigDecimal((Double) getCellValue(cell)));
                        break;
                    case 6:
                        bankCreditEntity.setCreditStartDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        break;
                    case 7:
                        bankCreditEntity.setCreditEndDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        break;
                    case 8:
                        bankCreditEntity.setSingleMoneyLimit(new BigDecimal((Double) getCellValue(cell)));
                        break;
                    case 9:
                        bankCreditEntity.setIsForCredit((String) getCellValue(cell));
                        break;
                    case 10:
                        bankCreditEntity.setBatchDate((String) getCellValue(cell));
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
         * 增加判断逻辑
         * 1、先根据ORIGID primary_id  batchdate作为查询条件，查询是否存在记录
         * 2、如果不存在记录，则直接保存
         * 3、如果存在记录，则更新该条记录
         */
        for (final BankCreditEntity bankCreditEntity : bankCreditEntities) {
            //根据ORIGID primary_id batchdate 作为查询条件
            String batchDate = bankCreditEntity.getBatchDate();
            if (StringUtils.isEmpty(batchDate)) {
                batchDate = new SimpleDateFormat(Constants.YYYY_MM_DD).format(new Date());
            }
            String primary_id = bankCreditEntity.getPrimaryId();
            String org_id = bankCreditEntity.getOrgId();
            //构建查询条件
            QueryCondition queryCondition = createQueryCondition(org_id,primary_id,batchDate);

            //根据查询条件查询是否存在记录
            List<BankCreditEntity> resListToDB  = this.bankCreditDao.queryBankCreditByCondition(queryCondition);
            //如果没有查询到记录，则保存当前新记录
            if (CollectionUtils.isEmpty(resListToDB)) {
                this.bankCreditDao.insertBankCreditToMiddleDB(bankCreditEntity);
            }else{
                //否则更新当前记录
                for ( BankCreditEntity bankCredit : resListToDB){
                    bankCreditEntity.setId(bankCredit.getId());
                    this.bankCreditDao.updateBankCredit(bankCreditEntity);
                }
            }

        }


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
