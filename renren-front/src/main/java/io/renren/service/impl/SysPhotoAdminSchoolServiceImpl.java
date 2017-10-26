package io.renren.service.impl;

import io.renren.dao.SysPhotoAdminSchoolDao;
import io.renren.entity.SysPhotoAdminSchoolEntity;
import io.renren.service.SysPhotoAdminSchoolService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("sysPhotoAdminSchoolService")
public class SysPhotoAdminSchoolServiceImpl extends ServiceImpl<SysPhotoAdminSchoolDao, SysPhotoAdminSchoolEntity>  implements SysPhotoAdminSchoolService {
	@Autowired
	private SysPhotoAdminSchoolDao sysPhotoAdminSchoolDao;
	
	@Override
	public SysPhotoAdminSchoolEntity queryObject(Integer id){
		return sysPhotoAdminSchoolDao.queryObject(id);
	}
	
	@Override
	public List<SysPhotoAdminSchoolEntity> queryList(Map<String, Object> map){
		return sysPhotoAdminSchoolDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysPhotoAdminSchoolDao.queryTotal(map);
	}
	
	@Override
	public void save(SysPhotoAdminSchoolEntity sysPhotoAdminSchool){
		sysPhotoAdminSchoolDao.save(sysPhotoAdminSchool);
	}
	
	@Override
	public void update(SysPhotoAdminSchoolEntity sysPhotoAdminSchool){
		sysPhotoAdminSchoolDao.update(sysPhotoAdminSchool);
	}
	
	@Override
	public void delete(Integer id){
		sysPhotoAdminSchoolDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		sysPhotoAdminSchoolDao.deleteBatch(ids);
	}
	
}
