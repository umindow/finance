package com.supervise.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.supervise.cache.FiedRoleCache;
import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.common.SessionUser;
import com.supervise.config.role.DataType;
import com.supervise.config.role.DepType;
import com.supervise.controller.vo.DataSet;
import com.supervise.controller.vo.ViewVo;
import com.supervise.core.data.in.*;
import com.supervise.core.data.out.*;
import com.supervise.core.data.translate.GenericDataTranslate;
import com.supervise.dao.mysql.entity.*;
import com.supervise.dao.mysql.mapper.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    private final Logger logger = LoggerFactory.getLogger(DataController.class);

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

    @Getter
    private UserEntity userEntity;

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
            userEntity = SessionUser.INSTANCE.getCurrentUser();
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
                //只能清除字段，必留机构ID+项目id+batchdate
//                RecourseEntity recourseEntity = recourseMapper.selectByPrimaryKey(dataId);
//                recourseReset(recourseEntity);
//                recourseMapper.updateByPrimaryKeySelective(recourseEntity);
                //直接删除
                recourseMapper.deleteByPrimaryKey(dataId);
            } else if (DataType.SUPERVISE_REPLACE_DATA.getDataLevel() == dataType.intValue()) {
                //只能清除字段，必留机构ID+项目id+batchdate
//                CompensatoryEntity compensatoryEntity = compensatoryMapper.selectByPrimaryKey(dataId);
//                compensatoryReset(compensatoryEntity);
//                compensatoryMapper.updateByPrimaryKeySelective(compensatoryEntity);
                //直接删除
                compensatoryMapper.deleteByPrimaryKey(dataId);
            } else if (DataType.SUPERVISE_FEE_DATA.getDataLevel() == dataType.intValue()) {
                //只能清除字段，必留机构ID+项目id+batchdate
//                FeeAndRefundEntity feeAndRefundEntity = feeAndRefundMapper.selectByPrimaryKey(dataId);
//                feeAndRefundReset(feeAndRefundEntity);
//                feeAndRefundMapper.updateByPrimaryKeySelective(feeAndRefundEntity);
                //直接删除
                feeAndRefundMapper.deleteByPrimaryKey(dataId);
            } else if (DataType.SUPERVISE_BIZ_DATA.getDataLevel() == dataType.intValue()) {
                BusinessDataEntity businessDataEntity = businessDataMapper.selectByPrimaryKey(dataId);
                userEntity = SessionUser.INSTANCE.getCurrentUser();
                String depId = userEntity.getDepId();
                Long dep = -1L;
                if (StringUtils.isEmpty(depId)) {
                    dep = Long.parseLong(depId);
                }
                //如果是综合营运部，则可以直接删除该条记录；否则只能清除该部门所有权限字段的内容,并更新数据
                if (DepType.COMPREHENSIVE_DEP.getDepId() == dep || -1 == dep) {
                    businessDataMapper.deleteByPrimaryKey(dataId);
                    //同时删除还款、代偿、追偿、收退费信息，以机构ID+项目id+batchdate为删除条件
                    String batchDate = businessDataEntity.getBatchDate();
                    String orgId = businessDataEntity.getOrgId();
                    String projId = businessDataEntity.getProjId();
                    //删除收退费信息
                    Example example = new Example(FeeAndRefundEntity.class);
                    Example.Criteria fcriteria = example.createCriteria();
                    fcriteria.andEqualTo("batchDate", batchDate);
                    fcriteria.andEqualTo("orgId", orgId);
                    fcriteria.andEqualTo("projId", projId);
                    feeAndRefundMapper.deleteByExample(example);
                    example.clear();

                    //删除还款信息
                    example = new Example(RepaymentEntity.class);
                    Example.Criteria rcriteria = example.createCriteria();
                    rcriteria.andEqualTo("batchDate", batchDate);
                    rcriteria.andEqualTo("orgId", orgId);
                    rcriteria.andEqualTo("projId", projId);
                    repaymentMapper.deleteByExample(example);
                    example.clear();
                    //删除追偿信息
                    example = new Example(RecourseEntity.class);
                    Example.Criteria recriteria = example.createCriteria();
                    recriteria.andEqualTo("batchDate", batchDate);
                    recriteria.andEqualTo("orgId", orgId);
                    recriteria.andEqualTo("projId", projId);
                    recourseMapper.deleteByExample(example);
                    example.clear();
                    //删除代偿信息
                    example = new Example(CompensatoryEntity.class);
                    Example.Criteria ccriteria = example.createCriteria();
                    ccriteria.andEqualTo("batchDate", batchDate);
                    ccriteria.andEqualTo("orgId", orgId);
                    ccriteria.andEqualTo("projId", projId);
                    compensatoryMapper.deleteByExample(example);
                    example.clear();
                } else {
                    //否则清除有权限部分的字段，并更新数据库
                    Map<String, FiedRoleCache.DepRoleRef> filedRoles = FiedRoleCache.mapDepRoleRefs(DataType.SUPERVISE_BIZ_DATA.getDataLevel());
                    deleteBusinessDataEntity4Role(businessDataEntity, filedRoles, userEntity);
                    String dateStr = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(new Date());
                    Date newDate = DateUtils.String2Date(dateStr, Constants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
                    businessDataEntity.setUpdateDate(newDate);
                    businessDataMapper.updateByPrimaryKeySelective(businessDataEntity);
                }
            } else if (DataType.SUPERVISE_REBACK_DATA.getDataLevel() == dataType.intValue()) {
                //只能清除字段，必留机构ID+项目id+batchdate
//                RepaymentEntity repaymentEntity = repaymentMapper.selectByPrimaryKey(dataId);
//                repaymentReset(repaymentEntity);
//                repaymentMapper.updateByPrimaryKeySelective(repaymentEntity);
                //直接删除
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
            //需要根据机构ID+项目ID+批次 查询BUSINESS是否存在该项目，如果存在则更新，否则作为垃圾数据删除
            String batchDate = recourseEntity.getBatchDate();
            String orgId = recourseEntity.getOrgId();
            String projId = recourseEntity.getProjId();

            Example example = new Example(BusinessDataEntity.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("batchDate", batchDate);
            criteria.andEqualTo("orgId", orgId);
            criteria.andEqualTo("projId", projId);
            List<BusinessDataEntity> resList = this.businessDataMapper.selectByExample(example);

            //如果没有查询到数据，则说明项目不存在
            if (CollectionUtils.isEmpty(resList)) {
                recourseMapper.delete(recourseEntity);
                logger.info("delete recourse record : orgId:" + orgId + " projId:" + projId + " batchDate:" + batchDate);
                logger.info("reason:the project in business Not Existed");
            } else {
                recourseMapper.updateByPrimaryKeySelective(recourseEntity);
                logger.info("update recourse record : orgId:" + orgId + " projId:" + projId + " batchDate:" + batchDate);
            }
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
            //需要根据机构ID+项目ID+批次 查询BUSINESS是否存在该项目，如果存在则更新，否则作为垃圾数据删除
            String batchDate = compensatoryEntity.getBatchDate();
            String orgId = compensatoryEntity.getOrgId();
            String projId = compensatoryEntity.getProjId();

            Example example = new Example(BusinessDataEntity.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("batchDate", batchDate);
            criteria.andEqualTo("orgId", orgId);
            criteria.andEqualTo("projId", projId);
            List<BusinessDataEntity> resList = this.businessDataMapper.selectByExample(example);

            //如果没有查询到数据，则说明项目不存在
            if (CollectionUtils.isEmpty(resList)) {
                compensatoryMapper.delete(compensatoryEntity);
                logger.info("delete compensatory record : orgId:" + orgId + " projId:" + projId + " batchDate:" + batchDate);
                logger.info("reason:the project in business Not Existed");
            } else {
                compensatoryMapper.updateByPrimaryKeySelective(compensatoryEntity);
                logger.info("update compensatory record : orgId:" + orgId + " projId:" + projId + " batchDate:" + batchDate);
            }
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
            //需要根据机构ID+项目ID+批次 查询BUSINESS是否存在该项目，如果存在则更新，否则作为垃圾数据删除
            String batchDate = feeAndRefundEntity.getBatchDate();
            String orgId = feeAndRefundEntity.getOrgId();
            String projId = feeAndRefundEntity.getProjId();

            Example example = new Example(BusinessDataEntity.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("batchDate", batchDate);
            criteria.andEqualTo("orgId", orgId);
            criteria.andEqualTo("projId", projId);
            List<BusinessDataEntity> resList = this.businessDataMapper.selectByExample(example);

            //如果没有查询到数据，则说明项目不存在
            if (CollectionUtils.isEmpty(resList)) {
                feeAndRefundMapper.delete(feeAndRefundEntity);
                logger.info("delete feeAndRefund record : orgId:" + orgId + " projId:" + projId + " batchDate:" + batchDate);
                logger.info("reason:the project in business Not Existed");
            } else {
                feeAndRefundMapper.updateByPrimaryKeySelective(feeAndRefundEntity);
                logger.info("update feeAndRefund record : orgId:" + orgId + " projId:" + projId + " batchDate:" + batchDate);
            }

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
            //根据ID号查询全量的业务数据
            Long dataId = businessDataEntity.getId();

            BusinessDataEntity businessDataExist = businessDataMapper.selectByPrimaryKey(dataId);
            userEntity = SessionUser.INSTANCE.getCurrentUser();
            //根据权限设置更新的字段
            Map<String, FiedRoleCache.DepRoleRef> filedRoles = FiedRoleCache.mapDepRoleRefs(DataType.SUPERVISE_BIZ_DATA.getDataLevel());
            businessDataExist = updateBusinessDataEntity4Role(businessDataExist, businessDataEntity, filedRoles, userEntity);
            String dateStr = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(new Date());
            Date newDate = DateUtils.String2Date(dateStr, Constants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
            businessDataExist.setUpdateDate(newDate);
            businessDataMapper.updateByPrimaryKeySelective(businessDataExist);
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
            //需要根据机构ID+项目ID+批次 查询BUSINESS是否存在该项目，如果存在则更新，否则作为垃圾数据删除
            String batchDate = repaymentEntity.getBatchDate();
            String orgId = repaymentEntity.getOrgId();
            String projId = repaymentEntity.getProjId();

            Example example = new Example(BusinessDataEntity.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("batchDate", batchDate);
            criteria.andEqualTo("orgId", orgId);
            criteria.andEqualTo("projId", projId);
            List<BusinessDataEntity> resList = this.businessDataMapper.selectByExample(example);

            //如果没有查询到数据，则说明项目不存在
            if (CollectionUtils.isEmpty(resList)) {
                repaymentMapper.delete(repaymentEntity);
                logger.info("delete repayment record : orgId:" + orgId + " projId:" + projId + " batchDate:" + batchDate);
                logger.info("reason:the project in business Not Existed");
            } else {
                repaymentMapper.updateByPrimaryKeySelective(repaymentEntity);
                logger.info("update repayment record : orgId:" + orgId + " projId:" + projId + " batchDate:" + batchDate);
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 构建新的业务信息对象，把权限范围内的字段删除
     *
     * @param businessDataEntity
     * @param filedRoles
     * @param userEntity
     * @return BusinessDataEntity
     */
    private void deleteBusinessDataEntity4Role(BusinessDataEntity businessDataEntity, Map<String, FiedRoleCache.DepRoleRef> filedRoles, UserEntity userEntity) {
        //如果有该字段的权限，则清除
        //客户类型
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("client_type"))) {
            businessDataEntity.setClientType(Constants.NULLSTR);
        }
        //客户编码
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("client_id"))) {
            businessDataEntity.setClientId(Constants.NULLSTR);
        }
        //客户名称
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("client_name"))) {
            businessDataEntity.setClientName(Constants.NULLSTR);
        }
        //证件类型
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("id_card_type"))) {
            businessDataEntity.setIDCardType(Constants.NULLSTR);
        }
        //证件编码
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("id_card"))) {
            businessDataEntity.setIDCard(Constants.NULLSTR);
        }
        //所属行业编号（一级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("calling_first"))) {
            businessDataEntity.setCallingFirst(Constants.NULLSTR);
        }
        //所属行业编号（二级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("calling_second"))) {
            businessDataEntity.setCallingSecond(Constants.NULLSTR);
        }
        //所属地区编号（一级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("area_first"))) {
            businessDataEntity.setAreaFirst(Constants.NULLSTR);
        }
        //所属地区编号（二级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("area_second"))) {
            businessDataEntity.setAreaSecond(Constants.NULLSTR);
        }
        //所属地区编号（三级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("area_third"))) {
            businessDataEntity.setAreaThird(Constants.NULLSTR);
        }
        //客户规模编码
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("company_scale"))) {
            businessDataEntity.setCompanyScale(Constants.NULLSTR);
        }
        //是否涉农
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("is_farming"))) {
            businessDataEntity.setIsFarming(Constants.NULLSTR);
        }
        //业务类型
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("business_type"))) {
            businessDataEntity.setBusinessType(Constants.NULLSTR);
        }
        //合同金额
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("contract_money"))) {
            businessDataEntity.setContractMoney(null);
        }
        //已放款金额
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("loan_money"))) {
            businessDataEntity.setLoanMoney(null);
        }
        //贷款年利率
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("loan_rate"))) {
            businessDataEntity.setLoanRate(null);
        }
        //担保综合费率
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("assure_rate"))) {
            businessDataEntity.setAssureRate(null);
        }
        //放款日期
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("loan_date"))) {
            businessDataEntity.setLoanDate(null);
        }
        //合同截止日期
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("contract_end_date"))) {
            businessDataEntity.setContractEndDate(null);
        }
        //还款方式
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("repay_type_id"))) {
            businessDataEntity.setRepayType(Constants.NULLSTR);
        }
        //反担保措施
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("pledge_type"))) {
            businessDataEntity.setPledgeType(Constants.NULLSTR);
        }
        //反担保备注
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("approve_option"))) {
            businessDataEntity.setApproveOption(Constants.NULLSTR);
        }
        //银行授信记录标示ID
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("bank_credit_primary_id"))) {
            businessDataEntity.setBankCreditPrimaryId(Constants.NULLSTR);
        }
        //合作银行ID
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("co_bank_id"))) {
            businessDataEntity.setCoBankId(Constants.NULLSTR);
        }
        //项目状态
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("proj_status"))) {
            businessDataEntity.setProjSatus(Constants.NULLSTR);
        }
        //担保权人
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("assure_person"))) {
            businessDataEntity.setAssurePerson(Constants.NULLSTR);
        }
        //反担保物价值
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("pledge_worth"))) {
            businessDataEntity.setPledgeWorth(null);
        }
        //存单质押
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("is_impawn"))) {
            businessDataEntity.setIsImpawn(Constants.NULLSTR);
        }
        //受理时间
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("accept_date"))) {
            businessDataEntity.setAcceptDate(null);
        }
        //合同编码
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("contract_id"))) {
            businessDataEntity.setContractId(Constants.NULLSTR);
        }
        //客户存入保证金
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("client_bail_money"))) {
            businessDataEntity.setClientBailMoney(null);
        }
        //存出保证金
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("out_bail_money"))) {
            businessDataEntity.setOutBailMoney(null);
        }
        //资本属性
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("capital_belong"))) {
            businessDataEntity.setCapitalBelong(Constants.NULLSTR);
        }
        //项目结束时间
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("proj_end_date"))) {
            businessDataEntity.setProjEndDate(null);
        }
    }

    /**
     * 构建新的业务信息对象，把权限范围内的字段删除
     *
     * @param businessDataExist
     * @param businessDataUpdate
     * @param filedRoles
     * @param userEntity
     * @return BusinessDataEntity
     */
    private BusinessDataEntity updateBusinessDataEntity4Role(BusinessDataEntity businessDataExist, BusinessDataEntity businessDataUpdate, Map<String, FiedRoleCache.DepRoleRef> filedRoles, UserEntity userEntity) {
        //如果有该字段的权限，则清除
        //客户类型
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("client_type"))) {
            businessDataExist.setClientType(businessDataUpdate.getClientType());
        }
        //客户编码
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("client_id"))) {
            businessDataExist.setClientId(businessDataUpdate.getClientId());
        }
        //客户名称
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("client_name"))) {
            businessDataExist.setClientName(businessDataUpdate.getClientName());
        }
        //证件类型
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("id_card_type"))) {
            businessDataExist.setIDCardType(businessDataUpdate.getIDCardType());
        }
        //证件编码
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("id_card"))) {
            businessDataExist.setIDCard(businessDataUpdate.getIDCard());
        }
        //所属行业编号（一级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("calling_first"))) {
            businessDataExist.setCallingFirst(businessDataUpdate.getCallingFirst());
        }
        //所属行业编号（二级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("calling_second"))) {
            businessDataExist.setCallingSecond(businessDataUpdate.getCallingSecond());
        }
        //所属地区编号（一级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("area_first"))) {
            businessDataExist.setAreaFirst(businessDataUpdate.getAreaFirst());
        }
        //所属地区编号（二级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("area_second"))) {
            businessDataExist.setAreaSecond(businessDataUpdate.getAreaSecond());
        }
        //所属地区编号（三级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("area_third"))) {
            businessDataExist.setAreaThird(businessDataUpdate.getAreaThird());
        }
        //客户规模编码
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("company_scale"))) {
            businessDataExist.setCompanyScale(businessDataUpdate.getCompanyScale());
        }
        //是否涉农
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("is_farming"))) {
            businessDataExist.setIsFarming(businessDataUpdate.getIsFarming());
        }
        //业务类型
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("business_type"))) {
            businessDataExist.setBusinessType(businessDataUpdate.getBusinessType());
        }
        //合同金额
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("contract_money"))) {
            businessDataExist.setContractMoney(businessDataUpdate.getContractMoney());
        }
        //已放款金额
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("loan_money"))) {
            businessDataExist.setLoanMoney(businessDataUpdate.getLoanMoney());
        }
        //贷款年利率
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("loan_rate"))) {
            businessDataExist.setLoanRate(businessDataUpdate.getLoanRate());
        }
        //担保综合费率
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("assure_rate"))) {
            businessDataExist.setAssureRate(businessDataUpdate.getAssureRate());
        }
        //放款日期
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("loan_date"))) {
            businessDataExist.setLoanDate(businessDataUpdate.getLoanDate());
        }
        //合同截止日期
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("contract_end_date"))) {
            businessDataExist.setContractEndDate(businessDataUpdate.getContractEndDate());
        }
        //还款方式
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("repay_type_id"))) {
            businessDataExist.setRepayType(businessDataUpdate.getRepayType());
        }
        //反担保措施
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("pledge_type"))) {
            businessDataExist.setPledgeType(businessDataUpdate.getPledgeType());
        }
        //反担保备注
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("approve_option"))) {
            businessDataExist.setApproveOption(businessDataUpdate.getApproveOption());
        }
        //银行授信记录标示ID
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("bank_credit_primary_id"))) {
            businessDataExist.setBankCreditPrimaryId(businessDataUpdate.getBankCreditPrimaryId());
        }
        //合作银行ID
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("co_bank_id"))) {
            businessDataExist.setCoBankId(businessDataUpdate.getCoBankId());
        }
        //项目状态
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("proj_status"))) {
            businessDataExist.setProjSatus(businessDataUpdate.getProjSatus());
        }
        //担保权人
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("assure_person"))) {
            businessDataExist.setAssurePerson(businessDataUpdate.getAssurePerson());
        }
        //反担保物价值
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("pledge_worth"))) {
            businessDataExist.setPledgeWorth(businessDataUpdate.getPledgeWorth());
        }
        //存单质押
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("is_impawn"))) {
            businessDataExist.setIsImpawn(businessDataUpdate.getIsImpawn());
        }
        //受理时间
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("accept_date"))) {
            businessDataExist.setAcceptDate(businessDataUpdate.getAcceptDate());
        }
        //合同编码
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("contract_id"))) {
            businessDataExist.setContractId(businessDataUpdate.getContractId());
        }
        //客户存入保证金
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("client_bail_money"))) {
            businessDataExist.setClientBailMoney(businessDataUpdate.getClientBailMoney());
        }
        //存出保证金
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("out_bail_money"))) {
            businessDataExist.setOutBailMoney(businessDataUpdate.getOutBailMoney());
        }
        //资本属性
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("capital_belong"))) {
            businessDataExist.setCapitalBelong(businessDataUpdate.getCapitalBelong());
        }
        //项目结束时间
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("proj_end_date"))) {
            businessDataExist.setProjEndDate(businessDataUpdate.getProjEndDate());
        }
        return businessDataExist;
    }


    /**
     * 重置对象
     *
     * @param recourseEntity
     * @return
     */
    private void recourseReset(RecourseEntity recourseEntity) {
        recourseEntity.setReplevyMoney(null);
        recourseEntity.setSendStatus(Constants.DATA_READY_SEND);
        recourseEntity.setReplevyType(Constants.NULLSTR);
        recourseEntity.setReplevyDate(null);
        recourseEntity.setContractId(Constants.NULLSTR);
        recourseEntity.setUpdateDate(new Date());
    }

    /**
     * 重置对象
     *
     * @param compensatoryEntity
     * @return
     */
    private void compensatoryReset(CompensatoryEntity compensatoryEntity) {
        compensatoryEntity.setReplaceMoney(null);
        compensatoryEntity.setUpdateDate(new Date());
        compensatoryEntity.setContractId(Constants.NULLSTR);
        compensatoryEntity.setReplaceDate(null);
        compensatoryEntity.setSendStatus(Constants.DATA_READY_SEND);
    }

    /**
     * 重置对象
     *
     * @param feeAndRefundEntity
     * @return
     */
    private void feeAndRefundReset(FeeAndRefundEntity feeAndRefundEntity) {
        feeAndRefundEntity.setChargeMoney(null);
        feeAndRefundEntity.setSendStatus(Constants.DATA_READY_SEND);
        feeAndRefundEntity.setChargeTime(null);
        feeAndRefundEntity.setChargeWay(Constants.NULLSTR);
        feeAndRefundEntity.setChargeType(Constants.NULLSTR);
        feeAndRefundEntity.setContractId(Constants.NULLSTR);
        feeAndRefundEntity.setUpdateDate(new Date());
    }

    /**
     * 重置对象
     *
     * @param repaymentEntity
     * @return
     */
    private void repaymentReset(RepaymentEntity repaymentEntity) {
//        compensatoryEntity.setReplaceMoney(null);
//        compensatoryEntity.setUpdateDate(new Date());
//        compensatoryEntity.setContractId(Constants.NULLSTR);
//        compensatoryEntity.setReplaceDate(null);
//        compensatoryEntity.setSendStatus(Constants.DATA_READY_SEND);
//        feeAndRefundEntity.setChargeMoney(null);
//        feeAndRefundEntity.setSendStatus(Constants.DATA_READY_SEND);
//        feeAndRefundEntity.setChargeTime(null);
//        feeAndRefundEntity.setChargeWay(Constants.NULLSTR);
//        feeAndRefundEntity.setChargeType(Constants.NULLSTR);
//        feeAndRefundEntity.setContractId(Constants.NULLSTR);
//        feeAndRefundEntity.setUpdateDate(new Date());
        repaymentEntity.setSendStatus(Constants.DATA_READY_SEND);
        repaymentEntity.setUpdateDate(new Date());
        repaymentEntity.setContractId(Constants.NULLSTR);
        repaymentEntity.setPrincipal(null);
        repaymentEntity.setPunishMoney(null);
        repaymentEntity.setInterest(null);
        repaymentEntity.setRepayDate(null);
    }

