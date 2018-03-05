package com.supervise.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.common.SessionUser;
import com.supervise.config.role.DataType;
import com.supervise.controller.vo.DataSet;
import com.supervise.controller.vo.ViewVo;
import com.supervise.core.data.in.*;
import com.supervise.core.data.out.*;
import com.supervise.core.data.translate.GenericDataTranslate;
import com.supervise.dao.mysql.entity.*;
import com.supervise.dao.mysql.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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
    private RepaymentMapper repaymentMapper;
    @Autowired
    private BusinessDataMapper businessDataMapper;
    @Autowired
    private CompensatoryMapper compensatoryMapper;
    @Autowired
    private FeeAndRefundMapper feeAndRefundMapper;
    @Autowired
    private RecourseMapper recourseMapper;
    @Autowired
    private BankCreditDataImport bankCreditDataImport;
    @Autowired
    private BusinessDataImport businessDataImport;
    @Autowired
    private CompensatoryDataImport compensatoryDataImport;
    @Autowired
    private FeeAndRefundDataImport feeAndRefundDataImport;
    @Autowired
    private RecourseDataImport recourseDataImport;
    @Autowired
    private RepaymentDataImport repaymentDataImport;
    @Autowired
    private BankCreditOutport bankCreditOutport;
    @Autowired
    private BusinessOutport businessOutport;
    @Autowired
    private CompensatoryOutport compensatoryOutport;
    @Autowired
    private FeeAndRefundOutport feeAndRefundOutport;
    @Autowired
    private RecourseOutport recourseOutport;
    @Autowired
    private RepaymentOutport repaymentOutport;

    @RequestMapping(value = "genericDataList", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(value = "p", required = false) Integer pageNum, @RequestParam(value = "date", required = false) String date, @RequestParam(value = "dataType", required = true) Integer dataType) {
        if (date == null || "".equals(date)) {
            date = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
        }
        if (DataType.SUPERVISE_BANK_DATA.getDataLevel() == dataType.intValue()) {
            return new BankCreditDataList().dataList(pageNum, date, dataType);
        } else if (DataType.SUPERVISE_TRACE_DATA.getDataLevel() == dataType.intValue()) {
            return new TraceDataList().dataList(pageNum, date, dataType);
        } else if (DataType.SUPERVISE_REPLACE_DATA.getDataLevel() == dataType.intValue()) {
            return new ReplaceDataList().dataList(pageNum, date, dataType);
        } else if (DataType.SUPERVISE_FEE_DATA.getDataLevel() == dataType.intValue()) {
            return new RefundAndFeeCreditDataList().dataList(pageNum, date, dataType);
        } else if (DataType.SUPERVISE_BIZ_DATA.getDataLevel() == dataType.intValue()) {
            return new BusinessDataList().dataList(pageNum, date, dataType);
        } else if (DataType.SUPERVISE_REBACK_DATA.getDataLevel() == dataType.intValue()) {
            return new RepaymentDataList().dataList(pageNum, date, dataType);
        } else {
            return null;
        }
    }

    protected interface SimpleDataList {
        ModelAndView dataList(Integer pageNum, String date, Integer dataType);
    }

    protected class BankCreditDataList implements SimpleDataList {
        @Override
        public ModelAndView dataList(Integer pageNum, String date, Integer dataType) {
            Page<BankCreditEntity> pager = PageHelper.startPage(pageNum == null ? 1 : pageNum, Constants.PAGE_SIZE);
            ModelAndView view = new ModelAndView("pages/data/genericDataList", "list", pager);
            Example entityExample = new Example(BankCreditEntity.class);
            Example.Criteria criteria = entityExample.createCriteria();
            if (null != date && !"".equals(date)) {
                criteria.andEqualTo("batchDate", date);
            }
            List<BankCreditEntity> bankCreditEntities = bankCreditMapper.selectByExample(entityExample);
            if (CollectionUtils.isEmpty(bankCreditEntities)) {
                pager.setPageNum(1);
                pager.setPages(1);
            }
            DataSet dataSet = new GenericDataTranslate<BankCreditEntity>().translate(bankCreditEntities, DataType.SUPERVISE_BANK_DATA.getDataLevel(), SessionUser.INSTANCE.getCurrentUser());
            view.addObject("dataSet", dataSet);
            ViewVo viewVo = new ViewVo();
            viewVo.setDataType(dataType);
            viewVo.setDate(date);
            viewVo.setDeleteUrl("/data/deleteData");
            viewVo.setModuleName(DataType.SUPERVISE_BANK_DATA.getDataName());
            viewVo.setModifyUrl("/data/bankCreditModify");
            view.addObject("view", viewVo);
            return view;
        }
    }

    //追偿
    protected class TraceDataList implements SimpleDataList {
        @Override
        public ModelAndView dataList(Integer pageNum, String date, Integer dataType) {
            Page<RecourseEntity> pager = PageHelper.startPage(pageNum == null ? 1 : pageNum, Constants.PAGE_SIZE);
            ModelAndView view = new ModelAndView("pages/data/genericDataList", "list", pager);
            Example entityExample = new Example(RecourseEntity.class);
            Example.Criteria criteria = entityExample.createCriteria();
            if (null != date && !"".equals(date)) {
                criteria.andEqualTo("batchDate", date);
            }
            List<RecourseEntity> recourseEntities = recourseMapper.selectByExample(entityExample);
            if (CollectionUtils.isEmpty(recourseEntities)) {
                pager.setPageNum(1);
                pager.setPages(1);
            }
            DataSet dataSet = new GenericDataTranslate<RecourseEntity>().translate(recourseEntities, DataType.SUPERVISE_TRACE_DATA.getDataLevel(), SessionUser.INSTANCE.getCurrentUser());
            view.addObject("dataSet", dataSet);
            ViewVo viewVo = new ViewVo();
            viewVo.setDataType(dataType);
            viewVo.setDate(date);
            viewVo.setDeleteUrl("/data/deleteData");
            viewVo.setModuleName(DataType.SUPERVISE_TRACE_DATA.getDataName());
            viewVo.setModifyUrl("/data/recourseModify");
            view.addObject("view", viewVo);
            return view;
        }
    }

    //代偿
    protected class ReplaceDataList implements SimpleDataList {
        @Override
        public ModelAndView dataList(Integer pageNum, String date, Integer dataType) {
            Page<CompensatoryEntity> pager = PageHelper.startPage(pageNum == null ? 1 : pageNum, Constants.PAGE_SIZE);
            ModelAndView view = new ModelAndView("pages/data/genericDataList", "list", pager);
            Example entityExample = new Example(CompensatoryEntity.class);
            Example.Criteria criteria = entityExample.createCriteria();
            if (null != date && !"".equals(date)) {
                criteria.andEqualTo("batchDate", date);
            }
            List<CompensatoryEntity> compensatoryEntities = compensatoryMapper.selectByExample(entityExample);
            if (CollectionUtils.isEmpty(compensatoryEntities)) {
                pager.setPageNum(1);
                pager.setPages(1);
            }
            DataSet dataSet = new GenericDataTranslate<CompensatoryEntity>().translate(compensatoryEntities, DataType.SUPERVISE_REPLACE_DATA.getDataLevel(), SessionUser.INSTANCE.getCurrentUser());
            view.addObject("dataSet", dataSet);
            ViewVo viewVo = new ViewVo();
            viewVo.setDataType(dataType);
            viewVo.setDate(date);
            viewVo.setDeleteUrl("/data/deleteData");
            viewVo.setModuleName(DataType.SUPERVISE_REPLACE_DATA.getDataName());
            viewVo.setModifyUrl("/data/componsatoryModify");
            view.addObject("view", viewVo);
            return view;
        }
    }

    protected class RefundAndFeeCreditDataList implements SimpleDataList {
        @Override
        public ModelAndView dataList(Integer pageNum, String date, Integer dataType) {
            Page<FeeAndRefundEntity> pager = PageHelper.startPage(pageNum == null ? 1 : pageNum, Constants.PAGE_SIZE);
            ModelAndView view = new ModelAndView("pages/data/genericDataList", "list", pager);
            Example entityExample = new Example(FeeAndRefundEntity.class);
            Example.Criteria criteria = entityExample.createCriteria();
            if (null != date && !"".equals(date)) {
                criteria.andEqualTo("batchDate", date);
            }
            List<FeeAndRefundEntity> feeAndRefundEntities = feeAndRefundMapper.selectByExample(entityExample);
            if (CollectionUtils.isEmpty(feeAndRefundEntities)) {
                pager.setPageNum(1);
                pager.setPages(1);
            }
            DataSet dataSet = new GenericDataTranslate<FeeAndRefundEntity>().translate(feeAndRefundEntities, DataType.SUPERVISE_FEE_DATA.getDataLevel(), SessionUser.INSTANCE.getCurrentUser());
            view.addObject("dataSet", dataSet);
            ViewVo viewVo = new ViewVo();
            viewVo.setDataType(dataType);
            viewVo.setDate(date);
            viewVo.setDeleteUrl("/data/deleteData");
            viewVo.setModuleName(DataType.SUPERVISE_FEE_DATA.getDataName());
            viewVo.setModifyUrl("/data/feeAndRefundModfiy");
            view.addObject("view", viewVo);
            return view;
        }
    }

    protected class BusinessDataList implements SimpleDataList {
        @Override
        public ModelAndView dataList(Integer pageNum, String date, Integer dataType) {
            Page<BusinessDataEntity> pager = PageHelper.startPage(pageNum == null ? 1 : pageNum, Constants.PAGE_SIZE);
            ModelAndView view = new ModelAndView("pages/data/genericDataList", "list", pager);
            Example entityExample = new Example(BankCreditEntity.class);
            Example.Criteria criteria = entityExample.createCriteria();
            if (null != date && !"".equals(date)) {
                criteria.andEqualTo("batchDate", date);
            }
            List<BusinessDataEntity> businessDataEntities = businessDataMapper.selectByExample(entityExample);
            if (CollectionUtils.isEmpty(businessDataEntities)) {
                pager.setPageNum(1);
                pager.setPages(1);
            }
            DataSet dataSet = new GenericDataTranslate<BusinessDataEntity>().translate(businessDataEntities, DataType.SUPERVISE_BIZ_DATA.getDataLevel(), SessionUser.INSTANCE.getCurrentUser());
            view.addObject("dataSet", dataSet);
            ViewVo viewVo = new ViewVo();
            viewVo.setDataType(dataType);
            viewVo.setDate(date);
            viewVo.setDeleteUrl("/data/deleteData");
            viewVo.setModuleName(DataType.SUPERVISE_BIZ_DATA.getDataName());
            viewVo.setModifyUrl("/data/businessModify");
            view.addObject("view", viewVo);
            return view;
        }
    }

    protected class RepaymentDataList implements SimpleDataList {
        @Override
        public ModelAndView dataList(Integer pageNum, String date, Integer dataType) {
            Page<RepaymentEntity> pager = PageHelper.startPage(pageNum == null ? 1 : pageNum, Constants.PAGE_SIZE);
            ModelAndView view = new ModelAndView("pages/data/genericDataList", "list", pager);
            Example entityExample = new Example(RepaymentEntity.class);
            Example.Criteria criteria = entityExample.createCriteria();
            if (null != date && !"".equals(date)) {
                criteria.andEqualTo("batchDate", date);
            }
            List<RepaymentEntity> repaymentEntities = repaymentMapper.selectByExample(entityExample);
            if (CollectionUtils.isEmpty(repaymentEntities)) {
                pager.setPageNum(1);
                pager.setPages(1);
            }
            DataSet dataSet = new GenericDataTranslate<RepaymentEntity>().translate(repaymentEntities, DataType.SUPERVISE_REBACK_DATA.getDataLevel(), SessionUser.INSTANCE.getCurrentUser());
            view.addObject("dataSet", dataSet);
            ViewVo viewVo = new ViewVo();
            viewVo.setDataType(dataType);
            viewVo.setDate(date);
            viewVo.setDeleteUrl("/data/deleteData");
            viewVo.setModuleName(DataType.SUPERVISE_REBACK_DATA.getDataName());
            viewVo.setModifyUrl("/data/repaymentModify");
            view.addObject("view", viewVo);
            return view;
        }
    }

    /**
     * 统一的数据导入入口
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @Transactional
    public String dataImport(@RequestParam("file") MultipartFile file, @RequestParam("type") Integer type) {
        if (null == file) {
            return "文件为空";
        }
        try {
            if (DataType.SUPERVISE_BANK_DATA.getDataLevel() == type) {
                bankCreditDataImport.in(file);//银行授信
            }
            if (DataType.SUPERVISE_TRACE_DATA.getDataLevel() == type) {
                recourseDataImport.in(file);//追偿
            }
            if (DataType.SUPERVISE_REBACK_DATA.getDataLevel() == type) {
                repaymentDataImport.in(file);//退款
            }
            if (DataType.SUPERVISE_REPLACE_DATA.getDataLevel() == type) {
                compensatoryDataImport.in(file);//追偿
            }
            if (DataType.SUPERVISE_FEE_DATA.getDataLevel() == type) {
                feeAndRefundDataImport.in(file);//退费
            }
            if (DataType.SUPERVISE_BIZ_DATA.getDataLevel() == type) {
                businessDataImport.in(file);//系统业务
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return "导入成功";
    }

    @ResponseBody
    @RequestMapping("/outport")
    public String dataOutport(@RequestParam(value = "type", required = false) Integer type, @RequestParam(value = "date", required = false) String date, HttpServletResponse response) {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        try {
            String fileName = DataType.typeOfType(type).getDataEnName() + ".xls";
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            UserEntity userEntity = SessionUser.INSTANCE.getCurrentUser();
            if (DataType.SUPERVISE_BANK_DATA.getDataLevel() == type) {
                bankCreditOutport.export(servletOutputStream, DataType.SUPERVISE_BANK_DATA, date, userEntity);
            }
            if (DataType.SUPERVISE_TRACE_DATA.getDataLevel() == type) {
                recourseOutport.export(servletOutputStream, DataType.SUPERVISE_TRACE_DATA, date, userEntity);
                //追偿
            }
            if (DataType.SUPERVISE_REBACK_DATA.getDataLevel() == type) {
                repaymentOutport.export(servletOutputStream, DataType.SUPERVISE_REBACK_DATA, date, userEntity);
            }
            if (DataType.SUPERVISE_REPLACE_DATA.getDataLevel() == type) {
                compensatoryOutport.export(servletOutputStream, DataType.SUPERVISE_REPLACE_DATA, date, userEntity);
            }
            if (DataType.SUPERVISE_FEE_DATA.getDataLevel() == type) {
                feeAndRefundOutport.export(servletOutputStream, DataType.SUPERVISE_FEE_DATA, date, userEntity);
            }
            if (DataType.SUPERVISE_BIZ_DATA.getDataLevel() == type) {
                businessOutport.export(servletOutputStream, DataType.SUPERVISE_BIZ_DATA, date, userEntity);
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping("/deleteData")
    public String deleteData(@RequestParam(value = "dataId", required = false) Long dataId, @RequestParam(value = "dataType", required = false) Integer dataType) {
        try {
            if (DataType.SUPERVISE_BANK_DATA.getDataLevel() == dataType.intValue()) {
                bankCreditMapper.deleteByPrimaryKey(dataId);
            } else if (DataType.SUPERVISE_TRACE_DATA.getDataLevel() == dataType.intValue()) {
                recourseMapper.deleteByPrimaryKey(dataId);
            } else if (DataType.SUPERVISE_REPLACE_DATA.getDataLevel() == dataType.intValue()) {
                compensatoryMapper.deleteByPrimaryKey(dataId);
            } else if (DataType.SUPERVISE_FEE_DATA.getDataLevel() == dataType.intValue()) {
                feeAndRefundMapper.deleteByPrimaryKey(dataId);
            } else if (DataType.SUPERVISE_BIZ_DATA.getDataLevel() == dataType.intValue()) {
                businessDataMapper.deleteByPrimaryKey(dataId);
            } else if (DataType.SUPERVISE_REBACK_DATA.getDataLevel() == dataType.intValue()) {
                repaymentMapper.deleteByPrimaryKey(dataId);
            } else {
                return "数据类型不存在";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return "succcess";
    }

    @ResponseBody
    @RequestMapping(value = "/bankCreditModify", method = RequestMethod.POST)
    public boolean bankCreditModify(BankCreditEntity bankCreditEntity) {
        try {
            if (null == bankCreditEntity || bankCreditEntity.getId() == null) {
                return false;
            }
            bankCreditMapper.updateByPrimaryKey(bankCreditEntity);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @ResponseBody
    @RequestMapping(value = "/recourseModify", method = RequestMethod.POST)
    public boolean recourseModify(RecourseEntity recourseEntity) {
        try {
            if (null == recourseEntity || recourseEntity.getId() == null) {
                return false;
            }
            recourseMapper.updateByPrimaryKey(recourseEntity);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @ResponseBody
    @RequestMapping(value = "/componsatoryModify", method = RequestMethod.POST)
    public boolean componsatoryModify(CompensatoryEntity compensatoryEntity) {
        try {
            if (null == compensatoryEntity || compensatoryEntity.getId() == null) {
                return false;
            }
            compensatoryMapper.updateByPrimaryKey(compensatoryEntity);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @ResponseBody
    @RequestMapping(value = "/feeAndRefundModfiy", method = RequestMethod.POST)
    public boolean feeAndRefundModfiy(FeeAndRefundEntity feeAndRefundEntity) {
        try {
            if (null == feeAndRefundEntity || feeAndRefundEntity.getId() == null) {
                return false;
            }
            feeAndRefundMapper.updateByPrimaryKey(feeAndRefundEntity);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @ResponseBody
    @RequestMapping(value = "/businessModify", method = RequestMethod.POST)
    public boolean businessModify(BusinessDataEntity businessDataEntity) {
        try {
            if (null == businessDataEntity || businessDataEntity.getId() == null) {
                return false;
            }
            businessDataMapper.updateByPrimaryKey(businessDataEntity);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @ResponseBody
    @RequestMapping(value = "/repaymentModify", method = RequestMethod.POST)
    public boolean repaymentModify(RepaymentEntity repaymentEntity) {
        try {
            if (null == repaymentEntity || repaymentEntity.getId() == null) {
                return false;
            }
            repaymentMapper.updateByPrimaryKey(repaymentEntity);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
