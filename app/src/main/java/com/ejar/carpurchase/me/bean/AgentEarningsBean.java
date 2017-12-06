package com.ejar.carpurchase.me.bean;

import java.util.List;

/**
 * Created by wxd1 on 2017/6/27.
 */

public class AgentEarningsBean {


    /**
     * msg : 查看成功
     * code : 200
     * data : {"URevenueRecord":[{"id":24,"userId":41,"shopId":null,"money":1111,"state":6,"type":1,"type2":6,"remark":"Asdfasdf","time":1496905258000,"staye2":1,"topUserID":null,"userName":"张三","money2":4000,"orderId":null,"userId2":1},{"id":25,"userId":41,"shopId":null,"money":99,"state":6,"type":1,"type2":6,"remark":"Aa","time":1496905279000,"staye2":1,"topUserID":null,"userName":"李四","money2":5000,"orderId":null,"userId2":2},{"id":26,"userId":41,"shopId":null,"money":259,"state":6,"type":1,"type2":6,"remark":"备注消息asd","time":1496905298000,"staye2":1,"topUserID":null,"userName":"王五","money2":52751,"orderId":null,"userId2":3},{"id":27,"userId":41,"shopId":null,"money":12,"state":6,"type":1,"type2":6,"remark":"业绩转为余额","time":1496906007000,"staye2":1,"topUserID":null,"userName":"小胡","money2":2745,"orderId":null,"userId2":4},{"id":28,"userId":41,"shopId":null,"money":12,"state":6,"type":1,"type2":6,"remark":"业绩转为余额","time":1496906007000,"staye2":1,"topUserID":null,"userName":"张三","money2":575.4,"orderId":null,"userId2":1},{"id":29,"userId":41,"shopId":null,"money":155,"state":6,"type":1,"type2":6,"remark":"你好","time":1496906139000,"staye2":3,"topUserID":null,"userName":"李四","money2":212,"orderId":null,"userId2":2},{"id":30,"userId":41,"shopId":null,"money":155,"state":6,"type":1,"type2":6,"remark":"你","time":1496906181000,"staye2":3,"topUserID":null,"userName":"王五","money2":5753274,"orderId":null,"userId2":3},{"id":31,"userId":41,"shopId":null,"money":100,"state":6,"type":1,"type2":6,"remark":"他","time":1496906956000,"staye2":3,"topUserID":null,"userName":"小胡","money2":2452,"orderId":null,"userId2":4},{"id":32,"userId":41,"shopId":null,"money":1500,"state":6,"type":1,"type2":6,"remark":"业绩转为余额","time":1496907302000,"staye2":1,"topUserID":null,"userName":null,"money2":0.475,"orderId":null,"userId2":23},{"id":33,"userId":41,"shopId":null,"money":1500,"state":6,"type":1,"type2":6,"remark":"业绩转为余额","time":1496907302000,"staye2":1,"topUserID":null,"userName":null,"money2":74575,"orderId":null,"userId2":23},{"id":34,"userId":41,"shopId":null,"money":21,"state":6,"type":1,"type2":6,"remark":"备注消息","time":1496914103000,"staye2":1,"topUserID":null,"userName":null,"money2":54545,"orderId":null,"userId2":23}],"page":{"pageNo":1,"startNum":0,"size":15,"totleNumber":11,"totlePage":1}}
     */

    private String msg;
    private String code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
         * URevenueRecord : [{"id":24,"userId":41,"shopId":null,"money":1111,"state":6,"type":1,"type2":6,"remark":"Asdfasdf","time":1496905258000,"staye2":1,"topUserID":null,"userName":"张三","money2":4000,"orderId":null,"userId2":1},{"id":25,"userId":41,"shopId":null,"money":99,"state":6,"type":1,"type2":6,"remark":"Aa","time":1496905279000,"staye2":1,"topUserID":null,"userName":"李四","money2":5000,"orderId":null,"userId2":2},{"id":26,"userId":41,"shopId":null,"money":259,"state":6,"type":1,"type2":6,"remark":"备注消息asd","time":1496905298000,"staye2":1,"topUserID":null,"userName":"王五","money2":52751,"orderId":null,"userId2":3},{"id":27,"userId":41,"shopId":null,"money":12,"state":6,"type":1,"type2":6,"remark":"业绩转为余额","time":1496906007000,"staye2":1,"topUserID":null,"userName":"小胡","money2":2745,"orderId":null,"userId2":4},{"id":28,"userId":41,"shopId":null,"money":12,"state":6,"type":1,"type2":6,"remark":"业绩转为余额","time":1496906007000,"staye2":1,"topUserID":null,"userName":"张三","money2":575.4,"orderId":null,"userId2":1},{"id":29,"userId":41,"shopId":null,"money":155,"state":6,"type":1,"type2":6,"remark":"你好","time":1496906139000,"staye2":3,"topUserID":null,"userName":"李四","money2":212,"orderId":null,"userId2":2},{"id":30,"userId":41,"shopId":null,"money":155,"state":6,"type":1,"type2":6,"remark":"你","time":1496906181000,"staye2":3,"topUserID":null,"userName":"王五","money2":5753274,"orderId":null,"userId2":3},{"id":31,"userId":41,"shopId":null,"money":100,"state":6,"type":1,"type2":6,"remark":"他","time":1496906956000,"staye2":3,"topUserID":null,"userName":"小胡","money2":2452,"orderId":null,"userId2":4},{"id":32,"userId":41,"shopId":null,"money":1500,"state":6,"type":1,"type2":6,"remark":"业绩转为余额","time":1496907302000,"staye2":1,"topUserID":null,"userName":null,"money2":0.475,"orderId":null,"userId2":23},{"id":33,"userId":41,"shopId":null,"money":1500,"state":6,"type":1,"type2":6,"remark":"业绩转为余额","time":1496907302000,"staye2":1,"topUserID":null,"userName":null,"money2":74575,"orderId":null,"userId2":23},{"id":34,"userId":41,"shopId":null,"money":21,"state":6,"type":1,"type2":6,"remark":"备注消息","time":1496914103000,"staye2":1,"topUserID":null,"userName":null,"money2":54545,"orderId":null,"userId2":23}]
         * page : {"pageNo":1,"startNum":0,"size":15,"totleNumber":11,"totlePage":1}
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
             * size : 15
             * totleNumber : 11
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
             * id : 24
             * userId : 41
             * shopId : null
             * money : 1111.0
             * state : 6
             * type : 1
             * type2 : 6
             * remark : Asdfasdf
             * time : 1496905258000
             * staye2 : 1
             * topUserID : null
             * userName : 张三
             * money2 : 4000.0
             * orderId : null
             * userId2 : 1
             */

            private int id;
            private int userId;
            private Object shopId;
            private double money;
            private int state;
            private int type;
            private int type2;
            private String remark;
            private long time;
            private int staye2;
            private Object topUserID;
            private String userName;
            private double money2;
            private Object orderId;
            private int userId2;

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

            public Object getShopId() {
                return shopId;
            }

            public void setShopId(Object shopId) {
                this.shopId = shopId;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
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

            public Object getTopUserID() {
                return topUserID;
            }

            public void setTopUserID(Object topUserID) {
                this.topUserID = topUserID;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public double getMoney2() {
                return money2;
            }

            public void setMoney2(double money2) {
                this.money2 = money2;
            }

            public Object getOrderId() {
                return orderId;
            }

            public void setOrderId(Object orderId) {
                this.orderId = orderId;
            }

            public int getUserId2() {
                return userId2;
            }

            public void setUserId2(int userId2) {
                this.userId2 = userId2;
            }
        }
    }
}
