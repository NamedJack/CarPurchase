package com.ejar.carpurchase.shop;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.bean.China;
import com.ejar.carpurchase.bean.ProvinceBean;
import com.ejar.carpurchase.databinding.AtyAddAdressBinding;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


public class AddAdressActivity extends BaseBindingActivity<AtyAddAdressBinding> {

    private AppCompatActivity thisActivity;
    private Context mContext;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_add_adress);
        thisActivity = this;
        mContext = this.getApplicationContext();
        initRegionPicker();
        bindingView.llLocationPicker.setOnClickListener(v -> pvOptions.show());

        bindingView.ivReturn.setOnClickListener(v -> finish());

        province = getIntent().getStringExtra("provice");
        city = getIntent().getStringExtra("city");
        area = getIntent().getStringExtra("area");
        String adress = getIntent().getStringExtra("adress");
        name = getIntent().getStringExtra("name");
        tel = getIntent().getStringExtra("tel");
        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            bindingView.etName.setText(name);
            bindingView.etPhone.setText(tel);
            bindingView.etDetailAdress.setText(adress);
            bindingView.tvLocation.setText(province + " " + city + " " + area);

            bindingView.tvTitle.setText("编辑地址");
        }

        bindingView.button.setOnClickListener(v -> doAdd());
    }


    private String name, tel, address;

    private void doAdd() {
        name = bindingView.etName.getText().toString().trim();
        tel = bindingView.etPhone.getText().toString().trim();
        address = bindingView.etDetailAdress.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(address) ||
                TextUtils.isEmpty(tel)) {
            TU.cT("请完整填写信息");
            return;
        }
        if (TextUtils.isEmpty(id)) {

            String url = UrlConf.AddAdress_URL;
            WNetUtil.StringCallBack(OkHttpUtils.post().url(url).tag(netTag)
                            .addParams("token", App.token)
                            .addParams("name", name)
                            .addParams("tel", tel)
                            .addParams("province", province)
                            .addParams("city", city)
                            .addParams("area", area)
                            .addParams("address", address)
                    , url, AddAdressActivity.this, new WNetUtil.WNetStringCallback() {
                        @Override
                        public void success(String response, int id) {
                            Logger.e(response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optInt("code") == 200) {
                                    TU.cT("添加成功");
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void error(Call call, Exception e, int id) {
                            TU.cT("添加地址失败");
                        }
                    });

        } else {
            //编辑地址


            String url = UrlConf.EditAdress_URL;
            WNetUtil.StringCallBack(OkHttpUtils.post().url(url).tag(netTag)
                            .addParams("id", id)
                            .addParams("token", App.token)
                            .addParams("name", name)
                            .addParams("tel", tel)
                            .addParams("province", province)
                            .addParams("city", city)
                            .addParams("area", area)
                            .addParams("address", address)
                    , url, AddAdressActivity.this, new WNetUtil.WNetStringCallback() {
                        @Override
                        public void success(String response, int id) {
                            Logger.e(response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optInt("code") == 200) {
                                    TU.cT("编辑成功");
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void error(Call call, Exception e, int id) {
                            TU.cT("编辑地址失败");
                        }
                    });
        }
    }


    public String province = "", city = " ", area = " ";
    //省
    private List<ProvinceBean> options1Items = new ArrayList<ProvinceBean>();
    //市
    private List<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    //区 县
    private List<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();

    private void initData() {
        try {
            //解析json数据
            InputStream is = getResources().getAssets().open("city.json");

            int available = is.available();

            byte[] b = new byte[available];
            int read = is.read(b);
            //注意格式，utf-8 或者gbk  否则解析出来可能会出现乱码
            String json = new String(b, "GBK");

            // System.out.println(json);

            Gson gson = new Gson();
            China china = gson.fromJson(json, China.class);
            ArrayList<China.Province> citylist = china.citylist;
            //======省级
            for (China.Province province : citylist
                    ) {
                String provinceName = province.p;

//                 System.out.println("provinceName==="+provinceName);
                ArrayList<China.Province.Area> c = province.c;
                //选项1
                options1Items.add(new ProvinceBean(0, provinceName, "", ""));
                ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
                //区级
                //选项2

                ArrayList<String> options2Items_01 = new ArrayList<String>();
                if (c != null) {
                    for (China.Province.Area area : c
                            ) {
                        //System.out.println("area------" + area.n + "------");

                        options2Items_01.add(area.n);
                        ArrayList<China.Province.Area.Street> a = area.a;
                        ArrayList<String> options3Items_01_01 = new ArrayList<String>();

                        //县级
                        if (a != null) {
                            for (China.Province.Area.Street street : a
                                    ) {
                                // System.out.println("street/////" + street.s);
                                options3Items_01_01.add(street.s);
                            }
                            options3Items_01.add(options3Items_01_01);
                        } else {
                            //县级为空的时候
                            options3Items_01_01.add("");
                            options3Items_01.add(options3Items_01_01);
                        }
                    }
                    options2Items.add(options2Items_01);
                } else {
                    //区级为空的时候  国外
                    options2Items_01.add("");
                    options2Items.add(options2Items_01);
                }
                options3Items.add(options3Items_01);
                ArrayList<String> options3Items_01_01 = new ArrayList<String>();
                options3Items_01_01.add("");
                options3Items_01.add(options3Items_01_01);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    OptionsPickerView pvOptions;

    //修改区域
    private void initRegionPicker() {


        initData();

        pvOptions = new OptionsPickerView.Builder(thisActivity, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {

                province = options1Items.get(options1).getPickerViewText();
                city = options2Items.get(options1).get(option2);
                area = options3Items.get(options1).get(option2).get(options3);

                if (city.equals("") || area.equals("")) city = province;


                String tx = options1Items.get(options1).getPickerViewText() + " "
                        + options2Items.get(options1).get(option2) + " "
                        + options3Items.get(options1).get(option2).get(options3);
                bindingView.tvLocation.setText(tx);
//                city += "市";
//                tvCity.setText(city);
//                if (tvCity.getText().toString().trim().equals(oldCity)) {
//                    return;
//                } else {
//                    oldCity = city;
//                    handler.sendEmptyMessage(2);
//                }
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("城市选择")//标题
                .setSubCalSize(20)//确定和取消文字大小
                .setTitleSize(22)//标题文字大小
//                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
//                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
                .setContentTextSize(20)//滚轮文字大小
//                .setLinkage(false)//设置是否联动，默认true
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);


    }
}
