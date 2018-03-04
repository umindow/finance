package com.supervise.cache;

import com.alibaba.fastjson.JSON;
import com.supervise.config.role.DataType;
import com.supervise.config.role.DepRole;
import com.supervise.config.role.DepType;
import com.supervise.dao.mysql.entity.*;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xishui.hb on 2018/2/6 上午11:35.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public final class FiedRoleCache {
    private final static Logger logger = LoggerFactory.getLogger(FiedRoleCache.class);
    private static final Map<Integer, Map<String, DepRoleRef>> FIED_ROLE_CACHE = new HashMap<Integer, Map<String, DepRoleRef>>();


    public static Map<String, DepRoleRef> mapDepRoleRefs(Integer dataType) {
        return FIED_ROLE_CACHE.containsKey(dataType) ? FIED_ROLE_CACHE.get(dataType) : null;
    }

    public static DepRoleRef depRoleRef(Integer dataType, String columnName) {
        return FIED_ROLE_CACHE.containsKey(dataType) ? (FIED_ROLE_CACHE.get(dataType).containsKey(columnName) ? FIED_ROLE_CACHE.get(dataType).get(columnName) : null) : null;
    }

    static {
        initRoleCache();
    }

    private static void initRoleCache() {
        commonCache(BankCreditEntity.class, DataType.SUPERVISE_BANK_DATA.getDataLevel());//银行授信
        commonCache(BusinessDataEntity.class, DataType.SUPERVISE_BIZ_DATA.getDataLevel());//系统业务
        commonCache(CompensatoryEntity.class, DataType.SUPERVISE_REPLACE_DATA.getDataLevel());//代偿
        commonCache(FeeAndRefundEntity.class, DataType.SUPERVISE_FEE_DATA.getDataLevel());//收费退费数据
        commonCache(RepaymentEntity.class, DataType.SUPERVISE_REBACK_DATA.getDataLevel());//退款
        commonCache(RecourseEntity.class, DataType.SUPERVISE_TRACE_DATA.getDataLevel());//追偿
    }

    private static void commonCache(Class<?> cls, Integer dataType) {
        Field[] fields = cls.getDeclaredFields();
        if (null == fields || fields.length <= 0) {
            logger.info("ClassEntity has non Fields."+cls.getSimpleName());
            return;
        }
        Map<String, DepRoleRef> depRoleRefMap = new HashMap<String, DepRoleRef>();
        for (final Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (null == column) {
                continue;
            }
            DepRole depRole = field.getAnnotation(DepRole.class);
            if(null == depRole){
                continue;
            }
            depRoleRefMap.put(column.name(), (null == depRole) ? null : new DepRoleRef(depRole.depTypes(), depRole.modify(), column.name(), field.getName(), depRole.fieldCnName(), depRole.index()));
        }
        FIED_ROLE_CACHE.put(dataType, depRoleRefMap);
        logger.info("Add Field Role Ref DataType:%s, Refs:%s", DataType.typeOfType(dataType).getDataName(), JSON.toJSON(depRoleRefMap));
    }

    @Data
    public static class DepRoleRef {
        private DepType[] depTypes;
        private boolean modify;
        private String columnName;
        private String fieldName;
        private String fieldCnName;
        private int index;

        public DepRoleRef(DepType[] depTypes, boolean modify, String columnName, String fieldName, String fieldCnName, int index) {
            this.depTypes = depTypes;
            this.modify = modify;
            this.columnName = columnName;
            this.fieldName = fieldName;
            this.fieldCnName = fieldCnName;
            this.index = index;
        }
    }

    public static boolean checkFieldRole(int userDepId, DepRoleRef depRoleRef) {
        if (null == depRoleRef) {
            return false;
        }
        if (!depRoleRef.isModify()) {
            return false;
        }
        if (depRoleRef.depTypes.length > 0) {
            for (final DepType depType : depRoleRef.getDepTypes()) {
                if (depType.getDepId() == userDepId) {
                    return true;
                }
            }
        }
        return false;
    }
}
