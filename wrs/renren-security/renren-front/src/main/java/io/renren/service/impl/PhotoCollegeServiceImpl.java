package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoCollegeDao;
import io.renren.entity.PhotoCollegeEntity;
import io.renren.service.PhotoCollegeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoCollegeService")
public class PhotoCollegeServiceImpl extends ServiceImpl<PhotoCollegeDao, PhotoCollegeEntity>  implements PhotoCollegeService {
	@Autowired
	private PhotoCollegeDao photoCollegeDao;
	
	@Override
	public PhotoCollegeEntity queryObject(Long id){
		return photoCollegeDao.queryObject(id);
	}
	
	@Override
	public List<PhotoCollegeEntity> queryList(Map<String, Object> map){
		return photoCollegeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoCollegeDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoCollegeEntity photoCollege){
		photoCollegeDao.save(photoCollege);
	}
	
	@Override
	public void update(PhotoCollegeEntity photoCollege){
		photoCollegeDao.update(photoCollege);
	}
	
	@Override
	public void delete(Long id){
		photoCollegeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		photoCollegeDao.deleteBatch(ids);
	}
	
}
