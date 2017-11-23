package io.renren.service.smart.impl;

import io.renren.dao.smart.WeixinFunctionImgDao;
import io.renren.entity.smart.WeixinFunctionImgEntity;
import io.renren.service.smart.WeixinFunctionImgService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("weixinFunctionImgService")
public class WeixinFunctionImgServiceImpl extends ServiceImpl<WeixinFunctionImgDao, WeixinFunctionImgEntity>  implements WeixinFunctionImgService {
	@Autowired
	private WeixinFunctionImgDao weixinFunctionImgDao;
	
	@Override
	public WeixinFunctionImgEntity queryObject(Integer id){
		return weixinFunctionImgDao.queryObject(id);
	}
	
	@Override
	public List<WeixinFunctionImgEntity> queryList(Map<String, Object> map){
		return weixinFunctionImgDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return weixinFunctionImgDao.queryTotal(map);
	}
	
	@Override
	public void save(WeixinFunctionImgEntity weixinFunctionImg){
		weixinFunctionImgDao.save(weixinFunctionImg);
	}
	
	@Override
	public void update(WeixinFunctionImgEntity weixinFunctionImg){
		weixinFunctionImgDao.update(weixinFunctionImg);
	}
	
	@Override
	public void delete(Integer id){
		weixinFunctionImgDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		weixinFunctionImgDao.deleteBatch(ids);
	}
	
}
