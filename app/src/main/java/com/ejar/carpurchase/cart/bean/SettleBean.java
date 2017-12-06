package com.ejar.carpurchase.cart.bean;

import java.util.List;

/**
 * Created by wongxd on 2017/7/27.
 * 购物车结算
 */

public class SettleBean {

    /**
     * code : 200
     * msg : OK
     * data : {"score":50,"address":{"id":1,"account":"311","name":"是电讯形成","address":"多喝点多喝点多喝点多喝点多喝点","zip":null,"tel":"1111111111","mobile":"4545","isdefault":1,"province":"多喝点","city":"多喝点","area":"多喝点","email":""},"balance":100,"fee":0,"products":[{"id":63,"name":"360手机","price":1699,"nowPrice":100,"picture":"/static/product/picture/14f647b4-426c-49c0-ba50-8559fbcd235a.jpg,/static/product/picture/0825a077-07fc-4ccf-b12f-231e81e01a2f.jpg,/static/product/picture/26301419-d04e-4ac9-a33a-07fbcdb7d817.jpg,/static/product/picture/1888c8b6-8d7e-442f-abd9-9ef2343e1be8.jpg,/static/product/picture/3ca6bd53-d7dd-46cf-84e5-8c659705e938.jpg,/static/product/picture/9a67fc59-409f-409f-8669-4469c793737e.jpg","status":1,"productHTML":"<p>360手机 N5S 全网通 6GB+64GB 幻影黑 移动联通电信4G手机 双卡双待<\/p><p><br><\/p>","image":"/static/product/image/a37926bd-84bc-4f23-86b4-04822608767b.jpeg","catalogID":1,"sellcount":0,"sort":null,"keyWord":"360手机","delFalg":0,"time":"2017-07-27 10:00:49","buyNumber":5,"attributes":[{"id":1,"name":"白色","pid":1},{"id":2,"name":"X","pid":2},{"id":3,"name":"玻璃","pid":3}],"catalogName":"","parameters":null,"pictures":""}]}
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
         * score : 50
         * address : {"id":1,"account":"311","name":"是电讯形成","address":"多喝点多喝点多喝点多喝点多喝点","zip":null,"tel":"1111111111","mobile":"4545","isdefault":1,"province":"多喝点","city":"多喝点","area":"多喝点","email":""}
         * balance : 100
         * fee : 0
         * products : [{"id":63,"name":"360手机","price":1699,"nowPrice":100,"picture":"/static/product/picture/14f647b4-426c-49c0-ba50-8559fbcd235a.jpg,/static/product/picture/0825a077-07fc-4ccf-b12f-231e81e01a2f.jpg,/static/product/picture/26301419-d04e-4ac9-a33a-07fbcdb7d817.jpg,/static/product/picture/1888c8b6-8d7e-442f-abd9-9ef2343e1be8.jpg,/static/product/picture/3ca6bd53-d7dd-46cf-84e5-8c659705e938.jpg,/static/product/picture/9a67fc59-409f-409f-8669-4469c793737e.jpg","status":1,"productHTML":"<p>360手机 N5S 全网通 6GB+64GB 幻影黑 移动联通电信4G手机 双卡双待<\/p><p><br><\/p>","image":"/static/product/image/a37926bd-84bc-4f23-86b4-04822608767b.jpeg","catalogID":1,"sellcount":0,"sort":null,"keyWord":"360手机","delFalg":0,"time":"2017-07-27 10:00:49","buyNumber":5,"attributes":[{"id":1,"name":"白色","pid":1},{"id":2,"name":"X","pid":2},{"id":3,"name":"玻璃","pid":3}],"catalogName":"","parameters":null,"pictures":""}]
         */

