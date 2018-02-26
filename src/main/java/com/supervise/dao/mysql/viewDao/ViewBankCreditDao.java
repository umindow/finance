package com.supervise.dao.mysql.viewDao;

import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.viewmapper.ViewBankCreditMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ViewBankCreditDao")
public class ViewBankCreditDao {

	@Autowired
	private ViewBankCreditMapper viewBankCreditMapper;
	
	
	/**
     * 按照指定条件从视图中查询还款信息
     * @param batchDate 批次 查询条件
     * @return 按照指定查询条件返回的查询结果集合
     */
    public List<BankCreditEntity> queryBankCreditFromView(
            String batchDate){
        List<BankCreditEntity> responseList =
                this.viewBankCreditMapper.queryBankCreditView(batchDate);
        return responseList;
    }
}
