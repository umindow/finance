package com.supervise.dao.mysql.viewDao;

import com.supervise.dao.mysql.entity.ViewRepaymentEntity;
import com.supervise.dao.mysql.viewmapper.ViewRepaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ViewRepaymentDao")
public class ViewRepaymentDao{

	@Autowired
	private ViewRepaymentMapper viewrepaymentMapper;
	
	
	/**
     * 按照指定条件从视图中查询还款信息
     * @param batchDate 批次 查询条件
     * @return 按照指定查询条件返回的查询结果集合
     */
    public List<ViewRepaymentEntity> queryRepaymentFromView(
            String batchDate){
        List<ViewRepaymentEntity> responseList =
                this.viewrepaymentMapper.queryRepaymentView(batchDate);
        return responseList;
    }
}
