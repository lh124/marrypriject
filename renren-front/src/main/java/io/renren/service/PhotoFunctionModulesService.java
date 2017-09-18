package io.renren.service;

import io.renren.entity.PhotoFunctionModulesEntity;
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
public interface PhotoFunctionModulesService extends IService<PhotoFunctionModulesEntity>{
	
	PhotoFunctionModulesEntity queryObject(Integer id);
	
	List<PhotoFunctionModulesEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoFunctionModulesEntity photoFunctionModules);
	
	void update(PhotoFunctionModulesEntity photoFunctionModules);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
