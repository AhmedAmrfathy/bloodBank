package com.ahmed.bank.ui.fragment.navigationcycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.bank.R;
import com.ahmed.bank.adapter.GridRecyclerviewAdaptergovernates;
import com.ahmed.bank.adapter.GridRecylerviewAdapter;
import com.ahmed.bank.data.local.SharedPreferencesManger;
import com.ahmed.bank.data.model.generalResponse.GeneralResponse;
import com.ahmed.bank.data.model.generalResponse.GeneralResponseData;
import com.ahmed.bank.data.rest.ApiServices;
import com.ahmed.bank.data.rest.RetrofitClient;
import com.ahmed.bank.helper.HelperMethod;

import java.util.ArrayList;
import java.util.List;

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
public class NotificationSettings extends Fragment {


    @BindView(R.id.NotificationSettings_fragment_img_back)
    ImageView NotificationSettingsFragmentImgBack;
    @BindView(R.id.NotificationSettings_fragment_tv_aboutapp)
    TextView NotificationSettingsFragmentTvAboutapp;
    @BindView(R.id.NotificationSettings_fragment_rl_header)
    RelativeLayout NotificationSettingsFragmentRlHeader;
    @BindView(R.id.NotificationSettings_fragment_tv_anytext)
    TextView NotificationSettingsFragmentTvAnytext;
    @BindView(R.id.NotificationSettings_fragment_rec_bloodtypes)
    RecyclerView NotificationSettingsFragmentRecBloodtypes;
    @BindView(R.id.NotificationSettings_fragment_rec_governates)
    RecyclerView NotificationSettingsFragmentRecGovernates;
    @BindView(R.id.NotificationSettings_fragment_btn_save)
    Button NotificationSettingsFragmentBtnSave;
    Unbinder unbinder;
    private ApiServices apiServices;
    private LinearLayoutManager linearLayoutManager;
    private List<String> listofgoernwillchange;
    private List<String> listofbloodtypewillchange;

    private GridRecyclerviewAdaptergovernates adapter;
    private GridRecyclerviewAdaptergovernates adapterr;


    // private List<Datum>bloodlist;
    // private List<GeneralResponseData>governlist;

    public NotificationSettings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        getnotificationsettings();

        return view;
    }

    private void getnotificationsettings() {
        apiServices.getnotificationsettings(SharedPreferencesManger.LoadData(getActivity(), "api_token")).enqueue(new Callback<com.ahmed.bank.data.model.NotificationSettings.NotificationSettings>() {
            @Override
            public void onResponse(Call<com.ahmed.bank.data.model.NotificationSettings.NotificationSettings> call, Response<com.ahmed.bank.data.model.NotificationSettings.NotificationSettings> response) {
                if (response.body().getStatus() == 1) {
                    final List<String> oldselectedlistofbloodtypes = new ArrayList<>();
                    final List<String> oldselectedlistofgovernates = new ArrayList<>();
                    oldselectedlistofbloodtypes.addAll(response.body().getData().getBloodTypes());
                    oldselectedlistofgovernates.addAll(response.body().getData().getGovernorates());

                    getgovernorate(oldselectedlistofgovernates);

                    getbloodtype(oldselectedlistofbloodtypes);

                }
            }

            @Override
            public void onFailure(Call<com.ahmed.bank.data.model.NotificationSettings.NotificationSettings> call, Throwable t) {

            }
        });
    }

    private void getgovernorate(final List<String> oldselectedlistofgovernates) {
        apiServices.getgovernorate().enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.body().getStatus() == 1) {
                    List<GeneralResponseData> governlist = new ArrayList<>();
                    governlist.addAll(response.body().getData());
                    NotificationSettingsFragmentRecGovernates.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    adapterr = new GridRecyclerviewAdaptergovernates(governlist, oldselectedlistofgovernates, getContext(), getActivity());
                    listofgoernwillchange = adapterr.selectedIds;
                    NotificationSettingsFragmentRecGovernates.setAdapter(adapterr);
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {

            }
        });

    }

    private void getbloodtype(final List<String> oldselectedlistofbloodtypes) {
        apiServices.getbloodtype().enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.body().getStatus() == 1) {
                    List<GeneralResponseData> bloodlist = new ArrayList<>();
                    bloodlist.addAll(response.body().getData());
                    NotificationSettingsFragmentRecBloodtypes.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    adapter = new GridRecyclerviewAdaptergovernates(bloodlist, oldselectedlistofbloodtypes, getContext(), getActivity());
                    listofbloodtypewillchange = adapter.selectedIds;
                    NotificationSettingsFragmentRecBloodtypes.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {

            }
        });
    }

//    private void initrecyclerviewofgovernates() {
//
//        apiServices.getgovernorate().enqueue(new Callback<GeneralResponse>() {
//            @Override
//            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
//                if (response.body().getStatus()==1){
//                    List<GeneralResponseData>governlist=new ArrayList<>();
//                    governlist.addAll(response.body().getData());
//                    NotificationSettingsFragmentRecGovernates.setLayoutManager(new GridLayoutManager(getContext(),3));
//                    GridRecyclerviewAdaptergovernates adapter=new GridRecyclerviewAdaptergovernates(governlist,getContext(),getActivity());
//                    NotificationSettingsFragmentRecGovernates.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GeneralResponse> call, Throwable t) {
//
//            }
//        });
//
//    }
//
//    private void initrecyclerviewofbloodtype() {
//
//        apiServices.getbloodtype().enqueue(new Callback<GeneralResponse>() {
//            @Override
//            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
//                if(response.body().getStatus()==1){
//                    List<GeneralResponseData>bloodlist=new ArrayList<>();
//                    bloodlist.addAll(response.body().getData());
//                    NotificationSettingsFragmentRecBloodtypes.setLayoutManager(new GridLayoutManager(getContext(),3));
//                    GridRecylerviewAdapter adapter=new GridRecylerviewAdapter(bloodlist,getContext(),getActivity(),apiServices);
//                    NotificationSettingsFragmentRecBloodtypes.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GeneralResponse> call, Throwable t) {
//
//            }
//        });
//
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.NotificationSettings_fragment_btn_save)
    public void onViewClicked() {
        listofgoernwillchange = adapterr.selectedIds;
        listofbloodtypewillchange = adapter.selectedIds;
        apiServices.sendnotificationchanged(SharedPreferencesManger.LoadData(getActivity(), "api_token")
                , listofgoernwillchange, listofbloodtypewillchange).enqueue(new Callback<com.ahmed.bank.data.model.NotificationSettings.NotificationSettings>() {
            @Override
            public void onResponse(Call<com.ahmed.bank.data.model.NotificationSettings.NotificationSettings> call, Response<com.ahmed.bank.data.model.NotificationSettings.NotificationSettings> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(getContext(), "Successfully saved changed", Toast.LENGTH_SHORT).show();
                    Repair repair = new Repair();
                    HelperMethod.replac(repair, getFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);
                }

            }

            @Override
            public void onFailure(Call<com.ahmed.bank.data.model.NotificationSettings.NotificationSettings> call, Throwable t) {

            }
        });
    }
}
