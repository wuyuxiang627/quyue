package com.game.jxj.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.game.jxj.model.entity.ImageDNS;
import com.game.jxj.model.entity.LoginResp;
import com.game.jxj.model.entity.ObjectTokenResp;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.http.HttpHelper;
import com.game.jxj.model.http.subscriber.SimpleCommonSubscriber;
import com.gm.utils.CacheUtils;
import com.gm.utils.ToastUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadImgWangyiUtil {

    private String BUCKETNAME = "art";

    public static final String TYPE = "image/jpeg";

    public Context mContext;

    public HttpHelper httpHelper;

    public File file;

    public IUploadImgListener mUploadImgLis;

    public UploadImgWangyiUtil(Context context, HttpHelper httpHelper,File file,IUploadImgListener iUploadImgListener){
        this.mContext = context;
        this.httpHelper = httpHelper;
        this.file = file;
        this.mUploadImgLis = iUploadImgListener;
    }




    public  String setSign(HashMap<String, String> hashMap) {
        String md5Sign = null;
        //获取ASCLL编码排序字符串
        String asccllString = AscllMap(hashMap);
        //添加token
        LoginResp loginResp = (LoginResp) CacheUtils.getInstance().getSerializable("loginResp", null);
        String sign = asccllString + "&key=" + loginResp.token;
        Log.e("sign", "sign  " + sign);
        //MD5加密
        try {
            md5Sign = MD5(sign).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("sign", "MD5sign  " + md5Sign);
        getObjectToken(sign,file);
        return md5Sign;

    }



    public void getObjectToken(String sign,File file ){
        LoginResp loginResp = (com.game.jxj.model.entity.LoginResp) CacheUtils.getInstance().getSerializable("loginResp", null);
        HashMap map = new HashMap();
        map.put("userNo",loginResp.userId);
        map.put("sign",sign);

        httpHelper.alipayRecharge(map,file.getName())
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult2())
                .subscribeWith(new SimpleCommonSubscriber<Optional<ObjectTokenResp>>(mContext) {
                                   @Override
                                   public void onNext(Optional<ObjectTokenResp> LoginResp) {
                                       ObjectTokenResp objectTokenResp = LoginResp.get();
                                       ToastUtils.showLong(objectTokenResp.url);
                                       Log.e("图片上传", "objectTokenResp.url  " + objectTokenResp.url);
                                       getWANGYIImgDNS(objectTokenResp.token,objectTokenResp.url,file);
                                       mUploadImgLis.getObjectTokenOnNext(objectTokenResp);
                                   }

                               }
                );
    }




    /**
     * 获取网易云服务DNS地址
     *
     * @param token
     * @param
     */
    public void getWANGYIImgDNS(final String token, final String iamgeurl, final File file) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://lbs-eastchina1.126.net/lbs?version=1.0&bucketname="+BUCKETNAME).get().build();
        Log.e("图片上传凭证", "DNS0: " + request + "");
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("图片上传凭证9", "DNS失败: " + e.getMessage());
                mUploadImgLis.getWangyiDNSOnFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                Log.e("onResponseDNScode", code + "");
                if (response.code() == 200) {
                    String string = response.body().string();
                    Log.e("图片上传凭证10", "onResponseDNS1: " + string + "");
                    ImageDNS imageDNS = new Gson().fromJson(string, ImageDNS.class);
                    Log.e("图片上传凭证11", "onResponseDNS2: " + imageDNS + "");
                    for (String ip : imageDNS.getUpload()) {
                        //执行上传文件
                        pushFileYUN(ip, token, file,iamgeurl);
                        break;
                    }
                    mUploadImgLis.getWangyiDNSOnResponse(imageDNS);

                } else {
                    ToastUtils.showShort("不是200要爆炸，快跑");
                }
            }
        });

    }


    /**
     * Xutils  图片上传
     * @param URl
     * @param Token
     * @param file
     * @param iamgeurl
     */
    private void pushFileYUN(String URl, String Token, File file, final String iamgeurl) {

        final String urlIP = URl.substring(URl.lastIndexOf("/") + 1);
        final String url = URl + "/" + BUCKETNAME + "/" + file.getName() + "?" + "offset=0&complete=true&version=1.0";


        RequestParams paramsxUtils = new RequestParams(url);
        paramsxUtils.setUseCookie(true);
        paramsxUtils.addHeader("Host",urlIP);
        paramsxUtils.addHeader("x-nos-token",Token);
        paramsxUtils.addBodyParameter("file",file);
        x.http().post(paramsxUtils, new org.xutils.common.Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("图片上传成功12", s + "");
                mUploadImgLis.onSuccess(iamgeurl,s);


            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.e("图片上传失败13", throwable.getMessage() + "");
                mUploadImgLis.onError(throwable,b);
            }

            @Override
            public void onCancelled(CancelledException e) {
                mUploadImgLis.onCancelled(e);

            }

            @Override
            public void onFinished() {
                mUploadImgLis.onFinished();

            }

            @Override
            public void onWaiting() {
                mUploadImgLis.onWaiting();

            }

            @Override
            public void onStarted() {
                mUploadImgLis.onStarted();

            }

            @Override
            public void onLoading(long l, long l1, boolean b) {
                mUploadImgLis.onLoading(l,l1,b);
            }
        });

//
//            RequestBody fileBody = FormBody.create(MediaType.parse(TYPE), file);
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("file", file.getName(), fileBody).build();
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//        final Request request = new Request.Builder()
////                .addHeader("Content-Type", "image/jpeg")
//                .addHeader("Host", urlIP)
//                .addHeader("x-nos-token", Token).url(url).post(requestBody).build();
//        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String string = response.body().string();
//                Log.e("图片上传凭证5", "onResponseHttp成功1: " + string + "");
//                String code = MGson.get(string, "code");
//            }
//        });

    }

















    public  String MD5(String data) throws Exception {
        java.security.MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    //ASCLL编码排序
    public  String AscllMap(HashMap map) {
        Collection<String> keyset = map.keySet();

        List list = new ArrayList<String>(keyset);

        Collections.sort(list);
        //这种打印出的字符串顺序和微信官网提供的字典序顺序是一致的
        String stringBuilder = new String();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder = stringBuilder + list.get(i) + "=" + map.get(list.get(i)) + "&";
//            System.out.println(list.get(i)+"="+map.get(list.get(i)));
        }

        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    public  String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}
