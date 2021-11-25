package com.cookandroid.aifooddiaryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Camera extends AppCompatActivity {
    File file;
    ImageButton btn_camera,btn_handwrite;
    String mCurrentPhotoPath;
    TextView tv_AddFood;
    String date,meal;
    String flag="camera";
    final private static String TAG = "CAMERA";
    Intent intent_c;
    Intent intent_h;
    ListView lv_recommend;
    ArrayList<list_recommend_item> mitems = new ArrayList<>();
    Adapter_Recommed adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        btn_camera=findViewById(R.id.imageButton2);
        btn_handwrite=findViewById(R.id.imageButton);
        File sdcard = Environment.getExternalStorageDirectory();
        file= new File(sdcard,"capture.jpg");
        tv_AddFood=findViewById(R.id.tv_AddFood);
        lv_recommend=findViewById(R.id.lv_recommend);

        // DB에서 나와 비슷한 사용자 정보 가져옴
        Response.Listener<String> getresponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String userFoodpurpose;
                    int rcm_kcal[] = new int[3];
                    int rcm_carbo[] = new int[3];
                    int rcm_protein[] = new int[3];
                    int rcm_fat[] = new int[3];

                    JSONObject getjsonObject = new JSONObject(response);

                    // 현재 유저의 식단 목적 저장
                    userFoodpurpose = getjsonObject.getString("userFoodpurpose");
                    
                    // 추천 받은 권장 섭취 칼로리 변수에 저장
                    rcm_kcal[0] = getjsonObject.getInt("Recommend1");
                    rcm_kcal[1] = getjsonObject.getInt("Recommend2");
                    rcm_kcal[2] = getjsonObject.getInt("Recommend3");

                    // 가져온 값에 대해 권장 탄수화물, 단백질, 지방 섭취량 구해줌
                    if(userFoodpurpose != null) {
                        //어댑터 연결
                        adapter = new Adapter_Recommed(getApplicationContext(), mitems);
                        lv_recommend.setAdapter(adapter);

                        for(int i = 0; i < 3; i++) {
                            if(userFoodpurpose.equals("K") || userFoodpurpose.equals("")) {
                                rcm_carbo[i] = (int) Math.floor((rcm_kcal[i] * 0.6) / 4);
                                rcm_protein[i] = (int) Math.floor((rcm_kcal[i] * 0.2) / 4);
                                rcm_fat[i] = (int) Math.floor((rcm_kcal[i] * 0.2) / 9);
                            } else if(userFoodpurpose.equals("B")) {
                                rcm_carbo[i] = (int) Math.floor((rcm_kcal[i] * 0.55) / 4);
                                rcm_protein[i] = (int) Math.floor((rcm_kcal[i] * 0.25) / 4);
                                rcm_fat[i] = (int) Math.floor((rcm_kcal[i] * 0.2) / 9);
                            } else if(userFoodpurpose.equals("D")) {
                                rcm_carbo[i] = (int) Math.floor((rcm_kcal[i] * 0.55) / 4);
                                rcm_protein[i] = (int) Math.floor((rcm_kcal[i] * 0.15) / 4);
                                rcm_fat[i] = (int) Math.floor((rcm_kcal[i] * 0.3) / 9);
                            }
                            list_recommend_item item = new list_recommend_item();
                            item.setKacl(rcm_kcal[i]+"");
                            item.setCarbo(rcm_carbo[i]+"");
                            item.setProtein(rcm_protein[i]+"");
                            item.setFat(rcm_fat[i]+"");
                            mitems.add(item);
                            adapter.setItem(mitems);

                        }
                    }

                } catch(JSONException e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        // 서버로 Volley를 이용해서 요청을 함.
        RecommendUserRequest recommendUserRequest = new RecommendUserRequest(HomeActivity.userID, getresponseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(recommendUserRequest);
        



        Intent intent_r = getIntent();
        if(intent_r!=null){
            meal=intent_r.getStringExtra("meal");
            date=intent_r.getStringExtra("date");
            tv_AddFood.setText(date+" 식단추가");
        }
        //카메라로 갈 인텐트와 수기작성으로 갈 인텐트 선언
        intent_c= new Intent(Camera.this,Add_Camera.class);
        intent_h= new Intent(Camera.this,Add_HandWrite.class);
        //다음 엑티비티에도 날짜 정보 끼니 정보 전달
        //카메라 엑티비티
        intent_c.putExtra("meal",meal);
        intent_c.putExtra("date",date);
        //수기작성 엑티비티
        intent_h.putExtra("meal",meal);
        intent_h.putExtra("date",date);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            }
            else {
                Log.d(TAG, "권한 설정 요청"); requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }


        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        btn_handwrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent_h);
            }
        });


    }





    private void dispatchTakePictureIntent() {
        PackageManager pm= this.getPackageManager();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(pm) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            } if(photoFile != null) {
                //여기서부터 고쳐야함
                Uri photoURI = FileProvider.getUriForFile(this, "com.cookandroid.aifooddiaryapp.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 101);
            }
        }
    }



    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data); // 카메라 촬영을 하면 이미지뷰에 사진 삽입

        if(requestCode == 101 && resultCode == Activity.RESULT_OK) {
            /////////////////////////////////
            // 학습모델로 이미지 보내는 코드/////////
            /////////////////////////////////

            //사진 파일명을 다음 엑티비티로 넘겨준다
            intent_c.putExtra("file_path",mCurrentPhotoPath);
            intent_c.putExtra("flag",flag);


            //사진촬영이 완료되었을 경우 Add_Camera로 이동
            startActivity(intent_c);



        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = date+meal;
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile( imageFileName, ".jpg", storageDir );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

}