package com.supervise.core.data.spi;

import java.sql.ResultSet;

/**
 * Created by xishui.hb on 2018/1/31 上午9:24.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public interface DataMapper <D>{

    D mapper(ResultSet resultSet);
}
