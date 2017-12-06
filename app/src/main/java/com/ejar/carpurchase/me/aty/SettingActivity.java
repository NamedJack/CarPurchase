package com.ejar.carpurchase.me.aty;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.flyco.animation.Attention.Swing;
import com.flyco.animation.Attention.Tada;
import com.flyco.dialog.widget.NormalDialog;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.LoginActivity;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.base.exception.AppManager;
import com.ejar.carpurchase.databinding.AtySettingBinding;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class SettingActivity extends BaseBindingActivity<AtySettingBinding> {

    private AppCompatActivity thisActivity;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_setting);
        thisActivity = this;
        mContext = this.getApplicationContext();
        initClick();
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName;
            int packageCode = packageInfo.versionCode;
            bindingView.tvVersion.setText( versionName + " (Build:" + packageCode + ")");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initClick() {
        bindingView.rlPersonInfo.setOnClickListener(v -> {
            Bundle userInfo = getIntent().getExtras();
            Intent intent = new Intent(thisActivity, Person_Info_Activity.class);
            intent.putExtras(userInfo);
            startActivity(intent);
        });
        bindingView.rlPwd.setOnClickListener(v -> startActivity(new Intent(thisActivity, ChangePwdActivity.class)));
        bindingView.rlLogout.setOnClickListener(v -> {
            final NormalDialog dialog = new NormalDialog(thisActivity);
            dialog.isTitleShow(false)
                    .content("确定退出吗？")
                    .contentGravity(Gravity.CENTER)//
                    .btnText("确定", "取消")
                    .showAnim(new Tada())
                    .show();
            dialog.setOnBtnClickL(() -> {

                        OkHttpUtils.post().url(UrlConf.LogoutUrl).addParams("token", App.token).tag(netTag).build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        TU.cT(e.getMessage());
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        Logger.e(response);
                                        Intent intent = new Intent(thisActivity, LoginActivity.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                });
                    }
                    ,
                    dialog::superDismiss
            );
        });
        bindingView.rlPayPwd.setOnClickListener(v -> startActivity(new Intent(thisActivity, PayPwdActivity.class)));

        bindingView.ivReturn.setOnClickListener(v -> finish());

        bindingView.rlCheckVersion.setOnClickListener(v -> {
                    checkNewVersion();
//                    SophixManager.getInstance().queryAndLoadNewPatch();
                }
        );
    }


    private void checkNewVersion() {

        OkHttpUtils.post().url(UrlConf.GetVesionNo_URL)
                .addParams("type", "1")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                TU.cT("查询失败  " + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Logger.e(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("code") == 200) {
                        final JSONObject i = jsonObject.optJSONObject("data");
                        if (null == i) TU.cT("未能从服务器获取版本信息");
                        int version = i.optInt("edition");
                        final String url = i.optString("url");
                        if (version > getVersionCode()) {
                            final NormalDialog dialog = new NormalDialog(thisActivity);
                            dialog.isTitleShow(false)//
                                    .bgColor(Color.parseColor("#383838"))//
                                    .cornerRadius(5)//
                                    .content("检测到新版本\n如不下载将无法使用本应用\n确定下载吗？")//
                                    .contentGravity(Gravity.CENTER)//
                                    .contentTextColor(Color.parseColor("#ffffff"))//
                                    .dividerColor(Color.parseColor("#222222"))//
                                    .btnTextSize(15.5f, 15.5f)//
                                    .btnTextColor(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"))//
                                    .btnPressColor(Color.parseColor("#2B2B2B"))//
                                    .widthScale(0.85f)//
                                    .showAnim(new Swing())//
                                    .show();

                            dialog.setOnBtnClickL(
                                    () -> {
                                        dialog.dismiss();
                                        AppManager.getAppManager().AppExit(mContext);
                                    },
                                    () -> {

                                        //调用浏览器下载
                                        Intent intent = new Intent();
                                        intent.setAction("android.intent.action.VIEW");
                                        Uri content_url = Uri.parse(url);
                                        intent.setData(content_url);
                                        startActivity(intent);
                                        dialog.superDismiss();
                                        AppManager.getAppManager().AppExit(mContext);
                                    });
                        } else {
                            TU.cT("已经是最新版本");
                        }
                    } else
                        TU.cT("查询失败  " + jsonObject.optInt("code") + "\n"
                                + jsonObject.optString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public int getVersionCode() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName;
            int packageCode = packageInfo.versionCode;
            return packageCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
