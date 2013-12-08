package com.tcc.cti.web.common.service;

import com.tcc.cti.web.common.mapper.BaseMapper;
/**
 * 实现{@link BaseService}
 * 
 * @author hantongshan
 *
 * @param <T> 实体类型
 * @param <PK> 实体键值
 */
public abstract class BaseServiceImpl<T, PK> implements BaseService<T, PK> {

    private BaseMapper<T, PK> mapper;

    /**
     * 得到指定实体的键值。
     * 
     * @param t 实体对象
     * @return 实体键值
     */
    protected abstract PK getPK(T t);
    
    /**
     * 判断Mapper是否设置
     * 
     * @param m 指定的Mapper
     */
    protected void mapperNullPointer(BaseMapper<T, PK> m){
        if(m == null){
            throw new NullPointerException("You must set mapper");
        }
    }
    
    public PK save(T t,boolean insert){
        mapperNullPointer(mapper);
        if(insert){
            mapper.insert(t);
        }else{
            mapper.updateByPrimaryKey(t);
        }
        return getPK(t);
    }

    public PK save(T t) {
        boolean insert = (getPK(t) == null);
        return save(t,insert);
    }

    public void delete(PK pk) {
        mapperNullPointer(mapper);
        mapper.deleteByPrimaryKey(pk);
    }

    public T selectByPrimaryKey(PK pk) {
        mapperNullPointer(mapper);
        return mapper.selectByPrimaryKey(pk);
    }
    
    /**
     * 设置数据操作Mapper
     * 
     * @param mapper2 数据操作对象
     */
    protected void setMapper(BaseMapper<T, PK> mapper2){
        this.mapper = mapper2;
    }
}

