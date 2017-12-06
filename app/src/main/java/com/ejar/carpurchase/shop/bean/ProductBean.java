package com.ejar.carpurchase.shop.bean;

import java.util.List;

/**
 * Created by wongxd on 2017/7/26.
 * 商城首页商品列表
 */
public class ProductBean {

    /**
     * code : 200
     * msg : OK
     * data : {"totalPage":1,"productList":[{"id":39,"name":"44444","price":34,"nowPrice":34,"picture":"/static/product/picture/458f8f02-a589-4fa7-ad08-90e72b313f8d.jpg,/static/product/picture/94f6c34d-da05-408d-8e58-0a66f7fc49a6.jpg,/static/product/picture/44dccbf6-90f5-4d73-b82f-ee028c3dd114.jpg","status":1,"productHTML":"<p>444444444444444444<\/p>","image":"/static/product/image/d01aedca-ac3d-4e4f-b7b5-17105e33dfd1.jpg","catalogID":1,"sellcount":88,"sort":null,"keyWord":"2323","delFalg":0,"time":null,"attributes":null,"catalogName":"","parameters":null,"pictures":""}]}
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
         * totalPage : 1
         * productList : [{"id":39,"name":"44444","price":34,"nowPrice":34,"picture":"/static/product/picture/458f8f02-a589-4fa7-ad08-90e72b313f8d.jpg,/static/product/picture/94f6c34d-da05-408d-8e58-0a66f7fc49a6.jpg,/static/product/picture/44dccbf6-90f5-4d73-b82f-ee028c3dd114.jpg","status":1,"productHTML":"<p>444444444444444444<\/p>","image":"/static/product/image/d01aedca-ac3d-4e4f-b7b5-17105e33dfd1.jpg","catalogID":1,"sellcount":88,"sort":null,"keyWord":"2323","delFalg":0,"time":null,"attributes":null,"catalogName":"","parameters":null,"pictures":""}]
         */

        private int totalPage;
        private List<ProductListBean> productList;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<ProductListBean> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductListBean> productList) {
            this.productList = productList;
        }

        public static class ProductListBean {
            /**
             * id : 39
             * name : 44444
             * price : 34.0
             * nowPrice : 34.0
             * picture : /static/product/picture/458f8f02-a589-4fa7-ad08-90e72b313f8d.jpg,/static/product/picture/94f6c34d-da05-408d-8e58-0a66f7fc49a6.jpg,/static/product/picture/44dccbf6-90f5-4d73-b82f-ee028c3dd114.jpg
             * status : 1
             * productHTML : <p>444444444444444444</p>
             * image : /static/product/image/d01aedca-ac3d-4e4f-b7b5-17105e33dfd1.jpg
             * catalogID : 1
             * sellcount : 88
             * sort : null
             * keyWord : 2323
             * delFalg : 0
             * time : null
             * attributes : null
             * catalogName :
             * parameters : null
             * pictures :
             */

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
            private Object time;
            private Object attributes;
            private String catalogName;
            private Object parameters;
            private String pictures;

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

            public Object getTime() {
                return time;
            }

            public void setTime(Object time) {
                this.time = time;
            }

            public Object getAttributes() {
                return attributes;
            }

            public void setAttributes(Object attributes) {
                this.attributes = attributes;
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
        }
    }
}
