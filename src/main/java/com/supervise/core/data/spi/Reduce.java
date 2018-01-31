package com.supervise.core.data.spi;

/**
 * Created by xishui.hb on 2018/1/31 上午10:13.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public interface Reduce<T,P> {
    //t是原始的数据,P是需要被聚合的数据
    void reduce(T originalData,P reduceData);
}
