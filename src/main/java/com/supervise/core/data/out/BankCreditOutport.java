package com.supervise.core.data.out;

import com.supervise.config.role.DataType;
import com.supervise.controller.vo.DataSet;
import com.supervise.controller.vo.ViewVo;
import com.supervise.core.data.translate.GenericDataTranslate;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.entity.UserEntity;
import com.supervise.dao.mysql.middleDao.BankCreditDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xishui.hb on 2018/3/5 上午10:05.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Service
public class BankCreditOutport extends AbstractDataOutport{
    @Autowired
    private BankCreditDao bankCreditDao;

    @Override
    public DataSet dataSet(ViewVo viewVo,DataType dataType, UserEntity userEntity) {

        List<BankCreditEntity> entities = bankCreditDao.queryBankCreditByCondition(viewVo.getBatchDate(), viewVo.getCreditStartDate(), viewVo.getCreditEndDate());
        return new GenericDataTranslate<BankCreditEntity>().translate(entities,dataType.getDataLevel(),userEntity);

    }
}
