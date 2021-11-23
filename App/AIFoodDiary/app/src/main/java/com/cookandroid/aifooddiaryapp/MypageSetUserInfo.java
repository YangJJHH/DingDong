package com.cookandroid.aifooddiaryapp;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MypageSetUserInfo extends StringRequest {
    // 서버 URL 설정 - php 파일 연동
    final static private String URL = "http://15.164.88.236/SetMypage.php";
    private Map<String, String> map;

    public MypageSetUserInfo(String userID, double userHeight, double userWeight, double userBodyfat, double userMusclemass, int userBMR, int userRecommend, char userFoodpurpose, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);


        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userHeight", userHeight + "");
        map.put("userWeight", userWeight + "");
        map.put("userBodyfat", userBodyfat + "");
        map.put("userMusclemass", userMusclemass + "");
        map.put("userBMR", userBMR + "");
        map.put("userRecommend", userRecommend + "");
        map.put("userFoodpurpose", userFoodpurpose + "");


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

