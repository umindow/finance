package com.supervise.dao.mysql.middleDao;

import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.dao.mysql.entity.FeeAndRefundEntity;
import com.supervise.dao.mysql.mapper.FeeAndRefundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Repository("FeeAndRefundDao")
public class FeeAndRefundDao {
	
	@Autowired
	private FeeAndRefundMapper feeAndRefundMapper;

	/**
	 * 收费以及退费记录插入中间库数据表
	* insertFeeAndRefundToMiddleDB
	* @param feeAndRefundEntity 还款记录
	* @return int 主键ID号
	 */
    public int insertFeeAndRefundToMiddleDB(FeeAndRefundEntity feeAndRefundEntity){
    
    	//将entity中的batchdate做处理，格式：yyyy-MM-dd HH:mm:ss 转换成yyyy-MM-dd
    	int id = -1;
    	if(null!=feeAndRefundEntity){
			String batchDate = feeAndRefundEntity.getBatchDate().substring(0,10);
			feeAndRefundEntity.setBatchDate(batchDate);
			String dateStr = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(new Date());
			Date newDate = DateUtils.String2Date(dateStr, Constants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
			feeAndRefundEntity.setCreateDate(newDate);
			feeAndRefundEntity.setUpdateDate(newDate);
			id= this.feeAndRefundMapper.insert(feeAndRefundEntity);
    	}
    	return id;
    }

	/**
	 * 按照指定条件从中间库中查询收费以及退费信息
	 * @param batchDate 批次 查询条件
	 * @return 按照指定查询条件返回的查询结果集合
	 */
	public List<FeeAndRefundEntity> queryFeeAndRefundFormMiddleDB(
			String batchDate){
		List<FeeAndRefundEntity> responseList =
				this.feeAndRefundMapper.queryFeeAndRefundFormMiddleDB(batchDate);
		return responseList;
	}

	/**
	 * 按照指定条件从中间库中查询收退费信息
	 * @param queryCondition  查询条件
	 * @return 按照指定查询条件返回的查询结果集合
	 */
	public List<FeeAndRefundEntity> queryFeeAndRefundByCondition(
			QueryCondition queryCondition){
		List<FeeAndRefundEntity> responseList =
				this.feeAndRefundMapper.queryFeeAndRefundByConditions(queryCondition);
		return responseList;
	}

	/**
	 * 更新收退费信息
	 * @param feeAndRefundEntity
	 * int id
	 */
	public  int updateFeeAndRefund(FeeAndRefundEntity feeAndRefundEntity){
		int id = -1;
		if(null!=feeAndRefundEntity){
			String dateStr = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(new Date());
			Date newDate = DateUtils.String2Date(dateStr,Constants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
			feeAndRefundEntity.setUpdateDate(newDate);
			id = this.feeAndRefundMapper.updateByPrimaryKeySelective(feeAndRefundEntity);
		}
		return id;
	}

	/**
	 * 删除收退费信息
	 * @param feeAndRefundEntity
	 * int id
	 */
	public  int deleteFeeAndRefund(FeeAndRefundEntity feeAndRefundEntity){
		int id = -1;
		if(null!=feeAndRefundEntity){
			id = this.feeAndRefundMapper.delete(feeAndRefundEntity);
		}
		return id;
	}

	/**
	 * 根据ID主键删除收退费信息
	 * @param key
	 * int
	 */
	public  int deleteFeeAndRefundByID(Long key){
		int id = -1;
		if(null!=key){
			id = this.feeAndRefundMapper.deleteByPrimaryKey(key);
		}
		return id;
	}
}
