package com.zwz.maze;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zwz.maze.game.Maze;
import com.zwz.maze.util.TimeUtil;
import com.zwz.maze.view.MazeViewGroup;

public class GameActivity extends AppCompatActivity {
    private MazeViewGroup group;//迷宫ViewGroup
    //控件
    private TextView timer;
    private TextView giveUp;
    private TextView stop;
    //菜单控件
    private TextView menuButton1;
    private TextView menuButton2;
    private TextView menuButton3;
    private TextView menuTitle;
    private TextView score;
    private ConstraintLayout menu;

    private Maze maze;//迷宫控制

    //结束等待
    private Runnable giveUpRunnable = new Runnable() {
        @Override
        public void run() {
            menu.setVisibility(View.VISIBLE);
        }
    };
    private Handler mHandle;//主线程handle
    private boolean isPause = false;//是否暂停
    private long currentSecond = 0;//当前毫秒数

    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        group = findViewById(R.id.viewGroup);
        giveUp=findViewById(R.id.give_up_b);
        timer=findViewById(R.id.timer);
        stop=findViewById(R.id.stop_b);

        menuButton1=findViewById(R.id.menu_b1);
        menuButton2=findViewById(R.id.menu_b2);
        menuButton3=findViewById(R.id.menu_b3);
        menuTitle=findViewById(R.id.menu_title);
        score=findViewById(R.id.score);
        menu=findViewById(R.id.menu);

        mHandle = new Handler();

        Intent intent = getIntent();
        int size = intent.getIntExtra("size",9);
        group.setSize(size,size);

        maze=new Maze(context,group);
        maze.start();
        maze.setSortList();
        menu.setVisibility(View.GONE);

        mHandle.post(timeRunnable);

        giveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maze.showWay();
                isPause=true;
                menuTitle.setText("继续努力！");
                menuButton1.setText("再次挑战");
                score.setVisibility(View.INVISIBLE);
                menuButton1.setVisibility(View.VISIBLE);
                menuButton2.setVisibility(View.INVISIBLE);
                menuButton3.setVisibility(View.VISIBLE);
                menuButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        maze.start();
                        menu.setVisibility(View.GONE);
                        currentSecond=0;
                        isPause=false;

                        mHandle.post(timeRunnable);
                    }
                });
                menuButton3.setText("返回");
                menuButton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(GameActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });

                mHandle.postDelayed(giveUpRunnable,3000);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stop();
                maze.test();
            }
        });


    }

    //暂停
    private void stop() {
        isPause=true;
        menuTitle.setText("暂停");
        score.setVisibility(View.INVISIBLE);
        menuButton1.setVisibility(View.INVISIBLE);
        menuButton2.setVisibility(View.VISIBLE);
        menuButton3.setVisibility(View.INVISIBLE);
        menuButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setVisibility(View.GONE);
                isPause=false;

                mHandle.post(timeRunnable);
            }
        });
        menu.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!maze.win())
            stop();
    }

    //计时器
    private Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {

            currentSecond = currentSecond + 1000;
            timer.setText(TimeUtil.getFormatHMS(currentSecond));
            if(maze.win()){
                isPause=true;
                menuTitle.setText("你赢了！");
                menuButton1.setText("再次挑战");

                score.setVisibility(View.VISIBLE);
                menuButton1.setVisibility(View.VISIBLE);
                menuButton2.setVisibility(View.INVISIBLE);
                menuButton3.setVisibility(View.VISIBLE);

                menuButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        maze.start();
                        menu.setVisibility(View.GONE);
                        currentSecond=0;
                        isPause=false;

                        mHandle.post(timeRunnable);
                    }
                });
                menuButton3.setText("返回");
                menuButton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(GameActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });

                score.setText("你的成绩："+TimeUtil.getFormatHMS(currentSecond));
                menu.setVisibility(View.VISIBLE);
            }
            if (!isPause) {
                //递归调用本runnable对象，实现每隔一秒一次执行任务
                mHandle.postDelayed(this, 1000);
            }
        }
    };

}
