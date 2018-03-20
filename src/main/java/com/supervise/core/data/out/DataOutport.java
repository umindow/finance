package com.supervise.core.data.out;

import com.supervise.config.role.DataType;
import com.supervise.controller.vo.ViewVo;
import com.supervise.dao.mysql.entity.UserEntity;

import javax.servlet.ServletOutputStream;

/**
 * Created by xishui.hb on 2018/3/5 上午9:20.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public interface DataOutport {

    void export(ServletOutputStream servletOutputStream, DataType dataType, ViewVo viewVo, UserEntity userEntity) throws Exception;
}
