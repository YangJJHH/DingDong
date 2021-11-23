package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Add_HandWrite extends AppCompatActivity {
    String date,meal,food_name;
    String flag="hand";
    Button btn_add;

    public static AutoCompleteTextView auto;

    // 스피너 객체 생성
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hand_write);

        btn_add=findViewById(R.id.btn_add);

        spinner = (Spinner) findViewById(R.id.spinner);

        Intent intent_r=getIntent();
        if(intent_r!=null){
            meal=intent_r.getStringExtra("meal");
            date=intent_r.getStringExtra("date");
            flag=intent_r.getStringExtra("flag");
        }

        // 자동 완성 텍스트뷰 아이템 변수 생성
        String [] items = {"밥", "삶은달걀", "계란후라이", "고구마", "닭가슴살", "바나나", "샌드위치", "라면", "제육볶음",
                "연어", "스크램블드에그", "회", "치킨", "피자", "사과", "토마토", "수박", "복숭아", "삼겹살", "스테이크", "떡볶이", "파스타", "족발", "두부", "샐러드", "김치찌개", "찜닭", "육회", "갈비찜", "김치"};

        auto = (AutoCompleteTextView)findViewById(R.id.auto_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items);
        auto.setAdapter(adapter);
        if(flag==null){
            flag="hand";
        }


        // 자주 먹는 식단 데이터 db에서 가져옴
        Response.Listener<String> getresponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject getjsonObject = new JSONObject(response);

                    String food = getjsonObject.getString("food_name");

                    String []food_names = food.split(",");

                    SpinnerAdapter adapter2 = new SpinnerAdapter(food_names, Add_HandWrite.this);
                    spinner.setAdapter(adapter2);
                    spinner.setSelection(0, false);

                    // 스피너 아이템이 선택 되면 autoText에 띄워줌
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            auto.setText(food_names[position]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            auto.setText("");
                        }
                    });

                } catch(JSONException e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }
        };

        // 서버로 Volley를 이용해서 요청을 함.
        GetUsuallyMealRequest getUsuallyMealRequest = new GetUsuallyMealRequest(HomeActivity.userID, getresponseListener);
        RequestQueue queue = Volley.newRequestQueue(Add_HandWrite.this);
        queue.add(getUsuallyMealRequest);


        //음식추가 버튼
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food_name = auto.getText().toString();
                if(flag.equals("add_camera")){
                    Intent intent_res = new Intent();
                    intent_res.putExtra("meal", meal);
                    intent_res.putExtra("date", date);
                    intent_res.putExtra("food_name", food_name);
                    intent_res.putExtra("flag", flag);
                    setResult(RESULT_OK,intent_res);
                    finish();
                }else {
                    //정보를 담아주고 엑티비티전환
                    Intent intent_s = new Intent(Add_HandWrite.this, Add_Camera.class);
                    intent_s.putExtra("meal", meal);
                    intent_s.putExtra("date", date);
                    intent_s.putExtra("food_name", food_name);
                    intent_s.putExtra("flag", flag);
                    startActivity(intent_s);
                }
            }
        });
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