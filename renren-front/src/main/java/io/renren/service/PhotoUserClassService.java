package io.renren.service;

import io.renren.entity.PhotoUserClassEntity;
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
public interface PhotoUserClassService extends IService<PhotoUserClassEntity>{
	
	PhotoUserClassEntity queryObject(Long id);
	
	List<PhotoUserClassEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoUserClassEntity photoUserClass);
	
	void update(PhotoUserClassEntity photoUserClass);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
