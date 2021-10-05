package com.cookandroid.aifooddiaryapp;

import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;

public class Frag_Add_Camera extends Fragment {
    private View view;
    private ImageView img_food_photo;
    //이미지 파일경로
    String mCurrentPhotoPath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_add_camera, container, false);
        img_food_photo=(ImageView) view.findViewById(R.id.img_food_photo);

        //frag_camera 에서 받아온 파일경로 받아오고 loadImage메소드 호출
        Bundle bundle = getArguments();
        if(bundle!=null){
            //bundle 통해서 파일 경로 얻어오기
            mCurrentPhotoPath= bundle.getString("file_path");
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
                if (bitmap != null) {
                    img_food_photo.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.fromFile(file));
                if (bitmap != null) {
                    img_food_photo.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
