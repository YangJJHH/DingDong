package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    // 버튼, editText, checkbox 위젯 변수 선언
    Button btn_login;
    EditText et_id, et_pass;
    CheckBox chk_remember_id, chk_autologin;

    String userID, userPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 위젯 변수와 위젯 id 연결
        btn_login = (Button) findViewById(R.id.btn_login);
        et_id = (EditText) findViewById(R.id.et_id);
        et_pass = (EditText) findViewById(R.id.et_pass);
        chk_remember_id = (CheckBox) findViewById(R.id.chk_remember_id);
        chk_autologin = (CheckBox) findViewById(R.id.chk_autologin);

        // 아이디 저장, 자동 로그인 구현을 위해 SharedPreferences 변수 선언
        SharedPreferences setting = getSharedPreferences("UserLogin", MODE_PRIVATE);;
        SharedPreferences.Editor editor =  setting.edit();

        boolean rememberid = setting.getBoolean("RememberIDEnabled", false);
        userID = setting.getString("userID", "");

        // 아이디 저장이 체크 되어있다면 아이디를 et_id에 찍어줌
        if(rememberid == true) {
            chk_remember_id.setChecked(true);
            et_id.setText(userID);
        }

        // et_id에는 영문과 숫자만 11자까지 입력되어야함
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
                if(!ps.matcher(source).matches()) {
                    return "";
                }
                return null;
            }
        };
        et_id.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(11)});

        //로그인 버튼 이벤트처리
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 현재 화면에서 입력받은 userID와 userPass 값 변수에 저장
                userID = et_id.getText().toString();
                userPass = et_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            // 로그인에 성공한 경우
                            if(success) {
                                // chk_autologin이 체크 되어있다면 userID, userPass, 체크박스 값 저장
                                if(chk_autologin.isChecked() == true) {
                                    SharedPreferences setting = getSharedPreferences("UserLogin", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = setting.edit();
                                    editor.putString("userID", userID);
                                    editor.putString("userPass", userPass);
                                    editor.putBoolean("AutoLoginEnabled", true);
                                    editor.commit();
                                }
                                // 체크박스가 해제 되어있다면
                                else {
                                    editor.putBoolean("AutoLoginEnabled", false);
                                    editor.clear();
                                    editor.commit();
                                }
                                // chk_remeber_id가 체크 되어있다면, userID와 체크박스 값 저장
                                if(chk_remember_id.isChecked() == true) {
                                    SharedPreferences setting = getSharedPreferences("UserLogin", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = setting.edit();
                                    editor.putString("userID", userID);
                                    editor.putBoolean("RememberIDEnabled", true);
                                    editor.commit();
                                }
                                // 체크박스가 해제 되어있다면
                                else {
                                    editor.putBoolean("RememberIDEnabled", false);
                                    editor.clear();
                                    editor.commit();
                                }

                                Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, HomeActivity.class);
                                // 유저 ID를 인텐트에 넣어서 보냄
                                intent.putExtra("userID", userID);

                                // 이후 로그아웃 후 EditText가 비어있게 함
                                et_id.setText("");
                                et_pass.setText("");
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
                LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);
            }
        });
    }
}