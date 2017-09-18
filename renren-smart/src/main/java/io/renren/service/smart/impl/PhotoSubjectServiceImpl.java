package io.renren.service.smart.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.dao.smart.PhotoSubjectDao;
import io.renren.entity.smart.PhotoSubjectEntity;
import io.renren.service.smart.PhotoSubjectService;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoSubjectService")
public class PhotoSubjectServiceImpl extends ServiceImpl<PhotoSubjectDao, PhotoSubjectEntity>  implements PhotoSubjectService {
	@Autowired
	private PhotoSubjectDao photoSubjectDao;
	
	@Override
	public PhotoSubjectEntity queryObject(Long id){
		return photoSubjectDao.queryObject(id);
	}
	
	@Override
	public List<PhotoSubjectEntity> queryList(Map<String, Object> map){
		return photoSubjectDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoSubjectDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoSubjectEntity photoSubject){
		photoSubjectDao.save(photoSubject);
	}
	
	@Override
	public void update(PhotoSubjectEntity photoSubject){
		photoSubjectDao.update(photoSubject);
	}
	
	@Override
	public void delete(Long id){
		photoSubjectDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoSubjectDao.deleteBatch(ids);
	}
	
}
