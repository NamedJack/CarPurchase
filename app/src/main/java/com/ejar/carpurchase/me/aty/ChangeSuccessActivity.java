package com.ejar.carpurchase.me.aty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ejar.carpurchase.AtyMainActivity;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyChangeSuccessBinding;

public class ChangeSuccessActivity extends BaseBindingActivity<AtyChangeSuccessBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_change_success);
        AppCompatActivity thisActivity = this;
        Context mContext = this.getApplicationContext();

        bindingView.ivReturn.setOnClickListener(v -> finish());

        bindingView.btnReturnPersonCenter.setOnClickListener(v->startActivity(new Intent(thisActivity, AtyMainActivity.class)));
    }
}
