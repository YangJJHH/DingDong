package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

// !!!!!!!!!!인텐트 부분 수정해야함 !!!!!!!!!!!!!!!!!!!

public class Register_sex extends AppCompatActivity {
    ImageButton imgbtn_man, imgbtn_woman;  // 남성, 여성 이미지 버튼 위젯 변수 선언
    Button btn_next;    // 버튼 위젯 변수 선언
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sex);

        // 각 버튼 변수와 위젯 id 연결
        imgbtn_man = (ImageButton) findViewById(R.id.imgbtn_man);
        imgbtn_woman = (ImageButton) findViewById(R.id.imgbtn_woman);
        btn_next = (Button) findViewById(R.id.btn_next);

        // imgbtn_man(남성 버튼)이 눌렸을 때의 이벤트 처리
        imgbtn_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgbtn_man.setImageResource(R.drawable.sex_man_onclick);
                imgbtn_woman.setImageResource(R.drawable.sex_woman);
            }
        });

        // imgbtn_woman(여성 버튼)이 눌렸을 때의 이벤트 처리
        imgbtn_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgbtn_man.setImageResource(R.drawable.sex_man);
                imgbtn_woman.setImageResource(R.drawable.sex_woman_onclick);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_sex.this, Register_body.class);
                startActivity(intent);
            }
        });

    }
}