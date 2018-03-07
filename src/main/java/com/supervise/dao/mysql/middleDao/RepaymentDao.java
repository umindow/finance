package com.supervise.dao.mysql.middleDao;

import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.dao.mysql.entity.RepaymentEntity;
import com.supervise.dao.mysql.mapper.RepaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Repository("RepaymentDao")
public class RepaymentDao{
	
	@Autowired
	private RepaymentMapper repaymentMapper;

	/**
	 * 还款记录插入中间库数据表
	* insertRepayment 
	* @param repaymentEntity 还款记录
	* @return int 主键ID号
	 */
    public int insertRepaymentToMiddleDB(RepaymentEntity repaymentEntity){
    
    	//将entity中的batchdate做处理，格式：yyyy-MM-dd HH:mm:ss 转换成yyyy-MM-dd
    	int id = -1;
    	if(null!=repaymentEntity){
			String batchDate = repaymentEntity.getBatchDate().substring(0,10);
			repaymentEntity.setBatchDate(batchDate);
			String dateStr = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(new Date());
			Date newDate = DateUtils.String2Date(dateStr,Constants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
			repaymentEntity.setCreateDate(newDate);
			repaymentEntity.setUpdateDate(newDate);
			id= this.repaymentMapper.insert(repaymentEntity);
    	}
    	return id;
    }

	/**
	 * 按照指定条件从中间库中查询还款信息
	 * @param batchDate 批次 查询条件
	 * @return 按照指定查询条件返回的查询结果集合
	 */
	public List<RepaymentEntity> queryRepaymentFormMiddleDB(
			String batchDate){
		List<RepaymentEntity> responseList =
				this.repaymentMapper.queryRepaymentFormMiddleDB(batchDate);
		return responseList;
	}

	/**
	 * 按照指定条件从中间库中查询还款信息
	 * @param queryCondition  查询条件
	 * @return 按照指定查询条件返回的查询结果集合
	 */
	public List<RepaymentEntity> queryRepaymentByCondition(
			QueryCondition queryCondition){
		List<RepaymentEntity> responseList =
				this.repaymentMapper.queryRepaymentByConditions(queryCondition);
		return responseList;
	}

	/**
	 * 更新还款信息
	 * @param repaymentEntity
	 * int id
	 */
	public  int updateRepayment(RepaymentEntity repaymentEntity){
		int id = -1;
		if(null!=repaymentEntity){
			String dateStr = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(new Date());
			Date newDate = DateUtils.String2Date(dateStr,Constants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
			repaymentEntity.setUpdateDate(newDate);
			id = this.repaymentMapper.updateByPrimaryKeySelective(repaymentEntity);
		}
		return id;
	}

	/**
	 * 删除还款信息
	 * @param repaymentEntity
	 * int id
	 */
	public  int deleteRepayment(RepaymentEntity repaymentEntity){
		int id = -1;
		if(null!=repaymentEntity){
			id = this.repaymentMapper.delete(repaymentEntity);
		}
		return id;
	}

	/**
	 * 根据ID主键删除还款信息
	 * @param key
	 * int
	 */
	public  int deleteRepaymentByID(Long key){
		int id = -1;
		if(null!=key){
			id = this.repaymentMapper.deleteByPrimaryKey(key);
		}
		return id;
	}

	/**
	 * 按照指定条件从中间库中查询还款信息
	 * @param id  查询条件
	 * @return 按照指定查询条件返回的查询结果集合
	 */
	public RepaymentEntity queryRepaymentByKey(Long id){
		RepaymentEntity res  =
				this.repaymentMapper.selectByPrimaryKey(id);
		return res;
	}

	/**
	 * 删除还款信息
	 * @param batchDate  条件
	 * @return
	 */
	public void deleteRepaymentByBatchDate(String batchDate){
		Example example = new Example(RepaymentEntity.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("batchDate", batchDate);
		this.repaymentMapper.deleteByExample(example);
	}
}
