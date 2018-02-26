package com.supervise.dao.mysql.viewDao;

import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.viewmapper.ViewBusinessDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ViewBusinessDataDao")
public class ViewBusinessDataDao {

	@Autowired
	private ViewBusinessDataMapper viewBusinessDataMapper;
	
	
	/**
     * 按照指定条件从视图中查询业务数据信息
     * @param batchDate 批次 查询条件
     * @return 按照指定查询条件返回的查询结果集合
     */
    public List<BusinessDataEntity> queryBusinessDataFromView(
            String batchDate){
        List<BusinessDataEntity> responseList =
                this.viewBusinessDataMapper.queryBusinessDataView(batchDate);
        return responseList;
    }
}
