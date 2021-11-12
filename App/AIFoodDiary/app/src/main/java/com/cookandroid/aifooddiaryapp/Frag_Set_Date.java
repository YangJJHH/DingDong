package com.cookandroid.aifooddiaryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Calendar;

public class Frag_Set_Date extends Fragment {
    private View view;
    DatePicker datePicker;
    Spinner spinner;
    Button btn_ok;
    String date,meal;
    TextView tv_meal,tv_date;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getContext(),"식단추가 할 날짜를 선택하세요",Toast.LENGTH_SHORT).show();
        view = inflater.inflate(R.layout.frag_set_date, container, false);
        datePicker=view.findViewById(R.id.DatePicker);
        spinner = view.findViewById(R.id.spinner);
        tv_meal = view.findViewById(R.id.tv_meal);
        tv_date = view.findViewById(R.id.tv_date);
        btn_ok=view.findViewById(R.id.btn_ok);
        ArrayAdapter mealAdapter = ArrayAdapter.createFromResource(getContext(), R.array.meals,R.layout.custom_spinner_item );

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
                //다음화면으로 넘어갈떄 날짜정보와 끼니 정보 넘겨줄 bundle
                Bundle bundle = new Bundle();
                bundle.putString("meal",meal);
                bundle.putString("date",date);

                //정보를 담아주고 프래그먼트 전환
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Frag_Camera frag_camera = new Frag_Camera();
                frag_camera.setArguments(bundle);
                transaction.replace(R.id.main_frame, frag_camera);
                transaction.commit();
            }
        });


        return view;
    }
}
