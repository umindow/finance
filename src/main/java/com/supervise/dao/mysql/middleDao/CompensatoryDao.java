package com.supervise.dao.mysql.middleDao;

import com.supervise.dao.mysql.entity.CompensatoryEntity;
import com.supervise.dao.mysql.mapper.CompensatoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CompensatoryDao")
public class CompensatoryDao {
	
	@Autowired
	private CompensatoryMapper compensatoryMapper;

	/**
	 * 代偿记录插入中间库数据表
	* insertCompensatoryToMiddleDB
	* @param compensatoryEntity 代偿记录
	* @return int 主键ID号
	 */
    public int insertCompensatoryToMiddleDB(CompensatoryEntity compensatoryEntity){
    
    	//将entity中的batchdate做处理，格式：yyyy-MM-dd HH:mm:ss 转换成yyyy-MM-dd
    	int id = -1;
    	if(null!=compensatoryEntity){
    		 String batchDate = compensatoryEntity.getBatchDate().substring(0,10);
			compensatoryEntity.setBatchDate(batchDate);
    		 id= this.compensatoryMapper.insert(compensatoryEntity);
    	}
    	return id;
    }

	/**
	 * 按照指定条件从中间库中查询代偿信息
	 * @param batchDate 批次 查询条件
	 * @return 按照指定查询条件返回的查询结果集合
	 */
	public List<CompensatoryEntity> queryCompensatoryFormMiddleDB(
			String batchDate){
		List<CompensatoryEntity> responseList =
				this.compensatoryMapper.queryCompensatoryFormMiddleDB(batchDate);
		return responseList;
	}
}
