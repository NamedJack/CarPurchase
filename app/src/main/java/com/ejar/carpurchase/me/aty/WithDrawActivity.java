package com.ejar.carpurchase.me.aty;

import android.content.Context;
import android.content.Intent;
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
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyWithdrawBinding;
import com.ejar.carpurchase.me.bean.WithDrawBean;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;

/**
 * 提现管理
 */
public class WithDrawActivity extends BaseBindingActivity<AtyWithdrawBinding> {

    private AppCompatActivity thisActivity;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_withdraw);
        thisActivity = this;
        mContext = this.getApplicationContext();
        initClick();
        initRecycleView();
        getWithDrawList(false);
    }

    private void initClick() {
        bindingView.ivReturn.setOnClickListener(v -> finish());
        bindingView.tvAddWithdraw.setOnClickListener(v -> {
            if (App.upaybindingBean == null || TextUtils.isEmpty(App.upaybindingBean.getName())) {
                TU.cT("请先绑定银行卡");
                return;
            }
            startActivity(new Intent(thisActivity, AddWithDrawActivity.class));
        });
    }


    private ArrayList<WithDrawBean.RecordBean.RecordsBean> list;
    private RvWithdrawAdapter adapter;

    private void initRecycleView() {
        list = new ArrayList<>();
        adapter = new RvWithdrawAdapter();
        bindingView.rvWithdraw.setAdapter(adapter);
        bindingView.rvWithdraw.setLayoutManager(new LinearLayoutManager(mContext));

        bindingView.srlWithdraw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                getWithDrawList(false);
            }
        });


        // 滑动最后一个Item的时候回调onLoadMoreRequested方法
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getWithDrawList(true);
            }
        }, bindingView.rvWithdraw);

    }

    int pageNo = 1;

    private void getWithDrawList(boolean isLoadMore) {
        if (!isLoadMore)
            bindingView.clLoaddata.setStatus(LoadDataLayout.LOADING);
        bindingView.clLoaddata.setOnReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                getWithDrawList(isLoadMore);
            }
        });
        String url = UrlConf.WithdrawRegistration;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url)
                        .addParams("token", App.token)
                        .addParams("pageNo", pageNo + "")
                        .addParams("pageSize", "10")
                , url, thisActivity, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        bindingView.srlWithdraw.setRefreshing(false);
                        Gson gson = new Gson();
                        WithDrawBean withDrawBean = gson.fromJson(response, WithDrawBean.class);
                        if (withDrawBean.getCode() == 200) {
                            bindingView.clLoaddata.setStatus(LoadDataLayout.SUCCESS);
                            int totalPage = withDrawBean.getRecord().getPages();
                            if (isLoadMore) {
                                if (pageNo > totalPage) {
                                    adapter.loadMoreEnd();
                                } else {
                                    adapter.addData(withDrawBean.getRecord().getRecords());
                                    adapter.loadMoreComplete();
                                }
                            } else {
                                bindingView.srlWithdraw.setRefreshing(false);
                                adapter.setNewData(withDrawBean.getRecord().getRecords());
                                // 检查是否满一屏，如果不满足关闭loadMore
                                adapter.disableLoadMoreIfNotFullPage(bindingView.rvWithdraw);
                            }
                            if (withDrawBean.getRecord().getRecords().size() != 0)
                                pageNo++;
                            else if (!isLoadMore)
                                bindingView.clLoaddata.setStatus(LoadDataLayout.EMPTY);
                        } else {
                            if (isLoadMore) adapter.loadMoreFail();
                            else bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                        }

                        bindingView.tvTip.setText("共 " + adapter.getData().size() + " 条提现记录");
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                        bindingView.srlWithdraw.setRefreshing(false);
                    }
                });
    }

    class RvWithdrawAdapter extends BaseQuickAdapter<WithDrawBean.RecordBean.RecordsBean, BaseViewHolder> {
        public RvWithdrawAdapter() {
            super(R.layout.item_rv_withdraw);
        }

        @Override
        protected void convert(BaseViewHolder helper, WithDrawBean.RecordBean.RecordsBean item) {

/**
 * lnterest : 77.5
 * remark : 你
 * bankName : 哦logo
 * id : 24
 * state : 1  提现的处理情况，1为未处理，2为已经处理',  3未通过'
 * type : 2   1.余额提现，2积分提现 ，3业绩提现',
 * userId : 16
 * account : 454846498935558
 * carryMoeny : 155
 * carrytime : 1496906181000
 */

            TextView tvState = helper.getView(R.id.tv_state);
            helper.setVisible(R.id.ll_reason, false);
            if (item.getState() == 1) {
                tvState.setText("审核中");
                tvState.setTextColor(getResources().getColor(R.color.txt_charge_aduit));
                tvState.setBackground(getResources().getDrawable(R.drawable.charge_aduit));
            } else if (item.getState() == 2) {
                tvState.setText("提现成功");
                tvState.setTextColor(getResources().getColor(R.color.txt_charge_success));
                tvState.setBackground(getResources().getDrawable(R.drawable.charge_success));
            } else {
                tvState.setText("提现失败");
                tvState.setTextColor(getResources().getColor(R.color.txt_remark));
                tvState.setBackground(getResources().getDrawable(R.drawable.charge_fail));
                helper.setVisible(R.id.ll_reason, true)
                        .setText(R.id.tv_reson, item.getRemark2());
            }

            String type = "";
            switch (item.getType()) {
                case 1:
                    type = "余额提现";
                    break;
                case 2:
                    type = "积分提现";
                    break;
                case 3:
                    type = "业绩提现";
                    break;
                case 4:
                    type = "收益提现";
                    break;
                case 5:
                    type = "团队提现";
                    break;
            }

            helper.setText(R.id.tv_money, "提现金额:" + item.getCarryMoeny() + "")
                    .setText(R.id.tv_fee, item.getLnterest() + "")
                    .setText(R.id.tv_type, type)
                    .setText(R.id.tv_start_time, times(item.getCarrytime() + ""));

        }


    }


    /**
     * 调用此方法输入所要转换的时间戳输入例如（14660689144）输出（"2016年6月16日 17时21分54秒"）
     *
     * @param time
     * @return
     */
    public String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc));
        return times;
    }


    /**
     * 获取时间，从yyyy-MM-dd HH:mm:ss  到  10位 的时间戳
     *
     * @param user_time
     * @return
     */
    public String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);//不截取就是十一位的时间戳
        } catch (ParseException e) {
        }
        return re_time;
    }
}
