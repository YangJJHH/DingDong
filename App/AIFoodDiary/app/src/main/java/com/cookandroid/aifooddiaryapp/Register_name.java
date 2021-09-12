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

        btn_next = (Button) findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //
                // 입력받은 이름 DB에 저장 해야함
                //
                Intent intent = new Intent(Register_name.this, Register_age.class);
                startActivity(intent);
            }
        });
    }
}