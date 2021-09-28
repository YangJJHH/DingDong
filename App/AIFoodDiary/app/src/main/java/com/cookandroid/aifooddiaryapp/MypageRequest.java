package com.cookandroid.aifooddiaryapp;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MypageRequest extends StringRequest {
    final static private String URL = "http://10.50.239.148/Mypage.php";
    private Map<String, String> map;

    public MypageRequest(double userHeight, double userWeight, double userBodyfat,double userMusclemass, int userBMR, char userFoodpurpose,Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);


        map = new HashMap<>();
        map.put("userHeight", userHeight + "");
        map.put("userWeight", userWeight + "");
        map.put("userBodyfat", userBodyfat + "");
        map.put("userMusclemass", userMusclemass + "");
        map.put("userBMR", userBMR + "");
        map.put("userFoodpurpose", userFoodpurpose+"");


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

