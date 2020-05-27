package com.ahmed.bank.ui.fragment.navigationcycle;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ahmed.bank.R;
import com.ahmed.bank.data.local.SharedPreferencesManger;
import com.ahmed.bank.data.model.DateModel;
import com.ahmed.bank.data.model.generalResponse.GeneralResponse;
import com.ahmed.bank.data.model.login.Client;
import com.ahmed.bank.data.model.login.Login;
import com.ahmed.bank.data.rest.ApiServices;
import com.ahmed.bank.data.rest.RetrofitClient;
import com.ahmed.bank.helper.HelperMethod;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
public class AboutMe_fragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.fragment_about_me_tv_Editprofile)
    TextView fragmentAboutMeTvEditprofile;
    @BindView(R.id.fragment_aboutme_ed_name)
    TextInputEditText fragmentAboutmeEdName;
    @BindView(R.id.fragment_aboutme_til_name)
    TextInputLayout fragmentAboutmeTilName;
    @BindView(R.id.fragment_aboutme_ed_email)
    TextInputEditText fragmentAboutmeEdEmail;
    @BindView(R.id.fragment_aboutme_til_email)
    TextInputLayout fragmentAboutmeTilEmail;
    @BindView(R.id.fragment_aboutme_ed_dateofbirth)
    TextView fragmentAboutmeEdDateofbirth;
    @BindView(R.id.fragment_aboutme_til_dateofbirth)
    RelativeLayout fragmentAboutmeTilDateofbirth;
    @BindView(R.id.fragment_aboutme_spiner_bloodtype)
    AppCompatSpinner fragmentAboutmeSpinerBloodtype;
    @BindView(R.id.fragment_aboutme_ed_lastdonationname)
    TextView fragmentAboutmeEdLastdonationname;
    @BindView(R.id.fragment_aboutme_til_lastdonationname)
    RelativeLayout fragmentAboutmeTilLastdonationname;
    @BindView(R.id.fragmentaboutmeSpinerGovernorate)
    AppCompatSpinner fragmentaboutmeSpinerGovernorate;
    @BindView(R.id.fragmentaboutmeSpinerCityid)
    AppCompatSpinner fragmentaboutmeSpinerCityid;
    @BindView(R.id.fragment_aboutme_ed_password)
    TextInputEditText fragmentAboutmeEdPassword;
    @BindView(R.id.fragment_aboutme_til_password)
    TextInputLayout fragmentAboutmeTilPassword;
    @BindView(R.id.fragment_aboutme_tv_passwordconfig)
    TextInputEditText fragmentAboutmeTvPasswordconfig;
    @BindView(R.id.fragment_aboutme_til_passwordconfig)
    TextInputLayout fragmentAboutmeTilPasswordconfig;
    @BindView(R.id.fragment_aboutme_ed_phone)
    TextInputEditText fragmentAboutmeEdPhone;
    @BindView(R.id.fragment_aboutme_til_phone)
    TextInputLayout fragmentAboutmeTilPhone;
    @BindView(R.id.fragment_aboutme_btn_save)
    Button fragmentAboutmeBtnSave;
    ApiServices apiServices;

    private ArrayList<Integer> citiesid;
    private ArrayList<Integer> ids;
    private DateModel datemodel;
    private Client client;

    public AboutMe_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_me, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);

        getProfile();
        return view;
    }

    private void getProfile() {
        try {
            apiServices.getprofiledata(SharedPreferencesManger.LoadData(getActivity(), "api_token")).enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    if (response.body().getStatus() == 1) {
                        client = response.body().getData().getClient();
                        fragmentAboutmeEdName.setHint(response.body().getData().getClient().getName());
                        fragmentAboutmeEdEmail.setHint(response.body().getData().getClient().getEmail());
                        fragmentAboutmeEdDateofbirth.setHint(response.body().getData().getClient().getBirthDate());
                        fragmentAboutmeSpinerBloodtype.setId((response.body().getData().getClient().getBloodType().getId()));
                        fragmentAboutmeEdLastdonationname.setHint(response.body().getData().getClient().getDonationLastDate());
                        fragmentaboutmeSpinerGovernorate.setId(response.body().getData().getClient().getCity().getGovernorate().getId());
                        fragmentaboutmeSpinerCityid.setId(response.body().getData().getClient().getCity().getId());
                        fragmentAboutmeEdPassword.setText("");
                        fragmentAboutmeTvPasswordconfig.setText("");
                        fragmentAboutmeEdPhone.setHint(response.body().getData().getClient().getPhone());
                        getGovernorates();
                        getBloodType();
                    }
                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                }
            });
        }catch (Exception e){}
    }

    private void getBloodType() {
        try {
            apiServices.getbloodtype().enqueue(new Callback<GeneralResponse>() {
                @Override
                public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                    if (response.body().getStatus() == 1) {
                        final ArrayList<String> name = new ArrayList<>();
                        ids = new ArrayList<>();
                        name.add("Blood Type");
                        ids.add(0);
                        int pos = 0;
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            name.add(response.body().getData().get(i).getName());
                            ids.add(response.body().getData().get(i).getId());
                            if (response.body().getData().get(i).getId() == client.getBloodType().getId()) {
                                pos = 1 + i;
                            }
                        }
                        ArrayAdapter<String> spinnerData = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, name);
                        fragmentAboutmeSpinerBloodtype.setAdapter(spinnerData);

                        fragmentAboutmeSpinerBloodtype.setSelection(pos);
                    }

                }

                @Override
                public void onFailure(Call<GeneralResponse> call, Throwable t) {

                }
            });
        }catch (Exception e){}
    }

    private void getGovernorates() {
        try{
        apiServices.getgovernorate().enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.body().getStatus() == 1) {

                    final ArrayList<String> names = new ArrayList<String>();
                    final ArrayList<Integer> ids = new ArrayList<Integer>();
                    names.add("Governorate");
                    ids.add(0);
                    int pos = 0;
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        names.add(response.body().getData().get(i).getName());
                        ids.add(response.body().getData().get(i).getId());

                        if (response.body().getData().get(i).getId() == client.getCity().getGovernorate().getId()) {
                            pos = 1 + i;
                        }
                    }
                    ArrayAdapter<String> spinnerData = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, names);
                    fragmentaboutmeSpinerGovernorate.setAdapter(spinnerData);
                    fragmentaboutmeSpinerGovernorate.setSelection(pos);
                    fragmentaboutmeSpinerGovernorate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                            } else {
                                getcities(ids.get(position));
                            }
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
        try{
        apiServices.getcities(id).enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.body().getStatus() == 1) {
                    ArrayList<String> name = new ArrayList<String>();
                    citiesid = new ArrayList<>();
                    name.add("City");
                    citiesid.add(0);
                    int pos = 0;
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        name.add(response.body().getData().get(i).getName());
                        citiesid.add(response.body().getData().get(i).getId());
                        if (response.body().getData().get(i).getId() == client.getCity().getId()) {
                            pos = 1 + i;
                        }
                    }
                    ArrayAdapter<String> spinnerData = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, name);
                    fragmentaboutmeSpinerCityid.setAdapter(spinnerData);
                    fragmentaboutmeSpinerCityid.setSelection(pos);

                    citiesid.get(fragmentaboutmeSpinerCityid.getSelectedItemPosition());
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


    @OnClick({R.id.fragment_aboutme_ed_dateofbirth, R.id.fragment_aboutme_ed_lastdonationname, R.id.fragment_aboutme_btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_aboutme_ed_dateofbirth:
                datemodel = new DateModel("01", "01", "1970", "01-01-1970");

                DecimalFormat mFormat = new DecimalFormat("00");
                Calendar calander = Calendar.getInstance();
                String cDay = mFormat.format(Double.valueOf(String.valueOf(calander.get(Calendar.DAY_OF_MONTH))));
                String cMonth = mFormat.format(Double.valueOf(String.valueOf(calander.get(Calendar.MONTH + 1))));
                String cYear = String.valueOf(calander.get(Calendar.YEAR));

                datemodel = new DateModel(cDay, cMonth, cYear, cDay + "-" + cMonth + "-" + cYear);
                HelperMethod.showCalender(getActivity(), "Date Of Birth", fragmentAboutmeEdDateofbirth, datemodel);
                break;
            case R.id.fragment_aboutme_ed_lastdonationname:
                datemodel = new DateModel("01", "01", "1970", "01-01-1970");

                DecimalFormat mmFormat = new DecimalFormat("00");
                Calendar ccalander = Calendar.getInstance();
                String ccDay = mmFormat.format(Double.valueOf(String.valueOf(ccalander.get(Calendar.DAY_OF_MONTH))));
                String ccMonth = mmFormat.format(Double.valueOf(String.valueOf(ccalander.get(Calendar.MONTH + 1))));
                String ccYear = String.valueOf(ccalander.get(Calendar.YEAR));

                datemodel = new DateModel(ccDay, ccMonth, ccYear, ccDay + "-" + ccMonth + "-" + ccYear);
                HelperMethod.showCalender(getActivity(), "Last Donation Date", fragmentAboutmeEdLastdonationname, datemodel);
                break;
            case R.id.fragment_aboutme_btn_save:


                break;
        }
    }
}
