package com.ejar.carpurchase.shop.bean;

import java.util.List;

/**
 * Created by wongxd on 2017/7/23.
 */

public class SpecBean {

    /**
     * status : 200
     * msg : OK
     * data : {"attribute":[{"key":"颜色","value":[{"id":4,"name":"褐色","pid":1},{"id":1,"name":"白色","pid":1}],"attribute":null},{"key":"尺寸","value":[{"id":5,"name":"L","pid":2},{"id":2,"name":"X","pid":2}],"attribute":null},{"key":"材质","value":[{"id":3,"name":"玻璃","pid":3},{"id":6,"name":"不锈钢","pid":3}],"attribute":null}],"combination":[{"id":1,"productID":1,"detailId":"1,2,3","stock":0,"price":"0.00"},{"id":2,"productID":1,"detailId":"1,2,6","stock":0,"price":"0.00"},{"id":3,"productID":1,"detailId":"1,3,5","stock":0,"price":"0.00"},{"id":4,"productID":1,"detailId":"4,5,6","stock":0,"price":"0.00"}]}
     */

    private int status;
    private String msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        private List<AttributeBean> attribute;
        private List<CombinationBean> combination;

        public List<AttributeBean> getAttribute() {
            return attribute;
        }

        public void setAttribute(List<AttributeBean> attribute) {
            this.attribute = attribute;
        }

        public List<CombinationBean> getCombination() {
            return combination;
        }

        public void setCombination(List<CombinationBean> combination) {
            this.combination = combination;
        }

        public static class AttributeBean {
            /**
             * key : 颜色
             * value : [{"id":4,"name":"褐色","pid":1},{"id":1,"name":"白色","pid":1}]
             * attribute : null
             */

            private String key;
            private Object attribute;
            private List<ValueBean> value;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public Object getAttribute() {
                return attribute;
            }

            public void setAttribute(Object attribute) {
                this.attribute = attribute;
            }

            public List<ValueBean> getValue() {
                return value;
            }

            public void setValue(List<ValueBean> value) {
                this.value = value;
            }

            public static class ValueBean {
                /**
                 * id : 4
                 * name : 褐色
                 * pid : 1
                 */


                /**
                 * 0  normal     1-unclickable
                 */
                private int status = 0;

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

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

        public static class CombinationBean {
            /**
             * id : 1
             * productID : 1
             * detailId : 1,2,3
             * stock : 0
             * price : 0.00
             */

            private int id;
            private int productID;
            private String detailId;
            private int stock;
            private String price;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getProductID() {
                return productID;
            }

            public void setProductID(int productID) {
                this.productID = productID;
            }

            public String getDetailId() {
                return detailId;
            }

            public void setDetailId(String detailId) {
                this.detailId = detailId;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
