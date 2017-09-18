package io.renren.service.smart.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.smart.StudentEpcDao;
import io.renren.entity.smart.StudentEpcEntity;
import io.renren.service.smart.StudentEpcService;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("studentEpcService")
public class StudentEpcServiceImpl extends ServiceImpl<StudentEpcDao, StudentEpcEntity>  implements StudentEpcService {
	@Autowired
	private StudentEpcDao studentEpcDao;
	
	@Override
	public StudentEpcEntity queryObject(Integer id){
		return studentEpcDao.queryObject(id);
	}
	
	@Override
	public List<StudentEpcEntity> queryList(Map<String, Object> map){
		return studentEpcDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return studentEpcDao.queryTotal(map);
	}
	
	@Override
	public void save(StudentEpcEntity studentEpc){
		studentEpcDao.save(studentEpc);
	}
	
	@Override
	public void update(StudentEpcEntity studentEpc){
		studentEpcDao.update(studentEpc);
	}
	
	@Override
	public void delete(Integer id){
		studentEpcDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		studentEpcDao.deleteBatch(ids);
	}
	
}
