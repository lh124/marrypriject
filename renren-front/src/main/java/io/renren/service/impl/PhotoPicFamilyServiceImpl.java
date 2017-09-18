package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoPicFamilyDao;
import io.renren.entity.PhotoPicFamilyEntity;
import io.renren.service.PhotoPicFamilyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoPicFamilyService")
public class PhotoPicFamilyServiceImpl extends ServiceImpl<PhotoPicFamilyDao, PhotoPicFamilyEntity>  implements PhotoPicFamilyService {
	@Autowired
	private PhotoPicFamilyDao photoPicFamilyDao;
	
	@Override
	public PhotoPicFamilyEntity queryObject(Long id){
		return photoPicFamilyDao.queryObject(id);
	}
	
	@Override
	public List<PhotoPicFamilyEntity> queryList(Map<String, Object> map){
		return photoPicFamilyDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoPicFamilyDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoPicFamilyEntity photoPicFamily){
		photoPicFamilyDao.save(photoPicFamily);
	}
	
	@Override
	public void update(PhotoPicFamilyEntity photoPicFamily){
		photoPicFamilyDao.update(photoPicFamily);
	}
	
	@Override
	public void delete(Long id){
		photoPicFamilyDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoPicFamilyDao.deleteBatch(ids);
	}
	
}
