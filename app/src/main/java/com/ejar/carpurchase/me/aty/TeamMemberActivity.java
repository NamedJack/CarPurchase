package com.ejar.carpurchase.me.aty;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ganxin.library.LoadDataLayout;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtySeeMenberBinding;
import com.ejar.carpurchase.me.bean.ChargeBean;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

public class TeamMemberActivity extends BaseBindingActivity<AtySeeMenberBinding> {


    private AppCompatActivity thisActivity;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_see_menber);
        thisActivity = this;
        mContext = this.getApplicationContext();
        initClick();
        initRecycleView();
        getList();
    }

    private void initClick() {
        bindingView.ivReturn.setOnClickListener(v -> finish());
    }

    private ArrayList<ChargeBean.RecordBean.RecordsBean> list;
    private RvAdapter adapter;

    private void initRecycleView() {
        list = new ArrayList<>();
        adapter = new RvAdapter();
        bindingView.rvSeePeople.setAdapter(adapter);
        bindingView.rvSeePeople.setLayoutManager(new LinearLayoutManager(mContext));

        bindingView.srlSeePeople.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getList();
            }
        });
    }


    private void getList() {

        bindingView.clLoaddata.setOnReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                getList();
            }
        });
        Logger.e(App.token);
        String url = UrlConf.Team_member_URL;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url)
                        .addParams("token", App.token)
                , url, thisActivity, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        bindingView.srlSeePeople.setRefreshing(false);
                        Logger.e(response);
                        Gson gson = new Gson();
                        TeamMemberBean bean = gson.fromJson(response, TeamMemberBean.class);
                        if (bean.getCode() == 200) {
                            bindingView.clLoaddata.setStatus(LoadDataLayout.SUCCESS);
                            bindingView.srlSeePeople.setRefreshing(false);
                            if (bean.getUserxmm().size() == 0) {
                                bindingView.clLoaddata.setStatus(LoadDataLayout.EMPTY);
                            }
                            adapter.setNewData(bean.getUserxmm());

                            bindingView.tvListSize.setText("共 " + adapter.getData().size() + " 个用户");
                        } else {

                            bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                        }

                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        bindingView.srlSeePeople.setRefreshing(false);
                        bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                    }
                });
    }

    class RvAdapter extends BaseQuickAdapter<TeamMemberBean.UserxmmBean, BaseViewHolder> {
        public RvAdapter() {
            super(R.layout.item_rv_see_people);
        }

        @Override
        protected void convert(BaseViewHolder helper, TeamMemberBean.UserxmmBean item) {
            helper.setText(R.id.tv_name, "真实姓名: " + item.getName() + "")
                    .setText(R.id.tv_id, "账号: " + item.getAccount() + "");

        }


    }

    static class TeamMemberBean {

        /**
         * msg : 查看成功!
         * code : 200
         * userxmm : [{"id":23,"account":"15351252592","password":null,"operate":null,"name":"15351252592","parentId":null,"parentName":null,"ratio":null,"phone":null,"uniqueId":null,"idCard":null,"totalmoney":null,"money":null,"offlineAllRebate":null,"offlineRebate":null,"allMoney":null,"red":null,"redAllMoney":null,"redMoney":null,"redMoneyts":null,"pileMoney":null,"state":5,"topUser":null,"payPassword":null,"email":null,"qq":null,"province":null,"city":null,"provinceAdcode":null,"cityAdcode":null,"county":null,"street":null,"countyAdcode":null,"streetAdcode":null,"detailedAddress":null,"userHead":null,"qrcode":null,"time":null,"angentAllMoney":null,"angentMoney":null,"angentAllMoney2":null,"angentMoney2":null},{"id":103,"account":"13198418693","password":null,"operate":null,"name":"13198418693","parentId":null,"parentName":null,"ratio":null,"phone":null,"uniqueId":null,"idCard":null,"totalmoney":null,"money":null,"offlineAllRebate":null,"offlineRebate":null,"allMoney":null,"red":null,"redAllMoney":null,"redMoney":null,"redMoneyts":null,"pileMoney":null,"state":5,"topUser":null,"payPassword":null,"email":null,"qq":null,"province":null,"city":null,"provinceAdcode":null,"cityAdcode":null,"county":null,"street":null,"countyAdcode":null,"streetAdcode":null,"detailedAddress":null,"userHead":null,"qrcode":null,"time":null,"angentAllMoney":null,"angentMoney":null,"angentAllMoney2":null,"angentMoney2":null}]
         */

        private String msg;
        private int code;
        private List<UserxmmBean> userxmm;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public List<UserxmmBean> getUserxmm() {
            return userxmm;
        }

        public void setUserxmm(List<UserxmmBean> userxmm) {
            this.userxmm = userxmm;
        }

        public static class UserxmmBean {
            /**
             * id : 23
             * account : 15351252592
             * password : null
             * operate : null
             * name : 15351252592
             * parentId : null
             * parentName : null
             * ratio : null
             * phone : null
             * uniqueId : null
             * idCard : null
             * totalmoney : null
             * money : null
             * offlineAllRebate : null
             * offlineRebate : null
             * allMoney : null
             * red : null
             * redAllMoney : null
             * redMoney : null
             * redMoneyts : null
             * pileMoney : null
             * state : 5
             * topUser : null
             * payPassword : null
             * email : null
             * qq : null
             * province : null
             * city : null
             * provinceAdcode : null
             * cityAdcode : null
             * county : null
             * street : null
             * countyAdcode : null
             * streetAdcode : null
             * detailedAddress : null
             * userHead : null
             * qrcode : null
             * time : null
             * angentAllMoney : null
             * angentMoney : null
             * angentAllMoney2 : null
             * angentMoney2 : null
             */

            private int id;
            private String account;
            private String name;
            private int state;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }
        }
    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（14660689144）输出（"2016年6月16日 17时21分54秒"）
     *
     * @param time
     * @return
     */
    public String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc));
        return times;
    }
}
