package com.ejar.carpurchase.me.bean;

public class BankInfoBean {

        /**
         * upaybinding : {"id":6,"name":"嘻嘻","userId":16,"payaccount":"454846498935558","state":3,"bankOfDeposit":"哦logo","bankofka":"攻击力","phone":"18683661692","province":"北京","city":"北京市","county":"延庆县","street":null,"remark":null}
         * code : 200
         */

        private UpaybindingBean upaybinding;
        private int code;

        public UpaybindingBean getUpaybinding() {
            return upaybinding;
        }

        public void setUpaybinding(UpaybindingBean upaybinding) {
            this.upaybinding = upaybinding;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public static class UpaybindingBean {
            /**
             * id : 6
             * name : 嘻嘻
             * userId : 16
             * payaccount : 454846498935558
             * state : 3
             * bankOfDeposit : 哦logo
             * bankofka : 攻击力
             * phone : 18683661692
             * province : 北京
             * city : 北京市
             * county : 延庆县
             * street : null
             * remark : null
             */

            private int id;
            private String name;
            private int userId;
            private String payaccount;
            private int state;
            private String bankOfDeposit;
            private String bankofka;
            private String phone;
            private String province;
            private String city;
            private String county;
            private String street;
            private String remark;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getPayaccount() {
                return payaccount;
            }

            public void setPayaccount(String payaccount) {
                this.payaccount = payaccount;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
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
    }