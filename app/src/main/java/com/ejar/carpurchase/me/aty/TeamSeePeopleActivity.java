package com.ejar.carpurchase.me.aty;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ganxin.library.LoadDataLayout;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtySeePeopleBinding;
import com.ejar.carpurchase.me.bean.ChargeBean;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

public class TeamSeePeopleActivity extends BaseBindingActivity<AtySeePeopleBinding> {


    private AppCompatActivity thisActivity;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_see_people);
        thisActivity = this;
        mContext = this.getApplicationContext();
        initClick();
        initRecycleView();
        getList(false);
    }

    private void initClick() {
        bindingView.ivReturn.setOnClickListener(v -> finish());
    }

    private ArrayList<ChargeBean.RecordBean.RecordsBean> list;
    private RvAdapter adapter;

    private void initRecycleView() {
        list = new ArrayList<>();
        adapter = new RvAdapter();
        bindingView.rvSeePeople.setAdapter(adapter);
        bindingView.rvSeePeople.setLayoutManager(new LinearLayoutManager(mContext));

        bindingView.srlSeePeople.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                getList(false);
            }
        });


        // 滑动最后一个Item的时候回调onLoadMoreRequested方法
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getList(true);
            }
        }, bindingView.rvSeePeople);

    }

    int pageNo = 1;

    private void getList(boolean isLoadMore) {
        if (!isLoadMore)
            bindingView.clLoaddata.setStatus(LoadDataLayout.LOADING);
        bindingView.clLoaddata.setOnReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                getList(isLoadMore);
            }
        });
        Logger.e(App.token);
        String url = UrlConf.Team_See_People_URL;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url)
                        .addParams("token", App.token)
                        .addParams("pageNo", pageNo + "")
                        .addParams("pageSize", "10")
                , url, thisActivity, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        bindingView.srlSeePeople.setRefreshing(false);
                        Logger.e(response);
                        Gson gson = new Gson();
                        TeamSeePeopleBean  bean = gson.fromJson(response, TeamSeePeopleBean.class);
                        if (bean.getCode() == 200) {
                            bindingView.clLoaddata.setStatus(LoadDataLayout.SUCCESS);
                            int totalPage = bean.getUserxok().getPages();
                            if (isLoadMore) {
                                if (pageNo > totalPage) {
                                    adapter.loadMoreEnd();
                                } else {
                                    adapter.addData(bean.getUserxok().getRecords());
                                    adapter.loadMoreComplete();
                                }
                            } else {
                                bindingView.srlSeePeople.setRefreshing(false);
                                adapter.setNewData(bean.getUserxok().getRecords());
                                // 检查是否满一屏，如果不满足关闭loadMore
                                adapter.disableLoadMoreIfNotFullPage(bindingView.rvSeePeople);
                            }
                            if (bean.getUserxok().getRecords().size() != 0) {
                                pageNo++;
                            } else if (!isLoadMore)
                                bindingView.clLoaddata.setStatus(LoadDataLayout.EMPTY);
                            bindingView.tvListSize.setText("共 " + adapter.getData().size() + " 个用户");
                        } else {
                            if (isLoadMore) adapter.loadMoreFail();
                            else bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        bindingView.srlSeePeople.setRefreshing(false);
                        bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                    }
                });
    }

    class RvAdapter extends BaseQuickAdapter<TeamSeePeopleBean.UserxokBean.RecordsBean, BaseViewHolder> {
        public RvAdapter() {
            super(R.layout.item_rv_see_people);
        }

        @Override
        protected void convert(BaseViewHolder helper, TeamSeePeopleBean.UserxokBean.RecordsBean item) {
            helper.setText(R.id.tv_name, "真实姓名: " + item.getName() + "")
                    .setText(R.id.tv_id, "账号: " + item.getAccount() + "");

        }


    }


    public static class TeamSeePeopleBean {

        /**
         * msg : 查看成功
         * userxok : {"offset":0,"limit":2147483647,"total":17,"size":10,"pages":2,"current":1,"searchCount":true,"openSort":true,"optimizeCount":false,"orderByField":null,"records":[{"name":"李术云","id":27,"state":1,"account":"15609010990"},{"name":"13881142938","id":33,"state":4,"account":"13881142938"},{"name":"15528526888","id":38,"state":1,"account":"15528526888"},{"name":"13550847858","id":39,"state":4,"account":"13550847858"},{"name":"13320907383","id":40,"state":1,"account":"13320907383"},{"name":"15228719875","id":41,"state":1,"account":"15228719875"},{"name":"17608153626","id":48,"state":1,"account":"17608153626"},{"name":"钟定飞","id":57,"state":4,"account":"15908234541"},{"name":"陈胜","id":80,"state":1,"account":"13508128488"},{"name":"17381682661","id":116,"state":4,"account":"17381682661"}],"condition":null,"offsetCurrent":0,"asc":true}
         * code : 200
         */

        private String msg;
        private UserxokBean userxok;
        private int code;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public UserxokBean getUserxok() {
            return userxok;
        }

        public void setUserxok(UserxokBean userxok) {
            this.userxok = userxok;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public static class UserxokBean {
            /**
             * offset : 0
             * limit : 2147483647
             * total : 17
             * size : 10
             * pages : 2
             * current : 1
             * searchCount : true
             * openSort : true
             * optimizeCount : false
             * orderByField : null
             * records : [{"name":"李术云","id":27,"state":1,"account":"15609010990"},{"name":"13881142938","id":33,"state":4,"account":"13881142938"},{"name":"15528526888","id":38,"state":1,"account":"15528526888"},{"name":"13550847858","id":39,"state":4,"account":"13550847858"},{"name":"13320907383","id":40,"state":1,"account":"13320907383"},{"name":"15228719875","id":41,"state":1,"account":"15228719875"},{"name":"17608153626","id":48,"state":1,"account":"17608153626"},{"name":"钟定飞","id":57,"state":4,"account":"15908234541"},{"name":"陈胜","id":80,"state":1,"account":"13508128488"},{"name":"17381682661","id":116,"state":4,"account":"17381682661"}]
             * condition : null
             * offsetCurrent : 0
             * asc : true
             */

            private int offset;
            private int limit;
            private int total;
            private int size;
            private int pages;
            private int current;
            private boolean searchCount;
            private boolean openSort;
            private boolean optimizeCount;
            private Object orderByField;
            private Object condition;
            private int offsetCurrent;
            private boolean asc;
            private List<RecordsBean> records;

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public int getLimit() {
                return limit;
            }

            public void setLimit(int limit) {
                this.limit = limit;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public int getPages() {
                return pages;
            }

            public void setPages(int pages) {
                this.pages = pages;
            }

            public int getCurrent() {
                return current;
            }

            public void setCurrent(int current) {
                this.current = current;
            }

            public boolean isSearchCount() {
                return searchCount;
            }

            public void setSearchCount(boolean searchCount) {
                this.searchCount = searchCount;
            }

            public boolean isOpenSort() {
                return openSort;
            }

            public void setOpenSort(boolean openSort) {
                this.openSort = openSort;
            }

            public boolean isOptimizeCount() {
                return optimizeCount;
            }

            public void setOptimizeCount(boolean optimizeCount) {
                this.optimizeCount = optimizeCount;
            }

            public Object getOrderByField() {
                return orderByField;
            }

            public void setOrderByField(Object orderByField) {
                this.orderByField = orderByField;
            }

            public Object getCondition() {
                return condition;
            }

            public void setCondition(Object condition) {
                this.condition = condition;
            }

            public int getOffsetCurrent() {
                return offsetCurrent;
            }

            public void setOffsetCurrent(int offsetCurrent) {
                this.offsetCurrent = offsetCurrent;
            }

            public boolean isAsc() {
                return asc;
            }

            public void setAsc(boolean asc) {
                this.asc = asc;
            }

            public List<RecordsBean> getRecords() {
                return records;
            }

            public void setRecords(List<RecordsBean> records) {
                this.records = records;
            }

            public static class RecordsBean {
                /**
                 * name : 李术云
                 * id : 27
                 * state : 1
                 * account : 15609010990
                 */

                private String name;
                private int id;
                private int state;
                private String account;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getState() {
                    return state;
                }

                public void setState(int state) {
                    this.state = state;
                }

                public String getAccount() {
                    return account;
                }

                public void setAccount(String account) {
                    this.account = account;
                }
            }
        }
    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（14660689144）输出（"2016年6月16日 17时21分54秒"）
     *
     * @param time
     * @return
     */
    public String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc));
        return times;
    }
}
