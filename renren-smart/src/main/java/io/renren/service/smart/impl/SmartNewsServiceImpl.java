package io.renren.service.smart.impl;

import io.renren.dao.app.SmartNewsDao;
import io.renren.entity.app.SmartNewsEntity;
import io.renren.service.smart.SmartNewsService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("smartNewsService")
public class SmartNewsServiceImpl extends ServiceImpl<SmartNewsDao, SmartNewsEntity>  implements SmartNewsService {
	@Autowired
	private SmartNewsDao smartNewsDao;
	
	@Override
	public SmartNewsEntity queryObject(Integer id){
		return smartNewsDao.queryObject(id);
	}
	
	@Override
	public List<SmartNewsEntity> queryList(Map<String, Object> map){
		return smartNewsDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return smartNewsDao.queryTotal(map);
	}
	
	@Override
	public void save(SmartNewsEntity smartNews){
		smartNewsDao.save(smartNews);
	}
	
	@Override
	public void update(SmartNewsEntity smartNews){
		smartNewsDao.update(smartNews);
	}
	
	@Override
	public void delete(Integer id){
		smartNewsDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		smartNewsDao.deleteBatch(ids);
	}
	
}
