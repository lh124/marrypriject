package io.renren.entity.app;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-01-13 08:59:53
 */
 @TableName("smart_app")
public class SmartAppEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//1.0.1(100)
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//版本号
	@TableField(value="edition")
	private Integer edition;
			
	//类型（1安卓，2iOS）
	@TableField(value="equipment_type")
	private Integer equipmentType;
			
	//是否更新（0否，1是）
	@TableField(value="update_type")
	private Integer updateType;
			
	//下载路径
	@TableField(value="equipment_path")
	private String equipmentPath;
	
	//版本说明
	@TableField(value="remark")
	private String remark; 
	
	@TableField(value="package_size")
	private String packageSize;
			

	public String getPackageSize() {
		return packageSize;
	}
	public void setPackageSize(String packageSize) {
		this.packageSize = packageSize;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 设置：1.0.1(100)
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：1.0.1(100)
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：版本号
	 */
	public void setEdition(Integer edition) {
		this.edition = edition;
	}
	/**
	 * 获取：版本号
	 */
	public Integer getEdition() {
		return edition;
	}
	/**
	 * 设置：类型（1安卓，2iOS）
	 */
	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}
	/**
	 * 获取：类型（1安卓，2iOS）
	 */
	public Integer getEquipmentType() {
		return equipmentType;
	}
	/**
	 * 设置：是否更新（0否，1是）
	 */
	public void setUpdateType(Integer updateType) {
		this.updateType = updateType;
	}
	/**
	 * 获取：是否更新（0否，1是）
	 */
	public Integer getUpdateType() {
		return updateType;
	}
	/**
	 * 设置：下载路径
	 */
	public void setEquipmentPath(String equipmentPath) {
		this.equipmentPath = equipmentPath;
	}
	/**
	 * 获取：下载路径
	 */
	public String getEquipmentPath() {
		return equipmentPath;
	}
}
