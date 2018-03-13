package com.supervise.dao.mysql.viewmapper;

import com.supervise.config.mysql.base.BaseMapper;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.entity.ViewBankCreditEntity;
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
     * @return List<ViewBankCreditEntity>
     */
    @Results({
            @Result(column = "银行授信记录标识", property = "primaryId"),
            @Result(column = "机构编码", property = "orgId"),
            @Result(column = "银行编号", property = "bankId"),
            @Result(column = "授信类型", property = "creditTypeId"),
            @Result(column = "授信额度", property = "creditMoney"),
            @Result(column = "保证金比例", property = "bailScale"),
            @Result(column = "授信开始日期", property = "creditStartDate"),
            @Result(column = "授信结束日期", property = "creditEndDate"),
            @Result(column = "单笔限额", property = "singleMoneyLimit"),
            @Result(column = "是否循环授信", property = "isForCredit"),
            @Result(column = "批次", property = "batchDate")
    })
    @SelectProvider(type = BankCreditProvider.class, method = "queryBankCreditView")
    List<ViewBankCreditEntity> queryBankCreditView(String batchDate);
}
