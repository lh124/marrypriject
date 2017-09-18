package io.renren.service;

import io.renren.entity.PhotoFamilyEntity;
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
public interface PhotoFamilyService extends IService<PhotoFamilyEntity>{
	
	PhotoFamilyEntity queryObject(Long id);
	
	List<PhotoFamilyEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoFamilyEntity photoFamily);
	
	void update(PhotoFamilyEntity photoFamily);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
