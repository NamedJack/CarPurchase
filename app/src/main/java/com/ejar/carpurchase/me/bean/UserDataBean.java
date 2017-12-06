package com.ejar.carpurchase.me.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wxd1 on 2017/6/5.
 */

public class UserDataBean implements Serializable {


    /**
     * msg : 成功
     * upaybinding : {"id":"","name":"bdhd","userId":"","payaccount":"","state":"","bankOfDeposit":"","bankofka":"","phone":"","province":"","city":"","county":"","street":"","remark":""}
     * Entityshop : {"id":20,"state":2,"userId":4,"shopName":"yangxiao      yangxiao","companyName":"宜家","idCardNumber":"","name":"","log":"/static/upload/entityimg/e96fe7b7-6983-47b0-9656-f8c198f538e6.jpg","phone":"13689682940","lng":"","lat":"","idCardFrontPhoto":"","idCardNegativePhoto":"","province":"北京","provinceAdcode":"1","city":"北京市","street":"科创园街道","streetAdcode":"54333","adders":"","cityAdcode":"52805","county":"延庆县","countyAdcode":"3065","remark":"","ratio":"","typeId":3,"time":1497419424000,"handletime":"","typeName":"","shopdetails":"","qrcode":"","businessNo":"0824567","turnover":1140.5,"businessPhoto":"/static/upload/entityimg/178366bd-c6ed-4930-9828-4da124aa50ef.jpg","allIncome":11210}
     * code : 200
     * user : {"id":4,"account":"13689682940","password":"","operate":"","name":"杨枭","phone":"13689682940","uniqueId":"","idCard":"","totalmoney":10000,"money":8500,"offlineAllRebate":40,"offlineRebate":40,"allMoney":1000,"red":0,"redAllMoney":0,"redMoney":0,"pileMoney":0,"state":3,"topUser":"","payPassword":"","email":"469785267@QQ.com","qq":"469785267","province":"四川","city":"绵阳市","county":"涪城区","street":"科创园街道","userHead":"/static/userimg/img/3000983f-a88f-4bad-9a62-b4fb30421089.jpg","qrcode":"/static/upload/userQrcode/1e059336-1ff4-47a4-b709-ec55363b72c04ec178bfs.png","time":""}
     */

    private String msg;
    private UpaybindingBean upaybinding;
    private EntityshopBean Entityshop;
    private String code;
    private UserBean user;
    private List<TeamBean> timx;

    public List<TeamBean> getTimx() {
        return timx;
    }

