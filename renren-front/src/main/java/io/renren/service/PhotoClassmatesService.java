package io.renren.service;

import io.renren.entity.PhotoClassmatesEntity;
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
public interface PhotoClassmatesService extends IService<PhotoClassmatesEntity>{
	
	PhotoClassmatesEntity queryObject(Long id);
	
	List<PhotoClassmatesEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoClassmatesEntity photoClassmates);
	
	void update(PhotoClassmatesEntity photoClassmates);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
