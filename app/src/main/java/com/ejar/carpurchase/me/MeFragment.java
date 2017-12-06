package com.ejar.carpurchase.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ganxin.library.LoadDataLayout;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingFragment;
import com.ejar.carpurchase.databinding.FgtMeBinding;
import com.ejar.carpurchase.me.aty.AgentEarningsActivity;
import com.ejar.carpurchase.me.aty.AgentSeePeopleActivity;
import com.ejar.carpurchase.me.aty.BecomeMerchantActivity;
import com.ejar.carpurchase.me.aty.BillFormActivity;
import com.ejar.carpurchase.me.aty.BindCardActivity;
import com.ejar.carpurchase.me.aty.ChargeManageActivity;
import com.ejar.carpurchase.me.aty.ConsumeInfoActivity;
import com.ejar.carpurchase.me.aty.ConsumeRegistrationActivity;
import com.ejar.carpurchase.me.aty.MyShareActivity;
import com.ejar.carpurchase.me.aty.OrderCenterActivity;
import com.ejar.carpurchase.me.aty.PerformanceStatisticsActivity;
import com.ejar.carpurchase.me.aty.Person_Info_Activity;
import com.ejar.carpurchase.me.aty.SettingActivity;
import com.ejar.carpurchase.me.aty.TeamMemberActivity;
import com.ejar.carpurchase.me.aty.TeamSeePeopleActivity;
import com.ejar.carpurchase.me.aty.TransferRecordActivity;
import com.ejar.carpurchase.me.aty.WithDrawActivity;
import com.ejar.carpurchase.me.bean.UserDataBean;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.glide.GlideCircleTransform;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;


/**
 * Created by wxd1 on 2017/5/25.
 */

public class MeFragment extends BaseBindingFragment<FgtMeBinding> {


    private String detailAdress;

    @Override
    public int setContent() {
        return R.layout.fgt_me;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String title = getArguments().getString("title");
        bindingView.setTitle(title);
        initClick();
    }

