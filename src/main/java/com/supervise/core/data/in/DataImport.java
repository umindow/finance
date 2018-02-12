package com.supervise.core.data.in;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by xishui.hb on 2018/2/12 下午3:14.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public interface DataImport{

    void in(MultipartFile file) throws Exception;

}
