package com.supervise.core.data.translate;

import com.supervise.cache.FiedRoleCache;

import java.util.List;

/**
 * Created by xishui.hb on 2018/3/4 下午5:27.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public interface FieldTranslate {

    List<String> fieldTranslate(List<FiedRoleCache.DepRoleRef> depRoleRefs);
}
