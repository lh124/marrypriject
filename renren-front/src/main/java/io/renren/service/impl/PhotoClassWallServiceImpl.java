package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoClassWallDao;
import io.renren.entity.PhotoClassWallEntity;
import io.renren.service.PhotoClassWallService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoClassWallService")
public class PhotoClassWallServiceImpl extends ServiceImpl<PhotoClassWallDao, PhotoClassWallEntity>  implements PhotoClassWallService {
	@Autowired
	private PhotoClassWallDao photoClassWallDao;
	
	@Override
	public PhotoClassWallEntity queryObject(Long id){
		return photoClassWallDao.queryObject(id);
	}
	
	@Override
	public List<PhotoClassWallEntity> queryList(Map<String, Object> map){
		return photoClassWallDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoClassWallDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoClassWallEntity photoClassWall){
		photoClassWallDao.save(photoClassWall);
	}
	
	@Override
	public void update(PhotoClassWallEntity photoClassWall){
		photoClassWallDao.update(photoClassWall);
	}
	
	@Override
	public void delete(Long id){
		photoClassWallDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoClassWallDao.deleteBatch(ids);
	}
	
}
