package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoGraduationTimeDao;
import io.renren.entity.PhotoGraduationTimeEntity;
import io.renren.service.PhotoGraduationTimeService;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoGraduationTimeService")
public class PhotoGraduationTimeServiceImpl extends ServiceImpl<PhotoGraduationTimeDao, PhotoGraduationTimeEntity>  implements PhotoGraduationTimeService {
	@Autowired
	private PhotoGraduationTimeDao photoGraduationTimeDao;
	
	@Override
	public PhotoGraduationTimeEntity queryObject(Integer id){
		return photoGraduationTimeDao.queryObject(id);
	}
	
	@Override
	public List<PhotoGraduationTimeEntity> queryList(Map<String, Object> map){
		return photoGraduationTimeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoGraduationTimeDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoGraduationTimeEntity photoGraduationTime){
		photoGraduationTimeDao.save(photoGraduationTime);
	}
	
	@Override
	public void update(PhotoGraduationTimeEntity photoGraduationTime){
		photoGraduationTimeDao.update(photoGraduationTime);
	}
	
	@Override
	public void delete(Integer id){
		photoGraduationTimeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		photoGraduationTimeDao.deleteBatch(ids);
	}

	@Override
	public List<PhotoGraduationTimeEntity> queryGraduationClass(Integer status,
			Long schoolId, Long collegeId, Integer classify) {
		// TODO Auto-generated method stub
		return photoGraduationTimeDao.queryGraduationClass(status, schoolId, collegeId, classify);
	}
	
}
