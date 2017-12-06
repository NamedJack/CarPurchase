package com.ejar.carpurchase.me.aty;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyOrderCenterBinding;
import com.ejar.carpurchase.me.fgt.OrderItemFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxd1 on 2017/7/21.
 */
public class OrderCenterActivity extends BaseBindingActivity<AtyOrderCenterBinding> {

    private AppCompatActivity thisActivity;
    private Context mContext;
    private List<PageBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_order_center);

        bindingView.ivReturn.setOnClickListener(v -> finish());

        initData();
        OrderVPadapter padapter = new OrderVPadapter(getSupportFragmentManager(), list);
        bindingView.vpOrder.setAdapter(padapter);
        bindingView.vpOrder.setOffscreenPageLimit(4);
        bindingView.tablayoutOrder.setupWithViewPager(bindingView.vpOrder);


        bindingView.vpOrder.setCurrentItem(getIntent().getIntExtra("gotoPage", 0));
    }

    private void initData() {

//        1:代付款 2：已付款/待发货 3：待收货/已发货 4：完成/已收货
        list = new ArrayList<>();
        OrderItemFragment fragment = OrderItemFragment.getInstance(0);
        list.add(new PageBean("全部订单", fragment));

        fragment = OrderItemFragment.getInstance(1);
        list.add(new PageBean("待付款", fragment));


        fragment = OrderItemFragment.getInstance(3);
        list.add(new PageBean("待收货", fragment));

        fragment = OrderItemFragment.getInstance(4);
        list.add(new PageBean("已完成", fragment));


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

    private static class OrderVPadapter extends FragmentStatePagerAdapter {
        List<PageBean> list;

        public OrderVPadapter(FragmentManager fm, List<PageBean> fragmentList) {
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
