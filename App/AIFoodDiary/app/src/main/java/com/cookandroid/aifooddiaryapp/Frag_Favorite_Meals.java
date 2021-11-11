package com.cookandroid.aifooddiaryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Frag_Favorite_Meals extends Fragment {
    private View view;
    private ArrayList<Usually_Meal> arrayList;
    private Usually_Meal_Adapter usuallyMeal_adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    Button btn;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_favoirte_meals, container, false);
        //리싸이클러뷰 연결
        recyclerView=(RecyclerView)view.findViewById(R.id.rv);
        linearLayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();
        usuallyMeal_adapter = new Usually_Meal_Adapter(arrayList);
        recyclerView.setAdapter(usuallyMeal_adapter);

        btn=(Button)view.findViewById(R.id.btn_add_meal);

        //예시 식단 추가
        Usually_Meal m = new Usually_Meal("제육",134,22,23,34);
        arrayList.add(m);
        //새로고침
        usuallyMeal_adapter.notifyDataSetChanged();

        //test용 메소드
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usually_Meal m1 = new Usually_Meal("치킨",131234,322,23,34);
                arrayList.add(m1);
                //새로고침
                usuallyMeal_adapter.notifyDataSetChanged();
            }
        });
        return view;
    }
}
