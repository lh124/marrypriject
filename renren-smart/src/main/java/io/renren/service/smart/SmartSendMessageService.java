package io.renren.service.smart;

import io.renren.entity.smart.SmartSendMessageEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-02-01 09:17:27
 */
public interface SmartSendMessageService extends IService<SmartSendMessageEntity>{
	
	SmartSendMessageEntity queryObject(Integer id);
	
	List<SmartSendMessageEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SmartSendMessageEntity smartSendMessage);
	
	void update(SmartSendMessageEntity smartSendMessage);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
