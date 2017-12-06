package com.ejar.carpurchase.shop.bean;

import java.util.List;

/**
 * Created by wongxd on 2017/7/26.
 */

public class ShopCatalogBean {

    /**
     * status : 200
     * msg : OK
     * data : [{"id":1,"name":"数码","pid":0},{"id":2,"name":"母婴","pid":0},{"id":3,"name":"首饰","pid":0},{"id":4,"name":"家电","pid":0},{"id":5,"name":"运动","pid":0},{"id":7,"name":"PC","pid":1},{"id":10,"name":"手机","pid":1}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
         * name : 数码
         * pid : 0
         */

        private int id;
        private String name;
        private int pid;

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

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }
    }
}
