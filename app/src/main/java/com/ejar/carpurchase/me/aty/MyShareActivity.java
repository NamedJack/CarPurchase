package com.ejar.carpurchase.me.aty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyMyShareBinding;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.glide.GlideCircleTransform;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class MyShareActivity extends BaseBindingActivity<AtyMyShareBinding> {

    private AppCompatActivity thisActivity;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_my_share);
        thisActivity = this;
        mContext = this.getApplicationContext();

        Bundle userInfo = getIntent().getExtras();

        String imgPath = userInfo.getString("img", "");//头像
        String nickName = userInfo.getString("nickName", "未知");
        int type = userInfo.getInt("type", 1);
        String prvince = userInfo.getString("prvince", "未知");
        String city = userInfo.getString("city", "未知");
        String county = userInfo.getString("county", "未知");
        String street = userInfo.getString("street", "未知");

        if (!TextUtils.isEmpty(imgPath)) {
            Glide.with(mContext)
                    .load(imgPath)
                    .transform(new GlideCircleTransform(mContext))
                    .into(bindingView.ivAvatar);
        }

        bindingView.tvLocation.setText(prvince + " " + city + " " + county + " " + street);
        bindingView.tvUser.setText(nickName);
        if (type == 1) {
            bindingView.tvType.setText("普通用户");
            bindingView.tvType.setBackground(getResources().getDrawable(R.drawable.shape_remark));
        }
        initClick();
        getQrImg();

        bindingView.tvMyMemberIncome.setOnClickListener(v -> {
            startActivity(new Intent(thisActivity, MyMemberIncomeActivity.class));
        });

        try {
            bindingView.tvMyTop.setText("我的推荐人:" + App.userBean.getTopUser() + "");
        } catch (Exception e) {
            e.printStackTrace();
            bindingView.tvMyTop.setVisibility(View.GONE);
        }
    }

    private void getQrImg() {
        OkHttpUtils.post().url(UrlConf.ViewShareQRUrl).tag(netTag).addParams("token", App.token)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                TU.cT(e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Logger.e(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    TU.cT(jsonObject.optString("msg"));
                    if (jsonObject.optInt("code") == 200) {
                        Glide.with(mContext).load(UrlConf.IMGHOST + jsonObject.optString("qrcode"))
                                .error(R.drawable.error)
                                .into(bindingView.ivQr);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initClick() {
        bindingView.ivReturn.setOnClickListener(v -> finish());
        bindingView.tvShareRegistration.setOnClickListener(v -> startActivity(new Intent(thisActivity, ShareRegistrationActivity.class)));
    }
}
