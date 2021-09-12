package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Register_age extends AppCompatActivity {
    // 버튼 생성
    Button btn_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_age);

        //위젯연결
        btn_next=(Button)findViewById(R.id.btn_next);

        //다음 버튼 이벤트 처리
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_age.this,Register_sex.class);
                startActivity(intent);
            }
        });
    }
}