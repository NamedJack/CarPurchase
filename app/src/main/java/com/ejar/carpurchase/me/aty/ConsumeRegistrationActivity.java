package com.ejar.carpurchase.me.aty;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;

import com.bigkoo.pickerview.OptionsPickerView;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyConsumeRegistrationBinding;
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

public class ConsumeRegistrationActivity extends BaseBindingActivity<AtyConsumeRegistrationBinding> {

    private AppCompatActivity thisActivity;
    private Context mContext;
    private String customName;
    private String customRedMoney;
    private String shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_consume_registration);
        thisActivity = this;
        mContext = this.getApplicationContext();

        shopId = getIntent().getStringExtra("shopId");
        options1Items.add("现金支付");
        options1Items.add("混合支付");
        options1Items.add("积分支付");

        bindingView.ivReturn.setOnClickListener(v -> finish());
        bindingView.llTypePicker.setOnClickListener(v -> showPicker());
        bindingView.tvShopName.setText(App.upaybindingBean.getName());
        bindingView.btnSubmit.setOnClickListener(v -> {
            try {
                doSubmit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        initWatcher();

//        bindingView.etCustomrAccount.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 此处为得到焦点时的处理内容
//                } else {
//                    // 此处为失去焦点时的处理内容
//                    getInfo(bindingView.etCustomrAccount.getText().toString().trim());
//                }
//            }
//        });
//
//
//        bindingView.etMoneyPay.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 此处为得到焦点时的处理内容
//                } else {
//                    // 此处为失去焦点时的处理内容
//                    getMoneyFee(bindingView.etMoneyPay.getText().toString().trim());
//                }
//            }
//        });
//
//
//        bindingView.etRedPay.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 此处为得到焦点时的处理内容
//                } else {
//                    // 此处为失去焦点时的处理内容
//                    getInfo(bindingView.etRedPay.getText().toString().trim());
//                }
//            }
//        });

        bindingView.btnAccountSearch.setOnClickListener(v -> getInfo(bindingView.etCustomrAccount.getText().toString().trim()));

        bindingView.btnMoneySearch.setOnClickListener(v -> getMoneyFee(bindingView.etMoneyPay.getText().toString().trim()));

        bindingView.btnRedSearch.setOnClickListener(v -> getRedFee(bindingView.etRedPay.getText().toString().trim()));

    }


    int watcherType = -1;
    String editString;
    private Handler handler = new Handler();

    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            //在这里调用服务器的接口，获取数据
            switch (watcherType) {
                case 1: //获取用户信息
                    getInfo(editString);
                    break;
                case 2: //获取现金费用
                    getMoneyFee(editString);
                    break;
                case 3: //获取积分费用
                    getRedFee(editString);
                    break;
            }

        }
    };


    private void initWatcher() {
        bindingView.etCustomrAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (delayRun != null) {
                    //每次editText有变化的时候，则移除上次发出的延迟线程
                    handler.removeCallbacks(delayRun);
                }
                editString = s.toString();
                watcherType = 1;
                if (TextUtils.isEmpty(editString) || editString.length() < 2)
                    return;
                //延迟800ms，如果不再输入字符，则执行该线程的run方法
                handler.postDelayed(delayRun, 1500);
            }
        });

        bindingView.etMoneyPay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (delayRun != null) {
                    //每次editText有变化的时候，则移除上次发出的延迟线程
                    handler.removeCallbacks(delayRun);
                }
                editString = s.toString();
                watcherType = 2;
                if (TextUtils.isEmpty(editString) || editString.length() < 2)
                    return;
                //延迟800ms，如果不再输入字符，则执行该线程的run方法
                handler.postDelayed(delayRun, 1500);
            }
        });

        bindingView.etRedPay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (delayRun != null) {
                    //每次editText有变化的时候，则移除上次发出的延迟线程
                    handler.removeCallbacks(delayRun);
                }
                editString = s.toString();
                watcherType = 3;
                if (TextUtils.isEmpty(editString) || editString.length() < 2)
                    return;
                //延迟800ms，如果不再输入字符，则执行该线程的run方法
                handler.postDelayed(delayRun, 1000);
            }
        });

    }

    /**
     * 根据现金支付 获取 手续费
     *
     * @param money
     */
    private void getMoneyFee(String money) {
        if (TextUtils.isEmpty(money)) return;
        String url = UrlConf.GetMoneyFeefoUrl;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url).tag(netTag)
                        .addParams("token", App.token)
                        .addParams("money", money)
                , url, thisActivity, "获取现金手续费", true, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            TU.cT(jsonObject.optString("msg"));
                            if (jsonObject.optInt("code") == 200) {
                                String fee = jsonObject.optString("moneyFactorge");
                                bindingView.tvMoneyFee.setText(fee);
                                calculateFee();
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


    /**
     * 获取积分费用
     *
     * @param red
     */
    private void getRedFee(String red) {
        if (TextUtils.isEmpty(red)) return;
        String url = UrlConf.GetRedFeefoUrl;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url).tag(netTag)
                        .addParams("token", App.token)
                        .addParams("money", red)
                , url, thisActivity, "获取积分手续费", true, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            TU.cT(jsonObject.optString("msg"));
                            if (jsonObject.optInt("code") == 200) {
                                String fee = jsonObject.optString("redMoneyFactorge");
                                bindingView.tvRedFee.setText(fee);

                                calculateFee();

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

    /**
     * 计算平台费
     */
    private void calculateFee() {
        String moneyFee = bindingView.tvMoneyFee.getText().toString().trim();
        String redFee = bindingView.tvRedFee.getText().toString().trim();
        String type = bindingView.tvType.getText().toString();
        double plantformFee = 0;
        if (type.equals("积分支付")) {
            if (TextUtils.isEmpty(redFee)) return;
            plantformFee = Double.valueOf(redFee);
        } else if (type.equals("现金支付")) {
            if (TextUtils.isEmpty(moneyFee)) return;
            plantformFee = Double.valueOf(moneyFee);
        } else {
            if (TextUtils.isEmpty(redFee) || TextUtils.isEmpty(moneyFee)) return;
            plantformFee = Double.valueOf(moneyFee) + Double.valueOf(redFee);
        }
        bindingView.tvPlantformFee.setText(plantformFee + "");
    }


    /**
     * 获取买家信息
     *
     * @param account 买家账号
     */
    public void getInfo(String account) {
        if (TextUtils.isEmpty(account)) return;
        String url = UrlConf.GetUserInfoUrl;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url).tag(netTag)
                        .addParams("token", App.token)
                        .addParams("account", account)
                , url, thisActivity, "获取买家信息中", true, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            TU.cT(jsonObject.optString("msg"));
                            if (jsonObject.optInt("code") == 200) {
                                JSONObject object = jsonObject.optJSONObject("data");
                                customName = object.optString("name");
                                customRedMoney = object.optString("redMoney");
                                bindingView.tvCustomInfo.setText(customName);
                                bindingView.tvHadRed.setText(customRedMoney);
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


    public void doSubmit() {
        Logger.e("支付方式" + type);
        String payPwd = bindingView.etPayPwd.getText().toString().trim();
        String account = bindingView.etCustomrAccount.getText().toString().trim();
        String waresName = bindingView.etWaresName.getText().toString().trim();
        String waresNumber = bindingView.etWaresNum.getText().toString().trim();
        String redMoney = bindingView.etRedPay.getText().toString().trim();
        String money = bindingView.etMoneyPay.getText().toString().trim();

        if (TextUtils.isEmpty(payPwd) ||
                TextUtils.isEmpty(account) ||
                TextUtils.isEmpty(waresName) ||
                TextUtils.isEmpty(waresNumber)) {
            TU.cT("本页数据为必填");
            return;
        }

        String url = UrlConf.ConsumeRegistrationUrl;
        PostFormBuilder builder = OkHttpUtils.post().url(url).tag(netTag)
                .addParams("token", App.token)
                .addParams("shopId", shopId)
                .addParams("payPassword", payPwd)
                .addParams("account", account)
                .addParams("waresName", waresName)
                .addParams("waresNumber", waresNumber)
                .addParams("type", type + "");
        if (type != 1) {
            if (TextUtils.isEmpty(redMoney)) {
                TU.cT("请输入使用积分金额");
                return;
            }
            builder.addParams("redMoney", redMoney);
        }
        if (type != 2) {
            if (TextUtils.isEmpty(money)) {
                TU.cT("请输入使用现金金额");
                return;
            }
            builder.addParams("money", money);
        }
        WNetUtil.StringCallBack(builder
                , url, thisActivity, "提交登记中", true, new WNetUtil.WNetStringCallback() {
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


    int type = 1; //                1 现金支付3混合支付2积分支付
    private List<String> options1Items = new ArrayList<>();

    OptionsPickerView pvOptions;

    private void showPicker() {

        pvOptions = new OptionsPickerView.Builder(thisActivity, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {

                String tx = options1Items.get(options1);
                bindingView.tvType.setText(tx);

                switch (tx) {
                    case "现金支付":
                        type = 1;
                        bindingView.llRedPay.setVisibility(View.GONE);
                        bindingView.llRedFee.setVisibility(View.GONE);
                        bindingView.llMoneyPay.setVisibility(View.VISIBLE);
                        bindingView.llMoneyFee.setVisibility(View.VISIBLE);
                        break;
                    case "积分支付":
                        type = 2;
                        bindingView.llRedPay.setVisibility(View.VISIBLE);
                        bindingView.llRedFee.setVisibility(View.VISIBLE);
                        bindingView.llMoneyPay.setVisibility(View.GONE);
                        bindingView.llMoneyFee.setVisibility(View.GONE);
                        break;
                    case "混合支付":
                        type = 3;
                        bindingView.llRedPay.setVisibility(View.VISIBLE);
                        bindingView.llRedFee.setVisibility(View.VISIBLE);
                        bindingView.llMoneyPay.setVisibility(View.VISIBLE);
                        bindingView.llMoneyFee.setVisibility(View.VISIBLE);
                        break;
                }

            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("支付方式")//标题
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
        if (pvOptions != null && pvOptions.isShowing())
            pvOptions.dismiss();
        else
            super.onBackPressed();
    }
}
