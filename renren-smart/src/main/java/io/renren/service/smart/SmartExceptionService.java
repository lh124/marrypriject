package io.renren.service.smart;

import io.renren.entity.smart.SmartExceptionEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-12-07 12:40:40
 */
public interface SmartExceptionService extends IService<SmartExceptionEntity>{
	
	SmartExceptionEntity queryObject(Integer id);
	
	List<SmartExceptionEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SmartExceptionEntity smartException);
	
	void update(SmartExceptionEntity smartException);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
