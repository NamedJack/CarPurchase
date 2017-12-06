package com.ejar.carpurchase.me.bean;

import java.util.List;

/**
 * Created by wxd1 on 2017/6/27.
 */

public class AgentSeePeopleBean {

    /**
     * msg : 查看成功
     * code : 200
     * data : {"offset":0,"limit":2147483647,"total":2,"size":10,"pages":1,"current":1,"searchCount":true,"openSort":true,"optimizeCount":false,"orderByField":null,"records":[{"id":17,"account":"18780547853","password":null,"operate":null,"name":"杨枭","parentId":null,"ratio":null,"phone":"18780547853","uniqueId":1234567,"idCard":null,"totalmoney":null,"money":null,"offlineAllRebate":null,"offlineRebate":null,"allMoney":null,"red":null,"redAllMoney":null,"redMoney":null,"pileMoney":null,"state":4,"topUser":1,"payPassword":null,"email":"974501076@qq.com","qq":"888888","province":"四川","city":"绵阳市","provinceAdcode":22,"cityAdcode":1960,"county":"涪城区","street":"南山街道","countyAdcode":38574,"streetAdcode":54429,"detailedAddress":null,"userHead":"/static/userimg/img/2fd3ba66-42be-4608-99c2-0a03587e9248.jpg","qrcode":null,"time":null,"angentAllMoney":null,"angentMoney":null},{"id":23,"account":"1511561561","password":null,"operate":null,"name":null,"parentId":null,"ratio":null,"phone":null,"uniqueId":null,"idCard":null,"totalmoney":null,"money":null,"offlineAllRebate":null,"offlineRebate":null,"allMoney":null,"red":null,"redAllMoney":null,"redMoney":null,"pileMoney":null,"state":1,"topUser":null,"payPassword":null,"email":null,"qq":null,"province":"四川","city":"绵阳市","provinceAdcode":22,"cityAdcode":1960,"county":"涪城区","street":"南山街道","countyAdcode":38574,"streetAdcode":54429,"detailedAddress":null,"userHead":null,"qrcode":null,"time":null,"angentAllMoney":null,"angentMoney":null}],"condition":null,"asc":true,"offsetCurrent":0}
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
         * records : [{"id":17,"account":"18780547853","password":null,"operate":null,"name":"杨枭","parentId":null,"ratio":null,"phone":"18780547853","uniqueId":1234567,"idCard":null,"totalmoney":null,"money":null,"offlineAllRebate":null,"offlineRebate":null,"allMoney":null,"red":null,"redAllMoney":null,"redMoney":null,"pileMoney":null,"state":4,"topUser":1,"payPassword":null,"email":"974501076@qq.com","qq":"888888","province":"四川","city":"绵阳市","provinceAdcode":22,"cityAdcode":1960,"county":"涪城区","street":"南山街道","countyAdcode":38574,"streetAdcode":54429,"detailedAddress":null,"userHead":"/static/userimg/img/2fd3ba66-42be-4608-99c2-0a03587e9248.jpg","qrcode":null,"time":null,"angentAllMoney":null,"angentMoney":null},{"id":23,"account":"1511561561","password":null,"operate":null,"name":null,"parentId":null,"ratio":null,"phone":null,"uniqueId":null,"idCard":null,"totalmoney":null,"money":null,"offlineAllRebate":null,"offlineRebate":null,"allMoney":null,"red":null,"redAllMoney":null,"redMoney":null,"pileMoney":null,"state":1,"topUser":null,"payPassword":null,"email":null,"qq":null,"province":"四川","city":"绵阳市","provinceAdcode":22,"cityAdcode":1960,"county":"涪城区","street":"南山街道","countyAdcode":38574,"streetAdcode":54429,"detailedAddress":null,"userHead":null,"qrcode":null,"time":null,"angentAllMoney":null,"angentMoney":null}]
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
             * id : 17
             * account : 18780547853
             * password : null
             * operate : null
             * name : 杨枭
             * parentId : null
             * ratio : null
             * phone : 18780547853
             * uniqueId : 1234567
             * idCard : null
             * totalmoney : null
             * money : null
             * offlineAllRebate : null
             * offlineRebate : null
             * allMoney : null
             * red : null
             * redAllMoney : null
             * redMoney : null
             * pileMoney : null
             * state : 4
             * topUser : 1
             * payPassword : null
             * email : 974501076@qq.com
             * qq : 888888
             * province : 四川
             * city : 绵阳市
             * provinceAdcode : 22
             * cityAdcode : 1960
             * county : 涪城区
             * street : 南山街道
             * countyAdcode : 38574
             * streetAdcode : 54429
             * detailedAddress : null
             * userHead : /static/userimg/img/2fd3ba66-42be-4608-99c2-0a03587e9248.jpg
             * qrcode : null
             * time : null
             * angentAllMoney : null
             * angentMoney : null
             */

