package com.ejar.carpurchase.cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.ganxin.library.LoadDataLayout;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingFragment;
import com.ejar.carpurchase.base.rx.RxEventCodeType;
import com.ejar.carpurchase.base.rx.Subscribe;
import com.ejar.carpurchase.base.rx.ThreadMode;
import com.ejar.carpurchase.cart.bean.CartBean;
import com.ejar.carpurchase.cart.bean.CartDataBean;
import com.ejar.carpurchase.databinding.FgtCartBinding;
import com.ejar.carpurchase.shop.OrderConfirmActivity;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by wxd1 on 2017/7/21.
 */

public class CartFragment extends BaseBindingFragment<FgtCartBinding> {
    @Override
    public int setContent() {
        return R.layout.fgt_cart;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {

        bindingView.tvOption.setOnClickListener((v) -> optionClick());
        bindingView.ivSelectAll.setOnClickListener((v) -> selectAllClick());
        bindingView.clLoaddata.setStatus(LoadDataLayout.LOADING);
        bindingView.srlCart.setOnRefreshListener(() -> {
            loadData();
        });

        bindingView.btnSubmit.setOnClickListener(v -> {
            if (bindingView.btnSubmit.getText().toString().contains("结算")) {
                settleCart();
            } else delete();
        });
        initRecycleview();
    }

    private List<CartBean> list = new ArrayList<>();
    private CartRvAdapter adapter;

    private void initRecycleview() {

        adapter = new CartRvAdapter(list);
        bindingView.rvCart.setAdapter(adapter);
        bindingView.rvCart.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setEmptyView(R.layout.item_rv_empty, bindingView.rvCart);
    }


    double fee = 0, feeCondition = 0;

    @Override
    protected void loadData() {
        super.loadData();
        list.clear();
        adapter.getSelectedSet().clear();
        OkHttpUtils.post().url(UrlConf.CartList_URL).tag(netTag)
                .addParams("token", App.token)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                bindingView.srlCart.setRefreshing(false);
                bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
            }

            @Override
            public void onResponse(String response, int id) {
                Logger.e(response);
                bindingView.srlCart.setRefreshing(false);
                bindingView.clLoaddata.setStatus(LoadDataLayout.SUCCESS);
                CartDataBean cartDataBean = new Gson().fromJson(response, CartDataBean.class);
                if (cartDataBean.getCode() == 400) {
                    TU.cT(cartDataBean.getMsg());
                    list.clear();
                    bindingView.tvTotal.setText("0");
                    bindingView.tvTransFee.setText("0");
                    return;
                }

                try {
                    fee = cartDataBean.getData().getFeeCondition().getFee();
                    feeCondition = cartDataBean.getData().getFeeCondition().getCondition();
                    bindingView.tvTransFee.setText(cartDataBean.getData().getFeeCondition().getFee() + "");
                    for (CartDataBean.DataBean.BuycarListBean data : cartDataBean.getData().getBuycarList()) {
                        list.add(new CartBean(CartBean.TYPE_NORMAL, data));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        });

    }

    private boolean isSelectAll = false;


    /**
     * 动态计算 总价 和 运费
     */
    @Subscribe(code = RxEventCodeType.CART_AMOUNT, threadMode = ThreadMode.MAIN)
    private void showAmount(String s) {
        Logger.e("总价" + s);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bindingView.tvTotal.post(() -> {
                    bindingView.tvTotal.setText("￥" + s);
                    double amount = Double.valueOf(s);
                    if (amount > feeCondition) {
                        bindingView.tvTransFee.setText("运费 " + 0 + " 元");
                    } else bindingView.tvTransFee.setText("运费 " + fee + " 元");
                });

                String old = bindingView.btnSubmit.getText().toString().substring(0, 2);
                bindingView.btnSubmit.setText(old + "(" + adapter.getSelectedSet().size() + ")");
            }
        });

    }

    /**
     * 结算
     */
    private void settleCart() {
        if (adapter.getSelectedSet().size() <= 0) return;
        String ids = "";
        for (CartBean item : adapter.getSelectedSet()) {
            ids += item.getDataBean().getId() + ",";
        }
        String cartIds = ids.substring(0, ids.length() - 1);
        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConf.SettleCart_URL).tag(netTag)
                        .addParams("token", App.token)
                        .addParams("cartIds", cartIds), UrlConf.SettleCart_URL, (AppCompatActivity) getActivity()
                , "结算中", true, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        showResult(true);
                        adapter.getData().removeAll(adapter.getSelectedSet());
                        adapter.notifyDataSetChanged();
                        adapter.getSelectedSet().clear();
                        bindingView.tvTotal.setText("0");
                        bindingView.tvTransFee.setText("0");
                        bindingView.btnSubmit.setText("结算(0)");
                        Intent i = new Intent(getActivity(), OrderConfirmActivity.class);
                        i.putExtra("json", response);
                        i.putExtra("cartIds", cartIds);
                        startActivity(i);
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        showResult(false);
                    }
                });


    }


    /**
     * 编辑模式 下的删除
     */
    private void delete() {
        if (adapter.getSelectedSet().size() <= 0) return;
        String ids = "";
        for (CartBean item : adapter.getSelectedSet()) {
            ids += item.getDataBean().getId() + ",";
        }
        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConf.DeleteCarts_URL).tag(netTag)
                .addParams("token", App.token)
                .addParams("ids", ids.substring(0, ids.length() - 1)), UrlConf.DeleteCarts_URL, (AppCompatActivity) getActivity(), "删除中", true, new WNetUtil.WNetStringCallback() {
            @Override
            public void success(String response, int id) {
                Logger.e(response);
                showResult(true);
                adapter.getData().removeAll(adapter.getSelectedSet());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void error(Call call, Exception e, int id) {
                showResult(false);
            }
        });


    }


    /**
     * 全选 logic
     */
    private void selectAllClick() {
        if (isSelectAll) {
            bindingView.ivSelectAll.setImageResource(R.mipmap.download_un);
            isSelectAll = false;
            adapter.getSelectedSet().clear();
        } else {
            bindingView.ivSelectAll.setImageResource(R.mipmap.download);
            isSelectAll = true;
        }

        for (CartBean dataBean : adapter.getData()) {
            dataBean.getDataBean().setIsSelected(isSelectAll);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * toolbar 右侧 "编辑"  和 "完成" logic
     */
    private void optionClick() {
        String s = bindingView.tvOption.getText().toString().trim();
        if (s.equals("编辑")) {
            bindingView.tvOption.setText("完成");
            bindingView.tvHe.setVisibility(View.GONE);
            bindingView.btnSubmit.setText("删除");
            bindingView.btnSubmit.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red));
            for (CartBean dataBean : adapter.getData()) {
                dataBean.getDataBean().setIsEdit(true);
            }

        } else {
            bindingView.tvOption.setText("编辑");
            bindingView.tvHe.setVisibility(View.VISIBLE);
            bindingView.btnSubmit.setText("结算");
            bindingView.btnSubmit.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.app_color));
            for (CartBean dataBean : adapter.getData()) {
                dataBean.getDataBean().setIsEdit(false);
            }
            List<UpdateCartBean> beanList = new ArrayList<>();
            for (CartBean cartBean : adapter.getSelectedSet()) {
                beanList.add(new UpdateCartBean(cartBean.getDataBean().getId() + "", cartBean.getDataBean().getProductNum() + ""));
            }
            if (adapter.getSelectedSet().size() != 0)
                updateCarts(new Gson().toJson(beanList));
        }
        adapter.notifyDataSetChanged();
    }


    /**
     * 批量修改购物车 的 buycars benn
     */
    private static class UpdateCartBean {
        private String id;
        private String productNum;

        public UpdateCartBean(String id, String productNum) {
            this.id = id;
            this.productNum = productNum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProductNum() {
            return productNum;
        }

        public void setProductNum(String productNum) {
            this.productNum = productNum;
        }
    }

    /**
     * 批量修改购物车
     */
    private void updateCarts(String buycars) {
        adapter.getSelectedSet().clear();
        String url = UrlConf.EditCarts_URL;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url).tag(netTag).addParams("token", App.token)
                        .addParams("buycars", buycars)
                , url, (AppCompatActivity) getActivity(), "修改中", true, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 200) {
                                TU.cT("修改成功");
                                showResult(true);
                            } else {
                                TU.cT("修改失败! " + jsonObject.optString("msg"));
                                showResult(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        TU.cT("修改失败");
                        showResult(false);
                    }
                });
    }

    /**
     * 显示操作结果
     *
     * @param isSuccess
     */
    private void showResult(boolean isSuccess) {
        View view = View.inflate(getContext(), R.layout.dialog_result, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_result);
        if (!isSuccess) iv.setImageResource(R.mipmap.failure);
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.show();

        dialog.getWindow().setContentView(view);

        iv.postDelayed(() -> dialog.dismiss(), 500);

    }

}
