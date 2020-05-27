package com.ahmed.bank.ui.fragment.navigationcycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmed.bank.R;
import com.ahmed.bank.data.local.SharedPreferencesManger;
import com.ahmed.bank.data.model.AppSettings.AppSettings;
import com.ahmed.bank.data.rest.ApiServices;
import com.ahmed.bank.data.rest.RetrofitClient;
import com.ahmed.bank.helper.HelperMethod;

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
public class AboutApp extends Fragment {


    @BindView(R.id.AboutApp_fragment_img_back)
    ImageView AboutAppFragmentImgBack;
    @BindView(R.id.Fragment_AbouutApp_tv_content)
    TextView FragmentAbouutAppTvContent;
    Unbinder unbinder;
    private ApiServices apiServices;

    public AboutApp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about_app, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        getdata();
        return view;
    }

    private void getdata() {
        try {


            apiServices.getappinformation(SharedPreferencesManger.LoadData(getActivity(), "api_token")).enqueue(new Callback<AppSettings>() {
                @Override
                public void onResponse(Call<AppSettings> call, Response<AppSettings> response) {
                    if (response.body().getStatus() == 1) {
                        FragmentAbouutAppTvContent.setText(response.body().getData().getAboutApp());
                    }
                }

                @Override
                public void onFailure(Call<AppSettings> call, Throwable t) {

                }
            });
        }catch (Exception e){}


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.AboutApp_fragment_img_back)
    public void onViewClicked() {
        Repair repair=new Repair();
        HelperMethod.replac(repair,getActivity().getSupportFragmentManager(),R.id.activity_cycle_navigationdrawer_fl_container,null,null);
    }
}
