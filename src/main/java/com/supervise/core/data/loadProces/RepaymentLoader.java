package com.supervise.core.data.loadProces;

import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.core.data.spi.GenericDataProcessorHandler;
import com.supervise.dao.mysql.entity.RepaymentEntity;
import com.supervise.dao.mysql.entity.ViewRepaymentEntity;
import com.supervise.dao.mysql.middleDao.RepaymentDao;
import com.supervise.dao.mysql.viewDao.ViewRepaymentDao;
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
public class RepaymentLoader extends GenericDataProcessorHandler<List<RepaymentEntity>,String>{
    private final Logger logger = LoggerFactory.getLogger(RepaymentLoader.class);
    @Autowired
    private ViewRepaymentDao viewRepaymentDao;

    @Autowired
    private RepaymentDao repaymentDao;

    @Override
    public void afterData(List<RepaymentEntity> dataRes) {
        //插入数据库
        if(!CollectionUtils.isEmpty(dataRes)){
            logger.info("RepaymentEntity size :"+dataRes.size());
            for(RepaymentEntity repaymentEntity : dataRes){
                logger.info(repaymentEntity.getBatchDate());
                int ret = repaymentDao.insertRepaymentToMiddleDB(repaymentEntity);
                logger.info("ret:"+ret);
            }
        }
    }
    //涉及多个数据源的load，所以需要需要做reduce操作
    @Override
    public List<RepaymentEntity> loadedData(String  batchDate) {
        List<ViewRepaymentEntity> repaymentEntitys =  viewRepaymentDao.queryRepaymentFromView(batchDate);
        List<RepaymentEntity> repaymentEntityList  = conver(repaymentEntitys);
        return repaymentEntityList;
    }

    @Override
    public void processor(List<RepaymentEntity> data) {
        //数据清洗，如果通过，可以父类实现或者抽离
        return ;
    }

    /**
     * VIEW对象转换成数据库对象
     * @param vrepaymentEntitys
     * @return
     */
    private List<RepaymentEntity> conver(List<ViewRepaymentEntity> vrepaymentEntitys){
        List<RepaymentEntity> reList = new ArrayList<RepaymentEntity>();
        if(!CollectionUtils.isEmpty(vrepaymentEntitys)){
            for(ViewRepaymentEntity vRepaymentEntity : vrepaymentEntitys ){
                RepaymentEntity repaymentEntity = new RepaymentEntity();
                repaymentEntity.setInterest(vRepaymentEntity.getInterest());
                repaymentEntity.setOrgId(vRepaymentEntity.getOrgId());
                repaymentEntity.setPunishMoney(vRepaymentEntity.getPunishMoney());
                repaymentEntity.setPrincipal(vRepaymentEntity.getPrincipal());
                repaymentEntity.setContractId(vRepaymentEntity.getContractId());
                repaymentEntity.setProjId(vRepaymentEntity.getProjId());

                String batchDate = "";
                if(vRepaymentEntity.getBatchDate()!=null){
                    batchDate = new SimpleDateFormat(Constants.YYYY_MM_DD).format(vRepaymentEntity.getBatchDate());
                }else{
                    batchDate = new SimpleDateFormat(Constants.YYYY_MM_DD).format(new Date());
                }
                repaymentEntity.setBatchDate(batchDate);
                if(!StringUtils.isEmpty(vRepaymentEntity.getRepayDate())){
                    Date  repayDate = DateUtils.String2Date(vRepaymentEntity.getRepayDate(),Constants.YYYY_MM_DD, Locale.ENGLISH);
                    repaymentEntity.setRepayDate(repayDate);
                }
                reList.add(repaymentEntity);
            }
        }
        return reList;
    }
}
