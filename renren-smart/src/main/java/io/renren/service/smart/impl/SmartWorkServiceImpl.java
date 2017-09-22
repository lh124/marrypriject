package io.renren.service.smart.impl;

import io.renren.dao.smart.SmartWorkDao;
import io.renren.entity.smart.SmartWorkEntity;
import io.renren.service.smart.SmartWorkService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("smartWorkService")
public class SmartWorkServiceImpl extends ServiceImpl<SmartWorkDao, SmartWorkEntity>  implements SmartWorkService {
	@Autowired
	private SmartWorkDao smartWorkDao;
	
	@Override
	public SmartWorkEntity queryObject(Integer id){
		return smartWorkDao.queryObject(id);
	}
	
	@Override
	public List<SmartWorkEntity> queryList(Map<String, Object> map){
		return smartWorkDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return smartWorkDao.queryTotal(map);
	}
	
	@Override
	public void save(SmartWorkEntity smartWork){
		smartWorkDao.save(smartWork);
	}
	
	@Override
	public void update(SmartWorkEntity smartWork){
		smartWorkDao.update(smartWork);
	}
	
	@Override
	public void delete(Integer id){
		smartWorkDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		smartWorkDao.deleteBatch(ids);
	}
	
}
