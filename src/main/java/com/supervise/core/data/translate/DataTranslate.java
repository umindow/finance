package com.supervise.core.data.translate;

import com.supervise.controller.vo.DataSet;
import com.supervise.dao.mysql.entity.UserEntity;

import java.util.List;

/**
 * Created by xishui.hb on 2018/3/4 下午3:59.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public interface DataTranslate<T> {

    DataSet translate(List<T> t, int dataType, UserEntity userEntity);
}
