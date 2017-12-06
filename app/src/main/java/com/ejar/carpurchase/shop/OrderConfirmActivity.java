package com.ejar.carpurchase.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.animation.Attention.Tada;
import com.flyco.dialog.widget.NormalDialog;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.base.rx.RxEventCodeType;
import com.ejar.carpurchase.base.rx.Subscribe;
import com.ejar.carpurchase.cart.bean.SettleBean;
import com.ejar.carpurchase.databinding.AtyOrderConfirmBinding;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static com.ejar.carpurchase.App.token;

public class OrderConfirmActivity extends BaseBindingActivity<AtyOrderConfirmBinding> {


    private AppCompatActivity thisActivity;
    private Context mContext;
    private String adressId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_order_confirm);
        thisActivity = this;
        mContext = this.getApplicationContext();
        initView();
    }


    @Subscribe(code = RxEventCodeType.ORDER_CONFIRM_ADRESS_ID)
    public void doChangeAdress(AdressActivity.AdressBean.DataBean adress) {
        this.adressId = adress.getId() + "";
        String p = TextUtils.isEmpty(adress.getProvince()) ? "" : adress.getProvince();
        String c = TextUtils.isEmpty(adress.getCity()) ? "" : adress.getCity();
        String a = TextUtils.isEmpty(adress.getArea()) ? "" : adress.getArea();
        String adr = TextUtils.isEmpty(adress.getAddress()) ? "" : adress.getAddress();
        String location = p + " " + c + " " + a + " " + adr;
        bindingView.tvPeople.setText(adress.getName());
        bindingView.tvAdress.setText(location);
        bindingView.tvPhone.setText(adress.getTel());

    }

    SettleBean settleBean;

    private void initView() {
        bindingView.ivReturn.setOnClickListener(v -> finish());
        RvAdapter adapter = new RvAdapter();
        bindingView.rv.setAdapter(adapter);
        bindingView.rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter.setEmptyView(R.layout.item_rv_empty, bindingView.rv);


        cartIds = getIntent().getStringExtra("cartIds");
        if (cartIds == null) cartIds = "";

        settleBean = new Gson().fromJson(getIntent().getStringExtra("json"), SettleBean.class);
        adapter.setNewData(settleBean.getData().getProducts());
        if (settleBean.getData().getAddress() == null) {
            adressId = "-1";
        } else {
            adressId = settleBean.getData().getAddress().getId() + "";
        }

        for (SettleBean.DataBean.ProductsBean p : settleBean.getData().getProducts()) {
            amount += p.getBuyNumber() * p.getNowPrice();

            score += p.getBigScore() * p.getBuyNumber();

            String specId = "";
            String spec = "";
            for (SettleBean.DataBean.ProductsBean.AttributesBean attr : p.getAttributes()) {
                specId += attr.getId() + ",";
                spec += attr.getName() + ",";
            }
            orderDetailBeanList.add(new OrderDetailBean(p.getId() + "", p.getNowPrice() + "", p.getBuyNumber() + "", specId.substring(0, specId.length() - 1)
                    , spec.substring(0, spec.length() - 1)));
        }
        SettleBean.DataBean.AddressBean adress = settleBean.getData().getAddress();

        if (null != adress) {
            String p = TextUtils.isEmpty(adress.getProvince()) ? "" : adress.getProvince();
            String c = TextUtils.isEmpty(adress.getCity()) ? "" : adress.getCity();
            String a = TextUtils.isEmpty(adress.getArea()) ? "" : adress.getArea();
            String adr = TextUtils.isEmpty(adress.getAddress()) ? "" : adress.getAddress();
            String location = p + " " + c + " " + a + " " + adr;
            bindingView.tvPeople.setText(adress.getName());
            bindingView.tvAdress.setText(location);
            bindingView.tvPhone.setText(adress.getTel());

        }

        bindingView.tvTransFee.setText("￥ " + settleBean.getData().getFee());

        bindingView.tvRedUse.setText("(可用积分:" + settleBean.getData().getScore() + ")");

        bindingView.tvAmount.setText("￥ " + amount + "");

        bindingView.btnSubmit.setOnClickListener(v -> doSubmit());

        bindingView.rlAdress.setOnClickListener(v -> {
            Intent i = new Intent(thisActivity, AdressActivity.class);

            startActivity(i);
        });

        bindingView.cbRed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bindingView.tvRed.setVisibility(View.VISIBLE);

                    //使用了积分
                    double hadScore = settleBean.getData().getScore();
                    useScore = score;
                    if (score > hadScore) {
                        TU.cT("您的可用积分为：" + hadScore + "  ，不足以抵扣  ");
                        useScore = 0;
                        bindingView.cbRed.setChecked(false);
                        bindingView.tvRed.setVisibility(View.GONE);
                    } else {
                        bindingView.tvRed.setText("使用 " + useScore + " 积分进行抵扣");
                    }

                } else {
                    useScore = 0;
                    bindingView.tvRed.setVisibility(View.GONE);
                }


            }
        });

    }

    double useScore = 0d;
    double amount = 0d;
    double score = 0d;
    String cartIds = "";
    List<OrderDetailBean> orderDetailBeanList = new ArrayList<>();

    private void doSubmit() {
        if (adressId.equals("-1")) {
            TU.cT("请设置地址！");
            return;
        }

        amount = amount - useScore;
        Gson gson = new Gson();
        String jsonArray = gson.toJson(orderDetailBeanList);
        String url = UrlConf.SubmitOrder_URL;
        PostFormBuilder builder = OkHttpUtils.post().url(url)
                .addParams("token", token)
                .addParams("addressId", adressId)
                .addParams("money", amount + "")
                .addParams("score", useScore + "")
                .addParams("fee", bindingView.tvTransFee.getText().toString().trim().replace("￥", ""))
                .addParams("orderdetail", jsonArray)
                .addParams("cartIds", cartIds);
//        Logger.e("token " + App.token + " addressId  " + settleBean.getData().getAddress().getId() + "  money  " + amount
//                + "  score  " + score + "  fee  " + bindingView.tvTransFee.getText().toString().trim().replace("￥", "") + "  orderdetail  " + jsonArray + " cartIds " + cartIds
//        );

        WNetUtil.StringCallBack(builder
                , url, thisActivity, "提交订单中", true, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        /**
                         * {"code":200,"msg":"OK","data":{"orderId":62}}
                         */
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 200) {
                                String orderId = jsonObject.optJSONObject("data").optString("orderId");
                                doPaynow(orderId, amount);
                            } else TU.cT("提交失败！" + jsonObject.optString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {

                    }
                });
    }


    /**
     * 立即支付
     *
     * @param orderId
     * @param money
     */
    private void doPaynow(String orderId, double money) {
        NormalDialog dialog = new NormalDialog(this);
        dialog.isTitleShow(false)
                .content("确定支付吗？")
                .contentGravity(Gravity.CENTER)//
                .btnText("确定", "取消")
                .showAnim(new Tada())
                .show();
        dialog.setOnBtnClickL(() -> {
                    OkHttpUtils.post().url(UrlConf.PayNowOrder_URL).addParams("token", App.token).addParams("id", orderId)
                            .addParams("payMoney", money + "")
                            .build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            TU.cT("支付失败");
                            finish();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Logger.e(response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optInt("code") == 200) {
                                    TU.cT("支付成功");
                                } else {
                                    TU.cT("支付失败!" + jsonObject.optString("msg"));
                                }
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    dialog.superDismiss();
                },
                dialog::superDismiss);
    }


    private class OrderDetailBean {
        private String productID;
        private String productPrice;
        private String number;
        private String specId;
        private String specInfo;


        public OrderDetailBean(String productID, String productPrice, String number, String specId, String specInfo) {
            this.productID = productID;
            this.productPrice = productPrice;
            this.number = number;
            this.specId = specId;
            this.specInfo = specInfo;
        }

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getSpecId() {
            return specId;
        }

        public void setSpecId(String specId) {
            this.specId = specId;
        }

        public String getSpecInfo() {
            return specInfo;
        }

        public void setSpecInfo(String specInfo) {
            this.specInfo = specInfo;
        }
    }

    private class RvAdapter extends BaseQuickAdapter<SettleBean.DataBean.ProductsBean, BaseViewHolder> {
        public RvAdapter() {
            super(R.layout.item_rv_order_confirm);
        }

        @Override
        protected void convert(BaseViewHolder helper, SettleBean.DataBean.ProductsBean item) {
            String spec = "";
            for (SettleBean.DataBean.ProductsBean.AttributesBean attr : item.getAttributes()) {
                spec += attr.getName() + " ";
            }
            helper.setText(R.id.tv_product_name, item.getName())
                    .setText(R.id.tv_spec, spec)
                    .setText(R.id.tv_price, "￥ " + item.getNowPrice())
                    .setText(R.id.tv_num, "数量 x" + item.getBuyNumber());

            ImageView iv = helper.getView(R.id.iv_img);
            Glide.with(iv.getContext()).load(UrlConf.IMGHOST + item.getImage())
                    .centerCrop().placeholder(R.drawable.placeholder).into(iv);
        }
    }
}
