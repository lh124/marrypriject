package io.renren.service.married.impl;

import io.renren.dao.married.MarryImgDao;
import io.renren.entity.married.MarryImgEntity;
import io.renren.service.married.MarryImgService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("marryImgService")
public class MarryImgServiceImpl extends ServiceImpl<MarryImgDao, MarryImgEntity>  implements MarryImgService {
	@Autowired
	private MarryImgDao marryImgDao;
	
	@Override
	public MarryImgEntity queryObject(Integer id){
		return marryImgDao.queryObject(id);
	}
	
	@Override
	public List<MarryImgEntity> queryList(Map<String, Object> map){
		return marryImgDao.queryList(map);
	}
	
	@Override
	public List<MarryImgEntity> queryList1(Map<String, Object> map){
		return marryImgDao.queryList1(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return marryImgDao.queryTotal(map);
	}
	
	@Override
	public void save(MarryImgEntity marryImg){
		marryImgDao.save(marryImg);
	}
	
	@Override
	public void update(MarryImgEntity marryImg){
		marryImgDao.update(marryImg);
	}
	
	@Override
	public void delete(Integer id){
		marryImgDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		marryImgDao.deleteBatch(ids);
	}
	
}
