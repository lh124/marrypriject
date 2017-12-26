package io.renren.service.smart.impl;

import io.renren.dao.smart.ClassNoticeDao;
import io.renren.entity.smart.ClassNoticeEntity;
import io.renren.service.smart.ClassNoticeService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("classNoticeService")
public class ClassNoticeServiceImpl extends ServiceImpl<ClassNoticeDao, ClassNoticeEntity>  implements ClassNoticeService {
	@Autowired
	private ClassNoticeDao classNoticeDao;
	
	@Override
	public ClassNoticeEntity queryObject(Integer id){
		return classNoticeDao.queryObject(id);
	}
	
	@Override
	public List<ClassNoticeEntity> queryList(Map<String, Object> map){
		return classNoticeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return classNoticeDao.queryTotal(map);
	}
	
	@Override
	public void save(ClassNoticeEntity classNotice){
		classNoticeDao.save(classNotice);
	}
	
	@Override
	public void update(ClassNoticeEntity classNotice){
		classNoticeDao.update(classNotice);
	}
	
	@Override
	public void delete(Integer id){
		classNoticeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		classNoticeDao.deleteBatch(ids);
	}

	@Override
	public List<ClassNoticeEntity> queryListtongji(Map<String, Object> map) {
		return classNoticeDao.queryListtongji(map);
	}
	
}
