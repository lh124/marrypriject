package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoPicSchoolDao;
import io.renren.entity.PhotoPicSchoolEntity;
import io.renren.service.PhotoPicSchoolService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoPicSchoolService")
public class PhotoPicSchoolServiceImpl extends ServiceImpl<PhotoPicSchoolDao, PhotoPicSchoolEntity>  implements PhotoPicSchoolService {
	@Autowired
	private PhotoPicSchoolDao photoPicSchoolDao;
	
	@Override
	public PhotoPicSchoolEntity queryObject(Long id){
		return photoPicSchoolDao.queryObject(id);
	}
	
	@Override
	public List<PhotoPicSchoolEntity> queryList(Map<String, Object> map){
		return photoPicSchoolDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoPicSchoolDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoPicSchoolEntity photoPicSchool){
		photoPicSchoolDao.save(photoPicSchool);
	}
	
	@Override
	public void update(PhotoPicSchoolEntity photoPicSchool){
		photoPicSchoolDao.update(photoPicSchool);
	}
	
	@Override
	public void delete(Long id){
		photoPicSchoolDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoPicSchoolDao.deleteBatch(ids);
	}
	
}
