package com.ejar.carpurchase.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ejar.carpurchase.R;

/**
 * Created by wongxd on 17-7-20.
 * 底部带指示器的textview
 */

public class SubscriptTextview extends LinearLayout {

    private TextView tv;
    private View mView;
    private View v;

    public SubscriptTextview(Context context) {
        this(context, null);
    }

    public SubscriptTextview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SubscriptTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttrValues(context, attrs);
    }

    private void initView(Context context) {
        mView = View.inflate(context, R.layout.subscript_textview, this);
        tv = (TextView) mView.findViewById(R.id.tv);
        v = mView.findViewById(R.id.v);
    }


    int mTextSize;
    int mTextColor;

    /**
     * 初始化属性集合
     *
     * @param context
     * @param attrs
     */
    private void initAttrValues(Context context, AttributeSet attrs) {
        // //获取在AttributeSet中定义的 VerifyCode 中声明的属性的集合
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.subscriptTextview);
        //获取TypeArray的长度
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            //获取此项属性的ID
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.subscriptTextview_textSize:
                    // 默认设置为16sp，TypeValue类 px转sp 一个转换类
                    mTextSize = typedArray.getDimensionPixelSize(index, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    tv.setTextSize(mTextSize);
                    break;
                case R.styleable.subscriptTextview_textColor:
                    mTextColor = typedArray.getColor(index, Color.BLACK);
                    tv.setTextColor(mTextColor);
                    break;
                case R.styleable.subscriptTextview_text:
                    String s = typedArray.getString(index);
                    tv.setText(s);
                    break;
            }
        }
        //Recycles the TypedArray, to be re-used by a later caller
        //官方解释：回收TypedArray 以便后面的使用者重用
        typedArray.recycle();
    }


    public void setText(String s) {
        tv.setText(s);
    }

    public void setVVisible(boolean visible) {
        v.setVisibility(visible ? VISIBLE : GONE);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
