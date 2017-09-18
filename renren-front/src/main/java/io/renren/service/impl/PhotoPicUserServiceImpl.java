package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoPicUserDao;
import io.renren.entity.PhotoPicUserEntity;
import io.renren.service.PhotoPicUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoPicUserService")
public class PhotoPicUserServiceImpl extends ServiceImpl<PhotoPicUserDao, PhotoPicUserEntity>  implements PhotoPicUserService {
	@Autowired
	private PhotoPicUserDao photoPicUserDao;
	
	@Override
	public PhotoPicUserEntity queryObject(Long id){
		return photoPicUserDao.queryObject(id);
	}
	
	@Override
	public List<PhotoPicUserEntity> queryList(Map<String, Object> map){
		return photoPicUserDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoPicUserDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoPicUserEntity photoPicUser){
		photoPicUserDao.save(photoPicUser);
	}
	
	@Override
	public void update(PhotoPicUserEntity photoPicUser){
		photoPicUserDao.update(photoPicUser);
	}
	
	@Override
	public void delete(Long id){
		photoPicUserDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoPicUserDao.deleteBatch(ids);
	}
	
}
