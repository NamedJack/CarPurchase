package com.ejar.carpurchase.cart;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.rx.RxBus;
import com.ejar.carpurchase.base.rx.RxEventCodeType;
import com.ejar.carpurchase.cart.bean.CartBean;
import com.ejar.carpurchase.cart.bean.CartDataBean;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by wxd1 on 2017/7/21.
 */

public class CartRvAdapter extends BaseMultiItemQuickAdapter<CartBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private List<CartBean> selectedSet = new ArrayList<>();

    public CartRvAdapter(List<CartBean> data) {
        super(data);
        addItemType(CartBean.TYPE_NORMAL, R.layout.item_rvcart);
        addItemType(CartBean.TYPE_UN_USE, R.layout.item_rvcart_unuse);
    }


    /**
     * To bind different types of holder and solve different the bind events
     *
     * @param holder
     * @param position
     * @see #getDefItemViewType(int)
     */
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
//        super.onBindViewHolder(holder, position, payloads);

        //Do not move position, need to change before LoadMoreView binding
//        autoLoadMore(position);
//        int viewType = holder.getItemViewType();
//
//        switch (viewType) {
//            case 0:
//
//                convert(holder, mData.get(holder.getLayoutPosition() - getHeaderLayoutCount()));
//                break;
//            case LOADING_VIEW:
//                mLoadMoreView.convert(holder);
//                break;
//            case HEADER_VIEW:
//                break;
//            case EMPTY_VIEW:
//                break;
//            case FOOTER_VIEW:
//                break;
//            default:
//                convert(holder, mData.get(holder.getLayoutPosition() - getHeaderLayoutCount()));
//                break;
//        }

        onBindViewHolder(holder, position);
    }


    @Override
    protected void convert(BaseViewHolder helper, CartBean item) {
        switch (helper.getItemViewType()) {
            case CartBean.TYPE_NORMAL:
                CartDataBean.DataBean.BuycarListBean data = item.getDataBean();
                if (!data.getIsEdit()) {
                    helper.setVisible(R.id.rl_edit, false)
                            .setVisible(R.id.rl_detail, true);
                } else {
                    helper.setVisible(R.id.rl_edit, true)
                            .setVisible(R.id.rl_detail, false);
                }

                if (!data.getIsSelected()) {
                    helper.setImageResource(R.id.iv_check, R.mipmap.download_un);
                    if (selectedSet.contains(item)) {
                        selectedSet.remove(item);
                    }
                } else {
                    helper.setImageResource(R.id.iv_check, R.mipmap.download);
                    if (!selectedSet.contains(item)) {
                        selectedSet.add(item);
                    }
                }


                String spec = "";

                for (CartDataBean.DataBean.BuycarListBean.AttributesBean s : data.getAttributes()) {
                    spec += s.getName() + " ";
                }

                helper.setText(R.id.tv_name, data.getProductName())
                        .setText(R.id.tv_spec, spec)
                        .setText(R.id.tv_price, "￥ " + data.getUnivalent())
                        .setText(R.id.tv_num, "数量 x " + data.getProductNum())
                        .setText(R.id.tv_num_edit, data.getProductNum() + "")
                ;

                ImageView iv = helper.getView(R.id.iv_img);
                Glide.with(iv.getContext()).load(UrlConf.SHOP_IMGHOST + data.getProductImg())
                        .centerCrop().placeholder(R.drawable.placeholder).into(iv);


                /**
                 * 点击
                 */
                helper.getView(R.id.tv_edit).setOnClickListener(v -> {
                    data.setIsEdit(!data.getIsEdit());
                    notifyItemChanged(helper.getLayoutPosition(),1);
                });

                helper.getView(R.id.iv_check).setOnClickListener(v -> {
                    data.setIsSelected(!data.getIsSelected());
                    notifyItemChanged(helper.getLayoutPosition(),1);
                });


                /**
                 *编辑模式
                 */

                TextView tv_num_edit = helper.getView(R.id.tv_num_edit);

                helper.getView(R.id.tv_complite).setOnClickListener(v -> {
                    changeCart(data.getId() + "", data.getProductNum() + "");
                    data.setIsEdit(!data.getIsEdit());
                    notifyItemChanged(helper.getLayoutPosition(),1);
                });

                helper.getView(R.id.tv_delete).setOnClickListener(v -> {
                    changeCart(data.getId() + "", "0");
                    if (selectedSet.contains(item))
                        selectedSet.remove(item);
                    getData().remove(item);
                    notifyItemRemoved(helper.getLayoutPosition());
                });


                helper.getView(R.id.iv_reduce).setOnClickListener(v -> {
                    int num = Integer.valueOf(tv_num_edit.getText().toString());
                    if (num > 1) {
                        data.setProductNum(--num);
                    }
                    notifyItemChanged(helper.getLayoutPosition(),1);
                });

                helper.getView(R.id.iv_add).setOnClickListener(v -> {
                    int num = Integer.valueOf(tv_num_edit.getText().toString());
                    data.setProductNum(++num);
                    notifyItemChanged(helper.getLayoutPosition(),1);
                });

                /**
                 * 每次计算后，总价 和运费 要计算
                 */
                new Thread(() -> {
                    double amount = 0d;
                    for (CartBean ii : getSelectedSet()) {
                        double price = ii.getDataBean().getUnivalent();
                        int num = ii.getDataBean().getProductNum();
                        amount += price * num;
                    }
                    RxBus.getDefault().post(RxEventCodeType.CART_AMOUNT, amount + "");
                }).start();


                break;
            case CartBean.TYPE_UN_USE:
                //清除失效宝贝
                break;
        }


    }

    public List<CartBean> getSelectedSet() {
        return selectedSet;
    }

    /**
     * 修改 删除 购物车
     *
     * @param cartId
     * @param number
     */
    private void changeCart(String cartId, String number) {
        OkHttpUtils.post()
                .url(UrlConf.ChangeCart_URL)
                .addParams("token", App.token)
                .addParams("id", cartId)
                .addParams("number", number)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                TU.cT("修改失败");
            }

            @Override
            public void onResponse(String response, int id) {
                Logger.e("修改 删除 购物车" + response);
            }
        });
    }
}
