package com.supervise.core.data.translate;

import com.supervise.cache.FiedRoleCache;
import com.supervise.dao.mysql.entity.UserEntity;

import java.util.*;

/**
 * Created by xishui.hb on 2018/3/4 下午6:54.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public abstract class AbstractTranslate {
    public List<FiedRoleCache.DepRoleRef> depRoleRefs(int dataType,UserEntity userEntity){
        Map<String, FiedRoleCache.DepRoleRef> depRoleRefMap = FiedRoleCache.mapDepRoleRefs(dataType);
        if(null == depRoleRefMap || depRoleRefMap.isEmpty()){
            return null;
        }
        List<FiedRoleCache.DepRoleRef> depRoleRefs = new ArrayList<FiedRoleCache.DepRoleRef>();
        for (final Map.Entry<String, FiedRoleCache.DepRoleRef> entry : depRoleRefMap.entrySet()) {
            FiedRoleCache.DepRoleRef depRoleRef = entry.getValue();
            if(FiedRoleCache.checkFieldRoleNoModify(userEntity,depRoleRef,false)) {
                depRoleRefs.add(entry.getValue());
            }
        }
        Collections.sort(depRoleRefs, new Comparator<FiedRoleCache.DepRoleRef>() {
            @Override
            public int compare(FiedRoleCache.DepRoleRef o1, FiedRoleCache.DepRoleRef o2) {
                return o1.getIndex() >= o2.getIndex() ? 1 : -1;
            }
        });
        return depRoleRefs;
    }
}
