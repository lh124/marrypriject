package io.renren.service;

import io.renren.entity.PhotoContentEntity;
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
public interface PhotoContentService extends IService<PhotoContentEntity>{
	
	PhotoContentEntity queryObject(Long id);
	
	List<PhotoContentEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoContentEntity photoContent);
	
	void update(PhotoContentEntity photoContent);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
