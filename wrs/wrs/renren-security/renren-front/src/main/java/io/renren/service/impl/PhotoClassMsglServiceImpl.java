package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoClassMsglDao;
import io.renren.entity.PhotoClassMsglEntity;
import io.renren.service.PhotoClassMsglService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoClassMsglService")
public class PhotoClassMsglServiceImpl extends ServiceImpl<PhotoClassMsglDao, PhotoClassMsglEntity>  implements PhotoClassMsglService {
	@Autowired
	private PhotoClassMsglDao photoClassMsglDao;
	
	@Override
	public PhotoClassMsglEntity queryObject(Long id){
		return photoClassMsglDao.queryObject(id);
	}
	
	@Override
	public List<PhotoClassMsglEntity> queryList(Map<String, Object> map){
		return photoClassMsglDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoClassMsglDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoClassMsglEntity photoClassMsgl){
		photoClassMsglDao.save(photoClassMsgl);
	}
	
	@Override
	public void update(PhotoClassMsglEntity photoClassMsgl){
		photoClassMsglDao.update(photoClassMsgl);
	}
	
	@Override
	public void delete(Long id){
		photoClassMsglDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoClassMsglDao.deleteBatch(ids);
	}
	
}
