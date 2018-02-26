package com.supervise.dao.mysql.viewDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supervise.dao.mysql.entity.RepaymentEntity;
import com.supervise.dao.mysql.viewmapper.ViewRepaymentMapper;

@Repository("ViewRepaymentDao")
public class ViewRepaymentDao{

	@Autowired
	private ViewRepaymentMapper viewrepaymentMapper;
	
	
	/**
     * 按照指定条件从视图中查询还款信息
     * @param batchDate 批次 查询条件
     * @return 按照指定查询条件返回的查询结果集合
     */
    public List<RepaymentEntity> queryRepaymentFromView(
            String batchDate){
        List<RepaymentEntity> responseList =
                this.viewrepaymentMapper.queryRepaymentView(batchDate);
        return responseList;
    }
}
