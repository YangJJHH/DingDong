package com.cookandroid.aifooddiaryapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.File;
import java.io.IOException;

public class Frag_Add_HandWrite extends Fragment {
    private View view;
    String date,meal,food_name;
    String flag="hand";
    Button btn_add;

    public static AutoCompleteTextView auto;

    // 스피너 객체 생성
    Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_add_handwrite, container, false);

        // 위젯 id 연결
        btn_add = view.findViewById(R.id.btn_add);
        spinner = (Spinner) view.findViewById(R.id.spinner);


        // 자동 완성 텍스트뷰 아이템 변수 생성
        String [] items = {"밥", "삶은달걀", "계란후라이", "고구마", "닭가슴살", "바나나", "샌드위치", "라면", "제육볶음",
                "연어", "스크램블드에그", "회", "치킨", "피자", "사과", "토마토", "수박", "복숭아", "삼겹살", "스테이크", "떡볶이", "파스타", "족발", "두부", "샐러드", "김치찌개", "찜닭", "육회", "갈비찜", "김치"};

        auto = (AutoCompleteTextView) view.findViewById(R.id.auto_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, items);
        auto.setAdapter(adapter);

        // 자주 먹는 식단 데이터 db에서 가져옴
        Response.Listener<String> getresponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject getjsonObject = new JSONObject(response);

                    String food = getjsonObject.getString("food_name");

                    String []food_names = food.split(",");

                    SpinnerAdapter adapter2 = new SpinnerAdapter(food_names, getContext());
                    spinner.setAdapter(adapter2);

                    // 스피너 아이템이 선택 되면 autoText에 띄워줌
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            auto.setText(spinner.getItemAtPosition(position)+"");
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

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

    public class SpinnerAdapter extends BaseAdapter {
        String food_names[];
        Context context;

        public SpinnerAdapter(String[] food_names, Context context) {
            this.food_names = food_names;
            this.context = context;
        }

        @Override
        public int getCount() {
            return food_names.length;
        }

        @Override
        public Object getItem(int position) {
            return food_names[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.spinner_usually_meal, null);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.item);
            tv.setText(food_names[position]);

            return convertView;
        }
    }
}

