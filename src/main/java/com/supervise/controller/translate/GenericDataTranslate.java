package com.supervise.controller.translate;

import com.supervise.cache.FiedRoleCache;
import com.supervise.config.mysql.base.BaseEntity;
import com.supervise.controller.vo.DataSet;
import com.supervise.controller.vo.DataVo;
import com.supervise.controller.vo.FieldValue;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.entity.UserEntity;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

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
        if (CollectionUtils.isEmpty(datas) || null == userEntity) {
            return new DataSet(null);
        }
        int userDepId = Integer.valueOf(userEntity.getDepId());
        List<FiedRoleCache.DepRoleRef> depRoleRefs = depRoleRefs(dataType, userDepId);
        DataSet dataSet = new DataSet(new GenericFieldTranslate().fieldTranslate(depRoleRefs));

        List<DataVo> dataVos = new ArrayList<DataVo>();
        DataVo dataVo = null;
        for (final T t : datas) {
            dataVo = new DataVo();
            BaseEntity baseEntity = (BaseEntity) t;
            dataVo.setDataId(baseEntity.getId());
            List<FieldValue> fieldValues = new ArrayList<FieldValue>();
            for (final FiedRoleCache.DepRoleRef depRoleRef : depRoleRefs) {
                String methodName = "get" + depRoleRef.getFieldName().replaceFirst(depRoleRef.getFieldName().substring(0, 1), depRoleRef.getFieldName().substring(0, 1)
                        .toUpperCase());
                try {
                    fieldValues.add(new FieldValue(t.getClass().getMethod(methodName).invoke(t), depRoleRef.getColumnName(), depRoleRef.getFieldCnName(), depRoleRef.isModify()));
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
}
