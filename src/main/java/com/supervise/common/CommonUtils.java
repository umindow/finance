package com.supervise.common;



import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * ClassName: SchTranslatorUtils <br/>
 * Description: 通用工具类 <br/>
 * Date: 2017/1/4 11:24 <br/>
 * @version 1.0 <br/>
 */
public final class CommonUtils {



    /**
     * 判断集合是否为空
     *
     * @param collection 目标集合
     * @return boolean   是否为空
     */
    public static boolean collectionIsEmpty(Collection collection) {
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * 判断集合是否为空
     * @param map map集合
     * @return boolean   是否为空
     */
    public static boolean mapIsEmpty(Map map) {
        return CollectionUtils.isEmpty(map);
    }


}