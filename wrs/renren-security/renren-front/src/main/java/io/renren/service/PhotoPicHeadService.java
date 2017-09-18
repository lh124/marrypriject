package io.renren.service;

import io.renren.entity.PhotoPicHeadEntity;
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
public interface PhotoPicHeadService extends IService<PhotoPicHeadEntity>{
	
	PhotoPicHeadEntity queryObject(Long id);
	
	List<PhotoPicHeadEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoPicHeadEntity photoPicHead);
	
	void update(PhotoPicHeadEntity photoPicHead);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
