package io.renren.service;

import io.renren.entity.PhotoPicSchoolEntity;
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
public interface PhotoPicSchoolService extends IService<PhotoPicSchoolEntity>{
	
	PhotoPicSchoolEntity queryObject(Long id);
	
	List<PhotoPicSchoolEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoPicSchoolEntity photoPicSchool);
	
	void update(PhotoPicSchoolEntity photoPicSchool);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
