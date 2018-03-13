package com.supervise.dao.mysql.viewmapper;

import com.supervise.config.mysql.base.BaseMapper;
import com.supervise.dao.mysql.entity.RepaymentEntity;
import com.supervise.dao.mysql.entity.ViewRepaymentEntity;
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
     * @return List<ViewRepaymentEntity>
     */
    @Results({
            @Result(column = "机构编码", property = "orgId"),
            @Result(column = "项目编码", property = "projId"),
            @Result(column = "合同编码", property = "contractId"),
            @Result(column = "实际还款日期", property = "repayDate"),
            @Result(column = "实际归还本金", property = "principal"),
            @Result(column = "批次", property = "batchDate")
    })
    @SelectProvider(type = RepaymentProvider.class, method = "queryRepaymentView")
    List<ViewRepaymentEntity> queryRepaymentView(String batchDate);
}
