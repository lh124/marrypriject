package io.renren.service;

import io.renren.entity.PhotoPicFamilyEntity;
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
public interface PhotoPicFamilyService extends IService<PhotoPicFamilyEntity>{
	
	PhotoPicFamilyEntity queryObject(Long id);
	
	List<PhotoPicFamilyEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoPicFamilyEntity photoPicFamily);
	
	void update(PhotoPicFamilyEntity photoPicFamily);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
