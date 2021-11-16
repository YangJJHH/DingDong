package com.cookandroid.aifooddiaryapp;

import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.io.IOException;

public class Frag_Add_HandWrite extends Fragment {
    private View view;
    String date,meal,food_name;
    String flag="hand";
    Button btn_add;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_add_handwrite, container, false);
        btn_add=view.findViewById(R.id.btn_add);

        // 자동 완성 텍스트뷰 아이템 변수 생성
        String [] items = {"밥", "삶은달걀", "계란후라이", "고구마", "닭가슴살", "바나나", "샌드위치", "라면", "제육볶음",
                "연어", "스크램블드에그", "회", "치킨", "피자", "사과", "토마토", "수박", "복숭아", "삼겹살", "스테이크", "떡볶이", "파스타", "족발", "두부", "샐러드", "김치찌개", "찜닭", "육회", "갈비찜", "김치"};

        AutoCompleteTextView auto = (AutoCompleteTextView) view.findViewById(R.id.auto_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, items);
        auto.setAdapter(adapter);

        //frag_camera 에서 받아온 파일경로 받아오고 loadImage메소드 호출
        Bundle bundle = getArguments();
        if(bundle!=null) {
            //bundle 통해서 파일 경로 얻어오기
            meal = bundle.getString("meal");
            date = bundle.getString("date");
        }

        //음식추가 버튼
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food_name=auto.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("meal",meal);
                bundle.putString("date",date);
                bundle.putString("food_name",food_name);
                bundle.putString("flag",flag);

                //정보를 담아주고 프래그먼트 전환
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Frag_Add_Camera frag_add_camera = new Frag_Add_Camera();
                frag_add_camera.setArguments(bundle);
                transaction.replace(R.id.main_frame, frag_add_camera);
                transaction.commit();
            }
        });

        return  view;
    }


}

