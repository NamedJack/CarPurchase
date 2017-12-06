package com.ejar.carpurchase.me.aty;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.baiiu.tsnackbar.Prompt;
import com.baiiu.tsnackbar.TSnackBar;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyBindCardBinding;
import com.ejar.carpurchase.me.bean.BankInfoBean;
import com.ejar.carpurchase.me.bean.LocationBean;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class BindCardActivity extends BaseBindingActivity<AtyBindCardBinding> {

    private AppCompatActivity thisActivity;
    private Context mContext;
    private BankInfoBean bankInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_bind_card);
        thisActivity = this;
        mContext = this.getApplicationContext();

        boolean isHadCard = getIntent().getBooleanExtra("isHadCard", false);
        if (isHadCard) {
            bindingView.tvTitle.setText("查看(编辑)银行卡");
            bindingView.btnDelete.setVisibility(View.VISIBLE);
            bindingView.btnDelete.setOnClickListener(v -> {
                new AlertDialog.Builder(thisActivity).setTitle("确定删除吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doDelete();
                            }
                        })
                        .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            });
            bindingView.btnSubmit.setText("修改");
            getBankInfo();
        } else {
            bindingView.etPhone.setText("");
            bindingView.etCard.setText("");
            bindingView.tvLocationPicker.setText("");
        }

        bindingView.ivReturn.setOnClickListener(v -> finish());
        bindingView.tvLocationPicker.setOnClickListener(v -> doPickLoction());
        bindingView.btnSubmit.setOnClickListener(v -> doSubmit(isHadCard));
    }

    private void getBankInfo() {
        String url = UrlConf.ViewBindCardUrl;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url).tag(netTag).addParams("token", App.token), url
                , thisActivity, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        Gson gson = new Gson();
                        bankInfoBean = gson.fromJson(response, BankInfoBean.class);
                        if (null != bankInfoBean && bankInfoBean.getCode() == 200) {
                            /**
                             * id : 6
                             * name : 嘻嘻
                             * userId : 16
                             * payaccount : 454846498935558
                             * state : 3
                             * bankOfDeposit : 哦logo
                             * bankofka : 攻击力
                             * phone : 18683661692
                             * province : 北京
                             * city : 北京市
                             * county : 延庆县
                             * street : null
                             * remark : null
                             */
                            bindingView.setInfo(bankInfoBean.getUpaybinding());
                            bindingView.etPhone.setText(bankInfoBean.getUpaybinding().getPhone());
                            bindingView.etCard.setText(bankInfoBean.getUpaybinding().getPayaccount());
                            bindingView.tvLocationPicker.setText(bankInfoBean.getUpaybinding().getProvince()+" "
                            +bankInfoBean.getUpaybinding().getCity()+" "
                            +bankInfoBean.getUpaybinding().getCounty()+" ");
                            province = bankInfoBean.getUpaybinding().getProvince() + "";
                            city = bankInfoBean.getUpaybinding().getCity() + "";
                            area = bankInfoBean.getUpaybinding().getCounty() + "";
                            street = bankInfoBean.getUpaybinding().getStreet() + "";
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {

                    }
                });
    }


    private void doDelete() {
        int id = 0;
        try {
            id = bankInfoBean.getUpaybinding().getId();
        } catch (Exception e) {
            e.printStackTrace();
            TSnackBar.make(thisActivity, "没有获取到银行卡信息,不能删除！", Prompt.ERROR).show();
            return;
        }
        String url = UrlConf.DeleteBindCardUrl;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url).tag(netTag).addParams("token", App.token)
                        .addParams("id", id + "")
                , url, thisActivity, "删除中", true, new WNetUtil.WNetStringCallback() {
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

    private void doSubmit(boolean isChange) {

        if (isChange && bankInfoBean == null) {
            TSnackBar.make(thisActivity, "没有获取到银行卡信息,不能修改！", Prompt.ERROR).show();
            return;
        }

        String name = bindingView.etTrueName.getText().toString().trim();
        String card = bindingView.etCard.getText().toString().trim();
        String bank = bindingView.etBank.getText().toString().trim();
        String bankChild = bindingView.etBankChild.getText().toString().trim();
        String location = bindingView.tvLocationPicker.getText().toString().trim();
        String phone = bindingView.etPhone.getText().toString().trim();

        if (TextUtils.isEmpty(name) ||
                TextUtils.isEmpty(card) ||
                TextUtils.isEmpty(bank) ||
                TextUtils.isEmpty(bankChild) ||
                TextUtils.isEmpty(location)) {
            TU.cT("本页面所有数据为必填");
            return;
        }
        if (TextUtils.isEmpty(province) ||
                TextUtils.isEmpty(city) ||
                TextUtils.isEmpty(area)) {
            TU.cT("请重新选择地址信息");
            return;
        }


        String url = isChange ? UrlConf.ChangeBindCardUrl : UrlConf.BindCardUrl;

        WNetUtil.StringCallBack(OkHttpUtils.post().url(url).tag(netTag)
                        .addParams("token", App.token)
                        .addParams("name", name)
                        .addParams("phone", phone)
                        .addParams("state", 3 + "")
                        .addParams("payaccount", card)
                        .addParams("BankOfDeposit", bank)
                        .addParams("bankofka", bankChild)
                        .addParams("province", province)
                        .addParams("city", city)
                        .addParams("county", area)
                        .addParams("street", street)
                , url, thisActivity, isChange ? "修改银行卡信息中" : "绑定银行卡中", true,
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

    OptionsPickerView pvOptions;

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

    @Override
    public void onBackPressed() {
        if (null != pvOptions && pvOptions.isShowing())
            pvOptions.dismiss();
        else
            super.onBackPressed();
    }
}
