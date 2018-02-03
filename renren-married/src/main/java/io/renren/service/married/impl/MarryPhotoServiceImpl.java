package io.renren.service.married.impl;

import io.renren.dao.married.MarryPhotoDao;
import io.renren.entity.married.MarryPhotoEntity;
import io.renren.service.married.MarryPhotoService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("marryPhotoService")
public class MarryPhotoServiceImpl extends ServiceImpl<MarryPhotoDao, MarryPhotoEntity>  implements MarryPhotoService {
	@Autowired
	private MarryPhotoDao marryPhotoDao;
	
	@Override
	public MarryPhotoEntity queryObject(Integer id){
		return marryPhotoDao.queryObject(id);
	}
	
	@Override
	public List<MarryPhotoEntity> queryList(Map<String, Object> map){
		return marryPhotoDao.queryList(map);
	}
	
	@Override
	public List<MarryPhotoEntity> queryList1(Map<String, Object> map){
		return marryPhotoDao.queryList1(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return marryPhotoDao.queryTotal(map);
	}
	
	@Override
	public void save(MarryPhotoEntity marryPhoto){
		marryPhotoDao.save(marryPhoto);
	}
	
	@Override
	public void update(MarryPhotoEntity marryPhoto){
		marryPhotoDao.update(marryPhoto);
	}
	
	@Override
	public void delete(Integer id){
		marryPhotoDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		marryPhotoDao.deleteBatch(ids);
	}
	
}
