package com.ejar.carpurchase.me.fgt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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
import com.ejar.carpurchase.base.BaseBindingFragment;
import com.ejar.carpurchase.base.rx.RxBus;
import com.ejar.carpurchase.base.rx.RxEventCodeType;
import com.ejar.carpurchase.base.rx.Subscribe;
import com.ejar.carpurchase.databinding.FgtOrderItemBinding;
import com.ejar.carpurchase.me.aty.OrderDetailActivity;
import com.ejar.carpurchase.me.bean.ChargeBean;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by wxd1 on 2017/7/21.
 */

public class OrderItemFragment extends BaseBindingFragment<FgtOrderItemBinding> {

    public static OrderItemFragment getInstance(@Nullable Bundle args) {
        OrderItemFragment fg = new OrderItemFragment();
        if (args != null)
            fg.setArguments(args);
        return fg;
    }

    public static OrderItemFragment getInstance(int flag) {
        OrderItemFragment fg = new OrderItemFragment();
        Bundle b = new Bundle();
        b.putInt("flag", flag);
        fg.setArguments(b);
        return fg;
    }

    private int flag = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flag = getArguments().getInt("flag");
    }

    @Override
    public int setContent() {
        return R.layout.fgt_order_item;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecycleView();
    }

    private ArrayList<ChargeBean.RecordBean.RecordsBean> list;
    private RvAdapter adapter;

    private void initRecycleView() {
        list = new ArrayList<>();
        adapter = new RvAdapter();
        bindingView.rvOrderItem.setAdapter(adapter);
        bindingView.rvOrderItem.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setEmptyView(R.layout.item_rv_empty, bindingView.rvOrderItem);
        bindingView.srlOrderItem.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getlist(false);
            }
        });


        // 滑动最后一个Item的时候回调onLoadMoreRequested方法
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getlist(true);
            }
        }, bindingView.rvOrderItem);


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                Intent i = new Intent(getActivity(), OrderDetailActivity.class);
                i.putExtra("id", adapter.getData().get(position).getId() + "");
                startActivity(i);
            }
        });
    }


    int page = 1;
    int totalPage = 1;

    @Override
    protected void loadData() {
        super.loadData();
        getlist(false);

    }

    @Override
    public void onResume() {
        super.onResume();
        getlist(false);
    }

    @Subscribe(code = RxEventCodeType.ORDER_LIST_REFRESH)
    public void doRefresh(Long time) {
        getlist(false);
    }


    private void getlist(boolean isLoadMore) {
        //        1:代付款 2：已付款/待发货 3：待收货/已发货 4：完成/已收货
        if (!isLoadMore) {
            page = 1;
            totalPage = 1;
        }
        String url = UrlConf.OrderList_URL;
        PostFormBuilder builder = OkHttpUtils.post().tag(netTag).url(url)
                .addParams("token", App.token)
                .addParams("pageNo", page + "")
                .addParams("pageSize", "10");

        if (flag != 0) {
            builder.addParams("status", flag + "");
        }

        WNetUtil.StringCallBack(builder
                , url + flag, (AppCompatActivity) getActivity(), "获取订单中", true, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        bindingView.srlOrderItem.setRefreshing(false);
                        Logger.e(response);
                        OrderBean orderBean = new Gson().fromJson(response, OrderBean.class);
//                        TU.cT(""+orderBean.getMsg());
                        if (orderBean.getCode() == 200) {
                            totalPage = orderBean.getData().getTotalPage();
                            if (page > totalPage) {
                                adapter.loadMoreEnd();
                                return;
                            }
                            if (isLoadMore) {
                                if (orderBean.getData().getOrderList().size() != 0) {
                                    adapter.addData(orderBean.getData().getOrderList());
                                    ++page;
                                    adapter.loadMoreComplete();
                                }
                            } else {
                                if (orderBean.getData().getOrderList().size() != 0) {
                                    adapter.setNewData(orderBean.getData().getOrderList());
                                    ++page;
                                }
                            }
                        } else if (orderBean.getCode() == 500 && orderBean.msg.equals("暂无数据")) {
                            if (isLoadMore)
                                adapter.loadMoreEnd();
                            else adapter.setNewData(null);
                        } else {
                            if (isLoadMore) {
                                adapter.loadMoreFail();
                            } else adapter.setNewData(null);
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        bindingView.srlOrderItem.setRefreshing(false);
                        if (isLoadMore) adapter.loadMoreFail();
                    }
                });
    }

    private class RvAdapter extends BaseQuickAdapter<OrderBean.DataBean.OrderListBean, BaseViewHolder> {
        public RvAdapter() {
            super(R.layout.item_rv_order_center);
        }

        @Override
        protected void convert(BaseViewHolder helper, OrderBean.DataBean.OrderListBean item) {
            OrderBean.DataBean.OrderListBean.OrderdetailBean.ProductBean productBean = item.getOrderdetail().get(0).getProduct();
            OrderBean.DataBean.OrderListBean.OrderdetailBean orderdetailBean = item.getOrderdetail().get(0);
            ImageView iv = helper.getView(R.id.iv_img);
            Glide.with(iv.getContext()).load(UrlConf.SHOP_IMGHOST + productBean.getImage()).centerCrop().placeholder(R.drawable.placeholder).into(iv);


            Button btnLeft = helper.getView(R.id.btn_left);
            Button btnRight = helper.getView(R.id.btn_right);
            String state = "";
//            1:代付款 2：已付款/待发货 3：待收货/已发货 4：完成/已收货
            switch (item.getStatus()) {
                case 1:
                    state = "待付款";
                    btnLeft.setVisibility(View.VISIBLE);
                    btnRight.setVisibility(View.VISIBLE);
                    btnLeft.setText("取消订单");
                    btnRight.setText("去付款");
                    btnRight.setOnClickListener(v -> doPaynow(item.getId() + "", item.getMoney(), helper.getLayoutPosition()));
                    btnLeft.setOnClickListener(v -> doCancel(item.getId() + "", helper.getLayoutPosition()));
                    break;
                case 2:
                    state = "待发货";
                    btnLeft.setVisibility(View.GONE);
                    btnRight.setVisibility(View.GONE);
                    break;
                case 3:
                    state = "待收货";
                    btnLeft.setVisibility(View.GONE);
                    btnRight.setVisibility(View.VISIBLE);
                    btnRight.setText("确认收货");
                    btnRight.setOnClickListener(v -> doConfirm(item.getId() + "", helper.getLayoutPosition()));
                    break;
                case 4:
                    state = "已完成";
                    btnLeft.setVisibility(View.VISIBLE);
                    btnRight.setVisibility(View.GONE);
                    btnLeft.setText("删除订单");
                    btnLeft.setOnClickListener(v -> doDeleteOrder(item.getId() + "", helper.getLayoutPosition()));
                    break;
            }
            int totalNum = 0;
            for (OrderBean.DataBean.OrderListBean.OrderdetailBean order : item.getOrderdetail()) {
                totalNum += order.getNumber();
            }
            helper.setText(R.id.tv_name, productBean.getName())
                    .setText(R.id.tv_spec, orderdetailBean.getSpecInfo())
                    .setText(R.id.tv_price, "￥ " + productBean.getNowPrice() + "")
                    .setText(R.id.tv_num, "数量 x " + orderdetailBean.getNumber())
                    .setText(R.id.tv_num_all, totalNum + "")
                    .setText(R.id.tv_amount, "￥ " + item.getMoney() + "(含运费:" + item.getFee() + ")")
                    .setText(R.id.tv_state, state);
        }


        /**
         * 立即支付
         *
         * @param orderId
         * @param money
         * @param layoutPosition
         */
        private void doPaynow(String orderId, double money, int layoutPosition) {
            NormalDialog dialog = new NormalDialog(getActivity());
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
                                        RxBus.getDefault().post(RxEventCodeType.ORDER_LIST_REFRESH, System.currentTimeMillis());
                                    } else {
                                        TU.cT("支付失败！" + jsonObject.optString("msg"));
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
         * @param layoutPosition
         */
        private void doCancel(String orderId, int layoutPosition) {
            NormalDialog dialog = new NormalDialog(getActivity());
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
                                        RxBus.getDefault().post(RxEventCodeType.ORDER_LIST_REFRESH, System.currentTimeMillis());
                                    } else {
                                        TU.cT("取消订单失败!" + jsonObject.optString("msg"));
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
         * 确认收货
         *
         * @param orderId
         * @param layoutPosition
         */
        private void doConfirm(String orderId, int layoutPosition) {
            NormalDialog dialog = new NormalDialog(getActivity());
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
                                        RxBus.getDefault().post(RxEventCodeType.ORDER_LIST_REFRESH, System.currentTimeMillis());
                                    } else {
                                        TU.cT("收货失败!" + jsonObject.optString("msg"));
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
         * @param pos     postion
         */
        private void doDeleteOrder(String orderId, int pos) {
            NormalDialog dialog = new NormalDialog(getActivity());
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
                                        getData().remove(pos);
                                        notifyItemRemoved(pos);
                                    } else {
                                        TU.cT("删除失败!" + jsonObject.optString("msg"));
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


    }

    private static class OrderBean {

        /**
         * code : 200
         * msg : OK
         * data : {"totalPage":3,"orderList":[{"id":73,"orderNo":"2017072815025498027203","creatTime":"2017-07-28 15:02:54","payFlag":0,"money":2569,"payTime":"","userId":311,"isSend":0,"isConfirm":0,"sendTime":"","confirmTime":"","score":0,"fee":0,"payMethod":0,"addressId":2,"status":1,"totalMoney":2569,"user":null,"address":null,"isDelete":0,"isCancel":0,"orderdetail":[{"id":51,"orderID":73,"productID":59,"productPrice":2569,"number":1,"specInfo":"褐色,L,不锈钢","specId":"4,5,6","product":{"id":59,"name":"飞利浦","price":1299,"nowPrice":1000,"picture":"/static/product/picture/c4630b93-cb73-4619-b32f-e9d686d21f52.png,/static/product/picture/b688b5ea-401b-48a3-8818-bb486dbf3596.png","status":1,"productHTML":"<p>飞利浦I999（双4G） 主屏尺寸:5.5英寸 主屏分辨率:2560x1440像素<\/p><p><br><\/p>","image":"/static/product/image/51f26a9b-f71c-45b2-b41a-432b3c6dbe4c.png","catalogID":1,"sellcount":0,"sort":null,"keyWord":"飞利浦","delFalg":0,"time":"2017-07-28 13:48:00","isIntegral":0,"bigScore":78,"buyNumber":4,"attributes":"","catalogName":"","parameters":null,"pictures":""}}]}]}
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
             * totalPage : 3
             * orderList : [{"id":73,"orderNo":"2017072815025498027203","creatTime":"2017-07-28 15:02:54","payFlag":0,"money":2569,"payTime":"","userId":311,"isSend":0,"isConfirm":0,"sendTime":"","confirmTime":"","score":0,"fee":0,"payMethod":0,"addressId":2,"status":1,"totalMoney":2569,"user":null,"address":null,"isDelete":0,"isCancel":0,"orderdetail":[{"id":51,"orderID":73,"productID":59,"productPrice":2569,"number":1,"specInfo":"褐色,L,不锈钢","specId":"4,5,6","product":{"id":59,"name":"飞利浦","price":1299,"nowPrice":1000,"picture":"/static/product/picture/c4630b93-cb73-4619-b32f-e9d686d21f52.png,/static/product/picture/b688b5ea-401b-48a3-8818-bb486dbf3596.png","status":1,"productHTML":"<p>飞利浦I999（双4G） 主屏尺寸:5.5英寸 主屏分辨率:2560x1440像素<\/p><p><br><\/p>","image":"/static/product/image/51f26a9b-f71c-45b2-b41a-432b3c6dbe4c.png","catalogID":1,"sellcount":0,"sort":null,"keyWord":"飞利浦","delFalg":0,"time":"2017-07-28 13:48:00","isIntegral":0,"bigScore":78,"buyNumber":4,"attributes":"","catalogName":"","parameters":null,"pictures":""}}]}]
             */

            private int totalPage;
            private List<OrderListBean> orderList;

            public int getTotalPage() {
                return totalPage;
            }

            public void setTotalPage(int totalPage) {
                this.totalPage = totalPage;
            }

            public List<OrderListBean> getOrderList() {
                return orderList;
            }

            public void setOrderList(List<OrderListBean> orderList) {
                this.orderList = orderList;
            }

            public static class OrderListBean {
                /**
                 * id : 73
                 * orderNo : 2017072815025498027203
                 * creatTime : 2017-07-28 15:02:54
                 * payFlag : 0
                 * money : 2569
                 * payTime :
                 * userId : 311
                 * isSend : 0
                 * isConfirm : 0
                 * sendTime :
                 * confirmTime :
                 * score : 0
                 * fee : 0
                 * payMethod : 0
                 * addressId : 2
                 * status : 1
                 * totalMoney : 2569.0
                 * user : null
                 * address : null
                 * isDelete : 0
                 * isCancel : 0
                 * orderdetail : [{"id":51,"orderID":73,"productID":59,"productPrice":2569,"number":1,"specInfo":"褐色,L,不锈钢","specId":"4,5,6","product":{"id":59,"name":"飞利浦","price":1299,"nowPrice":1000,"picture":"/static/product/picture/c4630b93-cb73-4619-b32f-e9d686d21f52.png,/static/product/picture/b688b5ea-401b-48a3-8818-bb486dbf3596.png","status":1,"productHTML":"<p>飞利浦I999（双4G） 主屏尺寸:5.5英寸 主屏分辨率:2560x1440像素<\/p><p><br><\/p>","image":"/static/product/image/51f26a9b-f71c-45b2-b41a-432b3c6dbe4c.png","catalogID":1,"sellcount":0,"sort":null,"keyWord":"飞利浦","delFalg":0,"time":"2017-07-28 13:48:00","isIntegral":0,"bigScore":78,"buyNumber":4,"attributes":"","catalogName":"","parameters":null,"pictures":""}}]
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
                private Object user;
                private Object address;
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

                public Object getUser() {
                    return user;
                }

                public void setUser(Object user) {
                    this.user = user;
                }

                public Object getAddress() {
                    return address;
                }

                public void setAddress(Object address) {
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

                public static class OrderdetailBean {
                    /**
                     * id : 51
                     * orderID : 73
                     * productID : 59
                     * productPrice : 2569
                     * number : 1
                     * specInfo : 褐色,L,不锈钢
                     * specId : 4,5,6
                     * product : {"id":59,"name":"飞利浦","price":1299,"nowPrice":1000,"picture":"/static/product/picture/c4630b93-cb73-4619-b32f-e9d686d21f52.png,/static/product/picture/b688b5ea-401b-48a3-8818-bb486dbf3596.png","status":1,"productHTML":"<p>飞利浦I999（双4G） 主屏尺寸:5.5英寸 主屏分辨率:2560x1440像素<\/p><p><br><\/p>","image":"/static/product/image/51f26a9b-f71c-45b2-b41a-432b3c6dbe4c.png","catalogID":1,"sellcount":0,"sort":null,"keyWord":"飞利浦","delFalg":0,"time":"2017-07-28 13:48:00","isIntegral":0,"bigScore":78,"buyNumber":4,"attributes":"","catalogName":"","parameters":null,"pictures":""}
                     */

                    private int id;
                    private int orderID;
                    private int productID;
                    private int productPrice;
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

                    public int getProductPrice() {
                        return productPrice;
                    }

                    public void setProductPrice(int productPrice) {
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
                         * price : 1299.0
                         * nowPrice : 1000.0
                         * picture : /static/product/picture/c4630b93-cb73-4619-b32f-e9d686d21f52.png,/static/product/picture/b688b5ea-401b-48a3-8818-bb486dbf3596.png
                         * status : 1
                         * productHTML : <p>飞利浦I999（双4G） 主屏尺寸:5.5英寸 主屏分辨率:2560x1440像素</p><p><br></p>
                         * image : /static/product/image/51f26a9b-f71c-45b2-b41a-432b3c6dbe4c.png
                         * catalogID : 1
                         * sellcount : 0
                         * sort : null
                         * keyWord : 飞利浦
                         * delFalg : 0
                         * time : 2017-07-28 13:48:00
                         * isIntegral : 0
                         * bigScore : 78.0
                         * buyNumber : 4
                         * attributes :
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
                        private Object sort;
                        private String keyWord;
                        private int delFalg;
                        private String time;
                        private int isIntegral;
                        private double bigScore;
                        private int buyNumber;
                        private String attributes;
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

                        public Object getSort() {
                            return sort;
                        }

                        public void setSort(Object sort) {
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

                        public String getAttributes() {
                            return attributes;
                        }

                        public void setAttributes(String attributes) {
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
}
