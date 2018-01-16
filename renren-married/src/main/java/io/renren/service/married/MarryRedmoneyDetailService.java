package io.renren.service.married;

import io.renren.entity.married.MarryRedmoneyDetailEntity;

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
public interface MarryRedmoneyDetailService extends IService<MarryRedmoneyDetailEntity>{
	
	MarryRedmoneyDetailEntity queryObject(Integer id);
	
	List<MarryRedmoneyDetailEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MarryRedmoneyDetailEntity marryRedmoneyDetail);
	
	void update(MarryRedmoneyDetailEntity marryRedmoneyDetail);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
