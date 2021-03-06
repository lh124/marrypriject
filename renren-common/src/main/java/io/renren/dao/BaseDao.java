package io.renren.dao;

import java.util.List;
import java.util.Map;

/**
 * 基础Dao(还需在XML文件里，有对应的SQL语句)
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:31:36
 */
public interface BaseDao<T> {
	
	void save(T t);
	 
	void save(Map<String, Object> map);
	
	void saveBatch(List<T> list);
	
	int update(T t);
	
	int update(Map<String, Object> map);
	
	int delete(Object id);
	
	int deleteEpc(String epc);
	
	int delete(Map<String, Object> map);
	
	int deleteBatch(Object[] id);

	T queryObject(Object id);
	
	T findByOpenIdLike(Object openid);
	
	T queryObjectIdEpc(Map<String, Object> map);
	
	T queryObjectName(Object name);
	
	List<T> queryList(Map<String, Object> map);
	
	List<T> queryList1(Map<String, Object> map);
	
	T queryObjectScreen(Object id);
	
	List<T> queryListorder(Map<String, Object> map);
	
	List<T> queryListtongji(Map<String, Object> map);
	 
	List<T> queryListtongjiimgxf(Map<String, Object> map);
	
	List<T> queryListmysql(Map<String, Object> map);
	
	List<T> queryListStudent(Map<String, Object> map);
	
	List<T> queryList(Object id);
	
	int queryTotal(Map<String, Object> map);

	int queryTotal();
}
