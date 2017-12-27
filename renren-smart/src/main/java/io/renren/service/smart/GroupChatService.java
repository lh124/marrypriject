package io.renren.service.smart;

import io.renren.entity.smart.GroupChatEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-11-25 10:00:42
 */
public interface GroupChatService extends IService<GroupChatEntity>{
	
	GroupChatEntity queryObject(Integer id);
	
	List<GroupChatEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(GroupChatEntity groupChat);
	
	void update(GroupChatEntity groupChat);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
