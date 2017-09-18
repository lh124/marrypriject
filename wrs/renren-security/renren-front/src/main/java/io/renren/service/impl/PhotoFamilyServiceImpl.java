package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoFamilyDao;
import io.renren.entity.PhotoFamilyEntity;
import io.renren.service.PhotoFamilyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoFamilyService")
public class PhotoFamilyServiceImpl extends ServiceImpl<PhotoFamilyDao, PhotoFamilyEntity>  implements PhotoFamilyService {
	@Autowired
	private PhotoFamilyDao photoFamilyDao;
	
	@Override
	public PhotoFamilyEntity queryObject(Long id){
		return photoFamilyDao.queryObject(id);
	}
	
	@Override
	public List<PhotoFamilyEntity> queryList(Map<String, Object> map){
		return photoFamilyDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoFamilyDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoFamilyEntity photoFamily){
		photoFamilyDao.save(photoFamily);
	}
	
	@Override
	public void update(PhotoFamilyEntity photoFamily){
		photoFamilyDao.update(photoFamily);
	}
	
	@Override
	public void delete(Long id){
		photoFamilyDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoFamilyDao.deleteBatch(ids);
	}
	
}
