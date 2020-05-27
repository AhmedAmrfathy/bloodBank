package com.ahmed.bank.ui.fragment.navigationcycle;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ahmed.bank.R;
import com.ahmed.bank.adapter.NontificationListAdapter;
import com.ahmed.bank.data.local.SharedPreferencesManger;
import com.ahmed.bank.data.model.DisplayNotificationDetails.DisplayNotificationDetails;
import com.ahmed.bank.data.model.Donations.Donation;
import com.ahmed.bank.data.rest.ApiServices;
import com.ahmed.bank.data.rest.RetrofitClient;
import com.ahmed.bank.helper.HelperMethod;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ahmed.bank.helper.HelperMethod.MAP_VIEW_BUNDLE_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class Donationetails extends Fragment implements OnMapReadyCallback {
    public Donation donation;
    public  String donationreguestid;
    @BindView(R.id.Donationdetails_fragment_img_back)
    ImageView DonationdetailsFragmentImgBack;
    @BindView(R.id.Donationdetails_fragment_tv_donreg)
    TextView DonationdetailsFragmentTvDonreg;
    @BindView(R.id.Donationdetails_fragment_tv_nameofdonationowner)
    TextView DonationdetailsFragmentTvNameofdonationowner;
    @BindView(R.id.Donationdetails_fragment_rl_toolbarheader)
    RelativeLayout DonationdetailsFragmentRlToolbarheader;
    @BindView(R.id.Donationdetails_fragment_tv_nameofdonationownerinpage)
    TextView DonationdetailsFragmentTvNameofdonationownerinpage;
    @BindView(R.id.Donationdetails_fragment_tv_getnameofdonationownerinpage)
    TextView DonationdetailsFragmentTvGetnameofdonationownerinpage;
    @BindView(R.id.Donationdetails_fragment_tv_ageofdonationownerinpage)
    TextView DonationdetailsFragmentTvAgeofdonationownerinpage;
    @BindView(R.id.Donationdetails_fragment_tv_getageofdonationownerinpage)
    TextView DonationdetailsFragmentTvGetageofdonationownerinpage;
    @BindView(R.id.Donationdetails_fragment_tv_numberofcysofdonationownerinpage)
    TextView DonationdetailsFragmentTvNumberofcysofdonationownerinpage;
    @BindView(R.id.Donationdetails_fragment_tv_getnumberofcysofdonationownerinpage)
    TextView DonationdetailsFragmentTvGetnumberofcysofdonationownerinpage;
    @BindView(R.id.Donationdetails_fragment_tv_hospitalofdonationownerinpage)
    TextView DonationdetailsFragmentTvHospitalofdonationownerinpage;
    @BindView(R.id.Donationdetails_fragment_tv_gethospitalofdonationownerinpage)
    TextView DonationdetailsFragmentTvGethospitalofdonationownerinpage;
    @BindView(R.id.Donationdetails_fragment_tv_adressofhospitalofdonationownerinpage)
    TextView DonationdetailsFragmentTvAdressofhospitalofdonationownerinpage;
    @BindView(R.id.Donationdetails_fragment_tv_getadressofhospitalofdonationownerinpage)
    TextView DonationdetailsFragmentTvGetadressofhospitalofdonationownerinpage;
    @BindView(R.id.Donationdetails_fragment_tv_phonenumberofdonationownerinpage)
    TextView DonationdetailsFragmentTvPhonenumberofdonationownerinpage;
    @BindView(R.id.Donationdetails_fragment_tv_getphonenumberofdonationownerinpage)
    TextView DonationdetailsFragmentTvGetphonenumberofdonationownerinpage;
    @BindView(R.id.Donationdetails_fragment_tv_description)
    TextView DonationdetailsFragmentTvDescription;
    Unbinder unbinder;
    @BindView(R.id.Donationetails_fragment_btn_call)
    Button DonationetailsFragmentBtnCall;
    @BindView(R.id.Donationetails_fragment_img_call)
    ImageView DonationetailsFragmentImgCall;
    @BindView(R.id.mapview)
    MapView mapview;
    private GoogleMap gmap;
    private double lt;
    private double lg;
    private ApiServices apiServices;




    public Donationetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donationetails, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices= RetrofitClient.getClient().create(ApiServices.class);
        initmapview(savedInstanceState);
        settdata();
        return view;
    }

    private void settdata() {
        try{
        apiServices.getdonationreguestdetails(SharedPreferencesManger.LoadData(getActivity(),"api_token"),donationreguestid).enqueue(new Callback<DisplayNotificationDetails>() {
            @Override
            public void onResponse(Call<DisplayNotificationDetails> call, Response<DisplayNotificationDetails> response) {
                if (response.body().getStatus()==1){
                    DonationdetailsFragmentTvGetnameofdonationownerinpage.setText(response.body().getData().getPatientName());
                    DonationdetailsFragmentTvGetageofdonationownerinpage.setText(response.body().getData().getPatientAge());
                    DonationdetailsFragmentTvGetnumberofcysofdonationownerinpage.setText(response.body().getData().getBagsNum());
                    DonationdetailsFragmentTvGethospitalofdonationownerinpage.setText(response.body().getData().getHospitalName());
                    DonationdetailsFragmentTvGetadressofhospitalofdonationownerinpage.setText(response.body().getData().getHospitalAddress());
                    DonationdetailsFragmentTvGetphonenumberofdonationownerinpage.setText(response.body().getData().getPhone());
                    DonationdetailsFragmentTvDescription.setText(response.body().getData().getNotes());
                    DonationdetailsFragmentTvNameofdonationowner.setText(response.body().getData().getPatientName());
                    lt = Double.parseDouble(response.body().getData().getLatitude());
                    lg = Double.parseDouble(response.body().getData().getLongitude());
                }
            }

            @Override
            public void onFailure(Call<DisplayNotificationDetails> call, Throwable t) {

            }
        });}catch (Exception e){}
    }

    private void initmapview( Bundle savedInstanceState ) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapview.onCreate(mapViewBundle);
        mapview.getMapAsync(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Donationdetails_fragment_img_back)
    public void onViewClicked() {
        Repair repair = new Repair();
        HelperMethod.replac(repair, getActivity().getSupportFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);
    }

    @OnClick(R.id.Donationetails_fragment_btn_call)
    public void onViewClickedd() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + DonationdetailsFragmentTvGetphonenumberofdonationownerinpage.getText()));
        startActivity(intent);
    }

    @OnClick(R.id.Donationetails_fragment_img_call)
    public void onViewClicke() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + DonationdetailsFragmentTvGetphonenumberofdonationownerinpage.getText()));
        startActivity(intent);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapview.onSaveInstanceState(mapViewBundle);
    }
    @Override
    public void onResume() {
        super.onResume();
        mapview.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapview.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapview.onStop();
    }
    @Override
    public void onPause() {
        mapview.onPause();
        super.onPause();
    }
    @Override
    public void onDestroy() {
        mapview.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapview.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        LatLng latLng=new LatLng(lt,lg);
        gmap.addMarker(new MarkerOptions().position(latLng).title("position"));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}


