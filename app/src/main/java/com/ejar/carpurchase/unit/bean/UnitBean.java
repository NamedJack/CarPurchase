package com.ejar.carpurchase.unit.bean;

import java.util.List;

/**
 * Created by wxd1 on 2017/5/27.
 */

public class UnitBean {

    /**
     * msg : 查询成功
     * code : 200
     * data : {"shop":[{"id":1,"state":null,"userId":null,"shopName":"京东","companyName":null,"idCardNumber":null,"name":null,"log":"assets/upload/img/201701/12545487446447.jpg","phone":null,"lng":null,"lat":null,"idCardFrontPhoto":null,"idCardNegativePhoto":null,"province":null,"provinceAdcode":null,"city":null,"street":null,"streetAdcode":null,"adders":null,"cityAdcode":null,"county":null,"countyAdcode":null,"remark":null,"ratio":null,"typeId":7,"time":null,"handletime":null,"typeName":"百货->电器品->液晶电视","qrcode":null,"shopdetails":null,"businessNo":null,"businessPhoto":null,"allIncome":null,"turnover":null},{"id":2,"state":null,"userId":null,"shopName":"淘宝","companyName":null,"idCardNumber":null,"name":null,"log":"assets/upload/img/201701/12545487446447.jpg","phone":null,"lng":null,"lat":null,"idCardFrontPhoto":null,"idCardNegativePhoto":null,"province":null,"provinceAdcode":null,"city":null,"street":null,"streetAdcode":null,"adders":null,"cityAdcode":null,"county":null,"countyAdcode":null,"remark":null,"ratio":null,"typeId":7,"time":null,"handletime":null,"typeName":"百货->电器品->液晶电视","qrcode":null,"shopdetails":null,"businessNo":null,"businessPhoto":null,"allIncome":null,"turnover":null},{"id":5,"state":null,"userId":null,"shopName":"赶集网","companyName":null,"idCardNumber":null,"name":null,"log":"assets/upload/img/201701/12545487446447.jpg","phone":null,"lng":null,"lat":null,"idCardFrontPhoto":null,"idCardNegativePhoto":null,"province":null,"provinceAdcode":null,"city":null,"street":null,"streetAdcode":null,"adders":null,"cityAdcode":null,"county":null,"countyAdcode":null,"remark":null,"ratio":null,"typeId":7,"time":null,"handletime":null,"typeName":"百货->电器品->液晶电视","qrcode":null,"shopdetails":null,"businessNo":null,"businessPhoto":null,"allIncome":null,"turnover":null}],"psge":{"pageNo":1,"startNum":0,"size":10,"totleNumber":9,"totlePage":1}}
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
         * shop : [{"id":1,"state":null,"userId":null,"shopName":"京东","companyName":null,"idCardNumber":null,"name":null,"log":"assets/upload/img/201701/12545487446447.jpg","phone":null,"lng":null,"lat":null,"idCardFrontPhoto":null,"idCardNegativePhoto":null,"province":null,"provinceAdcode":null,"city":null,"street":null,"streetAdcode":null,"adders":null,"cityAdcode":null,"county":null,"countyAdcode":null,"remark":null,"ratio":null,"typeId":7,"time":null,"handletime":null,"typeName":"百货->电器品->液晶电视","qrcode":null,"shopdetails":null,"businessNo":null,"businessPhoto":null,"allIncome":null,"turnover":null},{"id":2,"state":null,"userId":null,"shopName":"淘宝","companyName":null,"idCardNumber":null,"name":null,"log":"assets/upload/img/201701/12545487446447.jpg","phone":null,"lng":null,"lat":null,"idCardFrontPhoto":null,"idCardNegativePhoto":null,"province":null,"provinceAdcode":null,"city":null,"street":null,"streetAdcode":null,"adders":null,"cityAdcode":null,"county":null,"countyAdcode":null,"remark":null,"ratio":null,"typeId":7,"time":null,"handletime":null,"typeName":"百货->电器品->液晶电视","qrcode":null,"shopdetails":null,"businessNo":null,"businessPhoto":null,"allIncome":null,"turnover":null},{"id":5,"state":null,"userId":null,"shopName":"赶集网","companyName":null,"idCardNumber":null,"name":null,"log":"assets/upload/img/201701/12545487446447.jpg","phone":null,"lng":null,"lat":null,"idCardFrontPhoto":null,"idCardNegativePhoto":null,"province":null,"provinceAdcode":null,"city":null,"street":null,"streetAdcode":null,"adders":null,"cityAdcode":null,"county":null,"countyAdcode":null,"remark":null,"ratio":null,"typeId":7,"time":null,"handletime":null,"typeName":"百货->电器品->液晶电视","qrcode":null,"shopdetails":null,"businessNo":null,"businessPhoto":null,"allIncome":null,"turnover":null}]
         * psge : {"pageNo":1,"startNum":0,"size":10,"totleNumber":9,"totlePage":1}
         */

        private PsgeBean psge;
        private List<ShopBean> shop;

        public PsgeBean getPsge() {
            return psge;
        }

        public void setPsge(PsgeBean psge) {
            this.psge = psge;
        }

        public List<ShopBean> getShop() {
            return shop;
        }

        public void setShop(List<ShopBean> shop) {
            this.shop = shop;
        }

