package io.renren.service.married.impl;

import io.renren.dao.married.MarryGetmoneyDao;
import io.renren.entity.married.MarryGetmoneyEntity;
import io.renren.service.married.MarryGetmoneyService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("marryGetmoneyService")
public class MarryGetmoneyServiceImpl extends ServiceImpl<MarryGetmoneyDao, MarryGetmoneyEntity>  implements MarryGetmoneyService {
	@Autowired
	private MarryGetmoneyDao marryGetmoneyDao;
	
	@Override
	public MarryGetmoneyEntity queryObject(Integer id){
		return marryGetmoneyDao.queryObject(id);
	}
	
	@Override
	public List<MarryGetmoneyEntity> queryList(Map<String, Object> map){
		return marryGetmoneyDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return marryGetmoneyDao.queryTotal(map);
	}
	
	@Override
	public void save(MarryGetmoneyEntity marryGetmoney){
		marryGetmoneyDao.save(marryGetmoney);
	}
	
	@Override
	public void update(MarryGetmoneyEntity marryGetmoney){
		marryGetmoneyDao.update(marryGetmoney);
	}
	
	@Override
	public void delete(Integer id){
		marryGetmoneyDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		marryGetmoneyDao.deleteBatch(ids);
	}
	
}
