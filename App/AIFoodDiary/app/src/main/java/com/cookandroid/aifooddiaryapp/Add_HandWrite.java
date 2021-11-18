package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class Add_HandWrite extends AppCompatActivity {
    String date,meal,food_name;
    String flag="hand";
    Button btn_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hand_write);

        btn_add=findViewById(R.id.btn_add);
        Intent intent_r=getIntent();
        if(intent_r!=null){
            meal=intent_r.getStringExtra("meal");
            date=intent_r.getStringExtra("date");
            flag=intent_r.getStringExtra("flag");
        }

        // 자동 완성 텍스트뷰 아이템 변수 생성
        String [] items = {"밥", "삶은달걀", "계란후라이", "고구마", "닭가슴살", "바나나", "샌드위치", "라면", "제육볶음",
                "연어", "스크램블드에그", "회", "치킨", "피자", "사과", "토마토", "수박", "복숭아", "삼겹살", "스테이크", "떡볶이", "파스타", "족발", "두부", "샐러드", "김치찌개", "찜닭", "육회", "갈비찜", "김치"};

        AutoCompleteTextView auto = (AutoCompleteTextView)findViewById(R.id.auto_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items);
        auto.setAdapter(adapter);
        if(flag==null){
            flag="hand";
        }

        else if(flag.equals("add_camera")){
            Toast.makeText(getApplicationContext(),"result", Toast.LENGTH_SHORT).show();
        }


        //음식추가 버튼
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food_name = auto.getText().toString();
                if(flag.equals("add_camera")){
                    Toast.makeText(getApplicationContext(),"result", Toast.LENGTH_SHORT).show();
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
}