        public static class PsgeBean {
            /**
             * pageNo : 1
             * startNum : 0
             * size : 10
             * totleNumber : 9
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

        public static class ShopBean {
            /**
             * id : 1
             * state : null
             * userId : null
             * shopName : 京东
             * companyName : null
             * idCardNumber : null
             * name : null
             * log : assets/upload/img/201701/12545487446447.jpg
             * phone : null
             * lng : null
             * lat : null
             * idCardFrontPhoto : null
             * idCardNegativePhoto : null
             * province : null
             * provinceAdcode : null
             * city : null
             * street : null
             * streetAdcode : null
             * adders : null
             * cityAdcode : null
             * county : null
             * countyAdcode : null
             * remark : null
             * ratio : null
             * typeId : 7
             * time : null
             * handletime : null
             * typeName : 百货->电器品->液晶电视
             * qrcode : null
             * shopdetails : null
             * businessNo : null
             * businessPhoto : null
             * allIncome : null
             * turnover : null
             */

            private int id;
            private Object state;
            private Object userId;
            private String shopName;
            private Object companyName;
            private Object idCardNumber;
            private Object name;
            private String log;
            private String phone;
            private Object lng;
            private Object lat;
            private Object idCardFrontPhoto;
            private Object idCardNegativePhoto;
            private String province;
            private Object provinceAdcode;
            private String city;
            private String street;
            private Object streetAdcode;
            private Object adders;
            private Object cityAdcode;
            private String county;
            private Object countyAdcode;
            private Object remark;
            private Object ratio;
            private int typeId;
            private Object time;
            private Object handletime;
            private String typeName;
            private Object qrcode;
            private Object shopdetails;
            private Object businessNo;
            private Object businessPhoto;
            private Object allIncome;
            private Object turnover;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getState() {
                return state;
            }

            public void setState(Object state) {
                this.state = state;
            }

            public Object getUserId() {
                return userId;
            }

            public void setUserId(Object userId) {
                this.userId = userId;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public Object getCompanyName() {
                return companyName;
            }

            public void setCompanyName(Object companyName) {
                this.companyName = companyName;
            }

            public Object getIdCardNumber() {
                return idCardNumber;
            }

            public void setIdCardNumber(Object idCardNumber) {
                this.idCardNumber = idCardNumber;
            }

            public Object getName() {
                return name;
            }

            public void setName(Object name) {
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

            public Object getLng() {
                return lng;
            }

            public void setLng(Object lng) {
                this.lng = lng;
            }

            public Object getLat() {
                return lat;
            }

            public void setLat(Object lat) {
                this.lat = lat;
            }

            public Object getIdCardFrontPhoto() {
                return idCardFrontPhoto;
            }

            public void setIdCardFrontPhoto(Object idCardFrontPhoto) {
                this.idCardFrontPhoto = idCardFrontPhoto;
            }

            public Object getIdCardNegativePhoto() {
                return idCardNegativePhoto;
            }

            public void setIdCardNegativePhoto(Object idCardNegativePhoto) {
                this.idCardNegativePhoto = idCardNegativePhoto;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public Object getProvinceAdcode() {
                return provinceAdcode;
            }

            public void setProvinceAdcode(Object provinceAdcode) {
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

            public Object getStreetAdcode() {
                return streetAdcode;
            }

            public void setStreetAdcode(Object streetAdcode) {
                this.streetAdcode = streetAdcode;
            }

            public Object getAdders() {
                return adders;
            }

            public void setAdders(Object adders) {
                this.adders = adders;
            }

            public Object getCityAdcode() {
                return cityAdcode;
            }

            public void setCityAdcode(Object cityAdcode) {
                this.cityAdcode = cityAdcode;
            }

            public String getCounty() {
                return county;
            }

            public void setCounty(String county) {
                this.county = county;
            }

            public Object getCountyAdcode() {
                return countyAdcode;
            }

            public void setCountyAdcode(Object countyAdcode) {
                this.countyAdcode = countyAdcode;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public Object getRatio() {
                return ratio;
            }

            public void setRatio(Object ratio) {
                this.ratio = ratio;
            }

            public int getTypeId() {
                return typeId;
            }

            public void setTypeId(int typeId) {
                this.typeId = typeId;
            }

            public Object getTime() {
                return time;
            }

            public void setTime(Object time) {
                this.time = time;
            }

            public Object getHandletime() {
                return handletime;
            }

            public void setHandletime(Object handletime) {
                this.handletime = handletime;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public Object getQrcode() {
                return qrcode;
            }

            public void setQrcode(Object qrcode) {
                this.qrcode = qrcode;
            }

            public Object getShopdetails() {
                return shopdetails;
            }

            public void setShopdetails(Object shopdetails) {
                this.shopdetails = shopdetails;
            }

            public Object getBusinessNo() {
                return businessNo;
            }

            public void setBusinessNo(Object businessNo) {
                this.businessNo = businessNo;
            }

            public Object getBusinessPhoto() {
                return businessPhoto;
            }

            public void setBusinessPhoto(Object businessPhoto) {
                this.businessPhoto = businessPhoto;
            }

            public Object getAllIncome() {
                return allIncome;
            }

            public void setAllIncome(Object allIncome) {
                this.allIncome = allIncome;
            }

            public Object getTurnover() {
                return turnover;
            }

            public void setTurnover(Object turnover) {
                this.turnover = turnover;
            }
        }
    }
}
