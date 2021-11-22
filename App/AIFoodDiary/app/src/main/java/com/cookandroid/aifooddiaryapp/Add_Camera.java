package com.cookandroid.aifooddiaryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

public class Add_Camera extends AppCompatActivity {
    Bitmap bitmap;
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
    int index;

    // 음식 정보 변수!!!!!!!!!
    String foodK_name, foodSize, foodCarbo, foodProtein, foodFat, foodKcal;

    // 푸드 캘린더에 작성할 때 mealType 변수 필요 아침 : M, 점심 : L, 저녁 : D, 간식 : S
    String mealType = "";

    // DB에 넣을 음식 이름!!!!!
    String userMeal = "";

    // 음식 사진 서버에 올릴 변수들 선언
    //
    //
    String encodeImageString;
    private static final String url = "http://15.164.88.236/InsertPhotoInDB.php";


    // DB에서 푸드 정보 가져오는 메소드
    public void getFoodInfo(String food_name,int index) {
        // 기존 회원 정보 가져오는 과정 필요
        Response.Listener<String> getresponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject getjsonObject = new JSONObject(response);

                    foodSize = getjsonObject.getString("foodSize");
                    foodCarbo = getjsonObject.getString("foodCarbo");
                    foodProtein = getjsonObject.getString("foodProtein");
                    foodFat = getjsonObject.getString("foodFat");
                    foodKcal = getjsonObject.getString("foodKcal");


                    Toast.makeText(getApplicationContext(), foodSize+":1", Toast.LENGTH_SHORT).show();
                    //카드뷰 정보입력
                    String info="음식이름: "+food_name +"\n\n음식 섭취량: " +foodSize+"g"+"\n\n칼로리: " + foodKcal+"Kcal"+"\n\n탄수화물: " + foodCarbo+"g"+"\n\n단백질: " + foodProtein+"g"+"\n\n지방: " + foodFat+"g";
                    tv_food_info[index].setText(info);
                    //
                    cv_food[index].setVisibility(View.VISIBLE);
                    if(userMeal.equals("")){
                        userMeal+=food_name;
                    }else{
                        userMeal+=(","+food_name);
                    }

                } catch(JSONException e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }
        };
        Toast.makeText(getApplicationContext(), foodSize+":2", Toast.LENGTH_SHORT).show();
        // 서버로 Volley를 이용해서 요청을 함.
        FoodInfo_GetRequest foodInfoGetRequest = new FoodInfo_GetRequest(food_name, getresponseListener);
        RequestQueue queue = Volley.newRequestQueue(Add_Camera.this);
        queue.add(foodInfoGetRequest);


    }

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
                    String tmp1[]=userMeal.split(",");
                    String rst="";
                    int length=tmp1.length-1;
                    for(int i=0; i<length; i++){
                        if(i==length-1){
                            rst+=tmp1[i];
                        }else{
                            rst+=tmp1[i]+",";
                        }
                    }
                    userMeal=rst;
                }
            });
        }

        //식단기록 버튼 리스너
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(meal.equals("아침")) {
                    mealType = "M";
                } else if(meal.equals("점심")) {
                    mealType = "L";
                } else if(meal.equals("저녁")) {
                    mealType = "D";
                } else if(meal.equals("간식")) {
                    mealType = "S";
                }

                // 해당 회원의 식단 정보 DB에 저장
                Response.Listener<String> setresponseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject getjsonObject = new JSONObject(response);

                            boolean success = getjsonObject.getBoolean("success");

                            if(success) {
                                Toast.makeText(getApplicationContext(), "식단을 정상적으로 등록하였습니다.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Add_Camera.this, HomeActivity.class);
                                startActivity(intent);
                            } else
                                Toast.makeText(getApplicationContext(), "식단을 등록하는데 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();

                        } catch(JSONException e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                };

                // 서버로 Volley를 이용해서 요청을 함.
                // String userID, String mealDate, String mealType, String userMeal, String mealPhoto, listener
                FoodCalendar_SetRequest foodCalendarSetRequest = new FoodCalendar_SetRequest(HomeActivity.userID, date, mealType, userMeal, mCurrentPhotoPath,setresponseListener);
                RequestQueue queue = Volley.newRequestQueue(Add_Camera.this);
                queue.add(foodCalendarSetRequest);
                Toast.makeText(getApplicationContext(),userMeal,Toast.LENGTH_SHORT).show();
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
            modelRun();
            addFood();
        }
        else{
            //수기추가로부터 왔을경우
            addFood();

        }



    }   // onCreate 메소드 종료 부분

    public void addFood(){
        index=-1;
        for(int i=0; i<7; i++){
            if(cv_food[i].getVisibility()==View.GONE){
                index=i;
                break;
            }
        }
        getFoodInfo(food_name,index);
    }


    //이미지뷰에 음식사진 불러오기
    public void loadImage(){
        File file = new File(mCurrentPhotoPath);
        if (Build.VERSION.SDK_INT >= 29) {
            ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), Uri.fromFile(file));
            try {
                bitmap = ImageDecoder.decodeBitmap(source);
                Bitmap targetBmp = bitmap.copy(Bitmap.Config.ARGB_8888, false);
                if (bitmap != null) {
                    img_food_photo.setImageBitmap(bitmap);
                    encodeBitmapImage(bitmap);
                    uploadDataToDB();
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
                    encodeBitmapImage(bitmap);
                    uploadDataToDB();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // DB에 파일 올릴 메소드
    public void uploadDataToDB() {
        final String name = date;

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> map = new HashMap<>();
                // 1번 인자는 PHP 파일의 $_POST['']; 부분과 똑같이 해줘야 한다
                map.put("name", name);
                map.put("upload", encodeImageString);
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytesOfImage, Base64.DEFAULT);
    }



    public void modelRun(){}

}