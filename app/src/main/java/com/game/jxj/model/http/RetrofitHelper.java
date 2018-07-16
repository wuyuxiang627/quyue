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
import com.game.jxj.model.http.api.Apis;
import com.game.jxj.model.http.api.WangYiApis;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;
import okhttp3.RequestBody;

/**
 * Created by PC005 on 2017/11/28.
 */

public class RetrofitHelper implements HttpHelper {


    private Apis api;
    private WangYiApis wangYiApis;

    private Gson gson;

    @Inject
    public RetrofitHelper(Apis gankApis, WangYiApis wangYiApis) {
        this.api = gankApis;
        this.wangYiApis = wangYiApis;
        gson = new GsonBuilder().create();
    }


    @Override
    public Flowable<HttpResp<List<SendCodeResp>>> sendCode(String phone, String mark) {
        return api.getPhoneCode(phone, mark);
    }

    @Override
    public Flowable<HttpResp<RegisterResp>> register(String phone, String pw, String code, String refereeUser) {
        return api.register(phone, pw, code, refereeUser);
    }

    @Override
    public Flowable<HttpResp<LoginResp>> login(String phone, String pw) {
        return api.login(phone, pw);
    }

    @Override
    public Flowable<HttpResp<List<MusicListResp>>> getMusicList(MusicListReq data) {
        return api.getMusicList(convertRequestBody(data));
    }

    @Override
    public Flowable<HttpResp<LoginResp>> getToken(String openId, String nickName, int sex, String filePath) {
        return api.getToken(openId, nickName, sex, filePath);
    }

    @Override
    public Flowable<HttpResp<List<MusicListResp>>> getMyMusicList(MyMusicListReq data) {
        return api.getMyMusicList(convertRequestBody(data));
    }

    public Flowable<HttpResp<List<FollowListResp>>> getFollowList(FollowListReq listReq) {
        return api.getFollowList(convertRequestBody(listReq));
    }

    @Override
    public Flowable<HttpResp<AlipayWithdrawalResp>> getAlipayWithdrawal(AlipayWithdrawalReq alipayWithdrawalReq) {
        return api.getAlipayWithdrawal(convertRequestBody(alipayWithdrawalReq));
    }

    @Override
    public Flowable<HttpResp<String>> deleteEvaluateFavor(String evaluateId) {
        return api.deleteEvaluateFavor(evaluateId);
    }

    @Override
    public Flowable<HttpResp<String>> deleteVideo(DeleteVideoReq deleteVideoReq) {
        return api.deleteVideo(convertRequestBody(deleteVideoReq));
    }

    @Override
    public Flowable<HttpResp<List<FollowVideoListResp>>> getFollowVideoList(HttpReq httpReq) {
        return api.getFollowVideoList(convertRequestBody(httpReq));
    }

    @Override
    public Flowable<HttpResp<List<VideoResp>>> getRecommend() {
        return api.getRecommend();
    }

    @Override
    public Flowable<HttpResp<UserVideoInfoResp>> getUserVideoInfo(UserVideoInfoReq userVideoInfoReq) {
        return api.getUserVideoInfo(convertRequestBody(userVideoInfoReq));
    }

    @Override
    public Flowable<HttpResp<List<VideoResp>>> getVideoByMusicId(VideoBymusicIdReq videoBymusicIdReq) {
        return api.getVideoByMusicId(convertRequestBody(videoBymusicIdReq));
    }

    @Override
    public Flowable<HttpResp<List<VideoResp>>> getVideoInfo(String videoId) {
        return api.getVideoInfo(videoId);
    }

    @Override
    public Flowable<HttpResp<VideoListResp>> getVideoList(VideoListReq videoListReq) {
        return api.getVideoList(convertRequestBody(videoListReq));
    }

    @Override
    public Flowable<HttpResp<String>> saveArtEvaluate(SaveArtEvaluateReq saveArtEvaluateReq) {
        return api.saveArtEvaluate(convertRequestBody(saveArtEvaluateReq));
    }

