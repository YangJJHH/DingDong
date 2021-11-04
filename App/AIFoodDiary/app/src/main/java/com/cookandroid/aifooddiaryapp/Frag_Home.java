package com.cookandroid.aifooddiaryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Frag_Home extends Fragment {
    private View view;

    // 위젯 변수 선언
    ProgressBar prgbar_calorie, prgbar_protein, prgbar_carbohydrate, prgbar_fat;
    TextView tv_current_calorie, tv_current_protein, tv_current_carbohydrate, tv_current_fat;

    // 데이터베이스에서 받아오는 정보 저장할 변수 선언
    int today_calorie, today_carbohydrate, today_protein, today_fat;            // 오늘 먹어야할 것들 : today_0000
    int current_calorie = 0, current_carbohydrate = 0, current_protein = 0, current_fat = 0;    // 현재 먹은 수치 : current_0000
    double userHeight;

    ///////////// 하루 권장 섭취량 구하는 식 : ((키)-100)*0.9 * 30 (40 : 운동하는 사람들, 30 : 보통, 20 : 다이어터)
    ///////////// 탄단지 비율 -> 전체 권장 섭취 칼로리에서 보통 사람 비율 (탄,단,지) : (6, 2, 2) , 다이어트 : 4, 3, 3, 벌크업 : 5, 3, 2
    ///////////// 구하는 법은? -> 권장 섭취 칼로리 * 해당 영양소 비율 / 해당 영양소 칼로리 (탄수화물, 단백질 : 4, 지방 :9)
    ///////////// 하루 권장 섭취 탄수화물 구하기 -> Ex) 2523 * 0.6 / 4

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_home, container, false);

        // 프로그래스바 위젯 변수 위젯 id 연결
        prgbar_calorie = (ProgressBar) view.findViewById(R.id.prgbar_calorie);
        prgbar_carbohydrate = (ProgressBar) view.findViewById(R.id.prgbar_carbohydrate);
        prgbar_protein = (ProgressBar) view.findViewById(R.id.prgbar_protein);
        prgbar_fat = (ProgressBar) view.findViewById(R.id.prgbar_fat);

        // 텍스트뷰 위젯 변수 위젯 id 연결
        tv_current_calorie = (TextView) view.findViewById(R.id.tv_current_calorie);
        tv_current_carbohydrate = (TextView) view.findViewById(R.id.tv_current_carbohydrate);
        tv_current_protein = (TextView) view. findViewById(R.id.tv_current_protein);
        tv_current_fat = (TextView) view.findViewById(R.id.tv_current_fat);

        // 기존 회원 정보 가져오는 과정 필요
        Response.Listener<String> getresponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject getjsonObject = new JSONObject(response);

                    // 현재 회원의 키 정보 변수에 저장
                    userHeight = Double.parseDouble(getjsonObject.getString("userHeight"));

                    if(getjsonObject.getString("userBMR").equals("0")) {
                        // userBMR이 설정이 되어있지 않다면 권장 섭취량으로 설정해줌
                        if(getjsonObject.getString("userFoodpurpose").equals(" ")) {
                            today_calorie = (int) Math.floor(((userHeight) - 100) * 0.9 * 30);
                            today_carbohydrate = (int) Math.floor(today_calorie * 0.6 / 4);
                            today_protein = (int) Math.floor(today_calorie * 0.2 / 4);
                            today_fat = (int) Math.floor(today_calorie * 0.2 / 4);

                            // 현재 먹은 값들 가져와서 변수에 넣어줘야함!!!!!!!!!!!!!!!!!!!
                            // 채워 넣을것!!!!!!!!!!!!!!!!!!!!!!!


                        }   // 다이어트인 사람들의 권장 섭취량 수정
                        else if(getjsonObject.getString("userFoodpurpose").equals("D")) {
                            today_calorie = (int) Math.floor(((userHeight) - 100) * 0.9 * 20);
                            today_carbohydrate = (int) Math.floor(today_calorie * 0.4 / 4);
                            today_protein = (int) Math.floor(today_calorie * 0.3 / 4);
                            today_fat = (int) Math.floor(today_calorie * 0.3 / 4);

                            // 현재 먹은 값들 가져와서 변수에 넣어줘야함!!!!!!!!!!!!!!!!!!!
                            // 채워 넣을것!!!!!!!!!!!!!!!!!!!!!!!


                        }   // 벌크업인 사람들의 권장 섭취량 수정
                        else if(getjsonObject.getString("userFoodpurpose").equals("D")) {
                            today_calorie = (int) Math.floor(((userHeight) - 100) * 0.9 * 40);
                            today_carbohydrate = (int) Math.floor(today_calorie * 0.5 / 4);
                            today_protein = (int) Math.floor(today_calorie * 0.3 / 4);
                            today_fat = (int) Math.floor(today_calorie * 0.2 / 4);

                            // 현재 먹은 값들 가져와서 변수에 넣어줘야함!!!!!!!!!!!!!!!!!!!
                            // 채워 넣을것!!!!!!!!!!!!!!!!!!!!!!!


                        }

                    } else {
                        // userBMR이 설정 되어 있으므로 해당 BMR을 권장 섭취량으로 설정해줌
                        today_calorie = Integer.parseInt(getjsonObject.getString("userBMR"));
                        today_carbohydrate = (int) Math.floor(today_calorie * 0.5 / 4);
                        today_protein = (int) Math.floor(today_calorie * 0.3 / 4);
                        today_fat = (int) Math.floor(today_calorie * 0.2 / 4);
                    }

                    // 프로그래스바 설정해줌
                    prgbar_calorie.setMax(today_calorie);
                    prgbar_carbohydrate.setMax(today_carbohydrate);
                    prgbar_protein.setMax(today_protein);
                    prgbar_fat.setMax(today_fat);

                    // 현재 유저의 권장 섭취량 정보를 TextView에 표시해줌
                    tv_current_calorie.setText(current_calorie + " / " + today_calorie);
                    tv_current_carbohydrate.setText(current_carbohydrate + " / " + today_carbohydrate);
                    tv_current_protein.setText(current_protein + " / " + today_protein);
                    tv_current_fat.setText(current_fat + " / " + today_fat);


                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        // 서버로 Volley를 이용해서 요청을 함.
        HomeGetUserData homeGetUserData = new HomeGetUserData(HomeActivity.userID, getresponseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(homeGetUserData);

        return view;
    }
}
