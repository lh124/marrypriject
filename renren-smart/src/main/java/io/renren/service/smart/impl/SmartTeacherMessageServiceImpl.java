package io.renren.service.smart.impl;

import io.renren.dao.smart.SmartTeacherMessageDao;
import io.renren.entity.smart.SmartTeacherMessageEntity;
import io.renren.service.smart.SmartTeacherMessageService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("smartTeacherMessageService")
public class SmartTeacherMessageServiceImpl extends ServiceImpl<SmartTeacherMessageDao, SmartTeacherMessageEntity>  implements SmartTeacherMessageService {
	@Autowired
	private SmartTeacherMessageDao smartTeacherMessageDao;
	
	@Override
	public SmartTeacherMessageEntity queryObject(Integer id){
		return smartTeacherMessageDao.queryObject(id);
	}
	
	@Override
	public List<SmartTeacherMessageEntity> queryList(Map<String, Object> map){
		return smartTeacherMessageDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return smartTeacherMessageDao.queryTotal(map);
	}
	
	@Override
	public void save(SmartTeacherMessageEntity smartTeacherMessage){
		smartTeacherMessageDao.save(smartTeacherMessage);
	}
	
	@Override
	public void update(SmartTeacherMessageEntity smartTeacherMessage){
		smartTeacherMessageDao.update(smartTeacherMessage);
	}
	
	@Override
	public void delete(Integer id){
		smartTeacherMessageDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		smartTeacherMessageDao.deleteBatch(ids);
	}
	
}
