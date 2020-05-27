package com.ahmed.bank.ui.fragment.splashcycle;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmed.bank.R;
import com.ahmed.bank.helper.HelperMethod;


public class SplashFragment extends Fragment {


    private long SPLASH_DISPLAY_LENGTH=3000;

    public SplashFragment() {
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
        View view=inflater.inflate(R.layout.fragment_splash, container, false);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SliderFragment1 sliderFragment1=new SliderFragment1();
                HelperMethod.replac(sliderFragment1,getActivity().getSupportFragmentManager(),R.id.splash_cycle_activity_fl_container,null,null);
                /* Create an Intent that will start the Menu-Activity. */

            }
        }, SPLASH_DISPLAY_LENGTH);


        return view;
    }


}
