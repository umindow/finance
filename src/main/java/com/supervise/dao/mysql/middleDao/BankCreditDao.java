package com.supervise.dao.mysql.middleDao;

import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.mapper.BankCreditMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Repository("BankCreditDao")
public class BankCreditDao {
	
	@Autowired
	private BankCreditMapper bankCreditMapper;

	/**
	 * 还款记录插入中间库数据表
	* insertBankCreditToMiddleDB
	* @param bankCreditEntity 还款记录
	* @return int 主键ID号
	 */
    public int insertBankCreditToMiddleDB(BankCreditEntity bankCreditEntity){
    
    	//将entity中的batchdate做处理，格式：yyyy-MM-dd HH:mm:ss 转换成yyyy-MM-dd
    	int id = -1;
    	if(null!=bankCreditEntity){
			String batchDate = bankCreditEntity.getBatchDate().substring(0,10);
			bankCreditEntity.setBatchDate(batchDate);
			String dateStr = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(new Date());
			Date newDate = DateUtils.String2Date(dateStr, Constants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
			bankCreditEntity.setCreateDate(newDate);
			bankCreditEntity.setUpdateDate(newDate);
			bankCreditEntity.setSendStatus(Constants.DATA_READY_SEND);
			id= this.bankCreditMapper.insert(bankCreditEntity);
    	}
    	return id;
    }

	/**
	 * 按照指定条件从视图中查询还款信息
	 * @param batchDate 批次 查询条件
	 * @return 按照指定查询条件返回的查询结果集合
	 */
	public List<BankCreditEntity> queryBankCreditFormMiddleDB(
			String batchDate){
		List<BankCreditEntity> responseList =
				this.bankCreditMapper.queryBankCreditFormMiddleDB(batchDate);
		return responseList;
	}

	/**
	 * 按照指定条件从中间库中查询银行授信信息
	 * @param queryCondition  查询条件
	 * @return 按照指定查询条件返回的查询结果集合
	 */
	public List<BankCreditEntity> queryBankCreditByCondition(
			QueryCondition queryCondition){
		List<BankCreditEntity> responseList =
				this.bankCreditMapper.queryBankCreditByConditions(queryCondition);
		return responseList;
	}

	/**
	 * 更新银行授信信息
	 * @param bankCreditEntity
	 * int id
	 */
	public  int updateBankCredit(BankCreditEntity bankCreditEntity){
		int id = -1;
		if(null!=bankCreditEntity){
			String dateStr = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(new Date());
			Date newDate = DateUtils.String2Date(dateStr,Constants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
			bankCreditEntity.setUpdateDate(newDate);
			id = this.bankCreditMapper.updateByPrimaryKeySelective(bankCreditEntity);
		}
		return id;
	}

	/**
	 * 删除还款信息
	 * @param bankCreditEntity
	 * int id
	 */
	public  int deleteRepayment(BankCreditEntity bankCreditEntity){
		int id = -1;
		if(null!=bankCreditEntity){
			id = this.bankCreditMapper.delete(bankCreditEntity);
		}
		return id;
	}

	/**
	 * 根据ID主键删除银行授信信息
	 * @param key
	 * int
	 */
	public  int deleteRepaymentByID(Long key){
		int id = -1;
		if(null!=key){
			id = this.bankCreditMapper.deleteByPrimaryKey(key);
		}
		return id;
	}

	/**
	 * 按照指定条件从中间库中查询银行授信信息
	 * @param id  查询条件
	 * @return 按照指定查询条件返回的查询结果集合
	 */
	public BankCreditEntity queryBankCreditByKey(Long id){
		BankCreditEntity res  =
				this.bankCreditMapper.selectByPrimaryKey(id);
		return res;
	}
	/**
	 * 删除银行授信信息
	 * @param batchDate  条件
	 * @return
	 */
	public void deleteBankCreditByBatchDate(String batchDate){
		Example example = new Example(BankCreditEntity.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("batchDate",batchDate);
		bankCreditMapper.deleteByExample(example);
	}

	/**
	 * 按照指定条件从中间库中查询银行授信信息
	 * @param date
	 * @param cstartDate >=授信开始日期
	 * @param cendDate   <=授信结束日期
	 * @return List<BankCreditEntity>
	 */
	public List<BankCreditEntity> queryBankCreditByCondition(String date,String cstartDate,String cendDate){

		Example example = new Example(BankCreditEntity.class);
		Example.Criteria fcriteria = example.createCriteria();
		if(!StringUtils.isEmpty(cstartDate)){
			Date startD = DateUtils.String2Date(cstartDate,Constants.YYYY_MM_DD,Locale.ENGLISH);
			fcriteria.andGreaterThanOrEqualTo("creditStartDate", startD);
		}
		if(!StringUtils.isEmpty(cendDate)){
			Date endD = DateUtils.String2Date(cendDate,Constants.YYYY_MM_DD,Locale.ENGLISH);
			fcriteria.andLessThanOrEqualTo("creditEndDate", endD);
		}
		if (!StringUtils.isEmpty(date)) {
			fcriteria.andEqualTo("batchDate", date);
		}
		List<BankCreditEntity> responseList  = this.bankCreditMapper.selectByExample(example);
		return responseList;
	}
}
