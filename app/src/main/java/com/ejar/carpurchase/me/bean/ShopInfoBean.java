package com.ejar.carpurchase.me.bean;

import java.io.Serializable;

public class ShopInfoBean implements Serializable {

    /**
     * msg : 查看成功
     * code : 200
     * data : {"id":8,"state":1,"userId":16,"shopName":"好的好的好","companyName":"多喝点回电话","idCardNumber":"","name":"","log":"assets/upload/img/201701/12545487446447.jpg","phone":"18683656595","lng":"","lat":"","idCardFrontPhoto":"","idCardNegativePhoto":"","province":"北京","provinceAdcode":"1","city":"北京市","street":"","streetAdcode":"","adders":"","cityAdcode":"52805","county":"延庆县","countyAdcode":"3065","remark":"","ratio":"","typeId":7,"time":1496745571000,"handletime":"","typeName":"","qrcode":"","shopdetails":"","businessNo":"123454649","businessPhoto":"/static/upload/entityimg3bb43f25-b04d-4b90-a67d-2552cfcaf285.jpg","allIncome":0,"turnover":0}
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

    public static class DataBean implements Serializable{
        /**
         * id : 8
         * state : 1
         * userId : 16
         * shopName : 好的好的好
         * companyName : 多喝点回电话
         * idCardNumber :
         * name :
         * log : assets/upload/img/201701/12545487446447.jpg
         * phone : 18683656595
         * lng :
         * lat :
         * idCardFrontPhoto :
         * idCardNegativePhoto :
         * province : 北京
         * provinceAdcode : 1
         * city : 北京市
         * street :
         * streetAdcode :
         * adders :
         * cityAdcode : 52805
         * county : 延庆县
         * countyAdcode : 3065
         * remark :
         * ratio :
         * typeId : 7
         * time : 1496745571000
         * handletime :
         * typeName :
         * qrcode :
         * shopdetails :
         * businessNo : 123454649
         * businessPhoto : /static/upload/entityimg3bb43f25-b04d-4b90-a67d-2552cfcaf285.jpg
         * allIncome : 0
         * turnover : 0
         */

        private int id;
        private int state;
        private int userId;
        private String shopName;
        private String companyName;
        private String idCardNumber;
        private String name;
        private String log;
        private String phone;
        private String lng;
        private String lat;
        private String idCardFrontPhoto;
        private String idCardNegativePhoto;
        private String province;
        private String provinceAdcode;
        private String city;
        private String street;
        private String streetAdcode;
        private String adders;
        private String cityAdcode;
        private String county;
        private String countyAdcode;
        private String remark;
        private String ratio;
        private int typeId;
        private long time;
        private String handletime;
        private String typeName;
        private String qrcode;
        private String shopdetails;
        private String businessNo;
        private String businessPhoto;
        private int allIncome;
        private int turnover;

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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getIdCardNumber() {
            return idCardNumber;
        }

        public void setIdCardNumber(String idCardNumber) {
            this.idCardNumber = idCardNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getIdCardFrontPhoto() {
            return idCardFrontPhoto;
        }

        public void setIdCardFrontPhoto(String idCardFrontPhoto) {
            this.idCardFrontPhoto = idCardFrontPhoto;
        }

        public String getIdCardNegativePhoto() {
            return idCardNegativePhoto;
        }

        public void setIdCardNegativePhoto(String idCardNegativePhoto) {
            this.idCardNegativePhoto = idCardNegativePhoto;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getProvinceAdcode() {
            return provinceAdcode;
        }

        public void setProvinceAdcode(String provinceAdcode) {
            this.provinceAdcode = provinceAdcode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getStreetAdcode() {
            return streetAdcode;
        }

        public void setStreetAdcode(String streetAdcode) {
            this.streetAdcode = streetAdcode;
        }

        public String getAdders() {
            return adders;
        }

        public void setAdders(String adders) {
            this.adders = adders;
        }

        public String getCityAdcode() {
            return cityAdcode;
        }

        public void setCityAdcode(String cityAdcode) {
            this.cityAdcode = cityAdcode;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getCountyAdcode() {
            return countyAdcode;
        }

        public void setCountyAdcode(String countyAdcode) {
            this.countyAdcode = countyAdcode;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getHandletime() {
            return handletime;
        }

        public void setHandletime(String handletime) {
            this.handletime = handletime;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public String getShopdetails() {
            return shopdetails;
        }

        public void setShopdetails(String shopdetails) {
            this.shopdetails = shopdetails;
        }

        public String getBusinessNo() {
            return businessNo;
        }

        public void setBusinessNo(String businessNo) {
            this.businessNo = businessNo;
        }

        public String getBusinessPhoto() {
            return businessPhoto;
        }

        public void setBusinessPhoto(String businessPhoto) {
            this.businessPhoto = businessPhoto;
        }

        public int getAllIncome() {
            return allIncome;
        }

        public void setAllIncome(int allIncome) {
            this.allIncome = allIncome;
        }

        public int getTurnover() {
            return turnover;
        }

        public void setTurnover(int turnover) {
            this.turnover = turnover;
        }
    }
}