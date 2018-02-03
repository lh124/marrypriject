package io.renren.service.married.impl;

import io.renren.dao.married.MarryOrdersDao;
import io.renren.entity.married.MarryOrdersEntity;
import io.renren.service.married.MarryOrdersService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("marryOrdersService")
public class MarryOrdersServiceImpl extends ServiceImpl<MarryOrdersDao, MarryOrdersEntity>  implements MarryOrdersService {
	@Autowired
	private MarryOrdersDao marryOrdersDao;
	
	@Override
	public MarryOrdersEntity queryObject(Integer id){
		return marryOrdersDao.queryObject(id);
	}
	
	@Override
	public List<MarryOrdersEntity> queryList(Map<String, Object> map){
		return marryOrdersDao.queryList(map);
	}
	
	@Override
	public List<MarryOrdersEntity> queryList1(Map<String, Object> map){
		return marryOrdersDao.queryList1(map);
	}
	
	@Override
	public List<MarryOrdersEntity> queryListorder(Map<String, Object> map){
		return marryOrdersDao.queryListorder(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return marryOrdersDao.queryTotal(map);
	}
	
	@Override
	public void save(MarryOrdersEntity marryOrders){
		marryOrdersDao.save(marryOrders);
	}
	
	@Override
	public void update(MarryOrdersEntity marryOrders){
		marryOrdersDao.update(marryOrders);
	}
	
	@Override
	public void delete(Integer id){
		marryOrdersDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		marryOrdersDao.deleteBatch(ids);
	}
	
}
