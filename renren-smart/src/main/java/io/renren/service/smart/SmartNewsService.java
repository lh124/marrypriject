package io.renren.service.smart;

import io.renren.entity.app.SmartNewsEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-12-26 08:58:40
 */
public interface SmartNewsService extends IService<SmartNewsEntity>{
	
	SmartNewsEntity queryObject(Integer id);
	
	List<SmartNewsEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SmartNewsEntity smartNews);
	
	void update(SmartNewsEntity smartNews);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
