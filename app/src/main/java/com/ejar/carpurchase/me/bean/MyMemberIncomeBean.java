package com.ejar.carpurchase.me.bean;

import java.util.List;

/**
 * Created by wxd1 on 2017/6/30.
 */

public class MyMemberIncomeBean {

    /**
     * msg : 查看成功
     * code : 200
     * data : {"URevenueRecord":[{"id":1,"userId":1,"shopId":"","money":1000,"state":1,"type":1,"type2":3,"remark":"线下充值金额","time":1498567294000,"staye2":3,"topUserID":"","userName":"","money2":"","orderId":"","userId2":""},{"id":19,"userId":1,"shopId":"","money":10000,"state":1,"type":1,"type2":3,"remark":"线下充值金额","time":1498615060000,"staye2":3,"topUserID":null,"userName":null,"money2":null,"orderId":null,"userId2":null}],"page":{"pageNo":1,"startNum":0,"size":10,"totleNumber":2,"totlePage":1}}
     */

    private String msg;
    private int code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * URevenueRecord : [{"id":1,"userId":1,"shopId":"","money":1000,"state":1,"type":1,"type2":3,"remark":"线下充值金额","time":1498567294000,"staye2":3,"topUserID":"","userName":"","money2":"","orderId":"","userId2":""},{"id":19,"userId":1,"shopId":"","money":10000,"state":1,"type":1,"type2":3,"remark":"线下充值金额","time":1498615060000,"staye2":3,"topUserID":null,"userName":null,"money2":null,"orderId":null,"userId2":null}]
         * page : {"pageNo":1,"startNum":0,"size":10,"totleNumber":2,"totlePage":1}
         */

        private PageBean page;
        private List<URevenueRecordBean> URevenueRecord;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public List<URevenueRecordBean> getURevenueRecord() {
            return URevenueRecord;
        }

        public void setURevenueRecord(List<URevenueRecordBean> URevenueRecord) {
            this.URevenueRecord = URevenueRecord;
        }

        public static class PageBean {
            /**
             * pageNo : 1
             * startNum : 0
             * size : 10
             * totleNumber : 2
             * totlePage : 1
             */

            private int pageNo;
            private int startNum;
            private int size;
            private int totleNumber;
            private int totlePage;

            public int getPageNo() {
                return pageNo;
            }

            public void setPageNo(int pageNo) {
                this.pageNo = pageNo;
            }

            public int getStartNum() {
                return startNum;
            }

            public void setStartNum(int startNum) {
                this.startNum = startNum;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public int getTotleNumber() {
                return totleNumber;
            }

            public void setTotleNumber(int totleNumber) {
                this.totleNumber = totleNumber;
            }

            public int getTotlePage() {
                return totlePage;
            }

            public void setTotlePage(int totlePage) {
                this.totlePage = totlePage;
            }
        }

        public static class URevenueRecordBean {
            /**
             * id : 1
             * userId : 1
             * shopId :
             * money : 1000
             * state : 1
             * type : 1
             * type2 : 3
             * remark : 线下充值金额
             * time : 1498567294000
             * staye2 : 3
             * topUserID :
             * userName :
             * money2 :
             * orderId :
             * userId2 :
             */

            private int id;
            private int userId;
            private String shopId;
            private int money;
            private int state;
            private int type;
            private int type2;
            private String remark;
            private long time;
            private int staye2;
            private String topUserID;
            private String userName;
            private String money2;
            private String orderId;
            private String userId2;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getShopId() {
                return shopId;
            }

            public void setShopId(String shopId) {
                this.shopId = shopId;
            }

            public int getMoney() {
                return money;
            }

            public void setMoney(int money) {
                this.money = money;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getType2() {
                return type2;
            }

            public void setType2(int type2) {
                this.type2 = type2;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getStaye2() {
                return staye2;
            }

            public void setStaye2(int staye2) {
                this.staye2 = staye2;
            }

            public String getTopUserID() {
                return topUserID;
            }

            public void setTopUserID(String topUserID) {
                this.topUserID = topUserID;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getMoney2() {
                return money2;
            }

            public void setMoney2(String money2) {
                this.money2 = money2;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getUserId2() {
                return userId2;
            }

            public void setUserId2(String userId2) {
                this.userId2 = userId2;
            }
        }
    }
}
