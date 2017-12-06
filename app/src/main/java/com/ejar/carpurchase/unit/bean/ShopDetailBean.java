package com.ejar.carpurchase.unit.bean;

import java.util.List;

/**
 * Created by wxd1 on 2017/6/9.
 */

public class ShopDetailBean {
    /**
     * msg : 查询成功
     * shop : {"id":19,"state":2,"userId":40,"shopName":"服装->男士新款->冬季混搭","companyName":"易家","idCardNumber":null,"name":null,"log":"/static/upload/entityimg/7185794c-3223-4760-a335-0a0f30926100.png","phone":"13689682940","lng":null,"lat":null,"idCardFrontPhoto":null,"idCardNegativePhoto":null,"province":"山西","provinceAdcode":"6","city":"太原市","street":"暂无","streetAdcode":"暂无","adders":null,"cityAdcode":"303","county":"杏花岭区","countyAdcode":"36785","remark":null,"ratio":null,"typeId":3,"time":1497519016000,"handletime":null,"typeName":null,"turnover":0,"businessPhoto":"/static/upload/entityimg/b7e1ea90-c15c-4b78-9ba2-a3d9908df824.jpg","businessNo":"58558855amt","allIncome":0,"shopdetails":null,"qrcode":null}
     * code : 200
     * imglist : []
     */

    private String msg;
    private ShopBean shop;
    private int code;
    private List<ImgBean> imglist;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<?> getImglist() {
        return imglist;
    }

    public void setImglist(List<ImgBean> imglist) {
        this.imglist = imglist;
    }

    public static class ShopBean {
        /**
         * id : 19
         * state : 2
         * userId : 40
         * shopName : 服装->男士新款->冬季混搭
         * companyName : 易家
         * idCardNumber : null
         * name : null
         * log : /static/upload/entityimg/7185794c-3223-4760-a335-0a0f30926100.png
         * phone : 13689682940
         * lng : null
         * lat : null
         * idCardFrontPhoto : null
         * idCardNegativePhoto : null
         * province : 山西
         * provinceAdcode : 6
         * city : 太原市
         * street : 暂无
         * streetAdcode : 暂无
         * adders : null
         * cityAdcode : 303
         * county : 杏花岭区
         * countyAdcode : 36785
         * remark : null
         * ratio : null
         * typeId : 3
         * time : 1497519016000
         * handletime : null
         * typeName : null
         * turnover : 0
         * businessPhoto : /static/upload/entityimg/b7e1ea90-c15c-4b78-9ba2-a3d9908df824.jpg
         * businessNo : 58558855amt
         * allIncome : 0
         * shopdetails : null
         * qrcode : null
         */

        private int id;
        private int state;
        private int userId;
        private String shopName;
        private String companyName;
        private Object idCardNumber;
        private Object name;
        private String log;
        private String phone;
        private Object lng;
        private Object lat;
        private Object idCardFrontPhoto;
        private Object idCardNegativePhoto;
        private String province;
        private String provinceAdcode;
        private String city;
        private String street;
        private String streetAdcode;
        private Object adders;
        private String cityAdcode;
        private String county;
        private String countyAdcode;
        private Object remark;
        private Object ratio;
        private int typeId;
        private long time;
        private Object handletime;
        private Object typeName;
        private int turnover;
        private String businessPhoto;
        private String businessNo;
        private int allIncome;
        private Object shopdetails;
        private Object qrcode;

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

        public Object getAdders() {
            return adders;
        }

        public void setAdders(Object adders) {
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

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public Object getHandletime() {
            return handletime;
        }

        public void setHandletime(Object handletime) {
            this.handletime = handletime;
        }

        public Object getTypeName() {
            return typeName;
        }

        public void setTypeName(Object typeName) {
            this.typeName = typeName;
        }

        public int getTurnover() {
            return turnover;
        }

        public void setTurnover(int turnover) {
            this.turnover = turnover;
        }

        public String getBusinessPhoto() {
            return businessPhoto;
        }

        public void setBusinessPhoto(String businessPhoto) {
            this.businessPhoto = businessPhoto;
        }

        public String getBusinessNo() {
            return businessNo;
        }

        public void setBusinessNo(String businessNo) {
            this.businessNo = businessNo;
        }

        public int getAllIncome() {
            return allIncome;
        }

        public void setAllIncome(int allIncome) {
            this.allIncome = allIncome;
        }

        public Object getShopdetails() {
            return shopdetails;
        }

        public void setShopdetails(Object shopdetails) {
            this.shopdetails = shopdetails;
        }

        public Object getQrcode() {
            return qrcode;
        }

        public void setQrcode(Object qrcode) {
            this.qrcode = qrcode;
        }
    }
    public static class ImgBean  {

