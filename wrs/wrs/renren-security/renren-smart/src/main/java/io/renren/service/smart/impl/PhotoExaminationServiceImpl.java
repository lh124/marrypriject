package io.renren.service.smart.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.dao.smart.PhotoExaminationDao;
import io.renren.entity.smart.PhotoExaminationEntity;
import io.renren.service.smart.PhotoExaminationService;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoExaminationService")
public class PhotoExaminationServiceImpl extends ServiceImpl<PhotoExaminationDao, PhotoExaminationEntity>  implements PhotoExaminationService {
	@Autowired
	private PhotoExaminationDao photoExaminationDao;
	
	@Override
	public PhotoExaminationEntity queryObject(Long id){
		return photoExaminationDao.queryObject(id);
	}
	
	@Override
	public List<PhotoExaminationEntity> queryList(Map<String, Object> map){
		return photoExaminationDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoExaminationDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoExaminationEntity photoExamination){
		photoExaminationDao.save(photoExamination);
	}
	
	@Override
	public void update(PhotoExaminationEntity photoExamination){
		photoExaminationDao.update(photoExamination);
	}
	
	@Override
	public void delete(Long id){
		photoExaminationDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoExaminationDao.deleteBatch(ids);
	}
	
}
