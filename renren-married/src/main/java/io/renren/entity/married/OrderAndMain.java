package io.renren.entity.married;

import java.util.List;
import java.util.Map;

public class OrderAndMain {
	
	private Integer userId;
	
	private String userName;
	
	
	private List<MarryMainEntity> mainList;
	



	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}




	public List<MarryMainEntity> getMainList() {
		return mainList;
	}


	public void setMainList(List<MarryMainEntity> mainList1) {
		this.mainList = mainList1;
	}


	@Override
	public String toString() {
		return "OrderAndMain [userId=" + userId + ", userName=" + userName
				+ ", mainList=" + mainList + "]";
	}

}
