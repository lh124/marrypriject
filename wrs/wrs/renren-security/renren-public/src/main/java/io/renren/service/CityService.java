package io.renren.service;

import io.renren.entity.CityEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 行政区域地州市信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
public interface CityService extends IService<CityEntity>{
	
	CityEntity queryObject(Integer id);
	
	List<CityEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(CityEntity city);
	
	void update(CityEntity city);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
