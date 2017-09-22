package io.renren.service.smart;

import io.renren.entity.smart.SmartCoursewareEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-09-21 15:11:15
 */
public interface SmartCoursewareService extends IService<SmartCoursewareEntity>{
	
	SmartCoursewareEntity queryObject(Integer id);
	
	List<SmartCoursewareEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SmartCoursewareEntity smartCourseware);
	
	void update(SmartCoursewareEntity smartCourseware);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
