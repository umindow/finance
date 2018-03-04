package com.supervise.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.supervise.common.Constants;
import com.supervise.common.SessionUser;
import com.supervise.config.role.DataType;
import com.supervise.controller.translate.GenericDataTranslate;
import com.supervise.controller.vo.DataSet;
import com.supervise.core.data.in.BankCreditDataImport;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.mapper.BankCreditMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by xishui.hb on 2018/1/30 下午5:30.
 * 数据操作controller，查询/编辑/trigger推送
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@RestController
@RequestMapping("/data")
public class DataController {
    @Autowired
    private BankCreditMapper bankCreditMapper;
    @Autowired
    private BankCreditDataImport bankCreditDataImport;

    @RequestMapping(value = "bankCreditList", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(value = "p", required = false) Integer pageNum, @RequestParam(value = "date", required = false) String date) {
        Page<BankCreditEntity> pager = PageHelper.startPage(pageNum == null ? 1 : pageNum, Constants.PAGE_SIZE);
        ModelAndView view = new ModelAndView("pages/data/genericDataList", "list", pager);
        Example entityExample = new Example(BankCreditEntity.class);
        Example.Criteria criteria = entityExample.createCriteria();
        if (StringUtils.isEmpty(date)) {
            date = new SimpleDateFormat(Constants.YYYY_MM_DD).format(new Date());
        }
        criteria.andEqualTo("batchDate",date);
        List<BankCreditEntity> bankCreditEntities = bankCreditMapper.selectByExample(entityExample);
        if (CollectionUtils.isEmpty(bankCreditEntities)) {
            pager.setPageNum(1);
            pager.setPages(1);
        }
        DataSet dataSet = new GenericDataTranslate<BankCreditEntity>().translate(bankCreditEntities, DataType.SUPERVISE_BANK_DATA.getDataLevel(), SessionUser.INSTANCE.getCurrentUser());
        view.addObject("dataSet",dataSet);
        view.addObject("date",date);
        view.addObject("moduleName",DataType.SUPERVISE_BANK_DATA.getDataName());
        return view;
    }

    /**
     * 统一的数据导入入口
     *
     * @param file
     * @param type
     * @return
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @Transactional
    public String dataImport(@RequestParam("file") MultipartFile file, @RequestParam("type") String type) {
        if (null == file) {
            return "文件为空";
        }
        if (DataImportType.BANKCREDIT.getType().equals(type)) {
            try {
                bankCreditDataImport.in(file);
            } catch (Exception e) {
                e.printStackTrace();
                return "导入失败:" + e.getMessage();
            }
        }
        return "导入成功";
    }


    public enum DataImportType {
        BANKCREDIT("bankCredit");//银行授信数据
        @Getter
        @Setter
        private String type;

        DataImportType(String type) {
            this.type = type;
        }
    }
}
