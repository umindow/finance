package com.supervise.core.data.loadProces;

import com.supervise.common.Constants;
import com.supervise.core.data.spi.GenericDataProcessorHandler;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.entity.TaskStatusEntity;
import com.supervise.dao.mysql.entity.ViewBankCreditEntity;
import com.supervise.dao.mysql.middleDao.BankCreditDao;
import com.supervise.dao.mysql.middleDao.TaskStatusDao;
import com.supervise.dao.mysql.viewDao.ViewBankCreditDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xishui.hb on 2018/1/31 上午10:09.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Service
public class BankCreditLoader extends GenericDataProcessorHandler<List<BankCreditEntity>,String>{
    private final Logger logger = LoggerFactory.getLogger(BankCreditLoader.class);
    @Autowired
    private ViewBankCreditDao viewBankCreditDao;

    @Autowired
    private BankCreditDao bankCreditDao;

    @Autowired
    private TaskStatusDao taskStatusDao;




    @Override
    public void afterData(List<BankCreditEntity> dataRes) {
        //插入数据库
        String dataType ="200";
        String dataName ="银行授信数据";
        String resultCode = "0";
        String option = "0";
        boolean issucess = true;

        try{
            if(!CollectionUtils.isEmpty(dataRes)){
                logger.info("BankCreditEntity size :"+dataRes.size());
                for(BankCreditEntity bankCreditEntity : dataRes){
                    int ret = bankCreditDao.insertBankCreditToMiddleDB(bankCreditEntity);
                    logger.info("ret:"+ret);
                }
            }
        }catch (Exception e){
            issucess = false;
            e.printStackTrace();
        }
        if(!issucess){
            resultCode = "-1";
        }else{
            resultCode = "0";
        }
        TaskStatusEntity taskStatusEntity  = new TaskStatusEntity(dataType,dataName,option,resultCode);
        taskStatusEntity.setRemark(String.valueOf(dataRes.size()));
        int ret = this.taskStatusDao.insertTaskStatusToMiddleDB(taskStatusEntity);
        if(ret!=-1){
            logger.info("银行授信数据 data taskstatus job Success,batchDate is :"+new SimpleDateFormat(Constants.YYYY_MM_DD).format(new Date()));
        }

    }
    //涉及多个数据源的load，
    @Override
    public List<BankCreditEntity> loadedData(String  batchDate) {
        List<ViewBankCreditEntity> bankCreditEntitys =  viewBankCreditDao.queryBankCreditFromView(batchDate);
        List<BankCreditEntity> bankCreditEntityList = conver(bankCreditEntitys);
        return bankCreditEntityList;
    }

    @Override
    public void processor(List<BankCreditEntity> data) {
        //数据清洗，如果通过，可以父类实现或者抽离
        return;
    }

    /**
     * VIEW对象转换成数据库对象
     * @param vbankCreditEntitys
     * @return
     */
    private List<BankCreditEntity> conver(List<ViewBankCreditEntity> vbankCreditEntitys){
        List<BankCreditEntity> bankCreditEntityList = new ArrayList<BankCreditEntity>();
        if(!CollectionUtils.isEmpty(vbankCreditEntitys)){
            for(ViewBankCreditEntity vbankCreditEntity : vbankCreditEntitys ){
                BankCreditEntity bankCreditEntity = new BankCreditEntity();
                bankCreditEntity.setCreditEndDate(vbankCreditEntity.getCreditEndDate());
                bankCreditEntity.setRemark(vbankCreditEntity.getRemark());
                bankCreditEntity.setSingleMoneyLimit(vbankCreditEntity.getSingleMoneyLimit());
                bankCreditEntity.setCreditStartDate(vbankCreditEntity.getCreditStartDate());
                bankCreditEntity.setLeaveMoney(vbankCreditEntity.getLeaveMoney());
                bankCreditEntity.setMainBankId(vbankCreditEntity.getMainBankId());
                bankCreditEntity.setBailMoney(vbankCreditEntity.getBailMoney());
                bankCreditEntity.setBailScale(vbankCreditEntity.getBailScale());
                bankCreditEntity.setBankId(vbankCreditEntity.getBankId());
                bankCreditEntity.setBlowupMulpitle(vbankCreditEntity.getBlowupMulpitle());
                bankCreditEntity.setCreditMoney(vbankCreditEntity.getCreditMoney());
                bankCreditEntity.setCreditStatus(vbankCreditEntity.getCreditStatus());
                bankCreditEntity.setCreditTypeId(vbankCreditEntity.getCreditTypeId());
                bankCreditEntity.setPrimaryId(vbankCreditEntity.getPrimaryId());
                bankCreditEntity.setItemLean(vbankCreditEntity.getItemLean());
                bankCreditEntity.setOrgId(vbankCreditEntity.getOrgId());
                bankCreditEntity.setIsForCredit(vbankCreditEntity.getIsForCredit());
                String batchDate = "";
                if(vbankCreditEntity.getBatchDate()!=null){
                    batchDate = new SimpleDateFormat(Constants.YYYY_MM_DD).format(vbankCreditEntity.getBatchDate());
                }else{
                    batchDate = new SimpleDateFormat(Constants.YYYY_MM_DD).format(new Date());
                }
                bankCreditEntity.setBatchDate(batchDate);
                bankCreditEntityList.add(bankCreditEntity);
            }
        }
        return bankCreditEntityList;
    }

    /**
     * 构造对象
     * @param dataype
     * @param dataName
     * @param optiontype
     * @param resultCode
     * @return
     */
//    private TaskStatusEntity createEntity(String dataype,String dataName,String optiontype,String resultCode){
//        TaskStatusEntity taskStatusEntity = new TaskStatusEntity();
//        taskStatusEntity.setRemark("");
//        taskStatusEntity.setResult(resultCode);
//        taskStatusEntity.setOpType(optiontype);
//        taskStatusEntity.setDataName(dataName);
//        taskStatusEntity.setDataType(dataype);
//        return  taskStatusEntity;
//    }
}
