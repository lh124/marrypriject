package io.renren.service;

import io.renren.entity.PhotoFamilyUserEntity;
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
public interface PhotoFamilyUserService extends IService<PhotoFamilyUserEntity>{
	
	PhotoFamilyUserEntity queryObject(Long id);
	
	List<PhotoFamilyUserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoFamilyUserEntity photoFamilyUser);
	
	void update(PhotoFamilyUserEntity photoFamilyUser);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
