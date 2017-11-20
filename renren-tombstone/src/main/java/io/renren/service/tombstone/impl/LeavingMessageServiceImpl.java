package io.renren.service.tombstone.impl;

import io.renren.dao.tombstone.LeavingMessageDao;
import io.renren.entity.tombstone.LeavingMessageEntity;
import io.renren.service.tombstone.LeavingMessageService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("leavingMessageService")
public class LeavingMessageServiceImpl extends ServiceImpl<LeavingMessageDao, LeavingMessageEntity>  implements LeavingMessageService {
	@Autowired
	private LeavingMessageDao leavingMessageDao;
	
	@Override
	public LeavingMessageEntity queryObject(Integer id){
		return leavingMessageDao.queryObject(id);
	}
	
	@Override
	public List<LeavingMessageEntity> queryList(Map<String, Object> map){
		return leavingMessageDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return leavingMessageDao.queryTotal(map);
	}
	
	@Override
	public void save(LeavingMessageEntity leavingMessage){
		leavingMessageDao.save(leavingMessage);
	}
	
	@Override
	public void update(LeavingMessageEntity leavingMessage){
		leavingMessageDao.update(leavingMessage);
	}
	
	@Override
	public void delete(Integer id){
		leavingMessageDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		leavingMessageDao.deleteBatch(ids);
	}
	
}
