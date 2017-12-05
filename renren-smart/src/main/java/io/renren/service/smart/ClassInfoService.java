package io.renren.service.smart;

import io.renren.entity.smart.ClassInfoEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-09-22 09:38:52
 */
public interface ClassInfoService extends IService<ClassInfoEntity>{
	
	ClassInfoEntity queryObject(Integer id);
	
	List<ClassInfoEntity> queryList(Map<String, Object> map);
	
	List<ClassInfoEntity> queryListtongji(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ClassInfoEntity classInfo);
	
	void update(ClassInfoEntity classInfo);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
