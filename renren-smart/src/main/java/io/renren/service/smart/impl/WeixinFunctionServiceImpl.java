package io.renren.service.smart.impl;

import io.renren.dao.smart.WeixinFunctionDao;
import io.renren.entity.smart.WeixinFunctionEntity;
import io.renren.service.smart.WeixinFunctionService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("weixinFunctionService")
public class WeixinFunctionServiceImpl extends ServiceImpl<WeixinFunctionDao, WeixinFunctionEntity>  implements WeixinFunctionService {
	@Autowired
	private WeixinFunctionDao weixinFunctionDao;
	
	@Override
	public WeixinFunctionEntity queryObject(Integer id){
		return weixinFunctionDao.queryObject(id);
	}
	
	@Override
	public List<WeixinFunctionEntity> queryList(Map<String, Object> map){
		return weixinFunctionDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return weixinFunctionDao.queryTotal(map);
	}
	
	@Override
	public void save(WeixinFunctionEntity weixinFunction){
		weixinFunctionDao.save(weixinFunction);
	}
	
	@Override
	public void update(WeixinFunctionEntity weixinFunction){
		weixinFunctionDao.update(weixinFunction);
	}
	
	@Override
	public void delete(Integer id){
		weixinFunctionDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		weixinFunctionDao.deleteBatch(ids);
	}
	
}
