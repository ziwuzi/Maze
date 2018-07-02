package com.zwz.maze.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class MazeViewGroup extends ViewGroup{
    private int groupWidth;//宽
    private int groupHeight;//高
    private int horizontalSize=17;//水平子view数
    private int verticalSize=17;//垂直子view数
    private int childWidth;//子view宽
    private int childHeight;//子view高

    public int getHorizontalSize(){
        return this.horizontalSize;
    }

    public int getVerticalSize(){
        return this.verticalSize;
    }

    public int getChildWidth() {
        return childWidth;
    }

    public int getChildHeight() {
        return childHeight;
    }

    //第一个构造方法
    public MazeViewGroup(Context context) {
        this(context, null);
    }
    //第二个构造方法
    public MazeViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    //第三个构造方法
    public MazeViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);

    }
    //第四个构造方法
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MazeViewGroup(Context context, AttributeSet attrs,
                         int defStyleAttr, int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes);

    }

    //设置子view大小
    public void setSize(int h,int v){
        this.horizontalSize=h;
        this.verticalSize=v;
        if(h==9){
            childHeight=100;
            childWidth=100;
        }
        if(h==17){
            childHeight=50;
            childWidth=50;
        }
        if(h==33){
            childHeight=30;
            childWidth=30;
        }
        if(h==5){
            childHeight=160;
            childWidth=160;
        }
    }
    //重写子view布局
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = this.getChildCount();
        int marginLeft=(groupWidth-childWidth*horizontalSize)/2;
        int marginTop=(groupHeight-childHeight*verticalSize)/2;
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            LayoutParams lParams = (LayoutParams) child.getLayoutParams();
            child.layout(lParams.left + marginLeft, lParams.top + marginTop, lParams.left + marginLeft + childWidth,
                    lParams.top + marginTop + childHeight);
        }
    }
    //重写宽高计算
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        groupWidth=MeasureSpec.getSize(widthMeasureSpec);
        groupHeight=MeasureSpec.getSize(heightMeasureSpec);
    }
    //自定义子view布局参数
    public static class LayoutParams extends ViewGroup.LayoutParams {

        public int left = 0;
        public int top = 0;

        public LayoutParams(Context arg0, AttributeSet arg1) {
            super(arg0, arg1);
        }

        public LayoutParams(int arg0, int arg1) {
            super(arg0, arg1);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams arg0) {
            super(arg0);
        }

    }

    @Override
    public android.view.ViewGroup.LayoutParams generateLayoutParams(
            AttributeSet attrs) {
        return new MazeViewGroup.LayoutParams(getContext(), attrs);
    }

    @Override
    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(
            android.view.ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return p instanceof MazeViewGroup.LayoutParams;
    }
}
