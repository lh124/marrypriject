package io.renren.chart;

public class FriendInfo {
    private String fuid;//好友uid
    private String nickname;//昵称
    private String remark;//备注
    private String image;//头像
     
    public String getFuid() {
        return fuid;
    }
    public void setFuid(String fuid) {
        this.fuid = fuid;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }   
}
