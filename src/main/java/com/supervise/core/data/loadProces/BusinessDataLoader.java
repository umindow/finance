package com.supervise.core.data.loadProces;

import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.core.data.spi.GenericDataProcessorHandler;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.entity.ViewBusinessDataEntity;
import com.supervise.dao.mysql.middleDao.BusinessDataDao;
import com.supervise.dao.mysql.viewDao.ViewBusinessDataDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
public class BusinessDataLoader extends GenericDataProcessorHandler<List<BusinessDataEntity>,String>{
    private final Logger logger = LoggerFactory.getLogger(BusinessDataLoader.class);
    @Autowired
    private ViewBusinessDataDao viewBusinessDataDao;

    @Autowired
    private BusinessDataDao businessDataDao;

    @Override
    public void afterData(List<BusinessDataEntity> dataRes) {
        //插入数据库
        if(!CollectionUtils.isEmpty(dataRes)){
            logger.info("BusinessDataEntity size :"+dataRes.size());
            for(BusinessDataEntity businessDataEntity : dataRes){
                logger.info(businessDataEntity.getBatchDate());
                int ret = businessDataDao.insertBusinessDataToMiddleDB(businessDataEntity);
                logger.info("ret:"+ret);
            }
        }


    }
    //涉及多个数据源的load，
    @Override
    public List<BusinessDataEntity> loadedData(String  batchDate) {
        String projStatus = Constants.PROJ_STATUS_REINS;//保后状态
        List<ViewBusinessDataEntity> businessDataEntityList =  viewBusinessDataDao.queryBusinessDataFromView(batchDate,projStatus);
        List<BusinessDataEntity>  businessDataEntitys  = conver(businessDataEntityList);
        return businessDataEntitys;
    }

    @Override
    public void processor(List<BusinessDataEntity> data) {
        //数据清洗，如果通过，可以父类实现或者抽离
        return ;
    }

    /**
     * VIEW对象转换成数据库对象
     * @param vbusinessDataEntitys
     * @return
     */
    private List<BusinessDataEntity> conver(List<ViewBusinessDataEntity> vbusinessDataEntitys){
        List<BusinessDataEntity> businessDataEntityList = new ArrayList<BusinessDataEntity>();
        if(!CollectionUtils.isEmpty(vbusinessDataEntitys)){
            for(ViewBusinessDataEntity vbusinessDataEntity : vbusinessDataEntitys ){
                BusinessDataEntity businessDataEntity = new BusinessDataEntity();
                businessDataEntity.setProjId(vbusinessDataEntity.getProjId());
                businessDataEntity.setOrgId(vbusinessDataEntity.getOrgId());

                businessDataEntity.setContractId(vbusinessDataEntity.getContractId());
                businessDataEntity.setContractMoney(vbusinessDataEntity.getContractMoney());
                businessDataEntity.setCallingFirst(vbusinessDataEntity.getCallingFirst());
                businessDataEntity.setCallingSecond(vbusinessDataEntity.getCallingSecond());
                businessDataEntity.setCapitalBelong(vbusinessDataEntity.getCapitalBelong());
                businessDataEntity.setClientId(vbusinessDataEntity.getClientId());
                businessDataEntity.setClientName(vbusinessDataEntity.getClientName());
                businessDataEntity.setClientBailMoney(vbusinessDataEntity.getClientBailMoney());
                businessDataEntity.setCoBankId(vbusinessDataEntity.getCoBankId());
                businessDataEntity.setCompanyScale(vbusinessDataEntity.getCompanyScale());
                businessDataEntity.setContractEndDate(vbusinessDataEntity.getContractEndDate());
                businessDataEntity.setClientType(vbusinessDataEntity.getClientType());

                businessDataEntity.setAcceptDate(vbusinessDataEntity.getAcceptDate());
                businessDataEntity.setAssurePerson(vbusinessDataEntity.getAssurePerson());
                businessDataEntity.setApproveOption(vbusinessDataEntity.getApproveOption());
                businessDataEntity.setAssureRate(vbusinessDataEntity.getAssureRate());
                businessDataEntity.setAreaFirst(vbusinessDataEntity.getAreaFirst());
                businessDataEntity.setAreaSecond(vbusinessDataEntity.getAreaSecond());
                businessDataEntity.setAreaThird(vbusinessDataEntity.getAreaThird());

                businessDataEntity.setBankCreditPrimaryId(vbusinessDataEntity.getBankCreditPrimaryId());
                businessDataEntity.setBusinessType(vbusinessDataEntity.getBusinessType());

                businessDataEntity.setFirstLoanDate(vbusinessDataEntity.getFirstLoanDate());
                businessDataEntity.setIsFarming(vbusinessDataEntity.getIsFarming());
                businessDataEntity.setIDCard(vbusinessDataEntity.getIDCard());
                businessDataEntity.setIDCardType(vbusinessDataEntity.getIDCardType());
                businessDataEntity.setIsImpawn(vbusinessDataEntity.getIsImpawn());
                businessDataEntity.setInitialBalance(vbusinessDataEntity.getInitialBalance());

                businessDataEntity.setLoanRate(vbusinessDataEntity.getLoanRate());
                businessDataEntity.setLoanMoney(vbusinessDataEntity.getLoanMoney());
                businessDataEntity.setOutBailMoney(vbusinessDataEntity.getOutBailMoney());
                businessDataEntity.setPledgeWorth(vbusinessDataEntity.getPledgeWorth());
                businessDataEntity.setPledgeType(vbusinessDataEntity.getPledgeType());
                businessDataEntity.setProjEndDate(vbusinessDataEntity.getProjEndDate());
                businessDataEntity.setProjSatus(vbusinessDataEntity.getProjSatus());
                businessDataEntity.setRepayType(vbusinessDataEntity.getRepayType());

                if(!StringUtils.isEmpty(vbusinessDataEntity.getLoanDate())){
                    Date loanDate = DateUtils.String2Date(vbusinessDataEntity.getLoanDate(),Constants.YYYY_MM_DD, Locale.ENGLISH);
                    businessDataEntity.setLoanDate(loanDate);
                }
                String batchDate = "";
                if(vbusinessDataEntity.getBatchDate()!=null){
                    batchDate = new SimpleDateFormat(Constants.YYYY_MM_DD).format(vbusinessDataEntity.getBatchDate());
                }else{
                    batchDate = new SimpleDateFormat(Constants.YYYY_MM_DD).format(new Date());
                }
                businessDataEntity.setBatchDate(batchDate);
                businessDataEntityList.add(businessDataEntity);
            }
        }
        return businessDataEntityList;
    }
}
