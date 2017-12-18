package io.renren.service.married.impl;

import io.renren.dao.married.MarryWeddingDao;
import io.renren.entity.married.MarryWeddingEntity;
import io.renren.service.married.MarryWeddingService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("marryWeddingService")
public class MarryWeddingServiceImpl extends ServiceImpl<MarryWeddingDao, MarryWeddingEntity>  implements MarryWeddingService {
	@Autowired
	private MarryWeddingDao marryWeddingDao;
	
	@Override
	public MarryWeddingEntity queryObject(Integer id){
		return marryWeddingDao.queryObject(id);
	}
	
	@Override
	public List<MarryWeddingEntity> queryList(Map<String, Object> map){
		return marryWeddingDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return marryWeddingDao.queryTotal(map);
	}
	
	@Override
	public void save(MarryWeddingEntity marryWedding){
		marryWeddingDao.save(marryWedding);
	}
	
	@Override
	public void update(MarryWeddingEntity marryWedding){
		marryWeddingDao.update(marryWedding);
	}
	
	@Override
	public void delete(Integer id){
		marryWeddingDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		marryWeddingDao.deleteBatch(ids);
	}
	
}
