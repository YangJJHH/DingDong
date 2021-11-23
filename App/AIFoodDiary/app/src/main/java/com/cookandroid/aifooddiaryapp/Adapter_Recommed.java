package com.cookandroid.aifooddiaryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_Recommed extends BaseAdapter {
    ArrayList<list_recommend_item> mItems;
    Context mContext;
    LayoutInflater inflater;

    public Adapter_Recommed( Context context, ArrayList<list_recommend_item> items) {
        this.mItems = new ArrayList<>();
        this.mItems.addAll(items);
        this.mContext = context;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setItem(ArrayList<list_recommend_item> items) {
        if (mItems != null) {
            mItems.clear();
            mItems.addAll(items);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class Holder {
        TextView tv_kcal, tv_carbon, tv_protein,tv_fat;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_recommend, parent, false);
            holder = new Holder();

            holder.tv_kcal = convertView.findViewById(R.id.tv_kcal);
            holder.tv_carbon = convertView.findViewById(R.id.tv_carbon);
            holder.tv_protein = convertView.findViewById(R.id.tv_protein);
            holder.tv_fat = convertView.findViewById(R.id.tv_fat);

            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }

        list_recommend_item item = mItems.get(position);

        if (item != null) {
            holder.tv_kcal.setText(item.getKacl());
            holder.tv_carbon.setText(item.getCarbo());
            holder.tv_protein.setText(item.getProtein());
            holder.tv_fat.setText(item.getFat());
        }

        return convertView;
    }
}
