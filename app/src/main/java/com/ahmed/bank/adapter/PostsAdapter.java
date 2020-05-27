package com.ahmed.bank.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmed.bank.R;
import com.ahmed.bank.data.local.SharedPreferencesManger;
import com.ahmed.bank.data.model.addremovefaourite.AddRemoveFav;
import com.ahmed.bank.data.model.getallposts.PostData;
import com.ahmed.bank.data.rest.ApiServices;
import com.ahmed.bank.helper.HelperMethod;
import com.ahmed.bank.ui.fragment.navigationcycle.PostDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ahmed.bank.data.rest.RetrofitClient.getClient;
import static com.ahmed.bank.helper.HelperMethod.onLoadImageFromUrl;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.viewholder> {

    @BindView(R.id.recylerviewelemnt_tv)
    TextView recylerviewelemntTv;
    @BindView(R.id.recylerviewelemnt_img_fav)
    ImageView recylerviewelemntImgFav;
    @BindView(R.id.recylerviewelemnt_img_main)
    ImageView recylerviewelemntImgMain;
    private List<PostData> postDataList = new ArrayList<>();
    private Context context;
    private Activity activity;
    private ApiServices apiServices;

    public PostsAdapter(List<PostData> postDataList, Context context, Activity activity) {
        this.postDataList = postDataList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post, null);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, int i) {
        setDate(viewholder, i);
        setaction(viewholder, i);

    }

    private void setDate(@NonNull viewholder viewholder, final int i) {
        viewholder.recylerviewelemntTv.setText(postDataList.get(i).getContent());
        if (postDataList.get(i).getIsFavourite()) {
            viewholder.recylerviewelemntImgFav.setImageResource(R.drawable.favouriteblue);
        } else {
            viewholder.recylerviewelemntImgFav.setImageResource(R.drawable.favourite_rec);
        }
        onLoadImageFromUrl(viewholder.recylerviewelemntImgMain, postDataList.get(i).getThumbnailFullPath(), context);
        viewholder.recylerviewelemntImgMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostDetails postDetails = new PostDetails();
                postDetails.post = postDataList.get(i);
                HelperMethod.replac(postDetails, ((AppCompatActivity) context).getSupportFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);
            }
        });
    }

    private void setaction(final viewholder viewHolder, final int position) {
        viewHolder.recylerviewelemntImgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiServices = getClient().create(ApiServices.class);
                try {
                apiServices.addremove(postDataList.get(position).getId(), SharedPreferencesManger.LoadData(activity, "api_token")).enqueue(new Callback<AddRemoveFav>() {
                    @Override
                    public void onResponse(Call<AddRemoveFav> call, Response<AddRemoveFav> response) {
                        if (response.body().getStatus() == 1) {
                            postDataList.get(position).setIsFavourite(!(postDataList.get(position).getIsFavourite()));
                            if (postDataList.get(position).getIsFavourite()) {
                                viewHolder.recylerviewelemntImgFav.setImageResource(R.drawable.favouriteblue);
                            } else {
                                viewHolder.recylerviewelemntImgFav.setImageResource(R.drawable.favourite_rec);

                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<AddRemoveFav> call, Throwable t) {

                    }
                });}catch (Exception e){}
            }
        });
    }


    @Override
    public int getItemCount() {
        return postDataList.size();
    }


    public static class viewholder extends RecyclerView.ViewHolder {

        View view;
        @BindView(R.id.recylerviewelemnt_tv)
        TextView recylerviewelemntTv;
        @BindView(R.id.recylerviewelemnt_img_fav)
        ImageView recylerviewelemntImgFav;
        @BindView(R.id.recylerviewelemnt_img_main)
        ImageView recylerviewelemntImgMain;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            ButterKnife.bind(this, view);

        }
    }

}


