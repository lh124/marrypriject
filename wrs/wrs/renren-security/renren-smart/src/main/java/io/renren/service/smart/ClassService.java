package io.renren.service.smart;

import io.renren.entity.smart.ClassEntity;

import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-08-28 12:02:26
 */
public interface ClassService extends IService<ClassEntity>{
	
	ClassEntity queryObject(Integer id);
	
	List<ClassEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ClassEntity classe);
	
	void update(ClassEntity classe);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
