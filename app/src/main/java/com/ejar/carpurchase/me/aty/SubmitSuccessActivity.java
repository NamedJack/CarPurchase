package com.ejar.carpurchase.me.aty;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtySubmitSuccessBinding;

public class SubmitSuccessActivity extends BaseBindingActivity<AtySubmitSuccessBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_submit_success);
        AppCompatActivity thisActivity = this;
        Context mContext = this.getApplicationContext();

        bindingView.ivReturn.setOnClickListener(v -> finish());

        bindingView.btnViewRegistration.setOnClickListener(v -> {

        });
    }
}
