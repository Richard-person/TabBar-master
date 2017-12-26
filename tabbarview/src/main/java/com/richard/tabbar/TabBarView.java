package com.richard.tabbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <br>Description : 标签栏
 * <br>Author : Richard
 * <br>Date : 2017/12/26 12:17
 * <br>Changelog:
 *   <br>Version            Date            Author              Detail
 *  <br> ----------------------------------------------------------------------
 *  <br>1.0        2017/12/26 12:17       Administrator      new file.
 */
public class TabBarView extends RadioGroup {

    private OnTabBarCheckedChangeListener mOnTabBarCheckedChangeListener;

    private List<String> datas = new ArrayList<>();

    //字体大小
    private float itemTextSize;
    //未选中字体颜色
    private int unCheckTextColor;
    //选中字体颜色
    private int checkedTextColor;
    //圆角弧度
    private float radius;
    //RadioGroup 背景颜色
    private int content_back_color;
    //item未选中背景颜色
    private int itemUnCheckBackColor;
    //item选中背景颜色
    private int itemCheckedBackColor;
    //间隔视图的宽度
    private int blankViewWidth;
    //间隔视图的背景颜色
    private int blankViewColor;
    //对radiuButton是否对总宽度均分占位
    private boolean isAverage;
    //radiuButton的左内边距和右内边距
    private int paddingLeftRight;
    //默认选中的tabbar item 下标位置
    private int defaultCheckedItemPosition;


    public TabBarView(Context context) {
        super(context);
        initView(null);
    }

