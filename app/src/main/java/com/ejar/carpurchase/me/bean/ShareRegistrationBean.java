package com.ejar.carpurchase.me.bean;

import java.util.List;

/**
 * Created by wxd1 on 2017/6/8.
 */

public class ShareRegistrationBean {

    /**
     * msg : 查看成功
     * code : 200
     * data : {"page":{"pageNo":1,"startNum":0,"size":10,"totleNumber":0,"totlePage":0},"user":[{"id":456,"account":"","name":"","phone":""}]}
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
         * page : {"pageNo":1,"startNum":0,"size":10,"totleNumber":0,"totlePage":0}
         * user : [{"id":456,"account":"","name":"","phone":""}]
         */

        private PageBean page;
        private List<UserBean> user;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public List<UserBean> getUser() {
            return user;
        }

        public void setUser(List<UserBean> user) {
            this.user = user;
        }

        public static class PageBean {
            /**
             * pageNo : 1
             * startNum : 0
             * size : 10
             * totleNumber : 0
             * totlePage : 0
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

        public static class UserBean {
            /**
             * id : 456
             * account :
             * name :
             * phone :
             */

            private int id;
            private String account;
            private String name;
            private String phone;

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

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }
}
