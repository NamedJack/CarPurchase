package com.ejar.carpurchase.me.aty;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ganxin.library.LoadDataLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyBillFormBinding;
import com.ejar.carpurchase.me.bean.BillFormBean;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;

/**
 * 报单业绩
 */
public class BillFormActivity extends BaseBindingActivity<AtyBillFormBinding> {

    private AppCompatActivity thisActivity;
    private Context mContext;
    private String shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_bill_form);
        thisActivity = this;
        mContext = this.getApplicationContext();
        shopId = getIntent().getStringExtra("shopId");
        if (TextUtils.isEmpty(shopId)) {
            TU.cT("未获取到店铺id，请重试");
            return;
        }
        bindingView.ivReturn.setOnClickListener(v -> finish());
        initRecycleView();
        getList(false);
    }


    private RvAdapter adapter;

    private void initRecycleView() {
        adapter = new RvAdapter();
        bindingView.rvBillForm.setAdapter(adapter);
        bindingView.rvBillForm.setLayoutManager(new LinearLayoutManager(mContext));

        bindingView.srlBillForm.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                getList(false);
            }
        });


        // 滑动最后一个Item的时候回调onLoadMoreRequested方法
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getList(true);
            }
        }, bindingView.rvBillForm);

    }

    int pageNo = 1;

    private void getList(boolean isLoadMore) {
        if (!isLoadMore)
            bindingView.clLoaddata.setStatus(LoadDataLayout.LOADING);
        bindingView.clLoaddata.setOnReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                getList(isLoadMore);
            }
        });
        String url = UrlConf.BillFormUrl;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url)
                        .addParams("token", App.token)
                        .addParams("pageNo", pageNo + "")
                        .addParams("pageSize", "10")
                        .addParams("shopId", shopId)
                        .addParams("state", 1 + "")
                , url, thisActivity, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        bindingView.srlBillForm.setRefreshing(false);
                        Logger.e(response);
                        Gson gson = new Gson();
                        BillFormBean bean = null;
                        try {
                            bean = gson.fromJson(response, BillFormBean.class);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            TU.cT("返回数据有误");
                            bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                        }
                        if ((bean != null ? bean.getCode() : 0) == 200) {
                            bindingView.clLoaddata.setStatus(LoadDataLayout.SUCCESS);
                            int totalPage = bean.getReport().getPages();
                            if (isLoadMore) {
                                if (pageNo > totalPage) {
                                    adapter.loadMoreEnd();
                                } else {
                                    adapter.addData(bean.getReport().getRecords());
                                    adapter.loadMoreComplete();
                                }
                            } else {
                                bindingView.srlBillForm.setRefreshing(false);
                                adapter.setNewData(bean.getReport().getRecords());
                                // 检查是否满一屏，如果不满足关闭loadMore
                                adapter.disableLoadMoreIfNotFullPage(bindingView.rvBillForm);
                            }
                            if (bean.getReport().getRecords().size() != 0)
                                pageNo++;
                            else if (!isLoadMore)
                                bindingView.clLoaddata.setStatus(LoadDataLayout.EMPTY);
                            bindingView.tvTip.setText("共 " + adapter.getData().size() + " 条相关记录");
                        } else {
                            if (isLoadMore) adapter.loadMoreFail();
                            else bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        bindingView.srlBillForm.setRefreshing(false);
                        bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                    }
                });
    }

    class RvAdapter extends BaseQuickAdapter<BillFormBean.ReportBean.RecordsBean, BaseViewHolder> {
        public RvAdapter() {
            super(R.layout.item_rv_bill_form);
        }

        @Override
        protected void convert(BaseViewHolder helper, BillFormBean.ReportBean.RecordsBean item) {
            /**
             *    * id : 1496996198507717965
             * shopName :
             * account : 18113401543
             * userId :
             * shopId : 10
             * money :
             * moneyFactorge :
             * redMoney :
             * redMoneyFactorge :
             * state : 1 1审核中 2 通过  3 失败
             * type : null
             * deductMoney : null
             * time : 1496996199000
             * remark :
             * name :
             * phone :
             * type2 :
             * waresName : shop
             * waresNumber :
             */

            try {
                TextView tvState = helper.getView(R.id.tv_state);
                switch (item.getState()) {
                    case 1:
                        tvState.setText("审核中");
                        tvState.setTextColor(getResources().getColor(R.color.txt_charge_aduit));
                        tvState.setBackground(getResources().getDrawable(R.drawable.charge_aduit));
                        break;
                    case 2:
                        tvState.setText("审核通过");
                        tvState.setTextColor(getResources().getColor(R.color.txt_charge_success));
                        tvState.setBackground(getResources().getDrawable(R.drawable.charge_success));
                        break;
                    case 3:
                        tvState.setText("审核失败");
                        tvState.setTextColor(getResources().getColor(R.color.app_red));
                        tvState.setBackground(getResources().getDrawable(R.drawable.charge_fail));
                        break;
                }

                helper.setText(R.id.tv_time, "下单时间:" + times(item.getTime() + ""))
                        .setText(R.id.tv_money, item.getMoney() + "")
                        .setText(R.id.tv_red, item.getRedMoney() + "")
                        .setText(R.id.tv_name, TextUtils.isEmpty(item.getShopName()) ? "" : item.getShopName())
                        .setText(R.id.tv_user, item.getName() == null ? "未知" : item.getName() + "")
                        .setText(R.id.tv_order_num, "订单:" + item.getId());
                double total = Double.valueOf(item.getRedMoney() + "") + Double.valueOf(item.getMoney() + "");
                helper.setText(R.id.tv_total, total + "");
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }


    }


    /**
     * 调用此方法输入所要转换的时间戳输入例如（14660689144）输出（"2016年6月16日 17时21分"）
     *
     * @param time
     * @return
     */
    public String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc));
        return times;
    }
}
