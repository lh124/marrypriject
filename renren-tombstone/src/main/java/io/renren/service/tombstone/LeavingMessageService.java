package io.renren.service.tombstone;

import io.renren.entity.tombstone.LeavingMessageEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-11-04 15:11:17
 */
public interface LeavingMessageService extends IService<LeavingMessageEntity>{
	
	LeavingMessageEntity queryObject(Integer id);
	
	List<LeavingMessageEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(LeavingMessageEntity leavingMessage);
	
	void update(LeavingMessageEntity leavingMessage);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
