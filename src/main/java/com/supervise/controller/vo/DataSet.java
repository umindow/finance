package com.supervise.controller.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by xishui.hb on 2018/3/4 下午3:26.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Data
public class DataSet {

    private List<String> fields;

    private List<DataVo> dataVos;
    public DataSet(List<String> fields) {
        this.fields = fields;
    }

}
