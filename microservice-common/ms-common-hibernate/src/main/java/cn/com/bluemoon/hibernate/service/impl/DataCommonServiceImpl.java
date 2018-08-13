package cn.com.bluemoon.hibernate.service.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import cn.com.bluemoon.hibernate.service.DataCommonService;


public class DataCommonServiceImpl implements DataCommonService{

	@PersistenceContext
	private EntityManager em ;
	private static final ConcurrentHashMap<String,String> idFieldNameMapping = new ConcurrentHashMap<String,String>(10);
	@Override
	@Transactional
	public <T> int save(T... entity) {
		int size =entity!=null ? entity.length : 0 ;
		for(T object: entity){
			em.persist(object);
		}
		return size;
	}
	
	@Override
	@Transactional
	public <T> int merge(T... entity) {
		int size =entity!=null ? entity.length : 0 ;
		for(T object: entity){
			em.merge(object);
		}
		return size;
	}

	@Override
	@Transactional
	public  <T> boolean delete(T entity) {
		boolean flag = em.contains(entity);
		if(flag){
			em.remove(entity);
		}
		return flag;
	}
	
	@Override
	@Transactional
	public <T> int deleteByIds(Class<T> clz,Serializable... ids) {
		String idFieldName = findIdFieldName(clz);
		StringBuilder delQL =new StringBuilder("delete from ")
				.append(clz.getSimpleName())
				.append(" where ").append(idFieldName).append(" in (:ids)");
		int delSize = em.createQuery(delQL.toString())
				.setParameter("ids", Arrays.asList(ids)).executeUpdate();
		return delSize;
	}
	
	@Override
	@Transactional
	public <T> int updateMultiEntity(T... entity) {
		for(T single: entity){
			em.merge(single);
		} 
		
		return entity!=null ? entity.length:0;
	}

	@Override
	@Transactional(readOnly=true)
	public <T> T findEntity(Class<T> clz,Serializable id) {
		return em.find(clz, id);
	}

	@Override
	@Transactional(readOnly=true)
	public <T> List<T> findMultiEntity(Class<T> clz,boolean typeSafe,Serializable... ids) {
		List<T> foundEntities = new  ArrayList<T>();
		String idFieldName = findIdFieldName(clz);
		StringBuilder findQL =new StringBuilder("from ")
				.append(clz.getSimpleName())
				.append(" where ").append(idFieldName).append(" in (:ids)");
		List resultList = em.createQuery(findQL.toString())
				.setParameter("ids", ids).getResultList();
		Object[] elms =resultList!=null ? resultList.toArray() : null;
		if(typeSafe && null != elms && elms.getClass().getComponentType().equals(clz) ){
			for(Object el : elms){
				foundEntities.add((T) el);
			}
		}
		return foundEntities;
	}

	public <E> String findIdFieldName(Class<E> clz) {
		String idFieldName = null;
		if(idFieldNameMapping.contains(clz.getName())){
			idFieldName = idFieldNameMapping.get(clz.getName());
		}else{
				// TODO 找出主键字段名称			
				idFieldName = em.getMetamodel().entity(clz).getId(Long.class).getName();  //Long.class
				System.out.println("find id field name:"+idFieldName+" for "+clz.getName());
				if(null!=idFieldName){
					idFieldNameMapping.put(clz.getName(), idFieldName);
				}
		}
		return idFieldName;
	}
	
	@Override
	@Transactional(readOnly=true)
	public long countTotal(String sql,Map<String,String> map){
		  long count=0;
		  Query query=getEm().createNativeQuery(sql);
		  for(String key:map.keySet()){
				  query.setParameter(key, map.get(key));
			}
		  BigInteger fStr=(BigInteger) query.getSingleResult();
		  if(fStr!=null){
			  count=fStr.longValue();
		  }
		 return count;
	}
	
	@Override
	@Transactional(readOnly=true)
	public long findCount(String sql,Map<String,Object> map){
		long count=0;
		Query query=getEm().createNativeQuery(sql);
		for(String key:map.keySet()){
			query.setParameter(key, map.get(key));
		}
		BigInteger fStr=(BigInteger) query.getSingleResult();
		if(fStr!=null){
			count=fStr.longValue();
		}
		return count;
	}
	
	@Override
	@Transactional(readOnly=true)
	public <T> List<T> findSqlMultiEntity(Class<T> clz,String sql,Map<String,Object> map){
		List<T> foundEntities = new  ArrayList<T>();
		Query query = getEm().createNativeQuery(sql, clz);
		for(String key:map.keySet()){
			query.setParameter(key, map.get(key));
		}
		foundEntities = query.getResultList();
		return foundEntities;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public <T> int updateList(List<T> list){
		int size =list!=null ? list.size() : 0 ;
		for (T t : list) {
			em.merge(t);
		}
		return size;
	}
}