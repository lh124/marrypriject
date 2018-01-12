package io.renren.service.married.impl;

import io.renren.dao.married.MarryOrderMainDao;
import io.renren.entity.married.MarryOrderMainEntity;
import io.renren.service.married.MarryOrderMainService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("marryOrderMainService")
public class MarryOrderMainServiceImpl extends ServiceImpl<MarryOrderMainDao, MarryOrderMainEntity>  implements MarryOrderMainService {
	@Autowired
	private MarryOrderMainDao marryOrderMainDao;
	
	@Override
	public MarryOrderMainEntity queryObject(Integer orderId){
		return marryOrderMainDao.queryObject(orderId);
	}
	
	@Override
	public List<MarryOrderMainEntity> queryList(Map<String, Object> map){
		return marryOrderMainDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return marryOrderMainDao.queryTotal(map);
	}
	
	@Override
	public void save(MarryOrderMainEntity marryOrderMain){
		marryOrderMainDao.save(marryOrderMain);
	}
	
	@Override
	public void update(MarryOrderMainEntity marryOrderMain){
		marryOrderMainDao.update(marryOrderMain);
	}
	
	@Override
	public void delete(Integer orderId){
		marryOrderMainDao.delete(orderId);
	}
	
	@Override
	public void deleteBatch(Integer[] orderIds){
		marryOrderMainDao.deleteBatch(orderIds);
	}
	
}
