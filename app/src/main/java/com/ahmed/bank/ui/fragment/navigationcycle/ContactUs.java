package com.ahmed.bank.ui.fragment.navigationcycle;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.bank.R;
import com.ahmed.bank.data.local.SharedPreferencesManger;
import com.ahmed.bank.data.model.AppSettings.AppSettings;
import com.ahmed.bank.data.model.SendContactMessage.SendContactMessage;
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
public class ContactUs extends Fragment {


    @BindView(R.id.ContactUs_fragment_img_back)
    ImageView ContactUsFragmentImgBack;
    @BindView(R.id.ContactUs_fragment_tv_aboutapp)
    TextView ContactUsFragmentTvAboutapp;
    @BindView(R.id.ContactUs_fragment_rl_header)
    RelativeLayout ContactUsFragmentRlHeader;
    @BindView(R.id.ContactUs_fragment_im_logo)
    ImageView ContactUsFragmentImLogo;
    @BindView(R.id.ContactUs_fragment_im_call)
    ImageView ContactUsFragmentImCall;
    @BindView(R.id.ContactUs_fragment_tv_phone)
    TextView ContactUsFragmentTvPhone;
    @BindView(R.id.ContactUs_fragment_tv_getphone)
    TextView ContactUsFragmentTvGetphone;
    @BindView(R.id.ContactUs_fragment_im_message)
    ImageView ContactUsFragmentImMessage;
    @BindView(R.id.ContactUs_fragment_tv_Email)
    TextView ContactUsFragmentTvEmail;
    @BindView(R.id.ContactUs_fragment_tv_getEmail)
    TextView ContactUsFragmentTvGetEmail;
    @BindView(R.id.ContactUs_fragment_rl_contactinfo)
    RelativeLayout ContactUsFragmentRlContactinfo;
    @BindView(R.id.ContactUs_fragment_img_gmail)
    ImageView ContactUsFragmentImgGmail;
    @BindView(R.id.ContactUs_fragment_img_whats)
    ImageView ContactUsFragmentImgWhats;
    @BindView(R.id.ContactUs_fragment_img_insta)
    ImageView ContactUsFragmentImgInsta;
    @BindView(R.id.ContactUs_fragment_img_youtube)
    ImageView ContactUsFragmentImgYoutube;
    @BindView(R.id.ContactUs_fragment_img_twitter)
    ImageView ContactUsFragmentImgTwitter;
    @BindView(R.id.ContactUs_fragment_img_face)
    ImageView ContactUsFragmentImgFace;
    @BindView(R.id.ContactUs_fragment_ed_name)
    EditText ContactUsFragmentEdName;
    @BindView(R.id.ContactUs_fragment_ed_Email)
    EditText ContactUsFragmentEdEmail;
    @BindView(R.id.ContactUs_fragment_ed_phone)
    EditText ContactUsFragmentEdPhone;
    @BindView(R.id.ContactUs_fragment_ed_messeageaddress)
    EditText ContactUsFragmentEdMesseageaddress;
    @BindView(R.id.ContactUs_fragment_ed_message)
    EditText ContactUsFragmentEdMessage;
    @BindView(R.id.ContactUs_fragment_bt_send)
    Button ContactUsFragmentBtSend;
    private ApiServices apiServices;
    Unbinder unbinder;
    private String google;
    private String face;
    private String insta;
    private String youtube;
    private String twitter;
    private String whats;

    public ContactUs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        setdata();

        return view;
    }

    private void setdata() {
        try{
        apiServices.getappinformation(SharedPreferencesManger.LoadData(getActivity(), "api_token")).enqueue(new Callback<AppSettings>() {
            @Override
            public void onResponse(Call<AppSettings> call, Response<AppSettings> response) {
                if (response.body().getStatus() == 1) {
                    ContactUsFragmentTvGetphone.setText(response.body().getData().getPhone());
                    ContactUsFragmentTvGetEmail.setText(response.body().getData().getEmail());
                    face = response.body().getData().getFacebookUrl();
                    whats = response.body().getData().getWhatsapp();
                    google = response.body().getData().getGoogleUrl();
                    twitter = response.body().getData().getTwitterUrl();
                    insta = response.body().getData().getInstagramUrl();
                    youtube = response.body().getData().getYoutubeUrl();
                }
            }

            @Override
            public void onFailure(Call<AppSettings> call, Throwable t) {

            }
        });}catch (Exception e){}
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ContactUs_fragment_img_gmail, R.id.ContactUs_fragment_img_whats, R.id.ContactUs_fragment_img_insta, R.id.ContactUs_fragment_img_youtube, R.id.ContactUs_fragment_img_twitter, R.id.ContactUs_fragment_img_face, R.id.ContactUs_fragment_bt_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ContactUs_fragment_img_gmail:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(google));
                startActivity(intent);
                break;
            case R.id.ContactUs_fragment_img_whats:
                Uri uri = Uri.parse("smsto:" + whats);
                Intent intent1 = new Intent(Intent.ACTION_SENDTO, uri);
                intent1.setPackage("com.whatsapp");
                startActivity(intent1);
                break;
            case R.id.ContactUs_fragment_img_insta:
                Intent intenttt = new Intent(Intent.ACTION_VIEW, Uri.parse(insta));
                startActivity(intenttt);
                break;
            case R.id.ContactUs_fragment_img_youtube:
                Intent intentttt = new Intent(Intent.ACTION_VIEW, Uri.parse(youtube));
                startActivity(intentttt);
                break;
            case R.id.ContactUs_fragment_img_twitter:
                Intent inten = new Intent(Intent.ACTION_VIEW, Uri.parse(twitter));
                startActivity(inten);
                break;
            case R.id.ContactUs_fragment_img_face:
                Intent intet = new Intent(Intent.ACTION_VIEW, Uri.parse(face));
                startActivity(intet);
                break;
            case R.id.ContactUs_fragment_bt_send:
                try{
                apiServices.send(SharedPreferencesManger.LoadData(getActivity(), "api_token"), ContactUsFragmentEdMesseageaddress.getText().toString(), ContactUsFragmentEdMessage.getText().toString()).enqueue(new Callback<SendContactMessage>() {
                    @Override
                    public void onResponse(Call<SendContactMessage> call, Response<SendContactMessage> response) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(getContext(), "Successfully sent", Toast.LENGTH_SHORT).show();
                            Repair repair = new Repair();
                            HelperMethod.replac(repair, getFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<SendContactMessage> call, Throwable t) {

                    }
                });}catch (Exception e){}

                break;
        }
    }

    @OnClick(R.id.ContactUs_fragment_img_back)
    public void onViewClicked() {
        Repair repair=new Repair();
        HelperMethod.replac(repair,getActivity().getSupportFragmentManager(),R.id.activity_cycle_navigationdrawer_fl_container,null,null);
    }
}
