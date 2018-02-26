package com.supervise.dao.mysql.middleDao;

import com.supervise.dao.mysql.entity.FeeAndRefundEntity;
import com.supervise.dao.mysql.mapper.FeeAndRefundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
