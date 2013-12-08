package com.tcc.cti.web.common.mapper;

import java.util.List;
import java.util.Map;
/**
 * MyBatis Mapper基础数据操作接口，定义和规范基础的操作方法。
 * 
 * @author hantongshan
 */
public interface BaseMapper<T,PK> {  
	
	/**
	 * 通过编号查询实体
	 * 
	 * @param pk 实体键 
	 * @return
	 */
	T selectByPrimaryKey(PK pk);
    
    /**
     * 插入指定的实体
     *  
     * @param t 实体对象
     */
	int insert(T t);
	
	/**
	 * 更新指定的实体
	 * 
	 * @param t 实体对象
	 */
	void updateByPrimaryKey(T t);
    
    /**
     * 删除指定的实体
     * 
     * @param pk 实体键
     */
    void deleteByPrimaryKey(PK pk);
    
	/**
	 * 根据参数查询实体记录,并按照{@code rowBounds}显示查询实体的范围。
	 * 
	 * @param params 查询参数
	 * @param rowBounds 显示实体范围
	 * @return
	 */
    List<T> query(Map<String,?> params);
	
	/**
	 * 根据参数查询实体记录数。
	 * 
	 * @param params 查询参数
	 * @return
	 */
	int queryCount(Map<String,?> params);
} 

