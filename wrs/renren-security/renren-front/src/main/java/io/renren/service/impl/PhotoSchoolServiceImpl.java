package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoSchoolDao;
import io.renren.entity.PhotoSchoolEntity;
import io.renren.service.PhotoSchoolService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoSchoolService")
public class PhotoSchoolServiceImpl extends ServiceImpl<PhotoSchoolDao, PhotoSchoolEntity>  implements PhotoSchoolService {
	@Autowired
	private PhotoSchoolDao photoSchoolDao;
	
	@Override
	public PhotoSchoolEntity queryObject(Long id){
		return photoSchoolDao.queryObject(id);
	}
	
	@Override
	public List<PhotoSchoolEntity> queryList(Map<String, Object> map){
		return photoSchoolDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoSchoolDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoSchoolEntity photoSchool){
		photoSchoolDao.save(photoSchool);
	}
	
	@Override
	public void update(PhotoSchoolEntity photoSchool){
		photoSchoolDao.update(photoSchool);
	}
	
	@Override
	public void delete(Long id){
		photoSchoolDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoSchoolDao.deleteBatch(ids);
	}
	
}
