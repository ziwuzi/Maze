package com.zwz.maze.game;

import android.graphics.Color;

import com.zwz.maze.R;

public class Player{
    public Point point;//玩家位置

    public Player(Point point){
        this.point=point;
        point.setImageResource(R.drawable.blue);
    }
    //移动
    public void move(Point newPoint){
        Point oldPoint=point;
        if(newPoint.x==point.x){
            if(newPoint.y==point.y+2&&point.reachTo[Point.RIGHT]==1){
                point=newPoint;
            }
            if(newPoint.y==point.y-2&&point.reachTo[Point.LEFT]==1){
                point=newPoint;
            }
        }
        if(newPoint.y==point.y){
            if(newPoint.x==point.x+2&&point.reachTo[Point.DOWN]==1){
                point=newPoint;
            }
            if(newPoint.x==point.x-2&&point.reachTo[Point.UP]==1){
                point=newPoint;
            }
        }
        oldPoint.setImageResource(R.drawable.none);
        point.setImageResource(R.drawable.blue);
    }
}
