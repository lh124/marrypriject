package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoTypeDao;
import io.renren.entity.PhotoTypeEntity;
import io.renren.service.PhotoTypeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoTypeService")
public class PhotoTypeServiceImpl extends ServiceImpl<PhotoTypeDao, PhotoTypeEntity>  implements PhotoTypeService {
	@Autowired
	private PhotoTypeDao photoTypeDao;
	
	@Override
	public PhotoTypeEntity queryObject(Long id){
		return photoTypeDao.queryObject(id);
	}
	
	@Override
	public List<PhotoTypeEntity> queryList(Map<String, Object> map){
		return photoTypeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoTypeDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoTypeEntity photoType){
		photoTypeDao.save(photoType);
	}
	
	@Override
	public void update(PhotoTypeEntity photoType){
		photoTypeDao.update(photoType);
	}
	
	@Override
	public void delete(Long id){
		photoTypeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoTypeDao.deleteBatch(ids);
	}

	@Override
	public List<PhotoTypeEntity> queryPhotoTypes(int type, long relatedId,
			boolean getMinType) {
		// TODO Auto-generated method stub
		return photoTypeDao.queryPhotoTypes(type, relatedId, getMinType);
	}

	@Override
	public List<PhotoTypeEntity> queryUserPhotoTypes(int type, long relatedId) {
		// TODO Auto-generated method stub
		return photoTypeDao.queryUserPhotoTypes(type, relatedId);
	}

	@Override
	public List<PhotoTypeEntity> queryClassPhotoTypes(int type, long relatedId) {
		// TODO Auto-generated method stub
		return photoTypeDao.queryClassPhotoTypes(type, relatedId);
	}
	
}
