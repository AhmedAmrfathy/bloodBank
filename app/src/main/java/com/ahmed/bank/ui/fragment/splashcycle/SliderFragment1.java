package com.ahmed.bank.ui.fragment.splashcycle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ahmed.bank.R;
import com.ahmed.bank.Type;
import com.ahmed.bank.adapter.SwiperAdapter;
import com.ahmed.bank.ui.activity.UserCycleActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class SliderFragment1 extends Fragment {

Unbinder unbinder;
    @BindView(R.id.fragment_slider_faragment_viewpager)
    ViewPager fragmentSliderFaragmentViewpager;
    @BindView(R.id.fragment_slider_btn_skip)
    Button fragmentSliderBtnSkip;
    @BindView(R.id.fragment_slider_linearlayout)
    LinearLayout fragmentSliderLinearlayout;
    private int[] arrofimg = {R.drawable.ic_slider, R.drawable.ic_slider_b};
    ArrayList<Type> list = new ArrayList<>();

    public SliderFragment1() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slider_fragment1, container, false);
        unbinder = ButterKnife.bind(this, view);
        for (int i = 0; i < arrofimg.length; i++) {
            list.add(new Type(arrofimg[i]));
        }
        SwiperAdapter adapt = new SwiperAdapter(getContext(), list);
        fragmentSliderFaragmentViewpager.setAdapter(adapt);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fragment_slider_btn_skip)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), UserCycleActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }
}
