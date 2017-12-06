package com.ejar.carpurchase.me.bean;

/**
 * Created by wxd1 on 2017/6/8.
 */

public class WithdrawRuleBean {

    /**
     * code : 200
     * yjrule : {"id":"","takeAPercentage":"","takeAPercentageRed":"","redRatio":"","downUserRatio":"","redMoney":"","takeAPercentageType":"","withdrawDepositRatio":0.5,"drawMoneyType":"","drawMoneyTime":"","drawMoneyTop":1000,"drawMoneyDown":100,"entityImgSize":0,"userMoney":""}
     */

    private int code;
    private YjruleBean yjrule;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public YjruleBean getYjrule() {
        return yjrule;
    }

    public void setYjrule(YjruleBean yjrule) {
        this.yjrule = yjrule;
    }

    public static class YjruleBean {
        /**
         * id :
         * takeAPercentage :
         * takeAPercentageRed :
         * redRatio :
         * downUserRatio :
         * redMoney :
         * takeAPercentageType :
         * withdrawDepositRatio : 0.5
         * drawMoneyType :
         * drawMoneyTime :
         * drawMoneyTop : 1000
         * drawMoneyDown : 100
         * entityImgSize : 0
         * userMoney :
         */

        private String id;
        private String takeAPercentage;
        private String takeAPercentageRed;
        private String redRatio;
        private String downUserRatio;
        private String redMoney;
        private String takeAPercentageType;
        private double withdrawDepositRatio;
        private String drawMoneyType;
        private String drawMoneyTime;
        private int drawMoneyTop;
        private int drawMoneyDown;
        private int entityImgSize;
        private String userMoney;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTakeAPercentage() {
            return takeAPercentage;
        }

        public void setTakeAPercentage(String takeAPercentage) {
            this.takeAPercentage = takeAPercentage;
        }

        public String getTakeAPercentageRed() {
            return takeAPercentageRed;
        }

        public void setTakeAPercentageRed(String takeAPercentageRed) {
            this.takeAPercentageRed = takeAPercentageRed;
        }

        public String getRedRatio() {
            return redRatio;
        }

        public void setRedRatio(String redRatio) {
            this.redRatio = redRatio;
        }

        public String getDownUserRatio() {
            return downUserRatio;
        }

        public void setDownUserRatio(String downUserRatio) {
            this.downUserRatio = downUserRatio;
        }

        public String getRedMoney() {
            return redMoney;
        }

        public void setRedMoney(String redMoney) {
            this.redMoney = redMoney;
        }

        public String getTakeAPercentageType() {
            return takeAPercentageType;
        }

        public void setTakeAPercentageType(String takeAPercentageType) {
            this.takeAPercentageType = takeAPercentageType;
        }

        public double getWithdrawDepositRatio() {
            return withdrawDepositRatio;
        }

        public void setWithdrawDepositRatio(double withdrawDepositRatio) {
            this.withdrawDepositRatio = withdrawDepositRatio;
        }

        public String getDrawMoneyType() {
            return drawMoneyType;
        }

        public void setDrawMoneyType(String drawMoneyType) {
            this.drawMoneyType = drawMoneyType;
        }

        public String getDrawMoneyTime() {
            return drawMoneyTime;
        }

        public void setDrawMoneyTime(String drawMoneyTime) {
            this.drawMoneyTime = drawMoneyTime;
        }

        public int getDrawMoneyTop() {
            return drawMoneyTop;
        }

        public void setDrawMoneyTop(int drawMoneyTop) {
            this.drawMoneyTop = drawMoneyTop;
        }

        public int getDrawMoneyDown() {
            return drawMoneyDown;
        }

        public void setDrawMoneyDown(int drawMoneyDown) {
            this.drawMoneyDown = drawMoneyDown;
        }

        public int getEntityImgSize() {
            return entityImgSize;
        }

        public void setEntityImgSize(int entityImgSize) {
            this.entityImgSize = entityImgSize;
        }

        public String getUserMoney() {
            return userMoney;
        }

        public void setUserMoney(String userMoney) {
            this.userMoney = userMoney;
        }
    }
}
