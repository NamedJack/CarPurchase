package com.ejar.carpurchase.me.bean;

import java.util.List;

/**
 * Created by wxd1 on 2017/6/14.
 */

public class ShopBannerBean {

    /**
     * msg : 查看成功
     * code : 200
     * data : [{"id":37,"shopId":20,"imgUrl":"/static/upload/entityimg/14c91ed0-983c-411c-918c-0ffd69a6c198.jpg"},{"id":38,"shopId":20,"imgUrl":"/static/upload/entityimg/81ccd3b5-b002-483b-8566-7b5ed88cf1cc.jpg"},{"id":39,"shopId":20,"imgUrl":"/static/upload/entityimg/a342cba1-a432-47e0-ae02-0c335d4fd20e.jpg"}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 37
         * shopId : 20
         * imgUrl : /static/upload/entityimg/14c91ed0-983c-411c-918c-0ffd69a6c198.jpg
         */

        private int id;
        private int shopId;
        private String imgUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
