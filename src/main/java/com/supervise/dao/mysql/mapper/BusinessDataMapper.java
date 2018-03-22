package com.supervise.dao.mysql.mapper;

import com.supervise.config.mysql.base.BaseMapper;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.provider.BusinessDataProvider;
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
public interface BusinessDataMapper extends BaseMapper<BusinessDataEntity>{

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
            @Result(column = "client_name", property = "clientName"),
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
            @Result(column = "loan_rate", property = "loanRate"),
            @Result(column = "assure_rate", property = "assureRate"),
            @Result(column = "loan_date", property = "loanDate"),
            @Result(column = "contract_end_date", property = "contractEndDate"),
            @Result(column = "repay_type_id", property = "repayType"),
            @Result(column = "pledge_type_id", property = "pledgeType"),
            @Result(column = "approve_option", property = "approveOption"),
            @Result(column = "bank_credit_primary_id", property = "bankCreditPrimaryId"),
            @Result(column = "co_bank_id", property = "coBankId"),
            @Result(column = "proj_status", property = "projSatus"),
            @Result(column = "assure_person", property = "assurePerson"),
            @Result(column = "pledge_worth", property = "pledgeWorth"),
            @Result(column = "is_impawn", property = "isImpawn"),
            @Result(column = "accept_date", property = "acceptDate"),
            @Result(column = "contract_id", property = "contractId"),
            @Result(column = "client_bail_money", property = "clientBailMoney"),
            @Result(column = "out_bail_money", property = "outBailMoney"),
            @Result(column = "capital_belong", property = "capitalBelong"),
            @Result(column = "proj_end_date", property = "projEndDate"),
            @Result(column = "batch_date", property = "batchDate"),
            @Result(column = "create_date", property = "createDate"),
            @Result(column = "update_date", property = "updateDate"),
            @Result(column = "sendStatus", property = "sendStatus")
    })
    @SelectProvider(type = BusinessDataProvider.class, method = "queryBusinessDataFormMiddleDB")
    List<BusinessDataEntity> queryBusinessDataFormMiddleDB(String batchDate);

        /**
         * 按照指定条件查询业务数据信息
         * @param queryCondition 查询条件
         * @return List<BusinessDataEntity>
         */
        @Results({
                @Result(column = "org_id", property = "orgId"),
                @Result(column = "proj_id", property = "projId"),
                @Result(column = "client_type", property = "clientType"),
                @Result(column = "client_id", property = "clientId"),
                @Result(column = "client_name", property = "clientName"),
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
                @Result(column = "loan_rate", property = "loanRate"),
                @Result(column = "assure_rate", property = "assureRate"),
                @Result(column = "loan_date", property = "loanDate"),
                @Result(column = "contract_end_date", property = "contractEndDate"),
                @Result(column = "repay_type_id", property = "repayType"),
                @Result(column = "pledge_type_id", property = "pledgeType"),
                @Result(column = "approve_option", property = "approveOption"),
                @Result(column = "bank_credit_primary_id", property = "bankCreditPrimaryId"),
                @Result(column = "co_bank_id", property = "coBankId"),
                @Result(column = "proj_status", property = "projSatus"),
                @Result(column = "assure_person", property = "assurePerson"),
                @Result(column = "pledge_worth", property = "pledgeWorth"),
                @Result(column = "is_impawn", property = "isImpawn"),
                @Result(column = "accept_date", property = "acceptDate"),
                @Result(column = "contract_id", property = "contractId"),
                @Result(column = "client_bail_money", property = "clientBailMoney"),
                @Result(column = "out_bail_money", property = "outBailMoney"),
                @Result(column = "capital_belong", property = "capitalBelong"),
                @Result(column = "proj_end_date", property = "projEndDate"),
                @Result(column = "batch_date", property = "batchDate"),
                @Result(column = "create_date", property = "createDate"),
                @Result(column = "update_date", property = "updateDate"),
                @Result(column = "sendStatus", property = "sendStatus")
        })
        @SelectProvider(type = BusinessDataProvider.class, method = "queryBusinessDataByConditions")
        List<BusinessDataEntity> queryBusinessDataByConditions(QueryCondition queryCondition);
}
