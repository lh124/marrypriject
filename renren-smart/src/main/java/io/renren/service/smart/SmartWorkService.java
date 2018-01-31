package io.renren.service.smart;

import io.renren.entity.smart.SmartWorkEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-09-21 13:38:19
 */
public interface SmartWorkService extends IService<SmartWorkEntity>{
	
	SmartWorkEntity queryObject(Integer id);
	
	List<SmartWorkEntity> queryList(Map<String, Object> map);
	
	List<SmartWorkEntity> queryListtongji(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SmartWorkEntity smartWork);
	
	void update(SmartWorkEntity smartWork);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
