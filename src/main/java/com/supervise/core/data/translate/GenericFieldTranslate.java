package com.supervise.core.data.translate;

import com.supervise.cache.FiedRoleCache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xishui.hb on 2018/3/4 下午5:50.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public class GenericFieldTranslate extends AbstractTranslate implements FieldTranslate {
    @Override
    public List<String> fieldTranslate(List<FiedRoleCache.DepRoleRef> depRoleRefs) {
        List<String> fields = new ArrayList<String>();
        for(final FiedRoleCache.DepRoleRef depRoleRef : depRoleRefs){
            fields.add(depRoleRef.getFieldCnName());
        }
        return fields;
    }
}
