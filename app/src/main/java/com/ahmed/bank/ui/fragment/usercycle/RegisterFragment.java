package com.ahmed.bank.ui.fragment.usercycle;

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
import android.widget.Toast;

import com.ahmed.bank.R;
import com.ahmed.bank.data.local.SharedPreferencesManger;
import com.ahmed.bank.data.model.DateModel;
import com.ahmed.bank.data.model.generalResponse.GeneralResponse;
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


public class RegisterFragment<apiServices> extends Fragment {
    Unbinder unbinder;

    ApiServices apiServices;
    @BindView(R.id.fragment_register_tv_createnewaccount)
    TextView fragmentRegisterTvCreatenewaccount;
    @BindView(R.id.fragment_register_ed_name)
    TextInputEditText fragmentRegisterEdName;
    @BindView(R.id.fragment_register_til_name)
    TextInputLayout fragmentRegisterTilName;
    @BindView(R.id.fragment_register_ed_email)
    TextInputEditText fragmentRegisterEdEmail;
    @BindView(R.id.fragment_register_til_email)
    TextInputLayout fragmentRegisterTilEmail;
    @BindView(R.id.fragment_register_ed_dateofbirth)
    TextView fragmentRegisterEdDateofbirth;
    @BindView(R.id.fragment_register_til_dateofbirth)
    RelativeLayout fragmentRegisterTilDateofbirth;
    @BindView(R.id.fragment_register_spiner_bloodtype)
    AppCompatSpinner fragmentRegisterSpinerBloodtype;
    @BindView(R.id.fragment_register_ed_lastdonationname)
    TextView fragmentRegisterEdLastdonationname;
    @BindView(R.id.fragment_register_til_lastdonationname)
    RelativeLayout fragmentRegisterTilLastdonationname;
    @BindView(R.id.fragmentRegisterSpinerGovernorate)
    AppCompatSpinner fragmentRegisterSpinerGovernorate;
    @BindView(R.id.fragmentRegisterSpinerCityid)
    AppCompatSpinner fragmentRegisterSpinerCityid;
    @BindView(R.id.fragment_register_ed_password)
    TextInputEditText fragmentRegisterEdPassword;
    @BindView(R.id.fragment_register_til_password)
    TextInputLayout fragmentRegisterTilPassword;
    @BindView(R.id.fragment_register_tv_passwordconfig)
    TextInputEditText fragmentRegisterTvPasswordconfig;
    @BindView(R.id.fragment_register_til_passwordconfig)
    TextInputLayout fragmentRegisterTilPasswordconfig;
    @BindView(R.id.fragment_register_ed_phone)
    TextInputEditText fragmentRegisterEdPhone;
    @BindView(R.id.fragment_register_til_phone)
    TextInputLayout fragmentRegisterTilPhone;
    @BindView(R.id.fragment_register_btn_create)
    Button fragmentRegisterBtnCreate;


    private ArrayList<Integer> citiesid;
    private ArrayList<Integer> ids;
    private DateModel datemodel;

    public RegisterFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        getgovernorates();
        getbllodtype();


        return view;
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
                    fragmentRegisterSpinerBloodtype.setAdapter(spinnerData);
                    fragmentRegisterSpinerBloodtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        });}catch (Exception E){}
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
                    fragmentRegisterSpinerGovernorate.setAdapter(spinnerData);
                    fragmentRegisterSpinerGovernorate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        try{
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
                    fragmentRegisterSpinerCityid.setAdapter(spinnerData);
                    citiesid.get(fragmentRegisterSpinerCityid.getSelectedItemPosition());
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


    @OnClick(R.id.fragment_register_ed_dateofbirth)
    public void onViewClicked() {
        datemodel = new DateModel("01", "01", "1970", "01-01-1970");

        DecimalFormat mFormat = new DecimalFormat("00");
        Calendar calander = Calendar.getInstance();
        String cDay = mFormat.format(Double.valueOf(String.valueOf(calander.get(Calendar.DAY_OF_MONTH))));
        String cMonth = mFormat.format(Double.valueOf(String.valueOf(calander.get(Calendar.MONTH + 1))));
        String cYear = String.valueOf(calander.get(Calendar.YEAR));

        datemodel = new DateModel(cDay, cMonth, cYear, cDay + "-" + cMonth + "-" + cYear);
        HelperMethod.showCalender(getActivity(), "Date Of Birth", fragmentRegisterEdDateofbirth, datemodel);

    }


    @OnClick(R.id.fragment_register_ed_lastdonationname)
    public void onViewClickedd() {
        datemodel = new DateModel("01", "01", "1970", "01-01-1970");

        DecimalFormat mFormat = new DecimalFormat("00");
        Calendar calander = Calendar.getInstance();
        String cDay = mFormat.format(Double.valueOf(String.valueOf(calander.get(Calendar.DAY_OF_MONTH))));
        String cMonth = mFormat.format(Double.valueOf(String.valueOf(calander.get(Calendar.MONTH + 1))));
        String cYear = String.valueOf(calander.get(Calendar.YEAR));

        datemodel = new DateModel(cDay, cMonth, cYear, cDay + "-" + cMonth + "-" + cYear);
        HelperMethod.showCalender(getActivity(), "Last Donation Date", fragmentRegisterEdLastdonationname, datemodel);
    }

    @OnClick(R.id.fragment_register_btn_create)
    public void onViewClickeddd() {
        try{
        apiServices.register(fragmentRegisterEdName.getText().toString(),
                fragmentRegisterEdEmail.getText().toString(),
                fragmentRegisterEdDateofbirth.getText().toString(), citiesid.get(fragmentRegisterSpinerCityid.getSelectedItemPosition()), fragmentRegisterEdPhone.getText().toString(),
                fragmentRegisterEdLastdonationname.getText().toString(), fragmentRegisterEdPassword.getText().toString(), fragmentRegisterEdPassword.getText().toString(),
                ids.get(fragmentRegisterSpinerBloodtype.getSelectedItemPosition())
        ).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(getActivity(), "succssfuly registed", Toast.LENGTH_SHORT).show();
                    SharedPreferencesManger.SaveData(getActivity(), "password", fragmentRegisterEdPassword.getText().toString());
                    LoginFragment loginFragment = new LoginFragment();
                    HelperMethod.replac(loginFragment, getActivity().getSupportFragmentManager(), R.id.activity_user_cycle_fl_container, null, null);
                }
                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });}catch (Exception e){}
    }
}