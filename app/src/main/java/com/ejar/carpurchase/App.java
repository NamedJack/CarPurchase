package com.ejar.carpurchase;

import android.app.Application;
import android.content.Context;

import com.ganxin.library.LoadDataLayout;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.base.exception.CrashHandler;
import com.ejar.carpurchase.me.bean.UserDataBean;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.location.LocationPicker;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import static com.ejar.carpurchase.BuildConfig.LOG_DEBUG;

/**
 * Created by wxd1 on 2017/5/25.
 */

public class App extends Application {
    public static Context instance;
    public static String token = "";
    public static UserDataBean.UserBean userBean;
    public static UserDataBean.UpaybindingBean upaybindingBean;
    public static String turnOver = "";
    public static int shopId;
    public static boolean isDeBug = true;
    public static boolean isPermission = false;
    public static double memberTiChen = 0d;//团队提成

    public static Context getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        TU.register(this);

        new Thread(() -> LocationPicker.register(this)).start();

        isDeBug = LOG_DEBUG;
        if (LOG_DEBUG) {
            Logger.init().logLevel(LogLevel.FULL);
        } else {
            Logger.init().logLevel(LogLevel.NONE);
            CrashHandler.getInstance().init(this);//初始化全局异常管理
        }


        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("商家联盟网络请求"))
//                .addInterceptor(new RequestInterceptor())
//                .addInterceptor(new ResponseInterceptor())
//                .addInterceptor(new LogInterceptor())
                .cookieJar(cookieJar)
                .connectTimeout(8000L, TimeUnit.MILLISECONDS)
                .readTimeout(8000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);


        LoadDataLayout.getBuilder()
                .setLoadingText(getString(R.string.custom_loading_text))
                .setLoadingTextSize(16)
                .setLoadingTextColor(R.color.colorPrimary)
                //.setLoadingViewLayoutId(R.layout.custom_loading_view) //如果设置了自定义loading视图,上面三个方法失效
                .setEmptyImgId(R.drawable.ic_empty)
                .setErrorImgId(R.drawable.ic_error)
                .setNoNetWorkImgId(R.drawable.ic_no_network)
                .setEmptyImageVisible(true)
                .setErrorImageVisible(true)
                .setNoNetWorkImageVisible(true)
                .setEmptyText(getString(R.string.custom_empty_text))
                .setErrorText(getString(R.string.custom_error_text))
                .setNoNetWorkText(getString(R.string.custom_nonetwork_text))
                .setAllTipTextSize(16)
                .setAllTipTextColor(R.color.text_color_child)
                .setAllPageBackgroundColor(R.color.pageBackground)
                .setReloadBtnText(getString(R.string.custom_reloadBtn_text))
                .setReloadBtnTextSize(16)
                .setReloadBtnTextColor(R.color.colorPrimary)
                .setReloadBtnBackgroundResource(R.drawable.selector_btn_normal)
                .setReloadBtnVisible(true)
                .setReloadClickArea(LoadDataLayout.FULL);

    }

}
