package com.cookandroid.aifooddiaryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Frag_Usually_Meals extends Fragment {
    private View view;
    private ArrayList<Usually_Meal> arrayList;
    private Usually_Meal_Adapter usuallyMeal_adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    // 위젯 변수 선언
    Button btn_add_meal, btn_save;
    AutoCompleteTextView auto_name;

    Usually_Meal m1;
    static Usually_Meal m2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_usually_meals, container, false);

        //리싸이클러뷰 연결
        recyclerView=(RecyclerView)view.findViewById(R.id.rv);
        linearLayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();
        usuallyMeal_adapter = new Usually_Meal_Adapter(arrayList);
        recyclerView.setAdapter(usuallyMeal_adapter);

        // 위젯 id 연결
        btn_add_meal = (Button)view.findViewById(R.id.btn_add_meal);
        btn_save = (Button) view.findViewById(R.id.btn_save);

        auto_name = (AutoCompleteTextView) view.findViewById(R.id.auto_name);

        m2 = new Usually_Meal();

        // 자동 완성 텍스트뷰 아이템 변수 생성
        String [] items = {"밥", "삶은달걀", "계란후라이", "고구마", "닭가슴살", "바나나", "샌드위치", "라면", "제육볶음",
                "연어", "스크램블드에그", "회", "치킨", "피자", "사과", "토마토", "수박", "복숭아", "삼겹살", "스테이크", "떡볶이", "파스타", "족발", "두부", "샐러드", "김치찌개", "찜닭", "육회", "갈비찜", "김치"};

        AutoCompleteTextView auto = (AutoCompleteTextView) view.findViewById(R.id.auto_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, items);
        auto.setAdapter(adapter);

        // 기존 회원 정보 가져오는 과정 필요
        Response.Listener<String> getresponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject getjsonObject = new JSONObject(response);

                    String food = getjsonObject.getString("food_name");

                    String food_names[] = food.split(",");
                    m2.setNames(food.split(","));

                    for(int i = 0; i < food_names.length; i++) {
                        m1 = new Usually_Meal(food_names[i]);
                        arrayList.add(m1);
                    }

                    //새로고침
                    usuallyMeal_adapter.notifyDataSetChanged();


                } catch(JSONException e) {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }
        };

        // 서버로 Volley를 이용해서 요청을 함.
        GetUsuallyMealRequest getUsuallyMealRequest = new GetUsuallyMealRequest(HomeActivity.userID, getresponseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(getUsuallyMealRequest);

        // 추가 버튼 클릭 시 이벤트 처리
        btn_add_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // AutoText에 담긴 음식 이름 가져옴
                String food_name = auto_name.getText().toString();
                for(int i = 0; i < items.length; i++) {
                    if(food_name.equals(items[i])) {
                        m1 = new Usually_Meal(food_name);
                        m2.add_names(food_name);

                        arrayList.add(m1);
                        //새로고침
                        usuallyMeal_adapter.notifyDataSetChanged();
                    }
                }


            }
        });

        // 저장 버튼 클릭 시 이벤트 처리
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 기록 된 자주 먹는 식단 DB에 저장
                Response.Listener<String> setresponseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject setjsonObject = new JSONObject(response);
                            boolean success = setjsonObject.getBoolean("success");

                            // 회원 정보 수정에 성공한 경우
                            if(success) {
                                Toast.makeText(getActivity().getApplicationContext(),"자주 먹는 식단을 수정하였습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                // 회원 정보 수정에 실패한 경우
                                Toast.makeText(getActivity().getApplicationContext(),"자주 먹는 식단 수정을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }

                        } catch(JSONException e) {
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                };

                // 서버로 Volley를 이용해서 요청을 함.
                SetUsuallyMealRequest setUsuallyMealRequest = new SetUsuallyMealRequest(HomeActivity.userID, m2.getPush_name(), setresponseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(setUsuallyMealRequest);
            }
        });


        return view;
    }

}
