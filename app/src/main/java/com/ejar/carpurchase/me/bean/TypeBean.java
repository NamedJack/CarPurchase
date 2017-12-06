package com.ejar.carpurchase.me.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Created by wxd1 on 2017/6/12.
 */

public class TypeBean {

    /**
     * msg : 查看成功
     * code : 200
     * data : [{"id":1,"name":"服装","pid":0},{"id":5,"name":"百货","pid":0}]
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

    public static class DataBean implements IPickerViewData{
        /**
         * id : 1
         * name : 服装
         * pid : 0
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

        @Override
        public String getPickerViewText() {
            return name;
        }
    }
}
