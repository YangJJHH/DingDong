package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class Register_body extends AppCompatActivity {
    EditText et_height, et_weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_body);

        et_height = (EditText) findViewById(R.id.et_height);
        et_weight = (EditText) findViewById(R.id.et_weight);

        // 이전 화면에서 보낸 인자를 받기 위한 변수 intent_r 선언
        Intent intent_r = getIntent();

        // 이전 화면들에서 받은 userID, userPass, userName, userBirth, userSex와 해당 화면에서 받은 et_height, et_weight 데이터들 데이터베이스에 넣어주는 과정 작성해야함.

    }
}