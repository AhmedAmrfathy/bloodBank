package com.ahmed.bank.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ahmed.bank.R;
import com.ahmed.bank.adapter.NontificationListAdapter;
import com.ahmed.bank.data.local.SharedPreferencesManger;
import com.ahmed.bank.data.model.NotificationList.Datum;
import com.ahmed.bank.data.rest.ApiServices;
import com.ahmed.bank.data.rest.RetrofitClient;
import com.ahmed.bank.helper.HelperMethod;
import com.ahmed.bank.ui.fragment.navigationcycle.Repair;

import java.util.ArrayList;

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
public class NotificationList extends Fragment {
    @BindView(R.id.NotificationList_fragment_img_back)
    ImageView NotificationListFragmentImgBack;
    @BindView(R.id.NotificationList_fragment_tv_aboutapp)
    TextView NotificationListFragmentTvAboutapp;
    @BindView(R.id.NotificationList_fragment_rl_header)
    RelativeLayout NotificationListFragmentRlHeader;
    @BindView(R.id.NotificationList_fragment_rv_notilist)
    RecyclerView NotificationListFragmentRvNotilist;
    Unbinder unbinder;
    private ApiServices apiServices;
    private NontificationListAdapter adapter;


    public NotificationList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_list, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        getnotifictionlist();
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    private void getnotifictionlist() {
        try{
        apiServices.getlistofnotification(SharedPreferencesManger.LoadData(getActivity(), "api_token")).enqueue(new Callback<com.ahmed.bank.data.model.NotificationList.NotificationList>() {
            @Override
            public void onResponse(Call<com.ahmed.bank.data.model.NotificationList.NotificationList> call, Response<com.ahmed.bank.data.model.NotificationList.NotificationList> response) {
                if (response.body().getStatus() == 1) {
                    ArrayList<Datum> arrayList = new ArrayList<>();
                    arrayList.addAll(response.body().getData().getData());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    NotificationListFragmentRvNotilist.setLayoutManager(linearLayoutManager);
                    adapter = new NontificationListAdapter(getContext(), getActivity(), apiServices, arrayList);
                    NotificationListFragmentRvNotilist.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<com.ahmed.bank.data.model.NotificationList.NotificationList> call, Throwable t) {

            }
        });}catch (Exception E){}
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.NotificationList_fragment_img_back)
    public void onViewClicked() {
        Repair repair=new Repair();
        HelperMethod.replac(repair, getFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);
    }
}
