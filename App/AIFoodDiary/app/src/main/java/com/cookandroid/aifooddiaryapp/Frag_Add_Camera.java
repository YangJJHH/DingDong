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
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    TextView tv_rst;
    //이미지 파일경로
    String mCurrentPhotoPath,date,meal;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_add_camera, container, false);
        img_food_photo=(ImageView) view.findViewById(R.id.img_food_photo);
        tv_rst=view.findViewById(R.id.tv_rst);
        //frag_camera 에서 받아온 파일경로 받아오고 loadImage메소드 호출
        Bundle bundle = getArguments();
        if(bundle!=null){
            //bundle 통해서 파일 경로 얻어오기
            mCurrentPhotoPath= bundle.getString("file_path");
            meal=bundle.getString("meal");
            date=bundle.getString("date");
            tv_rst.setText(mCurrentPhotoPath);
            loadImage();
        }
        return view;
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
