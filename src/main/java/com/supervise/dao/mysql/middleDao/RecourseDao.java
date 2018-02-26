package com.supervise.dao.mysql.middleDao;

import com.supervise.dao.mysql.entity.RecourseEntity;
import com.supervise.dao.mysql.mapper.RecourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RecourseDao")
public class RecourseDao {
	
	@Autowired
	private RecourseMapper recourseMapper ;

	/**
	 * 还款记录插入中间库数据表
	* insertRecourseToMiddleDB
	* @param recourseEntity 追偿记录
	* @return int 主键ID号
	 */
    public int insertRecourseToMiddleDB(RecourseEntity recourseEntity){
    
    	//将entity中的batchdate做处理，格式：yyyy-MM-dd HH:mm:ss 转换成yyyy-MM-dd
    	int id = -1;
    	if(null!=recourseEntity){
    		 String batchDate = recourseEntity.getBatchDate().substring(0,10);
			recourseEntity.setBatchDate(batchDate);
    		 id= this.recourseMapper.insert(recourseEntity);
    	}
    	return id;
    }

	/**
	 * 按照指定条件从中间库中查询追偿信息
	 * @param batchDate 批次 查询条件
	 * @return 按照指定查询条件返回的查询结果集合
	 */
	public List<RecourseEntity> queryRecourseFormMiddleDB(
			String batchDate){
		List<RecourseEntity> responseList =
				this.recourseMapper.queryRecourseFormMiddleDB(batchDate);
		return responseList;
	}
}
