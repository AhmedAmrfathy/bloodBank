package com.ahmed.bank.ui.fragment.usercycle;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.bank.R;
import com.ahmed.bank.data.local.SharedPreferencesManger;
import com.ahmed.bank.data.model.login.Login;
import com.ahmed.bank.data.rest.ApiServices;
import com.ahmed.bank.helper.HelperMethod;
import com.ahmed.bank.ui.activity.CycleNavigationdrawerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ahmed.bank.data.rest.RetrofitClient.getClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.fragment_login_edittext_phone)
    TextInputEditText fragmentLoginEdittextPhone;
    @BindView(R.id.fragment_login_til_phone)
    TextInputLayout fragmentLoginTilPhone;
    @BindView(R.id.fragment_login_edittext_password)
    TextInputEditText fragmentLoginEdittextPassword;
    @BindView(R.id.fragment_login_til_password)
    TextInputLayout fragmentLoginTilPassword;
    @BindView(R.id.fragment_login_ch_remember)
    CheckBox fragmentLoginChRemember;
    @BindView(R.id.fragment_login_tv_forgetpassword)
    TextView fragmentLoginTvForgetpassword;
    @BindView(R.id.fragment_login_btn_login)
    Button fragmentLoginBtnLogin;
    @BindView(R.id.fragment_login_btn_crate)
    Button fragmentLoginBtnCrate;
    @BindView(R.id.Login_Fragment_rel_cont)
    RelativeLayout LoginFragmentRelCont;
    private ApiServices apiServices;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = getClient().create(ApiServices.class);
        //checker();
        //onopen();

        return view;
    }

    private void checker() {
        fragmentLoginChRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferencesManger.SaveData(getActivity(), "username", fragmentLoginEdittextPhone.getText().toString());
                    SharedPreferencesManger.SaveData(getActivity(), "password", fragmentLoginEdittextPassword.getText().toString());
                } else {
                    SharedPreferencesManger.SaveData(getActivity(), "username", "");
                    SharedPreferencesManger.SaveData(getActivity(), "password", "");
                }
            }
        });
    }

    private void onopen() {
        fragmentLoginEdittextPhone.setText(SharedPreferencesManger.LoadData(getActivity(), "username"));
        fragmentLoginEdittextPassword.setText(SharedPreferencesManger.LoadData(getActivity(), "password"));
        if (SharedPreferencesManger.LoadData(getActivity(), "username").equals("") & SharedPreferencesManger.LoadData(getActivity(), "password").equals("")) {
            fragmentLoginChRemember.setChecked(false);
        } else {
            fragmentLoginChRemember.setChecked(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fragment_login_btn_login, R.id.fragment_login_btn_crate, R.id.Login_Fragment_rel_cont})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_login_btn_login:

                if (fragmentLoginEdittextPhone.getText().toString().length() != 11) {
                    Toast.makeText(getActivity(), "PhoneLength must be 11 number ", Toast.LENGTH_SHORT);
                    return;
                }

                if (fragmentLoginEdittextPassword.getText().toString().length() < 6) {
                    Toast.makeText(getActivity(), "Password must be more or equal 6 ", Toast.LENGTH_SHORT);
                    return;
                }
                try {

                    apiServices.login(fragmentLoginEdittextPhone.getText().toString(), fragmentLoginEdittextPassword.getText().toString())
                            .enqueue(new Callback<Login>() {
                                @Override
                                public void onResponse(Call<Login> call, Response<Login> response) {
                                    if (response.body().getStatus() == 1) {
                                        SharedPreferencesManger.SaveData(getActivity(), "api_token", response.body().getData().getApiToken());
                                        Intent intent = new Intent(getActivity(), CycleNavigationdrawerActivity.class);
                                        startActivity(intent);
                                    }

                                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<Login> call, Throwable t) {
                                    Toast.makeText(getActivity(), "eroor", Toast.LENGTH_SHORT).show();

                                }
                            });

                } catch (Exception e) {
                }

                break;
            case R.id.fragment_login_btn_crate:
                RegisterFragment registerFragment = new RegisterFragment();
                HelperMethod.replac(registerFragment, getActivity().getSupportFragmentManager(), R.id.activity_user_cycle_fl_container, null, null);
                break;
            case R.id.Login_Fragment_rel_cont:
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;


        }
    }

    @OnClick(R.id.fragment_login_tv_forgetpassword)
    public void onViewClicked() {
        ForgetPasswordStep1Fragment forgetPasswordStep1Fragment = new ForgetPasswordStep1Fragment();
        HelperMethod.replac(forgetPasswordStep1Fragment, getActivity().getSupportFragmentManager(), R.id.activity_user_cycle_fl_container, null, null);

    }


}
