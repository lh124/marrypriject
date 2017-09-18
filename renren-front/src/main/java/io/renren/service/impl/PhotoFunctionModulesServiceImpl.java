package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoFunctionModulesDao;
import io.renren.entity.PhotoFunctionModulesEntity;
import io.renren.service.PhotoFunctionModulesService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoFunctionModulesService")
public class PhotoFunctionModulesServiceImpl extends ServiceImpl<PhotoFunctionModulesDao, PhotoFunctionModulesEntity>  implements PhotoFunctionModulesService {
	@Autowired
	private PhotoFunctionModulesDao photoFunctionModulesDao;
	
	@Override
	public PhotoFunctionModulesEntity queryObject(Integer id){
		return photoFunctionModulesDao.queryObject(id);
	}
	
	@Override
	public List<PhotoFunctionModulesEntity> queryList(Map<String, Object> map){
		return photoFunctionModulesDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoFunctionModulesDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoFunctionModulesEntity photoFunctionModules){
		photoFunctionModulesDao.save(photoFunctionModules);
	}
	
	@Override
	public void update(PhotoFunctionModulesEntity photoFunctionModules){
		photoFunctionModulesDao.update(photoFunctionModules);
	}
	
	@Override
	public void delete(Integer id){
		photoFunctionModulesDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		photoFunctionModulesDao.deleteBatch(ids);
	}
	
}
