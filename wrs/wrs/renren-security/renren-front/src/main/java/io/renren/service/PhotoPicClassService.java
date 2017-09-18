package io.renren.service;

import io.renren.entity.PhotoPicClassEntity;
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
public interface PhotoPicClassService extends IService<PhotoPicClassEntity>{
	
	PhotoPicClassEntity queryObject(Long id);
	
	List<PhotoPicClassEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoPicClassEntity photoPicClass);
	
	void update(PhotoPicClassEntity photoPicClass);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
