package io.renren.service.married.impl;

import io.renren.dao.married.MarryParticipateDao;
import io.renren.entity.married.MarryParticipateEntity;
import io.renren.service.married.MarryParticipateService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("marryParticipateService")
public class MarryParticipateServiceImpl extends ServiceImpl<MarryParticipateDao, MarryParticipateEntity>  implements MarryParticipateService {
	@Autowired
	private MarryParticipateDao marryParticipateDao;
	
	@Override
	public MarryParticipateEntity queryObject(Integer id){
		return marryParticipateDao.queryObject(id);
	}
	
	@Override
	public List<MarryParticipateEntity> queryList(Map<String, Object> map){
		return marryParticipateDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return marryParticipateDao.queryTotal(map);
	}
	
	@Override
	public void save(MarryParticipateEntity marryParticipate){
		marryParticipateDao.save(marryParticipate);
	}
	
	@Override
	public void update(MarryParticipateEntity marryParticipate){
		marryParticipateDao.update(marryParticipate);
	}
	
	@Override
	public void delete(Integer id){
		marryParticipateDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		marryParticipateDao.deleteBatch(ids);
	}
	
}
