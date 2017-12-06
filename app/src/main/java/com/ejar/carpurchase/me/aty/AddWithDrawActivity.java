package com.ejar.carpurchase.me.aty;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyAddWithdrawBinding;
import com.ejar.carpurchase.me.bean.WithdrawRuleBean;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 添加提现
 */
public class AddWithDrawActivity extends BaseBindingActivity<AtyAddWithdrawBinding> {

    private AppCompatActivity thisActivity;
    private Context mContext;

    private String money;
    private String offlineRebate;
    private String red;
    private String agentMoney;

    private double fee;
    private double lessMoney;
    private double moreMoney;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_add_withdraw);
        thisActivity = this;
        mContext = this.getApplicationContext();

        try {
            money = App.userBean.getMoney() + "";
        } catch (Exception e) {
            e.printStackTrace();
            money = 0 + "";
        }
        try {
            offlineRebate = App.userBean.getOfflineRebate() + "";
        } catch (Exception e) {
            e.printStackTrace();
            offlineRebate = 0 + "";
        }
        try {
            red = App.userBean.getRedMoney() + "";
        } catch (Exception e) {
            e.printStackTrace();
            red = 0 + "";
        }


        options1Items.add("余额提现");
        options1Items.add("业绩提现");
        options1Items.add("积分提现");
        if (App.memberTiChen != 0) {
            options1Items.add("团队提现");
        }

        if (App.userBean.getState() == 4) {//代理商
            options1Items.add("收益提现");

            try {
                agentMoney = App.userBean.getAngentMoney() + "";
            } catch (Exception e) {
                e.printStackTrace();
                agentMoney = 0 + "";
            }
        }

        bindingView.ivReturn.setOnClickListener(v -> finish());
        bindingView.rlType.setOnClickListener(v -> showPicker());
        bindingView.btnSubmit.setOnClickListener(v -> doSubmit());
        doGetRule();

        bindingView.etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) return;
                String money = s.toString().replace(".", "").trim();
                int m = Integer.valueOf(money);
                if (m > moreMoney || m < lessMoney) {
                    TU.cT("提现金额只能在" + lessMoney + "--" + moreMoney + " 元之间");
                    bindingView.tvFee.setText("");
                } else {
                    double f = fee * m;
                    bindingView.tvFee.setText("公司运营费用：" + f);
                }

            }
        });

    }


    private List<String> options1Items = new ArrayList<>();
    OptionsPickerView pvOptions;

    private void showPicker() {

        pvOptions = new OptionsPickerView.Builder(thisActivity, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {

                String tx = options1Items.get(options1);
                bindingView.tvShowType.setText(tx);
                String info = "";
                switch (tx) {
                    case "余额提现":
                        info = money;
                        break;
                    case "业绩提现":
                        info = offlineRebate;
                        break;
                    case "积分提现":
                        info = red;
                        break;
                    case "收益提现":
                        info = agentMoney;
                        break;
                    case "团队提现":
                        info = App.memberTiChen + "";
                        break;
                }
                bindingView.tvMoneyDes.setText("可提：" + info + " 元");
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("提现类型")//标题
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
        pvOptions.setPicker(options1Items);

        pvOptions.show();

    }

    @Override
    public void onBackPressed() {
        if (pvOptions != null && pvOptions.isShowing()) pvOptions.dismiss();
        else
            super.onBackPressed();
    }

    /**
     * 提现规则
     */
    private void doGetRule() {
        String url = UrlConf.WithdrawRule;
        WNetUtil.StringCallBack(OkHttpUtils.post().tag(netTag).url(url)
                        .addParams("token", App.token)
                , url, thisActivity, new WNetUtil.WNetStringCallback() {

                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        Gson gson = new Gson();
                        WithdrawRuleBean withdrawRuleBean = gson.fromJson(response, WithdrawRuleBean.class);
                        if (null != withdrawRuleBean && withdrawRuleBean.getCode() == 200) {
                            fee = (withdrawRuleBean.getYjrule().getWithdrawDepositRatio()) / 100;
                            Logger.e("fee " + fee);
                            moreMoney = withdrawRuleBean.getYjrule().getDrawMoneyTop();
                            lessMoney = withdrawRuleBean.getYjrule().getDrawMoneyDown();
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {

                    }
                }
        );
    }

    private void doSubmit() {
        String type = bindingView.tvShowType.getText().toString().trim();
        String money = bindingView.etMoney.getText().toString().trim();
        String remark = bindingView.etRemark.getText().toString().trim();

        if (TextUtils.isEmpty(money) ||
                TextUtils.isEmpty(type)) {
            TU.cT("本页数据为必填");
            return;
        }

        PostFormBuilder builder = OkHttpUtils.post().url(UrlConf.WithdrawRequest).tag(netTag)
                .addParams("token", App.token)
                .addParams("remark", TextUtils.isEmpty(remark) ? "remark" : remark);

        switch (type) {
            case "余额提现":
                builder.addParams("money", money);
                break;
            case "业绩提现":
                builder.addParams("offlineRebate", money);
                break;
            case "积分提现":
                builder.addParams("redMoney", money);
                break;
            case "收益提现":
                builder.addParams("angentMoney", money);
                break;
            case "团队提现":
                builder.addParams("teamMoney", money);
                break;
        }

        WNetUtil.StringCallBack(builder
                , UrlConf.WithdrawRequest, AddWithDrawActivity.this, "提现请求中", true,
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
}
