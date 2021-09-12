package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// !!!!!!!!!!인텐트 부분 수정해야함 !!!!!!!!!!!!!!!!!!!

public class Register_name extends AppCompatActivity {
    Button btn_next;    // 다음 버튼 위젯 변수 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_name);

        // 다음 버튼 변수와 위젯 id 연결
        btn_next = (Button) findViewById(R.id.btn_next);

        // 다음 버튼이 눌렸을 때의 이벤트 처리
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_name.this, Register_sex.class);
                startActivity(intent);
            }
        });

    }
}