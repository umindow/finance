package com.supervise.dao.mysql.viewmapper;

import com.supervise.config.mysql.base.BaseMapper;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.entity.ViewBusinessDataEntity;
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
            @Result(column = "机构编码", property = "orgId"),
            @Result(column = "项目编码", property = "projId"),
            @Result(column = "客户类型", property = "clientType"),
            @Result(column = "客户编码", property = "clientId"),
            @Result(column = "客户名称", property = "clienName"),
            @Result(column = "证件类型", property = "iDCardType"),
            @Result(column = "证件号码", property = "iDCard"),
            @Result(column = "所属行业编码一级", property = "callingFirst"),
            @Result(column = "所属行业编码二级", property = "callingSecond"),
            @Result(column = "所属区域一级", property = "areaFirst"),
            @Result(column = "所属区域二级", property = "areaSecond"),
            @Result(column = "所属区域三级", property = "areaThird"),
            @Result(column = "客户规模编码", property = "companyScale"),
            @Result(column = "是否涉农", property = "isFarming"),
            @Result(column = "业务类型", property = "businessType"),
            @Result(column = "合同金额", property = "contractMoney"),
            @Result(column = "已放款金额", property = "loanMoney"),
            @Result(column = "担保综合费率", property = "assureRate"),
            @Result(column = "放款日期", property = "loanDate"),
            @Result(column = "还款方式", property = "repayType"),
            @Result(column = "反担保措施", property = "pledgeType"),
            @Result(column = "项目状态", property = "projSatus"),
            @Result(column = "受理时间", property = "acceptDate"),
            @Result(column = "合同编号", property = "contractId"),
            @Result(column = "资本属性", property = "capitalBelong"),
            @Result(column = "年初余额", property = "initialBalance"),
            @Result(column = "首次放款时间", property = "firstLoanDate"),
            @Result(column = "批次", property = "batchDate")
    })
    @SelectProvider(type = BusinessDataProvider.class, method = "queryBusinessDataView")
    List<ViewBusinessDataEntity> queryBusinessDataView(String batchDate);
}
