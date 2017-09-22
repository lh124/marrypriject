package io.renren.service.smart;

import io.renren.entity.smart.SmartActivitiesEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-09-21 10:22:32
 */
public interface SmartActivitiesService extends IService<SmartActivitiesEntity>{
	
	SmartActivitiesEntity queryObject(Integer id);
	
	List<SmartActivitiesEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SmartActivitiesEntity smartActivities);
	
	void update(SmartActivitiesEntity smartActivities);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
