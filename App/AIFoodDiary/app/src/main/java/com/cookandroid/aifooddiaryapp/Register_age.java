package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

// intent_s 는 인텐트 보낼 떄 쓰는 변수, intent_r은 인텐트 받을 때 쓸 변수!!!!!!!!!!!!

public class Register_age extends AppCompatActivity {
    // 버튼 생성
    Button btn_next;
    DatePicker spn_age;
    TextView tv_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_age);

        //위젯연결
        btn_next=(Button)findViewById(R.id.btn_next);
        spn_age = (DatePicker) findViewById(R.id.spn_age);
        tv_info = (TextView) findViewById(R.id.tv_info);

        // 이전 화면에서 보낸 인자를 받기 위한 변수 intent_r 선언
        Intent intent_r = getIntent();

        //
        // 데이트피커(스피너)에서 출생년도 받는거 어케 하는지 모르겠음.
        //


        //다음 버튼 이벤트 처리
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 이전 화면에서 받은 인자와 입력받은 userBirth를 다음 화면 인자로 넣어주며 전환
                Intent intent_s = new Intent(Register_age.this, Register_sex.class);
                intent_s.putExtra("userID",intent_r.getStringExtra("userID"));
                intent_s.putExtra("userPass",intent_r.getStringExtra("userPass"));
                intent_s.putExtra("userName",intent_r.getStringExtra("userName"));
                intent_s.putExtra("userBirth", "");     //여기 채워야함 데이트피커에서 year값 받고 나이 계산해서 넘겨주셈

                startActivity(intent_s);
            }
        });
    }
}