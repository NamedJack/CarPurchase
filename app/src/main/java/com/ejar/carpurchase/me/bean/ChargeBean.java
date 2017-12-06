package com.ejar.carpurchase.me.bean;

import java.util.List;

/**
 * Created by wxd1 on 2017/5/31.
 */

public class ChargeBean {
    /**
     * code : 200
     * record : {"offset":0,"limit":2147483647,"total":6,"size":10,"pages":1,"current":1,"searchCount":true,"openSort":true,"optimizeCount":false,"orderByField":null,"records":[{"money":1000000.82,"phone":"13689682940","name":"13689682940","adminId":0,"ordernumber":"14974976015414168128","remark":"cucumber","id":20,"time":1497497602000,"state":1,"type":1,"userId":40},{"money":1000000.82,"phone":"13689682940","name":"13689682940","adminId":0,"ordernumber":"14974975982249229745","remark":"cucumber","id":19,"time":1497497598000,"state":1,"type":1,"userId":40},{"money":1.0001E7,"phone":"13689682940","name":"13689682940","adminId":0,"ordernumber":"14974975195668552595","remark":"hvyvuve","id":18,"time":1497497520000,"state":1,"type":1,"userId":40},{"money":1.0001E7,"phone":"13689682940","name":"13689682940","adminId":0,"ordernumber":"14974975141874464494","remark":"hvyvuve","id":17,"time":1497497514000,"state":1,"type":1,"userId":40},{"money":1000100,"phone":"13689682940","name":"13689682940","adminId":0,"ordernumber":"14974970780703301840","remark":"jiangs","id":16,"time":1497497078000,"state":1,"type":1,"userId":40},{"money":100,"phone":"13689682940","name":"13689682940","adminId":0,"ordernumber":"1497496324875478091","remark":"vhhh","id":15,"time":1497496325000,"state":1,"type":1,"userId":40}],"condition":null,"offsetCurrent":0,"asc":true}
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
         * total : 6
         * size : 10
         * pages : 1
         * current : 1
         * searchCount : true
         * openSort : true
         * optimizeCount : false
         * orderByField : null
         * records : [{"money":1000000.82,"phone":"13689682940","name":"13689682940","adminId":0,"ordernumber":"14974976015414168128","remark":"cucumber","id":20,"time":1497497602000,"state":1,"type":1,"userId":40},{"money":1000000.82,"phone":"13689682940","name":"13689682940","adminId":0,"ordernumber":"14974975982249229745","remark":"cucumber","id":19,"time":1497497598000,"state":1,"type":1,"userId":40},{"money":1.0001E7,"phone":"13689682940","name":"13689682940","adminId":0,"ordernumber":"14974975195668552595","remark":"hvyvuve","id":18,"time":1497497520000,"state":1,"type":1,"userId":40},{"money":1.0001E7,"phone":"13689682940","name":"13689682940","adminId":0,"ordernumber":"14974975141874464494","remark":"hvyvuve","id":17,"time":1497497514000,"state":1,"type":1,"userId":40},{"money":1000100,"phone":"13689682940","name":"13689682940","adminId":0,"ordernumber":"14974970780703301840","remark":"jiangs","id":16,"time":1497497078000,"state":1,"type":1,"userId":40},{"money":100,"phone":"13689682940","name":"13689682940","adminId":0,"ordernumber":"1497496324875478091","remark":"vhhh","id":15,"time":1497496325000,"state":1,"type":1,"userId":40}]
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
             * money : 1000000.82
             * phone : 13689682940
             * name : 13689682940
             * adminId : 0
             * ordernumber : 14974976015414168128
             * remark : cucumber
             * id : 20
             * time : 1497497602000
             * state : 1
             * type : 1
             * userId : 40
             */

            private double money;
            private String phone;
            private String name;
            private int adminId;
            private String ordernumber;
            private String remark;
            private String remark2;
            private int id;
            private long time;
            private int state;
            private int type;
            private int userId;

            public String getRemark2() {
                return remark2;
            }

