package com.game.jxj.model.http.api;


import com.game.jxj.model.entity.ActivityInfoResp;
import com.game.jxj.model.entity.AlipayWithdrawalResp;
import com.game.jxj.model.entity.ArtUserReq;
import com.game.jxj.model.entity.BannerListResp;
import com.game.jxj.model.entity.DeleteOneNotifyReq;
import com.game.jxj.model.entity.EmptyDateResp;
import com.game.jxj.model.entity.EvaluatelistResp;
import com.game.jxj.model.entity.FollowListResp;
import com.game.jxj.model.entity.FollowVideoListResp;
import com.game.jxj.model.entity.HttpResp;
import com.game.jxj.model.entity.LoginResp;
import com.game.jxj.model.entity.MusicListResp;
import com.game.jxj.model.entity.RegisterResp;
import com.game.jxj.model.entity.SendCodeResp;
import com.game.jxj.model.entity.UserVideoInfoResp;
import com.game.jxj.model.entity.VideoListResp;
import com.game.jxj.model.entity.VideoResp;
import com.game.jxj.model.entity.WalletDetailedListResp;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


/**
 * Created by codeest on 16/8/19.
 */

public interface Apis {

    // String HOST = "http://192.168.31.238:8080/";
//    String HOST = "http://39.104.66.128:8080/wo-1.0-SNAPSHOT/";
    String HOST = "http://39.104.66.128:8080/wo-1.0-SNAPSHOT/";


    //============= 注册登录设置密码 start======================


    /**
     * 发送验证码
     *
     * @param userName 手机号
     * @param type     1注册
     * @return
     */
    @FormUrlEncoded
    @POST("oauthToken/getVerificationCode")
    Flowable<HttpResp<List<SendCodeResp>>> getPhoneCode(@Field("userName") String userName,
                                                        @Field("type") String type);


    /**
     * 注册
     *
     * @param userName         账号
     * @param password         密码
     * @param verificationCode 短信验证码
     * @param refereeUser      邀请码
     * @return
     */
    @FormUrlEncoded
    @POST("oauthToken/register")
    Flowable<HttpResp<RegisterResp>> register(@Field("userName") String userName,
                                              @Field("password") String password,
                                              @Field("verificationCode") String verificationCode,
                                              @Field("refereeUser") String refereeUser);


    /**
     * 登录
     *
     * @param userName 手机号
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("oauthToken/getToken")
    Flowable<HttpResp<LoginResp>> login(@Field("userName") String userName,
                                        @Field("password") String password);


    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    @FormUrlEncoded
    @POST("api/sys/user/updatePassword")
    Flowable<HttpResp<String>> updatePassword(@Field("oldPassword") String oldPassword,
                                              @Field("newPassword") String newPassword);


    //===================图片文件上传=====================

    /**
     * 图片上传
     *
     * @param file     上传的图片文件
     * @param fileType 文件类型
     * @return
     */

    @Multipart
    @POST("api/common/saveFile")
    Flowable<HttpResp<String>> saveFile(@Part MultipartBody.Part file,
                                        @Query("fileType") String fileType);


    //===================个人信息======================


    /**
     * 背景音乐列表
     *
     * @param data
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/music/getMusicList")
    Flowable<HttpResp<List<MusicListResp>>> getMusicList(@Body RequestBody data);


    /**
     * 我收藏音乐列表
     *
     * @param data
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/music/getMyMusicList")
    Flowable<HttpResp<List<MusicListResp>>> getMyMusicList(@Body RequestBody data);


    /**
     * 收藏音乐
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/art/music/saveMusicCollection")
    Flowable<HttpResp<EmptyDateResp>> saveMusicCollection(@Field("musicId") String musicId);


    /**
     * 取消收藏音乐
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/art/music/deleteMusicCollection")
    Flowable<HttpResp<EmptyDateResp>> deleteMusicCollection(@Field("musicId") String musicId);


    /**
     * 登录
     *
     * @param openId   openId
     * @param nickName 昵称
     * @param sex      性别 1男 2女
     * @param filePath 头像
     * @return
     */
    @FormUrlEncoded
    @POST("oauthToken/getToken")
    Flowable<HttpResp<LoginResp>> getToken(@Field("openId") String openId,
                                           @Field("nickName") String nickName,
                                           @Field("sex") int sex,
                                           @Field("filePath") String filePath);

