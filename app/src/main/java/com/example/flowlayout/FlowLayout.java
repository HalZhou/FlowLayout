package com.example.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {

    //上下左右padding
    private int PADDING = dp2px(15);
    //子View的间隔
    private final int ITEM_INVTERVAL = dp2px(10);
    //行间隔；
    private final int LINE_INTERVAL = dp2px(20);

    //记录每一行的最大高度
    private List<Integer> maxHeights = new ArrayList<>();

    //记录每一行的View集合
    private List<List<View>> lines = new ArrayList<>();

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        PADDING = getPaddingTop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        lines.clear();
        maxHeights.clear();

        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxWidth = parentWidth - PADDING * 2;
        //当前行的所占的宽度
        int currWidth = 0;
        //当前行的index
        int currLine = 0;
        //当前行最大的高度
        int maxHeight = 0;
        //是否换行
        boolean isTurn = false;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            //测量子View的大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            int temp = currWidth + PADDING + childWidth;
            if (temp > maxWidth) {
                //说明当前行放不下当前子View
                //判断是否是第一个子View
                if (i == 0){
                    currWidth = childWidth;
                }else {
                    //换行
                    currWidth = childWidth;
                    currLine++;
                    isTurn = true;
                }

                //新增一行
                addLine(childView);
                maxHeight = childHeight;
            } else {
                if (i == 0) {
                    //新增一行
                    addLine(childView);

                    maxHeight = childHeight;

                    currWidth = childWidth;
                } else {
                    //当前行增加子View
                    addCurrentLineChildView(childView, lines.get(currLine));

                    maxHeight = Math.max(childHeight, maxHeight);

                    currWidth = temp;
                }

                isTurn = false;
            }

            if (maxHeights.size() == 0 || isTurn) {
                maxHeights.add(maxHeight);
            } else {
                maxHeights.set(currLine, maxHeight);
            }
        }

        int parentHeight = PADDING * 2;
        for (int i = 0; i < maxHeights.size(); i++) {
            parentHeight = parentHeight + maxHeights.get(i) + LINE_INTERVAL;
        }


        setMeasuredDimension(parentWidth, parentHeight);
    }

    private void addCurrentLineChildView(View childView, List<View> views) {
        views.add(childView);
    }

    private void addLine(View childView) {
        List<View> line = new ArrayList<>();
        line.add(childView);
        lines.add(line);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int left = PADDING;
        int top = PADDING;
        for (int i = 0; i < lines.size(); i++) {
            List<View> line = lines.get(i);
            for (int j = 0; j < line.size(); j++) {
                View childView = line.get(j);

                int childMeasureWidth = childView.getMeasuredWidth();
                childView.layout(left, top, left + childMeasureWidth, top + childView.getMeasuredHeight());

                left = left + childMeasureWidth + ITEM_INVTERVAL;
            }
            left = PADDING;
            top = top + maxHeights.get(i) + LINE_INTERVAL;
        }
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
}