            public void setRemark2(String remark2) {
                this.remark2 = remark2;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getAdminId() {
                return adminId;
            }

            public void setAdminId(int adminId) {
                this.adminId = adminId;
            }

            public String getOrdernumber() {
                return ordernumber;
            }

            public void setOrdernumber(String ordernumber) {
                this.ordernumber = ordernumber;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
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
        }
    }


//    /**
//     * code : 200
//     * record : {"offset":0,"limit":2147483647,"total":3,"size":10,"pages":1,"current":1,"searchCount":true,"openSort":true,"optimizeCount":false,"orderByField":null,"records":[{"money":999,"phone":"18113401543","name":"bvcvb","adminId":0,"remark":"他健健康康","id":7,"time":1496826940000,"state":1,"type":1,"userId":19},{"money":188,"phone":"18113401543","name":"bvcvb","adminId":0,"remark":"途径","id":6,"time":1496824280000,"state":1,"type":1,"userId":19},{"money":55,"phone":"18113401543","name":"bvcvb","adminId":0,"remark":"Be","id":2,"time":1496738331000,"state":1,"type":1,"userId":19}],"condition":null,"asc":true,"offsetCurrent":0}
//     */
//
//    private int code;
//    private RecordBean record;
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public RecordBean getRecord() {
//        return record;
//    }
//
//    public void setRecord(RecordBean record) {
//        this.record = record;
//    }
//
//    public static class RecordBean {
//        /**
//         * offset : 0
//         * limit : 2147483647
//         * total : 3
//         * size : 10
//         * pages : 1
//         * current : 1
//         * searchCount : true
//         * openSort : true
//         * optimizeCount : false
//         * orderByField : null
//         * records : [{"money":999,"phone":"18113401543","name":"bvcvb","adminId":0,"remark":"他健健康康","id":7,"time":1496826940000,"state":1,"type":1,"userId":19},{"money":188,"phone":"18113401543","name":"bvcvb","adminId":0,"remark":"途径","id":6,"time":1496824280000,"state":1,"type":1,"userId":19},{"money":55,"phone":"18113401543","name":"bvcvb","adminId":0,"remark":"Be","id":2,"time":1496738331000,"state":1,"type":1,"userId":19}]
//         * condition : null
//         * asc : true
//         * offsetCurrent : 0
//         */
//
//        private int offset;
//        private int limit;
//        private int total;
//        private int size;
//        private int pages;
//        private int current;
//        private boolean searchCount;
//        private boolean openSort;
//        private boolean optimizeCount;
//        private Object orderByField;
//        private Object condition;
//        private boolean asc;
//        private int offsetCurrent;
//        private List<RecordsBean> records;
//
//        public int getOffset() {
//            return offset;
//        }
//
//        public void setOffset(int offset) {
//            this.offset = offset;
//        }
//
//        public int getLimit() {
//            return limit;
//        }
//
//        public void setLimit(int limit) {
//            this.limit = limit;
//        }
//
//        public int getTotal() {
//            return total;
//        }
//
//        public void setTotal(int total) {
//            this.total = total;
//        }
//
//        public int getSize() {
//            return size;
//        }
//
//        public void setSize(int size) {
//            this.size = size;
//        }
//
//        public int getPages() {
//            return pages;
//        }
//
//        public void setPages(int pages) {
//            this.pages = pages;
//        }
//
//        public int getCurrent() {
//            return current;
//        }
//
//        public void setCurrent(int current) {
//            this.current = current;
//        }
//
//        public boolean isSearchCount() {
//            return searchCount;
//        }
//
//        public void setSearchCount(boolean searchCount) {
//            this.searchCount = searchCount;
//        }
//
//        public boolean isOpenSort() {
//            return openSort;
//        }
//
//        public void setOpenSort(boolean openSort) {
//            this.openSort = openSort;
//        }
//
//        public boolean isOptimizeCount() {
//            return optimizeCount;
//        }
//
//        public void setOptimizeCount(boolean optimizeCount) {
//            this.optimizeCount = optimizeCount;
//        }
//
//        public Object getOrderByField() {
//            return orderByField;
//        }
//
//        public void setOrderByField(Object orderByField) {
//            this.orderByField = orderByField;
//        }
//
//        public Object getCondition() {
//            return condition;
//        }
//
//        public void setCondition(Object condition) {
//            this.condition = condition;
//        }
//
//        public boolean isAsc() {
//            return asc;
//        }
//
//        public void setAsc(boolean asc) {
//            this.asc = asc;
//        }
//
//        public int getOffsetCurrent() {
//            return offsetCurrent;
//        }
//
//        public void setOffsetCurrent(int offsetCurrent) {
//            this.offsetCurrent = offsetCurrent;
//        }
//
//        public List<RecordsBean> getRecords() {
//            return records;
//        }
//
//        public void setRecords(List<RecordsBean> records) {
//            this.records = records;
//        }
//
//        public static class RecordsBean {
//            /**
//             * money : 999
//             * phone : 18113401543
//             * name : bvcvb
//             * adminId : 0
//             * remark : 他健健康康
//             * id : 7
//             * time : 1496826940000
//             * state : 1
//             * type : 1
//             * userId : 19
//             * ordernumber
//             */
//
//
//            private String  ordernumber;
//
//            public String getOrdernumber() {
//                return ordernumber;
//            }
//
//            public void setOrdernumber(String ordernumber) {
//                this.ordernumber = ordernumber;
//            }
//
//            private int money;
//            private String phone;
//            private String name;
//            private int adminId;
//            private String remark;
//            private int id;
//            private long time;
//            private int state;
//            private int type;
//            private int userId;
//
//            public int getMoney() {
//                return money;
//            }
//
//            public void setMoney(int money) {
//                this.money = money;
//            }
//
//            public String getPhone() {
//                return phone;
//            }
//
//            public void setPhone(String phone) {
//                this.phone = phone;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            public int getAdminId() {
//                return adminId;
//            }
//
//            public void setAdminId(int adminId) {
//                this.adminId = adminId;
//            }
//
//            public String getRemark() {
//                return remark;
//            }
//
//            public void setRemark(String remark) {
//                this.remark = remark;
//            }
//
//            public int getId() {
//                return id;
//            }
//
//            public void setId(int id) {
//                this.id = id;
//            }
//
//            public long getTime() {
//                return time;
//            }
//
//            public void setTime(long time) {
//                this.time = time;
//            }
//
//            public int getState() {
//                return state;
//            }
//
//            public void setState(int state) {
//                this.state = state;
//            }
//
//            public int getType() {
//                return type;
//            }
//
//            public void setType(int type) {
//                this.type = type;
//            }
//
//            public int getUserId() {
//                return userId;
//            }
//
//            public void setUserId(int userId) {
//                this.userId = userId;
//            }
//        }
//    }



}
