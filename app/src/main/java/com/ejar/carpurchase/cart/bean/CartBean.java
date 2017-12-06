package com.ejar.carpurchase.cart.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by wxd1 on 2017/7/21.
 */

public class CartBean implements MultiItemEntity {
    public static final int TYPE_NORMAL = 1;
    /**
     * 失效
     */
    public static final int TYPE_UN_USE = 2;

    private int type;
    private CartDataBean.DataBean.BuycarListBean dataBean;

    public CartBean(int type,CartDataBean.DataBean.BuycarListBean dataBean) {
        this.type = type;
        this.dataBean = dataBean;
    }

    public CartDataBean.DataBean.BuycarListBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(CartDataBean.DataBean.BuycarListBean dataBean) {
        this.dataBean = dataBean;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
