package io.renren.service.smart.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.dao.smart.PhotoClassWorkMsgDao;
import io.renren.entity.smart.PhotoClassWorkMsgEntity;
import io.renren.service.smart.PhotoClassWorkMsgService;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoClassWorkMsgService")
public class PhotoClassWorkMsgServiceImpl extends ServiceImpl<PhotoClassWorkMsgDao, PhotoClassWorkMsgEntity>  implements PhotoClassWorkMsgService {
	@Autowired
	private PhotoClassWorkMsgDao photoClassWorkMsgDao;
	
	@Override
	public PhotoClassWorkMsgEntity queryObject(Long id){
		return photoClassWorkMsgDao.queryObject(id);
	}
	
	@Override
	public List<PhotoClassWorkMsgEntity> queryList(Map<String, Object> map){
		return photoClassWorkMsgDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoClassWorkMsgDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoClassWorkMsgEntity photoClassWorkMsg){
		photoClassWorkMsgDao.save(photoClassWorkMsg);
	}
	
	@Override
	public void update(PhotoClassWorkMsgEntity photoClassWorkMsg){
		photoClassWorkMsgDao.update(photoClassWorkMsg);
	}
	
	@Override
	public void delete(Long id){
		photoClassWorkMsgDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoClassWorkMsgDao.deleteBatch(ids);
	}

	@Override
	public List<PhotoClassWorkMsgEntity> queryListtongji(Map<String, Object> map) {
		return photoClassWorkMsgDao.queryListtongji(map); 
	}
	
}
