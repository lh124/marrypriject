package io.renren.service.married.impl;

import io.renren.dao.married.MarryMainDao;
import io.renren.entity.married.MarryMainEntity;
import io.renren.service.married.MarryMainService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("marryMainService")
public class MarryMainServiceImpl extends ServiceImpl<MarryMainDao, MarryMainEntity>  implements MarryMainService {
	@Autowired
	private MarryMainDao marryMainDao;
	
	@Override
	public MarryMainEntity queryObject(Integer id){
		return marryMainDao.queryObject(id);
	}
	
	@Override
	public List<MarryMainEntity> queryList(Map<String, Object> map){
		return marryMainDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return marryMainDao.queryTotal(map);
	}
	
	@Override
	public void save(MarryMainEntity marryMain){
		marryMainDao.save(marryMain);
	}
	
	@Override
	public void update(MarryMainEntity marryMain){
		marryMainDao.update(marryMain);
	}
	
	@Override
	public void delete(Integer id){
		marryMainDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		marryMainDao.deleteBatch(ids);
	}
	
}
