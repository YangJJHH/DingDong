package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

///////// sns 로그인 버튼 이벤트 처리 안됨 ////////////////

public class FirstActivity extends AppCompatActivity {
    //버튼 변수 생성
    Button btn_login, btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //위젯 연결
        btn_login=(Button)findViewById(R.id.btn_Login);
        btn_register=(Button)findViewById(R.id.btn_register);

        //로그인 버튼 이벤트 처리
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, Login.class);
                startActivity(intent);
            }
        });

        //회원가입 버튼 이벤트 처리
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, Register_id_pass.class);
                startActivity(intent);
            }
        });
    }
}