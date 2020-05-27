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
import com.ahmed.bank.data.model.NotificationList.Datum;
import com.ahmed.bank.data.rest.ApiServices;
import com.ahmed.bank.helper.HelperMethod;
import com.ahmed.bank.ui.fragment.navigationcycle.Donationetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NontificationListAdapter extends RecyclerView.Adapter<NontificationListAdapter.vviewholder> {
    private Context context;
    private Activity activity;
    private ApiServices apiServices;
    private List<Datum> listofnotify = new ArrayList<>();

    public NontificationListAdapter(Context context, Activity activity, ApiServices apiServices, List<Datum> listofnotify) {
        this.context = context;
        this.activity = activity;
        this.apiServices = apiServices;
        this.listofnotify = listofnotify;
    }

    @NonNull
    @Override
    public vviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemofnotificationlistclass, null);
        NontificationListAdapter.vviewholder vviewholder = new vviewholder(view);
        return vviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull vviewholder vviewholder, final int i) {
        if (listofnotify.get(i).getPivot().getIsRead().equals("1")){
            vviewholder.notimg.setImageResource(R.drawable.notificationnotsel);
        }
        else {vviewholder.notimg.setImageResource(R.drawable.notification);}
        vviewholder.nottext.setText(listofnotify.get(i).getTitle());
        vviewholder.notifdate.setText(listofnotify.get(i).getCreatedAt());
        vviewholder.nottext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listofnotify.get(i).getPivot().setIsRead("1");
                Donationetails donationetails=new Donationetails();
                donationetails.donationreguestid=listofnotify.get(i).getDonationRequestId();
                HelperMethod.replac(donationetails, ((AppCompatActivity) context).getSupportFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);

            }
        });
        vviewholder.notifdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listofnotify.get(i).getPivot().setIsRead("1");
                Donationetails donationetails=new Donationetails();
                donationetails.donationreguestid=listofnotify.get(i).getDonationRequestId();
                HelperMethod.replac(donationetails, ((AppCompatActivity) context).getSupportFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);

            }
        });

    }






    @Override
    public int getItemCount() {
        return listofnotify.size();
    }

    public static class vviewholder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.notimg)
        ImageView notimg;
        @BindView(R.id.nottext)
        TextView nottext;
        @BindView(R.id.notifdate)
        TextView notifdate;
        public vviewholder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