            private int id;
            private String account;
            private Object password;
            private Object operate;
            private String name;
            private Object parentId;
            private Object ratio;
            private String phone;
            private int uniqueId;
            private Object idCard;
            private Object totalmoney;
            private Object money;
            private Object offlineAllRebate;
            private Object offlineRebate;
            private Object allMoney;
            private Object red;
            private Object redAllMoney;
            private Object redMoney;
            private Object pileMoney;
            private int state;
            private int topUser;
            private Object payPassword;
            private String email;
            private String qq;
            private String province;
            private String city;
            private int provinceAdcode;
            private int cityAdcode;
            private String county;
            private String street;
            private int countyAdcode;
            private int streetAdcode;
            private Object detailedAddress;
            private String userHead;
            private Object qrcode;
            private Object time;
            private Object angentAllMoney;
            private Object angentMoney;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public Object getPassword() {
                return password;
            }

            public void setPassword(Object password) {
                this.password = password;
            }

            public Object getOperate() {
                return operate;
            }

            public void setOperate(Object operate) {
                this.operate = operate;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getParentId() {
                return parentId;
            }

            public void setParentId(Object parentId) {
                this.parentId = parentId;
            }

            public Object getRatio() {
                return ratio;
            }

            public void setRatio(Object ratio) {
                this.ratio = ratio;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getUniqueId() {
                return uniqueId;
            }

            public void setUniqueId(int uniqueId) {
                this.uniqueId = uniqueId;
            }

            public Object getIdCard() {
                return idCard;
            }

            public void setIdCard(Object idCard) {
                this.idCard = idCard;
            }

            public Object getTotalmoney() {
                return totalmoney;
            }

            public void setTotalmoney(Object totalmoney) {
                this.totalmoney = totalmoney;
            }

            public Object getMoney() {
                return money;
            }

            public void setMoney(Object money) {
                this.money = money;
            }

            public Object getOfflineAllRebate() {
                return offlineAllRebate;
            }

            public void setOfflineAllRebate(Object offlineAllRebate) {
                this.offlineAllRebate = offlineAllRebate;
            }

            public Object getOfflineRebate() {
                return offlineRebate;
            }

            public void setOfflineRebate(Object offlineRebate) {
                this.offlineRebate = offlineRebate;
            }

            public Object getAllMoney() {
                return allMoney;
            }

            public void setAllMoney(Object allMoney) {
                this.allMoney = allMoney;
            }

            public Object getRed() {
                return red;
            }

            public void setRed(Object red) {
                this.red = red;
            }

            public Object getRedAllMoney() {
                return redAllMoney;
            }

            public void setRedAllMoney(Object redAllMoney) {
                this.redAllMoney = redAllMoney;
            }

            public Object getRedMoney() {
                return redMoney;
            }

            public void setRedMoney(Object redMoney) {
                this.redMoney = redMoney;
            }

            public Object getPileMoney() {
                return pileMoney;
            }

            public void setPileMoney(Object pileMoney) {
                this.pileMoney = pileMoney;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int getTopUser() {
                return topUser;
            }

            public void setTopUser(int topUser) {
                this.topUser = topUser;
            }

            public Object getPayPassword() {
                return payPassword;
            }

            public void setPayPassword(Object payPassword) {
                this.payPassword = payPassword;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public int getProvinceAdcode() {
                return provinceAdcode;
            }

            public void setProvinceAdcode(int provinceAdcode) {
                this.provinceAdcode = provinceAdcode;
            }

            public int getCityAdcode() {
                return cityAdcode;
            }

            public void setCityAdcode(int cityAdcode) {
                this.cityAdcode = cityAdcode;
            }

            public String getCounty() {
                return county;
            }

            public void setCounty(String county) {
                this.county = county;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public int getCountyAdcode() {
                return countyAdcode;
            }

            public void setCountyAdcode(int countyAdcode) {
                this.countyAdcode = countyAdcode;
            }

            public int getStreetAdcode() {
                return streetAdcode;
            }

            public void setStreetAdcode(int streetAdcode) {
                this.streetAdcode = streetAdcode;
            }

            public Object getDetailedAddress() {
                return detailedAddress;
            }

            public void setDetailedAddress(Object detailedAddress) {
                this.detailedAddress = detailedAddress;
            }

            public String getUserHead() {
                return userHead;
            }

            public void setUserHead(String userHead) {
                this.userHead = userHead;
            }

            public Object getQrcode() {
                return qrcode;
            }

            public void setQrcode(Object qrcode) {
                this.qrcode = qrcode;
            }

            public Object getTime() {
                return time;
            }

            public void setTime(Object time) {
                this.time = time;
            }

            public Object getAngentAllMoney() {
                return angentAllMoney;
            }

            public void setAngentAllMoney(Object angentAllMoney) {
                this.angentAllMoney = angentAllMoney;
            }

            public Object getAngentMoney() {
                return angentMoney;
            }

            public void setAngentMoney(Object angentMoney) {
                this.angentMoney = angentMoney;
            }
        }
    }
}
