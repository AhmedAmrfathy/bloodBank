package com.ahmed.bank.ui.fragment.usercycle;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.bank.R;
import com.ahmed.bank.data.model.resetpassword.ResetPassword;
import com.ahmed.bank.data.rest.ApiServices;
import com.ahmed.bank.helper.HelperMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ahmed.bank.data.rest.RetrofitClient.getClient;


public class ForgetPasswordStep1Fragment extends Fragment {
    Unbinder unbinder;


    @BindView(R.id.fragment_forget_password_img_icon)
    ImageView fragmentForgetPasswordImgIcon;
    @BindView(R.id.fragment_forget_password_tv_forgetpass)
    TextView fragmentForgetPasswordTvForgetpass;
    @BindView(R.id.fragment_forget_password_ed_phonenumber)
    TextInputEditText fragmentForgetPasswordEdPhonenumber;
    @BindView(R.id.fragment_forget_password_til_phonenumber)
    TextInputLayout fragmentForgetPasswordTilPhonenumber;
    @BindView(R.id.fragment_forget_password_btn_send)
    Button fragmentForgetPasswordBtnSend;
    @BindView(R.id.ForgetPasswordStep1_fragment_rel_cont)
    RelativeLayout ForgetPasswordStep1FragmentRelCont;
    @BindView(R.id.ForgetPasswordStep1_fragment_scr_cont)
    ScrollView ForgetPasswordStep1FragmentScrCont;
    private ApiServices apiServices;
    public static String number;


    public ForgetPasswordStep1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password_step1, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.fragment_forget_password_btn_send)
    public void onViewClicked() {

        if (fragmentForgetPasswordEdPhonenumber.getText().toString().length() != 11) {
            Toast.makeText(getActivity(), "PhoneLength must be 11 number ", Toast.LENGTH_SHORT);
            return;
        }
        try {
            apiServices = getClient().create(ApiServices.class);
            apiServices.reset(fragmentForgetPasswordEdPhonenumber.getText().toString()).enqueue(new Callback<ResetPassword>() {
                @Override
                public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), "succefully sent", Toast.LENGTH_SHORT);
                        ForgetPasswordStep2Fragment forgetPasswordStep2Fragment = new ForgetPasswordStep2Fragment();
                        HelperMethod.replac(forgetPasswordStep2Fragment, getActivity().getSupportFragmentManager(), R.id.activity_user_cycle_fl_container, null, null);

                    }
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT);
                }

                @Override
                public void onFailure(Call<ResetPassword> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }


    }
    @OnClick(R.id.ForgetPasswordStep1_fragment_rel_cont)
    public void onViewClickedd( View view) {
        InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

}
