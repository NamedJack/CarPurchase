package com.ejar.carpurchase.me.aty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ejar.carpurchase.AtyMainActivity;
import com.ejar.carpurchase.LoginActivity;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyBecomeMerchantSuccessBinding;

public class BecomeMerchantSuccessActivity extends BaseBindingActivity<AtyBecomeMerchantSuccessBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_become_merchant_success);
        AppCompatActivity thisActivity = this;
        Context mContext = this.getApplicationContext();

        boolean isChange = getIntent().getBooleanExtra("isChange", false);

        if (isChange) {
            bindingView.tvTitle.setText("修改成功");
            bindingView.btnRelogin.setText("返回个人中心");
        }

        bindingView.ivReturn.setOnClickListener(v -> {
            if (isChange) {
                startActivity(new Intent(thisActivity, AtyMainActivity.class));
            } else {
                Intent intent = new Intent(thisActivity, LoginActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        bindingView.btnRelogin.setOnClickListener(v -> {
            if (isChange) {
                startActivity(new Intent(thisActivity, AtyMainActivity.class));
            } else {
                Intent intent = new Intent(thisActivity, LoginActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

}
