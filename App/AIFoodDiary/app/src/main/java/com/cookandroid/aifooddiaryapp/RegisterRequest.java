package com.cookandroid.aifooddiaryapp;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest{
    // 서버 URL 설정 - php 파일 연동
    final static private String URL = "/home/jung/Capston/Capstone_php/Register.php";
    private Map<String, String> map;

    public RegisterRequest(String userID, String userPass, String userName, int userAge, String userSex, double userHeight, double userWeight, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();

        map.put("userID", userID);
        map.put("userPassword", userPass);
        map.put("userName", userName);
        map.put("userAge", userAge + "");
        map.put("userSex", userSex);
        map.put("userHeight", userHeight + "");
        map.put("userWeight", userWeight + "");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
