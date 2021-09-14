package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

// intent_s 는 인텐트 보낼 떄 쓰는 변수, intent_r은 인텐트 받을 때 쓸 변수!!!!!!!!!!!!.

public class Register_id_pass extends AppCompatActivity {
    // 버튼 생성
    Button btn_next;
    EditText et_id, et_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_id_pass);

        //위젯 연결
        btn_next=(Button)findViewById(R.id.btn_next);
        et_id = (EditText) findViewById(R.id.et_id);
        et_pass = (EditText) findViewById(R.id.et_pass);

        //다음 버튼 이벤트 처리
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 입력받은 userID와 userPass에 대해 변수 선언
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

                // 입력받은 userID와 userPass를 다음 화면 인자로 넣어주며 전환
                Intent intent_s = new Intent(Register_id_pass.this, Register_name.class);
                intent_s.putExtra("userID", userID);
                intent_s.putExtra("userPass", userPass);
                startActivity(intent_s);
            }
        });
    }
}