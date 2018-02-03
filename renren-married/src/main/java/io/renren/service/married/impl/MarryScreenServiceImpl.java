package io.renren.service.married.impl;

import io.renren.dao.married.MarryScreenDao;
import io.renren.entity.married.MarryScreenEntity;
import io.renren.service.married.MarryScreenService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("marryScreenService")
public class MarryScreenServiceImpl extends ServiceImpl<MarryScreenDao, MarryScreenEntity>  implements MarryScreenService {
	@Autowired
	private MarryScreenDao marryScreenDao;
	
	@Override
	public MarryScreenEntity queryObject(Integer id){
		return marryScreenDao.queryObject(id);
	}
	
	@Override
	public List<MarryScreenEntity> queryList(Map<String, Object> map){
		return marryScreenDao.queryList(map);
	}
	
	@Override
	public List<MarryScreenEntity> queryList1(Map<String, Object> map){
		return marryScreenDao.queryList1(map);
	}
	@Override
	public MarryScreenEntity queryObjectScreen(Integer id){
		return marryScreenDao.queryObjectScreen(id);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return marryScreenDao.queryTotal(map);
	}
	
	@Override
	public void save(MarryScreenEntity marryScreen){
		marryScreenDao.save(marryScreen);
	}
	
	@Override
	public void update(MarryScreenEntity marryScreen){
		marryScreenDao.update(marryScreen);
	}
	
	@Override
	public void delete(Integer id){
		marryScreenDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		marryScreenDao.deleteBatch(ids);
	}
	
}
