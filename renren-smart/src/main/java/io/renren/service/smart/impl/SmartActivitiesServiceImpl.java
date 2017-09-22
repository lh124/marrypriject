package io.renren.service.smart.impl;

import io.renren.dao.smart.SmartActivitiesDao;
import io.renren.entity.smart.SmartActivitiesEntity;
import io.renren.service.smart.SmartActivitiesService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("smartActivitiesService")
public class SmartActivitiesServiceImpl extends ServiceImpl<SmartActivitiesDao, SmartActivitiesEntity>  implements SmartActivitiesService {
	@Autowired
	private SmartActivitiesDao smartActivitiesDao;
	
	@Override
	public SmartActivitiesEntity queryObject(Integer id){
		return smartActivitiesDao.queryObject(id);
	}
	
	@Override
	public List<SmartActivitiesEntity> queryList(Map<String, Object> map){
		return smartActivitiesDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return smartActivitiesDao.queryTotal(map);
	}
	
	@Override
	public void save(SmartActivitiesEntity smartActivities){
		smartActivitiesDao.save(smartActivities);
	}
	
	@Override
	public void update(SmartActivitiesEntity smartActivities){
		smartActivitiesDao.update(smartActivities);
	}
	
	@Override
	public void delete(Integer id){
		smartActivitiesDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		smartActivitiesDao.deleteBatch(ids);
	}
	
}
