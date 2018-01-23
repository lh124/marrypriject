package io.renren.service.app;

import io.renren.entity.app.WorkMainEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-01-23 11:24:25
 */
public interface WorkMainService extends IService<WorkMainEntity>{
	
	WorkMainEntity queryObject(Integer id);
	
	List<WorkMainEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(WorkMainEntity workMain);
	
	void update(WorkMainEntity workMain);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
