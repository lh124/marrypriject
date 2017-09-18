package io.renren.service.smart.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.dao.smart.PhotoPicWorkMsgDao;
import io.renren.entity.smart.PhotoPicWorkMsgEntity;
import io.renren.service.smart.PhotoPicWorkMsgService;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoPicWorkMsgService")
public class PhotoPicWorkMsgServiceImpl extends ServiceImpl<PhotoPicWorkMsgDao, PhotoPicWorkMsgEntity>  implements PhotoPicWorkMsgService {
	@Autowired
	private PhotoPicWorkMsgDao photoPicWorkMsgDao;
	
	@Override
	public PhotoPicWorkMsgEntity queryObject(Long id){
		return photoPicWorkMsgDao.queryObject(id);
	}
	
	@Override
	public List<PhotoPicWorkMsgEntity> queryList(Map<String, Object> map){
		return photoPicWorkMsgDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoPicWorkMsgDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoPicWorkMsgEntity photoPicWorkMsg){
		photoPicWorkMsgDao.save(photoPicWorkMsg);
	}
	
	@Override
	public void update(PhotoPicWorkMsgEntity photoPicWorkMsg){
		photoPicWorkMsgDao.update(photoPicWorkMsg);
	}
	
	@Override
	public void delete(Long id){
		photoPicWorkMsgDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoPicWorkMsgDao.deleteBatch(ids);
	}
	
}
