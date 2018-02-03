package io.renren.chart;

public class CloudData {
    public static final String accountImport = "im_open_login_svc/account_import";//新建用户(独立模式账号导入接口)
    public static final String openimSendmsg = "openim/sendmsg";//管理员向账号发消息(单发单聊消息)
    public static final String openimIm_push = "openim/im_push";//消息推送
    public static final String getPortrait = "profile/portrait_get";//拉取用户资料
    public static final String setPortrait = "profile/portrait_set";//设置用户资料
    public static final String addFriend = "sns/friend_add";//增加好友
    public static final String deleteFriend = "sns/friend_delete";//删除好友
    public static final String checkFriend = "sns/friend_check";//检验好友
    public static final String getFriend = "sns/friend_get_list";//获取指定单个好友信息
    public static final String getAllFriend = "sns/friend_get_all";//获取所有好友列表
    
    
    public static final String createGRoup = "group_open_http_svc/create_group";//创建群
    public static final String addGroupInfo = "group_open_http_svc/add_group_member";//加群
    public static final String getGroupInfo = "group_open_http_svc/get_group_info";//查询群详细资料
}
