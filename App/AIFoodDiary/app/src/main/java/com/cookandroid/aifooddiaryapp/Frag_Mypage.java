package com.cookandroid.aifooddiaryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Frag_Mypage extends Fragment {
    Button btn_list, btn_save, btn_logout;
    private View view;

    // 회원정보 위젯 변수 선언
    EditText et_height, et_weight, et_bodyfat, et_musclemass, et_BMR;
    RadioGroup rdg_foodpurpose;
    RadioButton rdb_diet, rdb_bulk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_mypage, container, false);

        // 위젯 변수 위젯 id 연결
        btn_list = (Button) view.findViewById(R.id.btn_list);
        btn_save = (Button) view.findViewById(R.id.btn_save);
        btn_logout = (Button) view.findViewById(R.id.btn_logout);

        et_height = (EditText) view.findViewById(R.id.et_height);
        et_weight = (EditText) view.findViewById(R.id.et_weight);
        et_bodyfat = (EditText) view.findViewById(R.id.et_bodyfat);
        et_musclemass = (EditText) view.findViewById(R.id.et_musclemass);
        et_BMR = (EditText) view.findViewById(R.id.et_BMR);
        rdg_foodpurpose = (RadioGroup) view.findViewById(R.id.rdg_foodpurpose);
        rdb_diet = (RadioButton) view.findViewById(R.id.rdb_diet);
        rdb_bulk = (RadioButton) view.findViewById(R.id.rdb_bulk);

        // 기존 회원 정보 가져오는 과정 필요
        Response.Listener<String> getresponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject getjsonObject = new JSONObject(response);

                    // 현재 회원 정보 EditText에 띄워줌
                    et_height.setText(getjsonObject.getString("userHeight"));
                    et_weight.setText(getjsonObject.getString("userWeight"));
                    et_bodyfat.setText(getjsonObject.getString("userBodyfat"));
                    et_musclemass.setText(getjsonObject.getString("userMusclemass"));
                    et_BMR.setText(getjsonObject.getString("userBMR"));

                    if(getjsonObject.getString("userFoodpurpose").equals("B")) {
                        rdb_bulk.setChecked(true);
                    } else {
                        rdb_diet.setChecked(true);
                    }

                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        // 서버로 Volley를 이용해서 요청을 함.
        MypageGetUserInfo mypageGetUserInfo = new MypageGetUserInfo(HomeActivity.userID, getresponseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(mypageGetUserInfo);


        // 자주먹는 식단 프래그먼트 이벤트 처리
        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Frag_Favorite_Meals frag_favorite_meals = new Frag_Favorite_Meals();
                transaction.replace(R.id.main_frame, frag_favorite_meals);
                transaction.commit();
            }
        });

        // 수정 된 회원 정보 업데이트 하는 과정 필요
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 입력받은 값들 모두 저장
                double userHeight = Double.parseDouble(et_height.getText().toString());
                double userWeight = Double.parseDouble(et_weight.getText().toString());
                double userBodyfat = Double.parseDouble(et_bodyfat.getText().toString());
                double userMusclemass = Double.parseDouble(et_musclemass.getText().toString());
                int userBMR = Integer.parseInt(et_BMR.getText().toString());
                char userFoodpurpose = ' ';

                switch(rdg_foodpurpose.getCheckedRadioButtonId()) {
                    case R.id.rdb_bulk :
                        userFoodpurpose = 'B';
                        break;
                    case R.id.rdb_diet:
                        userFoodpurpose = 'D';
                        break;
                }

                Response.Listener<String> setresponseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject setjsonObject = new JSONObject(response);
                            boolean success = setjsonObject.getBoolean("success");

                            // 회원 정보 수정에 성공한 경우
                            if(success) {
                                Toast.makeText(getActivity().getApplicationContext(),"회원 정보를 수정하였습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                // 회원 정보 수정에 실패한 경우
                                Toast.makeText(getActivity().getApplicationContext(),"회원 정보 수정을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }

                        } catch(JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                // 서버로 Volley를 이용해서 요청을 함.
                MypageRequest mypageRequest= new MypageRequest(HomeActivity.userID, userHeight, userWeight, userBodyfat, userMusclemass, userBMR, userFoodpurpose, setresponseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(mypageRequest);

            }
        });
        
        // 로그아웃 버튼 구현 필요
        
        
        

        return view;
    }
}
