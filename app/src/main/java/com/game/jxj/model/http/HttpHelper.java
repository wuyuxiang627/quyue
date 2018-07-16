package com.game.jxj.model.http;

import com.game.jxj.model.entity.ActivityInfoResp;
import com.game.jxj.model.entity.AlipayWithdrawalReq;
import com.game.jxj.model.entity.AlipayWithdrawalResp;
import com.game.jxj.model.entity.ArtUserReq;
import com.game.jxj.model.entity.BannerListResp;
import com.game.jxj.model.entity.DeleteFavorReq;
import com.game.jxj.model.entity.DeleteOneNotifyReq;
import com.game.jxj.model.entity.DeleteVideoReq;
import com.game.jxj.model.entity.EmptyDateResp;
import com.game.jxj.model.entity.EvaluateListReq;
import com.game.jxj.model.entity.EvaluatelistResp;
import com.game.jxj.model.entity.FavorReq;
import com.game.jxj.model.entity.FollowListReq;
import com.game.jxj.model.entity.FollowListResp;
import com.game.jxj.model.entity.FollowVideoListResp;
import com.game.jxj.model.entity.HomepageListReq;
import com.game.jxj.model.entity.HttpReq;
import com.game.jxj.model.entity.HttpResp;
import com.game.jxj.model.entity.LoginResp;
import com.game.jxj.model.entity.MusicListReq;
import com.game.jxj.model.entity.MusicListResp;
import com.game.jxj.model.entity.MyMusicListReq;
import com.game.jxj.model.entity.ObjectTokenResp;
import com.game.jxj.model.entity.RegisterResp;
import com.game.jxj.model.entity.SaveActivityReq;
import com.game.jxj.model.entity.SaveArtEvaluateReq;
import com.game.jxj.model.entity.SaveCertifiedEnterpriseReq;
import com.game.jxj.model.entity.SaveCooperationReq;
import com.game.jxj.model.entity.SaveEcaluateFavorReq;
import com.game.jxj.model.entity.SaveInvoiceReq;
import com.game.jxj.model.entity.SaveOpinioReq;
import com.game.jxj.model.entity.SaveReportReq;
import com.game.jxj.model.entity.SaveTransferAccountsReq;
import com.game.jxj.model.entity.SaveUserFollowReq;
import com.game.jxj.model.entity.SavelinkPathReq;
import com.game.jxj.model.entity.SaveuserVideoReq;
import com.game.jxj.model.entity.SendCodeResp;
import com.game.jxj.model.entity.UpdateArtUserReq;
import com.game.jxj.model.entity.UserVideoInfoReq;
import com.game.jxj.model.entity.UserVideoInfoResp;
import com.game.jxj.model.entity.VideoBymusicIdReq;
import com.game.jxj.model.entity.VideoListReq;
import com.game.jxj.model.entity.VideoListResp;
import com.game.jxj.model.entity.VideoResp;
import com.game.jxj.model.entity.WalletDetailedListReq;
import com.game.jxj.model.entity.WalletDetailedListResp;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;


public interface HttpHelper {


    Flowable<HttpResp<List<SendCodeResp>>> sendCode(String phone, String mark);

    Flowable<HttpResp<RegisterResp>> register(String phone, String pw, String code, String refereeUser);

    Flowable<HttpResp<LoginResp>> login(String phone, String pw);

    Flowable<HttpResp<List<MusicListResp>>> getMusicList(MusicListReq listReq);

    Flowable<HttpResp<LoginResp>> getToken(String openId, String nickName, int sex, String filePath);

    Flowable<HttpResp<List<MusicListResp>>> getMyMusicList(MyMusicListReq data);

    Flowable<HttpResp<EmptyDateResp>> saveMusicCollection(String musicId);

    Flowable<HttpResp<EmptyDateResp>> deleteMusicCollection(String musicId);

    Flowable<HttpResp<List<FollowListResp>>> getFollowList(FollowListReq listReq);

    Flowable<HttpResp<AlipayWithdrawalResp>> getAlipayWithdrawal(AlipayWithdrawalReq listReq);

    Flowable<HttpResp<String>> deleteEvaluateFavor(String evaluateId);

    Flowable<HttpResp<String>> deleteVideo(DeleteVideoReq deleteVideoReq);

    Flowable<HttpResp<List<FollowVideoListResp>>> getFollowVideoList(HttpReq httpReq);

    Flowable<HttpResp<List<VideoResp>>> getRecommend();