    /**
     * 关注粉丝列表
     *
     * @param data
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/userFollow/getFollowList")
    Flowable<HttpResp<List<FollowListResp>>> getFollowList(@Body RequestBody data);

    /**
     * 用户提现
     *
     * @param data
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/userWallet/alipayWithdrawal")
    Flowable<HttpResp<AlipayWithdrawalResp>> getAlipayWithdrawal(@Body RequestBody data);


    /**
     * userVideo : 用户视频操作
     */


    /**
     * 删除视频评论点赞
     *
     * @param evaluateId 视频评论ID
     * @return
     */
    @FormUrlEncoded
    @POST("api/art/userVideo/deleteEvaluateFavor")
    Flowable<HttpResp<String>> deleteEvaluateFavor(@Field("evaluateId") String evaluateId);

    /**
     * 逻辑删除
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/userVideo/deleteVideo")
    Flowable<HttpResp<String>> deleteVideo(@Body RequestBody data);

    /**
     * 我关注人的视频
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/userVideo/getFollowVideoList")
    Flowable<HttpResp<List<FollowVideoListResp>>> getFollowVideoList(@Body RequestBody data);

    /**
     * 获取推荐视频
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/art/userVideo/getRecommend")
    Flowable<HttpResp<List<VideoResp>>> getRecommend();

    /**
     * 获取我的视频或我关注的视频
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/userVideo/getUserVideoInfo")
    Flowable<HttpResp<UserVideoInfoResp>> getUserVideoInfo(@Body RequestBody body);

    /**
     * 根据音乐ID获取关联视频视频
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/userVideo/getVideoByMusicId")
    Flowable<HttpResp<List<VideoResp>>> getVideoByMusicId(@Body RequestBody body);

    /**
     * 获取视频详情
     *
     * @param videoId
     * @return
     */
    @GET("api/art/userVideo/getVideoInfo")
    Flowable<HttpResp<List<VideoResp>>> getVideoInfo(@Query("videoId") String videoId);


    /**
     * 获取视频列(半自动筛选)
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/userVideo/getVideoList")
    Flowable<HttpResp<VideoListResp>> getVideoList(@Body RequestBody body);

    /**
     * 创建视频评论
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/userVideo/saveArtEvaluate")
    Flowable<HttpResp<String>> saveArtEvaluate(@Body RequestBody body);

    /**
     * 创建视频评论点赞
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/userVideo/saveEvaluateFavor")
    Flowable<HttpResp<String>> saveEvaluateFavor(@Body RequestBody body);

    /**
     * 保存视频
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/userVideo/saveUserVideo")
    Flowable<HttpResp<String>> saveUserVideo(@Body RequestBody body);


    /**
     * activity : 活动相关操作
     */

    /**
     * 根据活动ID获取活动详情
     *
     * @param activityId 活动ID
     * @return
     */
    @FormUrlEncoded
    @POST("api/art/activity/getActivityInfo")
    Flowable<HttpResp<ActivityInfoResp>> getActivityInfo(@Field("activityId") String activityId);

    /**
     * 根据商家ID获取活动列表
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/activity/getActivityListByUserId")
    Flowable<HttpResp<List<ActivityInfoResp>>> getActivityListByUserId(@Body RequestBody body);

    /**
     * 活动支付
     *
     * @param activityId 活动ID
     * @return
     */
    @FormUrlEncoded
    @POST("api/art/activity/payActivity")
    Flowable<HttpResp<String>> payActivity(@Field("activityId") String activityId);

