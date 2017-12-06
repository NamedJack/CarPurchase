package com.ejar.carpurchase;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyForgetPwdBinding;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class ForgetPwdActivity extends BaseBindingActivity<AtyForgetPwdBinding> {

    private AppCompatActivity thisActivity;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_forget_pwd);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }

        thisActivity = this;
        mContext = this.getApplicationContext();

        bindingView.ivReturn.setOnClickListener(v -> onBackPressed());
        bindingView.btnReSetPwd.setIndeterminateProgressMode(true);
        bindingView.btnReSetPwd.setOnClickListener(v -> btnReSetPwdClick());
        bindingView.btnGetCode.setOnClickListener(v -> getPhoneCode());
    }

    private int time = 60;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (time <= 0) {
                time = 60;
                bindingView.btnGetCode.setText("获取验证码");
                bindingView.btnGetCode.setEnabled(true);
            } else {
                bindingView.btnGetCode.setText(time + "秒后重试");
                bindingView.btnGetCode.setEnabled(false);
                time--;
                mHandler.sendEmptyMessageDelayed(1, 1000);
            }
            return true;
        }
    });

    private void getPhoneCode() {
        if (time != 60) return;
        String phone = bindingView.etUsername.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            TU.cT("请输入手机号");
            return;
        }
        mHandler.sendEmptyMessage(1);
        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConf.GetCodeUrl)
                        .tag(netTag)
                        .addParams("phone", phone)
                        .addParams("setwer", 1 + "")
                , UrlConf.GetCodeUrl, thisActivity, "获取验证码中", false, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.optInt("code") != 200) {
                                time = 0;
                            }
                            TU.cT(jsonObject.optString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        Logger.e(e.getMessage());
                        time = 0;
                    }
                });
    }

    /**
     * 重置密码按钮
     */
    private void btnReSetPwdClick() {
        if (bindingView.btnReSetPwd.getProgress() == -1) {
            bindingView.btnReSetPwd.setProgress(0);
            return;
        }
        bindingView.btnReSetPwd.setProgress(50);
        String userName = bindingView.etUsername.getText().toString().trim();
        String pwd = bindingView.etPassword.getText().toString().trim();
        String rPwd = bindingView.etRepeatpassword.getText().toString().trim();
        String phoneCode = bindingView.etPhoneCode.getText().toString().trim();
        String code = bindingView.etCode.getText().toString().trim();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pwd)
                || TextUtils.isEmpty(rPwd)
                || TextUtils.isEmpty(phoneCode)
                || TextUtils.isEmpty(code)) {
//            TU.cT("本页面所有字段为必填");
            bindingView.btnReSetPwd.setProgress(-1);
            bindingView.btnReSetPwd.setErrorText("本页面所有字段为必填");
            return;
        }

        if (!pwd.equals(rPwd)) {
//            TU.cT("两次密码输入不一致");
            bindingView.btnReSetPwd.setProgress(-1);
            bindingView.btnReSetPwd.setErrorText("两次密码输入不一致");
            return;
        }

        if (!bindingView.verifycode.isEqualsIgnoreCase(code)) {
//            TU.cT("验证码错误");
            bindingView.btnReSetPwd.setProgress(-1);
            bindingView.btnReSetPwd.setErrorText("验证码错误");
            return;
        }
//        TU.cT("验证成功");


        Map<String, String> params = new HashMap<>();
        params.put("phone", userName);
        params.put("password", pwd);
        params.put("password2", rPwd);
        params.put("codel", phoneCode);

        WNetUtil.WNetBuild build = new WNetUtil.WNetBuild();
        build.tag(netTag)
                .activity(this)
                .url(UrlConf.ForgetPwdUrl)
                .method(WNetUtil.MT.post)
                .msg("重置密码中。。。")
                .params(params);

        WNetUtil.stringCallback(build, new WNetUtil.WNetStringCallback() {
            @Override
            public void success(String response, int id) {

                Logger.e(response);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);

                    if (jsonObject.optInt("code") == 200) {
                        bindingView.btnReSetPwd.setProgress(100);
                        TU.cT("修改成功,请登录。");
                        finish();
                    } else {
                        bindingView.btnReSetPwd.setProgress(-1);
                        bindingView.btnReSetPwd.setErrorText(jsonObject.optString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void error(Call call, Exception e, int id) {
                Logger.e(e.getMessage());
                bindingView.btnReSetPwd.setProgress(-1);
            }
        });
    }

    //################动画#################
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                bindingView.llContent.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(bindingView.llContent, bindingView.llContent.getWidth() / 2, 0,
                bindingView.tvTitle.getWidth() / 2, bindingView.llContent.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                bindingView.llContent.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(bindingView.llContent, bindingView.llContent.getWidth() / 2, 0,
                bindingView.llContent.getHeight(), bindingView.tvTitle.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                bindingView.llContent.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                ForgetPwdActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= 21) {
            animateRevealClose();
        } else super.onBackPressed();
    }
}
