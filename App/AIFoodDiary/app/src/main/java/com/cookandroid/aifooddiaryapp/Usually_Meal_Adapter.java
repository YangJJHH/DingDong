package com.cookandroid.aifooddiaryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        holder.tv_carbohydrate.setText("탄수화물: "+arrayList.get(position).getCarbohydrate()+"");
        holder.tv_kcal.setText("칼로리: "+arrayList.get(position).getKacl()+"");
        holder.tv_protein.setText("단백질 "+arrayList.get(position).getProtein()+"");
        holder.tv_fat.setText("지방: "+arrayList.get(position).getFat()+"");

        //리스트 아이템에 대한 이벤트 처리
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String meal_name=holder.tv_meal_name.getText().toString();
                Toast.makeText(view.getContext(),meal_name,Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return (null !=arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView tv_meal_name;
        protected TextView tv_kcal;
        protected TextView tv_carbohydrate;
        protected TextView tv_protein;
        protected TextView tv_fat;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_meal_name=(TextView)itemView.findViewById(R.id.tv_meal_name);
            this.tv_kcal=(TextView)itemView.findViewById(R.id.tv_kcal);
            this.tv_carbohydrate=(TextView)itemView.findViewById(R.id.tv_carbohydrate);
            this.tv_protein=(TextView)itemView.findViewById(R.id.tv_protein);
            this.tv_fat=(TextView)itemView.findViewById(R.id.tv_fat);
        }
    }
}
