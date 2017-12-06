package com.ejar.carpurchase.shop;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.rx.RxBus;
import com.ejar.carpurchase.base.rx.RxEventCodeType;
import com.ejar.carpurchase.base.rx.Subscribe;
import com.ejar.carpurchase.base.skuLib.Sku;
import com.ejar.carpurchase.base.skuLib.model.BaseSkuModel;
import com.ejar.carpurchase.custom.layoutManeger.SpaceItemDecoration;
import com.ejar.carpurchase.custom.layoutManeger.SpecLayoutManager;
import com.ejar.carpurchase.shop.bean.SpecBean;
import com.ejar.carpurchase.shop.sku.ItemClickListener;
import com.ejar.carpurchase.shop.sku.ProductModel;
import com.ejar.carpurchase.shop.sku.SkuAdapter;
import com.ejar.carpurchase.shop.sku.UiData;
import com.ejar.carpurchase.utils.DensityUtil;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.ejar.carpurchase.BuildConfig.LOG_DEBUG;

/**
 * wongxd 参考 http://blog.csdn.net/wbwjx/article/details/50507344
 */
public class SpecSelectFragment_rv extends android.support.v4.app.DialogFragment {
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


    public static SpecSelectFragment_rv newInstance() {
        Bundle args = new Bundle();
        SpecSelectFragment_rv fragment = new SpecSelectFragment_rv();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        必须在onCreate 中设置Style ,而在OnCreateView 中设置无效,因为此时对话框已经init了
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
        RxBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        RxBus.getDefault().unRegister(this);
        super.onDestroyView();
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


        ivAdd.setOnClickListener(v -> doNumLogic(true));

        ivReduce.setOnClickListener(v -> doNumLogic(false));

        tvAddToCart.setOnClickListener(v -> doAddToCart());
        tvBuy.setOnClickListener(v -> doBuy());

        String url = UrlConf.GoodSpec_URL;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url).addParams("productID", productId)
                , url, (AppCompatActivity) getActivity(), new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        initData(response);
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {

                    }
                });

        return view;
    }


    /**
     * 立即购买
     */
    private void doBuy() {
        if (specId.equals("null")) {
            TU.cT(tvSpec.getText().toString());
            return;
        }
        Long num = Long.valueOf(tvNum.getText().toString().trim());
        if (productStock < num) {
            TU.cT("库存不足");
            return;
        }
//        double amount = Double.valueOf(price) * num;
        OkHttpUtils.post().url(UrlConf.BuyNow_URL)
                .addParams("token", App.token)
                .addParams("productId", productId)
                .addParams("detailId", specId)
                .addParams("buyNumber", num + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        TU.cT("购买失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Logger.e(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.optInt("code") == 200) {
                                Intent i = new Intent(getActivity(), OrderConfirmActivity.class);
                                i.putExtra("json", response);
                                startActivity(i);
                                getDialog().dismiss();
                            } else TU.cT("购买失败! " + jsonObject.optString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Logger.e("productID " + productId + "  detailId  " + specId + "  num  " + num + " token " + App.token);
    }

    /**
     * 添加到购物车
     */
    private void doAddToCart() {
        if (specId.equals("null")) {
            TU.cT(tvSpec.getText().toString());
            return;
        }
        Long num = Long.valueOf(tvNum.getText().toString().trim());
        if (productStock < num) {
            TU.cT("库存不足");
            return;
        }
        Logger.e(specId);
        double amount = Double.valueOf(price) * num;
        OkHttpUtils.post().url(UrlConf.AddToCart_URL).addParams("productID", productId)
                .addParams("detailId", specId)
                .addParams("number", num + "")
                .addParams("amount", amount + "")
                .addParams("token", App.token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        TU.cT("添加到购物车失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        /**
                         * {
                         "status": 200,
                         "msg": "ok",
                         "data": null
                         }
                         */
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            TU.cT(jsonObject.optString("msg"));
                            if (jsonObject.optInt("code") == 200) {
                                TU.cT("成功添加购物车");
                                getDialog().dismiss();
                            } else TU.cT("添加到购物车失败! " + jsonObject.optString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 数量的加减
     *
     * @param isAdd
     */
    private void doNumLogic(boolean isAdd) {
        String numS = tvNum.getText().toString().trim();
        if (TextUtils.isEmpty(numS)) {
            tvNum.setText("1");
            return;
        }
        long num = Long.valueOf(numS);
        if (isAdd) {
            num++;
        } else {
            if (num <= 1) num = 1;
            else
                num--;
        }
        tvNum.setText(num + "");
    }

    UiData mUiData;
    ProductModel products;


    /**
     * 初始化展示的数据
     */
    private void initData(String json) {

        Glide.with(this).load(goodImgPath).centerCrop().placeholder(R.drawable.placeholder).into(iv);
        SpecBean bean = new Gson().fromJson(json, SpecBean.class);
        List<SpecBean.DataBean.AttributeBean> attrs = bean.getData().getAttribute();

        List<ProductModel.AttributesEntity> pAttr = new ArrayList<>();
        for (SpecBean.DataBean.AttributeBean attr : attrs) {
            ProductModel.AttributesEntity group = new ProductModel.AttributesEntity();
            group.setName(attr.getKey());
            group.setId(attr.getValue().get(0).getPid());
            for (SpecBean.DataBean.AttributeBean.ValueBean item : attr.getValue()) {
                group.getAttributeMembers().add(new ProductModel.AttributesEntity.AttributeMembersEntity(item.getPid(), item.getId(), item.getName()));
            }
            pAttr.add(group);
        }

        List<SpecBean.DataBean.CombinationBean> comb = bean.getData().getCombination();
        Map<String, BaseSkuModel> initData = new HashMap<>();
        for (SpecBean.DataBean.CombinationBean i : comb) {
            initData.put(i.getDetailId(), new BaseSkuModel(i.getPrice(), i.getStock()));
        }

        /**
         * 所有规格 的 组名
         */
        List<String> groupNameList = new ArrayList<String>();
        for (SpecBean.DataBean.AttributeBean s : attrs) {
            groupNameList.add(s.getKey());
        }

        mUiData = new UiData();
        mUiData.setGroupNameList(groupNameList);
        products = new ProductModel();

        products.setProductStocks(initData);
        products.setAttributes(pAttr);


        //添加list组
        for (int i = 0; i < products.getAttributes().size(); i++) {
            View viewList = View.inflate(getContext(), R.layout.bottom_sheet_group, null);
            TextView tvTitle = (TextView) viewList.findViewById(R.id.tv_title);
            RecyclerView recyclerViewBottom = (RecyclerView) viewList.findViewById(R.id.recycler_bottom);
            SkuAdapter skuAdapter = new SkuAdapter(products.getAttributes().get(i).getAttributeMembers(), products.getAttributes().get(i).getName());
            mUiData.getAdapters().add(skuAdapter);
            int item = 4;//设置列数
//            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(item, StaggeredGridLayoutManager.VERTICAL);
            SpecLayoutManager layoutManager = new SpecLayoutManager();
            layoutManager.setAutoMeasureEnabled(true);   //必须，防止recyclerview高度为wrap时测量item高度0

            recyclerViewBottom.addItemDecoration(new SpaceItemDecoration(DensityUtil.dip2px(getContext(),5),DensityUtil.dip2px(getContext(),10)));
            recyclerViewBottom.setLayoutManager(layoutManager);
            recyclerViewBottom.setAdapter(skuAdapter);

            tvTitle.setText(products.getAttributes().get(i).getName());
            llSpecContainer.addView(viewList);
        }
        // SKU 计算
        mUiData.setResult(Sku.skuCollection(products.getProductStocks()));
        if (LOG_DEBUG) {
            for (String key : mUiData.getResult().keySet()) {
                Log.w("SKU Result", "key = " + key + " value = " + mUiData.getResult().get(key));
            }
        }
        //设置点击监听器
        for (SkuAdapter adapter : mUiData.getAdapters()) {
            ItemClickListener listener = new ItemClickListener(mUiData, adapter);
            adapter.setOnClickListener(listener);
        }
        // 初始化按钮
        for (int i = 0; i < mUiData.getAdapters().size(); i++) {
            for (ProductModel.AttributesEntity.AttributeMembersEntity entity : mUiData.getAdapters().get(i).getAttributeMembersEntities()) {
                if (mUiData.getResult().get(entity.getAttributeMemberId() + "") == null || mUiData.getResult().get(entity.getAttributeMemberId() + "").getStock() <= 0) {
                    entity.setStatus(2);
                }
            }
        }


    }

    @Subscribe(code = RxEventCodeType.SKU_SELECTED_YET)
    public void doShowSpec(String s) {
        tvSpec.setText(s);
    }

    /**
     * 选择的规格id
     */
    private String specId = "null";

    private String price = "";

    private long productStock = 0;

    @Subscribe(code = RxEventCodeType.SKU_GETSPECID)
    public void doShowSpecId(String s) {
        specId = s;
        if (s.equals("null")) {
            tvStock.setText("");
            tvPrice.setText("");
            return;
        }
        s = s.substring(0, s.length() - 1);
        specId = s;
        productStock = mUiData.getResult().get(s).getStock();
        price = mUiData.getResult().get(s).getPrice();
        tvPrice.setText("￥ " + price);
        tvStock.setText("库存 " + productStock);
//        Logger.e("specid " + s + " price" + mUiData.getResult().get(s).getPrice() + " stock" + mUiData.getResult().get(s).getStock());
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


    private static String productId;
    private static String goodImgPath;

    /**
     * 可以避免重复
     *
     * @param appCompatActivity
     * @return
     */
    public static SpecSelectFragment_rv showDialog(AppCompatActivity appCompatActivity, String id, String goodImg) {
        productId = id;
        goodImgPath = goodImg;
        FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
        SpecSelectFragment_rv bottomDialogFragment =
                (SpecSelectFragment_rv) fragmentManager.findFragmentByTag(TAG);
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


}
