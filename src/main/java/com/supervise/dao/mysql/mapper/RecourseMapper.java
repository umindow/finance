package com.supervise.dao.mysql.mapper;

import com.supervise.config.mysql.base.BaseMapper;
import com.supervise.dao.mysql.entity.RecourseEntity;
import com.supervise.dao.mysql.provider.RecourseProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by xishui.hb on 2018/2/6 上午10:32.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public interface RecourseMapper extends BaseMapper<RecourseEntity>{

    /**
     * 按照指定条件从中间库查询还款信息
     * @param batchDate 批次
     * @return List<RecourseEntity>
     */
    @Results({
            @Result(column = "org_id", property = "orgId"),
            @Result(column = "proj_id", property = "projId"),
            @Result(column = "contract_id", property = "contractId"),
            @Result(column = "replevy_type", property = "replevyType"),
            @Result(column = "replevy_date", property = "replevyDate"),
            @Result(column = "replevy_money", property = "replevyMoney"),
            @Result(column = "batch_date", property = "batchDate")
    })
    @SelectProvider(type = RecourseProvider.class, method = "queryRecourseFormMiddleDB")
    List<RecourseEntity> queryRecourseFormMiddleDB(String batchDate);
}
