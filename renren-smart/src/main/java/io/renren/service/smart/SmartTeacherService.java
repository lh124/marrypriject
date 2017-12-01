package io.renren.service.smart;

import io.renren.entity.smart.SmartTeacherEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-12-01 15:34:43
 */
public interface SmartTeacherService extends IService<SmartTeacherEntity>{
	
	SmartTeacherEntity queryObject(Integer id);
	
	List<SmartTeacherEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SmartTeacherEntity smartTeacher);
	
	void update(SmartTeacherEntity smartTeacher);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
