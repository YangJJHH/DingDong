package com.cookandroid.aifooddiaryapp;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FoodCalendarInfo_Request extends StringRequest{
    // 서버 URL 설정 - php 파일 연동
    final static private String URL = "http://15.164.88.236/GetFoodCalendarInfo.php";
    private Map<String, String> map;

    public FoodCalendarInfo_Request(String userID, String currentDate, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();

        map.put("userID", userID);
        map.put("currentDate", currentDate);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
