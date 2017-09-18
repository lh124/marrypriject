package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoUserFunctionDao;
import io.renren.entity.PhotoUserFunctionEntity;
import io.renren.service.PhotoUserFunctionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoUserFunctionService")
public class PhotoUserFunctionServiceImpl extends ServiceImpl<PhotoUserFunctionDao, PhotoUserFunctionEntity>  implements PhotoUserFunctionService {
	@Autowired
	private PhotoUserFunctionDao photoUserFunctionDao;
	
	@Override
	public PhotoUserFunctionEntity queryObject(Long id){
		return photoUserFunctionDao.queryObject(id);
	}
	
	@Override
	public List<PhotoUserFunctionEntity> queryList(Map<String, Object> map){
		return photoUserFunctionDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoUserFunctionDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoUserFunctionEntity photoUserFunction){
		photoUserFunctionDao.save(photoUserFunction);
	}
	
	@Override
	public void update(PhotoUserFunctionEntity photoUserFunction){
		photoUserFunctionDao.update(photoUserFunction);
	}
	
	@Override
	public void delete(Long id){
		photoUserFunctionDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoUserFunctionDao.deleteBatch(ids);
	}
	
}
