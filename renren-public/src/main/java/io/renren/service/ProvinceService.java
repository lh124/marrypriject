package io.renren.service;

import io.renren.entity.ProvinceEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 省份信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
public interface ProvinceService extends IService<ProvinceEntity>{
	
	ProvinceEntity queryObject(Integer id);
	
	List<ProvinceEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ProvinceEntity province);
	
	void update(ProvinceEntity province);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
