package com.cookandroid.aifooddiaryapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Frag_FoodCalendar extends Fragment {
    private View view;

    // 위젯 변수 생성
    TextView tv_date;
    CardView cv_morning, cv_lunch, cv_dinner, cv_snack;
    ImageView img_morning, img_after, img_dinner, img_snack;


    // 날짜 변수 생성
    Date today;
    String currentDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_foodcalendar, container, false);

        // 위젯 변수 id 연결
        tv_date = (TextView) view.findViewById(R.id.tv_date);

        cv_morning = (CardView) view.findViewById(R.id.cv_morning);
        cv_lunch = (CardView) view.findViewById(R.id.cv_lunch);
        cv_dinner = (CardView) view.findViewById(R.id.cv_dinner);
        cv_snack = (CardView) view.findViewById(R.id.cv_snack);

        img_morning = (ImageView) view.findViewById(R.id.img_morning);
        img_after = (ImageView) view.findViewById(R.id.img_lunch);
        img_dinner = (ImageView) view.findViewById(R.id.img_dinner);
        img_snack = (ImageView) view.findViewById(R.id.img_snack);

        // 현재 날짜 불러와서 tv_date에 찍어줌
        today = new Date();
        tv_date.setText(new SimpleDateFormat("MM월 dd일").format(today));
        currentDate = new SimpleDateFormat("yyyy-MM-dd").format(today);

        // 현재 날짜에 식단 존재시 다이어리 가져오는 과정 필요
        // 푸드 다이어리 부분
        // 현재 보고 있는 데이트에 식단이 존재 한다면 기존에 있는 다이어리 내용 가져오는 과정 필요
        Response.Listener<String> getresponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject getjsonObject = new JSONObject(response);

                    if(getjsonObject.getBoolean("available") == true) {
                        // 해당 날짜에 데이터가 있다는 것.
                        if(getjsonObject.getInt("mealMorning") > 0) {
                            // 모닝에 데이터가 존재한다면 해당 음식 데이터 가져옴
                            String mealMorning = getjsonObject.getString("Morning");

                            // 가져온 음식 데이터는 , 를 기준으로 구분이 되어있으므로 스플릿해줌
                            String morning[] = mealMorning.split(",");

                            // 가져올 음식이 있는 것이므로 +모양(추가 모양)은 안 보이게 함.
                            cv_morning.setVisibility(View.GONE);

                            // 여기서부터 코딩 해주면 됩니다.
                            for(int i = 0; i < morning.length; i++) {

                            }

                        }

                        if(getjsonObject.getInt("mealLunch") > 0) {
                            // 점심에 데이터가 존재한다면 해당 음식 데이터 가져옴
                            String mealLunch = getjsonObject.getString("Lunch");

                            // 가져온 음식 데이터는 , 를 기준으로 구분이 되어있으므로 스플릿해줌
                            String lunch[] = mealLunch.split(",");

                            // 가져올 음식이 있는 것이므로 +모양(추가 모양)은 안 보이게 함.
                            cv_lunch.setVisibility(View.GONE);

                            // 여기서부터 코딩 해주면 됩니다.
                            //
                            //
                            //
                        }

                        if(getjsonObject.getInt("mealDinner") > 0) {
                            // 점심에 데이터가 존재한다면 해당 음식 데이터 가져옴
                            String mealDinner = getjsonObject.getString("Dinner");

                            // 가져온 음식 데이터는 , 를 기준으로 구분이 되어있으므로 스플릿해줌
                            String dinner[] = mealDinner.split(",");

                            // 가져올 음식이 있는 것이므로 +모양(추가 모양)은 안 보이게 함.
                            cv_dinner.setVisibility(View.GONE);

                            // 여기서부터 코딩 해주면 됩니다.
                            //
                            //
                            //
                        }

                    }

                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        // 서버로 Volley를 이용해서 요청을 함.
        FoodCalendar_GetRequest foodCalendar_getRequest= new FoodCalendar_GetRequest(HomeActivity.userID, currentDate, getresponseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(foodCalendar_getRequest);



        // 데이트 피커 생성
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String today = month + 1 + "월 " + dayOfMonth + "일";
                tv_date.setText(today);

                // 현재 선택한 날짜를 담을 변수 선언
                currentDate = year + "-" + month + 1 + "-" + dayOfMonth;

                // 푸드 다이어리 부분
                // 현재 보고 있는 데이트에 식단이 존재 한다면 기존에 있는 다이어리 내용 가져오는 과정 필요
                Response.Listener<String> getresponseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject getjsonObject = new JSONObject(response);

                            if(getjsonObject.getBoolean("available") == true) {
                                // 해당 날짜에 데이터가 있다는 것.
                                if(getjsonObject.getInt("mealMorning") > 0) {
                                    // 모닝에 데이터가 존재한다면 해당 음식 데이터 가져옴
                                    String mealMorning = getjsonObject.getString("Morning");

                                    // 가져온 음식 데이터는 , 를 기준으로 구분이 되어있으므로 스플릿해줌
                                    String moring[] = mealMorning.split(",");

                                    // 가져올 음식이 있는 것이므로 +모양(추가 모양)은 안 보이게 함.
                                    cv_morning.setVisibility(View.GONE);

                                    // 여기서부터 코딩 해주면 됩니다.
                                    //
                                    //
                                    //

                                }

                                if(getjsonObject.getInt("mealLunch") > 0) {
                                    // 점심에 데이터가 존재한다면 해당 음식 데이터 가져옴
                                    String mealLunch = getjsonObject.getString("Lunch");

                                    // 가져온 음식 데이터는 , 를 기준으로 구분이 되어있으므로 스플릿해줌
                                    String lunch[] = mealLunch.split(",");

                                    // 가져올 음식이 있는 것이므로 +모양(추가 모양)은 안 보이게 함.
                                    cv_lunch.setVisibility(View.GONE);

                                    // 여기서부터 코딩 해주면 됩니다.
                                    //
                                    //
                                    //
                                }

                                if(getjsonObject.getInt("mealDinner") > 0) {
                                    // 점심에 데이터가 존재한다면 해당 음식 데이터 가져옴
                                    String mealDinner = getjsonObject.getString("Dinner");

                                    // 가져온 음식 데이터는 , 를 기준으로 구분이 되어있으므로 스플릿해줌
                                    String dinner[] = mealDinner.split(",");

                                    // 가져올 음식이 있는 것이므로 +모양(추가 모양)은 안 보이게 함.
                                    cv_dinner.setVisibility(View.GONE);

                                    // 여기서부터 코딩 해주면 됩니다.
                                    //
                                    //
                                    //
                                }

                            }

                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // 서버로 Volley를 이용해서 요청을 함.
                FoodCalendar_GetRequest foodCalendar_getRequest= new FoodCalendar_GetRequest(HomeActivity.userID, currentDate, getresponseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(foodCalendar_getRequest);


            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.setTitle("날짜 선택");
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePickerDialog.getDatePicker().setCalendarViewShown(false);

        // 날짜를 클릭하면 데이트 피커가 나오고 날짜가 변경되게 함
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        // cv_morning 클릭 했을 때의 이벤트 처리
        cv_morning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Frag_Camera frag_camera = new Frag_Camera();
                transaction.replace(R.id.main_frame, frag_camera);
                transaction.commit();
            }
        });

        // cv_lunch 클릭 했을 때의 이벤트 처리
        cv_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Frag_Camera frag_camera = new Frag_Camera();
                transaction.replace(R.id.main_frame, frag_camera);
                transaction.commit();
            }
        });

        // cv_dinner 클릭 했을 때의 이벤트 처리
        cv_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Frag_Camera frag_camera = new Frag_Camera();
                transaction.replace(R.id.main_frame, frag_camera);
                transaction.commit();
            }
        });

        // cv_morning 클릭 했을 때의 이벤트 처리
        cv_snack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Frag_Camera frag_camera = new Frag_Camera();
                transaction.replace(R.id.main_frame, frag_camera);
                transaction.commit();
            }
        });

        return view;
    }
}
