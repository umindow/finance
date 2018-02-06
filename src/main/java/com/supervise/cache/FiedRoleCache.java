package com.supervise.cache;

import com.alibaba.fastjson.JSON;
import com.supervise.config.role.DataType;
import com.supervise.config.role.DepRole;
import com.supervise.config.role.DepType;
import com.supervise.dao.mysql.entity.BankCreditEntity;
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
        //SystemBizEntity
        systemBiz();
        //BankCreditEntity
        bankCredit();
    }

    private static void systemBiz() {

    }

    private static void bankCredit() {
        commonCache(BankCreditEntity.class, DataType.SUPERVISE_BANK_DATA.getDataType());
    }

    private static void commonCache(Class<?> cls, Integer dataType) {
        Field[] fields = cls.getFields();
        if (null == fields || fields.length <= 0) {
            logger.info("ClassEntity[%s] has non Fields.", cls.getName());
            return;
        }
        Map<String, DepRoleRef> depRoleRefMap = new HashMap<String, DepRoleRef>();
        for (final Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (null == column) {
                continue;
            }
            DepRole depRole = field.getAnnotation(DepRole.class);
            depRoleRefMap.put(column.name(), (null == depRole) ? null : new DepRoleRef(depRole.depTypes(), depRole.modify()));
        }
        FIED_ROLE_CACHE.put(dataType, depRoleRefMap);
        logger.info("Add Field Role Ref DataType:%s, Refs:%s", DataType.typeOfType(dataType).getDataName(), JSON.toJSON(depRoleRefMap));
    }

    @Data
    public static class DepRoleRef {
        private DepType[] depTypes;
        private boolean modify;

        public DepRoleRef(DepType[] depTypes, boolean modify) {
            this.depTypes = depTypes;
            this.modify = modify;
        }
    }
}
