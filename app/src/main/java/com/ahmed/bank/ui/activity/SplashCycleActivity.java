package com.ahmed.bank.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ahmed.bank.R;
import com.ahmed.bank.helper.HelperMethod;
import com.ahmed.bank.ui.fragment.navigationcycle.PostFragment;
import com.ahmed.bank.ui.fragment.splashcycle.SplashFragment;

public class SplashCycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_splash);
        SplashFragment splashFragment=new SplashFragment();
        HelperMethod.replac(splashFragment,getSupportFragmentManager(),R.id.splash_cycle_activity_fl_container,null,null);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
