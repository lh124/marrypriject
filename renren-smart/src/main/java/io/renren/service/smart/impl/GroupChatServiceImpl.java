package io.renren.service.smart.impl;

import io.renren.dao.smart.GroupChatDao;
import io.renren.entity.smart.GroupChatEntity;
import io.renren.service.smart.GroupChatService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("groupChatService")
public class GroupChatServiceImpl extends ServiceImpl<GroupChatDao, GroupChatEntity>  implements GroupChatService {
	@Autowired
	private GroupChatDao groupChatDao;
	
	@Override
	public GroupChatEntity queryObject(Integer id){
		return groupChatDao.queryObject(id);
	}
	
	@Override
	public List<GroupChatEntity> queryList(Map<String, Object> map){
		return groupChatDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return groupChatDao.queryTotal(map);
	}
	
	@Override
	public void save(GroupChatEntity groupChat){
		groupChatDao.save(groupChat);
	}
	
	@Override
	public void update(GroupChatEntity groupChat){
		groupChatDao.update(groupChat);
	}
	
	@Override
	public void delete(Integer id){
		groupChatDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		groupChatDao.deleteBatch(ids);
	}
	
}
