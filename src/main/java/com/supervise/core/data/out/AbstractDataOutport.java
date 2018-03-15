package com.supervise.core.data.out;

import com.supervise.common.DateUtils;
import com.supervise.config.role.DataType;
import com.supervise.controller.vo.DataSet;
import com.supervise.controller.vo.DataVo;
import com.supervise.controller.vo.FieldValue;
import com.supervise.dao.mysql.entity.UserEntity;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletOutputStream;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xishui.hb on 2018/3/5 上午9:47.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public abstract class AbstractDataOutport implements DataOutport {
    @Override
    public void export(ServletOutputStream servletOutputStream, DataType dataType, String date, UserEntity userEntity) throws Exception {
        DataSet dataSet = dataSet(date, dataType, userEntity);
        if (null == dataSet || CollectionUtils.isEmpty(dataSet.getFields())) {
            throw new Exception("无数据项.");
        }
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(dataType.getDataName());
        HSSFRow row = sheet.createRow(0);//表头title设置
        row.createCell(0).setCellValue("数据主键");
        //表头完毕
        for (int i = 0; i < dataSet.getFields().size(); i++) {
            row.createCell(i + 1).setCellValue(dataSet.getFields().get(i));

        }
        if(!CollectionUtils.isEmpty(dataSet.getDataVos())) {
            //表数据
            DataVo dataVo = null;
            FieldValue fieldValue = null;
            for (int j = 0; j < dataSet.getDataVos().size(); j++) {
                row = sheet.createRow(j + 1);
                dataVo = dataSet.getDataVos().get(j);
                row.createCell(0).setCellValue(dataVo.getDataId());
                for (int m = 0; m < dataVo.getValues().size(); m++) {
                    fieldValue = dataVo.getValues().get(m);
                    if (fieldValue.getValue() instanceof Integer) {
                        row.createCell(m + 1).setCellValue((Integer) fieldValue.getValue());
                    }
                    if (fieldValue.getValue() instanceof String) {
                        row.createCell(m + 1).setCellValue((String) fieldValue.getValue());
                    }
                    if (fieldValue.getValue() instanceof BigDecimal) {
                        row.createCell(m + 1).setCellValue(((BigDecimal) fieldValue.getValue()).doubleValue());
                    }
                    if (fieldValue.getValue() instanceof Double) {
                        row.createCell(m + 1).setCellValue((Double) fieldValue.getValue());
                    }
                    if (fieldValue.getValue() instanceof Long) {
                        row.createCell(m + 1).setCellValue((Long) fieldValue.getValue());
                    }
                    if (fieldValue.getValue() instanceof Float) {
                        row.createCell(m + 1).setCellValue((Float) fieldValue.getValue());
                    }
                    if (fieldValue.getValue() instanceof Date) {
                        row.createCell(m + 1).setCellValue(DateUtils.formatDate((Date) fieldValue.getValue(), DateUtils.DEFAULT_PATTERN));
                    }
                }
            }
        }
        workbook.write(servletOutputStream);
        servletOutputStream.flush();
        servletOutputStream.close();
    }

    abstract DataSet dataSet(String date, DataType dataType, UserEntity userEntity);
}
