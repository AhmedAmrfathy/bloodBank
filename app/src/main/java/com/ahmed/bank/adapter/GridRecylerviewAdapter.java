package com.ahmed.bank.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ahmed.bank.R;
import com.ahmed.bank.data.model.generalResponse.GeneralResponseData;
import com.ahmed.bank.data.rest.ApiServices;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridRecylerviewAdapter extends RecyclerView.Adapter<GridRecylerviewAdapter.viewholder> {


    private List<GeneralResponseData> bloodlist = new ArrayList<>();
    private List<String> oldselectedids = new ArrayList<>();
    public List<String> selectedids = new ArrayList<>();
    private Context context;
    private Activity activity;
    private ApiServices apiServices;


    public GridRecylerviewAdapter(List<GeneralResponseData> bloodlist, List<String> oldselectedids, Context context, Activity activity, ApiServices apiServices) {
        this.bloodlist = bloodlist;
        this.oldselectedids = oldselectedids;
        this.context = context;
        this.activity = activity;
        this.apiServices = apiServices;
    }


    @NonNull
    @Override
    public GridRecylerviewAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid_recycler_view_elemnt, null);
        GridRecylerviewAdapter.viewholder viewholder = new GridRecylerviewAdapter.viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridRecylerviewAdapter.viewholder viewholder, int i) {
        setdata(viewholder, i);
        setAction(viewholder, i);

    }

    private void setdata(@NonNull final GridRecylerviewAdapter.viewholder holder, final int position) {
        try {
            holder.checkbox.setText(bloodlist.get(position).getName());
            for (int i = 0; i < oldselectedids.size(); i++) {
                if (oldselectedids.get(i).equals(String.valueOf(bloodlist.get(position).getId()))) {
                    holder.checkbox.setChecked(true);
                    selectedids.add(String.valueOf(bloodlist.get(position).getId()));
                } else {
                    holder.checkbox.setChecked(false);
                }


            }

        } catch (Exception e) {
        }
    }

    private void setAction(final viewholder viewholder, final int i) {

            viewholder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        selectedids.add(String.valueOf(bloodlist.get(i).getId()));
                    } else {
                        for (int j = 0; j < selectedids.size(); j++) {
                            if (selectedids.get(j).equals(String.valueOf(bloodlist.get(i).getId()))) {
                                selectedids.remove(j);
                            }
                        }
                    }
                }
            });

    }

    @Override
    public int getItemCount() {
        return bloodlist.size();
    }


    public static class viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.checkbox)
        CheckBox checkbox;
        private View view;


        public viewholder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }
    }
}
