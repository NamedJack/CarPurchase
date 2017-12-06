package com.ejar.carpurchase.me.aty;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.LoginActivity;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyChangePwdBinding;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class ChangePwdActivity extends BaseBindingActivity<AtyChangePwdBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_change_pwd);

        bindingView.ivReturn.setOnClickListener(v -> finish());

        bindingView.btnGetCode.setOnClickListener(v -> getPhoneCode());

        bindingView.btnSubmit.setOnClickListener(v -> doModify());

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
        mHandler.sendEmptyMessage(1);
        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConf.GetModifyPwdCodeUrl).tag(netTag)
                        .addParams("token", App.token)
                , UrlConf.GetCodeUrl, ChangePwdActivity.this, "获取验证码中", false, new WNetUtil.WNetStringCallback() {
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
        String code = bindingView.etPhoneCode.getText().toString().trim();
        String newPwd = bindingView.etNew.getText().toString().trim();
        String confNewPwd = bindingView.etConfirmNew.getText().toString().trim();

        if (TextUtils.isEmpty(code) || TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(confNewPwd)) {
            TU.cT("本页所有数据为必填");
            return;
        }

        if (!newPwd.equals(confNewPwd)) {
            TU.cT("两次密码输入不一致");
            return;
        }

        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConf.ModifyPwdUrl).tag(netTag)
                        .addParams("token", App.token)
                        .addParams("password", newPwd)
                        .addParams("password1", confNewPwd)
                        .addParams("codel", code),
                UrlConf.ModifyPwdUrl, this, "修改密码中", true, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.optInt("code") == 200) {
                                TU.cT("修改成功，请重新登录");
                                Intent intent = new Intent(ChangePwdActivity.this, LoginActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
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