    Flowable<HttpResp<UserVideoInfoResp>> getUserVideoInfo(UserVideoInfoReq userVideoInfoReq);

    Flowable<HttpResp<List<VideoResp>>> getVideoByMusicId(VideoBymusicIdReq videoBymusicIdReq);

    Flowable<HttpResp<List<VideoResp>>> getVideoInfo(String videoId);

    Flowable<HttpResp<VideoListResp>> getVideoList(VideoListReq videoListReq);

    Flowable<HttpResp<String>> saveArtEvaluate(SaveArtEvaluateReq saveArtEvaluateReq);

    Flowable<HttpResp<String>> saveEvaluateFavor(SaveEcaluateFavorReq saveEcaluateFavorReq);

    Flowable<HttpResp<String>> saveUserVideo(SaveuserVideoReq saveuserVideoReq);

    Flowable<HttpResp<ActivityInfoResp>> getActivityInfo(String activityId);

    Flowable<HttpResp<List<ActivityInfoResp>>> getActivityListByUserId(HttpReq httpReq);

    Flowable<HttpResp<String>> payActivity(String activityId);

    Flowable<HttpResp<ActivityInfoResp>> saveActivity(SaveActivityReq saveActivityReq);

    Flowable<HttpResp<String>> saveCertifiedEnterprise(SaveCertifiedEnterpriseReq saveCertifiedEnterpriseReq);

    Flowable<HttpResp<String>> saveCooperation(SaveCooperationReq saveCooperationReq);

    Flowable<HttpResp<String>> saveOpinion(SaveOpinioReq saveOpinioReq);

    Flowable<HttpResp<String>> saveReport(SaveReportReq saveReportReq);

    Flowable<HttpResp<List<ActivityInfoResp>>> getActivityList();

    Flowable<HttpResp<List<BannerListResp>>> getBannerList();

    Flowable<HttpResp<List<ActivityInfoResp>>> getEndActivityList(HttpReq httpReq);

    Flowable<HttpResp<List<EvaluatelistResp>>> getEvaluateList(EvaluateListReq evaluateListReq);

    Flowable<HttpResp<List<VideoResp>>> getHomePageList(HomepageListReq homepageListReq);

    Flowable<HttpResp<String>> getVersion();

    Flowable<HttpResp<String>> saveVideoWatch(String createPerson);

    Flowable<HttpResp<String>> saveLinkPath(SavelinkPathReq savelinkPathReq);


    Flowable<HttpResp<List<MusicListResp>>> getMyMusicList(HttpReq httpReq);


    Flowable<HttpResp<String>> deleteAllNotify();

    Flowable<HttpResp<String>> deleteOneNotify(String notifyId);

    Flowable<HttpResp<List<DeleteOneNotifyReq>>> deleteOneNotify();

    Flowable<HttpResp<String>> saveReadStatus();

    Flowable<HttpResp<ArtUserReq>> getArtUser(String userId);

    Flowable<HttpResp<String>> getArtUserList(ArtUserReq artUserReq);

    Flowable<HttpResp<String>> updateArtUser(UpdateArtUserReq updateArtUserReq);

    Flowable<HttpResp<String>> updateRole(Integer roleType);

    Flowable<HttpResp<String>> deleteFavor(DeleteFavorReq favorReq);

    Flowable<HttpResp<String>> saveFavor(DeleteFavorReq favorReq);

    Flowable<HttpResp<String>> saveForward(FavorReq favorReq);

    Flowable<HttpResp<String>> deleteUserFollow(SaveUserFollowReq saveUserFollowReq);

    Flowable<HttpResp<String>> saveUserFollow(SaveUserFollowReq saveUserFollowReq);

    Flowable<HttpResp<String>> alipayRecharge(Integer money);

    Flowable<HttpResp<String>> getUserWallet();

    Flowable<HttpResp<WalletDetailedListResp>> getWalletDetailedList(WalletDetailedListReq walletDetailedListReq);

    Flowable<HttpResp<String>> saveInvoice(SaveInvoiceReq saveInvoiceReq);

    Flowable<HttpResp<String>> savePassword(String password, String idCard, String realName);

    Flowable<HttpResp<String>> saveTransferAccounts(SaveTransferAccountsReq saveTransferAccountsReq);

    Flowable<HttpResp<String>> verificationPassword(String password);

    Flowable<HttpResp<String>> weixinRecharge(Integer money);

    Flowable<HttpResp<ObjectTokenResp>> alipayRecharge(Map<String, String> headers, String objectname);


}
