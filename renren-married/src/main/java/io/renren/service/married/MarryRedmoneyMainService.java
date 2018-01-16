package io.renren.service.married;

import io.renren.entity.married.MarryRedmoneyMainEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-01-16 15:19:25
 */
public interface MarryRedmoneyMainService extends IService<MarryRedmoneyMainEntity>{
	
	MarryRedmoneyMainEntity queryObject(Integer id);
	
	List<MarryRedmoneyMainEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MarryRedmoneyMainEntity marryRedmoneyMain);
	
	void update(MarryRedmoneyMainEntity marryRedmoneyMain);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
