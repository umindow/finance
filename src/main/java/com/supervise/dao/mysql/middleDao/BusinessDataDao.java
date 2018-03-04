package com.supervise.dao.mysql.middleDao;

import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.mapper.BusinessDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Repository("BusinessDataDao")
public class BusinessDataDao {
	
	@Autowired
	private BusinessDataMapper businessDataMapper;

	/**
	 * 还款记录插入中间库数据表
	* insertBusinessDataToMiddleDB
	* @param businessDataEntity 还款记录
	* @return int 主键ID号
	 */
    public int insertBusinessDataToMiddleDB(BusinessDataEntity businessDataEntity){
    
    	//将entity中的batchdate做处理，格式：yyyy-MM-dd HH:mm:ss 转换成yyyy-MM-dd
    	int id = -1;
    	if(null!=businessDataEntity){
			String batchDate = businessDataEntity.getBatchDate().substring(0,10);
			businessDataEntity.setBatchDate(batchDate);
			String dateStr = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(new Date());
			Date newDate = DateUtils.String2Date(dateStr, Constants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
			businessDataEntity.setCreateDate(newDate);
			businessDataEntity.setUpdateDate(newDate);
			id= this.businessDataMapper.insert(businessDataEntity);
    	}
    	return id;
    }

	/**
	 * 按照指定条件从视图中查询业务数据信息
	 * @param batchDate 批次 查询条件
	 * @return 按照指定查询条件返回的查询结果集合
	 */
	public List<BusinessDataEntity> queryBusinessDataFormMiddleDB(
			String batchDate){
		List<BusinessDataEntity> responseList =
				this.businessDataMapper.queryBusinessDataFormMiddleDB(batchDate);
		return responseList;
	}

	/**
	 * 按照指定条件从中间库中查询业务数据信息
	 * @param queryCondition  查询条件
	 * @return 按照指定查询条件返回的查询结果集合
	 */
	public List<BusinessDataEntity> queryBusinessDataByCondition(
			QueryCondition queryCondition){
		List<BusinessDataEntity> responseList =
				this.businessDataMapper.queryBusinessDataByConditions(queryCondition);
		return responseList;
	}

	/**
	 * 更新业务数据信息
	 * @param businessDataEntity
	 * int id
	 */
	public  int updateBankCredit(BusinessDataEntity businessDataEntity){
		int id = -1;
		if(null!=businessDataEntity){
			String dateStr = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(new Date());
			Date newDate = DateUtils.String2Date(dateStr,Constants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
			businessDataEntity.setUpdateDate(newDate);
			id = this.businessDataMapper.updateByPrimaryKeySelective(businessDataEntity);
		}
		return id;
	}

	/**
	 * 删除业务数据
	 * @param businessDataEntity
	 * int id
	 */
	public  int deleteRepayment(BusinessDataEntity businessDataEntity){
		int id = -1;
		if(null!=businessDataEntity){
			id = this.businessDataMapper.delete(businessDataEntity);
		}
		return id;
	}

	/**
	 * 根据ID主键删除业务数据信息
	 * @param key
	 * int
	 */
	public  int deleteRepaymentByID(Long key){
		int id = -1;
		if(null!=key){
			id = this.businessDataMapper.deleteByPrimaryKey(key);
		}
		return id;
	}

}
