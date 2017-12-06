package com.ejar.carpurchase.shop;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.skuLib.Sku;
import com.ejar.carpurchase.base.skuLib.model.BaseSkuModel;
import com.ejar.carpurchase.shop.bean.SpecBean;
import com.ejar.carpurchase.utils.DensityUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ejar.carpurchase.BuildConfig.LOG_DEBUG;

/**
 * wongxd 参考 http://blog.csdn.net/wbwjx/article/details/50507344
 */
public class SpecSelectFragment extends android.support.v4.app.DialogFragment {
    public static final String TAG = "SpecSelectFragment";

    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};


    private ImageView iv;
    private ImageView ivClose;
    private TextView tvPrice;
    private TextView tvStock;
    private TextView tvSpec;
    private ImageView ivReduce;
    private ImageView ivAdd;
    private TextView tvNum;
    private TextView tvAddToCart;
    private TextView tvBuy;
    private LinearLayout llSpecContainer;

    /**
     * 当前选择的规格组合
     */

    Map<String, SpecBean.DataBean.AttributeBean.ValueBean> selectedEntities = new HashMap<>();


    public static SpecSelectFragment newInstance() {
        Bundle args = new Bundle();
        SpecSelectFragment fragment = new SpecSelectFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        必须在onCreate 中设置Style ,而在OnCreateView 中设置无效,因为此时对话框已经init了
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = initView();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.BottomDialog);

        builder.setView(view);

        AlertDialog dialog = builder.create();

        dialog.setCanceledOnTouchOutside(true);

        // 设置宽度为屏宽、靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        //设置没有效果
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        return dialog;
    }

    @NonNull
    private View initView() {
        View view = View.inflate(getActivity(), R.layout.fgt_spec_select, null);

        iv = (ImageView) view.findViewById(R.id.iv);
        ivClose = (ImageView) view.findViewById(R.id.iv_close);

        tvPrice = (TextView) view.findViewById(R.id.tv_price);
        tvStock = (TextView) view.findViewById(R.id.tv_stock);
        tvSpec = (TextView) view.findViewById(R.id.tv_spec);

        ivReduce = (ImageView) view.findViewById(R.id.iv_reduce);
        ivAdd = (ImageView) view.findViewById(R.id.iv_add);
        tvNum = (TextView) view.findViewById(R.id.tv_num_edit);

        tvAddToCart = (TextView) view.findViewById(R.id.tv_add_to_cart);
        tvBuy = (TextView) view.findViewById(R.id.tv_buy);

        llSpecContainer = (LinearLayout) view.findViewById(R.id.ll_spec_container);


        ivClose.setOnClickListener((v) -> getDialog().dismiss());

        initData();
        return view;
    }


    private String json = "{\n" +
            "  \"status\": 200,\n" +
            "  \"msg\": \"OK\",\n" +
            "  \"data\": {\n" +
            "    \"attribute\": [\n" +
            "      {\n" +
            "        \"key\": \"颜色\",\n" +
            "        \"value\": [\n" +
            "          {\n" +
            "            \"id\": 4,\n" +
            "            \"name\": \"褐色\",\n" +
            "            \"pid\": 1\n" +
            "          },\n" +
            "          {\n" +
            "            \"id\": 1,\n" +
            "            \"name\": \"白色\",\n" +
            "            \"pid\": 1\n" +
            "          }\n" +
            "        ],\n" +
            "        \"attribute\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"key\": \"尺寸\",\n" +
            "        \"value\": [\n" +
            "          {\n" +
            "            \"id\": 5,\n" +
            "            \"name\": \"L\",\n" +
            "            \"pid\": 2\n" +
            "          },\n" +
            "          {\n" +
            "            \"id\": 2,\n" +
            "            \"name\": \"X\",\n" +
            "            \"pid\": 2\n" +
            "          }\n" +
            "        ],\n" +
            "        \"attribute\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"key\": \"材质\",\n" +
            "        \"value\": [\n" +
            "          {\n" +
            "            \"id\": 3,\n" +
            "            \"name\": \"玻璃\",\n" +
            "            \"pid\": 3\n" +
            "          },\n" +
            "          {\n" +
            "            \"id\": 6,\n" +
            "            \"name\": \"不锈钢\",\n" +
            "            \"pid\": 3\n" +
            "          }\n" +
            "        ],\n" +
            "        \"attribute\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"combination\": [\n" +
            "      {\n" +
            "        \"id\": 1,\n" +
            "        \"productID\": 1,\n" +
            "        \"detailId\": \"1,2,3\",\n" +
            "        \"stock\": 10,\n" +
            "        \"price\": \"9.00\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 2,\n" +
            "        \"productID\": 1,\n" +
            "        \"detailId\": \"1,2,6\",\n" +
            "        \"stock\": 0,\n" +
            "        \"price\": \"0.00\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 3,\n" +
            "        \"productID\": 1,\n" +
            "        \"detailId\": \"1,3,5\",\n" +
            "        \"stock\": 19,\n" +
            "        \"price\": \"99.00\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 4,\n" +
            "        \"productID\": 1,\n" +
            "        \"detailId\": \"4,5,6\",\n" +
            "        \"stock\": 0,\n" +
            "        \"price\": \"0.00\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}\n";


    /**
     * sku 处理后的 组合字典
     */
    Map<String, BaseSkuModel> skuResult;

    List<TagThing> tags = new ArrayList<>();

    /**
     * 初始化展示的数据
     */
    private void initData() {
        SpecBean bean = new Gson().fromJson(json, SpecBean.class);
        List<SpecBean.DataBean.AttributeBean> attrs = bean.getData().getAttribute();

        //Sku logic
        List<SpecBean.DataBean.CombinationBean> comb = bean.getData().getCombination();
        Map<String, BaseSkuModel> initData = new HashMap<>();
        for (SpecBean.DataBean.CombinationBean i : comb) {
            initData.put(i.getDetailId(), new BaseSkuModel(i.getPrice(), i.getStock()));
        }
        skuResult = Sku.skuCollection(initData);

        if (LOG_DEBUG) {
            for (String k : skuResult.keySet()) {
                Log.e("SKU Result", "  key = " + k + "  value = " + skuResult.get(k).toString());
            }
        }


        /**
         * 所有规格 的 组名
         */
        List<String> groupNameList = new ArrayList<String>();
        for (SpecBean.DataBean.AttributeBean s : attrs) {
            groupNameList.add(s.getKey());
        }


        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        for (int i = 0; i < attrs.size(); i++) {

            TagFlowLayout flow = new TagFlowLayout(getContext());

            flow.setLayoutParams(lp);
            flow.setMaxSelectCount(1);
            flow.setPadding(20, 20, 20, 20);

            String key = attrs.get(i).getKey();
            List<SpecBean.DataBean.AttributeBean.ValueBean> values = attrs.get(i).getValue();
            //设置状态
            for (SpecBean.DataBean.AttributeBean.ValueBean v : values) {
                if (skuResult.get(v.getId() + "") == null || skuResult.get(v.getId() + "").getStock() <= 0) {
                    v.setStatus(1);
                } else v.setStatus(0);
            }

            flow.setAdapter(new TagAdapter<SpecBean.DataBean.AttributeBean.ValueBean>(values) {

                @Override
                public View getView(FlowLayout parent, int position, SpecBean.DataBean.AttributeBean.ValueBean valueBean) {
                    TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.tv, parent, false);
                    tv.setText(valueBean.getName());
                    switch (valueBean.getStatus()) {
                        case 0:
                            tv.setAlpha(1f);
                            tv.setBackground(getResources().getDrawable(R.drawable.tag_bg));
                            tv.setTextColor(getResources().getColorStateList(R.color.text_color));
//                            tv.setBackground(getResources().getDrawable(R.drawable.normal_bg));
//                            tv.setTextColor(Color.BLACK);
                            break;
                        case 1:
                            tv.setAlpha(0.4f);
                            tv.setBackground(getResources().getDrawable(R.drawable.unclickable_bg));
                            tv.setTextColor(Color.BLACK);
                            break;

                        case 2://check
                            tv.setAlpha(1f);
                            tv.setBackground(getResources().getDrawable(R.drawable.checked_bg));
                            tv.setTextColor(Color.WHITE);
                            break;
                    }
                    return tv;
                }
            });


            flow.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    if (values.get(position).getStatus() == 1) {
                        selectedEntities.remove(key);
                        flow.getSelectedList().clear();
                        return false;
                    }
                    if (flow.getSelectedList().size() == 0) {
                        selectedEntities.remove(key);
                    } else {
                        selectedEntities.put(key, values.get(position));
                    }

                    String tempSku = "";
                    for (String groupName : groupNameList) {

                        if (selectedEntities.get(groupName) != null) {
                            if (groupNameList.indexOf(groupName) == groupNameList.size() - 1)
                                tempSku += selectedEntities.get(groupName).getId();
                            else
                                tempSku += selectedEntities.get(groupName).getId() + ",";
                        }
                    }

                    for (TagThing thing : tags) {
                        for (SpecBean.DataBean.AttributeBean.ValueBean valueBean : thing.getData()) {
                            // 处理没有数据和没有库存(检测单个)
                            if (skuResult.get(valueBean.getId() + "") == null
                                    || skuResult.get(valueBean.getId() + "").getStock() <= 0) {
                                valueBean.setStatus(1);
                            } else if (thing.getTag().getSelectedList().contains(thing.getData().indexOf(valueBean))) {
                                valueBean.setStatus(2);
                            } else {
                                valueBean.setStatus(0);
                            }


                            // 检查数据
                            if (skuResult.get(tempSku) != null && skuResult.get(tempSku).getStock() > 0) {
                                valueBean.setStatus(valueBean.getStatus() == 2 ? 2 : 0);
                            } else {
                                valueBean.setStatus(1);
                            }
                        }

                        thing.getTag().getAdapter().notifyDataChanged();
                    }


                    //规格提示
                    String s = "";
                    if (selectedEntities.values().size() == attrs.size()) {
                        //所有规格选择完了，展示价格 和 库存
                        for (String groupName : groupNameList) {
                            for (String k : selectedEntities.keySet()) {
                                if (groupName.equals(k))
                                    s += selectedEntities.get(k).getName() + " ";
                            }
                        }
                        tvSpec.setText("已选 " + s);
                    } else {
                        for (String groupName : groupNameList) {
                            if (!selectedEntities.keySet().contains(groupName))
                                s += groupName + " ";
                        }
                        tvSpec.setText("请选择 " + s);
                    }
                    return true;
                }
            });


            //添加到container

            TextView tv = new TextView(getContext());
            tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getContext(), 30)));
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setPadding(DensityUtil.dip2px(getContext(), 10), 0, 0, 0);
            tv.setText(attrs.get(i).getKey());
            tv.setTextColor(Color.BLACK);

            View v = new View(getContext());
            v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getContext(), 0.5f)));
            v.setBackgroundColor(getResources().getColor(R.color.divier));

            llSpecContainer.addView(tv);
            llSpecContainer.addView(flow);
            llSpecContainer.addView(v);

            tags.add(new TagThing(flow, values));
        }


    }


    @Override
    public void onStart() {
        super.onStart();
        //全屏
        Dialog dialog = getDialog();
        if (null != dialog) {
            dialog.getWindow().setLayout(-1, -2);
        }
    }

    /**
     * 可以避免重复
     *
     * @param appCompatActivity
     * @return
     */
    public static SpecSelectFragment showDialog(AppCompatActivity appCompatActivity) {
        FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
        SpecSelectFragment bottomDialogFragment =
                (SpecSelectFragment) fragmentManager.findFragmentByTag(TAG);
        if (null == bottomDialogFragment) {
            bottomDialogFragment = newInstance();
        }

        if (!appCompatActivity.isFinishing()
                && null != bottomDialogFragment
                && !bottomDialogFragment.isAdded()) {
            fragmentManager.beginTransaction()
                    .add(bottomDialogFragment, TAG)
                    .commitAllowingStateLoss();
        }

        return bottomDialogFragment;
    }


    public static class TagThing {
        private TagFlowLayout tag;
        private List<SpecBean.DataBean.AttributeBean.ValueBean> data;

        public TagThing(TagFlowLayout tag, List<SpecBean.DataBean.AttributeBean.ValueBean> values) {
            this.tag = tag;
            this.data = values;
        }

        public TagFlowLayout getTag() {
            return tag;
        }

        public void setTag(TagFlowLayout tag) {
            this.tag = tag;
        }

        public List<SpecBean.DataBean.AttributeBean.ValueBean> getData() {
            return data;
        }

        public void setData(List<SpecBean.DataBean.AttributeBean.ValueBean> data) {
            this.data = data;
        }
    }
}
