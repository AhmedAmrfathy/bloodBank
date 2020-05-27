package com.ahmed.bank.ui.activity;

import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.ahmed.bank.R;
import com.ahmed.bank.data.rest.ApiServices;
import com.ahmed.bank.helper.HelperMethod;
import com.ahmed.bank.ui.fragment.NotificationList;
import com.ahmed.bank.ui.fragment.navigationcycle.AboutApp;
import com.ahmed.bank.ui.fragment.navigationcycle.AboutMe_fragment;
import com.ahmed.bank.ui.fragment.navigationcycle.ContactUs;
import com.ahmed.bank.ui.fragment.navigationcycle.FavouriteFragment;
import com.ahmed.bank.ui.fragment.navigationcycle.NotificationSettings;
import com.ahmed.bank.ui.fragment.navigationcycle.Repair;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CycleNavigationdrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.activity_cycle_navigationdrawer_nv_view)
    NavigationView activityCycleNavigationdrawerNvView;
    @BindView(R.id.activity_cycle_navigationdrawer_img_logo)
   public   DrawerLayout activityCycleNavigationdrawerImgLogo;
    private ApiServices apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_navigationdrawer);
        ButterKnife.bind(this);
        activityCycleNavigationdrawerNvView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, activityCycleNavigationdrawerImgLogo, null, R.string.drawer_navigation_open, R.string.drawer_navigation_close);
        activityCycleNavigationdrawerImgLogo.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Repair repair = new Repair();
        HelperMethod.replac(repair, getSupportFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.drawer_menu_item_aboutme) {
            AboutMe_fragment aboutMe_fragment = new AboutMe_fragment();
            HelperMethod.replac(aboutMe_fragment, getSupportFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);
        }
        if (menuItem.getItemId() == R.id.drawer_menu_item_favourite) {
            FavouriteFragment favouriteFragment = new FavouriteFragment();
            HelperMethod.replac(favouriteFragment, getSupportFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);
        }
        if (menuItem.getItemId() == R.id.drawer_menu_item_aboutapp) {
            AboutApp aboutApp = new AboutApp();
            HelperMethod.replac(aboutApp, getSupportFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);
        }
        if (menuItem.getItemId() == R.id.drawer_menu_item_contact) {
            ContactUs contactUs = new ContactUs();
            HelperMethod.replac(contactUs, getSupportFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);
        }
        if (menuItem.getItemId() == R.id.drawer_menu_item_exit) {
            moveTaskToBack(true);
            Process.killProcess(Process.myPid());
            System.exit(1);
        }
        if (menuItem.getItemId() == R.id.drawer_menu_item_notification) {
            NotificationSettings notificationSettings = new NotificationSettings();
            HelperMethod.replac(notificationSettings, getSupportFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);
        }


        activityCycleNavigationdrawerImgLogo.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (activityCycleNavigationdrawerImgLogo.isDrawerOpen(GravityCompat.START)) {
            activityCycleNavigationdrawerImgLogo.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }



}