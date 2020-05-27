package com.ahmed.bank.ui.fragment.navigationcycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ahmed.bank.R;
import com.ahmed.bank.adapter.DonationsAdapter;
import com.ahmed.bank.data.local.SharedPreferencesManger;
import com.ahmed.bank.data.model.Donations.Donation;
import com.ahmed.bank.data.model.Donations.Donations;
import com.ahmed.bank.data.model.generalResponse.GeneralResponse;
import com.ahmed.bank.data.rest.ApiServices;
import com.ahmed.bank.data.rest.RetrofitClient;
import com.ahmed.bank.helper.HelperMethod;
import com.ahmed.bank.ui.fragment.AddDonation;

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
public class DonationFragment extends Fragment {


    @BindView(R.id.DonationFragment_spn_bloodtype)
    AppCompatSpinner DonationFragmentSpnBloodtype;
    @BindView(R.id.DonationFragment_rellay_1)
    RelativeLayout DonationFragmentRellay1;
    @BindView(R.id.DonationFragment_spn_city)
    AppCompatSpinner DonationFragmentSpnCity;
    @BindView(R.id.DonationFragment_rellay_2)
    RelativeLayout DonationFragmentRellay2;
    @BindView(R.id.DonationFragment_rc_reguests)
    RecyclerView DonationFragmentRcReguests;
    DonationsAdapter donationsAdapter;
    Unbinder unbinder;
    List<Donation> donationdatalist = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    ApiServices apiServices;
    @BindView(R.id.DonationFragment_img_searchdonation)
    ImageView DonationFragmentImgSearchdonation;
    @BindView(R.id.DonationFragment_img_adddonation)
    ImageView DonationFragmentImgAdddonation;
    private ArrayList<Integer> citiesid;
    private ArrayList<Integer> ids;
    public static int posit;
    public static int positi;

    public DonationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donation, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        initrecyclerview();
        getallbloodtype();
        getAlldonations(1);

        return view;
    }

    private void initrecyclerview() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        DonationFragmentRcReguests.setLayoutManager(linearLayoutManager);
        donationsAdapter = new DonationsAdapter(donationdatalist, getActivity(), getActivity());
        DonationFragmentRcReguests.setAdapter(donationsAdapter);
    }

    private void getAlldonations(int page) {
        try{
        apiServices.getalldonations(SharedPreferencesManger.LoadData(getActivity(), "api_token"), page).enqueue(new Callback<Donations>() {
            @Override
            public void onResponse(Call<Donations> call, Response<Donations> response) {
                if (response.body().getStatus() == 1) {
                    donationdatalist.addAll(response.body().getData().getData());
                    donationsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Donations> call, Throwable t) {

            }
        });}catch (Exception e){}
    }

    private void getcities(Integer i) {
        try{
        apiServices.getcities(i).enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.body().getStatus() == 1) {
                    ArrayList<String> name = new ArrayList<String>();
                    citiesid = new ArrayList<>();
                    name.add("City");
                    citiesid.add(0);
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        name.add(response.body().getData().get(i).getName());
                        citiesid.add(response.body().getData().get(i).getId());
                    }
                    ArrayAdapter<String> spinnerData = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, name);
                    DonationFragmentSpnCity.setAdapter(spinnerData);
                    DonationFragmentSpnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                            }
                            positi = citiesid.get(position);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    citiesid.get(DonationFragmentSpnCity.getSelectedItemPosition());
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
            }
        });}catch (Exception e){}
    }

    private void getallbloodtype() {
        try{
        apiServices.getbloodtype().enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.body().getStatus() == 1) {
                    final ArrayList<String> name = new ArrayList<>();
                    ids = new ArrayList<>();
                    name.add("Blood Type");
                    ids.add(0);
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        name.add(response.body().getData().get(i).getName());
                        ids.add(response.body().getData().get(i).getId());
                    }
                    ArrayAdapter<String> spinnerData = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, name);
                    DonationFragmentSpnBloodtype.setAdapter(spinnerData);
                    DonationFragmentSpnBloodtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                            }
                            getcities(ids.get(position));
                            posit = ids.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {

            }
        });}catch (Exception e){}
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.DonationFragment_img_searchdonation)
    public void onViewClicked() {
        filter(1);
    }

    private void filter(int page) {
        try{
        apiServices.filterdonation(SharedPreferencesManger.LoadData(getActivity(), "api_token"), ids.get(posit), citiesid.get(positi), page).enqueue(
                new Callback<Donations>() {
                    @Override
                    public void onResponse(Call<Donations> call, Response<Donations> response) {
                        if (response.body().getStatus() == 1) {
                            donationdatalist.addAll(response.body().getData().getData());
                            donationsAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Donations> call, Throwable t) {
                    }
                }
        );}catch (Exception e){}
    }


    @OnClick(R.id.DonationFragment_img_adddonation)
    public void onViewClickedd() {
        AddDonation addDonation = new AddDonation();
        HelperMethod.replac(addDonation, getActivity().getSupportFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);
    }
}