package io.renren.service;

import io.renren.entity.PhotoMessageEntity;
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
public interface PhotoMessageService extends IService<PhotoMessageEntity>{
	
	PhotoMessageEntity queryObject(Long id);
	
	List<PhotoMessageEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoMessageEntity photoMessage);
	
	void update(PhotoMessageEntity photoMessage);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
