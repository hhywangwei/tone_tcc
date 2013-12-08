package com.tcc.cti.web.common.service;

/**
 * 业务基础服务接口，统一基础业务服务接口。
 * 如果业务复杂可不继承该类直接写自己的业务接口。
 * 
 * @author hantongshan
 *
 * @param <T> 实体类型
 * @param <PK> 实体键值
 */
public interface BaseService<T,PK> {
    
    /**
     * 保存指定的对象到数据中，{@code insert=true}插入数据，反之更新数据。
     * 
     * @param t 实体对象
     * @param insert 插入操作
     * @return
     */
    PK save(T t,boolean insert);
    
    /**
     * 保存实体对象到数据库中，键值为{@code null}时插入实体，反之更新实体。
     *  
     * @param t  实体对象
     * @return 实体键值
     */
    PK save(T t);
    
    /**
     * 删除实体对象
     * 
     * @param pk 实体键
     */
    void delete(PK pk);
    
    /**
     * 查询实体，如实体不存在则返回{@code null}
     * 
     * @param pk 实体键
     * @return
     */
    T selectByPrimaryKey(PK pk);

}
