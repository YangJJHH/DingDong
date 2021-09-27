package com.cookandroid.aifooddiaryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Frag_Mypage extends Fragment {
    Button btn_list;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_mypage, container, false);


        btn_list = (Button) view.findViewById(R.id.btn_list);
        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //자주먹는 식단 프래그먼트 이벤트 처리
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Frag_Favorite_Meals frag_favorite_meals = new Frag_Favorite_Meals();
                transaction.replace(R.id.main_frame, frag_favorite_meals);
                transaction.commit();
            }
        });




        return view;
    }
}
