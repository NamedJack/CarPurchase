package com.ejar.carpurchase.me.aty;

import android.os.Bundle;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyBalanceChargeBinding;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * 余额充值
 */
public class BalanceChargeActivity extends BaseBindingActivity<AtyBalanceChargeBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_balance_charge);
        bindingView.ivReturn.setOnClickListener(v -> finish());

        Bundle info = getIntent().getExtras();
        String money = info.getString("money", "");
        String phone = info.getString("phone", "");
        String trueName = info.getString("trueName", "");

        bindingView.tvBalance.setText("当前余额:" + money);
        bindingView.etToName.setText(trueName);
        bindingView.etToPhone.setText(phone);


        bindingView.btnSubmit.setOnClickListener(v -> doSubmit());
    }

    private void doSubmit() {
        String money = bindingView.etMoney.getText().toString().trim();
        String phone = bindingView.etToPhone.getText().toString().trim();
        String trueName = bindingView.etToName.getText().toString().trim();
        String remark = bindingView.etRemark.getText().toString().trim();

        if (TextUtils.isEmpty(money) ||
                TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(trueName)) {
            TU.cT("本页数据为必填");
            return;
        }

        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConf.BalanceChargeUrl).tag(netTag)
                        .addParams("token", App.token)
                        .addParams("money", money)
                        .addParams("type", 1 + "")   // TODO: 2017/6/13 type？
                        .addParams("name", trueName)
                        .addParams("remark", TextUtils.isEmpty(remark) ? "remark": remark)
                        .addParams("phone", phone),
                UrlConf.BalanceChargeUrl, BalanceChargeActivity.this, "提交充值信息中", true,
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
