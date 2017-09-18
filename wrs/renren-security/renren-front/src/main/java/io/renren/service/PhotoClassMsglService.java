package io.renren.service;

import io.renren.entity.PhotoClassMsglEntity;
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
public interface PhotoClassMsglService extends IService<PhotoClassMsglEntity>{
	
	PhotoClassMsglEntity queryObject(Long id);
	
	List<PhotoClassMsglEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoClassMsglEntity photoClassMsgl);
	
	void update(PhotoClassMsglEntity photoClassMsgl);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
