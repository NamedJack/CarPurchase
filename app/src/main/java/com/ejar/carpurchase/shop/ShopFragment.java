package com.ejar.carpurchase.shop;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingFragment;
import com.ejar.carpurchase.databinding.FgtShopBinding;
import com.ejar.carpurchase.shop.bean.ShopCatalogBean;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * Created by wxd1 on 2017/5/25.
 */

public class ShopFragment extends BaseBindingFragment<FgtShopBinding> {

    List<PageBean> list;

    @Override
    public int setContent() {
        return R.layout.fgt_shop;
    }


    @Override
    protected void loadData() {
        super.loadData();
        initData();
    }

    TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private void initData() {
        list = new ArrayList<>();
        WNetUtil.StringCallBack(OkHttpUtils.get().tag(netTag).url(UrlConf.ShopCatalog_URL), UrlConf.ShopCatalog_URL, (AppCompatActivity) getActivity(), new WNetUtil.WNetStringCallback() {
            @Override
            public void success(String response, int id) {

                Logger.e(response);
                try {
                    ShopItemFragment fragmentAll = ShopItemFragment.getInstance(-2);//全部
                    list.add(new PageBean("全部商品",fragmentAll));
                    ShopCatalogBean shopCatalogBean = new Gson().fromJson(response, ShopCatalogBean.class);
                    for (ShopCatalogBean.DataBean data : shopCatalogBean.getData()) {
                        ShopItemFragment fragment = ShopItemFragment.getInstance(data.getId());
                        list.add(new PageBean(data.getName(), fragment));
                    }

                    ShopVPadapter padapter = new ShopVPadapter(getChildFragmentManager(), list);
                    bindingView.vpShop.setAdapter(padapter);
                    bindingView.vpShop.setOffscreenPageLimit(list.size());
                    bindingView.tablayoutShop.setupWithViewPager(bindingView.vpShop);
                    bindingView.tablayoutShop.addOnTabSelectedListener(listener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(Call call, Exception e, int id) {

            }
        });


    }

    @Override
    public void onDestroy() {
        bindingView.tablayoutShop.removeOnTabSelectedListener(listener);
        super.onDestroy();
    }

    private static class PageBean {
        private String title;
        private Fragment fragment;

        public PageBean(String title, Fragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }
    }

    private static class ShopVPadapter extends FragmentStatePagerAdapter {
        List<PageBean> list;

        public ShopVPadapter(FragmentManager fm, List<PageBean> fragmentList) {
            super(fm);
            list = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position).fragment;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position).title;
        }
    }
}


