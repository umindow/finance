package com.supervise.core.data.in;

import com.google.common.collect.Lists;
import com.supervise.common.DateUtils;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.entity.RepaymentEntity;
import com.supervise.dao.mysql.mapper.BankCreditMapper;
import com.supervise.dao.mysql.mapper.BusinessDataMapper;
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
public class BusinessDataImport extends AbstractDataImport {
    @Autowired
    private BusinessDataMapper businessDataMapper;

    private List<BusinessDataEntity> businessDataEntitys = Lists.newArrayList();

    @Override
    public void resolve(Workbook wb) throws Exception {
        Sheet sheet = wb.getSheetAt(0);//获取第一个表格
        if (null == sheet) {
            return;
        }
        BusinessDataEntity businessDataEntity = null;
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
            businessDataEntity = new BusinessDataEntity();
            for (Cell cell : row) {
                if (cell == null) {
                    continue;
                }
                switch (cell.getColumnIndex()) {
                    case 0://机构编码
                    	businessDataEntity.setOrgId((String) getCellValue(cell));
                        break;
                    case 1://项目编码
                    	businessDataEntity.setProjId((String) getCellValue(cell));
                        break;
                    case 2://客户类型
                    	businessDataEntity.setClientType((String) getCellValue(cell));
                        break;
                    case 3://客户编码
                    	businessDataEntity.setClientId((String) getCellValue(cell));
                        break;
                    case 4://客户名称
                    	businessDataEntity.setClienName((String) getCellValue(cell));
                        break;
                    case 5://证件类型
                    	businessDataEntity.setIDCardType((String) getCellValue(cell));
                        break;
                    case 6://证件编码
                    	businessDataEntity.setIDCard((String) getCellValue(cell));
                        break;
                    case 7://所属行业编号（一级）
                    	businessDataEntity.setCallingFirst((String) getCellValue(cell));
                        break;
                    case 8://所属行业编号（二级）
                    	businessDataEntity.setCallingSecond((String) getCellValue(cell));
                        break;
                    case 9://所属地区编号（一级）
                    	businessDataEntity.setAreaFirst((String) getCellValue(cell));
                        break;
                    case 10://所属地区编号（二级）
                    	businessDataEntity.setAreaSecond((String) getCellValue(cell));
                        break;
                    case 11://所属地区编号（三级）
                    	businessDataEntity.setAreaThird((String) getCellValue(cell));
                        break;
                    case 12://客户规模编码
                    	businessDataEntity.setCompanyScale((String) getCellValue(cell));
                        break;
                    case 13://是否涉农
                    	businessDataEntity.setIsFarming((String) getCellValue(cell));
                        break;
                    case 14://业务类型
                    	businessDataEntity.setBusinessType((String) getCellValue(cell));
                        break;
                    case 15://合同金额
                    	businessDataEntity.setContractMoney(new BigDecimal((Double) getCellValue(cell)));
                        break;
                    case 16://已放款金额
                    	businessDataEntity.setLoanMoney(new BigDecimal((Double) getCellValue(cell)));
                        break;
                    case 17://贷款年利率
                    	businessDataEntity.setLoanRate(new BigDecimal((Double) getCellValue(cell)));
                        break;
                    case 18://担保综合费率
                    	businessDataEntity.setAssureRate(new BigDecimal((Double) getCellValue(cell)));
                        break;
                    case 19://放款日期
                    	businessDataEntity.setLoanDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        break;
                    case 20://合同截止日期
                    	businessDataEntity.setContractEndDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        break;
                    case 21://还款方式
                    	businessDataEntity.setRepayType((String) getCellValue(cell));
                        break;
                    case 22://反担保措施
                    	businessDataEntity.setPledgeType((String) getCellValue(cell));
                        break;
                    case 23://反担保备注
                    	businessDataEntity.setApproveOption((String) getCellValue(cell));
                        break;
                    case 24://银行授信记录标示ID
                    	businessDataEntity.setBankCreditPrimaryId((String) getCellValue(cell));
                        break;
                    case 25://合作银行ID
                    	businessDataEntity.setCoBankId((String) getCellValue(cell));
                        break;
                    case 26://项目状态
                    	businessDataEntity.setProjSatus((String) getCellValue(cell));
                        break;
                    case 27://担保权人
                    	businessDataEntity.setAssurePerson((String) getCellValue(cell));
                        break;
                    case 28://反担保物价值
                    	businessDataEntity.setPledgeWorth(new BigDecimal((Double) getCellValue(cell)));
                        break;
                    case 29://存单质押
                    	businessDataEntity.setIsImpawn((String) getCellValue(cell));
                        break;
                    case 30://受理时间
                    	businessDataEntity.setAcceptDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        break;
                    case 31://合同编码
                    	businessDataEntity.setContractId((String) getCellValue(cell));
                        break;
                    case 32://客户存入保证金
                    	businessDataEntity.setClientBailMoney(new BigDecimal((Double) getCellValue(cell)));
                        break;
                    case 33://存出保证金
                    	businessDataEntity.setOutBailMoney(new BigDecimal((Double) getCellValue(cell)));
                        break;
                    case 34://资本属性
                    	businessDataEntity.setCapitalBelong((String) getCellValue(cell));
                        break;
                    case 35://项目结束时间
                    	businessDataEntity.setProjEndDate(DateUtils.parseStringDate((String) getCellValue(cell), null));
                        break;
                    case 36://批次
                    	businessDataEntity.setBatchDate((String) getCellValue(cell));
                        break;
                    default:
                        break;
                }
            }
            businessDataEntitys.add(businessDataEntity);
        }
    }

    @Override
    public void save() throws Exception {
        if (CollectionUtils.isEmpty(businessDataEntitys)) {
            return;
        }
        for (final BusinessDataEntity businessDataEntity : businessDataEntitys) {
        	businessDataMapper.insert(businessDataEntity);
        }
    }
}
