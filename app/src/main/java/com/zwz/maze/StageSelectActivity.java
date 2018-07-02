package com.zwz.maze;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StageSelectActivity extends AppCompatActivity {
    private Button smallButton;
    private Button middleButton;
    private Button largeButton;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_select);

        smallButton=findViewById(R.id.small_b);
        middleButton=findViewById(R.id.middle_b);
        largeButton=findViewById(R.id.large_b);
        back=findViewById(R.id.back_b);

        smallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StageSelectActivity.this,GameActivity.class);
                intent.putExtra("size",9);
                startActivity(intent);
            }
        });
        middleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StageSelectActivity.this,GameActivity.class);
                intent.putExtra("size",17);
                startActivity(intent);
            }
        });
        largeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StageSelectActivity.this,GameActivity.class);
                intent.putExtra("size",33);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StageSelectActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
