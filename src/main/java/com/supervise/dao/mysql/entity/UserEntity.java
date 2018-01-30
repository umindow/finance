package com.supervise.dao.mysql.entity;

import com.supervise.config.mysql.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by xishui on 2018/1/30 下午1:52.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Data
@Table(name = "finance_user")
public class UserEntity extends BaseEntity{
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "user_cn_name")
    private String userCnName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "level")
    private int level;// level may appar role=>rule
    @Column(name = "data_levels")
    private String dataLevels;//json(List<Integer)
}
