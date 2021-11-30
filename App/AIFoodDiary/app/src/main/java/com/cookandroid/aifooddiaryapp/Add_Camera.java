package com.cookandroid.aifooddiaryapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    String mCurrentPhotoPath = "",date,meal,food_name,flag;
    int index;
    ProgressBar prgbar_calorie, prgbar_protein, prgbar_carbohydrate, prgbar_fat;
    int today_calorie, today_carbohydrate, today_protein, today_fat;            // 오늘 먹어야할 것들 : today_0000
    int current_calorie = 0, current_carbohydrate = 0, current_protein = 0, current_fat = 0;    // 현재 먹은 수치 : current_0000
    TextView tv_current_calorie, tv_current_protein, tv_current_carbohydrate, tv_current_fat;

    // 음식 정보 변수!!!!!!!!!
    String foodSize, foodCarbo, foodProtein, foodFat, foodKcal;

    // 푸드 캘린더에 작성할 때 mealType 변수 필요 아침 : M, 점심 : L, 저녁 : D, 간식 : S
    String mealType = "";

    // DB에 넣을 음식 이름!!!!!
    String userMeal = "";
    //프로그래스바 업데이트 위해 날짜 확인 변수
    boolean date_Check;
    //프로그래스바 정보 임시저장
    //오늘
    int tmp[]={Frag_Home.current_calorie,Frag_Home.current_carbohydrate,Frag_Home.current_protein,Frag_Home.current_fat};
    //오늘 아닌경우
    int  c[]={0,0,0,0};
    // 음식 사진 서버에 올릴 변수들 선언
    //
    //
    String encodeImageString;
    private static final String url = "http://15.164.88.236/InsertPhotoInDB.php";

    //카드뷰에 있는 영양정보 수치 저장할 배열
    int card_infoInt[][]={{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};

    AppCompatDialog progressDialog;

    // DB에서 푸드 정보 가져오는 메소드
    public void getFoodInfo(String food_name,int index) {
        // 음식 정보 가져옴
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


                    //카드뷰 정보입력
                    String info="음식이름: "+food_name +"\n\n1회 제공량: " +foodSize+"g"+"\n\n칼로리: " + foodKcal+"Kcal"+"\n\n탄수화물: " + foodCarbo+"g"+"\n\n단백질: " + foodProtein+"g"+"\n\n지방: " + foodFat+"g";
                    tv_food_info[index].setText(info);
                    //
                    cv_food[index].setVisibility(View.VISIBLE);
                    //날짜 확인
                    int kcal=(int)Math.floor(Double.parseDouble(foodKcal));
                    int carbo=(int)Math.floor(Double.parseDouble(foodCarbo));
                    int protein=(int)Math.floor(Double.parseDouble(foodProtein));
                    int fat=(int)Math.floor(Double.parseDouble(foodFat));
                    if(date_Check){
                        //프로그래스바 설정을 위한 임시데이터 저장
                        tmp[0]+=kcal;
                        tmp[1]+=carbo;
                        tmp[2]+=protein;
                        tmp[3]+=fat;

                    }
                    else{
                        c[0]+=kcal;
                        c[1]+=carbo;
                        c[2]+=protein;
                        c[3]+=fat;

                    }
                    //나중에 카드뷰 삭제할때 프로그래스바에서도 수정해줘야 하기 때문에 수치 저장
                    card_infoInt[index][0]=kcal;
                    card_infoInt[index][1]=carbo;
                    card_infoInt[index][2]=protein;
                    card_infoInt[index][3]=fat;
                    setProgressBar(date_Check);
                    if(userMeal.equals("")){
                        userMeal+=food_name;
                    }else{
                        userMeal+=(","+food_name);
                    }

                } catch(JSONException e) {
                    Toast.makeText(getApplicationContext(), "잘못된 음식 이름입니다. 다시 추가해 주세요", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }
        };
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
    //프로그래스바 설정 함수
    public void setProgressBar(boolean date_Check){
        // 프로그래스바 설정해줌
        prgbar_calorie.setMax(Frag_Home.today_calorie);
        prgbar_carbohydrate.setMax(Frag_Home.today_carbohydrate);
        prgbar_protein.setMax(Frag_Home.today_protein);
        prgbar_fat.setMax(Frag_Home.today_fat);
        //오늘 날짜인경우
        if(date_Check){
            //현재 정보 프로그래스바에 표시
            prgbar_calorie.setProgress(tmp[0]);
            prgbar_carbohydrate.setProgress(tmp[1]);
            prgbar_protein.setProgress(tmp[2]);
            prgbar_fat.setProgress(tmp[3]);
            // 현재 유저의 권장 섭취량 정보를 TextView에 표시해줌
            tv_current_calorie.setText(tmp[0] + " / " + Frag_Home.today_calorie + " kcal");
            tv_current_carbohydrate.setText(tmp[1] + " / " + Frag_Home.today_carbohydrate + " g");
            tv_current_protein.setText(tmp[2] + " / " + Frag_Home.today_protein + " g");
            tv_current_fat.setText(tmp[3] + " / " + Frag_Home.today_fat + " g");
        }
        //오늘 날짜가 아닌경우
        else{
            //현재 정보 프로그래스바에 표시
            prgbar_calorie.setProgress(c[0]);
            prgbar_carbohydrate.setProgress(c[1]);
            prgbar_protein.setProgress(c[2]);
            prgbar_fat.setProgress(c[3]);
            // 현재 유저의 권장 섭취량 정보를 TextView에 표시해줌
            tv_current_calorie.setText(c[0] + " / " + Frag_Home.today_calorie + " kcal");
            tv_current_carbohydrate.setText(c[1] + " / " + Frag_Home.today_carbohydrate + " g");
            tv_current_protein.setText(c[2] + " / " + Frag_Home.today_protein + " g");
            tv_current_fat.setText(c[3] + " / " + Frag_Home.today_fat + " g");

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_camera);
        img_food_photo=(ImageView)findViewById(R.id.img_food_photo);
        btn_add = findViewById(R.id.btn_add);
        cv_add_food=findViewById(R.id.cv_add_food);
        tv_AddFood=findViewById(R.id.tv_AddFood);

        // 프로그래스바 위젯 변수 위젯 id 연결
        prgbar_calorie = (ProgressBar)findViewById(R.id.prgbar_calorie);
        prgbar_carbohydrate = (ProgressBar)findViewById(R.id.prgbar_carbohydrate);
        prgbar_protein = (ProgressBar)findViewById(R.id.prgbar_protein);
        prgbar_fat = (ProgressBar)findViewById(R.id.prgbar_fat);

        // 텍스트뷰 위젯 변수 위젯 id 연결
        tv_current_calorie = (TextView)findViewById(R.id.tv_currentcalorie);
        tv_current_carbohydrate = (TextView)findViewById(R.id.tv_currentcarbohydrate);
        tv_current_protein = (TextView)findViewById(R.id.tv_currentprotein);
        tv_current_fat = (TextView)findViewById(R.id.tv_currentfat);
        //현재 날짜 확인
        Date today= new Date();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(today);

        //이전 엑티비티에서 받아온 데이터 받아오기
        Intent intent_r=getIntent();
        if(intent_r!=null){
            mCurrentPhotoPath = intent_r.getStringExtra("file_path");
            meal=intent_r.getStringExtra("meal");
            date=intent_r.getStringExtra("date");
            food_name=intent_r.getStringExtra("food_name");
            flag=intent_r.getStringExtra("flag");
            tv_AddFood.setText(date+"식단추가");
            if(mCurrentPhotoPath == null) {
                mCurrentPhotoPath = "";
            }
            if(date.equals(currentDate)){
                date_Check=true;
            }else{
                date_Check=false;
            }
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
                    //프로그래스바 업데이트
                    if(date_Check){
                        tmp[0]-=card_infoInt[index][0];
                        tmp[1]-=card_infoInt[index][1];
                        tmp[2]-=card_infoInt[index][2];
                        tmp[3]-=card_infoInt[index][3];
                    }
                    else{
                        c[0]-=card_infoInt[index][0];
                        c[1]-=card_infoInt[index][1];
                        c[2]-=card_infoInt[index][2];
                        c[3]-=card_infoInt[index][3];
                    }
                    card_infoInt[index][0]=0;
                    card_infoInt[index][1]=0;
                    card_infoInt[index][2]=0;
                    card_infoInt[index][3]=0;
                    setProgressBar(date_Check);
                    userMeal=rst;
                }
            });
        }

        //식단기록 버튼 리스너
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 만약 음식이 없는데 기록 버튼을 눌렀다면 기록이 안 되게 해야함
                if(food_name == null || food_name == "") {
                    Toast.makeText(getApplicationContext(), "음식을 추가하고 기록해주세요!", Toast.LENGTH_LONG).show();
                } else {
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
                                JSONObject setjsonObject = new JSONObject(response);

                                boolean success = setjsonObject.getBoolean("success");


                                if(success) {
                                    Toast.makeText(getApplicationContext(), "식단을 정상적으로 등록하였습니다.", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(Add_Camera.this, HomeActivity.class);
                                    intent.putExtra("userID", HomeActivity.userID);
                                    startActivity(intent);

                                } else
                                    Toast.makeText(getApplicationContext(), "식단을 등록하는데 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();

                            } catch(Exception e) {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

                                e.printStackTrace();
                            }
                        }
                    };
                    if(date_Check){
                        Frag_Home.current_calorie+=tmp[0];
                        Frag_Home.current_carbohydrate+=tmp[1];
                        Frag_Home.current_protein+=tmp[2];
                        Frag_Home.current_fat+=tmp[3];
                    }

                    // 서버로 Volley를 이용해서 요청을 함.
                    // String userID, String mealDate, String mealType, String userMeal, String mealPhoto, listener
                    FoodCalendar_SetRequest foodCalendarSetRequest = new FoodCalendar_SetRequest(HomeActivity.userID, date, mealType, userMeal, mCurrentPhotoPath, setresponseListener);
                    RequestQueue queue = Volley.newRequestQueue(Add_Camera.this);
                    queue.add(foodCalendarSetRequest);

                }


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
            uploadDataToDB();
        }
        else{
            //수기추가로부터 왔을경우
            addFood();

        }


    }   // onCreate 메소드 종료 부분

    // Activity를 반환해주는 메소드 (로딩 화면 실행시 필요)
    public Activity myActivity() {
        return this;
    }

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
                if (bitmap != null) {
                    img_food_photo.setImageBitmap(bitmap);
                    encodeBitmapImage(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(file));
                if (bitmap != null) {
                    img_food_photo.setImageBitmap(bitmap);
                    encodeBitmapImage(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // DB에 파일 올릴 메소드
    public void uploadDataToDB() {
        final String name = date;
        progressON(myActivity(), "Loading...");

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    // 음식 이름과 음식 인덱스 가져옴
                    String food_info = jsonObject.getString("food_info");
                    int in = jsonObject.getInt("index");

                    // 가져온 음식 이름은 ,로 구분지어져 있기 때문에 스플릿 해줌
                    String food[] = food_info.split(",");

                    if(in != 0) {
                        //음식 정보는 6개씩 나눠서 들어감
                        in = in / 6;
                        // 인덱스가 0이 아니면 가져온 정보 출력
                        for(int i = 0; i < in; i++) {
                            // 가중치 n 변수 선언
                            int n = 6;
                            n = n * i;
                            food_name = food[(i * n)];
                            foodSize = food[(i * n) + 1];
                            foodCarbo = food[(i * n) + 2];
                            foodProtein = food[(i * n) + 3];
                            foodFat = food[(i * n) + 4];
                            foodKcal = food[(i * n) + 5];

                            String info="음식이름: " + food_name +"\n\n1회 제공량: " +foodSize+"g"+"\n\n칼로리: " + foodKcal+"Kcal"+"\n\n탄수화물: " + foodCarbo+"g"+"\n\n단백질: " + foodProtein+"g"+"\n\n지방: " + foodFat+"g";
                            tv_food_info[index].setText(info);
                            cv_food[index].setVisibility(View.VISIBLE);
                            //날짜 확인
                            int kcal=(int)Math.floor(Double.parseDouble(foodKcal));
                            int carbo=(int)Math.floor(Double.parseDouble(foodCarbo));
                            int protein=(int)Math.floor(Double.parseDouble(foodProtein));
                            int fat=(int)Math.floor(Double.parseDouble(foodFat));
                            if(date_Check){
                                //프로그래스바 설정을 위한 임시데이터 저장
                                tmp[0]+=kcal;
                                tmp[1]+=carbo;
                                tmp[2]+=protein;
                                tmp[3]+=fat;

                            }
                            else{
                                c[0]+=kcal;
                                c[1]+=carbo;
                                c[2]+=protein;
                                c[3]+=fat;

                            }
                            //나중에 카드뷰 삭제할때 프로그래스바에서도 수정해줘야 하기 때문에 수치 저장
                            card_infoInt[index][0]=kcal;
                            card_infoInt[index][1]=carbo;
                            card_infoInt[index][2]=protein;
                            card_infoInt[index][3]=fat;
                            setProgressBar(date_Check);
                            if(userMeal.equals("")){
                                userMeal+=food_name;
                            }else{
                                userMeal+=(","+food_name);
                            }
                            index++;
                        }

                    } else {
                        // 인식된 음식이 없는 경우임
                        Toast.makeText(getApplicationContext(), "사진에 인식된 음식이 없습니다.\n+ 버튼을 눌러 수기로 작성해주세요.", Toast.LENGTH_LONG).show();
                    }

                    // 로딩 화면 종료
                    progressOFF();

                } catch (Exception e) {
                    // 오류가 나면 해당 오류 내용 알려주고 로딩 끝나게 함
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    progressOFF();
                }


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

            @Override
            public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
                return super.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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


    // 로딩화면 다이얼로그 생성
    public void progressON(Activity activity, String message) {

        if (activity == null || activity.isFinishing()) {
            return;
        }


        if (progressDialog != null && progressDialog.isShowing()) {
            progressSET(message);
        } else {

            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.loading);
            progressDialog.show();

        }


        final ImageView img_loading_frame = (ImageView) progressDialog.findViewById(R.id.iv_frame_loading);
        final AnimationDrawable frameAnimation = (AnimationDrawable) img_loading_frame.getBackground();
        img_loading_frame.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });

        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }


    }

    // 프로그래스 set 함수
    public void progressSET(String message) {

        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }

        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }

    }

    // 프로그래스 종료 함수
    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


}