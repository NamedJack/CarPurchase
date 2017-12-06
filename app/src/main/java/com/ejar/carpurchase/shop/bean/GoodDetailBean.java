package com.ejar.carpurchase.shop.bean;

/**
 * Created by wongxd on 2017/7/26.
 */

public class GoodDetailBean {

    /**
     * code : 200
     * msg : OK
     * data : {"id":50,"name":"魅族","price":2999,"nowPrice":1899,"picture":"/static/product/picture/61f421e5-8a62-420a-826c-a33d779f6861.jpg,/static/product/picture/08ffd67a-870c-4985-b3c9-30e1add0d4c9.jpg","status":1,"productHTML":"<p>魅族PRO 7（移动4G） 主屏尺寸:5.2英寸<\/p><p><br><\/p>","image":"","catalogID":1,"sellcount":99,"sort":null,"keyWord":"魅族","delFalg":0,"time":"2017-07-26 01:47:46","attributes":null,"catalogName":null,"parameters":null,"pictures":""}
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
         * id : 50
         * name : 魅族
         * price : 2999
         * nowPrice : 1899
         * picture : /static/product/picture/61f421e5-8a62-420a-826c-a33d779f6861.jpg,/static/product/picture/08ffd67a-870c-4985-b3c9-30e1add0d4c9.jpg
         * status : 1
         * productHTML : <p>魅族PRO 7（移动4G） 主屏尺寸:5.2英寸</p><p><br></p>
         * image :
         * catalogID : 1
         * sellcount : 99
         * sort : null
         * keyWord : 魅族
         * delFalg : 0
         * time : 2017-07-26 01:47:46
         * attributes : null
         * catalogName : null
         * parameters : null
         * pictures :
         */

        private int id;
        private String name;
        private int price;
        private int nowPrice;
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
        private Object attributes;
        private Object catalogName;
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getNowPrice() {
            return nowPrice;
        }

        public void setNowPrice(int nowPrice) {
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

        public Object getAttributes() {
            return attributes;
        }

        public void setAttributes(Object attributes) {
            this.attributes = attributes;
        }

        public Object getCatalogName() {
            return catalogName;
        }

        public void setCatalogName(Object catalogName) {
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
