package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoClassDao;
import io.renren.entity.PhotoClassEntity;
import io.renren.service.PhotoClassService;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoClassService")
public class PhotoClassServiceImpl extends ServiceImpl<PhotoClassDao, PhotoClassEntity>  implements PhotoClassService {
	@Autowired
	private PhotoClassDao photoClassDao;
	
	@Override
	public PhotoClassEntity queryObject(Long id){
		return photoClassDao.queryObject(id);
	}
	
	@Override
	public List<PhotoClassEntity> queryList(Map<String, Object> map){
		return photoClassDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoClassDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoClassEntity photoClass){
		photoClassDao.save(photoClass);
	}
	
	@Override
	public void update(PhotoClassEntity photoClass){
		photoClassDao.update(photoClass);
	}
	
	@Override
	public void delete(Long id){
		photoClassDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoClassDao.deleteBatch(ids);
	}

	@Override
	public List<HashMap<String, Object>> queryMapObject() {
		// TODO Auto-generated method stub
		
		return photoClassDao.queryMapObject();
	}

	@Override
	public List<PhotoClassEntity> querAdminClass(Long userId, int roleType) {
		// TODO Auto-generated method stub
		
		return photoClassDao.querAdminClass(userId, roleType);
	}
	
}
