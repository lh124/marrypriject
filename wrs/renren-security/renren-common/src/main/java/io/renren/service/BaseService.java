package io.renren.service;

import java.util.List;
import java.util.Map;

public interface BaseService<T> {

	T queryObject(Long id);
	
	List<T> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(T t);
	
	void update(T t);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
