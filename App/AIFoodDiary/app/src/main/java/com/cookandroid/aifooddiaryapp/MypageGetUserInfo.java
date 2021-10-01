package com.cookandroid.aifooddiaryapp;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MypageGetUserInfo extends StringRequest {
    final static private String URL = "http://10.50.239.148/GetUserInfo.php";
    private Map<String, String> map;

    public MypageGetUserInfo(String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

