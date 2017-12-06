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
import com.ejar.carpurchase.LoginActivity;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyBecomeMerchantBinding;
import com.ejar.carpurchase.me.bean.LocationBean;
import com.ejar.carpurchase.me.bean.TypeBean;
import com.ejar.carpurchase.me.bean.UserDataBean;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class BecomeMerchantActivity extends BaseBindingActivity<AtyBecomeMerchantBinding> {

    private AppCompatActivity thisActivity;
    private Context mContext;
    private String imgPath;
    private UserDataBean.EntityshopBean shopInfoBean;
    private int typeState;//判断申请状态的type

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_become_merchant);
        thisActivity = this;
        mContext = this.getApplicationContext();

        Bundle b = getIntent().getExtras();
        if (null != b) {
            //2修改    1 撤销申请
            typeState = b.getInt("type", -1);
            shopInfoBean = (UserDataBean.EntityshopBean) b.getSerializable("shopInfo");

            try {
                street = TextUtils.isEmpty(shopInfoBean != null ? shopInfoBean.getStreet() : null) ? "" : shopInfoBean.getStreet();
                bindingView.tvLocationPicker.setText(shopInfoBean.getProvince() + " "
                        + shopInfoBean.getCity() + " " + shopInfoBean.getCounty() + " " + street);

                bindingView.etShopName.setText(shopInfoBean.getShopName());
                bindingView.etCompanyName.setText(shopInfoBean.getCompanyName());
                bindingView.tvTypePicker.setText(shopInfoBean.getTypeName());
                bindingView.etPhone.setText(shopInfoBean.getPhone());
                bindingView.etBusinessLicense.setText(shopInfoBean.getBusinessNo());

                Glide.with(mContext).load(UrlConf.IMGHOST + shopInfoBean.getBusinessPhoto())
                        .into(bindingView.iv);

                type = shopInfoBean.getTypeId();
                province = shopInfoBean.getProvince();
                city = shopInfoBean.getCity();
                area = shopInfoBean.getCounty();
                street = shopInfoBean.getStreet();
                provinceId = Integer.valueOf(shopInfoBean.getProvinceAdcode());
                cityId = Integer.valueOf(shopInfoBean.getCityAdcode());
                areaId = Integer.valueOf(shopInfoBean.getCountyAdcode());
                streetId = Integer.valueOf(shopInfoBean.getStreetAdcode());

            } catch (Exception e) {
                e.printStackTrace();
            }


            if (typeState == 1) {
                bindingView.btnSubmit.setText("撤销申请");
                bindingView.btnSubmit.setOnClickListener(v -> doDeleteShop());
            } else if (typeState == 2) {
                bindingView.llReason.setVisibility(View.VISIBLE);
                bindingView.tvReason.setText(shopInfoBean.getRemark());
                bindingView.btnSubmit.setOnClickListener(v -> doResubmit());
            }
        } else bindingView.btnSubmit.setOnClickListener(v -> doSubmit());

        bindingView.ivReturn.setOnClickListener(v -> finish());

        bindingView.iv.setOnClickListener(v -> {
            startActivityForResult(new Intent(thisActivity, TakeImgActivity.class), 101);
            backgroundAlpha(0.5f);
        });

        bindingView.tvLocationPicker.setOnClickListener(v -> doPickLoction());
        bindingView.tvTypePicker.setOnClickListener(v -> queryType(0));

    }

    private void doResubmit() {
        doChange();
    }

    private void doChange() {
        String shopName = bindingView.etShopName.getText().toString().trim();
        String companyName = bindingView.etCompanyName.getText().toString().trim();
        String phone = bindingView.etPhone.getText().toString().trim();
        String businessLicense = bindingView.etBusinessLicense.getText().toString().trim();


        if (TextUtils.isEmpty(shopName) ||
                TextUtils.isEmpty(companyName) ||
                TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(businessLicense) ||
                TextUtils.isEmpty(area)) {
            TU.cT("本页所有数据为必填");
            return;
        }
        if (TextUtils.isEmpty(imgPath) && typeState == -1) {
            TU.cT("请上传营业执照");
            return;
        }


        PostFormBuilder builder = OkHttpUtils.post().url(UrlConf.UpdaeShopInfoUrl).tag(netTag)
                .addParams("token", App.token)
                .addParams("shopName", shopName)
                .addParams("companyName", companyName)
                .addParams("province", province)
                .addParams("provinceAdcode", provinceId + "")
                .addParams("city", city)
                .addParams("cityAdcode", cityId + "")
                .addParams("county", area)
                .addParams("countyAdcode", areaId + "")
                .addParams("phone", phone)
                .addParams("typeId", type + "")
                .addParams("id", shopInfoBean.getId() + "")
                .addParams("BusinessNo", businessLicense);
        if (!TextUtils.isEmpty(imgPath)) {
            String fName = imgPath.trim();
            String fileName = fName.substring(fName.lastIndexOf("/") + 1);
            builder.addFile("BusinessImg", fileName, new File(imgPath));
        } else builder.addParams("BusinessPhoto", "BusinessPhoto");

        if (!TextUtils.isEmpty(street))
            builder.addParams("street", street).addParams("streetAdcode", streetId + "");
        WNetUtil.StringCallBack(builder,
                UrlConf.UpdaeShopInfoUrl, thisActivity, "重新提交店铺信息中", true,
                new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            TU.cT(jsonObject.optString("msg"));
                            if (jsonObject.optInt("code") == 200) {
                                Intent intent = new Intent(thisActivity, BecomeMerchantSuccessActivity.class);
                                intent.putExtra("isChange", true);
                                startActivity(intent);
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

    private void doDeleteShop() {
        String url = UrlConf.DeleteShopUrl;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url).tag(netTag).addParams("token", App.token), url, thisActivity, new WNetUtil.WNetStringCallback() {
            @Override
            public void success(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    TU.cT(jsonObject.optString("msg"));
                    if (jsonObject.optInt("code") == 200) {
                        Intent intent = new Intent(thisActivity, LoginActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(Call call, Exception e, int id) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            imgPath = data.getStringExtra("path");
            Glide.with(mContext).load(imgPath)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(bindingView.iv);
        }
    }

    private void queryType(int type) {
        String url = UrlConf.QueryTypeUrl;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url).tag(netTag).addParams("pid", type + ""), url, thisActivity, new WNetUtil.WNetStringCallback() {
            @Override
            public void success(String response, int id) {
                Logger.e(response);
                Gson gson = new Gson();
                TypeBean typeBean = gson.fromJson(response, TypeBean.class);
                if (typeBean.getCode() == 200) {
                    optionsType.clear();
                    optionsType.addAll(typeBean.getData());
                    if (optionsType.size() != 0)
                        doShowTypePicker();
                } else TU.cT(typeBean.getMsg());
            }

            @Override
            public void error(Call call, Exception e, int id) {

            }
        });
    }

    private List<TypeBean.DataBean> optionsType = new ArrayList<>();

    OptionsPickerView pvOptions;
    int type = 0;

    private void doShowTypePicker() {
        pvOptions = new OptionsPickerView.Builder(thisActivity, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {

                String current = optionsType.get(options1).getPickerViewText();
                String before = bindingView.tvTypePicker.getText().toString().trim() + "";
                type = optionsType.get(options1).getId();
                int pid = optionsType.get(options1).getPid();
                String temp = pid == 0 ? " " : "->";
                if (pid == 0) before = "";
                bindingView.tvTypePicker.setText(before + temp + current);
                queryType(type);
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("选择经营项目")//标题
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
        pvOptions.setPicker(optionsType);
        pvOptions.show();
    }


    @Override
    public void onBackPressed() {

        if (null != pvOptions && pvOptions.isShowing()) {
            pvOptions.dismiss();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundAlpha(1f);
    }

    /**
     * 设置添加屏幕的背景透明度
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);

    }

    private void doSubmit() {
        String shopName = bindingView.etShopName.getText().toString().trim();
        String companyName = bindingView.etCompanyName.getText().toString().trim();
        String phone = bindingView.etPhone.getText().toString().trim();
        String businessLicense = bindingView.etBusinessLicense.getText().toString().trim();


        if (TextUtils.isEmpty(shopName) ||
                TextUtils.isEmpty(companyName) ||
                TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(businessLicense) ||
                TextUtils.isEmpty(area)) {
            TU.cT("本页所有数据为必填");
            return;
        }
//        if (TextUtils.isEmpty(imgPath)) {
//            TU.cT("请上传营业执照");
//            return;
//        }


        PostFormBuilder builder = OkHttpUtils.post().url(UrlConf.BecomShopUrl).tag(netTag)
                .addParams("token", App.token)
                .addParams("shopName", shopName)
                .addParams("companyName", companyName)
                .addParams("province", province)
                .addParams("provinceAdcode", provinceId + "")
                .addParams("city", city)
                .addParams("cityAdcode", cityId + "")
                .addParams("county", area)
                .addParams("countyAdcode", areaId + "")
                .addParams("phone", phone)
                .addParams("typeId", type + "")
                .addParams("BusinessNo", businessLicense);
        if (!TextUtils.isEmpty(imgPath)) {
            String fName = imgPath.trim();
            String fileName = fName.substring(fName.lastIndexOf("/") + 1);
            builder.addFile("BusinessImg", fileName, new File(imgPath));
        }

        if (!TextUtils.isEmpty(street))
            builder.addParams("street", street).addParams("streetAdcode", streetId + "");
        WNetUtil.StringCallBack(builder,
                UrlConf.BecomShopUrl, thisActivity, "申请成为商家中", true,
                new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            TU.cT(jsonObject.optString("msg"));
                            if (jsonObject.optInt("code") == 200) {
                                startActivity(new Intent(thisActivity, BecomeMerchantSuccessActivity.class));
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

    String province, city, area, street;
    int provinceId, cityId, areaId, streetId;
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
        pvOptions = new OptionsPickerView.Builder(thisActivity, new OptionsPickerView.OnOptionsSelectListener() {
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
                .setTitleText("请选择所在" + title)//标题
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
