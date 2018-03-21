package com.supervise.dao.mysql.middleDao;

import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.dao.mysql.entity.FeeAndRefundEntity;
import com.supervise.dao.mysql.mapper.FeeAndRefundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

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
			feeAndRefundEntity.setSendStatus(Constants.DATA_READY_SEND);
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

	/**
	 * 按照指定条件从中间库中查询收退费信息
	 * @param id  查询条件
	 * @return 按照指定查询条件返回的查询结果集合
	 */
	public FeeAndRefundEntity queryFeeAndRefundByKey(Long id){
		FeeAndRefundEntity res  =
				this.feeAndRefundMapper.selectByPrimaryKey(id);
		return res;
	}

	/**
	 * 删除收退费信息
	 * @param batchDate  条件
	 * @return
	 */
	public void deleteFeeAndRefundByBatchDate(String batchDate){
		Example example = new Example(FeeAndRefundEntity.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("batchDate", batchDate);
		this.feeAndRefundMapper.deleteByExample(example);
	}

	/**
	 * 按照指定条件从中间库中查询收退费信息
	 * @param date
	 * @param qprojId 模糊查询
	 * @return List<FeeAndRefundEntity>
	 */
	public List<FeeAndRefundEntity> queryFeeAndRefundByCondition(String date,String qprojId){

		Example example = new Example(FeeAndRefundEntity.class);
		Example.Criteria fcriteria = example.createCriteria();
		if (!StringUtils.isEmpty(date)) {
			fcriteria.andEqualTo("batchDate", date);
		}
		if(!StringUtils.isEmpty(qprojId)){
			fcriteria.andLike("projId", Constants.PRE_CENT+qprojId+Constants.PRE_CENT);
		}
		List<FeeAndRefundEntity> responseList  = this.feeAndRefundMapper.selectByExample(example);
		return responseList;
	}

	/**
	 * 删除收退费信息
	 * @param batchDate  条件
	 * @return
	 */
	public void deleteFeeAndRefundByCondition(String batchDate,String orgId,String projId){
		Example example = new Example(FeeAndRefundEntity.class);
		Example.Criteria fcriteria = example.createCriteria();
		fcriteria.andEqualTo("batchDate", batchDate);
		fcriteria.andEqualTo("orgId", orgId);
		fcriteria.andEqualTo("projId", projId);
		this.feeAndRefundMapper.deleteByExample(example);
	}
}
