package io.renren.service;

import io.renren.entity.PhotoGraduationTimeEntity;

import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
public interface PhotoGraduationTimeService extends IService<PhotoGraduationTimeEntity>{
	
	PhotoGraduationTimeEntity queryObject(Integer id);
	
	List<PhotoGraduationTimeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoGraduationTimeEntity photoGraduationTime);
	
	void update(PhotoGraduationTimeEntity photoGraduationTime);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
	
	List<PhotoGraduationTimeEntity> queryGraduationClass(Integer status, Long schoolId, Long collegeId, Integer classify);
}
