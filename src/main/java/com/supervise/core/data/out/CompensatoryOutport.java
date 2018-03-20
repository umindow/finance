package com.supervise.core.data.out;

import com.supervise.config.role.DataType;
import com.supervise.controller.vo.DataSet;
import com.supervise.controller.vo.ViewVo;
import com.supervise.core.data.translate.GenericDataTranslate;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.entity.CompensatoryEntity;
import com.supervise.dao.mysql.entity.UserEntity;
import com.supervise.dao.mysql.middleDao.BusinessDataDao;
import com.supervise.dao.mysql.middleDao.CompensatoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xishui.hb on 2018/3/5 上午10:07.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Service
public class CompensatoryOutport extends AbstractDataOutport{
//    @Autowired
//    private CompensatoryMapper compensatoryMapper;
    @Autowired
    private BusinessDataDao businessDataDao;

    @Autowired
    private CompensatoryDao compensatoryDao;
    @Override
    public DataSet dataSet(ViewVo viewVo, DataType dataType, UserEntity userEntity) {
        List<CompensatoryEntity> entities = compensatoryDao.queryCompensatoryByCondition(viewVo.getBatchDate(), viewVo.getProjId());
        List<CompensatoryEntity> viewDataEntities =  new ArrayList<CompensatoryEntity>();
        if (!CollectionUtils.isEmpty(entities)){
             //去业务表中获取客户编号以及客户名称
            for(CompensatoryEntity compensatoryEntity:entities){
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
            return new GenericDataTranslate<CompensatoryEntity>().translate(viewDataEntities,dataType.getDataLevel(),userEntity);

    }
}
