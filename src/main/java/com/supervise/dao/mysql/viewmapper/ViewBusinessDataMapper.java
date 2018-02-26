package com.supervise.dao.mysql.viewmapper;

import com.supervise.config.mysql.base.BaseMapper;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.provider.BusinessDataProvider;
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
public interface ViewBusinessDataMapper extends BaseMapper<BusinessDataEntity>{
	
	/**
     * 按照指定条件查询业务数据信息
     * @param batchDate 批次
     * @return List<BusinessDataEntity>
     */
    @Results({
            @Result(column = "org_id", property = "orgId"),
            @Result(column = "proj_id", property = "projId"),
            @Result(column = "client_type", property = "clientType"),
            @Result(column = "client_id", property = "clientId"),
            @Result(column = "client_name", property = "clienName"),
            @Result(column = "id_card_type", property = "iDCardType"),
            @Result(column = "id_card", property = "iDCard"),
            @Result(column = "calling_first", property = "callingFirst"),
            @Result(column = "calling_second", property = "callingSecond"),
            @Result(column = "area_first", property = "areaFirst"),
            @Result(column = "area_second", property = "areaSecond"),
            @Result(column = "area_third", property = "areaThird"),
            @Result(column = "company_scale", property = "companyScale"),
            @Result(column = "is_farming", property = "isFarming"),
            @Result(column = "business_type", property = "businessType"),
            @Result(column = "contract_money", property = "contractMoney"),
            @Result(column = "loan_money", property = "loanMoney"),
            @Result(column = "assure_rate", property = "assureRate"),
            @Result(column = "loan_date", property = "loanDate"),
            @Result(column = "repay_type", property = "repayType"),
            @Result(column = "pledge_type", property = "pledgeType"),
            @Result(column = "proj_status", property = "projSatus"),
            @Result(column = "accept_date", property = "acceptDate"),
            @Result(column = "contract_id", property = "contractId"),
            @Result(column = "capital_belong", property = "capitalBelong"),
            @Result(column = "initial_balance", property = "initialBalance"),
            @Result(column = "first_loan_date", property = "firstLoanDate"),
            @Result(column = "batch_date", property = "batchDate")
    })
    @SelectProvider(type = BusinessDataProvider.class, method = "queryBusinessDataView")
    List<BusinessDataEntity> queryBusinessDataView(String batchDate);
}