    @Override
    public Flowable<HttpResp<String>> saveEvaluateFavor(SaveEcaluateFavorReq saveEcaluateFavorReq) {
        return api.saveEvaluateFavor(convertRequestBody(saveEcaluateFavorReq));
    }

    @Override
    public Flowable<HttpResp<String>> saveUserVideo(SaveuserVideoReq saveuserVideoReq) {
        return api.saveUserVideo(convertRequestBody(saveuserVideoReq));
    }

    @Override
    public Flowable<HttpResp<ActivityInfoResp>> getActivityInfo(String activityId) {
        return api.getActivityInfo(activityId);
    }

    @Override
    public Flowable<HttpResp<List<ActivityInfoResp>>> getActivityListByUserId(HttpReq httpReq) {
        return api.getActivityListByUserId(convertRequestBody(httpReq));
    }

    @Override
    public Flowable<HttpResp<String>> payActivity(String activityId) {
        return api.payActivity(activityId);
    }

    @Override
    public Flowable<HttpResp<ActivityInfoResp>> saveActivity(SaveActivityReq saveActivityReq) {
        return api.saveActivity(convertRequestBody(saveActivityReq));
    }

    @Override
    public Flowable<HttpResp<String>> saveCertifiedEnterprise(SaveCertifiedEnterpriseReq saveCertifiedEnterpriseReq) {
        return api.saveCertifiedEnterprise(convertRequestBody(saveCertifiedEnterpriseReq));
    }

    @Override
    public Flowable<HttpResp<String>> saveCooperation(SaveCooperationReq saveCooperationReq) {
        return api.saveCooperation(convertRequestBody(saveCooperationReq));
    }

    @Override
    public Flowable<HttpResp<String>> saveOpinion(SaveOpinioReq saveOpinioReq) {
        return api.saveOpinion(convertRequestBody(saveOpinioReq));
    }

    @Override
    public Flowable<HttpResp<String>> saveReport(SaveReportReq saveReportReq) {
        return api.saveReport(convertRequestBody(saveReportReq));
    }

    @Override
    public Flowable<HttpResp<List<ActivityInfoResp>>> getActivityList() {
        return api.getActivityList();
    }

    @Override
    public Flowable<HttpResp<List<BannerListResp>>> getBannerList() {
        return api.getBannerList();
    }

    @Override
    public Flowable<HttpResp<List<ActivityInfoResp>>> getEndActivityList(HttpReq httpReq) {
        return api.getEndActivityList(convertRequestBody(httpReq));
    }

    @Override
    public Flowable<HttpResp<List<EvaluatelistResp>>> getEvaluateList(EvaluateListReq evaluateListReq) {
        return api.getEvaluateList(convertRequestBody(evaluateListReq));
    }

    @Override
    public Flowable<HttpResp<List<VideoResp>>> getHomePageList(HomepageListReq homepageListReq) {
        return api.getHomePageList(convertRequestBody(homepageListReq));
    }

    @Override
    public Flowable<HttpResp<String>> getVersion() {
        return api.getVersion();
    }

    @Override
    public Flowable<HttpResp<String>> saveVideoWatch(String createPerson) {
        return api.saveVideoWatch(createPerson);
    }

    @Override
    public Flowable<HttpResp<String>> saveLinkPath(SavelinkPathReq savelinkPathReq) {
        return api.saveLinkPath(convertRequestBody(savelinkPathReq));
    }


    @Override
    public Flowable<HttpResp<List<MusicListResp>>> getMyMusicList(HttpReq httpReq) {
        return api.getMyMusicList(convertRequestBody(httpReq));
    }


    @Override
    public Flowable<HttpResp<String>> deleteAllNotify() {
        return api.deleteAllNotify();
    }

    @Override
    public Flowable<HttpResp<String>> deleteOneNotify(String notifyId) {
        return api.deleteOneNotify(notifyId);
    }

