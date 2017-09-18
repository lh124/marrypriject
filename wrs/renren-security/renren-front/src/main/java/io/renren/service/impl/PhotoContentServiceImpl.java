package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoContentDao;
import io.renren.entity.PhotoContentEntity;
import io.renren.service.PhotoContentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoContentService")
public class PhotoContentServiceImpl extends ServiceImpl<PhotoContentDao, PhotoContentEntity>  implements PhotoContentService {
	@Autowired
	private PhotoContentDao photoContentDao;
	
	@Override
	public PhotoContentEntity queryObject(Long id){
		return photoContentDao.queryObject(id);
	}
	
	@Override
	public List<PhotoContentEntity> queryList(Map<String, Object> map){
		return photoContentDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoContentDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoContentEntity photoContent){
		photoContentDao.save(photoContent);
	}
	
	@Override
	public void update(PhotoContentEntity photoContent){
		photoContentDao.update(photoContent);
	}
	
	@Override
	public void delete(Long id){
		photoContentDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoContentDao.deleteBatch(ids);
	}
	
}