    /**
     * 创建活动
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/activity/saveActivity")
    Flowable<HttpResp<ActivityInfoResp>> saveActivity(@Body RequestBody body);


    /**
     * feedBack : 用户反馈给后台的信息 合作 举报 意见 企业认证
     */

    /**
     * 保存企业认证
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/feedBack/saveCertifiedEnterprise")
    Flowable<HttpResp<String>> saveCertifiedEnterprise(@Body RequestBody body);


    /**
     * 与我们合作
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/feedBack/saveCooperation")
    Flowable<HttpResp<String>> saveCooperation(@Body RequestBody body);

    /**
     * 意见反馈
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/feedBack/saveOpinion")
    Flowable<HttpResp<String>> saveOpinion(@Body RequestBody body);


    /**
     * 意见反馈
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/feedBack/saveReport")
    Flowable<HttpResp<String>> saveReport(@Body RequestBody body);


    /**
     * homePage : APP不需要登录注册接口
     */

    /**
     * 活动首页
     *
     * @return
     */

    @GET("homePage/getActivityList")
    Flowable<HttpResp<List<ActivityInfoResp>>> getActivityList();

    /**
     * 轮播图
     *
     * @return
     */

    @GET("homePage/getBannerList")
    Flowable<HttpResp<List<BannerListResp>>> getBannerList();

    /**
     * 已结束活动
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("homePage/getEndActivityList")
    Flowable<HttpResp<List<ActivityInfoResp>>> getEndActivityList(@Body RequestBody body);

    /**
     * 查询视频评论列表
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("homePage/getEvaluateList")
    Flowable<HttpResp<List<EvaluatelistResp>>> getEvaluateList(@Body RequestBody body);


    /**
     * 获取视频首页列表
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("homePage/getHomePageList")
    Flowable<HttpResp<List<VideoResp>>> getHomePageList(@Body RequestBody body);

    /**
     * 版本链接接口
     *
     * @return
     */
    @GET("homePage/getVersion")
    Flowable<HttpResp<String>> getVersion();

    /**
     * 播放视频记录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("homePage/saveVideoWatch")
    Flowable<HttpResp<String>> saveVideoWatch(@Field("createPerson") String createPerson);


    /***
     * linkPath : 链接点击操作
     */

    /**
     * 获取视频首页列表
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/api/art/linkPath/saveLinkPath")
    Flowable<HttpResp<String>> saveLinkPath(@Body RequestBody body);


    /**
     * music : 用户音乐操作
     */

//    /**
//     * 取消收藏音乐
//     * @param musicId
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("api/art/music/deleteMusicCollection")
//    Flowable<HttpResp<String>> deleteMusicCollection(@Field("musicId") String musicId);
//
//    /**
//     * 我收藏的音乐
//     * @param body
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("api/art/music/getMyMusicList")
//    Flowable<HttpResp<List<MusicListResp>>> getMyMusicList(@Body RequestBody body);
//
//    /**
//     * 收藏音乐
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("api/art/music/saveMusicCollection")
//    Flowable<HttpResp<String>> saveMusicCollection(@Field("musicId") String musicId);


    /**
     * notify : 系统通知相关操作
     */


    /**
     * 用户清空通知
     *
     * @return
     */
    @GET("api/sys/notify/deleteAllNotify")
    Flowable<HttpResp<String>> deleteAllNotify();

    /**
     * 单个删除通知
     *
     * @param notifyId
     * @return
     */
    @GET("api/sys/notify/deleteOneNotify")
    Flowable<HttpResp<String>> deleteOneNotify(@Query("notifyId") String notifyId);

    /**
     * 获取通知列表
     */
    @GET("api/sys/notify/getNotifyList")
    Flowable<HttpResp<List<DeleteOneNotifyReq>>> deleteOneNotify();


