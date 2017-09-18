package io.renren.entity.smart;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-08-28 11:52:18
 */
 @TableName("io")
public class IoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//
	@TableField(value="epc")
	private String epc;
			
	//
	@TableField(value="io_type")
	private String ioType;
			
	//
	@TableField(value="io_date")
	private Date ioDate;
			
	//
	@TableField(value="rfid_id")
	private String rfidId;
			

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setEpc(String epc) {
		this.epc = epc;
	}
	/**
	 * 获取：
	 */
	public String getEpc() {
		return epc;
	}
	/**
	 * 设置：
	 */
	public void setIoType(String ioType) {
		this.ioType = ioType;
	}
	/**
	 * 获取：
	 */
	public String getIoType() {
		return ioType;
	}
	/**
	 * 设置：
	 */
	public void setIoDate(Date ioDate) {
		this.ioDate = ioDate;
	}
	/**
	 * 获取：
	 */
	public Date getIoDate() {
		return ioDate;
	}
	/**
	 * 设置：
	 */
	public void setRfidId(String rfidId) {
		this.rfidId = rfidId;
	}
	/**
	 * 获取：
	 */
	public String getRfidId() {
		return rfidId;
	}
}
