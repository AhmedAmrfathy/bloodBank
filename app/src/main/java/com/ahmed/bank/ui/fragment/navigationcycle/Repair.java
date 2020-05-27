package com.ahmed.bank.ui.fragment.navigationcycle;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.bank.R;
import com.ahmed.bank.adapter.PagerAdapter;
import com.ahmed.bank.data.local.SharedPreferencesManger;
import com.ahmed.bank.data.model.Notifcount.Notifcount;
import com.ahmed.bank.data.rest.ApiServices;
import com.ahmed.bank.data.rest.RetrofitClient;
import com.ahmed.bank.helper.HelperMethod;
import com.ahmed.bank.ui.fragment.NotificationList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Repair extends Fragment {


    @BindView(R.id.Fragment_tb_head)
    TabLayout FragmentTbHead;
    @BindView(R.id.Fragment_vp_vp)
    ViewPager FragmentVpVp;
    @BindView(R.id.Fragment_fl_container)
    FrameLayout FragmentFlContainer;
    Unbinder unbinder;
    @BindView(R.id.tlatshort)
    AppCompatImageView tlatshort;
    DrawerLayout drawerLayout;
    @BindView(R.id.notif)
    AppCompatImageView notif;
    @BindView(R.id.Repair_fragment_tv_notifcount)
    TextView RepairFragmentTvNotifcount;
    private ApiServices apiServices;

    public Repair() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repair, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices= RetrofitClient.getClient().create(ApiServices.class);
        Getnonifcount();
        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.activity_cycle_navigationdrawer_img_logo);
        setupviewpager(FragmentVpVp);
        FragmentTbHead.setupWithViewPager(FragmentVpVp);

        return view;
    }

    private void Getnonifcount() {
        try{
        apiServices.getnotifcount(SharedPreferencesManger.LoadData(getActivity(),"api_token")).enqueue(new Callback<Notifcount>() {
            @Override
            public void onResponse(Call<Notifcount> call, Response<Notifcount> response) {
                if (response.body().getStatus()==1){
                    if (response.body().getData().getNotificationsCount().equals(0)){
                    }
                    else {
                        RepairFragmentTvNotifcount.setBackgroundResource(R.drawable.circlenotifcount);
                        RepairFragmentTvNotifcount.setText(String.valueOf(response.body().getData().getNotificationsCount()));
                    }
                }
            }

            @Override
            public void onFailure(Call<Notifcount> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT);
            }
        });}catch (Exception e){}
    }

    private void setupviewpager(ViewPager viewPager) {
        PagerAdapter pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        pagerAdapter.addfragment(new PostFragment(), "Posts");
        pagerAdapter.addfragment(new DonationFragment(), "Donation reguests");
        viewPager.setAdapter(pagerAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    @OnClick(R.id.tlatshort)
    public void onViewClicked() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @OnClick(R.id.notif)
    public void onViewClickedd() {
        NotificationList notificationList = new NotificationList();
        HelperMethod.replac(notificationList, getFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);
    }

    @OnClick(R.id.Repair_fragment_tv_notifcount)
    public void onViewClickeddd() {
        NotificationList notificationList = new NotificationList();
        HelperMethod.replac(notificationList, getFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);

    }
}
