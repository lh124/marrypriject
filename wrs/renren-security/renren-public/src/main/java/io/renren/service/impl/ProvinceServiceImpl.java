package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.ProvinceDao;
import io.renren.entity.ProvinceEntity;
import io.renren.service.ProvinceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("provinceService")
public class ProvinceServiceImpl extends ServiceImpl<ProvinceDao, ProvinceEntity>  implements ProvinceService {
	@Autowired
	private ProvinceDao provinceDao;
	
	@Override
	public ProvinceEntity queryObject(Integer id){
		return provinceDao.queryObject(id);
	}
	
	@Override
	public List<ProvinceEntity> queryList(Map<String, Object> map){
		return provinceDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return provinceDao.queryTotal(map);
	}
	
	@Override
	public void save(ProvinceEntity province){
		provinceDao.save(province);
	}
	
	@Override
	public void update(ProvinceEntity province){
		provinceDao.update(province);
	}
	
	@Override
	public void delete(Integer id){
		provinceDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		provinceDao.deleteBatch(ids);
	}
	
}