        /**
         * id : 11
         * shopId : 7
         * imgUrl : /static/upload/entityimg103bd1cd-49c5-460b-a5aa-3d928295623b.jpeg
         */

        private int id;
        private int shopId;
        private String imgUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
//    /**
//     * msg : 查询成功
//     * shop : {"id":5,"state":2,"userId":5,"shopName":"百货->电器品->液晶电视","companyName":"","idCardNumber":"","name":"赵七","log":"assets/upload/img/201701/12545487446447.jpg","phone":"18780547853","lng":"","lat":"","idCardFrontPhoto":"","idCardNegativePhoto":"","province":"","provinceAdcode":"","city":"","street":"","streetAdcode":"","adders":"遂宁市","cityAdcode":"","county":"","countyAdcode":"","remark":"","ratio":"","typeId":7,"time":"","handletime":"","typeName":"","shopdetails":"","businessNo":"123asd123","qrcode":"","turnover":500,"businessPhoto":"qwert","allIncome":0}
//     * code : 200
//     * imglist : []
//     */
//
//    private String msg;
//    private ShopBean shop;
//    private int code;
//    private List<ImgBean> imglist;
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public ShopBean getShop() {
//        return shop;
//    }
//
//    public void setShop(ShopBean shop) {
//        this.shop = shop;
//    }
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public List<ImgBean> getImglist() {
//        return imglist;
//    }
//
//    public void setImglist(List<ImgBean> imglist) {
//        this.imglist = imglist;
//    }
//
//    public static class ShopBean {
//        /**
//         * id : 5
//         * state : 2
//         * userId : 5
//         * shopName : 百货->电器品->液晶电视
//         * companyName :
//         * idCardNumber :
//         * name : 赵七
//         * log : assets/upload/img/201701/12545487446447.jpg
//         * phone : 18780547853
//         * lng :
//         * lat :
//         * idCardFrontPhoto :
//         * idCardNegativePhoto :
//         * province :
//         * provinceAdcode :
//         * city :
//         * street :
//         * streetAdcode :
//         * adders : 遂宁市
//         * cityAdcode :
//         * county :
//         * countyAdcode :
//         * remark :
//         * ratio :
//         * typeId : 7
//         * time :
//         * handletime :
//         * typeName :
//         * shopdetails :
//         * businessNo : 123asd123
//         * qrcode :
//         * turnover : 500
//         * businessPhoto : qwert
//         * allIncome : 0
//         */
//
//        private int id;
//        private int state;
//        private int userId;
//        private String shopName;
//        private String companyName;
//        private String idCardNumber;
//        private String name;
//        private String log;
//        private String phone;
//        private String lng;
//        private String lat;
//        private String idCardFrontPhoto;
//        private String idCardNegativePhoto;
//        private String province;
//        private String provinceAdcode;
//        private String city;
//        private String street;
//        private String streetAdcode;
//        private String adders;
//        private String cityAdcode;
//        private String county;
//        private String countyAdcode;
//        private String remark;
//        private String ratio;
//        private int typeId;
//        private String time;
//        private String handletime;
//        private String typeName;
//        private String shopdetails;
//        private String businessNo;
//        private String qrcode;
//        private int turnover;
//        private String businessPhoto;
//        private int allIncome;
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public int getState() {
//            return state;
//        }
//
//        public void setState(int state) {
//            this.state = state;
//        }
//
//        public int getUserId() {
//            return userId;
//        }
//
//        public void setUserId(int userId) {
//            this.userId = userId;
//        }
//
//        public String getShopName() {
//            return shopName;
//        }
//
//        public void setShopName(String shopName) {
//            this.shopName = shopName;
//        }
//
//        public String getCompanyName() {
//            return companyName;
//        }
//
//        public void setCompanyName(String companyName) {
//            this.companyName = companyName;
//        }
//
//        public String getIdCardNumber() {
//            return idCardNumber;
//        }
//
//        public void setIdCardNumber(String idCardNumber) {
//            this.idCardNumber = idCardNumber;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getLog() {
//            return log;
//        }
//
//        public void setLog(String log) {
//            this.log = log;
//        }
//
//        public String getPhone() {
//            return phone;
//        }
//
//        public void setPhone(String phone) {
//            this.phone = phone;
//        }
//
//        public String getLng() {
//            return lng;
//        }
//
//        public void setLng(String lng) {
//            this.lng = lng;
//        }
//
//        public String getLat() {
//            return lat;
//        }
//
//        public void setLat(String lat) {
//            this.lat = lat;
//        }
//
//        public String getIdCardFrontPhoto() {
//            return idCardFrontPhoto;
//        }
//
//        public void setIdCardFrontPhoto(String idCardFrontPhoto) {
//            this.idCardFrontPhoto = idCardFrontPhoto;
//        }
//
//        public String getIdCardNegativePhoto() {
//            return idCardNegativePhoto;
//        }
//
//        public void setIdCardNegativePhoto(String idCardNegativePhoto) {
//            this.idCardNegativePhoto = idCardNegativePhoto;
//        }
//
//        public String getProvince() {
//            return province;
//        }
//
//        public void setProvince(String province) {
//            this.province = province;
//        }
//
//        public String getProvinceAdcode() {
//            return provinceAdcode;
//        }
//
//        public void setProvinceAdcode(String provinceAdcode) {
//            this.provinceAdcode = provinceAdcode;
//        }
//
//        public String getCity() {
//            return city;
//        }
//
//        public void setCity(String city) {
//            this.city = city;
//        }
//
//        public String getStreet() {
//            return street;
//        }
//
//        public void setStreet(String street) {
//            this.street = street;
//        }
//
//        public String getStreetAdcode() {
//            return streetAdcode;
//        }
//
//        public void setStreetAdcode(String streetAdcode) {
//            this.streetAdcode = streetAdcode;
//        }
//
//        public String getAdders() {
//            return adders;
//        }
//
//        public void setAdders(String adders) {
//            this.adders = adders;
//        }
//
//        public String getCityAdcode() {
//            return cityAdcode;
//        }
//
//        public void setCityAdcode(String cityAdcode) {
//            this.cityAdcode = cityAdcode;
//        }
//
//        public String getCounty() {
//            return county;
//        }
//
//        public void setCounty(String county) {
//            this.county = county;
//        }
//
//        public String getCountyAdcode() {
//            return countyAdcode;
//        }
//
//        public void setCountyAdcode(String countyAdcode) {
//            this.countyAdcode = countyAdcode;
//        }
//
//        public String getRemark() {
//            return remark;
//        }
//
//        public void setRemark(String remark) {
//            this.remark = remark;
//        }
//
//        public String getRatio() {
//            return ratio;
//        }
//
//        public void setRatio(String ratio) {
//            this.ratio = ratio;
//        }
//
//        public int getTypeId() {
//            return typeId;
//        }
//
//        public void setTypeId(int typeId) {
//            this.typeId = typeId;
//        }
//
//        public String getTime() {
//            return time;
//        }
//
//        public void setTime(String time) {
//            this.time = time;
//        }
//
//        public String getHandletime() {
//            return handletime;
//        }
//
//        public void setHandletime(String handletime) {
//            this.handletime = handletime;
//        }
//
//        public String getTypeName() {
//            return typeName;
//        }
//
//        public void setTypeName(String typeName) {
//            this.typeName = typeName;
//        }
//
//        public String getShopdetails() {
//            return shopdetails;
//        }
//
//        public void setShopdetails(String shopdetails) {
//            this.shopdetails = shopdetails;
//        }
//
//        public String getBusinessNo() {
//            return businessNo;
//        }
//
//        public void setBusinessNo(String businessNo) {
//            this.businessNo = businessNo;
//        }
//
//        public String getQrcode() {
//            return qrcode;
//        }
//
//        public void setQrcode(String qrcode) {
//            this.qrcode = qrcode;
//        }
//
//        public int getTurnover() {
//            return turnover;
//        }
//
//        public void setTurnover(int turnover) {
//            this.turnover = turnover;
//        }
//
//        public String getBusinessPhoto() {
//            return businessPhoto;
//        }
//
//        public void setBusinessPhoto(String businessPhoto) {
//            this.businessPhoto = businessPhoto;
//        }
//
//        public int getAllIncome() {
//            return allIncome;
//        }
//
//        public void setAllIncome(int allIncome) {
//            this.allIncome = allIncome;
//        }
//    }
//
//    public   static class ImgBean  {
//
//        /**
//         * id : 11
//         * shopId : 7
//         * imgUrl : /static/upload/entityimg103bd1cd-49c5-460b-a5aa-3d928295623b.jpeg
//         */
//
//        private int id;
//        private int shopId;
//        private String imgUrl;
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public int getShopId() {
//            return shopId;
//        }
//
//        public void setShopId(int shopId) {
//            this.shopId = shopId;
//        }
//
//        public String getImgUrl() {
//            return imgUrl;
//        }
//
//        public void setImgUrl(String imgUrl) {
//            this.imgUrl = imgUrl;
//        }
//    }


}
