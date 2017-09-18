package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoPicHeadDao;
import io.renren.entity.PhotoPicHeadEntity;
import io.renren.service.PhotoPicHeadService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoPicHeadService")
public class PhotoPicHeadServiceImpl extends ServiceImpl<PhotoPicHeadDao, PhotoPicHeadEntity>  implements PhotoPicHeadService {
	@Autowired
	private PhotoPicHeadDao photoPicHeadDao;
	
	@Override
	public PhotoPicHeadEntity queryObject(Long id){
		return photoPicHeadDao.queryObject(id);
	}
	
	@Override
	public List<PhotoPicHeadEntity> queryList(Map<String, Object> map){
		return photoPicHeadDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoPicHeadDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoPicHeadEntity photoPicHead){
		photoPicHeadDao.save(photoPicHead);
	}
	
	@Override
	public void update(PhotoPicHeadEntity photoPicHead){
		photoPicHeadDao.update(photoPicHead);
	}
	
	@Override
	public void delete(Long id){
		photoPicHeadDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoPicHeadDao.deleteBatch(ids);
	}
	
}
