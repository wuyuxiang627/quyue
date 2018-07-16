package lib.net;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by PC005 on 2017/11/15.
 */

public class OkHttpUtil {


    private OkHttpClient mOkHttpClient;


    private static class InstanceHolder {
        private static final OkHttpUtil INSTANCE = new OkHttpUtil();
    }

    private OkHttpUtil() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        mOkHttpClient = builder.build();


    }

    public static OkHttpUtil getInstance() {
        return InstanceHolder.INSTANCE;
    }


    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }


}