    /**
     * 单个修改读取状态
     *
     * @param notifyId
     * @return
     */
    @GET("api/sys/notify/saveNotify")
    Flowable<HttpResp<String>> saveNotify(@Query("notifyId") String notifyId);

    /**
     * 修改当前用户全部已读
     *
     * @return
     */
    @GET("api/sys/notify/saveReadStatus")
    Flowable<HttpResp<String>> saveReadStatus();


    /**
     * user : 用户相关操作
     */

    /**
     * 显示个人信息
     *
     * @param userId 个人ID
     * @return
     */
    @FormUrlEncoded
    @POST("api/art/user/getArtUser")
    Flowable<HttpResp<ArtUserReq>> getArtUser(@Field("userId") String userId);


    /**
     * 查询有效用户
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/user/getArtUserList")
    Flowable<HttpResp<String>> getArtUserList(@Body RequestBody body);

    /**
     * 根据当前用户修改信息(包含芝麻认证)
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/user/updateArtUser")
    Flowable<HttpResp<String>> updateArtUser(@Body RequestBody body);


    /**
     * 修改角色
     *
     * @param roleType 0个人 1企业
     * @return
     */
    @FormUrlEncoded
    @POST("api/art/user/updateRole")
    Flowable<HttpResp<String>> updateRole(@Field("roleType") Integer roleType);


    /**
     * userFavor : 点赞相关操作
     */

    /**
     * 取消点赞
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/userFavor/deleteFavor")
    Flowable<HttpResp<String>> deleteFavor(@Body RequestBody body);

    /**
     * 保存点赞
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/userFavor/saveFavor")
    Flowable<HttpResp<String>> saveFavor(@Body RequestBody body);

    /**
     * 保存转发
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/userForward/saveForward")
    Flowable<HttpResp<String>> saveForward(@Body RequestBody body);

    /**
     * 取消关注
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/userFollow/deleteUserFollow")
    Flowable<HttpResp<String>> deleteUserFollow(@Body RequestBody body);

    /**
     * 保存关注记录
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/userFollow/saveUserFollow")
    Flowable<HttpResp<String>> saveUserFollow(@Body RequestBody body);


    /**
     * 用户支付宝支付
     *
     * @param money 金额
     * @return
     */
    @FormUrlEncoded
    @POST("api/art/userWallet/alipayRecharge")
    Flowable<HttpResp<String>> alipayRecharge(@Field("money") Integer money);

    /**
     * 查看个人资金账户
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/art/userWallet/getUserWallet")
    Flowable<HttpResp<String>> getUserWallet();

    /**
     * 查看个人资金明细
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/userWallet/getWalletDetailedList")
    Flowable<HttpResp<WalletDetailedListResp>> getWalletDetailedList(@Body RequestBody body);

    /**
     * 开发票
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/userWallet/saveInvoice")
    Flowable<HttpResp<String>> saveInvoice(@Body RequestBody body);

    /**
     * 创建和修改资金约束密码
     *
     * @param password 密码
     * @param idCard   身份证号
     * @param realName 真实姓名
     * @return
     */
    @FormUrlEncoded
    @POST("api/art/userWallet/savePassword")
    Flowable<HttpResp<String>> savePassword(@Field("password") String password, @Field("idCard") String idCard,
                                            @Field("realName") String realName);

    /**
     * 大额转账
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/art/userWallet/saveTransferAccounts")
    Flowable<HttpResp<String>> saveTransferAccounts(@Body RequestBody body);

    /**
     * 提现密码验证
     *
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("api/art/userWallet/verificationPassword")
    Flowable<HttpResp<String>> verificationPassword(@Field("password") String password);

    /**
     * 用户微信支付
     *
     * @param money 金额
     * @return
     */
    @FormUrlEncoded
    @POST("api/art/userWallet/weixinRecharge")
    Flowable<HttpResp<String>> weixinRecharge(@Field("money") Integer money);


}
