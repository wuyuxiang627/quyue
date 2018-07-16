package com.game.jxj.utils;


import com.game.jxj.model.entity.HttpResp;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.http.exception.ApiException;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 */
public class RxUtil {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {

                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<HttpResp<T>, Optional<T>> handleResult2() {   //compose判断结果

        return new FlowableTransformer<HttpResp<T>, Optional<T>>() {
            @Override
            public Flowable<Optional<T>> apply(Flowable<HttpResp<T>> upstream) {
                return upstream.flatMap(new Function<HttpResp<T>, Flowable<Optional<T>>>() {
                    @Override
                    public Flowable<Optional<T>> apply(HttpResp<T> tHttpResp) throws Exception {

                        int code = Integer.parseInt(tHttpResp.code);
                        if (code == 200) {

                            return createData(tHttpResp.transform());
                        } else {
                            return Flowable.error(new ApiException(code, tHttpResp.message));
                        }
                    }
                });
            }
        };

    }




    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<HttpResp<T>, T> handleResult() {   //compose判断结果
        return new FlowableTransformer<HttpResp<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<HttpResp<T>> httpResponseFlowable) {

                return httpResponseFlowable.flatMap(new Function<HttpResp<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(HttpResp<T> httpResp) {

                        int code = Integer.parseInt(httpResp.code);
                        if (code == 200) {

                            return createData(httpResp.data);
                        } else {
                            return Flowable.error(new ApiException(code, httpResp.message));
                        }
                    }
                });
            }
        };
    }


    /**
     * 生成Flowable
     *
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {

        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {

                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }


    public static <T> Flowable<Optional<T>> createData(final Optional<T> t) {

        return Flowable.create(e -> {

            try {

                e.onNext(t);
                e.onComplete();


            } catch (Exception ex) {
                e.onError(ex);
            }

        }, BackpressureStrategy.BUFFER);
    }
}
