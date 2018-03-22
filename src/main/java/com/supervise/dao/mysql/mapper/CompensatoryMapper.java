package com.supervise.dao.mysql.mapper;

import com.supervise.config.mysql.base.BaseMapper;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.dao.mysql.entity.CompensatoryEntity;
import com.supervise.dao.mysql.provider.CompensatoryProvider;
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
public interface CompensatoryMapper extends BaseMapper<CompensatoryEntity>{

    /**
     * 按照指定条件从中间库查询代偿信息
     * @param batchDate 批次
     * @return List<CompensatoryEntity>
     */
    @Results({
            @Result(column = "org_id", property = "orgId"),
            @Result(column = "proj_id", property = "projId"),
            @Result(column = "contract_id", property = "contractId"),
            @Result(column = "replace_date", property = "replaceDate"),
            @Result(column = "replace_money", property = "replaceMoney"),
            @Result(column = "batch_date", property = "batchDate"),
            @Result(column = "create_date", property = "createDate"),
            @Result(column = "update_date", property = "updateDate"),
            @Result(column = "sendStatus", property = "sendStatus")
    })
    @SelectProvider(type = CompensatoryProvider.class, method = "queryCompensatoryFormMiddleDB")
    List<CompensatoryEntity> queryCompensatoryFormMiddleDB(String batchDate);

    /**
     * 按照指定条件从中间库查询代偿信息
     * @param queryCondition 查询条件
     * @return List<CompensatoryEntity>
     */
    @Results({
            @Result(column = "org_id", property = "orgId"),
            @Result(column = "proj_id", property = "projId"),
            @Result(column = "contract_id", property = "contractId"),
            @Result(column = "replace_date", property = "replaceDate"),
            @Result(column = "replace_money", property = "replaceMoney"),
            @Result(column = "batch_date", property = "batchDate"),
            @Result(column = "create_date", property = "createDate"),
            @Result(column = "update_date", property = "updateDate"),
            @Result(column = "sendStatus", property = "sendStatus")
    })
    @SelectProvider(type = CompensatoryProvider.class, method = "queryCompensatoryByConditions")
    List<CompensatoryEntity> queryCompensatoryByConditions(QueryCondition queryCondition);
}
