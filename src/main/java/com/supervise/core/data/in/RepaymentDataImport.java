package com.supervise.core.data.in;

import com.google.common.collect.Lists;
import com.supervise.common.DateUtils;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.entity.RepaymentEntity;
import com.supervise.dao.mysql.mapper.BankCreditMapper;
import com.supervise.dao.mysql.mapper.RepaymentMapper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

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
    private RepaymentMapper repaymentMapper;

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
            repaymentEntity = new RepaymentEntity();
            for (Cell cell : row) {
                if (cell == null) {
                    continue;
                }
                switch (cell.getColumnIndex()) {
                    case 0://机构编码
                    	repaymentEntity.setOrgId((String) getCellValue(cell));
                        break;
                    case 1://项目编码
                    	repaymentEntity.setProjId((String) getCellValue(cell));
                        break;
                    case 2://合同编号
                    	repaymentEntity.setContractId((String) getCellValue(cell));
                        break;
                    case 3://实际还款日期
                    	repaymentEntity.setRepayDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        break;
                    case 4://实际归还本金
                    	repaymentEntity.setPrincipal(new BigDecimal((Double) getCellValue(cell)));
                        break;
                    case 5://实际归还利息
                    	repaymentEntity.setInterest(new BigDecimal((Double) getCellValue(cell)));
                        break;
                    case 6://收取罚息
                    	repaymentEntity.setPunishMoney(new BigDecimal((Double) getCellValue(cell)));
                        break;
                    case 7:
                    	repaymentEntity.setBatchDate((String) getCellValue(cell));
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
        for (final RepaymentEntity repaymentEntity : repaymentEntitys) {
        	repaymentMapper.insert(repaymentEntity);
        }
    }
}
