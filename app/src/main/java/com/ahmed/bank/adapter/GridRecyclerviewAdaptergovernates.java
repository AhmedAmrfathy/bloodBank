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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridRecyclerviewAdaptergovernates extends RecyclerView.Adapter<GridRecyclerviewAdaptergovernates.viewholder> {

    private List<GeneralResponseData> dataList = new ArrayList<>();
    private List<String> oldSelectedIds = new ArrayList<>();
    private Context context;
    private Activity activity;

    public List<String> selectedIds = new ArrayList<>();

    public GridRecyclerviewAdaptergovernates(List<GeneralResponseData> governlist, List<String> oldSelectedIds, Context context, Activity activity) {
        this.dataList = governlist;
        this.oldSelectedIds = oldSelectedIds;
        this.context = context;
        this.activity = activity;

    }

    @NonNull
    @Override
    public GridRecyclerviewAdaptergovernates.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid_recycler_view_elemnt, null);
        GridRecyclerviewAdaptergovernates.viewholder viewholder = new GridRecyclerviewAdaptergovernates.viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, int i) {
        setData(viewholder, i);

        setAction(viewholder, i);
    }

    private void setData(@NonNull final GridRecyclerviewAdaptergovernates.viewholder holder, final int position) {
        holder.checkbox.setText(dataList.get(position).getName());
        if (oldSelectedIds.contains(String.valueOf(dataList.get(position).getId()))) {
            holder.checkbox.setChecked(true);
            selectedIds.add(String.valueOf(dataList.get(position).getId()));
        } else {
            holder.checkbox.setChecked(false);
        }
    }

    private void setAction(final viewholder holder, final int position) {
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    selectedIds.add(String.valueOf(dataList.get(position).getId()));
                }
                else {
                    for (int i = 0; i < selectedIds.size(); i++) {
                        if (selectedIds.get(i).equals(String.valueOf(dataList.get(position).getId()))) {
                            selectedIds.remove(i);
                        }
                    }
                }
            }
        });


//        holder.checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.checkbox.isChecked()) {
//                    selectedIds.add(String.valueOf(dataList.get(position).getId()));
//                } else {
//                    for (int i = 0; i <= selectedIds.size(); i++) {
//                        if (selectedIds.get(i).equals(dataList.get(position).getId())) {
//                            selectedIds.remove(i);
//                        }
//                    }
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
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