    public TabBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }


    private void initView(AttributeSet attrs) {
        this.setOrientation(HORIZONTAL);

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TabBarView);

            itemTextSize = typedArray.getDimension(R.styleable.TabBarView_tbv_text_size, this.sp2px(getContext(), 14));
            unCheckTextColor = typedArray.getColor(R.styleable.TabBarView_tbv_text_uncheck_color, Color.parseColor("#9accbd"));
            checkedTextColor = typedArray.getColor(R.styleable.TabBarView_tbv_text_checked_color, Color.parseColor("#0d6fb7"));
            radius = typedArray.getDimension(R.styleable.TabBarView_tbv_radius, this.dp2px(getContext(), 4));
            content_back_color = typedArray.getColor(R.styleable.TabBarView_tbv_content_back_color, Color.parseColor("#2e000000"));
            itemUnCheckBackColor = typedArray.getColor(R.styleable.TabBarView_tbv_item_uncheck_back_color, Color.parseColor("#00000000"));
            itemCheckedBackColor = typedArray.getColor(R.styleable.TabBarView_tbv_item_checked_back_color, Color.parseColor("#ffffff"));
            blankViewWidth = (int) typedArray.getDimension(R.styleable.TabBarView_tbv_blank_view_width, this.dp2px(getContext(), 0.4f));
            blankViewColor = typedArray.getColor(R.styleable.TabBarView_tbv_blank_view_color, Color.parseColor("#9accbd"));
            isAverage = typedArray.getBoolean(R.styleable.TabBarView_tbv_width_isAverage, false);
            paddingLeftRight = (int) typedArray.getDimension(R.styleable.TabBarView_tbv_padding_left_right, this.dp2px(getContext(), 18));
            defaultCheckedItemPosition = typedArray.getInteger(R.styleable.TabBarView_tbv_default_checked_item_position, 0);

            String texts = typedArray.getString(R.styleable.TabBarView_tbv_texts);
            if (!TextUtils.isEmpty(texts)) {
                this.setData(Arrays.asList(texts.split("\\|")));
            }

            typedArray.recycle();
            typedArray = null;
        } else {
            itemTextSize = this.sp2px(getContext(), 14);
            unCheckTextColor = Color.parseColor("#9accbd");
            checkedTextColor = Color.parseColor("#0d6fb7");
            radius = this.dp2px(getContext(), 4);
            content_back_color = Color.parseColor("#2e000000");
            itemUnCheckBackColor = Color.parseColor("#00000000");
            itemCheckedBackColor = Color.parseColor("#ffffff");
            blankViewWidth = this.dp2px(getContext(), 0.4f);
            blankViewColor = Color.parseColor("#9accbd");
            isAverage = false;
            paddingLeftRight = this.dp2px(getContext(), 18);
            defaultCheckedItemPosition = 0;
        }

        this.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mOnTabBarCheckedChangeListener != null) {
                    RadioButton radioButton = findViewById(checkedId);
                    mOnTabBarCheckedChangeListener.checked(radioButton.getText().toString(), (Integer) radioButton.getTag());
                }
            }
        });


        //初始化默认选中位置
        if(null != this.datas && this.datas.size() > defaultCheckedItemPosition){
            this.checkItem(defaultCheckedItemPosition);
        }
    }

    /**
     * 设置数据
     */
    public void setData(List<String> datas) {
        this.datas.clear();
        this.removeAllViews();
        this.datas.addAll(datas);
        this.setBackgroundDrawable(generatorGradientDrawable(content_back_color, radius));

        int size = this.datas.size();
        for (int i = 0; i < size; i++) {
            String text = this.datas.get(i);
            this.addView(generatorRadioButton(text, i, isAverage, paddingLeftRight));

            if (i < size - 1 && blankViewWidth > 0) {
                this.addView(generatorBlankView(blankViewColor, blankViewWidth));
            }
        }
    }


    /**
     * 设置监听事件
     */
    public void setOnTabBarCheckedChangeListener(OnTabBarCheckedChangeListener onTabBarCheckedChangeListener) {
        mOnTabBarCheckedChangeListener = onTabBarCheckedChangeListener;
    }


    /**
     * 选中下标为position的radioButton
     */
    public void checkItem(int position) {
        if (this.getChildCount() > 0) {
            ((RadioButton) (findViewWithTag(position))).setChecked(true);
        }
    }


    /**
     * 获取已选中的RadiuButton位置
     */
    public int getCheckedItemPosition() {
        Object tag = findViewById(getCheckedRadioButtonId()).getTag();
        return tag == null ? -1 : (int) tag;
    }


    /**
     * 生成RadioButton
     *
     * @param text
     * @return
     */
    private RadioButton generatorRadioButton(String text, Object tag, boolean isAverage, int paddingLeftRight) {
        RadioButton radioButton = new RadioButton(getContext());

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            try {
                Field field = radioButton.getClass().getSuperclass().getDeclaredField("mButtonDrawable");
                field.setAccessible(true);
                field.set(radioButton, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            radioButton.setButtonDrawable(null);
        }

        radioButton.setPadding(
                paddingLeftRight
                , radioButton.getTop()
                , paddingLeftRight
                , radioButton.getBottom()
        );

        LayoutParams rlp;
        if (isAverage) {
            rlp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            rlp.weight = 1;
        } else {
            rlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        radioButton.setLayoutParams(rlp);

        radioButton.setTag(tag);
        radioButton.setGravity(Gravity.CENTER);
        radioButton.setText(text);
        radioButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemTextSize);
        radioButton.setTextColor(generatorTextColorDrawable(unCheckTextColor, checkedTextColor));
        radioButton.setBackgroundDrawable(generatorItemSelector(itemUnCheckBackColor, itemCheckedBackColor, radius));

        return radioButton;
    }


    /**
     * 生成竖线
     */
    private View generatorBlankView(int backColor, int width) {
        View blankView = new View(getContext());

        LayoutParams rlp = new LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
        rlp.topMargin = this.dp2px(getContext(), 5);
        rlp.bottomMargin = this.dp2px(getContext(), 5);
        blankView.setLayoutParams(rlp);

        blankView.setBackgroundColor(backColor);

        return blankView;
    }


    /**
     * 生成未选中和选中后的状态的Drawable
     *
     * @param unCheckColor 未选中背景颜色
     * @param checkedColor 未选中背景颜色
     * @param radius       圆角弧度
     */
    private StateListDrawable generatorItemSelector(int unCheckColor, int checkedColor, float radius) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{-android.R.attr.state_checked}, generatorGradientDrawable(unCheckColor, radius));
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, generatorGradientDrawable(checkedColor, radius));

        return stateListDrawable;
    }


    /**
     * 生成单个Drawable
     *
     * @param color  背景颜色
     * @param radius 圆角弧度
     */
    private GradientDrawable generatorGradientDrawable(int color, float radius) {
        //1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
        //topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius
        float[] radii = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadii(radii);
        gradientDrawable.setColor(color);

        return gradientDrawable;
    }


    /**
     * 生成字体颜色stateList
     *
     * @param unCheckTextColor 未选中颜色
     * @param checkedTextColor 选中的颜色
     * @return
     */
    private ColorStateList generatorTextColorDrawable(int unCheckTextColor, int checkedTextColor) {
        int[][] states = new int[2][];
        states[0] = new int[]{-android.R.attr.state_checked};
        states[1] = new int[]{android.R.attr.state_checked};

        int[] colors = new int[]{unCheckTextColor, checkedTextColor};

        return new ColorStateList(states, colors);
    }


    public interface OnTabBarCheckedChangeListener {
        void checked(String itemText, int position);
    }

    /**
     * sp转px
     *
     * @param context
     * @param spVal
     * @return
     */
    private int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    private int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }
}
