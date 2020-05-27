package com.ahmed.bank.ui.fragment.navigationcycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.bank.R;
import com.ahmed.bank.data.local.SharedPreferencesManger;
import com.ahmed.bank.data.model.Notifcount.Notifcount;
import com.ahmed.bank.data.model.getallposts.PostData;
import com.ahmed.bank.data.rest.ApiServices;
import com.ahmed.bank.data.rest.RetrofitClient;
import com.ahmed.bank.helper.HelperMethod;
import com.google.android.gms.common.api.Api;

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
public class PostDetails extends Fragment {
    public PostData post;
    @BindView(R.id.Favourite_fragment_img_back)
    ImageView FavouriteFragmentImgBack;
    @BindView(R.id.Favourite_fragment_tv_headertitle)
    TextView FavouriteFragmentTvHeadertitle;
    @BindView(R.id.Favourite_fragment_img_notfi)
    ImageView FavouriteFragmentImgNotfi;
    @BindView(R.id.fragment_adddonation_tv_createnewaccount)
    RelativeLayout fragmentAdddonationTvCreatenewaccount;
    @BindView(R.id.Favourite_fragment_img_baseimg)
    ImageView FavouriteFragmentImgBaseimg;
    @BindView(R.id.Favourite_fragment_tv_title)
    TextView FavouriteFragmentTvTitle;
    @BindView(R.id.Favourite_fragment_tv_content)
    TextView FavouriteFragmentTvContent;
    Unbinder unbinder;
    @BindView(R.id.PostDetails_fragment_tv_notifcount)
    TextView PostDetailsFragmentTvNotifcount;
    private ApiServices apiServices;


    public PostDetails() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices= RetrofitClient.getClient().create(ApiServices.class);
        Getnonifcount();
        setdata();
        return view;
    }

    private void Getnonifcount() {
        try{
        apiServices.getnotifcount(SharedPreferencesManger.LoadData(getActivity(),"api_token")).enqueue(new Callback<Notifcount>() {
            @Override
            public void onResponse(Call<Notifcount> call, Response<Notifcount> response) {
                if (response.body().getStatus()==1){
                    if (response.body().getData().getNotificationsCount().equals(0)){
                    }
                    else {
                        PostDetailsFragmentTvNotifcount.setBackgroundResource(R.drawable.circlenotifcount);
                        PostDetailsFragmentTvNotifcount.setText(String.valueOf(response.body().getData().getNotificationsCount()));
                    }
                }
            }

            @Override
            public void onFailure(Call<Notifcount> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT);
            }
        });}catch (Exception e){}
    }

    private void setdata() {
        FavouriteFragmentTvHeadertitle.setText(post.getCategory().getName());
        HelperMethod.onLoadImageFromUrl(FavouriteFragmentImgBaseimg, post.getThumbnailFullPath(), getContext());
        FavouriteFragmentTvTitle.setText(post.getTitle());
        FavouriteFragmentTvContent.setText(post.getContent());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Favourite_fragment_img_back)
    public void onViewClicked() {
        Repair repair = new Repair();
        HelperMethod.replac(repair, getActivity().getSupportFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);
    }
}
