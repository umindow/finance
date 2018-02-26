package com.supervise.dao.mysql.middleDao;

import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.mapper.BankCreditMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
