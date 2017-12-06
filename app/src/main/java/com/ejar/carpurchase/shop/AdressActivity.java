package com.ejar.carpurchase.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.base.rx.RxBus;
import com.ejar.carpurchase.base.rx.RxEventCodeType;
import com.ejar.carpurchase.databinding.AtyAdressBinding;
import com.ejar.carpurchase.unit.bean.UnitBean;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class AdressActivity extends BaseBindingActivity<AtyAdressBinding> {

    private AppCompatActivity thisActivity;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_adress);
        thisActivity = this;
        mContext = this.getApplicationContext();

        bindingView.ivReturn.setOnClickListener(v -> finish());

        initRecycleview();

        bindingView.tvAddAdress.setOnClickListener(v -> {
            Intent i = new Intent(thisActivity, AddAdressActivity.class);

            startActivity(i);
        });

        bindingView.srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getList();
            }
        });
    }

    private ArrayList<UnitBean.DataBean.ShopBean> list;
    private RvAdapter adapter;

    private void initRecycleview() {
        list = new ArrayList<>();
        adapter = new RvAdapter();
        bindingView.rv.setAdapter(adapter);
        bindingView.rv.setLayoutManager(new LinearLayoutManager(mContext));

        adapter.setLisener(map -> {
            Intent i = new Intent(thisActivity, AddAdressActivity.class);
            for (String k : map.keySet()) {
                i.putExtra(k, map.get(k));
            }
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList();
    }

    private void getList() {
        String url = UrlConf.AdressList_URL;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url).addParams("token", App.token)
                , url, thisActivity, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        bindingView.srl.setRefreshing(false);
                        AdressBean adressBean = new Gson().fromJson(response, AdressBean.class);
                        try {
                            adapter.setNewData(adressBean.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        bindingView.srl.setRefreshing(false);
                    }
                });
    }


    public static class AdressBean {

        /**
         * code : 200
         * msg : OK
         * data : [{"id":1,"account":"311","name":"是电讯形成","address":"多喝点多喝点多喝点多喝点多喝点","zip":null,"tel":"1111111111","mobile":"4545","isdefault":1,"province":"多喝点","city":"多喝点","area":"多喝点","email":null},{"id":2,"account":"311","name":"肖坤","address":"阿升大三大四的啊实打实阿萨德啊","zip":null,"tel":"123456789","mobile":null,"isdefault":0,"province":"四欻","city":"斯蒂芬","area":"阿瑟","email":null}]
         */

        private int code;
        private String msg;
        private List<DataBean> data;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 1
             * account : 311
             * name : 是电讯形成
             * address : 多喝点多喝点多喝点多喝点多喝点
             * zip : null
             * tel : 1111111111
             * mobile : 4545
             * isdefault : 1
             * province : 多喝点
             * city : 多喝点
             * area : 多喝点
             * email : null
             */

            private int id;
            private String account;
            private String name;
            private String address;
            private Object zip;
            private String tel;
            private String mobile;
            private int isdefault;
            private String province;
            private String city;
            private String area;
            private Object email;

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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public Object getZip() {
                return zip;
            }

            public void setZip(Object zip) {
                this.zip = zip;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public int getIsdefault() {
                return isdefault;
            }

            public void setIsdefault(int isdefault) {
                this.isdefault = isdefault;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }
        }
    }

    private static class RvAdapter extends BaseQuickAdapter<AdressBean.DataBean, BaseViewHolder> {
        public RvAdapter() {
            super(R.layout.item_rv_adress);
        }

        private onLisener lisener;

        public void setLisener(onLisener lisener) {
            this.lisener = lisener;
        }

        @Override
        protected void convert(BaseViewHolder helper, AdressBean.DataBean item) {
            String provice = TextUtils.isEmpty(item.getProvince()) ? "" : item.getProvince();
            String city = TextUtils.isEmpty(item.getCity()) ? "" : item.getCity();
            String area = TextUtils.isEmpty(item.getArea()) ? "" : item.getArea();
            String adress = TextUtils.isEmpty(item.getAddress()) ? "" : item.getAddress();
            String name = TextUtils.isEmpty(item.getName()) ? "" : item.getName();
            String tel = TextUtils.isEmpty(item.getTel()) ? "" : item.getTel();


            String location = provice + " " + city + " " + area + " " + adress;


            helper.setText(R.id.tv_name, "姓名：" + name)
                    .setText(R.id.tv_phone, "手机号：" + tel)
                    .setText(R.id.tv_adress, location);


            ImageView iv = helper.getView(R.id.iv_check);
            if (item.getIsdefault() != 1) {
                iv.setImageResource(R.mipmap.download_un);
            } else {
                iv.setImageResource(R.mipmap.download);
                RxBus.getDefault().post(RxEventCodeType.ORDER_CONFIRM_ADRESS_ID, item);
            }

            iv.setOnClickListener(v -> {
                //请求接口 修改默认

                OkHttpUtils.post().url(UrlConf.SetDefault_URL)
                        .addParams("token", App.token)
                        .addParams("id", item.getId() + "")
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        TU.cT("设置默认地址失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            Logger.e(response);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 200) {
                                for (AdressBean.DataBean a : getData()) {
                                    if (a.equals(item))
                                        a.isdefault = 1;
                                    else
                                        a.isdefault = 0;
                                }
                                notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            });

            TextView tvEdit = helper.getView(R.id.tv_edit);
            TextView tvDelete = helper.getView(R.id.tv_delete);
            tvEdit.setOnClickListener(v -> {
                Map<String, String> map = new HashMap<String, String>();
                map.put("provice", provice);
                map.put("city", city);
                map.put("area", area);
                map.put("name", name);
                map.put("tel", tel);
                map.put("id", item.getId() + "");
                if (lisener != null) {
                    lisener.onclick(map);
                }
            });

            tvDelete.setOnClickListener(v -> {
                OkHttpUtils.post().url(UrlConf.DeleteAdress_URL)
                        .addParams("token", App.token)
                        .addParams("id", item.getId() + "")
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        TU.cT("删除失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 200) {
                                getData().remove(item);
                                notifyItemRemoved(helper.getLayoutPosition());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            });
        }

        interface onLisener {
            void onclick(Map<String, String> map);
        }

    }
}
