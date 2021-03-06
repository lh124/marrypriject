package io.renren.service;

import io.renren.entity.PhotoUserFunctionEntity;
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
public interface PhotoUserFunctionService extends IService<PhotoUserFunctionEntity>{
	
	PhotoUserFunctionEntity queryObject(Long id);
	
	List<PhotoUserFunctionEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoUserFunctionEntity photoUserFunction);
	
	void update(PhotoUserFunctionEntity photoUserFunction);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
