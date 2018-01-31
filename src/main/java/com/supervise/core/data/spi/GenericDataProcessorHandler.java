package com.supervise.core.data.spi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xishui.hb on 2018/1/31 上午9:13.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public abstract class GenericDataProcessorHandler<T,P> implements LoadedAfterDataProcessor<T,P>,DataHandler<P>{
    private final Logger logger = LoggerFactory.getLogger(GenericDataProcessorHandler.class);
    @Override
    public void handler(P configParam) {
       //数据加载
       T data =  loadedData(configParam);
       if(null == data){

           return;
       }
       //数据清洗
       processor(data);
        //数据后续处理存库
       afterData(data);
    }
}
