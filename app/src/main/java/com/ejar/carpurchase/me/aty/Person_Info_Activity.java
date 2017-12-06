package com.ejar.carpurchase.me.aty;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyPersonInfoBinding;
import com.ejar.carpurchase.me.bean.LocationBean;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.glide.GlideCircleTransform;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class Person_Info_Activity extends BaseBindingActivity<AtyPersonInfoBinding> {

    private Context mContext;
    private AppCompatActivity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_person_info);
        thisActivity = this;
        mContext = this.getApplicationContext();


        Bundle userInfo = getIntent().getExtras();
        String userHead = userInfo.getString("img");
        if (!TextUtils.isEmpty(userHead)) {
            Glide.with(mContext).load(userHead).transform(new GlideCircleTransform(mContext)).into(bindingView.ivAvatar);
        }
        String userName = userInfo.getString("nickName");
        String email = userInfo.getString("email");

        province = userInfo.getString("prvince");
        city = userInfo.getString("city");
        area = userInfo.getString("county");
        street = userInfo.getString("street");
        detailAdress = userInfo.getString("detailAdress");
        if (TextUtils.isEmpty(street)) street = "";
        String qq = userInfo.getString("qq");
        String phone = userInfo.getString("phone");
        bindingView.etUsername.setText(userName);
        bindingView.etEmail.setText(email);
        bindingView.tvLocationPicker.setText(province + " " + city + " " + area + " " + street);
        bindingView.tvPhone.setText(phone);
        bindingView.etQq.setText(qq);
        bindingView.etAdressDetail.setText(detailAdress);


        bindingView.tvLocationPicker.setOnClickListener(v -> doPickLoction());

        bindingView.ivReturn.setOnClickListener(v -> finish());

        bindingView.rlChangePhone.setOnClickListener(v -> {
            startActivityForResult(new Intent(thisActivity, BindPhoneActivity.class), 102);
        });

        bindingView.rlImgAvatar.setOnClickListener(v -> {
            startActivityForResult(new Intent(thisActivity, TakeImgActivity.class), 101);
            backgroundAlpha(0.5f);
        });

        bindingView.btnSave.setOnClickListener(v -> doSubmit());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 101) {
                imgPath = data.getStringExtra("path");
                Glide.with(mContext).load(imgPath).transform(new GlideCircleTransform(mContext))
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(bindingView.ivAvatar);
            }
            if (requestCode == 102) {
                String phone = data.getStringExtra("phone");
                if (TextUtils.isEmpty(phone)) phone = "";
                bindingView.tvPhone.setText(phone);
            }
        }
    }

    private String imgPath;

    private void doSubmit() {
        String userName = bindingView.etUsername.getText().toString().trim();
        String email = bindingView.etEmail.getText().toString().trim();
        String qq = bindingView.etQq.getText().toString().trim();
        String phone = bindingView.tvPhone.getText().toString().trim();
        detailAdress = bindingView.etAdressDetail.getText().toString().trim();


        if (TextUtils.isEmpty(userName) ||
                TextUtils.isEmpty(phone) ||
                cityId == 0) {
            TU.cT("请选择现居地");
            return;
        }


        PostFormBuilder builder = OkHttpUtils.post().url(UrlConf.ModifyUserDataUrl).tag(netTag)
                .addParams("token", App.token)
                .addParams("name", userName)
                .addParams("phone", phone)
                .addParams("province", province)
                .addParams("city", city)
                .addParams("county", area)
                .addParams("provinceAdcode", provinceId + "")
                .addParams("cityAdcode", cityId + "")
                .addParams("countyAdcode", areaId + "");

        if (!TextUtils.isEmpty(street)) {
            builder.addParams("street", street)
                    .addParams("streetAdcode", streetId + "");
        }
        if (!TextUtils.isEmpty(email)) {
            builder.addParams("email", email);
        }
        if (!TextUtils.isEmpty(qq)) {
            builder.addParams("qq", qq);
        }
        if (!TextUtils.isEmpty(qq)) {
            builder.addParams("detailedAddress", detailAdress);
        }

        if (!TextUtils.isEmpty(imgPath)) {
            String fName = imgPath.trim();
            String fileName = fName.substring(fName.lastIndexOf("/") + 1);
            builder.addFile("UserHaed", fileName, new File(imgPath));
        } else builder.addParams("UserHead", "UserHead");

        WNetUtil.StringCallBack(builder, UrlConf.ModifyUserDataUrl, thisActivity, "修改个人资料中", true,
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
                        Logger.e(e.getMessage());
                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();
        backgroundAlpha(1f);
    }

    /**
     * 设置添加屏幕的背景透明度
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);

    }


    //#########################地区选择###################
    private void doPickLoction() {
        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConf.LocationPickUrl)
                        .addParams("pid", pid + "").tag(netTag)
                , UrlConf.LocationPickUrl, thisActivity, "获取地址中", true,
                new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Gson gson = new Gson();
                        LocationBean locationBean = gson.fromJson(response, LocationBean.class);
                        if (locationBean.getCode() == 200) {
                            if (null != locationBean && null != locationBean.getData()) {
                                locationItems = locationBean.getData();
                                showPicker();
                            }
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {

                    }
                }
        );
    }

    String province, city, area, street, detailAdress;
    int provinceId, cityId = 0, areaId, streetId;
    int pid = 0;
    int level = 1;
    private List<LocationBean.DataBean> locationItems = new ArrayList<>();


    private void showPicker() {
        if (locationItems.size() == 0) {//可能没有街道，就不传了
            pid = 0;
            level = 1;
            street = "";
            bindingView.tvLocationPicker.setText(province + " " + city + " " + area + " " + street);
            return;
        }
        String title = "";
        if (level == 1) title = "省";
        if (level == 2) title = "市";
        if (level == 3) title = "区域";
        if (level == 4) title = "街道";
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(thisActivity, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {

                String current = locationItems.get(options1).getPickerViewText();

                switch (locationItems.get(options1).getLevel()) {
                    case 1:
                        province = current;
                        pid = locationItems.get(options1).getId();
                        provinceId = pid;
                        level = 2;
                        doPickLoction();
                        break;
                    case 2:
                        city = current;
                        pid = locationItems.get(options1).getId();
                        cityId = pid;
                        level = 3;
                        doPickLoction();
                        break;
                    case 3:
                        area = current;
                        areaId = locationItems.get(options1).getId();
                        level = 4;
                        pid = areaId;
                        doPickLoction();
                        break;
                    case 4:
                        street = current;
                        streetId = locationItems.get(options1).getId();
                        pid = 0;
                        level = 1;
                        bindingView.tvLocationPicker.setText(province + " " + city + " " + area + " " + street);
                        break;
                }
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("请选择" + title)//标题
                .setSubCalSize(20)//确定和取消文字大小
                .setTitleSize(22)//标题文字大小
                .setSubmitColor(Color.parseColor("#ff6a36"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#ff6a36"))//取消按钮文字颜色
//                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
//                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
                .setContentTextSize(20)//滚轮文字大小
//                .setLinkage(false)//设置是否联动，默认true
                .setCyclic(false, false, false)//循环与否
//                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .build();
        pvOptions.setPicker(locationItems);

        pvOptions.show();

    }

}
