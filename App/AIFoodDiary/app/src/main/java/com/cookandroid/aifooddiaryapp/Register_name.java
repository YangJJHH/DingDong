package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

// !!!!!!!!!!인텐트 부분 수정해야함 !!!!!!!!!!!!!!!!!!!
// intent_s 는 인텐트 보낼 떄 쓰는 변수, intent_r은 인텐트 받을 때 쓸 변수!!!!!!!!!!!!

public class Register_name extends AppCompatActivity {
    Button btn_next;    // 다음 버튼 위젯 변수 선언
    EditText et_name;   // EditText 이름 위젯 변수 선언
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_name);
        
        // 위젯 변수 id와 연결
        btn_next = (Button) findViewById(R.id.btn_next);
        et_name = (EditText) findViewById(R.id.et_name);

        // 이전 화면에서 보낸 인자를 받기 위한 변수 intent_r 선언
        Intent intent_r = getIntent();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이전 화면에서 받은 인자와 입력받은 userName을 다음 화면 인자로 넣어주며 전환
                Intent intent_s = new Intent(Register_name.this, Register_age.class);
                intent_s.putExtra("userID",intent_r.getStringExtra("userID"));
                intent_s.putExtra("userPass",intent_r.getStringExtra("userPass"));
                intent_s.putExtra("userName",et_name.getText());
                startActivity(intent_s);
            }
        });
    }
}