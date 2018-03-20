package com.supervise.core.data.out;

import com.supervise.config.role.DataType;
import com.supervise.controller.vo.DataSet;
import com.supervise.controller.vo.ViewVo;
import com.supervise.core.data.translate.GenericDataTranslate;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.entity.UserEntity;
import com.supervise.dao.mysql.middleDao.BusinessDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xishui.hb on 2018/3/5 上午10:06.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Service
public class BusinessOutport extends AbstractDataOutport{
    @Autowired
    private BusinessDataDao businessDataDao;
    @Override
    public DataSet dataSet(ViewVo viewVo,DataType dataType,UserEntity userEntity) {

        List<BusinessDataEntity> entities = businessDataDao.queryBusinessDataByCondition(viewVo.getBatchDate(), viewVo.getProjId(), viewVo.getClientName());
        return new GenericDataTranslate<BusinessDataEntity>().translate(entities,dataType.getDataLevel(),userEntity);

    }
}
