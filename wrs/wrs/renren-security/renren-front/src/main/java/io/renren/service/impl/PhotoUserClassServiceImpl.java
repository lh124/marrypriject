package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoUserClassDao;
import io.renren.entity.PhotoUserClassEntity;
import io.renren.service.PhotoUserClassService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoUserClassService")
public class PhotoUserClassServiceImpl extends ServiceImpl<PhotoUserClassDao, PhotoUserClassEntity>  implements PhotoUserClassService {
	@Autowired
	private PhotoUserClassDao photoUserClassDao;
	
	@Override
	public PhotoUserClassEntity queryObject(Long id){
		return photoUserClassDao.queryObject(id);
	}
	
	@Override
	public List<PhotoUserClassEntity> queryList(Map<String, Object> map){
		return photoUserClassDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoUserClassDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoUserClassEntity photoUserClass){
		photoUserClassDao.save(photoUserClass);
	}
	
	@Override
	public void update(PhotoUserClassEntity photoUserClass){
		photoUserClassDao.update(photoUserClass);
	}
	
	@Override
	public void delete(Long id){
		photoUserClassDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoUserClassDao.deleteBatch(ids);
	}
	
}
