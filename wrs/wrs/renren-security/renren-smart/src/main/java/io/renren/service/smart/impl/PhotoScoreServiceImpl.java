package io.renren.service.smart.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.dao.smart.PhotoScoreDao;
import io.renren.entity.smart.PhotoScoreEntity;
import io.renren.service.smart.PhotoScoreService;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoScoreService")
public class PhotoScoreServiceImpl extends ServiceImpl<PhotoScoreDao, PhotoScoreEntity>  implements PhotoScoreService {
	@Autowired
	private PhotoScoreDao photoScoreDao;
	
	@Override
	public PhotoScoreEntity queryObject(Long id){
		return photoScoreDao.queryObject(id);
	}
	
	@Override
	public List<PhotoScoreEntity> queryList(Map<String, Object> map){
		return photoScoreDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoScoreDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoScoreEntity photoScore){
		photoScoreDao.save(photoScore);
	}
	
	@Override
	public void update(PhotoScoreEntity photoScore){
		photoScoreDao.update(photoScore);
	}
	
	@Override
	public void delete(Long id){
		photoScoreDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoScoreDao.deleteBatch(ids);
	}
	
}
