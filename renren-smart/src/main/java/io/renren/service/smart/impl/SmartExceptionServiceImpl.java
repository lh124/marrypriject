package io.renren.service.smart.impl;

import io.renren.dao.smart.SmartExceptionDao;
import io.renren.entity.smart.SmartExceptionEntity;
import io.renren.service.smart.SmartExceptionService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("smartExceptionService")
public class SmartExceptionServiceImpl extends ServiceImpl<SmartExceptionDao, SmartExceptionEntity>  implements SmartExceptionService {
	@Autowired
	private SmartExceptionDao smartExceptionDao;
	
	@Override
	public SmartExceptionEntity queryObject(Integer id){
		return smartExceptionDao.queryObject(id);
	}
	
	@Override
	public List<SmartExceptionEntity> queryList(Map<String, Object> map){
		return smartExceptionDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return smartExceptionDao.queryTotal(map);
	}
	
	@Override
	public void save(SmartExceptionEntity smartException){
		smartExceptionDao.save(smartException);
	}
	
	@Override
	public void update(SmartExceptionEntity smartException){
		smartExceptionDao.update(smartException);
	}
	
	@Override
	public void delete(Integer id){
		smartExceptionDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		smartExceptionDao.deleteBatch(ids);
	}
	
}
