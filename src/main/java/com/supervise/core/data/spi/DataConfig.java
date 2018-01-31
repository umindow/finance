package com.supervise.core.data.spi;

import com.supervise.core.data.spi.DataMapper;
import lombok.Data;

import java.util.List;

/**
 * Created by xishui.hb on 2018/1/31 上午9:14.
 *  一次数据库 单库操作，直连
 *  不支持?参数类型
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Data
public class DataConfig<D> {
    private String jdbcDriver;//驱动
    private String mysqlJdbcUrl;//数据库链接
    private String sql;//执行的sql
    private String userName;//登录用户名
    private String password;//登录密码

    private DataMapper<D> dataMapper;//数据返回解析
}
