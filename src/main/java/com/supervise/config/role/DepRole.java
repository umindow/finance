package com.supervise.config.role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xishui.hb on 2018/2/6 上午9:46.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DepRole {
    /**
     * 字段属于哪些部门
     * @return
     */
    DepType[] depTypes() default DepType.ALL;

    /**
     * 是否有修改权限，默认可以修改
     * @return
     */
    boolean modify() default true;
}
