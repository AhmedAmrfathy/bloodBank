package com.ahmed.bank.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.bank.R;
import com.ahmed.bank.data.local.SharedPreferencesManger;
import com.ahmed.bank.data.model.Notifcount.Notifcount;
import com.ahmed.bank.data.model.generalResponse.GeneralResponse;
import com.ahmed.bank.data.rest.ApiServices;
import com.ahmed.bank.data.rest.RetrofitClient;
import com.ahmed.bank.helper.HelperMethod;
import com.ahmed.bank.ui.activity.MapsActivity;
import com.ahmed.bank.ui.fragment.navigationcycle.Repair;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;
import static com.ahmed.bank.ui.activity.MapsActivity.latitude;
import static com.ahmed.bank.ui.activity.MapsActivity.longtiute;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDonation extends Fragment {

    @BindView(R.id.Favourite_img_back)
    ImageView FavouriteImgBack;
    @BindView(R.id.Favourite_tv_fav)
    TextView FavouriteTvFav;
    @BindView(R.id.fragment_adddonation_tv_createnewaccount)
    RelativeLayout fragmentAdddonationTvCreatenewaccount;
    @BindView(R.id.fragment_adddonation_ed_name)
    TextInputEditText fragmentAdddonationEdName;
    @BindView(R.id.fragment_adddonation_til_name)
    TextInputLayout fragmentAdddonationTilName;
    @BindView(R.id.fragment_adddonation_ed_email)
    TextInputEditText fragmentAdddonationEdEmail;
    @BindView(R.id.fragment_adddonation_til_email)
    TextInputLayout fragmentAdddonationTilEmail;
    @BindView(R.id.fragment_adddonation_ed_dateofbirth)
    TextView fragmentAdddonationEdDateofbirth;
    @BindView(R.id.fragment_register_til_dateofbirth)
    RelativeLayout fragmentRegisterTilDateofbirth;
    @BindView(R.id.fragment_adddonation_spiner_bloodtype)
    AppCompatSpinner fragmentAdddonationSpinerBloodtype;
    @BindView(R.id.fragment_adddonation_ed_hospitalname)
    TextInputEditText fragmentAdddonationEdHospitalname;
    @BindView(R.id.fragment_adddonation_til_hospitalname)
    TextInputLayout fragmentAdddonationTilHospitalname;
    @BindView(R.id.fragment_adddonation_tv_hospitaladdress)
    EditText fragmentAdddonationTvHospitaladdress;
    @BindView(R.id.fragment_adddonation_img_hospitaladdress)
    ImageView fragmentAdddonationImgHospitaladdress;
    @BindView(R.id.fragment_adddonation_til_hospitaladdress)
    RelativeLayout fragmentAdddonationTilHospitaladdress;
    @BindView(R.id.fragmentadddonationSpinerGovernorate)
    AppCompatSpinner fragmentadddonationSpinerGovernorate;
    @BindView(R.id.fragmentadddonationSpinerCityid)
    AppCompatSpinner fragmentadddonationSpinerCityid;
    @BindView(R.id.fragment_adddonation_ed_phonenumber)
    TextInputEditText fragmentAdddonationEdPhonenumber;
    @BindView(R.id.fragment_adddonation_til_phonenumber)
    TextInputLayout fragmentAdddonationTilPhonenumber;
    @BindView(R.id.fragment_adddonation_tv_Notice)
    TextInputEditText fragmentAdddonationTvNotice;
    @BindView(R.id.fragment_adddonation_til_Notice)
    TextInputLayout fragmentAdddonationTilNotice;
    @BindView(R.id.fragment_adddonation_btn_create)
    Button fragmentAdddonationBtnCreate;
    @BindView(R.id.fragmentadddonation_ed_numberofcys)
    EditText fragmentadddonationEdNumberofcys;
    Unbinder unbinder;
    @BindView(R.id.AddDonation_fragment_tv_notifcount)
    TextView AddDonationFragmentTvNotifcount;


    private ApiServices apiServices;
    private ArrayList<Integer> citiesid;
    private ArrayList<Integer> ids;
    private ArrayList<Integer> bloodtypesid;

    public AddDonation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_donation, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        Getnonifcount();
        getgovernorates();
        getbllodtype();

        return view;
    }

    private void Getnonifcount() {
        try{
        apiServices.getnotifcount(SharedPreferencesManger.LoadData(getActivity(), "api_token")).enqueue(new Callback<Notifcount>() {
            @Override
            public void onResponse(Call<Notifcount> call, Response<Notifcount> response) {
                if (response.body().getStatus() == 1) {
                    if (response.body().getData().getNotificationsCount().equals(0)) {
                    } else {
                        AddDonationFragmentTvNotifcount.setBackgroundResource(R.drawable.circlenotifcount);
                        AddDonationFragmentTvNotifcount.setText(String.valueOf(response.body().getData().getNotificationsCount()));
                    }
                }
            }

            @Override
            public void onFailure(Call<Notifcount> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT);
            }
        });}catch (Exception e){}
    }

    private void getbllodtype() {
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
                    fragmentAdddonationSpinerBloodtype.setAdapter(spinnerData);
                    fragmentAdddonationSpinerBloodtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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

    private void getgovernorates() {
        try {
        apiServices.getgovernorate().enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.body().getStatus() == 1) {

                    final ArrayList<String> names = new ArrayList<String>();
                    final ArrayList<Integer> ids = new ArrayList<Integer>();
                    names.add("Governorate");
                    ids.add(0);
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        names.add(response.body().getData().get(i).getName());
                        ids.add(response.body().getData().get(i).getId());
                    }


                    ArrayAdapter<String> spinnerData = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, names);
                    fragmentadddonationSpinerGovernorate.setAdapter(spinnerData);
                    fragmentadddonationSpinerGovernorate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {


                            }

                            getcities(ids.get(position));

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

    private void getcities(Integer id) {
        try {
        apiServices.getcities(id).enqueue(new Callback<GeneralResponse>() {
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
                    fragmentadddonationSpinerCityid.setAdapter(spinnerData);
                    citiesid.get(fragmentadddonationSpinerCityid.getSelectedItemPosition());
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


    @OnClick(R.id.fragment_adddonation_img_hospitaladdress)
    public void onViewClicked() {
        try {
            Intent intent = new Intent(getContext(), MapsActivity.class);
            startActivity(intent);
        }catch (Exception e){}
    }

    @OnClick(R.id.fragment_adddonation_btn_create)
    public void onViewClickedd() {

        String apitoken = SharedPreferencesManger.LoadData(getActivity(), "api_token");
        String patientname = fragmentAdddonationEdName.getText().toString();
        int patientage = Integer.parseInt(fragmentAdddonationEdEmail.getText().toString());
        int bloodtypeid = ids.get(fragmentAdddonationSpinerBloodtype.getSelectedItemPosition());
        int bagsnumber = Integer.parseInt(fragmentadddonationEdNumberofcys.getText().toString());
        String hospitalname = fragmentAdddonationEdHospitalname.getText().toString();
        String hospitaladress = fragmentAdddonationTvHospitaladdress.getText().toString();
        int city_id = citiesid.get(fragmentadddonationSpinerCityid.getSelectedItemPosition());
        String phone = (fragmentAdddonationEdPhonenumber.getText().toString());
        String notes = fragmentAdddonationTvNotice.getText().toString();
try{
        apiServices.adddon(apitoken, patientname, patientage, bloodtypeid, bagsnumber, hospitalname, hospitaladress, city_id, phone, notes, latitude, longtiute).enqueue(new Callback<com.ahmed.bank.data.model.AddDonation.AddDonation>() {
            @Override
            public void onResponse(Call<com.ahmed.bank.data.model.AddDonation.AddDonation> call, Response<com.ahmed.bank.data.model.AddDonation.AddDonation> response) {
                Log.d(TAG, "onResponse: ");
                if (response.body().getStatus() == 1) {
                    Toast.makeText(getContext(), "Successfuly added", Toast.LENGTH_SHORT).show();
                    Repair repair = new Repair();
                    HelperMethod.replac(repair, getFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);

                    // Repair repair=new Repair();
                    //HelperMethod.replac(repair,getFragmentManager(),R.id.activity_cycle_navigationdrawer_fl_container,null,null);
                }
            }

            @Override
            public void onFailure(Call<com.ahmed.bank.data.model.AddDonation.AddDonation> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });}catch (Exception e){}


    }

    @OnClick(R.id.Favourite_img_back)
    public void onViewClickeddd() {
        Repair repair = new Repair();
        HelperMethod.replac(repair, getActivity().getSupportFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);
    }
}
