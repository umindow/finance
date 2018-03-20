package com.supervise.core.data.out;

import com.supervise.config.role.DataType;
import com.supervise.controller.vo.DataSet;
import com.supervise.controller.vo.ViewVo;
import com.supervise.core.data.translate.GenericDataTranslate;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.entity.FeeAndRefundEntity;
import com.supervise.dao.mysql.entity.UserEntity;
import com.supervise.dao.mysql.middleDao.BusinessDataDao;
import com.supervise.dao.mysql.middleDao.FeeAndRefundDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xishui.hb on 2018/3/5 上午10:08.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Service
public class FeeAndRefundOutport extends AbstractDataOutport{
    @Autowired
    private FeeAndRefundDao feeAndRefundDao;

    @Autowired
    private BusinessDataDao businessDataDao;

    @Override
    public DataSet dataSet(ViewVo viewVo, DataType dataType, UserEntity userEntity) {
//        Example example = new Example(FeeAndRefundEntity.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("batchDate",date);
//        List<FeeAndRefundEntity> entities = feeAndRefundMapper.selectByExample(example);

        List<FeeAndRefundEntity> entities = feeAndRefundDao.queryFeeAndRefundByCondition(viewVo.getBatchDate(), viewVo.getProjId());
        List<FeeAndRefundEntity> viewDataEntities =  new ArrayList<FeeAndRefundEntity>();
        if (!CollectionUtils.isEmpty(entities)) {
            for(FeeAndRefundEntity feeAndRefundEntity:entities){
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
        return new GenericDataTranslate<FeeAndRefundEntity>().translate(viewDataEntities,dataType.getDataLevel(),userEntity);
    }
}
