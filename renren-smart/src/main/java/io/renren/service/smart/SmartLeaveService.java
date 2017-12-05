package io.renren.service.smart;

import io.renren.entity.smart.SmartLeaveEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-12-04 15:12:45
 */
public interface SmartLeaveService extends IService<SmartLeaveEntity>{
	
	SmartLeaveEntity queryObject(Integer id);
	
	List<SmartLeaveEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SmartLeaveEntity smartLeave);
	
	void update(SmartLeaveEntity smartLeave);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
