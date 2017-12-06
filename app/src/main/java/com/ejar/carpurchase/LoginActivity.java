package com.ejar.carpurchase;


import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.CompoundButton;

import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.base.click.ClickFilter;
import com.ejar.carpurchase.databinding.AtyLoginBinding;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by wxd1 on 2017/5/26.
 */

public class LoginActivity extends BaseBindingActivity<AtyLoginBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_login);
        bindingView.btnLogin.setOnClickListener(v -> btnLoginClick());
        ClickFilter.setFilter(bindingView.btnLogin);
        bindingView.tvRegister.setOnClickListener(v -> doIntoRegister());

        bindingView.ivReturn.setOnClickListener(v -> finish());

        bindingView.tvForgetPwd.setOnClickListener(v -> doReSetPwd());

        bindingView.cbRemenberPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                if (isChecked) {
                    String userName = bindingView.etUsername.getText().toString().trim();
                    String pwd = bindingView.etPassword.getText().toString().trim();
                    if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pwd)) {
                        return;
                    }
                    preferences.edit().putString("account", userName).apply();
                    preferences.edit().putString("pwd", pwd).apply();

                } else {
                    preferences.edit().remove("account").apply();
                    preferences.edit().remove("pwd").apply();
                }
            }
        });

        if (bindingView.cbRemenberPwd.isChecked()) {
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            bindingView.etUsername.setText(preferences.getString("account", ""));
            bindingView.etPassword.setText(preferences.getString("pwd", ""));
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        requestPermission();
    }

    /**
     * 动态请求权限
     */
    private void requestPermission() {
        //获取  READ_EXTERNAL_STORAGE/ACCESS_WIFI_STAT  E权限属于Dangerous Permissions,自行做好android6.0以上的运行时权限获取
        RxPermissions permissions = new RxPermissions(this);
        permissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_WIFI_STATE)
                .subscribe(permission -> { // will emit 2 Permission objects
                    if (permission.granted) {
                        // `permission.name` is granted !
                        App.isPermission = true;

                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // Denied permission without ask never again
                        String perName = permission.name.equals(Manifest.permission.READ_EXTERNAL_STORAGE) ? "读取存储卡" : "访问wifi状态";
                        TU.cT(perName + " 权限被禁止，应用无法正常运行");
                        App.isPermission = false;
                        requestPermission();
                    } else {
                        // Denied permission with ask never again
                        // Need to go to the settings
                        String perName = permission.name.equals(Manifest.permission.READ_EXTERNAL_STORAGE) ? "读取存储卡" : "访问wifi状态";
                        AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this)
                                .setMessage(perName + "\n权限被禁止，请到 设置-权限 中给予")
                                .setPositiveButton("确定", (dialog1, which) -> {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                })
                                .create();
                        dialog.show();
                    }
                });
    }

    /**
     * 忘记密码
     */
    private void doReSetPwd() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(null);
            getWindow().setEnterTransition(null);
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(this, bindingView.tvForgetPwd, bindingView.tvForgetPwd.getTransitionName());
            startActivity(new Intent(this, ForgetPwdActivity.class), options.toBundle());
        } else {
            startActivity(new Intent(LoginActivity.this, ForgetPwdActivity.class));
        }

    }

    /**
     * 登录点击
     */
    private void btnLoginClick() {

        if (bindingView.btnLogin.getProgress() == -1) {
            bindingView.btnLogin.setProgress(0);
            return;
        }
        bindingView.btnLogin.setIndeterminateProgressMode(true);
        bindingView.btnLogin.setProgress(50);

        String userName = bindingView.etUsername.getText().toString().trim();
        String pwd = bindingView.etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pwd)) {
            bindingView.btnLogin.setProgress(-1);
            bindingView.btnLogin.setErrorText("用户名和密码为必填");
            return;
        }

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        if (bindingView.cbRemenberPwd.isChecked()) {
            preferences.edit().putString("account", userName).apply();
            preferences.edit().putString("pwd", pwd).apply();
        } else {
            preferences.edit().clear().apply();
        }


        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConf.LoginUrl)
                        .tag(netTag)
                        .addParams("account", userName)
                        .addParams("password", pwd)
                , UrlConf.LoginUrl, LoginActivity.this,
                new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
//                        {"msg":"登录成功","code":"200","token":"f106d0c5c4d1831e6f4f3aaca04f2b68"}
                        Logger.e(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 200) {
                                bindingView.btnLogin.setProgress(100);
                                App.token = jsonObject.optString("token");
                                bindingView.btnLogin.postDelayed(() -> doIntoMain(), 800);
                            } else {
                                bindingView.btnLogin.setProgress(-1);
                                bindingView.btnLogin.setErrorText(jsonObject.optString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            bindingView.btnLogin.setProgress(0);
                        }

                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        bindingView.btnLogin.setProgress(0);
                    }
                }
        );


    }


    /**
     * 进入注册页面
     */
    public void doIntoRegister() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(null);
            getWindow().setEnterTransition(null);
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(this, bindingView.tvRegister, bindingView.tvRegister.getTransitionName());
            startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
        } else {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }

    /**
     * 进入mainactivity
     */
    public void doIntoMain() {
        Intent i2 = new Intent(this, AtyMainActivity.class);
//        if (Build.VERSION.SDK_INT >= 21) {
//            Explode explode = new Explode();
//            explode.setDuration(300);
//            getWindow().setExitTransition(explode);
//            getWindow().setEnterTransition(explode);
//            ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
//            startActivity(i2, oc2.toBundle());
//        } else
        startActivity(i2);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
    }
}
