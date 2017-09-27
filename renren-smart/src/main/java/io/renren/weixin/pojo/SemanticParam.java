package io.renren.weixin.pojo;

/**
 * �������
 * 
 * @author liufeng
 * @date 2014-11-18
 */
public class SemanticParam {
	// �ı���
	private String query;
	// �������
	private String city;
	// �������
	private String region;
	// γ��
	private String latitude;
	// ����
	private String longitude;
	// ���ͣ����֮���ö��ŷָ�
	private String category;
	// ���ں�Ψһ��ʶ
	private String appid;
	// �û�Ψһ��ʶ������ʹ��openid
	private String uid;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
