package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    // 버튼, editText 위젯 변수 선언
    Button btn_login;
    EditText et_id, et_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 위젯 변수와 위젯 id 연결
        btn_login = (Button) findViewById(R.id.btn_login);
        et_id = (EditText) findViewById(R.id.et_id);
        et_pass = (EditText) findViewById(R.id.et_pass);


        //로그인 버튼 이벤트처리
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 현재 화면에서 입력받은 userID와 userPass 값 변수에 저장
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            // 로그인에 성공한 경우
                            if(success) {
                                //String userID = jsonObject.getString("userID");
                                //String userPass = jsonObject.getString("userPassword");
                                String userName = jsonObject.getString("userName");
                                int userHeight = jsonObject.getInt("userHeight");
                                int userWeight = jsonObject.getInt("userWeight");

                                Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, HomeActivity.class);
                                startActivity(intent);

                            }
                            // 로그인에 실패한 경우
                            else {
                                Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
            }
        });
    }
}