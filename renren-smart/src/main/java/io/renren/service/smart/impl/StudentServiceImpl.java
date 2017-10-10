package io.renren.service.smart.impl;

import io.renren.dao.smart.StudentDao;
import io.renren.entity.smart.StudentEntity;
import io.renren.service.smart.StudentService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("studentService")
public class StudentServiceImpl extends ServiceImpl<StudentDao, StudentEntity>  implements StudentService {
	@Autowired
	private StudentDao studentDao;
	
	@Override
	public StudentEntity queryObject(Integer id){
		return studentDao.queryObject(id);
	}
	
	@Override
	public List<StudentEntity> queryList(Map<String, Object> map){
		return studentDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return studentDao.queryTotal(map);
	}
	
	@Override
	public void save(StudentEntity student){
		studentDao.save(student);
	}
	
	@Override
	public void update(StudentEntity student){
		studentDao.update(student);
	}
	
	@Override
	public void delete(Integer id){
		studentDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		studentDao.deleteBatch(ids);
	}

	@Override
	public void updateOpenId(Long id) {
		studentDao.updateOpenId(id);
	}

	@Override
	public List<StudentEntity> queryListtongji(Map<String, Object> map) {
		return studentDao.queryListtongji(map);
	}
	
}
