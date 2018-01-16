package io.renren.service.married.impl;

import io.renren.dao.married.MarryRedmoneyDetailDao;
import io.renren.entity.married.MarryRedmoneyDetailEntity;
import io.renren.service.married.MarryRedmoneyDetailService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("marryRedmoneyDetailService")
public class MarryRedmoneyDetailServiceImpl extends ServiceImpl<MarryRedmoneyDetailDao, MarryRedmoneyDetailEntity>  implements MarryRedmoneyDetailService {
	@Autowired
	private MarryRedmoneyDetailDao marryRedmoneyDetailDao;
	
	@Override
	public MarryRedmoneyDetailEntity queryObject(Integer id){
		return marryRedmoneyDetailDao.queryObject(id);
	}
	
	@Override
	public List<MarryRedmoneyDetailEntity> queryList(Map<String, Object> map){
		return marryRedmoneyDetailDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return marryRedmoneyDetailDao.queryTotal(map);
	}
	
	@Override
	public void save(MarryRedmoneyDetailEntity marryRedmoneyDetail){
		marryRedmoneyDetailDao.save(marryRedmoneyDetail);
	}
	
	@Override
	public void update(MarryRedmoneyDetailEntity marryRedmoneyDetail){
		marryRedmoneyDetailDao.update(marryRedmoneyDetail);
	}
	
	@Override
	public void delete(Integer id){
		marryRedmoneyDetailDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		marryRedmoneyDetailDao.deleteBatch(ids);
	}
	
}
