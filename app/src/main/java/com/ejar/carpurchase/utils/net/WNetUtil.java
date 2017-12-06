package com.ejar.carpurchase.utils.net;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.baiiu.tsnackbar.Prompt;
import com.baiiu.tsnackbar.TSnackBar;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.WeiboDialogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.OkHttpRequestBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by wxd1 on 2017/3/15.
 */

//网络访问Utils
public class WNetUtil {

    public interface WNetCallback {

    }

    //回调
    public interface WNetStringCallback extends WNetCallback {

        public void success(String response, int id);

        public void error(Call call, Exception e, int id);
    }

    public interface WNetStringCallbackWithProgress extends WNetStringCallback {

        public void success(String response, int id);

        public void inProgress(int progress);

        public void error(Call call, Exception e, int id);
    }

    //回调
    public interface WNetFileCallback extends WNetCallback {
        public void FileSuccess(File file, int id);

        /**
         * 进度，
         *
         * @param progress 进度 0-100
         */
        public void inProgress(int progress);


        public void error(Call call, Exception e, int id);
    }

    //网络请求准备回调
    public interface WNetCanGoCallback {
        void canGo();
    }

    private static HashSet<String> urls = new HashSet<>();//所有请求中的路径

    private static Map<String, Dialog> dialogMap = new HashMap<>();//包含所有提示框的map


    /***
     * StringCallBackWhitProgress 的访问
     *
     * @param builder        OkHttpRequestBuilder
     * @param url            url
     * @param c              activity
     * @param msg            dialog提示信息
     *
     * @param stringCallback 回调
     */
    public static void StringCallBackWhitProgress(OkHttpRequestBuilder builder, final String url, AppCompatActivity c, String msg, final WNetStringCallback stringCallback) {

        if (!NetworkAvailableUtils.isNetworkAvailable(c)) {
            stringCallback.error(null, new Exception("网络不可用"), 0);
//            String response = AcacheUtil.getDefault(c.getApplicationContext(), AcacheUtil.StringCache).getAsString(url);
//            stringCallback.success(response, -1);
            return;
        }

        if (urls.contains(url)) {
            TU.cT("请求进行中，请稍后");
            return;
        }
        urls.add(url);
        ProgressDialog dialog = new ProgressDialog(c);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
        dialog.setCancelable(false);
        dialog.setMessage(msg);
        dialog.show();

        dialogMap.put(url, dialog);
        builder.addHeader("Content-Type", "multipart/form-data")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        TSnackBar.make(c, TextUtils.isEmpty(e.getMessage()) ? "服务器开小差了" : e.getMessage(), Prompt.ERROR).show();
                        if (App.isDeBug)
                            TSnackBar.make(c, TextUtils.isEmpty(e.getMessage()) ? "服务器开小差了" : e.getMessage(), Prompt.ERROR).show();
                        else
                            TSnackBar.make(c, "服务器开小差了", Prompt.ERROR).show();
                        if (null != dialogMap.get(url)) {
                            dialog.dismiss();
                            dialogMap.remove(url);
                            urls.remove(url);
                        }
                        try {
                            stringCallback.error(call, e, id);
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        if (id != -1) {//网络获取，缓存
//                            AcacheUtil.getDefault(c.getApplicationContext(), AcacheUtil.StringCache).put(url, response);
//                        }
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if (jsonObject.optInt("state") != 200) {
//                                SnackbarUtil.LongSnackbar(c.getWindow().getDecorView(), msg + " 出错啦！ " + jsonObject.optString("msg"), SnackbarUtil.Alert).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if (jsonObject.optInt("code") == 300) {
//                                TU.cT("账号在其它地方登录，请重新登录");
//                                Intent intent = new Intent(c, LoginActivity.class)
//                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                c.startActivity(intent);
//                            } else if (jsonObject.optInt("code") != 200) {
//                                TSnackBar.make(c, jsonObject.optString("msg"), Prompt.ERROR).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }


                        try {
                            stringCallback.success(response, id);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (null != dialogMap.get(url)) {
                                dialog.dismiss();
                                dialogMap.remove(url);
                                urls.remove(url);
                            }
                        }
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        int p = (int) (progress * 100);
                        dialog.setProgress(p);
                    }
                });
    }


    /**
     * 简单调用，用在获取用 rv 展示的数据
     *
     * @param builder
     * @param url
     * @param c
     * @param stringCallback
     */
    public static void StringCallBack(OkHttpRequestBuilder builder, final String url, AppCompatActivity c, final WNetStringCallback stringCallback) {
        StringCallBack(builder, url, c, " ", false, stringCallback);
    }

