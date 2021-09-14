package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

// intent_s 는 인텐트 보낼 떄 쓰는 변수, intent_r은 인텐트 받을 때 쓸 변수!!!!!!!!!!!!

public class Register_age extends AppCompatActivity {
    // 버튼 생성
    Button btn_next;
    EditText et_age;
    TextView tv_info;
    int age; //사용자 나이

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_age);

        //위젯연결
        btn_next=(Button)findViewById(R.id.btn_next);
        et_age = (EditText) findViewById(R.id.et_age);
        tv_info = (TextView) findViewById(R.id.tv_info);

        // 이전 화면에서 보낸 인자를 받기 위한 변수 intent_r 선언
        Intent intent_r = getIntent();
        String userID = intent_r.getStringExtra("userID");
        String userPass = intent_r.getStringExtra("userPass");
        String userName = intent_r.getStringExtra("userName");

        //다음 버튼 이벤트 처리
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 입력받은 userAge에 대한 변수 선언
                int userAge = Integer.parseInt(et_age.getText().toString());

                // 이전 화면에서 받은 인자와 입력받은 userAge를 다음 화면 인자로 넣어주며 전환
                Intent intent_s = new Intent(Register_age.this, Register_sex.class);
                intent_s.putExtra("userID", userID);
                intent_s.putExtra("userPass", userPass);
                intent_s.putExtra("userName",userName);
                intent_s.putExtra("userAge", userAge);

                startActivity(intent_s);
            }
        });
    }
}