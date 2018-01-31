package io.renren.service.smart.impl;

import io.renren.dao.smart.SmartRankingDao;
import io.renren.entity.smart.SmartRankingEntity;
import io.renren.service.smart.SmartRankingService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("smartRankingService")
public class SmartRankingServiceImpl extends ServiceImpl<SmartRankingDao, SmartRankingEntity>  implements SmartRankingService {
	@Autowired
	private SmartRankingDao smartRankingDao;
	
	@Override
	public SmartRankingEntity queryObject(Integer id){
		return smartRankingDao.queryObject(id);
	}
	
	@Override
	public List<SmartRankingEntity> queryList(Map<String, Object> map){
		return smartRankingDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return smartRankingDao.queryTotal(map);
	}
	
	@Override
	public void save(SmartRankingEntity smartRanking){
		smartRankingDao.save(smartRanking);
	}
	
	@Override
	public void update(SmartRankingEntity smartRanking){
		smartRankingDao.update(smartRanking);
	}
	
	@Override
	public void delete(Integer id){
		smartRankingDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		smartRankingDao.deleteBatch(ids);
	}
	
}