        private double score;
        private AddressBean address;
        private int balance;
        private int fee;
        private List<ProductsBean> products;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class AddressBean {
            /**
             * id : 1
             * account : 311
             * name : 是电讯形成
             * address : 多喝点多喝点多喝点多喝点多喝点
             * zip : null
             * tel : 1111111111
             * mobile : 4545
             * isdefault : 1
             * province : 多喝点
             * city : 多喝点
             * area : 多喝点
             * email :
             */

            private int id;
            private String account;
            private String name;
            private String address;
            private Object zip;
            private String tel;
            private String mobile;
            private int isdefault;
            private String province;
            private String city;
            private String area;
            private String email;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public Object getZip() {
                return zip;
            }

            public void setZip(Object zip) {
                this.zip = zip;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public int getIsdefault() {
                return isdefault;
            }

            public void setIsdefault(int isdefault) {
                this.isdefault = isdefault;
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

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }
        }

        public static class ProductsBean {
            /**
             * id : 63
             * name : 360手机
             * price : 1699
             * nowPrice : 100
             * picture : /static/product/picture/14f647b4-426c-49c0-ba50-8559fbcd235a.jpg,/static/product/picture/0825a077-07fc-4ccf-b12f-231e81e01a2f.jpg,/static/product/picture/26301419-d04e-4ac9-a33a-07fbcdb7d817.jpg,/static/product/picture/1888c8b6-8d7e-442f-abd9-9ef2343e1be8.jpg,/static/product/picture/3ca6bd53-d7dd-46cf-84e5-8c659705e938.jpg,/static/product/picture/9a67fc59-409f-409f-8669-4469c793737e.jpg
             * status : 1
             * productHTML : <p>360手机 N5S 全网通 6GB+64GB 幻影黑 移动联通电信4G手机 双卡双待</p><p><br></p>
             * image : /static/product/image/a37926bd-84bc-4f23-86b4-04822608767b.jpeg
             * catalogID : 1
             * sellcount : 0
             * sort : null
             * keyWord : 360手机
             * delFalg : 0
             * time : 2017-07-27 10:00:49
             * buyNumber : 5
             * attributes : [{"id":1,"name":"白色","pid":1},{"id":2,"name":"X","pid":2},{"id":3,"name":"玻璃","pid":3}]
             * catalogName :
             * parameters : null
             * pictures :
             */

            private double bigScore;

            public double getBigScore() {
                return bigScore;
            }

            public void setBigScore(double bigScore) {
                this.bigScore = bigScore;
            }

            private int id;
            private String name;
            private double price;
            private double nowPrice;
            private String picture;
            private int status;
            private String productHTML;
            private String image;
            private int catalogID;
            private int sellcount;
            private Object sort;
            private String keyWord;
            private int delFalg;
            private String time;
            private int buyNumber;
            private String catalogName;
            private Object parameters;
            private String pictures;
            private List<AttributesBean> attributes;

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

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getNowPrice() {
                return nowPrice;
            }

            public void setNowPrice(double nowPrice) {
                this.nowPrice = nowPrice;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getProductHTML() {
                return productHTML;
            }

            public void setProductHTML(String productHTML) {
                this.productHTML = productHTML;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getCatalogID() {
                return catalogID;
            }

            public void setCatalogID(int catalogID) {
                this.catalogID = catalogID;
            }

            public int getSellcount() {
                return sellcount;
            }

            public void setSellcount(int sellcount) {
                this.sellcount = sellcount;
            }

            public Object getSort() {
                return sort;
            }

            public void setSort(Object sort) {
                this.sort = sort;
            }

            public String getKeyWord() {
                return keyWord;
            }

            public void setKeyWord(String keyWord) {
                this.keyWord = keyWord;
            }

            public int getDelFalg() {
                return delFalg;
            }

            public void setDelFalg(int delFalg) {
                this.delFalg = delFalg;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getBuyNumber() {
                return buyNumber;
            }

            public void setBuyNumber(int buyNumber) {
                this.buyNumber = buyNumber;
            }

            public String getCatalogName() {
                return catalogName;
            }

            public void setCatalogName(String catalogName) {
                this.catalogName = catalogName;
            }

            public Object getParameters() {
                return parameters;
            }

            public void setParameters(Object parameters) {
                this.parameters = parameters;
            }

            public String getPictures() {
                return pictures;
            }

            public void setPictures(String pictures) {
                this.pictures = pictures;
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
