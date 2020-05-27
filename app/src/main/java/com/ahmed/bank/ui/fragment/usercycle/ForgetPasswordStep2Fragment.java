package com.ahmed.bank.ui.fragment.usercycle;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.bank.R;
import com.ahmed.bank.data.model.newpassword.NewPassword;
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


public class ForgetPasswordStep2Fragment extends Fragment {

    @BindView(R.id.fragment_forget_password_step2_img_icon)
    ImageView fragmentForgetPasswordStep2ImgIcon;
    @BindView(R.id.fragment_forget_password_step2_tv_changingpass)
    TextView fragmentForgetPasswordStep2TvChangingpass;
    @BindView(R.id.fragment_forget_password_ed_configpass)
    TextInputEditText fragmentForgetPasswordEdConfigpass;
    @BindView(R.id.fragment_forget_password_step2_til_configpass)
    TextInputLayout fragmentForgetPasswordStep2TilConfigpass;
    @BindView(R.id.fragment_forget_password_step2_ed_newpassword)
    TextInputEditText fragmentForgetPasswordStep2EdNewpassword;
    @BindView(R.id.fragment_forget_password_step2_til_newpassword)
    TextInputLayout fragmentForgetPasswordStep2TilNewpassword;
    @BindView(R.id.fragment_forget_password_step2_ed_renewpassword)
    TextInputEditText fragmentForgetPasswordStep2EdRenewpassword;
    @BindView(R.id.fragment_forget_password_step2_til_renewpassword)
    TextInputLayout fragmentForgetPasswordStep2TilRenewpassword;
    @BindView(R.id.fragment_forget_password_step2_btn_confirmchanging)
    Button fragmentForgetPasswordStep2BtnConfirmchanging;
    Unbinder unbinder;
    private ApiServices apiServices;

    public ForgetPasswordStep2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password_step2, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices=getClient().create(ApiServices.class);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fragment_forget_password_step2_btn_confirmchanging)
    public void onViewClicked() {
        if (fragmentForgetPasswordStep2EdNewpassword.getText().toString().length()!= 11) {
            Toast.makeText(getActivity(),"PhoneLength must be 11 number ",Toast.LENGTH_SHORT);
            return;
        }
        if (fragmentForgetPasswordStep2EdRenewpassword.getText().toString().length()!= 11) {
            Toast.makeText(getActivity(),"PhoneLength must be 11 number ",Toast.LENGTH_SHORT);
            return;
        }

try {


apiServices.nnew(fragmentForgetPasswordStep2EdNewpassword.getText().toString(),fragmentForgetPasswordStep2EdRenewpassword.getText().toString(),fragmentForgetPasswordEdConfigpass.getText().toString(),ForgetPasswordStep1Fragment.number).enqueue(new Callback<NewPassword>() {
    @Override
    public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {
        if (response.body().getStatus()==1) {
            Toast.makeText(getActivity(), "password succussfully changed", Toast.LENGTH_SHORT).show();
            LoginFragment loginFragment=new LoginFragment();
            HelperMethod.replac(loginFragment,getActivity().getSupportFragmentManager(),R.id.activity_user_cycle_fl_container,null,null);
        }
        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onFailure(Call<NewPassword> call, Throwable t) {

    }
});}catch (Exception e){}

    }
}
