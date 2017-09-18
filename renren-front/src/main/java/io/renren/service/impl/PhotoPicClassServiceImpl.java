package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoPicClassDao;
import io.renren.entity.PhotoPicClassEntity;
import io.renren.service.PhotoPicClassService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoPicClassService")
public class PhotoPicClassServiceImpl extends ServiceImpl<PhotoPicClassDao, PhotoPicClassEntity>  implements PhotoPicClassService {
	@Autowired
	private PhotoPicClassDao photoPicClassDao;
	
	@Override
	public PhotoPicClassEntity queryObject(Long id){
		return photoPicClassDao.queryObject(id);
	}
	
	@Override
	public List<PhotoPicClassEntity> queryList(Map<String, Object> map){
		return photoPicClassDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoPicClassDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoPicClassEntity photoPicClass){
		photoPicClassDao.save(photoPicClass);
	}
	
	@Override
	public void update(PhotoPicClassEntity photoPicClass){
		photoPicClassDao.update(photoPicClass);
	}
	
	@Override
	public void delete(Long id){
		photoPicClassDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoPicClassDao.deleteBatch(ids);
	}
	
}
