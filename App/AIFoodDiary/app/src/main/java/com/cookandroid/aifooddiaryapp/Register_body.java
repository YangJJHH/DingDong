package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Register_body extends AppCompatActivity {
    // editText와 button 위젯 변수 선언
    EditText et_height, et_weight;
    Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_body);

        // 위젯 변수와 위젯 ID 연결
        et_height = (EditText) findViewById(R.id.et_height);
        et_weight = (EditText) findViewById(R.id.et_weight);
        btn_register = (Button) findViewById(R.id.btn_register);

        // 이전 화면에서 보낸 인자를 받기 위한 변수 intent_r 선언
        Intent intent_r = getIntent();

        // 데이터들 데이터베이스에 넣어주는 과정 작성해야함.
        String userID = intent_r.getStringExtra("userID");
        String userPass = intent_r.getStringExtra("userPass");
        String userName = intent_r.getStringExtra("userName");
        int userAge = intent_r.getIntExtra("userAge",0);
        String userSex = intent_r.getStringExtra("userSex");

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 화면에서 받은 키와 몸무게를 저장할 변수 저장
                double userHeight = Double.parseDouble(et_height.getText().toString());
                double userWeight = Double.parseDouble(et_weight.getText().toString());

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            //회원 등록에 성공한 경우
                            if(success) {
                                Toast.makeText(getApplicationContext(),"회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Register_body.this, Login.class);
                                startActivity(intent);
                            } else {
                                //회원 등록에 실패한 경우
                                Toast.makeText(getApplicationContext(),"회원 가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // 서버로 Volley를 이용해서 요청을 함.
                RegisterRequest registerRequest = new RegisterRequest(userID, userPass, userName, userAge, userSex, userHeight, userWeight, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Register_body.this);
                queue.add(registerRequest);
            }
        });
    }
}