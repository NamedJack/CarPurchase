package com.ejar.carpurchase.me.aty;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyBindPhoneBinding;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class BindPhoneActivity extends BaseBindingActivity<AtyBindPhoneBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_bind_phone);

        bindingView.ivReturn.setOnClickListener(v -> finish());
        bindingView.btnSure.setOnClickListener(v -> doModify());
        bindingView.btnGetCode.setOnClickListener(v -> getPhoneCode());

    }


    private int time = 120;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (time <= 0) {
                time = 120;
                bindingView.btnGetCode.setText("获取验证码");
                bindingView.btnGetCode.setEnabled(true);
            } else {
                bindingView.btnGetCode.setText(time + "秒后重试");
                bindingView.btnGetCode.setEnabled(false);
                time--;
                mHandler.sendEmptyMessageDelayed(1, 1000);
            }
            return true;
        }
    });

    private void getPhoneCode() {
        if (time != 120) return;
        String phone = bindingView.etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            TU.cT("请输入手机号");
            return;
        }
        mHandler.sendEmptyMessage(1);
        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConf.GetModifyPhoneCodeUrl).tag(netTag)
                        .addParams("token", App.token)
                        .addParams("phone", phone)

                , UrlConf.GetModifyPhoneCodeUrl, BindPhoneActivity.this, "获取验证码中", false, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.optInt("code") != 200) {
                                time = 0;
                            }
                            TU.cT(jsonObject.optString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        Logger.e(e.getMessage());
                        time = 0;
                    }
                });

    }


    private void doModify() {
        String phone = bindingView.etPhone.getText().toString().trim();
        String code = bindingView.etCode.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            TU.cT("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            TU.cT("请输入验证码");
            return;
        }

        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConf.ModifyPhoneUrl).tag(netTag)
                        .addParams("token", App.token)
                        .addParams("phone", phone)
                        .addParams("codel", code)

                , UrlConf.ModifyPhoneUrl, this, "绑定手机中", true, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.optInt("code") == 200) {
                                Intent intent = new Intent();
                                intent.putExtra("phone", phone);
                                setResult(RESULT_OK, intent);
                                finish();
                            } else TU.cT(jsonObject.optString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {

                    }
                }
        );
    }
}
