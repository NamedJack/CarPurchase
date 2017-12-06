package com.ejar.carpurchase.me.aty;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.animation.Attention.Tada;
import com.flyco.dialog.widget.NormalDialog;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyOrderDetailBinding;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;

public class OrderDetailActivity extends BaseBindingActivity<AtyOrderDetailBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_order_detail);

        bindingView.ivReturn.setOnClickListener(v -> finish());

        doInit();
    }

    private void doInit() {
        String id = getIntent().getStringExtra("id");
        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConf.OrderDetail_URL)
                        .addParams("token", App.token)
                        .addParams("id", id)
                , UrlConf.OrderDetail_URL, this, "获取订单详情中", true, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        OrderDetailBean orderdetailBean = new Gson().fromJson(response, OrderDetailBean.class);
                        if (orderdetailBean.getCode() == 200) {

                            String orderId = orderdetailBean.getData().getId() + "";

                            String state = "", stateDes = "";
                            switch (orderdetailBean.getData().getStatus()) {
                                case 1:
                                    state = "待付款";
                                    stateDes = "订单还未付款，付款后商家发货";
                                    bindingView.ivState.setImageResource(R.mipmap.unpay_un);

                                    bindingView.btnLeft.setVisibility(View.VISIBLE);
                                    bindingView.btnRight.setVisibility(View.VISIBLE);
                                    bindingView.btnLeft.setText("取消订单");
                                    bindingView.btnRight.setText("去付款");
                                    bindingView.btnLeft.setOnClickListener(v -> doCancel(orderId));
                                    bindingView.btnRight.setOnClickListener(v -> doPaynow(orderId, orderdetailBean.getData().getMoney()));
                                    break;
                                case 2:
                                    state = "待发货";
                                    stateDes = "商家还未发货，请耐心等待";
                                    bindingView.ivState.setImageResource(R.mipmap.unsure_un);

                                    bindingView.btnLeft.setVisibility(View.GONE);
                                    bindingView.btnRight.setVisibility(View.GONE);
                                    break;
                                case 3:
                                    state = "待收货";
                                    stateDes = "商家已发货，请注意查收";
                                    bindingView.ivState.setImageResource(R.mipmap.unsure_un);

                                    bindingView.btnLeft.setVisibility(View.GONE);
                                    bindingView.btnRight.setVisibility(View.VISIBLE);
                                    bindingView.btnRight.setText("确认收货");
                                    bindingView.btnRight.setOnClickListener(v -> doConfirm(orderId));
                                    break;
                                case 4:
                                    state = "已完成";
                                    stateDes = "订单已完成";
                                    bindingView.ivState.setImageResource(R.mipmap.unsure_un);

                                    bindingView.btnLeft.setVisibility(View.VISIBLE);
                                    bindingView.btnRight.setVisibility(View.GONE);
                                    bindingView.btnLeft.setText("删除订单");
                                    bindingView.btnLeft.setOnClickListener(v -> doDeleteOrder(orderId));
                                    break;
                            }
                            bindingView.tvState.setText(state);
                            bindingView.tvStateDes.setText(stateDes);

                            if (orderdetailBean.getData().getIsCancel() == 1) {
                                bindingView.tvState.setText("交易关闭");
                                bindingView.tvStateDes.setText("订单已取消，交易失败");
                                bindingView.rlState.setBackgroundColor(Color.GRAY);
                                bindingView.ivState.setImageResource(R.mipmap.trans_close);

                                bindingView.btnLeft.setVisibility(View.VISIBLE);
                                bindingView.btnRight.setVisibility(View.GONE);
                                bindingView.btnLeft.setText("删除订单");
                                bindingView.btnLeft.setOnClickListener(v -> doDeleteOrder(orderId));
                            }


                            OrderDetailBean.DataBean.AddressBean addressBean = orderdetailBean.getData().getAddress();
                            List<OrderDetailBean.DataBean.OrderdetailBean> orderdetailBeanList = orderdetailBean.getData().getOrderdetail();


                            String provice = TextUtils.isEmpty(addressBean.getProvince()) ? "" : addressBean.getProvince();
                            String city = TextUtils.isEmpty(addressBean.getCity()) ? "" : addressBean.getCity();
                            String area = TextUtils.isEmpty(addressBean.getArea()) ? "" : addressBean.getArea();
                            String adress = TextUtils.isEmpty(addressBean.getAddress()) ? "" : addressBean.getAddress();
                            String name = TextUtils.isEmpty(addressBean.getName()) ? "" : addressBean.getName();
                            String tel = TextUtils.isEmpty(addressBean.getTel()) ? "" : addressBean.getTel();

                            String location = provice + " " + city + " " + area + " " + adress;

                            bindingView.tvPeople.setText(name);
                            bindingView.tvPhone.setText(tel);
                            bindingView.tvAdress.setText(location);


                            bindingView.tvTransFee.setText(orderdetailBean.getData().getFee() + "");
                            bindingView.tvRed.setText("  使用积分："+orderdetailBean.getData().getScore() + "");


                            //支付方式：1，支付宝；2:微信；3：余额
                            bindingView.llPay.setVisibility(View.VISIBLE);
                            if (orderdetailBean.getData().getPayMethod() == 1) {
                                bindingView.ivPayType.setImageResource(R.mipmap.ailpay_pay);
                                bindingView.tvPayType.setText("支付宝支付");
                            } else if (orderdetailBean.getData().getPayMethod() == 2) {
                                bindingView.ivPayType.setImageResource(R.mipmap.wechat_pay);
                                bindingView.tvPayType.setText("微信支付");
                            } else if (orderdetailBean.getData().getPayMethod() == 3) {
                                bindingView.ivPayType.setImageResource(R.mipmap.card);
                                bindingView.tvPayType.setText("积分支付");
                            } else {
                                bindingView.llPay.setVisibility(View.GONE);
                            }

                            bindingView.tvMoney.setText(safeText(orderdetailBean.getData().getMoney() + ""));

                            bindingView.tvOrderTime.setText("下单时间: " + safeText(orderdetailBean.getData().getCreatTime()));
                            bindingView.tvPayTime.setText("付款时间: " + safeText(orderdetailBean.getData().getPayTime()));
                            bindingView.tvTransTime.setText("发货时间: " + safeText(orderdetailBean.getData().getSendTime()));
                            bindingView.tvCompliteTime.setText("完成时间: " + safeText(orderdetailBean.getData().getConfirmTime()));

                            RvAdapter adapter = new RvAdapter();
                            bindingView.rv.setAdapter(adapter);
                            bindingView.rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            adapter.setNewData(orderdetailBeanList);

                        } else {
                            TU.cT("获取详情失败");
                            finish();
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        TU.cT("获取详情失败");
                        finish();
                    }
                });
    }


    /**
     * 立即支付
     *
     * @param orderId
     * @param money
     */
    private void doPaynow(String orderId, double money) {
        NormalDialog dialog = new NormalDialog(this);
        dialog.isTitleShow(false)
                .content("确定支付吗？")
                .contentGravity(Gravity.CENTER)//
                .btnText("确定", "取消")
                .showAnim(new Tada())
                .show();
        dialog.setOnBtnClickL(() -> {
                    OkHttpUtils.post().url(UrlConf.PayNowOrder_URL).addParams("token", App.token).addParams("id", orderId)
                            .addParams("payMoney", money + "")
                            .build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            TU.cT("支付失败");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Logger.e(response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optInt("code") == 200) {
                                    TU.cT("支付成功");
                                    doInit();
                                } else {
                                    TU.cT("支付失败!"+ jsonObject.optString("msg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    dialog.superDismiss();
                },
                dialog::superDismiss);
    }

    /**
     * 取消订单
     *
     * @param orderId
     */
    private void doCancel(String orderId) {
        NormalDialog dialog = new NormalDialog(this);
        dialog.isTitleShow(false)
                .content("确定取消订单吗？")
                .contentGravity(Gravity.CENTER)//
                .btnText("确定", "取消")
                .showAnim(new Tada())
                .show();
        dialog.setOnBtnClickL(() -> {
                    OkHttpUtils.post().url(UrlConf.CancelOrder_URL).addParams("token", App.token).addParams("id", orderId)
                            .build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            TU.cT("取消订单失败");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Logger.e(response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optInt("code") == 200) {
                                    TU.cT("取消订单成功");
                                    finish();
                                } else {
                                    TU.cT("取消订单失败!"+ jsonObject.optString("msg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    dialog.superDismiss();
                }
                ,
                dialog::superDismiss);
    }

    /**
     * 确认收货
     *
     * @param orderId
     */
    private void doConfirm(String orderId) {
        NormalDialog dialog = new NormalDialog(this);
        dialog.isTitleShow(false)
                .content("确定收货吗？")
                .contentGravity(Gravity.CENTER)//
                .btnText("确定", "取消")
                .showAnim(new Tada())
                .show();
        dialog.setOnBtnClickL(() -> {
                    OkHttpUtils.post().url(UrlConf.ConfirOrder_URL).addParams("token", App.token).addParams("id", orderId)
                            .build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            TU.cT("确认收货失败");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Logger.e(response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optInt("code") == 200) {
                                    TU.cT("收货成功");
                                    doInit();
                                } else {
                                    TU.cT("收货失败！"+ jsonObject.optString("msg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    dialog.superDismiss();
                },
                dialog::superDismiss);
    }


    /**
     * 删除订单
     *
     * @param orderId
     */
    private void doDeleteOrder(String orderId) {
        NormalDialog dialog = new NormalDialog(this);
        dialog.isTitleShow(false)
                .content("确定删除订单吗？")
                .contentGravity(Gravity.CENTER)//
                .btnText("确定", "取消")
                .showAnim(new Tada())
                .show();
        dialog.setOnBtnClickL(() -> {
                    OkHttpUtils.post().url(UrlConf.DeleteOrder_URL).addParams("token", App.token).addParams("id", orderId)
                            .build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            TU.cT("删除失败");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Logger.e(response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optInt("code") == 200) {
                                    TU.cT("删除成功");
                                    finish();
                                } else {
                                    TU.cT("删除失败!"+ jsonObject.optString("msg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    dialog.superDismiss();
                },
                dialog::superDismiss);
    }

    public String safeText(String o) {
        return TextUtils.isEmpty(o) ? "--" : o;
    }

    private static class RvAdapter extends BaseQuickAdapter<OrderDetailBean.DataBean.OrderdetailBean, BaseViewHolder> {
        public RvAdapter() {
            super(R.layout.item_order_detail_rv);
        }

        @Override
        protected void convert(BaseViewHolder helper, OrderDetailBean.DataBean.OrderdetailBean item) {
            ImageView iv = helper.getView(R.id.iv_img);
            Glide.with(iv.getContext()).load(UrlConf.SHOP_IMGHOST + item.getProduct().getImage()).centerCrop().placeholder(R.drawable.placeholder).into(iv);

            helper.setText(R.id.tv_product_name, item.getProduct().getName())
                    .setText(R.id.tv_spec, "规格" + item.getSpecInfo())
                    .setText(R.id.tv_price, "￥ " + item.getProductPrice())
                    .setText(R.id.tv_num, "数量 x " + item.getNumber())
            ;
        }
    }

    public static class OrderDetailBean {

        /**
         * code : 200
         * msg : OK
         * data : {"id":73,"orderNo":"2017072815025498027203","creatTime":"2017-07-28 15:02:54","payFlag":1,"money":2569,"payTime":"2017-07-29 09:50:27","userId":311,"isSend":0,"isConfirm":0,"sendTime":"","confirmTime":"","score":0,"fee":0,"payMethod":0,"addressId":2,"status":2,"totalMoney":2569,"user":"","address":{"id":2,"account":"311","name":"肖坤","address":"阿升大三大四的啊实打实阿萨德啊","zip":"","tel":"123456789","mobile":"","isdefault":0,"province":"四欻","city":"斯蒂芬","area":"阿瑟","email":""},"isDelete":0,"isCancel":0,"orderdetail":[{"id":51,"orderID":73,"productID":59,"productPrice":2569,"number":1,"specInfo":"褐色,L,不锈钢","specId":"4,5,6","product":{"id":59,"name":"飞利浦","price":1299,"nowPrice":1000,"picture":"/static/product/picture/c4630b93-cb73-4619-b32f-e9d686d21f52.png,/static/product/picture/b688b5ea-401b-48a3-8818-bb486dbf3596.png","status":1,"productHTML":"<p>飞利浦I999（双4G） 主屏尺寸:5.5英寸 主屏分辨率:2560x1440像素<\/p><p><br><\/p>","image":"/static/product/image/51f26a9b-f71c-45b2-b41a-432b3c6dbe4c.png","catalogID":1,"sellcount":0,"sort":"","keyWord":"飞利浦","delFalg":0,"time":"2017-07-28 13:48:00","isIntegral":0,"isDefault":0,"bigScore":4646,"buyNumber":22,"attributes":null,"catalogName":"","parameters":null,"pictures":""}}]}
         */

        private int code;
        private String msg;
        private DataBean data;

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

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 73
             * orderNo : 2017072815025498027203
             * creatTime : 2017-07-28 15:02:54
             * payFlag : 1
             * money : 2569.0
             * payTime : 2017-07-29 09:50:27
             * userId : 311
             * isSend : 0
             * isConfirm : 0
             * sendTime :
             * confirmTime :
             * score : 0.0
             * fee : 0.0
             * payMethod : 0
             * addressId : 2
             * status : 2
             * totalMoney : 2569.0
             * user :
             * address : {"id":2,"account":"311","name":"肖坤","address":"阿升大三大四的啊实打实阿萨德啊","zip":"","tel":"123456789","mobile":"","isdefault":0,"province":"四欻","city":"斯蒂芬","area":"阿瑟","email":""}
             * isDelete : 0
             * isCancel : 0
             * orderdetail : [{"id":51,"orderID":73,"productID":59,"productPrice":2569,"number":1,"specInfo":"褐色,L,不锈钢","specId":"4,5,6","product":{"id":59,"name":"飞利浦","price":1299,"nowPrice":1000,"picture":"/static/product/picture/c4630b93-cb73-4619-b32f-e9d686d21f52.png,/static/product/picture/b688b5ea-401b-48a3-8818-bb486dbf3596.png","status":1,"productHTML":"<p>飞利浦I999（双4G） 主屏尺寸:5.5英寸 主屏分辨率:2560x1440像素<\/p><p><br><\/p>","image":"/static/product/image/51f26a9b-f71c-45b2-b41a-432b3c6dbe4c.png","catalogID":1,"sellcount":0,"sort":"","keyWord":"飞利浦","delFalg":0,"time":"2017-07-28 13:48:00","isIntegral":0,"isDefault":0,"bigScore":4646,"buyNumber":22,"attributes":null,"catalogName":"","parameters":null,"pictures":""}}]
             */

            private int id;
            private String orderNo;
            private String creatTime;
            private int payFlag;
            private double money;
            private String payTime;
            private int userId;
            private int isSend;
            private int isConfirm;
            private String sendTime;
            private String confirmTime;
            private double score;
            private double fee;
            private int payMethod;
            private int addressId;
            private int status;
            private double totalMoney;
            private String user;
            private AddressBean address;
            private int isDelete;
            private int isCancel;
            private List<OrderdetailBean> orderdetail;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public String getCreatTime() {
                return creatTime;
            }

            public void setCreatTime(String creatTime) {
                this.creatTime = creatTime;
            }

            public int getPayFlag() {
                return payFlag;
            }

            public void setPayFlag(int payFlag) {
                this.payFlag = payFlag;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public String getPayTime() {
                return payTime;
            }

            public void setPayTime(String payTime) {
                this.payTime = payTime;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getIsSend() {
                return isSend;
            }

            public void setIsSend(int isSend) {
                this.isSend = isSend;
            }

            public int getIsConfirm() {
                return isConfirm;
            }

            public void setIsConfirm(int isConfirm) {
                this.isConfirm = isConfirm;
            }

            public String getSendTime() {
                return sendTime;
            }

            public void setSendTime(String sendTime) {
                this.sendTime = sendTime;
            }

            public String getConfirmTime() {
                return confirmTime;
            }

            public void setConfirmTime(String confirmTime) {
                this.confirmTime = confirmTime;
            }

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public double getFee() {
                return fee;
            }

            public void setFee(double fee) {
                this.fee = fee;
            }

            public int getPayMethod() {
                return payMethod;
            }

            public void setPayMethod(int payMethod) {
                this.payMethod = payMethod;
            }

            public int getAddressId() {
                return addressId;
            }

            public void setAddressId(int addressId) {
                this.addressId = addressId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public double getTotalMoney() {
                return totalMoney;
            }

            public void setTotalMoney(double totalMoney) {
                this.totalMoney = totalMoney;
            }

            public String getUser() {
                return user;
            }

            public void setUser(String user) {
                this.user = user;
            }

            public AddressBean getAddress() {
                return address;
            }

            public void setAddress(AddressBean address) {
                this.address = address;
            }

            public int getIsDelete() {
                return isDelete;
            }

            public void setIsDelete(int isDelete) {
                this.isDelete = isDelete;
            }

            public int getIsCancel() {
                return isCancel;
            }

            public void setIsCancel(int isCancel) {
                this.isCancel = isCancel;
            }

            public List<OrderdetailBean> getOrderdetail() {
                return orderdetail;
            }

            public void setOrderdetail(List<OrderdetailBean> orderdetail) {
                this.orderdetail = orderdetail;
            }

            public static class AddressBean {
                /**
                 * id : 2
                 * account : 311
                 * name : 肖坤
                 * address : 阿升大三大四的啊实打实阿萨德啊
                 * zip :
                 * tel : 123456789
                 * mobile :
                 * isdefault : 0
                 * province : 四欻
                 * city : 斯蒂芬
                 * area : 阿瑟
                 * email :
                 */

                private int id;
                private String account;
                private String name;
                private String address;
                private String zip;
                private String tel;
                private String mobile;
                private int isdefault;
                private String province;
                private String city;
                private String area;
                private String email;

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

                public String getZip() {
                    return zip;
                }

                public void setZip(String zip) {
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

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }
            }

            public static class OrderdetailBean {
                /**
                 * id : 51
                 * orderID : 73
                 * productID : 59
                 * productPrice : 2569
                 * number : 1
                 * specInfo : 褐色,L,不锈钢
                 * specId : 4,5,6
                 * product : {"id":59,"name":"飞利浦","price":1299,"nowPrice":1000,"picture":"/static/product/picture/c4630b93-cb73-4619-b32f-e9d686d21f52.png,/static/product/picture/b688b5ea-401b-48a3-8818-bb486dbf3596.png","status":1,"productHTML":"<p>飞利浦I999（双4G） 主屏尺寸:5.5英寸 主屏分辨率:2560x1440像素<\/p><p><br><\/p>","image":"/static/product/image/51f26a9b-f71c-45b2-b41a-432b3c6dbe4c.png","catalogID":1,"sellcount":0,"sort":"","keyWord":"飞利浦","delFalg":0,"time":"2017-07-28 13:48:00","isIntegral":0,"isDefault":0,"bigScore":4646,"buyNumber":22,"attributes":null,"catalogName":"","parameters":null,"pictures":""}
                 */

                private int id;
                private int orderID;
                private int productID;
                private double productPrice;
                private int number;
                private String specInfo;
                private String specId;
                private ProductBean product;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getOrderID() {
                    return orderID;
                }

                public void setOrderID(int orderID) {
                    this.orderID = orderID;
                }

                public int getProductID() {
                    return productID;
                }

                public void setProductID(int productID) {
                    this.productID = productID;
                }

                public double getProductPrice() {
                    return productPrice;
                }

                public void setProductPrice(double productPrice) {
                    this.productPrice = productPrice;
                }

                public int getNumber() {
                    return number;
                }

                public void setNumber(int number) {
                    this.number = number;
                }

                public String getSpecInfo() {
                    return specInfo;
                }

                public void setSpecInfo(String specInfo) {
                    this.specInfo = specInfo;
                }

                public String getSpecId() {
                    return specId;
                }

                public void setSpecId(String specId) {
                    this.specId = specId;
                }

                public ProductBean getProduct() {
                    return product;
                }

                public void setProduct(ProductBean product) {
                    this.product = product;
                }

                public static class ProductBean {
                    /**
                     * id : 59
                     * name : 飞利浦
                     * price : 1299
                     * nowPrice : 1000
                     * picture : /static/product/picture/c4630b93-cb73-4619-b32f-e9d686d21f52.png,/static/product/picture/b688b5ea-401b-48a3-8818-bb486dbf3596.png
                     * status : 1
                     * productHTML : <p>飞利浦I999（双4G） 主屏尺寸:5.5英寸 主屏分辨率:2560x1440像素</p><p><br></p>
                     * image : /static/product/image/51f26a9b-f71c-45b2-b41a-432b3c6dbe4c.png
                     * catalogID : 1
                     * sellcount : 0
                     * sort :
                     * keyWord : 飞利浦
                     * delFalg : 0
                     * time : 2017-07-28 13:48:00
                     * isIntegral : 0
                     * isDefault : 0
                     * bigScore : 4646.0
                     * buyNumber : 22
                     * attributes : null
                     * catalogName :
                     * parameters : null
                     * pictures :
                     */

                    private int id;
                    private String name;
                    private double price;
                    private double nowPrice;
                    private String picture;
                    private int status;
                    private String productHTML;
                    private String image;
                    private int catalogID;
                    private int sellcount;
                    private String sort;
                    private String keyWord;
                    private int delFalg;
                    private String time;
                    private int isIntegral;
                    private int isDefault;
                    private double bigScore;
                    private int buyNumber;
                    private Object attributes;
                    private String catalogName;
                    private Object parameters;
                    private String pictures;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public double getPrice() {
                        return price;
                    }

                    public void setPrice(double price) {
                        this.price = price;
                    }

                    public double getNowPrice() {
                        return nowPrice;
                    }

                    public void setNowPrice(double nowPrice) {
                        this.nowPrice = nowPrice;
                    }

                    public String getPicture() {
                        return picture;
                    }

                    public void setPicture(String picture) {
                        this.picture = picture;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
                    }

                    public String getProductHTML() {
                        return productHTML;
                    }

                    public void setProductHTML(String productHTML) {
                        this.productHTML = productHTML;
                    }

                    public String getImage() {
                        return image;
                    }

                    public void setImage(String image) {
                        this.image = image;
                    }

                    public int getCatalogID() {
                        return catalogID;
                    }

                    public void setCatalogID(int catalogID) {
                        this.catalogID = catalogID;
                    }

                    public int getSellcount() {
                        return sellcount;
                    }

                    public void setSellcount(int sellcount) {
                        this.sellcount = sellcount;
                    }

                    public String getSort() {
                        return sort;
                    }

                    public void setSort(String sort) {
                        this.sort = sort;
                    }

                    public String getKeyWord() {
                        return keyWord;
                    }

                    public void setKeyWord(String keyWord) {
                        this.keyWord = keyWord;
                    }

                    public int getDelFalg() {
                        return delFalg;
                    }

                    public void setDelFalg(int delFalg) {
                        this.delFalg = delFalg;
                    }

                    public String getTime() {
                        return time;
                    }

                    public void setTime(String time) {
                        this.time = time;
                    }

                    public int getIsIntegral() {
                        return isIntegral;
                    }

                    public void setIsIntegral(int isIntegral) {
                        this.isIntegral = isIntegral;
                    }

                    public int getIsDefault() {
                        return isDefault;
                    }

                    public void setIsDefault(int isDefault) {
                        this.isDefault = isDefault;
                    }

                    public double getBigScore() {
                        return bigScore;
                    }

                    public void setBigScore(double bigScore) {
                        this.bigScore = bigScore;
                    }

                    public int getBuyNumber() {
                        return buyNumber;
                    }

                    public void setBuyNumber(int buyNumber) {
                        this.buyNumber = buyNumber;
                    }

                    public Object getAttributes() {
                        return attributes;
                    }

                    public void setAttributes(Object attributes) {
                        this.attributes = attributes;
                    }

                    public String getCatalogName() {
                        return catalogName;
                    }

                    public void setCatalogName(String catalogName) {
                        this.catalogName = catalogName;
                    }

                    public Object getParameters() {
                        return parameters;
                    }

                    public void setParameters(Object parameters) {
                        this.parameters = parameters;
                    }

                    public String getPictures() {
                        return pictures;
                    }

                    public void setPictures(String pictures) {
                        this.pictures = pictures;
                    }
                }
            }
        }
    }
}
