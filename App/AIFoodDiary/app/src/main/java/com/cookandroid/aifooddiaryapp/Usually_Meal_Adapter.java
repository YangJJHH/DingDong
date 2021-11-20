package com.cookandroid.aifooddiaryapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.cookandroid.aifooddiaryapp.Frag_Usually_Meals.m2;

// 커스텀 리사이클러뷰 어댑터 정의
public class Usually_Meal_Adapter extends RecyclerView.Adapter<Usually_Meal_Adapter.CustomViewHolder> {

    //Meal(식단) 객체 ArrayList 생성
    private ArrayList<Usually_Meal> arrayList;

    public Usually_Meal_Adapter(ArrayList<Usually_Meal> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    //어댑터 생성주기 설정
    public Usually_Meal_Adapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.list_meals,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Usually_Meal_Adapter.CustomViewHolder holder, int position) {

        holder.tv_meal_name.setText(arrayList.get(position).getName());

        //리스트 아이템에 대한 이벤트 처리
        holder.itemView.setTag(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 다이얼로그를 띄워줌
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("자주 먹는 음식 삭제");
                builder.setMessage("'" + holder.tv_meal_name.getText().toString() + "'를 삭제 하시겠습니까?");
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(view.getContext(), "삭제를 취소 하셨습니다.", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String meal_name = holder.tv_meal_name.getText().toString();

                        arrayList.remove(position);
                        notifyDataSetChanged();

                        m2.setRemove(meal_name);
                        String result = m2.getPush_name();

                        m2.setPush_name("");
                        m2.setPush_name(result);

                    }
                });
                builder.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return (null !=arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView tv_meal_name;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_meal_name = (TextView) itemView.findViewById(R.id.tv_meal_name);
        }
    }
}