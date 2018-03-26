package com.supervise.dao.mysql.middleDao;

import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.mapper.BusinessDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

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
    public   int insertBusinessDataToMiddleDB(BusinessDataEntity businessDataEntity){
    
    	//将entity中的batchdate做处理，格式：yyyy-MM-dd HH:mm:ss 转换成yyyy-MM-dd
    	int id = -1;
    	if(null!=businessDataEntity){
			String batchDate = businessDataEntity.getBatchDate().substring(0,10);
			businessDataEntity.setBatchDate(batchDate);
			String dateStr = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(new Date());
			Date newDate = DateUtils.String2Date(dateStr, Constants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
			businessDataEntity.setCreateDate(newDate);
			businessDataEntity.setUpdateDate(newDate);
			businessDataEntity.setSendStatus(Constants.DATA_READY_SEND);
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
	public   int updateBusinessData(BusinessDataEntity businessDataEntity){
		int id = -1;
		if(null!=businessDataEntity){
			String dateStr = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(new Date());
			Date newDate = DateUtils.String2Date(dateStr,Constants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
			businessDataEntity.setUpdateDate(newDate);
			id = this.businessDataMapper.updateByPrimaryKey(businessDataEntity);
		}
		return id;
	}

	/**
	 * 删除业务数据
	 * @param businessDataEntity
	 * int id
	 */
	public   int deleteBusinessData(BusinessDataEntity businessDataEntity){
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
	public   int deleteBusinessDataByID(Long key){
		int id = -1;
		if(null!=key){
			id = this.businessDataMapper.deleteByPrimaryKey(key);
		}
		return id;
	}

	/**
	 * 按照指定条件从中间库中查询业务数据信息
	 * @param id  查询条件
	 * @return 按照指定查询条件返回的查询结果集合
	 */
	public BusinessDataEntity queryBusinessDataByKey(Long id){
		BusinessDataEntity res  =
				this.businessDataMapper.selectByPrimaryKey(id);
		return res;
	}

	/**
	 * 删除业务数据信息
	 * @param batchDate  条件
	 * @return
	 */
	public   void deleteBusinessDataByBatchDate(String batchDate){
		Example example = new Example(BusinessDataEntity.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("batchDate", batchDate);
		this.businessDataMapper.deleteByExample(example);
	}

	/**
	 * 按照指定条件从中间库中查询业务信息
	 * @param date
	 * @param qprojId 模糊查询
	 * @param clientName 模糊查询
	 * @return List<BusinessDataEntity>
	 */
	public List<BusinessDataEntity> queryBusinessDataByCondition(String date,String qprojId,String clientName){

		Example example = new Example(BusinessDataEntity.class);
		Example.Criteria fcriteria = example.createCriteria();
		if (!StringUtils.isEmpty(date)) {
			fcriteria.andEqualTo("batchDate", date);
		}
		if(!StringUtils.isEmpty(qprojId)){
			fcriteria.andLike("projId", Constants.PRE_CENT+qprojId+Constants.PRE_CENT);
		}
		if(!StringUtils.isEmpty(clientName)){
			fcriteria.andLike("clientName", Constants.PRE_CENT+clientName+Constants.PRE_CENT);
		}
		List<BusinessDataEntity> responseList  = this.businessDataMapper.selectByExample(example);
		return responseList;
	}

	/**
	 * 按照指定条件从中间库中查询业务信息
	 * @param date
	 * @param qprojId 精准查询
	 * @param clientName 模糊查询
	 * @return List<BusinessDataEntity>
	 */
	public List<BusinessDataEntity> queryBusinessDataByExProj(String date,String qprojId,String clientName){

		Example example = new Example(BusinessDataEntity.class);
		Example.Criteria fcriteria = example.createCriteria();
		if (!StringUtils.isEmpty(date)) {
			fcriteria.andEqualTo("batchDate", date);
		}
		if(!StringUtils.isEmpty(qprojId)){
			fcriteria.andEqualTo("projId", qprojId);
		}
		if(!StringUtils.isEmpty(clientName)){
			fcriteria.andLike("clientName", Constants.PRE_CENT+clientName+Constants.PRE_CENT);
		}
		List<BusinessDataEntity> responseList  = this.businessDataMapper.selectByExample(example);
		return responseList;
	}
	/**
	 * 按照指定条件从中间库中查询业务数据信息
	 * @param batchDate  批次
	 * @param orgId  批次
	 * @param projId  批次
	 * @return 按照指定查询条件返回的查询结果集合
	 */
	public List<BusinessDataEntity> queryBusinessDataByExample(
			String batchDate,String orgId,String projId){
		Example example = new Example(BusinessDataEntity.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("batchDate", batchDate);
		criteria.andEqualTo("orgId", orgId);
		criteria.andEqualTo("projId", projId);
		List<BusinessDataEntity> resList = this.businessDataMapper.selectByExample(example);
		return resList;
	}
}
