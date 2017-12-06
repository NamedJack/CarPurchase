package com.ejar.carpurchase.me.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

public class LocationBean {
    /**
     * msg : 查看成功
     * code : 200
     * data : [{"id":1,"pid":0,"level":1,"name":"北京"}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    public static class DataBean implements IPickerViewData {
        /**
         * id : 1
         * pid : 0
         * level : 1
         * name : 北京
         */

        private int id;
        private int pid;
        private int level;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String getPickerViewText() {
            return name;
        }
    }


}
