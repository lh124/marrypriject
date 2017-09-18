package io.renren.service;

import io.renren.entity.PhotoClassEntity;

import com.baomidou.mybatisplus.service.IService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
public interface PhotoClassService extends IService<PhotoClassEntity>{
	
	PhotoClassEntity queryObject(Long id);
	
	List<PhotoClassEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoClassEntity photoClass);
	
	void update(PhotoClassEntity photoClass);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	List<HashMap<String, Object>> queryMapObject();
	
	List<PhotoClassEntity> querAdminClass(Long userId, int roleType);
}
