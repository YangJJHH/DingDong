package com.cookandroid.aifooddiaryapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
    RadioButton rdb_diet, rdb_bulk, rdb_keep;

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
        rdb_keep = (RadioButton) view.findViewById(R.id.rdb_keep);

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
                    } else if (getjsonObject.getString("userFoodpurpose").equals("D")){
                        rdb_diet.setChecked(true);
                    } else {
                        rdb_keep.setChecked(true);
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
                Frag_Usually_Meals frag_usually_meals = new Frag_Usually_Meals();
                transaction.replace(R.id.main_frame, frag_usually_meals);
                transaction.commit();
            }
        });

        // 수정 된 회원 정보 업데이트 하는 과정 필요
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 자료형이 맞지 않을 때(사용자의 실수로 . 이 두 번 입력됐을 때 예외 처리 함)
                try{

                    // 현재 입력받은 값들 모두 저장
                    double userHeight = Double.parseDouble(et_height.getText().toString());
                    double userWeight = Double.parseDouble(et_weight.getText().toString());
                    double userBodyfat = Double.parseDouble(et_bodyfat.getText().toString());
                    double userMusclemass = Double.parseDouble(et_musclemass.getText().toString());
                    int userBMR = Integer.parseInt(et_BMR.getText().toString());

                    int userRecommend = 0;
                    char userFoodpurpose = ' ';

                    switch(rdg_foodpurpose.getCheckedRadioButtonId()) {
                        case R.id.rdb_bulk :
                            userFoodpurpose = 'B';
                            userRecommend = (int) Math.floor(userBMR + (userBMR * 0.6));
                            break;
                        case R.id.rdb_diet:
                            userFoodpurpose = 'D';
                            userRecommend = (int) Math.floor(userBMR + (userBMR * 0.2));
                            break;
                        case R.id.rdb_keep:
                            userFoodpurpose = 'K';
                            userRecommend = (int) Math.floor(userBMR + (userBMR * 0.4));
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
                    MypageSetUserInfo mypageSetUserInfo = new MypageSetUserInfo(HomeActivity.userID, userHeight, userWeight, userBodyfat, userMusclemass, userBMR, userRecommend, userFoodpurpose, setresponseListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    queue.add(mypageSetUserInfo);

                }catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "오류! 입력된 값들을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        
        // 로그아웃 버튼 이벤트 처리
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences setting = getActivity().getSharedPreferences("UserLogin", getActivity().MODE_PRIVATE);

                boolean autologin = setting.getBoolean("AutoLoginEnabled", false);

                // 자동 로그인이 체크 되어있다면 자동 로그인에 있는 모든 정보 지워줌
                if(autologin == true) {
                    SharedPreferences.Editor editor = setting.edit();
                    editor.putBoolean("AutoLoginEnabled", false);
                    editor.clear();
                    editor.commit();
                }

                new AlertDialog.Builder(getActivity())
                        .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent intent = new Intent(getActivity(), Login.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                Toast.makeText(getActivity().getApplicationContext(), "성공적으로 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(getActivity().getApplicationContext(), "로그아웃을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
        
        

        return view;
    }
}
