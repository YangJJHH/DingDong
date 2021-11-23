package com.cookandroid.aifooddiaryapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    // 하단바 변수 선언
    private BottomNavigationView bottomNavigationView;
    Button btn_list;

    // 프래그먼트 매니저와 트랜잭션 변수 선언
    private FragmentManager fm;
    private FragmentTransaction ft;

    // 프래그먼트 변수 선언
    private Frag_Home frag_home;
    private Frag_FoodCalendar frag_foodcalendar;
    private Frag_Mypage frag_mypage;

    // 여러 화면에서 userID를 통해 데이터베이스에서 해당 회원의 값을 가져오기 위해 static 선언
    public static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 이전 (로그인 화면)에서 보낸 인텐트 값 받아옴
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");

        // 위젯 변수와 위젯 id 연결
        bottomNavigationView = findViewById(R.id.bottomNavi);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.action_home:
                        setFrag(0);
                        break;
                    case R.id.action_camera:
                        setFrag(1);
                        break;
                    case R.id.action_foodcalendar:
                        setFrag(2);
                        break;
                    case R.id.action_mypage:
                        setFrag(3);
                        break;
                }
                return false;
            }
        });

        // 프래그먼트 생성
        frag_home = new Frag_Home();
        frag_foodcalendar = new Frag_FoodCalendar();
        frag_mypage =  new Frag_Mypage();

        // 초기 표시 화면을 홈화면으로 하기 위해 매개변수 0을 줌
        setFrag(0);
    }

    // 프래그먼트 교체가 일어나는 함수 setFrag()
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, frag_home);
                ft.commit();
                break;
            case 1:
                Intent intent = new Intent(HomeActivity.this, Add_Set_Date.class);
                startActivity(intent);
                break;
            case 2:
                ft.replace(R.id.main_frame, frag_foodcalendar);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, frag_mypage);
                ft.commit();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        AlertDialog.Builder ad = new AlertDialog.Builder(HomeActivity.this);
        ad.setTitle("앱 종료");
        ad.setMessage("앱을 종료하시겠습니까?");
        ad.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                finishAndRemoveTask();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        ad.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ad.show();
    }
}