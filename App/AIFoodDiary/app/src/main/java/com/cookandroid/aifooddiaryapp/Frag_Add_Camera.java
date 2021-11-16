package com.cookandroid.aifooddiaryapp;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Frag_Add_Camera extends Fragment {
    private View view;
    private ImageView img_food_photo;
    Button btn_add;
    CardView cv_add_food;
    CardView[] cv_food= new CardView[7];
    CardView[] cv_food_cancel= new CardView[7];
    Integer id[]={R.id.cv_food1,R.id.cv_food2,R.id.cv_food3,R.id.cv_food4,R.id.cv_food5,R.id.cv_food6,R.id.cv_food7};
    Integer id2[]={R.id.cv_food1_cancel,R.id.cv_food2_cancel,R.id.cv_food3_cancel,R.id.cv_food4_cancel,R.id.cv_food5_cancel,R.id.cv_food6_cancel,R.id.cv_food7_cancel};
    CardView ca_food_cancel[]= new CardView[7];
    //이미지 파일경로
    String mCurrentPhotoPath,date,meal,food_name,flag;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_add_camera, container, false);
        img_food_photo=(ImageView) view.findViewById(R.id.img_food_photo);
        btn_add=view.findViewById(R.id.btn_add);
        cv_add_food=view.findViewById(R.id.cv_add_food);

        //카드뷰 위젯연결
        for(int i=0; i<7; i++){
            cv_food[i]=view.findViewById(id[i]);
            cv_food_cancel[i]=view.findViewById(id2[i]);
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


        //frag_camera 에서 받아온 파일경로 받아오고 loadImage메소드 호출
        Bundle bundle = getArguments();
        if(bundle!=null){
            //bundle 통해서 파일 경로 얻어오기
            mCurrentPhotoPath= bundle.getString("file_path");
            meal=bundle.getString("meal");
            date=bundle.getString("date");
            food_name= bundle.getString("food_name");
            flag=bundle.getString("flag");
        }
        if(flag.equals("camera")){
            //카메라 추가로부터 왔을경우
            loadImage();
        }
        else{
            //수기추가로부터 왔을경우
            addFood();
        }
        return view;
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
        //
        cv_food[index].setVisibility(View.VISIBLE);

    }

    //이미지뷰에 음식사진 불러오기
    public void loadImage(){
        Bitmap bitmap;
        File file = new File(mCurrentPhotoPath);
        if (Build.VERSION.SDK_INT >= 29) {
            ImageDecoder.Source source = ImageDecoder.createSource(getContext().getContentResolver(), Uri.fromFile(file));
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
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.fromFile(file));
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
