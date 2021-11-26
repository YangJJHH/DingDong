package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

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

        // 시작 페이드인 애니메이션 적용
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        ConstraintLayout layout = findViewById(R.id.layout);
        layout.startAnimation(animation);

        // 만약 데이터에 자동 로그인이 체크 되어있다면 바로 홈 화면으로 이동하게끔 함
        SharedPreferences setting = getSharedPreferences("UserLogin", MODE_PRIVATE);

        boolean autologin = setting.getBoolean("AutoLoginEnabled", false);

        // 자동 로그인이 체크 되어있다면 자동로그인 함.
        if(autologin == true) {
            Toast.makeText(getApplicationContext(),"자동 로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FirstActivity.this, HomeActivity.class);
            // 유저 ID를 인텐트에 넣어서 보냄
            intent.putExtra("userID", setting.getString("userID",""));
            startActivity(intent);
        }

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