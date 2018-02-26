package com.supervise.dao.mysql.viewmapper;

import com.supervise.config.mysql.base.BaseMapper;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.provider.BankCreditProvider;
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
public interface ViewBankCreditMapper extends BaseMapper<BankCreditEntity>{
	
	/**
     * 按照指定条件查询银行授信信息
     * @param batchDate 批次
     * @return List<BankCreditEntity>
     */
    @Results({
            @Result(column = "primary_id", property = "primaryId"),
            @Result(column = "org_id", property = "orgId"),
            @Result(column = "bank_id", property = "bankId"),
            @Result(column = "credit_type_id", property = "creditTypeId"),
            @Result(column = "credit_money", property = "creditMoney"),
            @Result(column = "bail_scale", property = "bailScale"),
            @Result(column = "credit_start_date", property = "creditStartDate"),
            @Result(column = "credit_end_date", property = "creditEndDate"),
            @Result(column = "single_money_limit", property = "singleMoneyLimit"),
            @Result(column = "is_for_credit", property = "isForCredit"),
            @Result(column = "batch_date", property = "batchDate")
    })
    @SelectProvider(type = BankCreditProvider.class, method = "queryBankCreditView")
    List<BankCreditEntity> queryBankCreditView(String batchDate);
}
