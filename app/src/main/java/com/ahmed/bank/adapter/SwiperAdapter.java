package com.ahmed.bank.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ahmed.bank.R;
import com.ahmed.bank.Type;

import java.util.ArrayList;

public class SwiperAdapter extends PagerAdapter {

    LayoutInflater layoutInflater;
    private Context context;
    ArrayList<Type> arrayList = new ArrayList<>();

    public SwiperAdapter(Context context, ArrayList<Type> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {

        return (view == (LinearLayout) o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.itemviewpager, container, false);
        ImageView img = (ImageView) view.findViewById(R.id.itemviewpager_img);
        img.setImageResource(arrayList.get(position).getImg());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);

    }
}



