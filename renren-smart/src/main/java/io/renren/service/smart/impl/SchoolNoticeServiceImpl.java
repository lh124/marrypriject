package io.renren.service.smart.impl;

import io.renren.dao.smart.SchoolNoticeDao;
import io.renren.entity.smart.SchoolNoticeEntity;
import io.renren.service.smart.SchoolNoticeService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("schoolNoticeService")
public class SchoolNoticeServiceImpl extends ServiceImpl<SchoolNoticeDao, SchoolNoticeEntity>  implements SchoolNoticeService {
	@Autowired
	private SchoolNoticeDao schoolNoticeDao;
	
	@Override
	public SchoolNoticeEntity queryObject(Integer id){
		return schoolNoticeDao.queryObject(id);
	}
	
	@Override
	public List<SchoolNoticeEntity> queryList(Map<String, Object> map){
		return schoolNoticeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return schoolNoticeDao.queryTotal(map);
	}
	
	@Override
	public void save(SchoolNoticeEntity schoolNotice){
		schoolNoticeDao.save(schoolNotice);
	}
	
	@Override
	public void update(SchoolNoticeEntity schoolNotice){
		schoolNoticeDao.update(schoolNotice);
	}
	
	@Override
	public void delete(Integer id){
		schoolNoticeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		schoolNoticeDao.deleteBatch(ids);
	}
	
}
