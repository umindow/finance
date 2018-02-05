package com.supervise.dao.mysql.entity;

import com.supervise.config.mysql.base.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by xishui.hb on 2018/2/2 下午12:23.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Data
@Table(name = "finance_config")
public class ConfigEntity extends BaseEntity{
    @Column(name = "config_type")
    private int configType;//配置类型 1:调度任务配置类型
    @Column(name = "create_user_id")
    private long createUserId;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "update_user_id")
    private long updateUserId;
    @Column(name = "config_content")
    private String configContent;//json(entity) jobInfo;

    public enum ConfigType {
        JOB_TYPE(1,"调度任务配置");
        @Getter
        @Setter
        private int type;
        @Getter
        @Setter
        private String desc;
        ConfigType(int type,String desc) {
            this.type = type;
            this.desc = desc;
        }
        public static String getConfigTypeDesc(int configType){
            for(final ConfigType config:values()){
                if(config.getType() == configType){
                    return config.getDesc();
                }
            }
            return "未知类型";
        }
    }

}
