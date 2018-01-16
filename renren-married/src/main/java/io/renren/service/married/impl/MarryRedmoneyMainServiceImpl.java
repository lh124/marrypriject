package io.renren.service.married.impl;

import io.renren.dao.married.MarryRedmoneyMainDao;
import io.renren.entity.married.MarryRedmoneyMainEntity;
import io.renren.service.married.MarryRedmoneyMainService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("marryRedmoneyMainService")
public class MarryRedmoneyMainServiceImpl extends ServiceImpl<MarryRedmoneyMainDao, MarryRedmoneyMainEntity>  implements MarryRedmoneyMainService {
	@Autowired
	private MarryRedmoneyMainDao marryRedmoneyMainDao;
	
	@Override
	public MarryRedmoneyMainEntity queryObject(Integer id){
		return marryRedmoneyMainDao.queryObject(id);
	}
	
	@Override
	public List<MarryRedmoneyMainEntity> queryList(Map<String, Object> map){
		return marryRedmoneyMainDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return marryRedmoneyMainDao.queryTotal(map);
	}
	
	@Override
	public void save(MarryRedmoneyMainEntity marryRedmoneyMain){
		marryRedmoneyMainDao.save(marryRedmoneyMain);
	}
	
	@Override
	public void update(MarryRedmoneyMainEntity marryRedmoneyMain){
		marryRedmoneyMainDao.update(marryRedmoneyMain);
	}
	
	@Override
	public void delete(Integer id){
		marryRedmoneyMainDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		marryRedmoneyMainDao.deleteBatch(ids);
	}
	
}
