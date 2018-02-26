package com.supervise.dao.mysql.viewmapper;

import com.supervise.config.mysql.base.BaseMapper;
import com.supervise.dao.mysql.entity.RepaymentEntity;
import com.supervise.dao.mysql.provider.RepaymentProvider;
import org.apache.ibatis.annotations.Mapper;
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
@Mapper
public interface ViewRepaymentMapper extends BaseMapper<RepaymentEntity>{
	
	/**
     * 按照指定条件查询还款信息
     * @param batchDate 批次
     * @return List<RepaymentEntity>
     */
    @Results({
            @Result(column = "org_id", property = "orgId"),
            @Result(column = "proj_id", property = "projId"),
            @Result(column = "contract_id", property = "contractId"),
            @Result(column = "repay_date", property = "repayDate"),
            @Result(column = "principal", property = "principal"),
            @Result(column = "batch_date", property = "batchDate")
    })
    @SelectProvider(type = RepaymentProvider.class, method = "queryRepaymentView")
    List<RepaymentEntity> queryRepaymentView(String batchDate);
}
