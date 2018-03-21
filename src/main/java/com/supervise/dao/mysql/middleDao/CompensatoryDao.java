package com.supervise.dao.mysql.middleDao;

import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.dao.mysql.entity.CompensatoryEntity;
import com.supervise.dao.mysql.mapper.CompensatoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

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
			compensatoryEntity.setSendStatus(Constants.DATA_READY_SEND);
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

	/**
	 * 删除代偿信息
	 * @param batchDate  条件
	 * @return
	 */
	public void deleteCompensatoryByBatchDate(String batchDate){
		Example example = new Example(CompensatoryEntity.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("batchDate", batchDate);
		this.compensatoryMapper.deleteByExample(example);
	}

	/**
	 * 按照指定条件从中间库中查询代偿信息
	 * @param date
	 * @param qprojId 模糊查询
	 * @return List<CompensatoryEntity>
	 */
	public List<CompensatoryEntity> queryCompensatoryByCondition(String date,String qprojId){

		Example example = new Example(CompensatoryEntity.class);
		Example.Criteria fcriteria = example.createCriteria();
		if (!StringUtils.isEmpty(date)) {
			fcriteria.andEqualTo("batchDate", date);
		}
		if(!StringUtils.isEmpty(qprojId)){
			fcriteria.andLike("projId", Constants.PRE_CENT+qprojId+Constants.PRE_CENT);
		}
		List<CompensatoryEntity> responseList  = this.compensatoryMapper.selectByExample(example);
		return responseList;
	}

	/**
	 * 删除代偿信息
	 * @param batchDate  条件
	 * @return
	 */
	public void deleteCompensatoryByCondition(String batchDate,String orgId,String projId){
		Example example = new Example(CompensatoryEntity.class);
		Example.Criteria ccriteria = example.createCriteria();
		ccriteria.andEqualTo("batchDate", batchDate);
		ccriteria.andEqualTo("orgId", orgId);
		ccriteria.andEqualTo("projId", projId);
		compensatoryMapper.deleteByExample(example);
	}
}
