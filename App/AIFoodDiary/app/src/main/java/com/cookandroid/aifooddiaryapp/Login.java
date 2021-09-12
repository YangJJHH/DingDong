package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {
    //버튼 생성
    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //위젯 연결
        btn_login=(Button)findViewById(R.id.btn_login);



        //로그인 버튼 이벤트처리
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //아이디 패스워드 일치 확인후 화면이동
            }
        });
    }
}