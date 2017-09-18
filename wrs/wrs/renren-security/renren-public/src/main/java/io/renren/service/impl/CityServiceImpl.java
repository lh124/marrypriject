package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.CityDao;
import io.renren.entity.CityEntity;
import io.renren.service.CityService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("cityService")
public class CityServiceImpl extends ServiceImpl<CityDao, CityEntity>  implements CityService {
	@Autowired
	private CityDao cityDao;
	
	@Override
	public CityEntity queryObject(Integer id){
		return cityDao.queryObject(id);
	}
	
	@Override
	public List<CityEntity> queryList(Map<String, Object> map){
		return cityDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return cityDao.queryTotal(map);
	}
	
	@Override
	public void save(CityEntity city){
		cityDao.save(city);
	}
	
	@Override
	public void update(CityEntity city){
		cityDao.update(city);
	}
	
	@Override
	public void delete(Integer id){
		cityDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		cityDao.deleteBatch(ids);
	}
	
}
