package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoPicPublicDao;
import io.renren.entity.PhotoPicPublicEntity;
import io.renren.service.PhotoPicPublicService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoPicPublicService")
public class PhotoPicPublicServiceImpl extends ServiceImpl<PhotoPicPublicDao, PhotoPicPublicEntity>  implements PhotoPicPublicService {
	@Autowired
	private PhotoPicPublicDao photoPicPublicDao;
	
	@Override
	public PhotoPicPublicEntity queryObject(Long id){
		return photoPicPublicDao.queryObject(id);
	}
	
	@Override
	public List<PhotoPicPublicEntity> queryList(Map<String, Object> map){
		return photoPicPublicDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoPicPublicDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoPicPublicEntity photoPicPublic){
		photoPicPublicDao.save(photoPicPublic);
	}
	
	@Override
	public void update(PhotoPicPublicEntity photoPicPublic){
		photoPicPublicDao.update(photoPicPublic);
	}
	
	@Override
	public void delete(Long id){
		photoPicPublicDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoPicPublicDao.deleteBatch(ids);
	}
	
}
