package com.ejar.carpurchase.me.aty;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyGotoBalanceBinding;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 转到余额
 */
public class GotoBlanceActivity extends BaseBindingActivity<AtyGotoBalanceBinding> {


    private String money;
    private String offlineRebate;
    private String red;

    private double fee;
    private int lessMoney;
    private Double moreMoney;
    private String turnOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_goto_balance);


        money = App.userBean.getMoney() + "";
        offlineRebate = App.userBean.getOfflineRebate() + "";
        red = App.userBean.getRedMoney() + "";
        lessMoney = 1;

        options1Items.add("业绩");
        options1Items.add("积分");
        if (App.userBean.getState() != 1) {
            options1Items.add("货款");
            turnOver = App.turnOver;
        }


        bindingView.ivReturn.setOnClickListener(v -> finish());

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
                double m = Double.valueOf(money);
                if (m > moreMoney || m < lessMoney) {
                    TU.cT("金额只能在" + lessMoney + "--" + moreMoney + " 元之间");
                }

            }
        });

        bindingView.rlType.setOnClickListener(v -> showPicker());
        bindingView.btnSubmit.setOnClickListener(v -> doSubmit());
    }


    private void doSubmit() {
        String money = bindingView.etMoney.getText().toString().trim();
        String t = bindingView.tvShowType.getText().toString().trim();

        if (TextUtils.isEmpty(money)) {
            TU.cT("请输入金额");
            return;
        }

        int type = -1;
        switch (t) {
            case "业绩":
                type = 1;
                break;
            case "积分":
                type = 2;
                break;
            case "货款":
                type = 3;
                break;

        }
        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConf.GotoBanlanceUrl).tag(netTag)
                        .addParams("token", App.token)
                        .addParams("money", money)
                        .addParams("type", type + "")  //1业绩 ，2积分 3货款
                , UrlConf.GotoBanlanceUrl, GotoBlanceActivity.this, "转到余额中", true,
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


    private List<String> options1Items = new ArrayList<>();

    private void showPicker() {

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(GotoBlanceActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {

                String tx = options1Items.get(options1);
                bindingView.tvShowType.setText(tx);
                String info = "";
                switch (tx) {
                    case "业绩":
                        info = offlineRebate;
                        moreMoney = Double.valueOf(offlineRebate);
                        break;
                    case "积分":
                        info = red;
                        moreMoney = Double.valueOf(red);
                        break;
                    case "货款":
                        info = turnOver;
                        moreMoney = Double.valueOf(turnOver);
                        break;
                }
                bindingView.tvMoneyDes.setText("可转：" + info + " 元");
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("类型")//标题
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
}
