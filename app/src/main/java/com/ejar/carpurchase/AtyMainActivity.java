package com.ejar.carpurchase;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;

import com.flyco.animation.Attention.Swing;
import com.flyco.dialog.widget.NormalDialog;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.base.BaseBindingFragment;
import com.ejar.carpurchase.base.exception.AppManager;
import com.ejar.carpurchase.cart.CartFragment;
import com.ejar.carpurchase.databinding.AtyMainBinding;
import com.ejar.carpurchase.me.MeFragment;
import com.ejar.carpurchase.shop.ShopFragment;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class AtyMainActivity extends BaseBindingActivity<AtyMainBinding> {

    private AppCompatActivity thisActivity;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_main);
        thisActivity = this;
        mContext = this.getApplicationContext();
        initData();
        checkNewVersion();
    }


    private void checkNewVersion() {
        OkHttpUtils.post().url(UrlConf.GetVesionNo_URL).addParams("type", "1")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Logger.e(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("code") == 200) {
                        final JSONObject i = jsonObject.optJSONObject("data");
                        if (null == i) TU.cT("未能从服务器获取版本信息");
                        int version = i.optInt("edition");
                        final String url = i.optString("url");
                        Log.e("msg", version + "versioncode" + getVersionCode());
                        if (version > getVersionCode()) {
                            final NormalDialog dialog = new NormalDialog(thisActivity);
                            dialog.isTitleShow(false)//
                                    .bgColor(Color.parseColor("#383838"))//
                                    .cornerRadius(5)//
                                    .content("检测到新版本\n如不下载将无法使用本应用\n确定下载吗？")//
                                    .contentGravity(Gravity.CENTER)//
                                    .contentTextColor(Color.parseColor("#ffffff"))//
                                    .dividerColor(Color.parseColor("#222222"))//
                                    .btnTextSize(15.5f, 15.5f)//
                                    .btnTextColor(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"))//
                                    .btnPressColor(Color.parseColor("#2B2B2B"))//
                                    .widthScale(0.85f)//
                                    .showAnim(new Swing())//
                                    .show();

                            dialog.setOnBtnClickL(
                                    () -> {
                                        dialog.dismiss();
                                        AppManager.getAppManager().AppExit(mContext);
                                    },
                                    () -> {
                                        //调用浏览器下载
                                        Intent intent = new Intent();
                                        intent.setAction("android.intent.action.VIEW");
                                        Uri content_url = Uri.parse(url);
                                        intent.setData(content_url);
                                        startActivity(intent);
                                        dialog.superDismiss();
                                        AppManager.getAppManager().AppExit(mContext);
                                    });
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public int getVersionCode() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName;
            int packageCode = packageInfo.versionCode;
            return packageCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void initData() {
        List<Fragment> fragments = new ArrayList<>(3);


        // create  fragment and add it
        BaseBindingFragment shopFragment = new ShopFragment();
        Bundle bMsg = new Bundle();
        bMsg.putString("title", "联盟商城");
        shopFragment.setArguments(bMsg);
        // add to fragments for adapter
        fragments.add(shopFragment);

//        // create  fragment and add it
//        BaseBindingFragment unitFragment = new UnitFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("title", "联盟商家");
//        unitFragment.setArguments(bundle);
//        // add to fragments for adapter
//        fragments.add(unitFragment);


        // create  fragment and add it
        BaseBindingFragment cartFragment = new CartFragment();
        // add to fragments for adapter
        fragments.add(cartFragment);


        // create  fragment and add it
        BaseBindingFragment meFragment = new MeFragment();
        Bundle bMe = new Bundle();
        bMe.putString("title", "会员中心");
        meFragment.setArguments(bMe);
        // add to fragments for adapter
        fragments.add(meFragment);


        // set adapter
        VpAdapter adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        bindingView.vpMain.setAdapter(adapter);
        bindingView.vpMain.setOffscreenPageLimit(3);

        // binding with ViewPager
        bindingView.bnveMain.setupWithViewPager(bindingView.vpMain);
    }


    /**
     * view pager adapter
     */
    private static class VpAdapter extends FragmentPagerAdapter {
        private List<Fragment> data;

        public VpAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }
    }
}
