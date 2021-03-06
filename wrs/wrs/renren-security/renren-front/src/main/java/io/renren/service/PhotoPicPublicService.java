package io.renren.service;

import io.renren.entity.PhotoPicPublicEntity;
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
public interface PhotoPicPublicService extends IService<PhotoPicPublicEntity>{
	
	PhotoPicPublicEntity queryObject(Long id);
	
	List<PhotoPicPublicEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoPicPublicEntity photoPicPublic);
	
	void update(PhotoPicPublicEntity photoPicPublic);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