    public void setTimx(List<TeamBean> timx) {
        this.timx = timx;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UpaybindingBean getUpaybinding() {
        return upaybinding;
    }

    public void setUpaybinding(UpaybindingBean upaybinding) {
        this.upaybinding = upaybinding;
    }

    public EntityshopBean getEntityshop() {
        return Entityshop;
    }

    public void setEntityshop(EntityshopBean Entityshop) {
        this.Entityshop = Entityshop;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UpaybindingBean implements Serializable {
        /**
         * id :
         * name : bdhd
         * userId :
         * payaccount :
         * state :
         * bankOfDeposit :
         * bankofka :
         * phone :
         * province :
         * city :
         * county :
         * street :
         * remark :
         */

        private String id;
        private String name;
        private String userId;
        private String payaccount;
        private String state;
        private String bankOfDeposit;
        private String bankofka;
        private String phone;
        private String province;
        private String city;
        private String county;
        private String street;
        private String remark;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPayaccount() {
            return payaccount;
        }

        public void setPayaccount(String payaccount) {
            this.payaccount = payaccount;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getBankOfDeposit() {
            return bankOfDeposit;
        }

        public void setBankOfDeposit(String bankOfDeposit) {
            this.bankOfDeposit = bankOfDeposit;
        }

        public String getBankofka() {
            return bankofka;
        }

        public void setBankofka(String bankofka) {
            this.bankofka = bankofka;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    public static class EntityshopBean implements Serializable {
        /**
         * id : 20
         * state : 2
         * userId : 4
         * shopName : yangxiao      yangxiao
         * companyName : 宜家
         * idCardNumber :
         * name :
         * log : /static/upload/entityimg/e96fe7b7-6983-47b0-9656-f8c198f538e6.jpg
         * phone : 13689682940
         * lng :
         * lat :
         * idCardFrontPhoto :
         * idCardNegativePhoto :
         * province : 北京
         * provinceAdcode : 1
         * city : 北京市
         * street : 科创园街道
         * streetAdcode : 54333
         * adders :
         * cityAdcode : 52805
         * county : 延庆县
         * countyAdcode : 3065
         * remark :
         * ratio :
         * typeId : 3
         * time : 1497419424000
         * handletime :
         * typeName :
         * shopdetails :
         * qrcode :
         * businessNo : 0824567
         * turnover : 1140.5
         * businessPhoto : /static/upload/entityimg/178366bd-c6ed-4930-9828-4da124aa50ef.jpg
         * allIncome : 11210
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
        private String shopdetails;
        private String qrcode;
        private String businessNo;
        private double turnover;
        private String businessPhoto;
        private int allIncome;

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

        public String getShopdetails() {
            return shopdetails;
        }

        public void setShopdetails(String shopdetails) {
            this.shopdetails = shopdetails;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public String getBusinessNo() {
            return businessNo;
        }

        public void setBusinessNo(String businessNo) {
            this.businessNo = businessNo;
        }

        public double getTurnover() {
            return turnover;
        }

        public void setTurnover(double turnover) {
            this.turnover = turnover;
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
    }

    public static class UserBean implements Serializable {

        /**
         * id : 1
         * account : 13689682940
         * password :
         * operate :
         * name : 13689682940
         * parentId :
         * parentName :
         * ratio :
         * phone : 13689682940
         * uniqueId : 100001
         * idCard :
         * totalmoney : 11000.0
         * money : 9500.0
         * offlineAllRebate : 0.0
         * offlineRebate : 0.0
         * allMoney : 0.0
         * red : 0.0
         * redAllMoney : 0.0
         * redMoney : 0.0
         * pileMoney : 0.0
         * state : 3
         * topUser : 1
         * payPassword :
         * email : 469785267@qq.com
         * qq : 469785267
         * province : 四川
         * city : 绵阳市
         * provinceAdcode : 22
         * cityAdcode : 1960
         * county : 涪城区
         * street : 科创园街道
         * countyAdcode : 38574
         * streetAdcode : 54333
         * detailedAddress : 126
         * userHead : /static/userimg/img/7b25f1c7-8d98-470d-8c1e-b48849e81aa0.png
         * qrcode : /static/upload/userQrcode/0c3a5667-a90b-4412-9028-92205147dfb61ec178bfs.png
         * time : null
         * angentAllMoney : 0
         * angentMoney : 0
         */

        private int id;
        private String account;
        private String password;
        private String operate;
        private String name;
        private String parentId;
        private String parentName;
        private String ratio;
        private String phone;
        private int uniqueId;
        private String idCard;
        private double totalmoney;
        private double money;
        private double offlineAllRebate;
        private double offlineRebate;
        private double allMoney;
        private double red;
        private double redAllMoney;
        private double redMoney;
        /**
         * 商城积分
         */
        private double redMoneyts;

        public double getRedMoneyts() {
            return redMoneyts;
        }

        public void setRedMoneyts(double redMoneyts) {
            this.redMoneyts = redMoneyts;
        }

        private double pileMoney;
        private int state;
        private int topUser;
        private String payPassword;
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
        private String detailedAddress;
        private String userHead;
        private String qrcode;
        private Object time;
        private double angentAllMoney;
        private double angentMoney;

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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getOperate() {
            return operate;
        }

        public void setOperate(String operate) {
            this.operate = operate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getParentName() {
            return parentName;
        }

        public void setParentName(String parentName) {
            this.parentName = parentName;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
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

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public double getTotalmoney() {
            return totalmoney;
        }

        public void setTotalmoney(double totalmoney) {
            this.totalmoney = totalmoney;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public double getOfflineAllRebate() {
            return offlineAllRebate;
        }

        public void setOfflineAllRebate(double offlineAllRebate) {
            this.offlineAllRebate = offlineAllRebate;
        }

        public double getOfflineRebate() {
            return offlineRebate;
        }

        public void setOfflineRebate(double offlineRebate) {
            this.offlineRebate = offlineRebate;
        }

        public double getAllMoney() {
            return allMoney;
        }

        public void setAllMoney(double allMoney) {
            this.allMoney = allMoney;
        }

        public double getRed() {
            return red;
        }

        public void setRed(double red) {
            this.red = red;
        }

        public double getRedAllMoney() {
            return redAllMoney;
        }

        public void setRedAllMoney(double redAllMoney) {
            this.redAllMoney = redAllMoney;
        }

        public double getRedMoney() {
            return redMoney;
        }

        public void setRedMoney(double redMoney) {
            this.redMoney = redMoney;
        }

        public double getPileMoney() {
            return pileMoney;
        }

        public void setPileMoney(double pileMoney) {
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

        public String getPayPassword() {
            return payPassword;
        }

        public void setPayPassword(String payPassword) {
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

        public String getDetailedAddress() {
            return detailedAddress;
        }

        public void setDetailedAddress(String detailedAddress) {
            this.detailedAddress = detailedAddress;
        }

        public String getUserHead() {
            return userHead;
        }

        public void setUserHead(String userHead) {
            this.userHead = userHead;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public Object getTime() {
            return time;
        }

        public void setTime(Object time) {
            this.time = time;
        }

        public double getAngentAllMoney() {
            return angentAllMoney;
        }

        public void setAngentAllMoney(double angentAllMoney) {
            this.angentAllMoney = angentAllMoney;
        }

        public double getAngentMoney() {
            return angentMoney;
        }

        public void setAngentMoney(double angentMoney) {
            this.angentMoney = angentMoney;
        }
    }

    public static class TeamBean implements Serializable {

        /**
         * id : 1
         * userID : 23
         * teamMoney : 1000.0
         * teamAllMoney : 1000.0
         * teamRecord : 1000.0
         * teamAllRecord : 1000.0
         */

        private int id;
        private int userID;
        private double teamMoney;
        private double teamAllMoney;
        private double teamRecord;
        private double teamAllRecord;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public double getTeamMoney() {
            return teamMoney;
        }

        public void setTeamMoney(double teamMoney) {
            this.teamMoney = teamMoney;
        }

        public double getTeamAllMoney() {
            return teamAllMoney;
        }

        public void setTeamAllMoney(double teamAllMoney) {
            this.teamAllMoney = teamAllMoney;
        }

        public double getTeamRecord() {
            return teamRecord;
        }

        public void setTeamRecord(double teamRecord) {
            this.teamRecord = teamRecord;
        }

        public double getTeamAllRecord() {
            return teamAllRecord;
        }

        public void setTeamAllRecord(double teamAllRecord) {
            this.teamAllRecord = teamAllRecord;
        }
    }
}
