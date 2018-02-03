package io.renren.chart;

public class UserInfo {
    private String nickname = "";//昵称
    private String image = "";//头像
    private Byte sex = null;//性别
    private String name = "";//昵称
     
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Byte getSex() {
        return sex;
    }
    public void setSex(Byte sex) {
        this.sex = sex;
    }
    public UserInfo(String nickname, String image, Byte sex) {
        super();
        this.nickname = nickname;
        this.image = image;
        this.sex = sex;
    }
    public UserInfo() {
        super();
    }
}

