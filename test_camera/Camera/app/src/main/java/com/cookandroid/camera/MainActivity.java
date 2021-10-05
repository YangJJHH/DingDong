package com.cookandroid.camera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.BuildConfig;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.os.Environment.DIRECTORY_PICTURES;

public class MainActivity extends AppCompatActivity{

    Button btnCamera;
    ImageView imageView;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 디자인 정의
        btnCamera = (Button) findViewById(R.id.btn_capture);
        imageView = (ImageView) findViewById(R.id.iv_result);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 0);
            }
        });

    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data); // 카메라 촬영을 하면 이미지뷰에 사진 삽입
        if(requestCode == 0 && resultCode == RESULT_OK) { // Bundle로 데이터를 입력
            Bundle extras = data.getExtras(); // Bitmap으로 컨버전
            Bitmap imageBitmap = (Bitmap) extras.get("data"); // 이미지뷰에 Bitmap으로 이미지를 입력
            imageView.setImageBitmap(imageBitmap);
        }
    }
}







