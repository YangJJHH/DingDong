package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

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
        String userID = intent_r.getStringExtra("userID");
        String userPass = intent_r.getStringExtra("userPass");

        // et_name에는 영문과 한글만 10자까지 입력받게 함.
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern ps = Pattern.compile("^[a-zA-Zㄱ-ㅎ가-힣]+$");
                if(!ps.matcher(source).matches()) {
                    return "";
                }
                return null;
            }
        };
        et_name.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(10)});

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // editText에 이름이 입력되어 있어야 다음 화면으로 전환
                if(et_name.length() != 0) {
                    // 입력받은 userName 변수 선언
                    String userName = et_name.getText().toString();

                    // 이전 화면에서 받은 인자와 입력받은 userName을 다음 화면 인자로 넣어주며 전환
                    Intent intent_s = new Intent(Register_name.this, Register_age.class);
                    intent_s.putExtra("userID", userID);
                    intent_s.putExtra("userPass", userPass);
                    intent_s.putExtra("userName", userName);
                    startActivity(intent_s);
                } else {
                    Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}