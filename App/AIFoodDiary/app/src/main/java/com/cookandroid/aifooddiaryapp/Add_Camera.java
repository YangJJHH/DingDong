package com.cookandroid.aifooddiaryapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class Add_Camera extends AppCompatActivity {
    private ImageView img_food_photo;
    Button btn_add;
    CardView cv_add_food;
    TextView tv_AddFood;
    TextView[] tv_food_info= new TextView[7];
    CardView[] cv_food= new CardView[7];
    CardView[] cv_food_cancel= new CardView[7];
    Integer id[]={R.id.cv_food1,R.id.cv_food2,R.id.cv_food3,R.id.cv_food4,R.id.cv_food5,R.id.cv_food6,R.id.cv_food7};
    Integer id2[]={R.id.cv_food1_cancel,R.id.cv_food2_cancel,R.id.cv_food3_cancel,R.id.cv_food4_cancel,R.id.cv_food5_cancel,R.id.cv_food6_cancel,R.id.cv_food7_cancel};
    Integer id3[]={R.id.tv_food1_info,R.id.tv_food2_info,R.id.tv_food3_info,R.id.tv_food4_info,R.id.tv_food5_info,R.id.tv_food6_info,R.id.tv_food7_info,};
    //이미지 파일경로
    String mCurrentPhotoPath,date,meal,food_name,flag;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                // MainActivity 에서 요청할 때 보낸 요청 코드 (3000)
                case 0:
                    food_name=data.getStringExtra("food_name");
                    addFood();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_camera);
        img_food_photo=(ImageView)findViewById(R.id.img_food_photo);
        btn_add=findViewById(R.id.btn_add);
        cv_add_food=findViewById(R.id.cv_add_food);
        tv_AddFood=findViewById(R.id.tv_AddFood);

        //이전 엑티비티에서 받아온 데이터 받아오기
        Intent intent_r=getIntent();
        if(intent_r!=null){
            mCurrentPhotoPath= intent_r.getStringExtra("file_path");
            meal=intent_r.getStringExtra("meal");
            date=intent_r.getStringExtra("date");
            food_name=intent_r.getStringExtra("food_name");
            flag=intent_r.getStringExtra("flag");
            tv_AddFood.setText(date+"식단추가");
        }

        //카드뷰.텍스트뷰 위젯연결
        for(int i=0; i<7; i++){
            cv_food[i]=findViewById(id[i]);
            cv_food_cancel[i]=findViewById(id2[i]);
            tv_food_info[i]=findViewById(id3[i]);
        }


        //카드뷰 취소 이벤트리스너 연결
        int i;
        for(i=0; i<7; i++){
            final int index;
            index=i;

            cv_food_cancel[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //x버튼 클릭시 식단 지우기
                    cv_food[index].setVisibility(View.GONE);
                }
            });
        }

        //식단기록 버튼 리스너
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //+버튼 눌렀을경우 이벤트 리스너
        cv_add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_Camera.this,Add_HandWrite.class);
                intent.putExtra("flag","add_camera");
                startActivityForResult(intent,0);
            }
        });





        if(flag.equals("camera")){
            //카메라 추가로부터 왔을경우
            loadImage();
        }
        else{
            //수기추가로부터 왔을경우
            Toast.makeText(getApplicationContext(),"rs", Toast.LENGTH_SHORT).show();
            addFood();

        }
    }
    public void addFood(){
        int index=-1;
        for(int i=0; i<7; i++){
            if(cv_food[i].getVisibility()==View.GONE){
                index=i;
                break;
            }
        }
        //
        //카드뷰 정보입력
        tv_food_info[index].setText("음식이름:"+food_name);
        //
        cv_food[index].setVisibility(View.VISIBLE);

    }


    //이미지뷰에 음식사진 불러오기
    public void loadImage(){
        Bitmap bitmap;
        File file = new File(mCurrentPhotoPath);
        if (Build.VERSION.SDK_INT >= 29) {
            ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), Uri.fromFile(file));
            try {
                bitmap = ImageDecoder.decodeBitmap(source);
                Bitmap targetBmp = bitmap.copy(Bitmap.Config.ARGB_8888, false);
                if (bitmap != null) {
                    img_food_photo.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(file));
                Bitmap targetBmp = bitmap.copy(Bitmap.Config.ARGB_8888, false);
                if (bitmap != null) {
                    img_food_photo.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}