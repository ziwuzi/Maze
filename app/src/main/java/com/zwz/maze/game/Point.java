package com.zwz.maze.game;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.zwz.maze.R;
import com.zwz.maze.view.MazeViewGroup;

public class Point extends CircularImageView{

    public int[] reachTo;//可达点
    public int x;//x坐标
    public int y;//y坐标
    public int width=50;//宽
    public int height=50;//高

    public final static int UP=0; //上
    public final static int RIGHT=1;//右
    public final static int DOWN=2;//下
    public final static int LEFT=3;//左

    public final static int NOT_SET=0;//未设置
    public final static int ABLE=1;//可达
    public final static int DISABLE=-1;//不可达

    public Point(Context context){
        this(context, null);
    }
    public Point(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Point(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        reachTo = new int[]{NOT_SET, NOT_SET, NOT_SET, NOT_SET};
    }

    //设置图像
    public void setImage(Context context){
        MazeViewGroup.LayoutParams layoutParams=new MazeViewGroup.LayoutParams(width, height);
        layoutParams.left=y*width;
        layoutParams.top=x*height;
        this.setLayoutParams(layoutParams);
        this.setBorderColor(Color.BLACK);
        this.setBorderWidth(5);
        this.setImageResource(R.drawable.none);
        this.setScaleType(ImageView.ScaleType.FIT_XY);
    }
    //初始化可达方向
    public void initReach(int size){
        if(x==0)
            reachTo[UP]=DISABLE;
        if(y==size-1)
            reachTo[RIGHT]=DISABLE;
        if(x==size-1)
            reachTo[DOWN]=DISABLE;
        if(y==0)
            reachTo[LEFT]=-1;
    }
    //判定是否能移动
    public boolean moveAble(){
        if(reachTo[UP]!=DISABLE||reachTo[RIGHT]!=DISABLE||reachTo[DOWN]!=DISABLE||reachTo[LEFT]!=DISABLE)
            return true;
        return false;
    }

}
