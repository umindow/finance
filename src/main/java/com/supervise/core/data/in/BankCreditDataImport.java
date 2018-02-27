package com.supervise.core.data.in;

import com.google.common.collect.Lists;
import com.supervise.common.DateUtils;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.mapper.BankCreditMapper;
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
    private BankCreditMapper bankCreditMapper;

    private List<BankCreditEntity> bankCreditEntities = Lists.newArrayList();

    @Override
    public void resolve(Workbook wb) throws Exception {
        Sheet sheet = wb.getSheetAt(0);//获取第一个表格
        if (null == sheet) {
            return;
        }
        BankCreditEntity bankCreditEntity = null;
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
            bankCreditEntity.setCreateDate(new Date());
            bankCreditEntity.setUpdateDate(new Date());
            for (Cell cell : row) {
                if (cell == null) {
                    continue;
                }
                switch (cell.getColumnIndex()) {
                    case 0://结构编码
                        bankCreditEntity.setPrimaryId((String) getCellValue(cell));
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
        for (final BankCreditEntity bankCreditEntity : bankCreditEntities) {
            bankCreditMapper.insert(bankCreditEntity);
        }
    }
}
