package io.renren.service;

import io.renren.entity.PhotoPicUserEntity;
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
public interface PhotoPicUserService extends IService<PhotoPicUserEntity>{
	
	PhotoPicUserEntity queryObject(Long id);
	
	List<PhotoPicUserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoPicUserEntity photoPicUser);
	
	void update(PhotoPicUserEntity photoPicUser);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
