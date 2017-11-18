package io.renren.service;

import io.renren.entity.PhotoFrontUserEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
public interface PhotoFrontUserService extends IService<PhotoFrontUserEntity>{
	
	PhotoFrontUserEntity queryObject(Long id);
	
	List<PhotoFrontUserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoFrontUserEntity photoFrontUser);
	
	void update(PhotoFrontUserEntity photoFrontUser);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	PhotoFrontUserEntity queryByAccount(String account);
	
	PhotoFrontUserEntity queryClassUser(Long userId, Long classId);
	
	List<PhotoFrontUserEntity> queryUsersByClassId(Long classId ,Integer status);
}
