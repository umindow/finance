package com.supervise.core.data.loadProces;

import com.supervise.core.data.spi.GenericDataProcessorHandler;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.middleDao.BankCreditDao;
import com.supervise.dao.mysql.viewDao.ViewBankCreditDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private ViewBankCreditDao viewBankCreditDao;

    @Autowired
    private BankCreditDao rankCreditDao;

    @Override
    public void afterData(List<BankCreditEntity> dataRes) {
        //插入数据库
        for(BankCreditEntity rankCreditEntity : dataRes){
            rankCreditDao.insertBankCreditToMiddleDB(rankCreditEntity);
        }

    }
    //涉及多个数据源的load，
    @Override
    public List<BankCreditEntity> loadedData(String  batchDate) {
        List<BankCreditEntity> bankCreditEntityList =  viewBankCreditDao.queryBankCreditFromView(batchDate);
        return bankCreditEntityList;
    }

    @Override
    public void processor(List<BankCreditEntity> data) {
        //数据清洗，如果通过，可以父类实现或者抽离
    }
}