//    private List<BankCreditVo> transfEntity2Vo(List<BankCreditEntity> entitis ){
//        List<BankCreditVo> vos = new ArrayList<BankCreditVo>();
//        if(!CollectionUtils.isEmpty(entitis)){
//            for(BankCreditEntity bankCreditEntity:entitis){
//                BankCreditVo bankCreditVo = new BankCreditVo();
//                bankCreditVo.setId(bankCreditEntity.getId());
//                bankCreditVo.setPage(bankCreditEntity.getPage());
//                bankCreditVo.setRows(bankCreditEntity.getRows());
//
//                bankCreditVo.setRemark(bankCreditEntity.getRemark());
//                bankCreditVo.setPrimaryId(bankCreditEntity.getPrimaryId());
//                bankCreditVo.setBailMoney(bankCreditEntity.getBailMoney());
//                bankCreditVo.setBailScale(bankCreditEntity.getBailScale());
//                bankCreditVo.setBankId(bankCreditEntity.getBankId());
//                bankCreditVo.setBatchDate(bankCreditEntity.getBatchDate());
//                bankCreditVo.setBlowupMulpitle(bankCreditEntity.getBlowupMulpitle());
//                bankCreditVo.setCreditMoney(bankCreditEntity.getCreditMoney());
//                bankCreditVo.setCreditTypeId(bankCreditEntity.getCreditTypeId());
//                bankCreditVo.setIsForCredit(bankCreditEntity.getIsForCredit());
//                bankCreditVo.setCreditStatus(bankCreditEntity.getCreditStatus());
//                bankCreditVo.setItemLean(bankCreditEntity.getItemLean());
//                bankCreditVo.setMainBankId(bankCreditEntity.getMainBankId());
//                bankCreditVo.setLeaveMoney(bankCreditEntity.getLeaveMoney());
//                bankCreditVo.setOrgId(bankCreditEntity.getOrgId());
//                bankCreditVo.setSingleMoneyLimit(bankCreditEntity.getSingleMoneyLimit());
//
//                Date startDate = bankCreditEntity.getCreditStartDate();
//                Date endDate = bankCreditEntity.getCreditEndDate();
//                String startStr = new SimpleDateFormat(Constants.YYYY_MM_DD).format(startDate);
//                String endStr = new SimpleDateFormat(Constants.YYYY_MM_DD).format(endDate);
//
//                bankCreditVo.setCreditStartDate(startStr);
//                bankCreditVo.setCreditEndDate(endStr);
//
//                vos.add(bankCreditVo);
//            }
//
//        }
//        return vos;
//    }

}
