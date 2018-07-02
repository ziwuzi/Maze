package com.zwz.maze.game;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;

import com.zwz.maze.R;
import com.zwz.maze.view.MazeViewGroup;

import java.util.ArrayList;
import java.util.Comparator;

public class Maze {
    private MazeViewGroup viewGroup;//图像集
    private ArrayList<Point> pointList=new ArrayList<>();//点集
    private ArrayList<Arrow> arrowList=new ArrayList<>();//箭头集

    private int size=17;//大小
    private Player player;//玩家位置
    private ArrayList<Point> theWayOut=null;//迷宫路径

    private Context context;//activity引用

    public Maze(Context context, MazeViewGroup viewGroup){
        this.viewGroup=viewGroup;
        this.context=context;
        this.size=viewGroup.getHorizontalSize();
        initMaze();
    }

    //初始化迷宫
    private void initMaze(){
        int height=viewGroup.getChildHeight();
        int width=viewGroup.getChildWidth();
        for(int i=0;i<size*size;i++){
            int x=i/size;
            int y=i%size;
            if(x%2==0&&y%2==0) {
                Point point = new Point(context);
                point.x = x;
                point.y = y;
                point.height=height;
                point.width=width;
                point.setImage(context);
                point.initReach(size);

                pointList.add(point);
                viewGroup.addView(point);
            }
            else{
                Arrow arrow=new Arrow();
                arrow.x=x;
                arrow.y=y;
                arrow.height=height;
                arrow.width=width;
                arrow.setImage(Arrow.NONE,context);
                arrowList.add(arrow);

                viewGroup.addView(arrow.arrowImage);
            }
        }
    }
    //开始
    public void start(){
        reset();
        player=new Player(findPoint(0,0));
        createMaze();
        setAction();
    }
    //查找点
    public Point findPoint(int x,int y){
        int index=x/2*(size+1)/2+y/2;
        return pointList.get(index);
    }
    //查找方向
    public Arrow findArrow(int x,int y){
        int pN=0;
        if(x%2==0)
            pN=(x+1)/2*(size+1)/2+(y+1)/2;
        else
            pN=(x+1)/2*(size+1)/2;
        int index=x*size+y-pN;
        return arrowList.get(index);
    }
    //生成迷宫
    public void createMaze(){
        Point point= findPoint(0,0);
        //生成从入口到出口的路径
        while(!(point.x==size-1&&point.y==size-1)) {
            initPoint();
            point= findPoint(0,0);
            ArrayList<Point> points = new ArrayList<>();
            while (!points.contains(point)) {
                points.add(point);
                point = randomMove(point);
                if(point.x==size-1&&point.y==size-1) {
                    points.add(point);
                    theWayOut=points;
                    break;
                }
            }
        }
        //生成分支路径
        setSortList();
        for(int i=sortList.size()-1;i>=0;i--){
            point = sortList.get(i);
            if (point.moveAble()) {
                ArrayList<Point> points = new ArrayList<>();
                while (!points.contains(point)) {
                    points.add(point);
                    point = randomMove(point);
                }
            }
        }
        drawArrow();
        //player.point=findPoint(0,0);
    }
    //随机移动
    public Point randomMove(Point point){
        if(point.moveAble()){
            int t=0,moveTo=0;
            for(int i=0;i<4;i++){
                if(point.reachTo[i]!=Point.DISABLE)
                    t++;
            }
            switch (t){
                case 1:
                    moveTo=1;
                    break;
                default:
                    moveTo= (int)(Math.random()*t)+1;
                    break;
            }
            t=0;
            for(int i=0;i<4;i++){
                if(point.reachTo[i]!=Point.DISABLE){
                    t++;
                    if(t==moveTo) {
                        point.reachTo[i]=Point.ABLE;
                        switch (i){
                            case 0:
                                point=findPoint(point.x-2,point.y);
                                point.reachTo[Point.DOWN]=Point.DISABLE;
                                break;
                            case 1:
                                point=findPoint(point.x,point.y+2);
                                point.reachTo[Point.LEFT]=Point.DISABLE;
                                break;
                            case 2:
                                point=findPoint(point.x+2,point.y);
                                point.reachTo[Point.UP]=Point.DISABLE;
                                break;
                            case 3:
                                point=findPoint(point.x,point.y-2);
                                point.reachTo[Point.RIGHT]=Point.DISABLE;
                                break;
                        }
                    }
                }
            }
        }
        return point;
    }
    //绘制箭头
    public void drawArrow(){
        Point point;
        for(int i=0;i<pointList.size();i++){
            point=pointList.get(i);
            for(int j=0;j<4;j++){
                if(point.reachTo[j]==Point.ABLE){
                    Arrow arrow=null;
                    switch (j){
                        case 0:
                            arrow=findArrow(point.x-1,point.y);
                            break;
                        case 1:
                            arrow=findArrow(point.x,point.y+1);
                            break;
                        case 2:
                            arrow=findArrow(point.x+1,point.y);
                            break;
                        case 3:
                            arrow=findArrow(point.x,point.y-1);
                            break;
                    }
                    if(arrow!=null)
                        arrow.setImage(j,context);
                }
            }
        }
    }
    //初始化点
    private void initPoint(){
        Point point=null;
        for(int i=0;i<pointList.size();i++){
            point=pointList.get(i);
            point.reachTo=new int[]{0,0,0,0};
            point.initReach(size);
        }
    }
    //设置点击事件
    public void setAction(){
        for(int i=0;i<pointList.size();i++){
            pointList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player.move((Point) v);
                }
            });
        }
    }
    //显示迷宫路径
    public void showWay(){
        Point point;
        for(int i=0;i<theWayOut.size();i++){
            point=theWayOut.get(i);
            point.setImageResource(R.drawable.red);
        }
    }
    //重置迷宫
    public void reset(){
        Point point;
        for(int i=0;i<pointList.size();i++){
            point=pointList.get(i);
            point.setImageResource(R.drawable.none);
            point.reachTo=new int[]{0,0,0,0};
            point.initReach(size);
        }
    }
    //是否赢了
    public boolean win(){
        if(player.point.x==size-1&&player.point.y==size-1)
            return true;
        else
            return false;
    }

    public int ti=0;

    public void test(){
        System.out.println("点击了");
        if(ti<sortList.size()) {
            Point point;
            point = sortList.get(ti);
            point.setImageResource(R.drawable.red);
            if (point.moveAble()) {
                ArrayList<Point> points = new ArrayList<>();
                while (!points.contains(point)) {
                    points.add(point);
                    point = randomMove(point);
                }
            }
            drawArrow();
            ti++;
        }
    }

    private ArrayList<Point> sortList;
    @TargetApi(Build.VERSION_CODES.N)
    public void setSortList(){
        sortList=new ArrayList<>();
        for(int i=0;i<pointList.size();i++)
            sortList.add(pointList.get(i));
        sortList.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if(o1.x+o1.y<o2.x+o2.y){
                    return 1;
                }
                else
                    return -1;
            }
        });
    }
}
