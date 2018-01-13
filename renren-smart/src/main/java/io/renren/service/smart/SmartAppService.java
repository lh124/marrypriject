package io.renren.service.smart;

import io.renren.entity.app.SmartAppEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-01-13 08:59:53
 */
public interface SmartAppService extends IService<SmartAppEntity>{
	
	SmartAppEntity queryObject(Integer id);
	
	List<SmartAppEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SmartAppEntity smartApp);
	
	void update(SmartAppEntity smartApp);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
