package io.renren.service.smart;

import io.renren.entity.smart.SmartTeacherMessageEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-01-30 09:33:09
 */
public interface SmartTeacherMessageService extends IService<SmartTeacherMessageEntity>{
	
	SmartTeacherMessageEntity queryObject(Integer id);
	
	List<SmartTeacherMessageEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SmartTeacherMessageEntity smartTeacherMessage);
	
	void update(SmartTeacherMessageEntity smartTeacherMessage);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
