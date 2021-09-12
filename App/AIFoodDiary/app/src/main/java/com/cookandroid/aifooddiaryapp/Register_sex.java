package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

// !!!!!!!!!!인텐트 부분 수정해야함 !!!!!!!!!!!!!!!!!!!

public class Register_sex extends AppCompatActivity {
    ImageButton imgbtn_man, imgbtn_woman;  // 남성, 여성 이미지 버튼 위젯 변수 선언
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sex);

        // 각 이미지버튼 변수와 위젯 id 연결
        imgbtn_man = (ImageButton) findViewById(R.id.imgbtn_man);
        imgbtn_woman = (ImageButton) findViewById(R.id.imgbtn_woman);

        // imgbtn_man(남성 버튼)이 눌렸을 때의 이벤트 처리
        imgbtn_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_sex.this, Register_body.class);
                startActivity(intent);
            }
        });

        // imgbtn_woman(여성 버튼)이 눌렸을 때의 이벤트 처리
        imgbtn_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_sex.this, Register_body.class);
                startActivity(intent);
            }
        });

    }
}