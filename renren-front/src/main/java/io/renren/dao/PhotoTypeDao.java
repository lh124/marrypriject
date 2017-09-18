package io.renren.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import io.renren.entity.PhotoTypeEntity;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 相册分类表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-06 11:27:27
 */
public interface PhotoTypeDao extends BaseDao<PhotoTypeEntity>,BaseMapper<PhotoTypeEntity> {
	/**
	 * 查询班级照片分类，并获分类下的照片
	 * @param type 
	 * @param relatedId
	 * @param getMinType 是否只获取排序第一的那个分类的照片
	 * @return
	 */
	List<PhotoTypeEntity> queryPhotoTypes(@Param("type") int type,@Param("relatedId") long relatedId, @Param("getMinType") boolean getMinType);
	
	/**
	 * 查询用户相册分类，并获取分类中的一张照片，每个分类只获取一张，用于显示
	 * @param type
	 * @param relatedId
	 * @return
	 */
	List<PhotoTypeEntity> queryUserPhotoTypes(@Param("type") int type,@Param("relatedId") long relatedId);
	
	/**
	 * 查询班级相册分类，并获取分类中的一张照片，每个分类只获取一张，用于显示
	 * @param type
	 * @param relatedId
	 * @return
	 */
	List<PhotoTypeEntity> queryClassPhotoTypes(@Param("type") int type,@Param("relatedId") long relatedId);
}
