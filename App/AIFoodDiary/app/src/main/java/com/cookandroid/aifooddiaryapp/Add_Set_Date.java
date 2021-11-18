package com.cookandroid.aifooddiaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Add_Set_Date extends AppCompatActivity {
    DatePicker datePicker;
    Spinner spinner;
    Button btn_ok;
    String date,meal;
    TextView tv_meal,tv_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_set_date);
        Toast.makeText(getApplicationContext(),"식단추가 할 날짜를 선택하세요",Toast.LENGTH_SHORT).show();
        datePicker=findViewById(R.id.DatePicker);
        spinner = findViewById(R.id.spinner);
        tv_meal = findViewById(R.id.tv_meal);
        tv_date = findViewById(R.id.tv_date);
        btn_ok=findViewById(R.id.btn_ok);
        ArrayAdapter mealAdapter = ArrayAdapter.createFromResource(this, R.array.meals,R.layout.custom_spinner_item );

        mealAdapter.setDropDownViewResource(R.layout.custom_spinner_item );
        spinner.setAdapter(mealAdapter); //어댑터에 연결해줍니다.

        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);
        date=cYear+"-"+(cMonth+1)+"-"+cDay;
        tv_date.setText(date);

        datePicker.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                date=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                tv_date.setText(date);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                meal=(String) spinner.getItemAtPosition(position);
                tv_meal.setText(meal);

            } //이 오버라이드 메소드에서 position은 몇번째 값이 클릭됬는지 알 수 있습니다.
            //getItemAtPosition(position)를 통해서 해당 값을 받아올수있습니다.

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });




        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다음 화면으로 끼니, 날짜 정보 넘겨줌
                Intent intent_s = new Intent(Add_Set_Date.this,Camera.class);
                intent_s.putExtra("meal", meal);
                intent_s.putExtra("date", date);
                startActivity(intent_s);
            }
        });
    }
}