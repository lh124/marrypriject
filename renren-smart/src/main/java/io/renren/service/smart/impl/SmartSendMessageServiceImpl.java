package io.renren.service.smart.impl;

import io.renren.dao.smart.SmartSendMessageDao;
import io.renren.entity.smart.SmartSendMessageEntity;
import io.renren.service.smart.SmartSendMessageService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("smartSendMessageService")
public class SmartSendMessageServiceImpl extends ServiceImpl<SmartSendMessageDao, SmartSendMessageEntity>  implements SmartSendMessageService {
	@Autowired
	private SmartSendMessageDao smartSendMessageDao;
	
	@Override
	public SmartSendMessageEntity queryObject(Integer id){
		return smartSendMessageDao.queryObject(id);
	}
	
	@Override
	public List<SmartSendMessageEntity> queryList(Map<String, Object> map){
		return smartSendMessageDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return smartSendMessageDao.queryTotal(map);
	}
	
	@Override
	public void save(SmartSendMessageEntity smartSendMessage){
		smartSendMessageDao.save(smartSendMessage);
	}
	
	@Override
	public void update(SmartSendMessageEntity smartSendMessage){
		smartSendMessageDao.update(smartSendMessage);
	}
	
	@Override
	public void delete(Integer id){
		smartSendMessageDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		smartSendMessageDao.deleteBatch(ids);
	}
	
}
