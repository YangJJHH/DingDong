package com.cookandroid.aifooddiaryapp;

public class Usually_Meal {
    private String name; //식단 이름
    private int kacl; //칼로리
    private int carbohydrate; //탄수화물
    private int protein; //단백질
    private int fat; //지방

    //생성자
    public Usually_Meal(String name, int kacl, int carbohydrate, int protein, int fat) {
        this.name = name;
        this.kacl = kacl;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
    }
    // getter,setter 생성
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKacl() {
        return kacl;
    }

    public void setKacl(int kacl) {
        this.kacl = kacl;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }
}