    /***
     * StringCallBack 的访问
     *
     * @param builder        OkHttpRequestBuilder
     * @param url            url
     * @param c              activity
     * @param msg            dialog提示信息
     * @param isShowDialog   是否显示dialog
     * @param stringCallback 回调
     */
    public static void StringCallBack(OkHttpRequestBuilder builder, final String url, AppCompatActivity c, String msg, boolean isShowDialog, final WNetStringCallback stringCallback) {

        if (!NetworkAvailableUtils.isNetworkAvailable(c)) {
            stringCallback.error(null, new Exception("网络不可用"), 0);
//            String response = AcacheUtil.getDefault(c.getApplicationContext(), AcacheUtil.StringCache).getAsString(url);
//            stringCallback.success(response, -1);
            return;
        }

        if (urls.contains(url)) {
            TU.cT("请求进行中，请稍后");
            return;
        }
        urls.add(url);
        Dialog dialog = null;
        if (isShowDialog) {
            dialog = WeiboDialogUtils.createLoadingDialog(c, msg);
        } else dialog = new Dialog(c);
        dialogMap.put(url, dialog);
        builder
//                .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=GBK")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (App.isDeBug)
                            TSnackBar.make(c, TextUtils.isEmpty(e.getMessage()) ? "服务器开小差了" : e.getMessage(), Prompt.ERROR).show();
                        else
                            TSnackBar.make(c, "服务器开小差了", Prompt.ERROR).show();
                        if (null != dialogMap.get(url)) {
                            WeiboDialogUtils.closeDialog(dialogMap.get(url));
                            dialogMap.remove(url);
                            urls.remove(url);
                        }
                        try {
                            stringCallback.error(call, e, id);
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        if (id != -1 && !url.equals(UrlConf.HOST + UrlConf.LoginUrl)) {//网络获取，缓存
//                            AcacheUtil.getDefault(c.getApplicationContext(), AcacheUtil.StringCache).put(url, response);
//                        }
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if (jsonObject.optInt("code") == 300) {
//                                TU.cT("账号在其它地方登录，请重新登录");
//                                Intent intent = new Intent(c, LoginActivity.class)
//                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                c.startActivity(intent);
//                            } else if (jsonObject.optInt("code") != 200) {
//                                TSnackBar.make(c, jsonObject.optString("msg"), Prompt.ERROR).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                        try {
                            stringCallback.success(response, id);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (null != dialogMap.get(url)) {
                                WeiboDialogUtils.closeDialog(dialogMap.get(url));
                                dialogMap.remove(url);
                                urls.remove(url);
                            }
                        }
                    }
                });
    }


    /***
     * FileCallBack 的访问
     *
     * @param builder          OkHttpRequestBuilder
     * @param url              url
     * @param c                context
     * @param fileStorePath    file存储路径
     * @param fileStoreName    file存储名称
     * @param msg              dialog提示信息
     * @param wNetFileCallback 回调
     */
    public static void FileCallBack(PostFormBuilder builder, final String url, String fileStorePath, String fileStoreName,
                                    AppCompatActivity c, String msg, final WNetFileCallback wNetFileCallback) {

        if (!NetworkAvailableUtils.isNetworkAvailable(c)) {
            wNetFileCallback.error(null, new Exception("网络不可用"), 0);
            return;
        }

        if (urls.contains(url)) {
            TU.cT("请求进行中，请稍后");
            return;
        }
        urls.add(url);

        ProgressDialog dialog = new ProgressDialog(c);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
        dialog.setCancelable(false);
        dialog.setMessage(msg);
        dialog.show();
        dialogMap.put(url, dialog);


        builder.addHeader("Content-Type", "multipart/form-data")
                .build().execute(new FileCallBack(fileStorePath, fileStoreName) {
            @Override
            public void onError(Call call, Exception e, int id) {
//                TSnackBar.make(c, e.getMessage(), Prompt.ERROR).show();
                if (App.isDeBug)
                    TSnackBar.make(c, TextUtils.isEmpty(e.getMessage()) ? "服务器开小差了" : e.getMessage(), Prompt.ERROR).show();
                else
                    TSnackBar.make(c, "服务器开小差了", Prompt.ERROR).show();
                if (null != dialogMap.get(url)) {
                    WeiboDialogUtils.closeDialog(dialogMap.get(url));
                    dialogMap.remove(url);
                    urls.remove(url);
                }
                try {
                    wNetFileCallback.error(call, e, id);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }


            }

            @Override
            public void onResponse(File response, int id) {

                try {
                    wNetFileCallback.FileSuccess(response, id);
                } catch (Exception e) {
                    e.printStackTrace();
                    TSnackBar.make(c, e.getMessage(), Prompt.ERROR);
                } finally {
                    if (null != dialogMap.get(url)) {
                        WeiboDialogUtils.closeDialog(dialogMap.get(url));
                        dialogMap.remove(url);
                        urls.remove(url);
                    }
                }
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                int p = (int) (progress * 100);
                dialog.setProgress(p);
                wNetFileCallback.inProgress(p);

            }
        });


    }

    /**
     * @param build
     */
    public static void stringCallback(WNetBuild build, WNetStringCallback wNetStringCallback) {
        try {
            if (build.P.isProgress) {
                StringCallBackWhitProgress(build.build(), build.P.url, build.P.compatActivity, build.P.msg, wNetStringCallback);

            } else
                StringCallBack(build.build(), build.P.url, build.P.compatActivity, build.P.msg, build.P.isShowDialog, wNetStringCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //#######################build##################################################
    public enum MT {
        get, post
    }

    public class WNetCofig {
        private String url = "";
        private AppCompatActivity compatActivity;
        private String msg = "";
        private WNetCallback callback;
        private boolean isShowDialog = true;
        private MT m;
        private Object tag;
        private Map<String, String> params;


        public OkHttpRequestBuilder build() throws Exception {
            if (m == null) {
                throw new Exception("WNetCofig without Method");
            }
            if (TextUtils.isEmpty(url)) {
                throw new Exception("WNetCofig without url");
            }
            if (compatActivity == null) {
                throw new Exception("WNetCofig without activity");
            }

            if (msg.equals("")) isShowDialog = false;
            OkHttpRequestBuilder builder;
            if (m == MT.post) {
                builder = OkHttpUtils.post().params(params);
            } else builder = OkHttpUtils.post();

            builder.url(url).tag(tag);
            return builder;
        }

        public WNetCofig params(Map<String, String> p) {
            this.params = p;
            return this;
        }

        public WNetCofig tag(Object tag) {
            this.tag = tag;
            return this;
        }

        public WNetCofig method(MT m) {
            this.m = m;
            return this;
        }

        public WNetCofig showDialog(boolean isShowDialog) {
            this.isShowDialog = isShowDialog;
            return this;
        }

        public WNetCofig url(String url) {
            this.url = url;
            return this;
        }

        public WNetCofig activity(AppCompatActivity compatActivity) {
            this.compatActivity = compatActivity;
            return this;
        }

        public WNetCofig msg(String msg) {
            this.msg = msg;
            return this;
        }

        public WNetCofig callback(WNetCallback callback) {
            this.callback = callback;
            return this;
        }
    }

    public static class WNetBuild {

        private final BuildParams P;

        public WNetBuild() {
            P = new BuildParams();
        }

        public OkHttpRequestBuilder build() throws Exception {
            if (P.m == null) {
                throw new Exception("WNetBuild without Method");
            }
            if (TextUtils.isEmpty(P.url)) {
                throw new Exception("WNetBuild without url");
            }
            if (P.compatActivity == null) {
                throw new Exception("WNetBuild without activity");
            }

            if (P.msg.equals("")) P.isShowDialog = false;
            if (P.tag == null) P.tag = P.compatActivity;
            OkHttpRequestBuilder builder;
            if (P.m == MT.post) {
                if (P.params == null) {
                    throw new Exception("WNetBuild post without params");
                }
                builder = OkHttpUtils.post().params(P.params);
            } else builder = OkHttpUtils.get();

            builder.url(P.url).tag(P.tag);
            return builder;
        }

        public WNetBuild params(Map<String, String> p) {
            P.params = p;
            return this;
        }

        public WNetBuild tag(Object tag) {
            P.tag = tag;
            return this;
        }

        public WNetBuild method(MT m) {
            P.m = m;
            return this;
        }

        public WNetBuild showDialog(boolean isShowDialog) {
            P.isShowDialog = isShowDialog;
            return this;
        }

        public WNetBuild url(String url) {
            P.url = url;
            return this;
        }

        public WNetBuild activity(AppCompatActivity compatActivity) {
            P.compatActivity = compatActivity;
            return this;
        }

        public WNetBuild msg(String msg) {
            P.msg = msg;
            return this;
        }

        public WNetBuild progress(boolean isShowProgress) {
            P.isProgress = isShowProgress;
            return this;
        }

    }

    public static class BuildParams {
        private String url = "";
        private AppCompatActivity compatActivity;
        private String msg = "";
        private boolean isShowDialog = true;
        private MT m;
        private Object tag;
        private Map<String, String> params;
        private boolean isProgress = false; //是否显示进度

        public BuildParams() {

        }
    }

}
