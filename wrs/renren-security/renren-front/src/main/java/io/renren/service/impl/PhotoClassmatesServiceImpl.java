package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoClassmatesDao;
import io.renren.entity.PhotoClassmatesEntity;
import io.renren.service.PhotoClassmatesService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoClassmatesService")
public class PhotoClassmatesServiceImpl extends ServiceImpl<PhotoClassmatesDao, PhotoClassmatesEntity>  implements PhotoClassmatesService {
	@Autowired
	private PhotoClassmatesDao photoClassmatesDao;
	
	@Override
	public PhotoClassmatesEntity queryObject(Long id){
		return photoClassmatesDao.queryObject(id);
	}
	
	@Override
	public List<PhotoClassmatesEntity> queryList(Map<String, Object> map){
		return photoClassmatesDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoClassmatesDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoClassmatesEntity photoClassmates){
		photoClassmatesDao.save(photoClassmates);
	}
	
	@Override
	public void update(PhotoClassmatesEntity photoClassmates){
		photoClassmatesDao.update(photoClassmates);
	}
	
	@Override
	public void delete(Long id){
		photoClassmatesDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoClassmatesDao.deleteBatch(ids);
	}
	
}
