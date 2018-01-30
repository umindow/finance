package com.supervise.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by xishui.hb on 2018/1/30 下午4:46.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Data
public class Login implements Serializable{
    private String userName;
    private String password;
}
