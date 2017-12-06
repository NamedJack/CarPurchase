package com.ejar.carpurchase.cart.bean;

import java.util.List;

/**
 * Created by wxd1 on 2017/7/21.
 */

public class CartDataBean {


    /**
     * code : 200
     * msg : OK
     * data : {"feeCondition":{"id":1,"condition":100,"fee":5},"buycarList":[{"id":2,"accountId":311,"productId":1,"productName":"哈哈哈","specId":"1,2,3","univalent":50,"productNum":25,"productImg":"212","amount":1250,"attributes":[{"id":1,"name":"白色","pid":1},{"id":2,"name":"X","pid":2},{"id":3,"name":"玻璃","pid":3}]},{"id":3,"accountId":311,"productId":1,"productName":"哈哈哈","specId":"1,2,6","univalent":50,"productNum":25,"productImg":"212","amount":1250,"attributes":[{"id":1,"name":"白色","pid":1},{"id":2,"name":"X","pid":2},{"id":6,"name":"不锈钢","pid":3}]},{"id":4,"accountId":311,"productId":1,"productName":"哈哈哈","specId":"1,3,5","univalent":50,"productNum":25,"productImg":"212","amount":1250,"attributes":[{"id":1,"name":"白色","pid":1},{"id":3,"name":"玻璃","pid":3},{"id":5,"name":"L","pid":2}]},{"id":5,"accountId":311,"productId":1,"productName":"哈哈哈","specId":"4,5,6","univalent":50,"productNum":25,"productImg":"212","amount":1250,"attributes":[{"id":4,"name":"褐色","pid":1},{"id":5,"name":"L","pid":2},{"id":6,"name":"不锈钢","pid":3}]},{"id":6,"accountId":311,"productId":1,"productName":"哈哈哈","specId":"1,5,6","univalent":50,"productNum":25,"productImg":"212","amount":1250,"attributes":[{"id":1,"name":"白色","pid":1},{"id":5,"name":"L","pid":2},{"id":6,"name":"不锈钢","pid":3}]}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * feeCondition : {"id":1,"condition":100,"fee":5}
         * buycarList : [{"id":2,"accountId":311,"productId":1,"productName":"哈哈哈","specId":"1,2,3","univalent":50,"productNum":25,"productImg":"212","amount":1250,"attributes":[{"id":1,"name":"白色","pid":1},{"id":2,"name":"X","pid":2},{"id":3,"name":"玻璃","pid":3}]},{"id":3,"accountId":311,"productId":1,"productName":"哈哈哈","specId":"1,2,6","univalent":50,"productNum":25,"productImg":"212","amount":1250,"attributes":[{"id":1,"name":"白色","pid":1},{"id":2,"name":"X","pid":2},{"id":6,"name":"不锈钢","pid":3}]},{"id":4,"accountId":311,"productId":1,"productName":"哈哈哈","specId":"1,3,5","univalent":50,"productNum":25,"productImg":"212","amount":1250,"attributes":[{"id":1,"name":"白色","pid":1},{"id":3,"name":"玻璃","pid":3},{"id":5,"name":"L","pid":2}]},{"id":5,"accountId":311,"productId":1,"productName":"哈哈哈","specId":"4,5,6","univalent":50,"productNum":25,"productImg":"212","amount":1250,"attributes":[{"id":4,"name":"褐色","pid":1},{"id":5,"name":"L","pid":2},{"id":6,"name":"不锈钢","pid":3}]},{"id":6,"accountId":311,"productId":1,"productName":"哈哈哈","specId":"1,5,6","univalent":50,"productNum":25,"productImg":"212","amount":1250,"attributes":[{"id":1,"name":"白色","pid":1},{"id":5,"name":"L","pid":2},{"id":6,"name":"不锈钢","pid":3}]}]
         */

        private FeeConditionBean feeCondition;
        private List<BuycarListBean> buycarList;

        public FeeConditionBean getFeeCondition() {
            return feeCondition;
        }

        public void setFeeCondition(FeeConditionBean feeCondition) {
            this.feeCondition = feeCondition;
        }

        public List<BuycarListBean> getBuycarList() {
            return buycarList;
        }

        public void setBuycarList(List<BuycarListBean> buycarList) {
            this.buycarList = buycarList;
        }

        public static class FeeConditionBean {
            /**
             * id : 1
             * condition : 100
             * fee : 5
             */

            private int id;
            private int condition;
            private int fee;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCondition() {
                return condition;
            }

            public void setCondition(int condition) {
                this.condition = condition;
            }

            public int getFee() {
                return fee;
            }

            public void setFee(int fee) {
                this.fee = fee;
            }
        }

        public static class BuycarListBean {
            /**
             * id : 2
             * accountId : 311
             * productId : 1
             * productName : 哈哈哈
             * specId : 1,2,3
             * univalent : 50
             * productNum : 25
             * productImg : 212
             * amount : 1250
             * attributes : [{"id":1,"name":"白色","pid":1},{"id":2,"name":"X","pid":2},{"id":3,"name":"玻璃","pid":3}]
             */

            private boolean isSelected = false;
            private boolean isEdit = false;

            public boolean getIsSelected() {
                return isSelected;
            }

            public void setIsSelected(boolean isSelected) {
                this.isSelected = isSelected;
            }

            public boolean getIsEdit() {
                return isEdit;
            }

            public void setIsEdit(boolean isEdit) {
                this.isEdit = isEdit;
            }

            private int id;
            private int accountId;
            private int productId;
            private String productName;
            private String specId;
            private double univalent;
            private int productNum;
            private String productImg;
            private double amount;
            private List<AttributesBean> attributes;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getAccountId() {
                return accountId;
            }

            public void setAccountId(int accountId) {
                this.accountId = accountId;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getSpecId() {
                return specId;
            }

            public void setSpecId(String specId) {
                this.specId = specId;
            }

            public double getUnivalent() {
                return univalent;
            }

            public void setUnivalent(double univalent) {
                this.univalent = univalent;
            }

            public int getProductNum() {
                return productNum;
            }

            public void setProductNum(int productNum) {
                this.productNum = productNum;
            }

            public String getProductImg() {
                return productImg;
            }

            public void setProductImg(String productImg) {
                this.productImg = productImg;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public List<AttributesBean> getAttributes() {
                return attributes;
            }

            public void setAttributes(List<AttributesBean> attributes) {
                this.attributes = attributes;
            }

            public static class AttributesBean {
                /**
                 * id : 1
                 * name : 白色
                 * pid : 1
                 */

                private int id;
                private String name;
                private int pid;

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

                public int getPid() {
                    return pid;
                }

                public void setPid(int pid) {
                    this.pid = pid;
                }
            }
        }
    }
}
