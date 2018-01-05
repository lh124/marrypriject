package io.renren.service.married.impl;

import io.renren.dao.married.MarrySignDao;
import io.renren.entity.married.MarrySignEntity;
import io.renren.service.married.MarrySignService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("marrySignService")
public class MarrySignServiceImpl extends ServiceImpl<MarrySignDao, MarrySignEntity>  implements MarrySignService {
	@Autowired
	private MarrySignDao marrySignDao;
	
	@Override
	public MarrySignEntity queryObject(Integer id){
		return marrySignDao.queryObject(id);
	}
	
	@Override
	public List<MarrySignEntity> queryList(Map<String, Object> map){
		return marrySignDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return marrySignDao.queryTotal(map);
	}
	
	@Override
	public void save(MarrySignEntity marrySign){
		marrySignDao.save(marrySign);
	}
	
	@Override
	public void update(MarrySignEntity marrySign){
		marrySignDao.update(marrySign);
	}
	
	@Override
	public void delete(Integer id){
		marrySignDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		marrySignDao.deleteBatch(ids);
	}

	@Override
	public List<MarrySignEntity> queryListtongji(Map<String, Object> map) {
		return marrySignDao.queryListtongji(map);
	}
	
}
