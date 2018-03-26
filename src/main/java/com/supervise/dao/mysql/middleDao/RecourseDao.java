package com.supervise.dao.mysql.middleDao;

import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.dao.mysql.entity.RecourseEntity;
import com.supervise.dao.mysql.mapper.RecourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
			String dateStr = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(new Date());
			Date newDate = DateUtils.String2Date(dateStr, Constants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
			recourseEntity.setCreateDate(newDate);
			recourseEntity.setUpdateDate(newDate);
			recourseEntity.setSendStatus(Constants.DATA_READY_SEND);
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

	/**
	 * 按照指定条件从中间库中查询追偿信息
	 * @param queryCondition  查询条件
	 * @return 按照指定查询条件返回的查询结果集合
	 */
	public List<RecourseEntity> queryRecourseByCondition(
			QueryCondition queryCondition){
		List<RecourseEntity> responseList =
				this.recourseMapper.queryRecourseByConditions(queryCondition);
		return responseList;
	}

	/**
	 * 更新追偿信息
	 * @param recourseEntity
	 * int id
	 */
	public  int updateRecourse(RecourseEntity recourseEntity){
		int id = -1;
		if(null!=recourseEntity){
			String dateStr = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(new Date());
			Date newDate = DateUtils.String2Date(dateStr,Constants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
			recourseEntity.setUpdateDate(newDate);
			id = this.recourseMapper.updateByPrimaryKey(recourseEntity);
		}
		return id;
	}

	/**
	 * 删除追偿信息
	 * @param recourseEntity
	 * int id
	 */
	public  int deleteRecourse(RecourseEntity recourseEntity){
		int id = -1;
		if(null!=recourseEntity){
			id = this.recourseMapper.delete(recourseEntity);
		}
		return id;
	}

	/**
	 * 根据ID主键删除追偿信息
	 * @param key
	 * int
	 */
	public  int deleteRecourseByID(Long key){
		int id = -1;
		if(null!=key){
			id = this.recourseMapper.deleteByPrimaryKey(key);
		}
		return id;
	}

	/**
	 * 按照指定条件从中间库中查询追偿信息
	 * @param id  查询条件
	 * @return 按照指定查询条件返回的查询结果集合
	 */
	public RecourseEntity queryRecourseByKey(Long id){
		RecourseEntity res  =
				this.recourseMapper.selectByPrimaryKey(id);
		return res;
	}

	/**
	 * 删除追偿信息
	 * @param batchDate  条件
	 * @return
	 */
	public void deleteRecourseByBatchDate(String batchDate){
		Example example = new Example(RecourseEntity.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("batchDate", batchDate);
		this.recourseMapper.deleteByExample(example);
	}

	/**
	 * 按照指定条件从中间库中查询追偿信息
	 * @param date
	 * @param qprojId 模糊查询
	 * @return List<RecourseEntity>
	 */
	public List<RecourseEntity> queryRecourseByCondition(String date,String qprojId){

		Example example = new Example(RecourseEntity.class);
		Example.Criteria fcriteria = example.createCriteria();
		if (!StringUtils.isEmpty(date)) {
			fcriteria.andEqualTo("batchDate", date);
		}
		if(!StringUtils.isEmpty(qprojId)){
			fcriteria.andLike("projId", Constants.PRE_CENT+qprojId+Constants.PRE_CENT);
		}
		List<RecourseEntity> responseList  = this.recourseMapper.selectByExample(example);
		return responseList;
	}

	/**
	 * 删除追偿信息
	 * @param batchDate  条件
	 * @return
	 */
	public void deleteRecourseByCondition(String batchDate,String orgId,String projId){
		Example example = new Example(RecourseEntity.class);
		Example.Criteria recriteria = example.createCriteria();
		recriteria.andEqualTo("batchDate", batchDate);
		recriteria.andEqualTo("orgId", orgId);
		recriteria.andEqualTo("projId", projId);
		recourseMapper.deleteByExample(example);
	}
}
