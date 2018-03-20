package com.supervise.core.data.out;

import com.supervise.config.role.DataType;
import com.supervise.controller.vo.DataSet;
import com.supervise.controller.vo.ViewVo;
import com.supervise.core.data.translate.GenericDataTranslate;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.entity.RecourseEntity;
import com.supervise.dao.mysql.entity.UserEntity;
import com.supervise.dao.mysql.middleDao.BusinessDataDao;
import com.supervise.dao.mysql.middleDao.RecourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xishui.hb on 2018/3/5 上午10:09.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Service
public class RecourseOutport extends AbstractDataOutport{
    @Autowired
    private BusinessDataDao businessDataDao;
    @Autowired
    private RecourseDao recourseDao;
    @Override
    public DataSet dataSet(ViewVo viewVo, DataType dataType,UserEntity userEntity) {
        List<RecourseEntity> recourseEntities = recourseDao.queryRecourseByCondition(viewVo.getBatchDate(), viewVo.getProjId());
        List<RecourseEntity> viewDataEntities =  new ArrayList<RecourseEntity>();
        if (!CollectionUtils.isEmpty(recourseEntities)) {
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
        return new GenericDataTranslate<RecourseEntity>().translate(viewDataEntities,dataType.getDataLevel(),userEntity);
    }
}
