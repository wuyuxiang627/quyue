//package lib.imageloader;
//
//import android.content.Context;
//
//import com.bumptech.glide.Registry;
//import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
//import com.bumptech.glide.load.model.GlideUrl;
//import com.bumptech.glide.module.LibraryGlideModule;
//
//import java.io.InputStream;
//
///**
// * Created by PC005 on 2017/11/27.
// */
//
////
////@GlideModule
////public final class OkHttpLibraryGlideModule extends LibraryGlideModule {
////
////
////
////    public void registerComponents(Context context, Registry registry) {
////        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
////    }
////}