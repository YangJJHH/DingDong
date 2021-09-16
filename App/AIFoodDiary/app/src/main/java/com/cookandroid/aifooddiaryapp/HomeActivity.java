package com.cookandroid.aifooddiaryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    // 하단바 변수 선언
    private BottomNavigationView bottomNavigationView;
    
    // 프래그먼트 매니저와 트랜잭션 변수 선언
    private FragmentManager fm;
    private FragmentTransaction ft;

    // 프래그먼트 변수 선언
    private Frag_Home frag_home;
    private Frag_FoodCalendar frag_foodcalendar;
    private Frag_Camera frag_camera;
    private Frag_Mypage frag_mypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
        frag_camera = new Frag_Camera();
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
                ft.replace(R.id.main_frame, frag_camera);
                ft.commit();
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
}