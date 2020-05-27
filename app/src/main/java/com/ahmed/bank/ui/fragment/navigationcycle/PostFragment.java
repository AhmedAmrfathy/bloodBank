package com.ahmed.bank.ui.fragment.navigationcycle;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.ahmed.bank.R;
import com.ahmed.bank.adapter.PostsAdapter;
import com.ahmed.bank.data.local.SharedPreferencesManger;
import com.ahmed.bank.data.model.filter.Filter;
import com.ahmed.bank.data.model.generalResponse.GeneralResponse;
import com.ahmed.bank.data.model.getallposts.AllPosts;
import com.ahmed.bank.data.model.getallposts.PostData;
import com.ahmed.bank.data.rest.ApiServices;
import com.ahmed.bank.helper.OnEndless;

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
public class PostFragment extends Fragment {


    @BindView(R.id.activity_cycle_navigationdrawer_spn_catg)
    AppCompatSpinner activityCycleNavigationdrawerSpnCatg;
    @BindView(R.id.activity_cycle_navigationdrawer_ed_search)
    TextInputEditText activityCycleNavigationdrawerEdSearch;
    @BindView(R.id.activity_cycle_navigationdrawer_recylerview_el)
    RecyclerView activityCycleNavigationdrawerRecylerviewEl;
    Unbinder unbinder;
    @BindView(R.id.activity_cycle_navigationdrawer_img_search)
    ImageView activityCycleNavigationdrawerImgSearch;

    private ApiServices apiServices;
    private List<PostData> postDataList = new ArrayList<>();

    private LinearLayoutManager linearLayoutManager;
    private PostsAdapter postsAdapter;
    private int maxPage = 0;
    private OnEndless onEndless;
    private ArrayList<Integer> ids = new ArrayList<>();
    private int category_id = 0;

    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = getClient().create(ApiServices.class);

        initRecyclerView();
        getCategoury();
        getPosts(1);

        return view;
    }

    private void getCategoury() {
        try{
        apiServices.getCategey().enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.body().getStatus() == 1) {
                    final ArrayList<String> name = new ArrayList<>();
                    ids = new ArrayList<>();
                    name.add("Categey");
                    ids.add(0);
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        name.add(response.body().getData().get(i).getName());
                        ids.add(response.body().getData().get(i).getId());
                    }
                    ArrayAdapter<String> spinnerData = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, name);
                    activityCycleNavigationdrawerSpnCatg.setAdapter(spinnerData);
                    activityCycleNavigationdrawerSpnCatg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            category_id = ids.get(position);
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

    public void filter(int page) {
        String searchedword = activityCycleNavigationdrawerEdSearch.getText().toString();
        try {


        apiServices.Filterdata(SharedPreferencesManger.LoadData(getActivity(), "api_token"), page, searchedword, category_id)
                .enqueue(new Callback<Filter>() {
                    @Override
                    public void onResponse(Call<Filter> call, Response<Filter> response) {
                        if (response.body().getStatus() == 1) {
                            postDataList.addAll(response.body().getData().getData());
                            postsAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onFailure(Call<Filter> call, Throwable t) {

                    }
                });}catch (Exception e){}
    }


    private void initRecyclerView() {

        linearLayoutManager = new LinearLayoutManager(getActivity());
        activityCycleNavigationdrawerRecylerviewEl.setLayoutManager(linearLayoutManager);

        onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (maxPage != 0 || current_page != 1) {
                    getPosts(current_page);
                }

            }
        };

        activityCycleNavigationdrawerRecylerviewEl.addOnScrollListener(onEndless);


        postsAdapter = new PostsAdapter(postDataList, getActivity(), getActivity());
        activityCycleNavigationdrawerRecylerviewEl.setAdapter(postsAdapter);

    }

    private void getPosts(int page) {
try{
        apiServices.getAllPosts(SharedPreferencesManger.LoadData(getActivity(), "api_token"), page).enqueue(new Callback<AllPosts>() {
            @Override
            public void onResponse(Call<AllPosts> call, Response<AllPosts> response) {
                if (response.body().getStatus() == 1) {
                    maxPage = response.body().getData().getLastPage();
                    postDataList.addAll(response.body().getData().getData());
                    postsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<AllPosts> call, Throwable t) {

            }
        });}catch (Exception e){}
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.activity_cycle_navigationdrawer_img_search)
    public void onViewClicked() {

        filter(1);

    }


}
