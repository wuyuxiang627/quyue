package lib.net;

import retrofit2.Retrofit;

/**
 * Created by PC005 on 2017/11/20.
 */

public class HttpClient {


    private Retrofit mRetrofit;


    private HttpClient() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(RequestConfig.TEST_URL)
                .client(OkHttpUtil.getInstance().getOkHttpClient())
                .build();

    }

    private static class InstanceHolder {

        private static final HttpClient INSTANCE = new HttpClient();
    }


    public static HttpClient getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static Retrofit getRetorfit() {

        return getInstance().mRetrofit;
    }

}
