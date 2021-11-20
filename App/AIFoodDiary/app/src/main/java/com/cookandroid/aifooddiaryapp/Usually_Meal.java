package com.cookandroid.aifooddiaryapp;

import java.util.ArrayList;

public class Usually_Meal {
    //식단 이름
    private String name, push_name = "";
    private String names[];
    private String temp[];
    private ArrayList<String> listname;

    private int index;

    //생성자
    public Usually_Meal() {
    }

    public Usually_Meal(String name) {
        this.name = name;
    }
    // getter,setter 생성
    public String getName() {
        return name;
    }

    public String getPush_name() {
        int arrLength = names.length;

        for(int i = 0; i < arrLength; i++) {
            if(i == arrLength-1) {
                push_name = push_name + names[i];
            } else if(i == 0) {
                push_name = names[i] + ",";
            } else
                push_name = push_name + names[i] + ",";
        }

        return push_name;
    }

    public void setNames(String []names) {this.names = names;}

    public void setRemove(String name) {
        listname = new ArrayList<String>(names.length - 1);
        for(int i = 0; i < names.length; i++) {
            if(!names[i].equals(name)) {
                listname.add(names[i]);
            }
        }
        names = new String[listname.size()];

        for(int i = 0; i < names.length; i++) {
            names[i] = listname.get(i);
        }

    }

    public void add_names(String n) {
        listname = new ArrayList<String>(names.length + 1);
        listname.clear();

        for(int i = 0; i < names.length; i++)
            listname.add(names[i]);

        listname.add(n);

        names = new String[listname.size()];
        for(int i = 0; i < listname.size(); i++) {
            names[i] = listname.get(i);
        }
    }

    public void setPush_name(String push_name) {this.push_name =  push_name;}

}
