package com.ahmed.bank.ui.fragment.navigationcycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.bank.R;
import com.ahmed.bank.adapter.PostsAdapter;
import com.ahmed.bank.data.local.SharedPreferencesManger;
import com.ahmed.bank.data.model.Notifcount.Notifcount;
import com.ahmed.bank.data.model.favourite.Favourite;
import com.ahmed.bank.data.model.getallposts.PostData;
import com.ahmed.bank.data.rest.ApiServices;
import com.ahmed.bank.helper.HelperMethod;
import com.ahmed.bank.ui.fragment.NotificationList;

import java.util.ArrayList;
import java.util.List;

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
public class FavouriteFragment extends Fragment {


    @BindView(R.id.Favourite_img_back)
    ImageView FavouriteImgBack;
    @BindView(R.id.Favourite_tv_fav)
    TextView FavouriteTvFav;
    @BindView(R.id.Favourite_img_notfi)
    ImageView FavouriteImgNotfi;
    @BindView(R.id.Favourite_rec_fav)
    RecyclerView FavouriteRecFav;
    Unbinder unbinder;
    ApiServices apiServices;
    @BindView(R.id.favourite_fragment_tv_notifcount)
    TextView favouriteFragmentTvNotifcount;
    private List<PostData> favouritepostDataList = new ArrayList<PostData>();
    private PostsAdapter postsAdapterfav;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = getClient().create(ApiServices.class);
        initrecycler();
        getallfavourite();
        Getnonifcount();
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
                        favouriteFragmentTvNotifcount.setBackgroundResource(R.drawable.circlenotifcount);
                        favouriteFragmentTvNotifcount.setText(String.valueOf(response.body().getData().getNotificationsCount()));
                    }

                }
            }

            @Override
            public void onFailure(Call<Notifcount> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT);
            }
        });}catch (Exception e){}
    }

    private void getallfavourite() {
        try{
        apiServices.favourite(SharedPreferencesManger.LoadData(getActivity(), "api_token")).enqueue(new Callback<Favourite>() {
            @Override
            public void onResponse(Call<Favourite> call, Response<Favourite> response) {
                if (response.body().getStatus() == 1) {
                    favouritepostDataList.addAll(response.body().getData().getData());
                    postsAdapterfav.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Favourite> call, Throwable t) {

            }
        });}catch (Exception e){}
    }

    private void initrecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        FavouriteRecFav.setLayoutManager(linearLayoutManager);
        postsAdapterfav = new PostsAdapter(favouritepostDataList, getContext(), getActivity());
        FavouriteRecFav.setAdapter(postsAdapterfav);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Favourite_img_back)
    public void onViewClicked() {
        Repair repair = new Repair();
        HelperMethod.replac(repair, getActivity().getSupportFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);
    }

    @OnClick(R.id.favourite_fragment_tv_notifcount)
    public void onViewClickedd() {
        NotificationList notificationList = new NotificationList();
        HelperMethod.replac(notificationList, getFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);

    }
}
