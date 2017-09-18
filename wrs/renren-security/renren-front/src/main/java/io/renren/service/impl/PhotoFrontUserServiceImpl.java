package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoFrontUserDao;
import io.renren.entity.PhotoFrontUserEntity;
import io.renren.service.PhotoFrontUserService;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoFrontUserService")
public class PhotoFrontUserServiceImpl extends ServiceImpl<PhotoFrontUserDao, PhotoFrontUserEntity>  implements PhotoFrontUserService {
	@Autowired
	private PhotoFrontUserDao photoFrontUserDao;
	
	@Override
	public PhotoFrontUserEntity queryObject(Long id){
		return photoFrontUserDao.queryObject(id);
	}
	
	@Override
	public List<PhotoFrontUserEntity> queryList(Map<String, Object> map){
		return photoFrontUserDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoFrontUserDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoFrontUserEntity photoFrontUser){
		photoFrontUserDao.save(photoFrontUser);
	}
	
	@Override
	public void update(PhotoFrontUserEntity photoFrontUser){
		photoFrontUserDao.update(photoFrontUser);
	}
	
	@Override
	public void delete(Long id){
		photoFrontUserDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoFrontUserDao.deleteBatch(ids);
	}

	@Override
	public PhotoFrontUserEntity queryByAccount(String account) {
		// TODO Auto-generated method stub
		
		return photoFrontUserDao.queryUserByAccount(account);
	}

	@Override
	public PhotoFrontUserEntity queryClassUser(Long userId, Long classId) {
		// TODO Auto-generated method stub
		return photoFrontUserDao.queryClassUser(userId, classId);
	}

	@Override
	public List<PhotoFrontUserEntity> queryUsersByClassId(Long classId,
			Integer status) {
		// TODO Auto-generated method stub
		return photoFrontUserDao.queryUsersByClassId(classId, status);
	}
	
}
