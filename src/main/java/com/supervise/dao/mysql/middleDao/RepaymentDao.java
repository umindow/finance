package com.supervise.dao.mysql.middleDao;

import com.supervise.dao.mysql.entity.RepaymentEntity;
import com.supervise.dao.mysql.mapper.RepaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
