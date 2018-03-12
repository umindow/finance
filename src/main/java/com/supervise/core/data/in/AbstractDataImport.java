package com.supervise.core.data.in;

import com.supervise.common.SessionUser;
import com.supervise.dao.mysql.entity.UserEntity;
import lombok.Getter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

/**
 * Created by xishui.hb on 2018/2/12 下午3:16.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public abstract class AbstractDataImport implements DataImport {
    private static final Logger log = LoggerFactory.getLogger(AbstractDataImport.class);
    public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
    private static final String OFFICE_EXCEL_XLS = ".xls";
    private static final String OFFICE_EXCEL_XLSX = ".xlsx";
    static final String NUMBER_REG = "^\\d*(\\.\\d*|)$";
    @Getter
    private UserEntity userEntity;
    @Override
    public void in(MultipartFile file) throws Exception {
        this.userEntity = SessionUser.INSTANCE.getCurrentUser();
        if(null == this.userEntity){
            throw new Exception("用户未登录");
        }
        checkData(file);
        save();
    }

    private final void checkData(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new Exception("file is null");
        }

        if (file.getOriginalFilename().endsWith(OFFICE_EXCEL_XLS)) {
            readXLS(file.getInputStream());
        } else if (file.getOriginalFilename().endsWith(OFFICE_EXCEL_XLSX)) {
            readXLSX(file);
        } else {
            throw new Exception("file does not support:" + file.getOriginalFilename());
        }
    }

    private final void readXLS(MultipartFile file) throws Exception {
        try {
            InputStream input = file.getInputStream();
            readXLS(input);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new Exception("文件提取错误");
        }
    }

    private final void readXLS(InputStream input) throws Exception {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(input);
            HSSFWorkbook wb = new HSSFWorkbook(fs, true);

            resolve(wb);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new Exception("文件解析错误");
        }
    }

    private final void readXLSX(MultipartFile file) throws Exception {
        try {
            InputStream input = file.getInputStream();
            readXLSX(input);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new Exception("文件提取错误");
        }
    }

    private final void readXLSX(InputStream input) throws Exception {
        try {
            OPCPackage op = OPCPackage.open(input);
            XSSFWorkbook wb = new XSSFWorkbook(op);
            resolve(wb);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception("文件解析错误");
        }
    }

    public abstract void resolve(Workbook wb) throws Exception;

    public abstract void save() throws Exception;

    private  Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    //  如果是date类型则 ，获取该cell的date值
                    return new SimpleDateFormat(DATE_FULL_STR).format(DateUtil.getJavaDate(cell.getNumericCellValue()));
                } else { // 纯数字
                    return cell.getNumericCellValue();
                }
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case Cell.CELL_TYPE_ERROR:
                return cell.getErrorCellValue();
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();
            case Cell.CELL_TYPE_BLANK:
                break;
            default:
                return null;
        }
        return null;
    }

    /**
     * 获取单元数值
     * @param cell
     * @return
     */
    protected final String getValue(Cell cell){
        String value = "";
        if(Cell.CELL_TYPE_NUMERIC==cell.getCellType()){
            if (DateUtil.isCellDateFormatted(cell)){
                return new SimpleDateFormat(DATE_FULL_STR).format(DateUtil.getJavaDate(cell.getNumericCellValue()));
            }else{
                Double big  = (Double)getCellValue(cell);
                value = big.toString();
            }
        }else if(Cell.CELL_TYPE_STRING==cell.getCellType()){
            value = (String) getCellValue(cell);
        }
        return value;
    }
}
