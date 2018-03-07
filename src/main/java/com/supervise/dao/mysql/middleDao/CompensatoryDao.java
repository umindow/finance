package com.supervise.dao.mysql.middleDao;

import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.dao.mysql.entity.CompensatoryEntity;
import com.supervise.dao.mysql.mapper.CompensatoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
			String dateStr = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(new Date());
			Date newDate = DateUtils.String2Date(dateStr, Constants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
			compensatoryEntity.setCreateDate(newDate);
			compensatoryEntity.setUpdateDate(newDate);
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

	/**
	 * 按照指定条件从中间库中查询代偿信息
	 * @param queryCondition  查询条件
	 * @return 按照指定查询条件返回的查询结果集合
	 */
	public List<CompensatoryEntity> queryCompensatoryByCondition(
			QueryCondition queryCondition){
		List<CompensatoryEntity> responseList =
				this.compensatoryMapper.queryCompensatoryByConditions(queryCondition);
		return responseList;
	}

	/**
	 * 更新代偿信息
	 * @param compensatoryEntity
	 * int id
	 */
	public  int updateCompensatory(CompensatoryEntity compensatoryEntity){
		int id = -1;
		if(null!=compensatoryEntity){
			String dateStr = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(new Date());
			Date newDate = DateUtils.String2Date(dateStr,Constants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
			compensatoryEntity.setUpdateDate(newDate);
			id = this.compensatoryMapper.updateByPrimaryKeySelective(compensatoryEntity);
		}
		return id;
	}

	/**
	 * 删除代偿信息
	 * @param compensatoryEntity
	 * int id
	 */
	public  int deleteCompensatory(CompensatoryEntity compensatoryEntity){
		int id = -1;
		if(null!=compensatoryEntity){
			id = this.compensatoryMapper.delete(compensatoryEntity);
		}
		return id;
	}

	/**
	 * 根据ID主键删除代偿信息
	 * @param key
	 * int
	 */
	public  int deleteCompensatoryByID(Long key){
		int id = -1;
		if(null!=key){
			id = this.compensatoryMapper.deleteByPrimaryKey(key);
		}
		return id;
	}

	/**
	 * 按照指定条件从中间库中查询代偿信息
	 * @param id  查询条件
	 * @return 按照指定查询条件返回的查询结果集合
	 */
	public CompensatoryEntity queryCompensatoryByKey(Long id){
		CompensatoryEntity res  =
				this.compensatoryMapper.selectByPrimaryKey(id);
		return res;
	}
}
