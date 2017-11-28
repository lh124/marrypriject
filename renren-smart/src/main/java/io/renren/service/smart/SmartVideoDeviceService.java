package io.renren.service.smart;

import io.renren.entity.smart.SmartVideoDeviceEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-11-27 17:07:15
 */
public interface SmartVideoDeviceService extends IService<SmartVideoDeviceEntity>{
	
	SmartVideoDeviceEntity queryObject(Integer id);
	
	List<SmartVideoDeviceEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SmartVideoDeviceEntity smartVideoDevice);
	
	void update(SmartVideoDeviceEntity smartVideoDevice);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
