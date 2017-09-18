package io.renren.service;

import io.renren.entity.PhotoTypeEntity;

import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 相册分类表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
public interface PhotoTypeService extends IService<PhotoTypeEntity>{
	
	PhotoTypeEntity queryObject(Long id);
	
	List<PhotoTypeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoTypeEntity photoType);
	
	void update(PhotoTypeEntity photoType);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	List<PhotoTypeEntity> queryPhotoTypes(int type ,long relatedId ,boolean getMinType);
	
	/**
	 * 查询用户相册分类，并获取分类中的一张照片，每个分类只获取一张，用于显示
	 * @param type
	 * @param relatedId
	 * @return
	 */
	List<PhotoTypeEntity> queryUserPhotoTypes(@Param("type") int type,@Param("relatedId") long relatedId);
	
	List<PhotoTypeEntity> queryClassPhotoTypes(@Param("type") int type,@Param("relatedId") long relatedId);
}
