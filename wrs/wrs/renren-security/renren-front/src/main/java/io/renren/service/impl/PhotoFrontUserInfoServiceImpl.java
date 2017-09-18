package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoFrontUserInfoDao;
import io.renren.entity.PhotoFrontUserInfoEntity;
import io.renren.service.PhotoFrontUserInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoFrontUserInfoService")
public class PhotoFrontUserInfoServiceImpl extends ServiceImpl<PhotoFrontUserInfoDao, PhotoFrontUserInfoEntity>  implements PhotoFrontUserInfoService {
	@Autowired
	private PhotoFrontUserInfoDao photoFrontUserInfoDao;
	
	@Override
	public PhotoFrontUserInfoEntity queryObject(Long id){
		return photoFrontUserInfoDao.queryObject(id);
	}
	
	@Override
	public List<PhotoFrontUserInfoEntity> queryList(Map<String, Object> map){
		return photoFrontUserInfoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoFrontUserInfoDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoFrontUserInfoEntity photoFrontUserInfo){
		photoFrontUserInfoDao.save(photoFrontUserInfo);
	}
	
	@Override
	public void update(PhotoFrontUserInfoEntity photoFrontUserInfo){
		photoFrontUserInfoDao.update(photoFrontUserInfo);
	}
	
	@Override
	public void delete(Long id){
		photoFrontUserInfoDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoFrontUserInfoDao.deleteBatch(ids);
	}
	
}
