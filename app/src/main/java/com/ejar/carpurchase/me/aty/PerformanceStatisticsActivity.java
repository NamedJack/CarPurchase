package com.ejar.carpurchase.me.aty;

import android.os.Bundle;

import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyPerformanceStatisticsBinding;

/**
 * 业绩统计
 */
public class PerformanceStatisticsActivity extends BaseBindingActivity<AtyPerformanceStatisticsBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_performance_statistics);

        bindingView.ivReturn.setOnClickListener(v -> finish());
    }
}