    @Override
    public Flowable<HttpResp<List<DeleteOneNotifyReq>>> deleteOneNotify() {
        return api.deleteOneNotify();
    }

    @Override
    public Flowable<HttpResp<String>> saveReadStatus() {
        return api.saveReadStatus();
    }

    @Override
    public Flowable<HttpResp<ArtUserReq>> getArtUser(String userId) {
        return api.getArtUser(userId);
    }

    @Override
    public Flowable<HttpResp<String>> getArtUserList(ArtUserReq artUserReq) {
        return api.getArtUserList(convertRequestBody(artUserReq));
    }

    @Override
    public Flowable<HttpResp<String>> updateArtUser(UpdateArtUserReq updateArtUserReq) {
        return api.updateArtUser(convertRequestBody(updateArtUserReq));
    }

    @Override
    public Flowable<HttpResp<String>> updateRole(Integer roleType) {
        return api.updateRole(roleType);
    }

    @Override
    public Flowable<HttpResp<String>> deleteFavor(DeleteFavorReq favorReq) {
        return api.deleteFavor(convertRequestBody(favorReq));
    }

    @Override
    public Flowable<HttpResp<String>> saveFavor(DeleteFavorReq favorReq) {
        return api.saveFavor(convertRequestBody(favorReq));
    }

    @Override
    public Flowable<HttpResp<String>> saveForward(FavorReq favorReq) {
        return api.saveForward(convertRequestBody(favorReq));
    }

    @Override
    public Flowable<HttpResp<String>> deleteUserFollow(SaveUserFollowReq saveUserFollowReq) {
        return api.deleteUserFollow(convertRequestBody(saveUserFollowReq));
    }

    @Override
    public Flowable<HttpResp<String>> saveUserFollow(SaveUserFollowReq saveUserFollowReq) {
        return api.saveUserFollow(convertRequestBody(saveUserFollowReq));
    }

    @Override
    public Flowable<HttpResp<String>> alipayRecharge(Integer money) {
        return api.alipayRecharge(money);
    }

    @Override
    public Flowable<HttpResp<String>> getUserWallet() {
        return api.getUserWallet();
    }

    @Override
    public Flowable<HttpResp<WalletDetailedListResp>> getWalletDetailedList(WalletDetailedListReq walletDetailedListReq) {
        return api.getWalletDetailedList(convertRequestBody(walletDetailedListReq));
    }

    @Override
    public Flowable<HttpResp<String>> saveInvoice(SaveInvoiceReq saveInvoiceReq) {
        return api.saveInvoice(convertRequestBody(saveInvoiceReq));
    }

    @Override
    public Flowable<HttpResp<String>> savePassword(String password, String idCard, String realName) {
        return api.savePassword(password,idCard,realName);
    }

    @Override
    public Flowable<HttpResp<String>> saveTransferAccounts(SaveTransferAccountsReq saveTransferAccountsReq) {
        return api.saveTransferAccounts(convertRequestBody(saveTransferAccountsReq));
    }

    @Override
    public Flowable<HttpResp<String>> verificationPassword(String password) {
        return api.verificationPassword(password);
    }

    @Override
    public Flowable<HttpResp<String>> weixinRecharge(Integer money) {
        return api.weixinRecharge(money);
    }

    @Override
    public Flowable<HttpResp<ObjectTokenResp>> alipayRecharge(Map<String, String> headers,String objectname) {
        return wangYiApis.alipayRecharge(headers,objectname);
    }


    @Override
    public Flowable<HttpResp<EmptyDateResp>> saveMusicCollection(String musicId) {
        return api.saveMusicCollection(musicId);
    }

    @Override
    public Flowable<HttpResp<EmptyDateResp>> deleteMusicCollection(String musicId) {
        return api.deleteMusicCollection(musicId);
    }


    private RequestBody convertRequestBody(Object o) {

        String json = gson.toJson(o);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);

        return requestBody;
    }
}
