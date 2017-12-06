package com.ejar.carpurchase.me.bean;

import java.util.List;

/**
 * Created by wxd1 on 2017/6/9.
 */

public class BillFormBean {


    /**
     * msg : 前台订单报备展示成功
     * code : 200
     * report : {"offset":0,"limit":2147483647,"total":4,"size":10,"pages":1,"current":1,"searchCount":true,"openSort":true,"optimizeCount":false,"orderByField":null,"records":[{"id":"14974358216424731669","shopName":"yangxiao      yangxiao","account":"17313623814","userId":"","shopId":20,"money":9,"moneyFactorge":0.45,"redMoney":1,"redMoneyFactorge":0.05,"state":1,"type":1,"deductMoney":1.1,"time":1497435822000,"remark":"","name":"17313623814","phone":"","type2":12,"waresName":"记录","waresNumber":1}],"condition":"","asc":true,"offsetCurrent":0}
     */

    private String msg;
    private int code;
    private ReportBean report;

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

    public ReportBean getReport() {
        return report;
    }

    public void setReport(ReportBean report) {
        this.report = report;
    }

    public static class ReportBean {
        /**
         * offset : 0
         * limit : 2147483647
         * total : 4
         * size : 10
         * pages : 1
         * current : 1
         * searchCount : true
         * openSort : true
         * optimizeCount : false
         * orderByField : null
         * records : [{"id":"14974358216424731669","shopName":"yangxiao      yangxiao","account":"17313623814","userId":"","shopId":20,"money":9,"moneyFactorge":0.45,"redMoney":1,"redMoneyFactorge":0.05,"state":1,"type":1,"deductMoney":1.1,"time":1497435822000,"remark":"","name":"17313623814","phone":"","type2":12,"waresName":"记录","waresNumber":1}]
         * condition :
         * asc : true
         * offsetCurrent : 0
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
        private String condition;
        private boolean asc;
        private int offsetCurrent;
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

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public boolean isAsc() {
            return asc;
        }

        public void setAsc(boolean asc) {
            this.asc = asc;
        }

        public int getOffsetCurrent() {
            return offsetCurrent;
        }

        public void setOffsetCurrent(int offsetCurrent) {
            this.offsetCurrent = offsetCurrent;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class RecordsBean {
            /**
             * id : 14974358216424731669
             * shopName : yangxiao      yangxiao
             * account : 17313623814
             * userId :
             * shopId : 20
             * money : 9
             * moneyFactorge : 0.45
             * redMoney : 1
             * redMoneyFactorge : 0.05
             * state : 1
             * type : 1
             * deductMoney : 1.1
             * time : 1497435822000
             * remark :
             * name : 17313623814
             * phone :
             * type2 : 12
             * waresName : 记录
             * waresNumber : 1
             */

            private String id;
            private String shopName;
            private String account;
            private String userId;
            private int shopId;
            private int money;
            private double moneyFactorge;
            private int redMoney;
            private double redMoneyFactorge;
            private int state;
            private int type;
            private double deductMoney;
            private long time;
            private String remark;
            private String name;
            private String phone;
            private int type2;
            private String waresName;
            private int waresNumber;

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

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public int getShopId() {
                return shopId;
            }

            public void setShopId(int shopId) {
                this.shopId = shopId;
            }

            public int getMoney() {
                return money;
            }

            public void setMoney(int money) {
                this.money = money;
            }

            public double getMoneyFactorge() {
                return moneyFactorge;
            }

            public void setMoneyFactorge(double moneyFactorge) {
                this.moneyFactorge = moneyFactorge;
            }

            public int getRedMoney() {
                return redMoney;
            }

            public void setRedMoney(int redMoney) {
                this.redMoney = redMoney;
            }

            public double getRedMoneyFactorge() {
                return redMoneyFactorge;
            }

            public void setRedMoneyFactorge(double redMoneyFactorge) {
                this.redMoneyFactorge = redMoneyFactorge;
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

            public double getDeductMoney() {
                return deductMoney;
            }

            public void setDeductMoney(double deductMoney) {
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

            public int getType2() {
                return type2;
            }

            public void setType2(int type2) {
                this.type2 = type2;
            }

            public String getWaresName() {
                return waresName;
            }

            public void setWaresName(String waresName) {
                this.waresName = waresName;
            }

            public int getWaresNumber() {
                return waresNumber;
            }

            public void setWaresNumber(int waresNumber) {
                this.waresNumber = waresNumber;
            }
        }
    }
}
