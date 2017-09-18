package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoMessageDao;
import io.renren.entity.PhotoMessageEntity;
import io.renren.service.PhotoMessageService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoMessageService")
public class PhotoMessageServiceImpl extends ServiceImpl<PhotoMessageDao, PhotoMessageEntity>  implements PhotoMessageService {
	@Autowired
	private PhotoMessageDao photoMessageDao;
	
	@Override
	public PhotoMessageEntity queryObject(Long id){
		return photoMessageDao.queryObject(id);
	}
	
	@Override
	public List<PhotoMessageEntity> queryList(Map<String, Object> map){
		return photoMessageDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoMessageDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoMessageEntity photoMessage){
		photoMessageDao.save(photoMessage);
	}
	
	@Override
	public void update(PhotoMessageEntity photoMessage){
		photoMessageDao.update(photoMessage);
	}
	
	@Override
	public void delete(Long id){
		photoMessageDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoMessageDao.deleteBatch(ids);
	}
	
}
