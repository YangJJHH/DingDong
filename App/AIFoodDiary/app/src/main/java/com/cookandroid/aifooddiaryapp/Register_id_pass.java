package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Register_id_pass extends AppCompatActivity {
    // 버튼 생성
    Button btn_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_id_pass);

        //위젯 연결
        btn_next=(Button)findViewById(R.id.btn_next);


        //다음 버튼 이벤트 처리

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                //입력받은 아이디 비밀번호 DB에 저장하는 과정//
                //

                //다음화면으로 전환
                Intent intent = new Intent(Register_id_pass.this, Register_name.class);
                startActivity(intent);
            }
        });
    }
}