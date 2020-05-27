package com.ahmed.bank.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ahmed.bank.R;
import com.ahmed.bank.helper.HelperMethod;
import com.ahmed.bank.ui.fragment.usercycle.LoginFragment;

public class UserCycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cycle);
        LoginFragment loginFragment=new LoginFragment();
        HelperMethod.replac(loginFragment,getSupportFragmentManager(),R.id.activity_user_cycle_fl_container,null,null);



    }
}
