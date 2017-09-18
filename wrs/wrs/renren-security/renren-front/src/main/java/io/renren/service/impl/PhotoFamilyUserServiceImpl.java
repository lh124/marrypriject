package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoFamilyUserDao;
import io.renren.entity.PhotoFamilyUserEntity;
import io.renren.service.PhotoFamilyUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoFamilyUserService")
public class PhotoFamilyUserServiceImpl extends ServiceImpl<PhotoFamilyUserDao, PhotoFamilyUserEntity>  implements PhotoFamilyUserService {
	@Autowired
	private PhotoFamilyUserDao photoFamilyUserDao;
	
	@Override
	public PhotoFamilyUserEntity queryObject(Long id){
		return photoFamilyUserDao.queryObject(id);
	}
	
	@Override
	public List<PhotoFamilyUserEntity> queryList(Map<String, Object> map){
		return photoFamilyUserDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoFamilyUserDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoFamilyUserEntity photoFamilyUser){
		photoFamilyUserDao.save(photoFamilyUser);
	}
	
	@Override
	public void update(PhotoFamilyUserEntity photoFamilyUser){
		photoFamilyUserDao.update(photoFamilyUser);
	}
	
	@Override
	public void delete(Long id){
		photoFamilyUserDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoFamilyUserDao.deleteBatch(ids);
	}
	
}
