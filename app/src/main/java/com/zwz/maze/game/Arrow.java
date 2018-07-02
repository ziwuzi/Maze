package com.zwz.maze.game;

import android.content.Context;
import android.widget.ImageView;

import com.zwz.maze.R;
import com.zwz.maze.view.MazeViewGroup;

/*
    箭头
 */
public class Arrow {
    public ImageView arrowImage;//图像
    public int direction=NONE;//方向
    public int x;//x坐标
    public int y;//y坐标
    public int width;//宽
    public int height;//高

    public final static int UP=0; //上
    public final static int RIGHT=1;//右
    public final static int DOWN =2;//下
    public final static int LEFT=3;//左
    public final static int NONE=-1;//无方向

    public Arrow(){

    }

    public Arrow(int direction,int x,int y){
        this.direction=direction;
        this.x=x;
        this.y=y;
    }

    public void setImage(int direction,Context context){
        if(arrowImage==null){
            arrowImage=new ImageView(context);
            MazeViewGroup.LayoutParams layoutParams=new MazeViewGroup.LayoutParams(width, height);
            layoutParams.left=y*width;
            layoutParams.top=x*height;
            arrowImage.setLayoutParams(layoutParams);
            arrowImage.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        switch (direction){
            default:
                arrowImage.setImageResource(R.drawable.none);
                break;
            case UP:
                arrowImage.setImageResource(R.drawable.up);
                break;
            case RIGHT:
                arrowImage.setImageResource(R.drawable.right);
                break;
            case DOWN:
                arrowImage.setImageResource(R.drawable.down);
                break;
            case LEFT:
                arrowImage.setImageResource(R.drawable.left);
                break;
        }
    }
}
