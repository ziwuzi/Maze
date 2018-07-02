package com.zwz.maze;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HelpActivity extends AppCompatActivity {
    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

    private TextView explain;
    private ImageView imageView;
    private Button back;

    private static int[] helpImage={R.drawable.help1,R.drawable.help2,R.drawable.help3,R.drawable.help4};
    private static String[] helpText={
            "1.这是一个迷宫游戏，你的位置是蓝色点。",
            "2.按照箭头点击周围可达点移动。",
            "3.右下角为终点。",
            "4.到达终点就获胜了，Have fun!"
    };
    private int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        explain=findViewById(R.id.explain);
        imageView=findViewById(R.id.imageView);
        back=findViewById(R.id.back_b);

        explain.setText(helpText[index]);
        imageView.setImageResource(helpImage[index]);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HelpActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            if(y1 - y2 > 30) {//向上划

            } else if(y2 - y1 > 30) {//向下划

            } else if(x1 - x2 > 30) {//向左划
                if(index<3){
                    index++;
                    explain.setText(helpText[index]);
                    imageView.setImageResource(helpImage[index]);
                }
            } else if(x2 - x1 > 30) {//向右划
                if(index>0){
                    index--;
                    explain.setText(helpText[index]);
                    imageView.setImageResource(helpImage[index]);
                }
            }
        }
        return super.onTouchEvent(event);
    }
}
