package io.renren.service.smart;

import io.renren.entity.smart.SmartGradeEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-01-27 10:26:32
 */
public interface SmartGradeService extends IService<SmartGradeEntity>{
	
	SmartGradeEntity queryObject(Integer id);
	
	List<SmartGradeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SmartGradeEntity smartGrade);
	
	void update(SmartGradeEntity smartGrade);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
