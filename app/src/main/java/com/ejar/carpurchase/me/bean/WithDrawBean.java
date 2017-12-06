package com.ejar.carpurchase.me.bean;

import java.util.List;

public class WithDrawBean {


    /**
     * code : 200
     * record : {"offset":0,"limit":2147483647,"total":2,"size":10,"pages":1,"current":1,"searchCount":true,"openSort":true,"optimizeCount":false,"orderByField":null,"records":[{"lnterest":77.5,"remark":"你","bankName":"哦logo","id":24,"state":1,"type":2,"userId":16,"account":"454846498935558","carryMoeny":155,"carrytime":1496906181000},{"lnterest":77.5,"remark":"你好","bankName":"哦logo","id":23,"state":1,"type":1,"userId":16,"account":"454846498935558","carryMoeny":155,"carrytime":1496906139000}],"condition":null,"asc":true,"offsetCurrent":0}
     */

    private int code;
    private RecordBean record;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public RecordBean getRecord() {
        return record;
    }

    public void setRecord(RecordBean record) {
        this.record = record;
    }

    public static class RecordBean {
        /**
         * offset : 0
         * limit : 2147483647
         * total : 2
         * size : 10
         * pages : 1
         * current : 1
         * searchCount : true
         * openSort : true
         * optimizeCount : false
         * orderByField : null
         * records : [{"lnterest":77.5,"remark":"你","bankName":"哦logo","id":24,"state":1,"type":2,"userId":16,"account":"454846498935558","carryMoeny":155,"carrytime":1496906181000},{"lnterest":77.5,"remark":"你好","bankName":"哦logo","id":23,"state":1,"type":1,"userId":16,"account":"454846498935558","carryMoeny":155,"carrytime":1496906139000}]
         * condition : null
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
        private Object condition;
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

        public Object getCondition() {
            return condition;
        }

        public void setCondition(Object condition) {
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
             * lnterest : 77.5
             * remark : 你
             * bankName : 哦logo
             * id : 24
             * state : 1
             * type : 2
             * userId : 16
             * account : 454846498935558
             * carryMoeny : 155
             * carrytime : 1496906181000
             */

            private double lnterest;
            private String remark;

            public String getRemark2() {
                return remark2;
            }

            public void setRemark2(String remark2) {
                this.remark2 = remark2;
            }

            private String remark2;
            private String bankName;
            private int id;
            private int state;
            private int type;
            private int userId;
            private String account;
            private double carryMoeny;
            private long carrytime;

            public double getLnterest() {
                return lnterest;
            }

            public void setLnterest(double lnterest) {
                this.lnterest = lnterest;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public double getCarryMoeny() {
                return carryMoeny;
            }

            public void setCarryMoeny(double carryMoeny) {
                this.carryMoeny = carryMoeny;
            }

            public long getCarrytime() {
                return carrytime;
            }

            public void setCarrytime(long carrytime) {
                this.carrytime = carrytime;
            }
        }
    }
}