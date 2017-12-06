package com.ejar.carpurchase.utils.location;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.ejar.carpurchase.bean.China;
import com.ejar.carpurchase.bean.ProvinceBean;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxd1 on 2017/5/31.
 */

public class LocationPicker {

    //################################区域选择###############
    public static String province = "", city = " ", area = " ";
    //省
    private static List<ProvinceBean> options1Items = new ArrayList<ProvinceBean>();
    //市
    private static List<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    //区 县
    private static List<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();

    private static void initData(Context context) {
        try {
            //解析json数据
            InputStream is = context.getResources().getAssets().open("city.json");

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

    public static void register(Context c) {
        initData(c);
    }


    //修改区域
    public static OptionsPickerView initRegionPicker(WeakReference<AppCompatActivity> weakReference, LocationCallback locationCallback) {


        OptionsPickerView pvOptions = new OptionsPickerView.Builder(weakReference.get(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {

                province = options1Items.get(options1).getPickerViewText();
                city = options2Items.get(options1).get(option2);
                area = options3Items.get(options1).get(option2).get(options3);

                if (city.equals("") || area.equals("")) city = province;


                String tx = options1Items.get(options1).getPickerViewText() + " "
                        + options2Items.get(options1).get(option2) + " "
                        + options3Items.get(options1).get(option2).get(options3);
                city += "市";

                if (locationCallback != null) {
                    locationCallback.getLocation(tx, city);
                }

            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("城市选择")//标题
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
                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);

        return pvOptions;

    }

    public interface LocationCallback {

        void getLocation(String location, String city);
    }
}
