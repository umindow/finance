package com.supervise.core.data.translate;

import com.supervise.cache.FiedRoleCache;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.BaseEntity;
import com.supervise.config.role.DepType;
import com.supervise.controller.vo.DataSet;
import com.supervise.controller.vo.DataVo;
import com.supervise.controller.vo.FieldValue;
import com.supervise.dao.mysql.entity.UserEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xishui.hb on 2018/3/4 下午4:00.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public class GenericDataTranslate<T extends BaseEntity> extends AbstractTranslate implements DataTranslate<T> {
    @Override
    public DataSet translate(List<T> datas, int dataType, UserEntity userEntity) {
        if (null == userEntity) {
            return new DataSet(null);
        }
        boolean isComp = isComdep(userEntity);
        List<FiedRoleCache.DepRoleRef> depRoleRefs = depRoleRefs(dataType, userEntity);
        DataSet dataSet = new DataSet(new GenericFieldTranslate().fieldTranslate(depRoleRefs));
        if(CollectionUtils.isEmpty(datas)){
            return dataSet;
        }
        List<DataVo> dataVos = new ArrayList<DataVo>();
        DataVo dataVo = null;
        for (final T t : datas) {
            dataVo = new DataVo();
            BaseEntity baseEntity = (BaseEntity) t;
            dataVo.setDataId(baseEntity.getId());
            List<FieldValue> fieldValues = new ArrayList<FieldValue>();
            for (final FiedRoleCache.DepRoleRef depRoleRef : depRoleRefs) {
                String fielName = depRoleRef.getFieldName();
                String methodName = "get" + depRoleRef.getFieldName().replaceFirst(depRoleRef.getFieldName().substring(0, 1), depRoleRef.getFieldName().substring(0, 1)
                        .toUpperCase());
                try {
                    Object value = t.getClass().getMethod(methodName).invoke(t);
                    if (depRoleRef.isDate() && (value instanceof Date)) {
                        if(depRoleRef.getDateFormat().length() >10){
                            fieldValues.add(new FieldValue(DateUtils.formatDate((Date) value, depRoleRef.getDateFormat()), depRoleRef.getFieldName(), depRoleRef.getFieldCnName(), depRoleRef.isModify(), depRoleRef.isDate(), "yyyy-mm-dd",true,"hh:mm:ss"
                                    ));
                        }else {
                            fieldValues.add(new FieldValue(DateUtils.formatDate((Date) value, depRoleRef.getDateFormat()), depRoleRef.getFieldName(), depRoleRef.getFieldCnName(), depRoleRef.isModify(), depRoleRef.isDate(), depRoleRef.getDateFormat()));
                        }
                    } else {
                        //如果是综合运营部，则可以修改BUSINESS中的客户姓名以及客户编号
                        if(("clientName".equalsIgnoreCase(fielName)||"clientId".equalsIgnoreCase(fielName))&&isComp){
                            fieldValues.add(new FieldValue(value, depRoleRef.getFieldName(), depRoleRef.getFieldCnName(), true));
                        }else{
                            fieldValues.add(new FieldValue(value, depRoleRef.getFieldName(), depRoleRef.getFieldCnName(), depRoleRef.isModify()));
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            dataVo.setValues(fieldValues);
            dataVos.add(dataVo);
        }

        dataSet.setDataVos(dataVos);
        return dataSet;
    }

    private boolean  isComdep(UserEntity userEntity){
        if(userEntity!=null){
            String depId = userEntity.getDepId();
            if(!StringUtils.isEmpty(depId)&&String.valueOf(DepType.COMPREHENSIVE_DEP.getDepId()).equalsIgnoreCase(depId)){
                return true;
            }
        }
        return false;
    }
}
