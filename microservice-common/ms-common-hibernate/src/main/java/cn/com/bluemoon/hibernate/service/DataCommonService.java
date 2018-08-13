package cn.com.bluemoon.hibernate.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface DataCommonService {
	<T> int save(T... entity);
	<T> int merge(T... entity);
	<T> boolean delete(T entity);
	<T> int deleteByIds(Class<T> clz,Serializable... ids);
	<T> int updateMultiEntity(T... entity);
	<T> T findEntity(Class<T> clz,Serializable id);
	<T> List<T> findMultiEntity(Class<T> clz,boolean typeSafe,Serializable... ids);
	public long countTotal(String sql,Map<String,String> map);
	long findCount(String sql,Map<String,Object> map);
	<T> List<T> findSqlMultiEntity(Class<T> clz,String sql,Map<String,Object> map);
	<T> int updateList(List<T> list);
}
