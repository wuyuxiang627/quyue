package com.game.jxj.di.module;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.game.jxj.di.qualifier.ApiUrl;
import com.game.jxj.di.qualifier.WangYiUrl;
import com.game.jxj.model.entity.UserInfo;
import com.game.jxj.model.http.api.Apis;
import com.game.jxj.model.http.api.WangYiApis;
import com.game.jxj.model.http.interceptor.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PC005 on 2017/11/28.
 */

@Module
public class HttpModule {

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttp(OkHttpClient.Builder builder) {


//        File cacheFile = new File(Constants.PATH_CACHE);
//        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
//        Interceptor cacheInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                if (!SystemUtil.isNetworkConnected()) {
//                    request = request.newBuilder()
//                            .cacheControl(CacheControl.FORCE_CACHE)
//                            .build();
//                }
//                Response response = chain.proceed(request);
//                if (SystemUtil.isNetworkConnected()) {
//                    int maxAge = 0;
//                    // 有网络时, 不缓存, 最大保存时长为0
//                    response.newBuilder()
//                            .header("Cache-Control", "public, max-age=" + maxAge)
//                            .removeHeader("Pragma")
//                            .build();
//                } else {
//                    // 无网络时，设置超时为4周
//                    int maxStale = 60 * 60 * 24 * 28;
//                    response.newBuilder()
//                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                            .removeHeader("Pragma")
//                            .build();
//                }
//                return response;
//            }
//        };
        Interceptor apikey = chain -> {
            Request request = chain.request();
            request = request.newBuilder()
                    .addHeader("accessToken", UserInfo.getInstance().token)
                    .build();
            return chain.proceed(request);
        };


        // 设置统一的请求头部参数
        builder.addInterceptor(apikey);


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);


        builder.addNetworkInterceptor(new StethoInterceptor());


        //设置超时
        builder.connectTimeout(3, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }


    @Singleton
    @Provides
    @ApiUrl
    Retrofit provideRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, Apis.HOST);
    }

    @Singleton
    @Provides
    @WangYiUrl
    Retrofit provideWangYiRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, WangYiApis.HOST);
    }


    @Singleton
    @Provides
    Apis provideService(@ApiUrl Retrofit retrofit) {
        return retrofit.create(Apis.class);
    }


    @Singleton
    @Provides
    WangYiApis provideWangyiService(@WangYiUrl Retrofit retrofit) {
        return retrofit.create(WangYiApis.class);
    }


    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {

        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

}
