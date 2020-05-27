package com.ahmed.bank.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.bank.R;
import com.ahmed.bank.helper.HelperMethod;
import com.ahmed.bank.ui.fragment.navigationcycle.Repair;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @BindView(R.id.MapsActivity_img_back)
    ImageView MapsActivityImgBack;
    @BindView(R.id.MapsActivity_tv_fav)
    TextView MapsActivityTvFav;
    @BindView(R.id.MapsActivity_rl_header)
    RelativeLayout MapsActivityRlHeader;
    @BindView(R.id.MapsActivity_btn_savelocation)
    Button MapsActivityBtnSavelocation;
    private GoogleMap mMap;
    public static double longtiute;
    public static double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng));
                latitude = latLng.latitude;
                longtiute = latLng.longitude;
            }
        });
    }

    @OnClick(R.id.MapsActivity_btn_savelocation)
    public void onViewClicked() {
        Toast.makeText(this, "location saved successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    @OnClick(R.id.MapsActivity_img_back)
    public void onViewClickedd() {
      finish();
    }
}
