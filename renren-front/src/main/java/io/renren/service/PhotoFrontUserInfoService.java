package io.renren.service;

import io.renren.entity.PhotoFrontUserInfoEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 用户信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
public interface PhotoFrontUserInfoService extends IService<PhotoFrontUserInfoEntity>{
	
	PhotoFrontUserInfoEntity queryObject(Long id);
	
	List<PhotoFrontUserInfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoFrontUserInfoEntity photoFrontUserInfo);
	
	void update(PhotoFrontUserInfoEntity photoFrontUserInfo);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
