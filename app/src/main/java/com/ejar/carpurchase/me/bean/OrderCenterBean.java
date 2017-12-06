package com.ejar.carpurchase.me.bean;

import java.util.List;

/**
 * Created by wxd1 on 2017/6/13.
 */

public class OrderCenterBean {

    /**
     * msg : 前台用户订单报备展示成功
     * code : 200
     * userreport : {"offset":0,"limit":2147483647,"total":1,"size":10,"pages":1,"current":1,"searchCount":true,"openSort":true,"optimizeCount":false,"orderByField":null,"records":[{"id":"14973171810915725700","shopName":null,"account":"18111111111","userId":38,"shopId":null,"money":null,"moneyFactorge":null,"redMoney":null,"redMoneyFactorge":null,"state":1,"type":null,"deductMoney":null,"time":1497317181000,"remark":null,"name":null,"phone":null,"type2":null,"waresName":"111","waresNumber":null}],"condition":null,"offsetCurrent":0,"asc":true}
     */

    private String msg;
    private int code;
    private UserreportBean userreport;

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

    public UserreportBean getUserreport() {
        return userreport;
    }

    public void setUserreport(UserreportBean userreport) {
        this.userreport = userreport;
    }

    public static class UserreportBean {
        /**
         * offset : 0
         * limit : 2147483647
         * total : 1
         * size : 10
         * pages : 1
         * current : 1
         * searchCount : true
         * openSort : true
         * optimizeCount : false
         * orderByField : null
         * records : [{"id":"14973171810915725700","shopName":null,"account":"18111111111","userId":38,"shopId":null,"money":null,"moneyFactorge":null,"redMoney":null,"redMoneyFactorge":null,"state":1,"type":null,"deductMoney":null,"time":1497317181000,"remark":null,"name":null,"phone":null,"type2":null,"waresName":"111","waresNumber":null}]
         * condition : null
         * offsetCurrent : 0
         * asc : true
         */

        private int offset;
        private int limit;
        private int total;
        private int size;
        private int pages;
        private int current;
        private boolean searchCount;
        private boolean openSort;
        private boolean optimizeCount;
        private Object orderByField;
        private Object condition;
        private int offsetCurrent;
        private boolean asc;
        private List<RecordsBean> records;

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public boolean isSearchCount() {
            return searchCount;
        }

        public void setSearchCount(boolean searchCount) {
            this.searchCount = searchCount;
        }

        public boolean isOpenSort() {
            return openSort;
        }

        public void setOpenSort(boolean openSort) {
            this.openSort = openSort;
        }

        public boolean isOptimizeCount() {
            return optimizeCount;
        }

        public void setOptimizeCount(boolean optimizeCount) {
            this.optimizeCount = optimizeCount;
        }

        public Object getOrderByField() {
            return orderByField;
        }

        public void setOrderByField(Object orderByField) {
            this.orderByField = orderByField;
        }

        public Object getCondition() {
            return condition;
        }

        public void setCondition(Object condition) {
            this.condition = condition;
        }

        public int getOffsetCurrent() {
            return offsetCurrent;
        }

        public void setOffsetCurrent(int offsetCurrent) {
            this.offsetCurrent = offsetCurrent;
        }

        public boolean isAsc() {
            return asc;
        }

        public void setAsc(boolean asc) {
            this.asc = asc;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class RecordsBean {


            /**
             * id : 14973171810915725700
             * shopName :
             * account : 18111111111
             * userId : 38
             * shopId :
             * money :
             * moneyFactorge :
             * redMoney :
             * redMoneyFactorge :
             * state : 1
             * type :
             * deductMoney :
             * time : 1497317181000
             * remark :
             * name :
             * phone :
             * type2 :
             * waresName : 111
             * waresNumber :
             */

            private String id;
            private String shopName;
            private String account;
            private int userId;
            private String shopId;
            private String money;
            private String moneyFactorge;
            private String redMoney;
            private String redMoneyFactorge;
            private int state;
            private String type;
            private String deductMoney;
            private long time;
            private String remark;
            private String name;
            private String phone;
            private String type2;
            private String waresName;
            private String waresNumber;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
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

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getMoneyFactorge() {
                return moneyFactorge;
            }

            public void setMoneyFactorge(String moneyFactorge) {
                this.moneyFactorge = moneyFactorge;
            }

            public String getRedMoney() {
                return redMoney;
            }

            public void setRedMoney(String redMoney) {
                this.redMoney = redMoney;
            }

            public String getRedMoneyFactorge() {
                return redMoneyFactorge;
            }

            public void setRedMoneyFactorge(String redMoneyFactorge) {
                this.redMoneyFactorge = redMoneyFactorge;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDeductMoney() {
                return deductMoney;
            }

            public void setDeductMoney(String deductMoney) {
                this.deductMoney = deductMoney;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getType2() {
                return type2;
            }

            public void setType2(String type2) {
                this.type2 = type2;
            }

            public String getWaresName() {
                return waresName;
            }

            public void setWaresName(String waresName) {
                this.waresName = waresName;
            }

            public String getWaresNumber() {
                return waresNumber;
            }

            public void setWaresNumber(String waresNumber) {
                this.waresNumber = waresNumber;
            }
        }
    }
}
