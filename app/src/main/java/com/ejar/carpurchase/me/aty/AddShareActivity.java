package com.ejar.carpurchase.me.aty;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyAddShareBinding;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class AddShareActivity extends BaseBindingActivity<AtyAddShareBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_add_share);

        bindingView.ivReturn.setOnClickListener(v -> finish());
        bindingView.btnGetCode.setOnClickListener(v->getPhoneCode());
        bindingView.btnSubmit.setOnClickListener(v->doSubmit());
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
        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConf.GetAddSharePeopleCodeUrl).tag(netTag)
                        .addParams("phone", phone)
                        .addParams("token", App.token)
                        .addParams("mox", 1 + "")
                , UrlConf.GetAddSharePeopleCodeUrl, AddShareActivity.this, "获取验证码中", false, new WNetUtil.WNetStringCallback() {
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


    private void doSubmit() {
        String name = bindingView.etName.getText().toString().trim();
        String trueName = bindingView.etTrueName.getText().toString().trim();
        String pwd = bindingView.etPassword.getText().toString().trim();
        String phone = bindingView.etPhone.getText().toString().trim();
        String code = bindingView.etCode.getText().toString().trim();

        if (TextUtils.isEmpty(name) ||
                TextUtils.isEmpty(trueName) ||
                TextUtils.isEmpty(pwd) ||
                TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(code)) {
            TU.cT("本页所有数据为必填");
            return;
        }

        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConf.AddSharePeopleUrl).tag(netTag)
                        .addParams("token", App.token)
                        .addParams("account", name)
                        .addParams("name", trueName)
                        .addParams("password", pwd)
                        .addParams("phone", phone)
                        .addParams("codel", code)

                , UrlConf.AddSharePeopleUrl, AddShareActivity.this, "添加会员中", true,
                new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            TU.cT(jsonObject.optString("msg"));
                            if (jsonObject.optInt("code") == 200) {
                                finish();
                            }
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
