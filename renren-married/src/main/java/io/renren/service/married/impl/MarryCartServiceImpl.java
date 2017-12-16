package io.renren.service.married.impl;

import io.renren.dao.married.MarryCartDao;
import io.renren.entity.married.MarryCartEntity;
import io.renren.service.married.MarryCartService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("marryCartService")
public class MarryCartServiceImpl extends ServiceImpl<MarryCartDao, MarryCartEntity>  implements MarryCartService {
	@Autowired
	private MarryCartDao marryCartDao;
	
	@Override
	public MarryCartEntity queryObject(Integer id){
		return marryCartDao.queryObject(id);
	}
	
	@Override
	public List<MarryCartEntity> queryList(Map<String, Object> map){
		return marryCartDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return marryCartDao.queryTotal(map);
	}
	
	@Override
	public void save(MarryCartEntity marryCart){
		marryCartDao.save(marryCart);
	}
	
	@Override
	public void update(MarryCartEntity marryCart){
		marryCartDao.update(marryCart);
	}
	
	@Override
	public void delete(Integer id){
		marryCartDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		marryCartDao.deleteBatch(ids);
	}
	
}
