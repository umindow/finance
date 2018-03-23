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
import com.supervise.dao.mysql.middleDao.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private BankCreditDao bankCreditDao;
    @Autowired
    private BusinessDataDao businessDataDao;
    @Autowired
    private RepaymentDao repaymentDao;
    @Autowired
    private CompensatoryDao compensatoryDao;
    @Autowired
    private FeeAndRefundDao feeAndRefundDao;
    @Autowired
    private RecourseDao recourseDao;
//    @Autowired
//    private BankCreditMapper bankCreditMapper;
//    @Autowired
//    private RepaymentMapper repaymentMapper;
//    @Autowired
//    private BusinessDataMapper businessDataMapper;
//    @Autowired
//    private CompensatoryMapper compensatoryMapper;
//    @Autowired
//    private FeeAndRefundMapper feeAndRefundMapper;
//    @Autowired
//    private RecourseMapper recourseMapper;
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
    public ModelAndView list(@RequestParam(value = "p", required = false) Integer pageNum,
                             @RequestParam(value = "batchDate", required = false) String batchDate,
                             @RequestParam(value = "dataType", required = true) Integer dataType,
                             @RequestParam(value = "creditStartDate", required = false) String creditStartDate,
                             @RequestParam(value = "creditEndDate", required = false) String creditEndDate,
                             @RequestParam(value = "projId", required = false) String projId,
                             @RequestParam(value = "clientName", required = false) String clientName) {
        if (batchDate == null || "".equals(batchDate)) {
            batchDate = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
        }
        ViewVo viewVo = new ViewVo();
        viewVo.setBatchDate(batchDate);
        viewVo.setClientName(clientName);
        viewVo.setCreditStartDate(creditStartDate);
        viewVo.setCreditEndDate(creditEndDate);
        viewVo.setProjId(projId);
        viewVo.setDataType(dataType);
        if(dataType == 200){
            viewVo.setCreditType(true);
        }else{
            viewVo.setCreditType(false);
        }
        if (DataType.SUPERVISE_BANK_DATA.getDataLevel() == dataType.intValue()) {
            return new BankCreditDataList().dataList(pageNum, viewVo, dataType);
        } else if (DataType.SUPERVISE_TRACE_DATA.getDataLevel() == dataType.intValue()) {
            return new TraceDataList().dataList(pageNum, viewVo, dataType);
        } else if (DataType.SUPERVISE_REPLACE_DATA.getDataLevel() == dataType.intValue()) {
            return new ReplaceDataList().dataList(pageNum, viewVo, dataType);
        } else if (DataType.SUPERVISE_FEE_DATA.getDataLevel() == dataType.intValue()) {
            return new FeeAndRefundDataList().dataList(pageNum, viewVo, dataType);
        } else if (DataType.SUPERVISE_BIZ_DATA.getDataLevel() == dataType.intValue()) {
            return new BusinessDataList().dataList(pageNum, viewVo, dataType);
        } else if (DataType.SUPERVISE_REBACK_DATA.getDataLevel() == dataType.intValue()) {
            return new RepaymentDataList().dataList(pageNum, viewVo, dataType);
        } else {
            return null;
        }
    }

    protected interface SimpleDataList {
        ModelAndView dataList(Integer pageNum, ViewVo viewVo, Integer dataType);
    }

    protected class BankCreditDataList implements SimpleDataList {
        @Override
        public ModelAndView dataList(Integer pageNum, ViewVo viewVo, Integer dataType) {
            Page<BankCreditEntity> pager = PageHelper.startPage(pageNum == null ? 1 : pageNum, Constants.PAGE_SIZE);
            ModelAndView view = new ModelAndView("pages/data/genericDataList", "list", pager);
            List<BankCreditEntity> bankCreditEntities = bankCreditDao.queryBankCreditByCondition(viewVo.getBatchDate(),viewVo.getCreditStartDate(),viewVo.getCreditEndDate());
            if (CollectionUtils.isEmpty(bankCreditEntities)) {
                pager.setPageNum(1);
                pager.setPages(1);
            }
            DataSet dataSet = new GenericDataTranslate<BankCreditEntity>().translate(bankCreditEntities, DataType.SUPERVISE_BANK_DATA.getDataLevel(), SessionUser.INSTANCE.getCurrentUser());
            view.addObject("dataSet", dataSet);
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
        public ModelAndView dataList(Integer pageNum, ViewVo viewVo, Integer dataType) {
            Page<RecourseEntity> pager = PageHelper.startPage(pageNum == null ? 1 : pageNum, Constants.PAGE_SIZE);
            ModelAndView view = new ModelAndView("pages/data/genericDataList", "list", pager);
            List<RecourseEntity> recourseEntities = recourseDao.queryRecourseByCondition(viewVo.getBatchDate(),viewVo.getProjId());
            List<RecourseEntity> viewDataEntities =  new ArrayList<RecourseEntity>();
            if (CollectionUtils.isEmpty(recourseEntities)) {
                pager.setPageNum(1);
                pager.setPages(1);
            }else{
                //去业务表中获取客户编号以及客户名称
                for(RecourseEntity recourseEntity:recourseEntities){
                    String projId = recourseEntity.getProjId();
                    //String orgId  = recourseEntity.getOrgId();
                    List<BusinessDataEntity> businessDataEntities = businessDataDao.queryBusinessDataByExProj(viewVo.getBatchDate(), projId, viewVo.getClientName());
                    if(!CollectionUtils.isEmpty(businessDataEntities)){
                        BusinessDataEntity businessDataEntity = businessDataEntities.get(0);
                        String clientId = businessDataEntity.getClientId();
                        String clientName = businessDataEntity.getClientName();
                        recourseEntity.setClientId(clientId);
                        recourseEntity.setClientName(clientName);
                        viewDataEntities.add(recourseEntity);
                    }else{
                        //如果没有在业务表中查询到项目编号，则视为脏数据，删除
                        List<BusinessDataEntity> exentities = businessDataDao.queryBusinessDataByExProj(viewVo.getBatchDate(), projId, "");
                        if(CollectionUtils.isEmpty(exentities)){
                            recourseDao.deleteRecourseByID(recourseEntity.getId());
                        }

                    }

                }
            }
            DataSet dataSet = new GenericDataTranslate<RecourseEntity>().translate(viewDataEntities, DataType.SUPERVISE_TRACE_DATA.getDataLevel(), SessionUser.INSTANCE.getCurrentUser());
            view.addObject("dataSet", dataSet);
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
        public ModelAndView dataList(Integer pageNum, ViewVo viewVo, Integer dataType) {
            Page<CompensatoryEntity> pager = PageHelper.startPage(pageNum == null ? 1 : pageNum, Constants.PAGE_SIZE);
            ModelAndView view = new ModelAndView("pages/data/genericDataList", "list", pager);
            List<CompensatoryEntity> compensatoryEntities = compensatoryDao.queryCompensatoryByCondition(viewVo.getBatchDate(), viewVo.getProjId());
            List<CompensatoryEntity> viewDataEntities =  new ArrayList<CompensatoryEntity>();
            if (CollectionUtils.isEmpty(compensatoryEntities)) {
                pager.setPageNum(1);
                pager.setPages(1);
            }else{
                //去业务表中获取客户编号以及客户名称
                for(CompensatoryEntity compensatoryEntity:compensatoryEntities){
                    String projId = compensatoryEntity.getProjId();
                    //String orgId  = compensatoryEntity.getOrgId();
                    List<BusinessDataEntity> businessDataEntities = businessDataDao.queryBusinessDataByExProj(viewVo.getBatchDate(), projId, viewVo.getClientName());
                    if(!CollectionUtils.isEmpty(businessDataEntities)){
                        BusinessDataEntity businessDataEntity = businessDataEntities.get(0);
                        String clientId = businessDataEntity.getClientId();
                        String clientName = businessDataEntity.getClientName();
                        compensatoryEntity.setClientId(clientId);
                        compensatoryEntity.setClientName(clientName);
                        viewDataEntities.add(compensatoryEntity);
                    }else{
                        //如果没有在业务表中查询到项目编号，则视为脏数据，删除
                        List<BusinessDataEntity> exentities = businessDataDao.queryBusinessDataByExProj(viewVo.getBatchDate(), projId, "");
                        if(CollectionUtils.isEmpty(exentities)){
                            compensatoryDao.deleteCompensatoryByID(compensatoryEntity.getId());
                        }
                    }

                }
            }
            DataSet dataSet = new GenericDataTranslate<CompensatoryEntity>().translate(viewDataEntities, DataType.SUPERVISE_REPLACE_DATA.getDataLevel(), SessionUser.INSTANCE.getCurrentUser());
            view.addObject("dataSet", dataSet);
            viewVo.setDeleteUrl("/data/deleteData");
            viewVo.setModuleName(DataType.SUPERVISE_REPLACE_DATA.getDataName());
            viewVo.setModifyUrl("/data/componsatoryModify");
            view.addObject("view", viewVo);
            return view;
        }
    }

    protected class FeeAndRefundDataList implements SimpleDataList {
        @Override
        public ModelAndView dataList(Integer pageNum, ViewVo viewVo, Integer dataType) {
            Page<FeeAndRefundEntity> pager = PageHelper.startPage(pageNum == null ? 1 : pageNum, Constants.PAGE_SIZE);
            ModelAndView view = new ModelAndView("pages/data/genericDataList", "list", pager);
            List<FeeAndRefundEntity> feeAndRefundEntities = feeAndRefundDao.queryFeeAndRefundByCondition(viewVo.getBatchDate(), viewVo.getProjId());
            List<FeeAndRefundEntity> viewDataEntities =  new ArrayList<FeeAndRefundEntity>();
            if (CollectionUtils.isEmpty(feeAndRefundEntities)) {
                pager.setPageNum(1);
                pager.setPages(1);
            }else{
                //去业务表中获取客户编号以及客户名称
                for(FeeAndRefundEntity feeAndRefundEntity:feeAndRefundEntities){
                    String projId = feeAndRefundEntity.getProjId();
                    // String orgId  = feeAndRefundEntity.getOrgId();
                    List<BusinessDataEntity> businessDataEntities = businessDataDao.queryBusinessDataByExProj(viewVo.getBatchDate(), projId, viewVo.getClientName());
                    if(!CollectionUtils.isEmpty(businessDataEntities)){
                        BusinessDataEntity businessDataEntity = businessDataEntities.get(0);
                        String clientId = businessDataEntity.getClientId();
                        String clientName = businessDataEntity.getClientName();
                        feeAndRefundEntity.setClientId(clientId);
                        feeAndRefundEntity.setClientName(clientName);
                        viewDataEntities.add(feeAndRefundEntity);
                    }else{
                        //如果没有在业务表中查询到项目编号，则视为脏数据，删除
                        List<BusinessDataEntity> exentities = businessDataDao.queryBusinessDataByExProj(viewVo.getBatchDate(), projId, "");
                        if(CollectionUtils.isEmpty(exentities)){
                            feeAndRefundDao.deleteFeeAndRefundByID(feeAndRefundEntity.getId());
                        }
                    }

                }
            }
            DataSet dataSet = new GenericDataTranslate<FeeAndRefundEntity>().translate(viewDataEntities, DataType.SUPERVISE_FEE_DATA.getDataLevel(), SessionUser.INSTANCE.getCurrentUser());
            view.addObject("dataSet", dataSet);
            viewVo.setDeleteUrl("/data/deleteData");
            viewVo.setModuleName(DataType.SUPERVISE_FEE_DATA.getDataName());
            viewVo.setModifyUrl("/data/feeAndRefundModfiy");
            view.addObject("view", viewVo);
            return view;
        }
    }

    protected class BusinessDataList implements SimpleDataList {
        @Override
        public ModelAndView dataList(Integer pageNum, ViewVo viewVo,Integer dataType) {
            Page<BusinessDataEntity> pager = PageHelper.startPage(pageNum == null ? 1 : pageNum, Constants.PAGE_SIZE);
            ModelAndView view = new ModelAndView("pages/data/genericDataList", "list", pager);
            List<BusinessDataEntity> businessDataEntities = businessDataDao.queryBusinessDataByCondition(viewVo.getBatchDate(),viewVo.getProjId(),viewVo.getClientName());
            if (CollectionUtils.isEmpty(businessDataEntities)) {
                pager.setPageNum(1);
                pager.setPages(1);
            }
            DataSet dataSet = new GenericDataTranslate<BusinessDataEntity>().translate(businessDataEntities, DataType.SUPERVISE_BIZ_DATA.getDataLevel(), SessionUser.INSTANCE.getCurrentUser());
            view.addObject("dataSet", dataSet);
            viewVo.setDeleteUrl("/data/deleteData");
            viewVo.setModuleName(DataType.SUPERVISE_BIZ_DATA.getDataName());
            viewVo.setModifyUrl("/data/businessModify");
            view.addObject("view", viewVo);
            return view;
        }
    }

    protected class RepaymentDataList implements SimpleDataList {
        @Override
        public ModelAndView dataList(Integer pageNum, ViewVo viewVo, Integer dataType) {
            Page<RepaymentEntity> pager = PageHelper.startPage(pageNum == null ? 1 : pageNum, Constants.PAGE_SIZE);
            ModelAndView view = new ModelAndView("pages/data/genericDataList", "list", pager);
            List<RepaymentEntity> repaymentEntities = repaymentDao.queryRepaymentByCondition(viewVo.getBatchDate(),viewVo.getProjId());
            List<RepaymentEntity> viewDataEntities =  new ArrayList<RepaymentEntity>();
            if (CollectionUtils.isEmpty(repaymentEntities)) {
                pager.setPageNum(1);
                pager.setPages(1);
            }else{
                //去业务表中获取客户编号以及客户名称
                for(RepaymentEntity repaymentEntity:repaymentEntities){
                    String projId = repaymentEntity.getProjId();
                    String orgId  = repaymentEntity.getOrgId();
                    List<BusinessDataEntity> businessDataEntities = businessDataDao.queryBusinessDataByExProj(viewVo.getBatchDate(), projId, viewVo.getClientName());
                    if(!CollectionUtils.isEmpty(businessDataEntities)){
                        BusinessDataEntity businessDataEntity = businessDataEntities.get(0);
                        String clientId = businessDataEntity.getClientId();
                        String clientName = businessDataEntity.getClientName();
                        repaymentEntity.setClientId(clientId);
                        repaymentEntity.setClientName(clientName);
                        viewDataEntities.add(repaymentEntity);
                    }else{
                        //如果没有在业务表中查询到项目编号，则视为脏数据，删除
                        List<BusinessDataEntity> exentities = businessDataDao.queryBusinessDataByExProj(viewVo.getBatchDate(), projId, "");
                        if(CollectionUtils.isEmpty(exentities)){
                            repaymentDao.deleteRepaymentByID(repaymentEntity.getId());
                        }
                    }

                }
            }
            DataSet dataSet = new GenericDataTranslate<RepaymentEntity>().translate(viewDataEntities, DataType.SUPERVISE_REBACK_DATA.getDataLevel(), SessionUser.INSTANCE.getCurrentUser());
            view.addObject("dataSet", dataSet);
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
    public String dataOutport(@RequestParam(value = "batchDate", required = false) String batchDate,
                              @RequestParam(value = "dataType", required = true) Integer dataType,
                              @RequestParam(value = "creditStartDate", required = false) String creditStartDate,
                              @RequestParam(value = "creditEndDate", required = false) String creditEndDate,
                              @RequestParam(value = "projId", required = false) String projId,
                              @RequestParam(value = "clientName", required = false) String clientName,
            HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        String date = batchDate;
        ViewVo viewVo = new ViewVo();
        viewVo.setBatchDate(batchDate);
        viewVo.setClientName(clientName);
        viewVo.setCreditStartDate(creditStartDate);
        viewVo.setCreditEndDate(creditEndDate);
        viewVo.setProjId(projId);
        viewVo.setDataType(dataType);
        if(dataType == 200){
            viewVo.setCreditType(true);
        }else{
            viewVo.setCreditType(false);
        }
        try {
            String fileName = DataType.typeOfType(dataType.intValue()).getDataEnName() + ".xls";
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            userEntity = SessionUser.INSTANCE.getCurrentUser();
            if (DataType.SUPERVISE_BANK_DATA.getDataLevel() == dataType.intValue()) {
                bankCreditOutport.export(servletOutputStream, DataType.SUPERVISE_BANK_DATA, viewVo, userEntity);
            }
            if (DataType.SUPERVISE_TRACE_DATA.getDataLevel() == dataType.intValue()) {
                recourseOutport.export(servletOutputStream, DataType.SUPERVISE_TRACE_DATA, viewVo, userEntity);
                //追偿
            }
            if (DataType.SUPERVISE_REBACK_DATA.getDataLevel() == dataType.intValue()) {
                repaymentOutport.export(servletOutputStream, DataType.SUPERVISE_REBACK_DATA, viewVo, userEntity);
            }
            if (DataType.SUPERVISE_REPLACE_DATA.getDataLevel() == dataType.intValue()) {
                compensatoryOutport.export(servletOutputStream, DataType.SUPERVISE_REPLACE_DATA, viewVo, userEntity);
            }
            if (DataType.SUPERVISE_FEE_DATA.getDataLevel() == dataType.intValue()) {
                feeAndRefundOutport.export(servletOutputStream, DataType.SUPERVISE_FEE_DATA, viewVo, userEntity);
            }
            if (DataType.SUPERVISE_BIZ_DATA.getDataLevel() == dataType.intValue()) {
                businessOutport.export(servletOutputStream, DataType.SUPERVISE_BIZ_DATA, viewVo, userEntity);
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
                bankCreditDao.deleteRepaymentByID(dataId);
            } else if (DataType.SUPERVISE_TRACE_DATA.getDataLevel() == dataType.intValue()) {
                //只能清除字段，必留机构ID+项目id+batchdate
//                RecourseEntity recourseEntity = recourseMapper.selectByPrimaryKey(dataId);
//                recourseReset(recourseEntity);
//                recourseMapper.updateByPrimaryKeySelective(recourseEntity);
                //直接删除
                repaymentDao.deleteRepaymentByID(dataId);
            } else if (DataType.SUPERVISE_REPLACE_DATA.getDataLevel() == dataType.intValue()) {
                //只能清除字段，必留机构ID+项目id+batchdate
//                CompensatoryEntity compensatoryEntity = compensatoryMapper.selectByPrimaryKey(dataId);
//                compensatoryReset(compensatoryEntity);
//                compensatoryMapper.updateByPrimaryKeySelective(compensatoryEntity);
                //直接删除
                compensatoryDao.deleteCompensatoryByID(dataId);
            } else if (DataType.SUPERVISE_FEE_DATA.getDataLevel() == dataType.intValue()) {
                //只能清除字段，必留机构ID+项目id+batchdate
//                FeeAndRefundEntity feeAndRefundEntity = feeAndRefundMapper.selectByPrimaryKey(dataId);
//                feeAndRefundReset(feeAndRefundEntity);
//                feeAndRefundMapper.updateByPrimaryKeySelective(feeAndRefundEntity);
                //直接删除
                feeAndRefundDao.deleteFeeAndRefundByID(dataId);
            } else if (DataType.SUPERVISE_BIZ_DATA.getDataLevel() == dataType.intValue()) {
                BusinessDataEntity businessDataEntity = businessDataDao.queryBusinessDataByKey(dataId);
                userEntity = SessionUser.INSTANCE.getCurrentUser();
                //如果是综合营运部，或者管理员则可以直接删除该条记录；否则只能清除该部门所有权限字段的内容,并更新数据
                if (isCompdep(userEntity) || "20".equalsIgnoreCase(userEntity.getDataLevels())) {
                    businessDataDao.deleteBusinessDataByID(dataId);
                    //同时删除还款、代偿、追偿、收退费信息，以机构ID+项目id+batchdate为删除条件
                    String batchDate = businessDataEntity.getBatchDate();
                    String orgId = businessDataEntity.getOrgId();
                    String projId = businessDataEntity.getProjId();
                    //删除收退费信息
                    feeAndRefundDao.deleteFeeAndRefundByCondition(batchDate,orgId,projId);
                    //删除还款信息
                    repaymentDao.deleteRepaymentByCondition(batchDate,orgId,projId);
                    //删除追偿信息
                    recourseDao.deleteRecourseByCondition(batchDate,orgId,projId);
                    //删除代偿信息
                    compensatoryDao.deleteCompensatoryByCondition(batchDate,orgId,projId);
                } else {
                    //否则清除有权限部分的字段，并更新数据库
                    Map<String, FiedRoleCache.DepRoleRef> filedRoles = FiedRoleCache.mapDepRoleRefs(DataType.SUPERVISE_BIZ_DATA.getDataLevel());
                    deleteBusinessDataEntity4Role(businessDataEntity, filedRoles, userEntity);
                    businessDataDao.updateBusinessData(businessDataEntity);
                }
            } else if (DataType.SUPERVISE_REBACK_DATA.getDataLevel() == dataType.intValue()) {
                //只能清除字段，必留机构ID+项目id+batchdate
//                RepaymentEntity repaymentEntity = repaymentMapper.selectByPrimaryKey(dataId);
//                repaymentReset(repaymentEntity);
//                repaymentMapper.updateByPrimaryKeySelective(repaymentEntity);
                //直接删除
                repaymentDao.deleteRepaymentByID(dataId);
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
            BankCreditEntity ebankCreditEntity  = bankCreditDao.queryBankCreditByKey(bankCreditEntity.getId());
            bankCreditEntity.setSendStatus(ebankCreditEntity.getSendStatus());
            bankCreditEntity.setCreateDate(ebankCreditEntity.getCreateDate());
            bankCreditEntity.setUpdateDate(new Date());
            bankCreditDao.updateBankCredit(bankCreditEntity);
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
            List<BusinessDataEntity> resList = this.businessDataDao.queryBusinessDataByExample(batchDate,orgId,projId);
            //如果没有查询到数据，则说明项目不存在
            if (CollectionUtils.isEmpty(resList)) {
                recourseDao.deleteRecourseByID(recourseEntity.getId());
                logger.info("delete recourse record : orgId:" + orgId + " projId:" + projId + " batchDate:" + batchDate);
                logger.info("reason:the project in business Not Existed");
            } else {
                RecourseEntity erecourseEntity = recourseDao.queryRecourseByKey(recourseEntity.getId());
                recourseEntity.setSendStatus(erecourseEntity.getSendStatus());
                recourseEntity.setCreateDate(erecourseEntity.getCreateDate());
                recourseDao.updateRecourse(recourseEntity);
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

            List<BusinessDataEntity> resList = this.businessDataDao.queryBusinessDataByExample(batchDate, orgId, projId);

            //如果没有查询到数据，则说明项目不存在
            if (CollectionUtils.isEmpty(resList)) {
                compensatoryDao.deleteCompensatoryByID(compensatoryEntity.getId());
                logger.info("delete compensatory record : orgId:" + orgId + " projId:" + projId + " batchDate:" + batchDate);
                logger.info("reason:the project in business Not Existed");
            } else {
                CompensatoryEntity ecompensatoryEntity = compensatoryDao.queryCompensatoryByKey(compensatoryEntity.getId());
                compensatoryEntity.setSendStatus(ecompensatoryEntity.getSendStatus());
                compensatoryEntity.setCreateDate(ecompensatoryEntity.getCreateDate());
                compensatoryDao.updateCompensatory(compensatoryEntity);
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

            List<BusinessDataEntity> resList = this.businessDataDao.queryBusinessDataByExample(batchDate, orgId, projId);

            //如果没有查询到数据，则说明项目不存在
            if (CollectionUtils.isEmpty(resList)) {
                feeAndRefundDao.deleteFeeAndRefundByID(feeAndRefundEntity.getId());
                logger.info("delete feeAndRefund record : orgId:" + orgId + " projId:" + projId + " batchDate:" + batchDate);
                logger.info("reason:the project in business Not Existed");
            } else {
                FeeAndRefundEntity efeeAndRefundEntity = feeAndRefundDao.queryFeeAndRefundByKey(feeAndRefundEntity.getId());
                feeAndRefundEntity.setSendStatus(efeeAndRefundEntity.getSendStatus());
                feeAndRefundEntity.setCreateDate(efeeAndRefundEntity.getCreateDate());
                feeAndRefundDao.updateFeeAndRefund(feeAndRefundEntity);
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

            BusinessDataEntity businessDataExist = businessDataDao.queryBusinessDataByKey(dataId);
            userEntity = SessionUser.INSTANCE.getCurrentUser();
            //根据权限设置更新的字段
            Map<String, FiedRoleCache.DepRoleRef> filedRoles = FiedRoleCache.mapDepRoleRefs(DataType.SUPERVISE_BIZ_DATA.getDataLevel());
            businessDataExist = updateBusinessDataEntity4Role(businessDataExist, businessDataEntity, filedRoles, userEntity);
//            String dateStr = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(new Date());
//            Date newDate = DateUtils.String2Date(dateStr, Constants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
//            businessDataExist.setUpdateDate(new Date());
            businessDataDao.updateBusinessData(businessDataExist);
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

            List<BusinessDataEntity> resList = this.businessDataDao.queryBusinessDataByExample(batchDate, orgId, projId);

            //如果没有查询到数据，则说明项目不存在
            if (CollectionUtils.isEmpty(resList)) {
                repaymentDao.deleteRepaymentByID(repaymentEntity.getId());
                logger.info("delete repayment record : orgId:" + orgId + " projId:" + projId + " batchDate:" + batchDate);
                logger.info("reason:the project in business Not Existed");
            } else {
                RepaymentEntity erepaymentEntity = repaymentDao.queryRepaymentByKey(repaymentEntity.getId());
                repaymentEntity.setSendStatus(erepaymentEntity.getSendStatus());
                repaymentEntity.setCreateDate(erepaymentEntity.getCreateDate());
                repaymentEntity.setUpdateDate(new Date());
                repaymentDao.updateRepayment(repaymentEntity);
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
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("clientType"))) {
            businessDataEntity.setClientType(Constants.NULLSTR);
        }
        //客户编码
//        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("clientId"))) {
//            businessDataEntity.setClientId(Constants.NULLSTR);
//        }
        //客户名称
//        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("clientName"))) {
//            businessDataEntity.setClientName(Constants.NULLSTR);
//        }
        //证件类型
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("iDCardType"))) {
            businessDataEntity.setIDCardType(Constants.NULLSTR);
        }
        //证件编码
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("iDCard"))) {
            businessDataEntity.setIDCard(Constants.NULLSTR);
        }
        //所属行业编号（一级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("callingFirst"))) {
            businessDataEntity.setCallingFirst(Constants.NULLSTR);
        }
        //所属行业编号（二级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("callingSecond"))) {
            businessDataEntity.setCallingSecond(Constants.NULLSTR);
        }
        //所属地区编号（一级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("areaFirst"))) {
            businessDataEntity.setAreaFirst(Constants.NULLSTR);
        }
        //所属地区编号（二级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("areaSecond"))) {
            businessDataEntity.setAreaSecond(Constants.NULLSTR);
        }
        //所属地区编号（三级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("areaThird"))) {
            businessDataEntity.setAreaThird(Constants.NULLSTR);
        }
        //客户规模编码
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("companyScale"))) {
            businessDataEntity.setCompanyScale(Constants.NULLSTR);
        }
        //是否涉农
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("isFarming"))) {
            businessDataEntity.setIsFarming(Constants.NULLSTR);
        }
        //业务类型
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("businessType"))) {
            businessDataEntity.setBusinessType(Constants.NULLSTR);
        }
        //合同金额
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("contractMoney"))) {
            businessDataEntity.setContractMoney(null);
        }
        //已放款金额
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("loanMoney"))) {
            businessDataEntity.setLoanMoney(null);
        }
        //贷款年利率
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("loanRate"))) {
            businessDataEntity.setLoanRate(null);
        }
        //担保综合费率
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("assureRate"))) {
            businessDataEntity.setAssureRate(null);
        }
        //放款日期
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("loanDate"))) {
            businessDataEntity.setLoanDate(null);
        }
        //合同截止日期
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("contractEndDate"))) {
            businessDataEntity.setContractEndDate(null);
        }
        //还款方式
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("repayType"))) {
            businessDataEntity.setRepayType(Constants.NULLSTR);
        }
        //反担保措施
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("pledgeType"))) {
            businessDataEntity.setPledgeType(Constants.NULLSTR);
        }
        //反担保备注
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("approveOption"))) {
            businessDataEntity.setApproveOption(Constants.NULLSTR);
        }
        //银行授信记录标示ID
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("bankCreditPrimaryId"))) {
            businessDataEntity.setBankCreditPrimaryId(Constants.NULLSTR);
        }
        //合作银行ID
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("coBankId"))) {
            businessDataEntity.setCoBankId(Constants.NULLSTR);
        }
        //项目状态
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("projSatus"))) {
            businessDataEntity.setProjSatus(Constants.NULLSTR);
        }
        //担保权人
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("assurePerson"))) {
            businessDataEntity.setAssurePerson(Constants.NULLSTR);
        }
        //反担保物价值
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("pledgeWorth"))) {
            businessDataEntity.setPledgeWorth(null);
        }
        //存单质押
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("isImpawn"))) {
            businessDataEntity.setIsImpawn(Constants.NULLSTR);
        }
        //受理时间
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("acceptDate"))) {
            businessDataEntity.setAcceptDate(null);
        }
        //合同编码
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("contractId"))) {
            businessDataEntity.setContractId(Constants.NULLSTR);
        }
        //客户存入保证金
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("clientBailMoney"))) {
            businessDataEntity.setClientBailMoney(null);
        }
        //存出保证金
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("outBailMoney"))) {
            businessDataEntity.setOutBailMoney(null);
        }
        //资本属性
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("capitalBelong"))) {
            businessDataEntity.setCapitalBelong(Constants.NULLSTR);
        }
        //项目结束时间
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("projEndDate"))) {
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
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("clientType"))) {
            businessDataExist.setClientType(businessDataUpdate.getClientType());
        }
        //客户编码
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("clientId"))||isCompdep(userEntity)) {
            businessDataExist.setClientId(businessDataUpdate.getClientId());
        }
        //客户名称
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("clientName"))||isCompdep(userEntity)) {
            businessDataExist.setClientName(businessDataUpdate.getClientName());
        }
        //证件类型
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("iDCardType"))) {
            businessDataExist.setIDCardType(businessDataUpdate.getIDCardType());
        }
        //证件编码
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("iDCard"))) {
            businessDataExist.setIDCard(businessDataUpdate.getIDCard());
        }
        //所属行业编号（一级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("callingFirst"))) {
            businessDataExist.setCallingFirst(businessDataUpdate.getCallingFirst());
        }
        //所属行业编号（二级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("callingSecond"))) {
            businessDataExist.setCallingSecond(businessDataUpdate.getCallingSecond());
        }
        //所属地区编号（一级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("areaFirst"))) {
            businessDataExist.setAreaFirst(businessDataUpdate.getAreaFirst());
        }
        //所属地区编号（二级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("areaSecond"))) {
            businessDataExist.setAreaSecond(businessDataUpdate.getAreaSecond());
        }
        //所属地区编号（三级）
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("areaThird"))) {
            businessDataExist.setAreaThird(businessDataUpdate.getAreaThird());
        }
        //客户规模编码
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("companyScale"))) {
            businessDataExist.setCompanyScale(businessDataUpdate.getCompanyScale());
        }
        //是否涉农
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("isFarming"))) {
            businessDataExist.setIsFarming(businessDataUpdate.getIsFarming());
        }
        //业务类型
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("businessType"))) {
            businessDataExist.setBusinessType(businessDataUpdate.getBusinessType());
        }
        //合同金额
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("contractMoney"))) {
            businessDataExist.setContractMoney(businessDataUpdate.getContractMoney());
        }
        //已放款金额
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("loanMoney"))) {
            businessDataExist.setLoanMoney(businessDataUpdate.getLoanMoney());
        }
        //贷款年利率
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("loanRate"))) {
            businessDataExist.setLoanRate(businessDataUpdate.getLoanRate());
        }
        //担保综合费率
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("assureRate"))) {
            businessDataExist.setAssureRate(businessDataUpdate.getAssureRate());
        }
        //放款日期
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("loanDate"))) {
            businessDataExist.setLoanDate(businessDataUpdate.getLoanDate());
        }
        //合同截止日期
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("contractEndDate"))) {
            businessDataExist.setContractEndDate(businessDataUpdate.getContractEndDate());
        }
        //还款方式
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("repayType"))) {
            businessDataExist.setRepayType(businessDataUpdate.getRepayType());
        }
        //反担保措施
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("pledgeType"))) {
            businessDataExist.setPledgeType(businessDataUpdate.getPledgeType());
        }
        //反担保备注
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("approveOption"))) {
            businessDataExist.setApproveOption(businessDataUpdate.getApproveOption());
        }
        //银行授信记录标示ID
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("bankCreditPrimaryId"))) {
            businessDataExist.setBankCreditPrimaryId(businessDataUpdate.getBankCreditPrimaryId());
        }
        //合作银行ID
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("coBankId"))) {
            businessDataExist.setCoBankId(businessDataUpdate.getCoBankId());
        }
        //项目状态
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("projSatus"))) {
            businessDataExist.setProjSatus(businessDataUpdate.getProjSatus());
        }
        //担保权人
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("assurePerson"))) {
            businessDataExist.setAssurePerson(businessDataUpdate.getAssurePerson());
        }
        //反担保物价值
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("pledgeWorth"))) {
            businessDataExist.setPledgeWorth(businessDataUpdate.getPledgeWorth());
        }
        //存单质押
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("isImpawn"))) {
            businessDataExist.setIsImpawn(businessDataUpdate.getIsImpawn());
        }
        //受理时间
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("acceptDate"))) {
            businessDataExist.setAcceptDate(businessDataUpdate.getAcceptDate());
        }
        //合同编码
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("contractId"))) {
            businessDataExist.setContractId(businessDataUpdate.getContractId());
        }
        //客户存入保证金
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("clientBailMoney"))) {
            businessDataExist.setClientBailMoney(businessDataUpdate.getClientBailMoney());
        }
        //存出保证金
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("outBailMoney"))) {
            businessDataExist.setOutBailMoney(businessDataUpdate.getOutBailMoney());
        }
        //资本属性
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("capitalBelong"))) {
            businessDataExist.setCapitalBelong(businessDataUpdate.getCapitalBelong());
        }
        //项目结束时间
        if (FiedRoleCache.checkFieldRole(userEntity, filedRoles.get("projEndDate"))) {
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


    /**
     * 判断是否综合运行部
     * @return
     */
    private boolean isCompdep(UserEntity userEntity){
        String depId = userEntity.getDepId();
        Long dep = -1L;
        if(org.apache.commons.lang3.StringUtils.isEmpty(depId)){
            dep = Long.parseLong(depId);
        }
        if(DepType.COMPREHENSIVE_DEP.getDepId()==dep){
            return true;
        }
        return false;
    }

}