    private void initClick() {
        bindingView.clLoaddata.setOnReloadListener((v, status) -> getUserData());
        bindingView.srlMe.setOnRefreshListener(this::getUserData);

        bindingView.ivShare.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MyShareActivity.class);
            Bundle userInfo = new Bundle();
            userInfo.putString("img", TextUtils.isEmpty(userDataBean.getUser().getUserHead()) ? "" : userDataBean.getUser().getUserHead());//头像
            userInfo.putString("nickName", userDataBean.getUser().getName());
            userInfo.putString("prvince", prvince);
            userInfo.putInt("type", userDataBean.getUser().getState());
            userInfo.putString("city", city);
            userInfo.putString("county", county);
            userInfo.putString("street", street);
            intent.putExtras(userInfo);
            startActivity(intent);
        });

        bindingView.ivAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Person_Info_Activity.class);
            Bundle userInfo = new Bundle();
            userInfo.putString("img", TextUtils.isEmpty(userDataBean.getUser().getUserHead()) ? "" : userDataBean.getUser().getUserHead());//头像
            userInfo.putString("nickName", userDataBean.getUser().getName());
            userInfo.putString("email", userDataBean.getUser().getEmail());
            userInfo.putString("prvince", prvince);
            userInfo.putString("city", city);
            userInfo.putString("county", county);
            userInfo.putString("street", street);
            userInfo.putString("detailAdress", detailAdress);
            userInfo.putString("qq", userDataBean.getUser().getQq());
            userInfo.putString("phone", userDataBean.getUser().getPhone());
            intent.putExtras(userInfo);
            startActivity(intent);
        });

        bindingView.ivSetting.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            Bundle userInfo = new Bundle();
            userInfo.putString("img", TextUtils.isEmpty(userDataBean.getUser().getUserHead()) ? "" : userDataBean.getUser().getUserHead());//头像
            userInfo.putString("nickName", userDataBean.getUser().getName());
            userInfo.putString("email", userDataBean.getUser().getEmail());
            userInfo.putString("prvince", prvince);
            userInfo.putString("city", city);
            userInfo.putString("county", county);
            userInfo.putString("street", street);
            userInfo.putString("detailAdress", detailAdress);
            userInfo.putString("qq", userDataBean.getUser().getQq());
            userInfo.putString("phone", userDataBean.getUser().getPhone());
            intent.putExtras(userInfo);
            startActivity(intent);
        });

        bindingView.llCard.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), BindCardActivity.class);
            if (userDataBean.getUpaybinding() != null) {
                intent.putExtra("isHadCard", true);
            }
            startActivity(intent);
        });

        bindingView.llBecomeShop.setOnClickListener(v -> startActivity(new Intent(getActivity(), BecomeMerchantActivity.class)));

        bindingView.llChargeManage.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), ChargeManageActivity.class);
            Bundle b = new Bundle();
            b.putString("money", userDataBean.getUser().getMoney() + "");
            b.putString("phone", userDataBean.getUser().getPhone() + "");
            String trueName = null;
            try {
                trueName = userDataBean.getUpaybinding().getName() + "";
            } catch (Exception e) {
                e.printStackTrace();
                trueName = null;
            }
            if (TextUtils.isEmpty(trueName)) {
                TU.cT("未绑定银行卡，无法进行操作");
            } else {
                b.putString("trueName", trueName);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        bindingView.llWithdraw.setOnClickListener(v -> startActivity(new Intent(getActivity(), WithDrawActivity.class)));

        bindingView.llTransferRecord.setOnClickListener(v -> startActivity(new Intent(getActivity(), TransferRecordActivity.class)));

        bindingView.llPreformanceStatistic.setOnClickListener(v -> startActivity(new Intent(getActivity(), PerformanceStatisticsActivity.class)));


        /**
         * 订单中心
         */
        bindingView.llOrderCenter.setOnClickListener(v -> startActivity(new Intent(getActivity(), OrderCenterActivity.class)));

        bindingView.llOrderNotpay.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), OrderCenterActivity.class);
            i.putExtra("gotoPage", 1);
            startActivity(i);
        });


        bindingView.llOrderNotconfirm.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), OrderCenterActivity.class);
            i.putExtra("gotoPage", 2);
            startActivity(i);
        });


        bindingView.llOrderComplite.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), OrderCenterActivity.class);
            i.putExtra("gotoPage", 3);
            startActivity(i);
        });


        //报单业绩
        bindingView.llBillForm.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), BillFormActivity.class);
            try {
                intent.putExtra("shopId", App.shopId + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(intent);
        });

        //消费登记
        bindingView.llConsumeAdd.setOnClickListener(v -> {
            String trueName = null;
            try {
                trueName = userDataBean.getUpaybinding().getName() + "";
            } catch (Exception e) {
                e.printStackTrace();
                trueName = null;
            }
            if (TextUtils.isEmpty(trueName)) {
                TU.cT("未绑定银行卡，无法进行操作");
                return;
            }
            Intent intent = new Intent(getActivity(), ConsumeRegistrationActivity.class);
            try {
                intent.putExtra("shopId", App.shopId + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(intent);
        });

        bindingView.llConsumeInfo.setOnClickListener(v -> startActivity(new Intent(getActivity(), ConsumeInfoActivity.class)));


    }

    @Override
    protected void loadData() {
        // TODO: 2017/7/28 测试，后期删除
//        if (LOG_DEBUG) {
//            bindingView.clLoaddata.setStatus(LoadDataLayout.SUCCESS);
//            return;
//        }
        getUserData();
    }

    private UserDataBean userDataBean;
    String prvince, city, county, street;

    private void getUserData() {

        bindingView.clLoaddata.setStatus(LoadDataLayout.LOADING);
        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConf.UserDataUrl)
                        .tag(netTag).addParams("token", App.token)
                , UrlConf.UserDataUrl, (AppCompatActivity) getActivity(), new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        bindingView.srlMe.setRefreshing(false);
                        Logger.e(response);
                        Gson gson = new Gson();
                        userDataBean = gson.fromJson(response, UserDataBean.class);

                        if (userDataBean != null && userDataBean.getCode().equals("200")) {
                            userDataBean.getUser().setUserHead(UrlConf.IMGHOST + userDataBean.getUser().getUserHead());
                            bindingView.clLoaddata.setStatus(LoadDataLayout.SUCCESS);
                            if (!TextUtils.isEmpty(userDataBean.getUser().getUserHead())) {
                                Glide.with(getContext().getApplicationContext())
                                        .load(userDataBean.getUser().getUserHead())
                                        .transform(new GlideCircleTransform(getContext().getApplicationContext()))
                                        .into(bindingView.ivAvatar);
                            }
                            prvince = userDataBean.getUser().getProvince();
                            city = userDataBean.getUser().getCity();
                            county = userDataBean.getUser().getCounty();
                            street = userDataBean.getUser().getStreet();


                            street = TextUtils.isEmpty(street) ? "" : street;
                            county = TextUtils.isEmpty(county) ? "" : county;
                            city = TextUtils.isEmpty(city) ? "" : city;
                            prvince = TextUtils.isEmpty(prvince) ? "" : prvince;

                            detailAdress = userDataBean.getUser().getDetailedAddress();


                            if (TextUtils.isEmpty(street)) {
                                userDataBean.getUser().setStreet("");
                            }
                            if (TextUtils.isEmpty(userDataBean.getUser().getCity()))
                                userDataBean.getUser().setCity("未知城市");

                            if (TextUtils.isEmpty(userDataBean.getUser().getStreet()))
                                userDataBean.getUser().setStreet("");


                            if (TextUtils.isEmpty(county))
                                userDataBean.getUser().setCounty("");

                            if (TextUtils.isEmpty(prvince) || TextUtils.isEmpty(city) || TextUtils.isEmpty(county)) {
                                TU.cT("请完善地址信息");
                                Intent intent = new Intent(getActivity(), Person_Info_Activity.class);
                                Bundle userInfo = new Bundle();
                                userInfo.putString("img", TextUtils.isEmpty(userDataBean.getUser().getUserHead()) ? ""
                                        : userDataBean.getUser().getUserHead());//头像
                                userInfo.putString("nickName", userDataBean.getUser().getName());
                                userInfo.putString("email", userDataBean.getUser().getEmail());
                                userInfo.putString("prvince", prvince);
                                userInfo.putString("city", city);
                                userInfo.putString("county", county);
                                userInfo.putString("street", street);
                                userInfo.putString("detailAdress", detailAdress);
                                userInfo.putString("qq", userDataBean.getUser().getQq());
                                userInfo.putString("phone", userDataBean.getUser().getPhone());
                                intent.putExtras(userInfo);
                                startActivity(intent);
                            }
                            App.userBean = userDataBean.getUser();
                            App.upaybindingBean = userDataBean.getUpaybinding();
                            bindingView.setUser(userDataBean.getUser());
                            //没有店铺信息，应该就是隐藏店铺了。
//                            if (userDataBean.getEntityshop() == null)
//                                bindingView.llMyShop.setVisibility(View.GONE);
//                            else bindingView.llMyShop.setVisibility(View.VISIBLE);

                            //判断是是用户还是 店家
                            if (userDataBean.getUser().getState() == 1) {
                                bindingView.llBecomeShop.setVisibility(View.GONE);
                                bindingView.llShopUtil.setVisibility(View.GONE);
                                bindingView.llAgentUtil.setVisibility(View.GONE);
                                bindingView.llMyShop.setVisibility(View.GONE);
                                bindingView.tvLocation.setVisibility(View.GONE);

                                bindingView.tvMenberType.setText("普通用户");
                                bindingView.tvMenberType.setBackground(getResources().getDrawable(R.drawable.shape_remark));
                                bindingView.llHuokuan.setVisibility(View.GONE);
                                bindingView.llCommission.setVisibility(View.GONE);
                                bindingView.llTeamOption.setVisibility(View.GONE);
//                                if (userDataBean.getEntityshop() != null) {
//                                    if (!TextUtils.isEmpty(userDataBean.getEntityshop().getShopName()))
//                                        handleShopInfo(userDataBean.getEntityshop());
//                                } else {//没有申请店铺的状态
//                                    bindingView.tvBecomeShopState.setText("");
//                                    shopIntent = new Intent(getActivity(), BecomeMerchantActivity.class);
//                                    bindingView.llMyShop.setVisibility(View.GONE);
//                                    bindingView.llBecomeShop.setVisibility(View.VISIBLE);
//                                    bindingView.llBecomeShop.setOnClickListener(v -> startActivity(shopIntent));
//                                }


                            } else if (userDataBean.getUser().getState() == 4) { //代理商
                                bindingView.llHuokuan.setVisibility(View.GONE);
                                bindingView.llCommission.setVisibility(View.VISIBLE);
                                bindingView.tvLocation.setVisibility(View.VISIBLE);
                                bindingView.llMyShop.setVisibility(View.GONE);
                                bindingView.tvMenberType.setText("代理商");
                                bindingView.llTeamOption.setVisibility(View.GONE);
                                bindingView.llShopUtil.setVisibility(View.GONE);
                                bindingView.llAgentUtil.setVisibility(View.VISIBLE);
                                bindingView.llBecomeShop.setVisibility(View.GONE);
                                bindingView.llSeePeople.setOnClickListener(v -> {
                                    Intent i = new Intent(getActivity(), AgentSeePeopleActivity.class);

                                    startActivity(i);
                                });

                                bindingView.llEarnings.setOnClickListener(v -> {
                                    Intent i = new Intent(getActivity(), AgentEarningsActivity.class);

                                    startActivity(i);
                                });

                                try {
                                    bindingView.tvLocation.setText(userDataBean.getUser().getParentName() + "");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                String commission = userDataBean.getUser().getAngentMoney() + "";
                                bindingView.tvCommission.setText(TextUtils.isEmpty(commission) ? "0" : commission);

                            } else if (userDataBean.getUser().getState() == 5) {
                                bindingView.llBecomeShop.setVisibility(View.GONE);
                                bindingView.llShopUtil.setVisibility(View.GONE);
                                bindingView.llMyShop.setVisibility(View.GONE);

                                bindingView.tvMenberType.setText("团队");
                                bindingView.tvMenberType.setBackground(getResources().getDrawable(R.drawable.shape_remark));
                                bindingView.llHuokuan.setVisibility(View.GONE);
                                bindingView.llCommission.setVisibility(View.GONE);
                                bindingView.llTeamOption.setVisibility(View.VISIBLE);
                                bindingView.llTeamUtil.setVisibility(View.VISIBLE);
                                bindingView.llAgentUtil.setVisibility(View.GONE);
                                bindingView.tvLocation.setVisibility(View.GONE);
                                bindingView.llTeamEarnings.setOnClickListener((v) -> {
                                    Intent i = new Intent(getActivity(), AgentEarningsActivity.class);
                                    i.putExtra("isTeam", true);
                                    startActivity(i);
                                });

                                bindingView.llTeamSeePeople.setOnClickListener(v -> {
                                    Intent i = new Intent(getActivity(), TeamSeePeopleActivity.class);

                                    startActivity(i);
                                });

                                bindingView.llTeamMember.setOnClickListener(v -> {
                                    Intent i = new Intent(getActivity(), TeamMemberActivity.class);

                                    startActivity(i);
                                });


                                //做判断 团队提成  团员提成查看
                                App.memberTiChen = 0;
                                try {
                                    int userId = userDataBean.getUser().getId();
                                    if (userDataBean.getTimx() != null) {
                                        for (UserDataBean.TeamBean t : userDataBean.getTimx()) {
                                            double teamMoney = t.getTeamMoney();
                                            if (userId == t.getUserID()) {
                                                App.memberTiChen = teamMoney;
                                                bindingView.tvTeamTicheng.setText(teamMoney + "");
                                            } else {
                                                bindingView.tvTeamMoney.setText(teamMoney + "");
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
//                            else { //商家
//                                bindingView.llCommission.setVisibility(View.GONE);
//                                bindingView.llHuokuan.setVisibility(View.VISIBLE);
//                                bindingView.llMyShop.setVisibility(View.VISIBLE);
//                                bindingView.llBecomeShop.setVisibility(View.GONE);
//                                bindingView.llShopUtil.setVisibility(View.VISIBLE);
//                                App.turnOver = userDataBean.getEntityshop().getTurnover() + "";
//                                bindingView.tvHuokuan.setText(TextUtils.isEmpty(App.turnOver) ? "0" : App.turnOver);
//                                handleShopInfo(userDataBean.getEntityshop());
//                            }

                        } else {
                            bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        bindingView.srlMe.setRefreshing(false);
                        bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                    }
                }
        );
    }

//    Intent shopIntent;
//
//    private void handleShopInfo(UserDataBean.EntityshopBean entityshopBean) {
//        if (entityshopBean == null) return;
//        App.shopId = entityshopBean.getId();
//        Bundle bundle = new Bundle();
//        switch (entityshopBean.getState()) {
//            //1为待审核，2为通过，3未通过
//            case 1:
//                bindingView.tvBecomeShopState.setText("待审核");
//                shopIntent = new Intent(getActivity(), BecomeMerchantActivity.class);
//                bundle.putSerializable("shopInfo", entityshopBean);
//                bundle.putInt("type", 1); // 1 撤销申请   2修改后重新提交
//                shopIntent.putExtras(bundle);
//                bindingView.llMyShop.setVisibility(View.GONE);
//                bindingView.llBecomeShop.setVisibility(View.VISIBLE);
//                bindingView.llBecomeShop.setOnClickListener(v -> startActivity(shopIntent));
//                break;
//            case 2:
//                bindingView.tvBecomeShopState.setText("通过");
//                shopIntent = new Intent(getActivity(), MyShopActivity.class);
//                bundle.putSerializable("shopInfo", entityshopBean);
//                shopIntent.putExtras(bundle);
//                bindingView.llMyShop.setOnClickListener(v -> startActivity(shopIntent));
//                break;
//            case 3:
//                bindingView.tvBecomeShopState.setText("未通过");
//                shopIntent = new Intent(getActivity(), BecomeMerchantActivity.class);
//                bundle.putSerializable("shopInfo", entityshopBean);
//                bundle.putInt("type", 2); // 1 撤销申请   2修改后重新提交
//                shopIntent.putExtras(bundle);
//                bindingView.llMyShop.setVisibility(View.GONE);
//                bindingView.llBecomeShop.setVisibility(View.VISIBLE);
//                bindingView.llBecomeShop.setOnClickListener(v -> startActivity(shopIntent));
//                break;
//        }
//
//    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: 2017/7/28 测试，后期删除
//        if (LOG_DEBUG) {
//            bindingView.clLoaddata.setStatus(LoadDataLayout.SUCCESS);
//            return;
//        }
        getUserData();

    }

}
