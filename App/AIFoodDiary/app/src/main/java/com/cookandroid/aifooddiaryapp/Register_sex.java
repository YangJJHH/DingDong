package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

// !!!!!!!!!!인텐트 부분 수정해야함 !!!!!!!!!!!!!!!!!!!
// intent_s 는 인텐트 보낼 떄 쓰는 변수, intent_r은 인텐트 받을 때 쓸 변수!!!!!!!!!!!!

public class Register_sex extends AppCompatActivity {
    ImageButton imgbtn_man, imgbtn_woman;  // 남성, 여성 이미지 버튼 위젯 변수 선언
    Button btn_next;    // 버튼 위젯 변수 선언
    String sex = null;        // 성별을 알려줄 변수  (남성은 M, 여성은 F)
    CardView cardView_m, cardView_w; // 카드뷰 위젯 변수 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sex);

        // 각 버튼 변수와 위젯 id 연결
        imgbtn_man = (ImageButton) findViewById(R.id.imgbtn_man);
        imgbtn_woman = (ImageButton) findViewById(R.id.imgbtn_woman);
        btn_next = (Button) findViewById(R.id.btn_next);
        cardView_m = (CardView) findViewById(R.id.cardView_m);
        cardView_w = (CardView) findViewById(R.id.cardView_w);

        // 이전 화면에서 보낸 인자를 받기 위한 변수 intent_r 선언
        Intent intent_r = getIntent();
        String userID = intent_r.getStringExtra("userID");
        String userPass = intent_r.getStringExtra("userPass");
        String userName = intent_r.getStringExtra("userName");
        int userAge = intent_r.getIntExtra("userAge",0);
        
        // imgbtn_man(남성 버튼)이 눌렸을 때의 이벤트 처리
        imgbtn_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView_m.setCardElevation(20);
                cardView_w.setCardElevation(4);
                sex = "M";
            }
        });

        // imgbtn_woman(여성 버튼)이 눌렸을 때의 이벤트 처리
        imgbtn_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView_m.setCardElevation(4);
                cardView_w.setCardElevation(20);
                sex = "F";
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 두 버튼 중 하나의 값이 눌려 있어야 다음 화면으로 전환
                if(sex != null) {
                    // 이전 화면에서 받은 인자와 입력받은 userSex를 다음 화면 인자로 넣어주며 전환
                    Intent intent_s = new Intent(Register_sex.this, Register_body.class);
                    intent_s.putExtra("userID", userID);
                    intent_s.putExtra("userPass", userPass);
                    intent_s.putExtra("userName",userName);
                    intent_s.putExtra("userAge", userAge);
                    intent_s.putExtra("userSex",sex);

                    startActivity(intent_s);
                }
                else {
                    Toast.makeText(getApplicationContext(), "성별을 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}