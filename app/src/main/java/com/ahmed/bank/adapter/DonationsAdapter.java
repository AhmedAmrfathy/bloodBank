package com.ahmed.bank.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ahmed.bank.R;
import com.ahmed.bank.data.model.Donations.Donation;
import com.ahmed.bank.data.rest.ApiServices;
import com.ahmed.bank.helper.HelperMethod;
import com.ahmed.bank.ui.fragment.navigationcycle.Donationetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DonationsAdapter extends RecyclerView.Adapter<DonationsAdapter.viewholder> {


    private List<Donation> donationdatalist = new ArrayList<>();
    private Context context;
    private Activity activity;
    private ApiServices apiServices;


    public DonationsAdapter(List<Donation> donationdatalist, Context context, Activity activity) {
        this.donationdatalist = donationdatalist;
        this.context = context;
        this.activity = activity;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.donationregrecyclerview, null);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, int i) {
        setdata(viewholder, i);
    }

    private void setdata(@NonNull final viewholder holder, final int position) {
        holder.DonationFragmentTvNameget.setText(donationdatalist.get(position).getPatientName());
        holder.DonationFragmentTvHospitalget.setText(donationdatalist.get(position).getHospitalName());
        holder.DonationFragmentTvCityget.setText(donationdatalist.get(position).getCity().getName());
        holder.DonationFragmentTvBloodtype.setText(donationdatalist.get(position).getBloodType().getName());
        holder.DonationFragmentBtCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + donationdatalist.get(position).getPhone()));
                context.startActivity(intent);
            }
        });
        holder.DonationFragmentBtDescr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.ahmed.bank.ui.fragment.navigationcycle.Donationetails donationetails = new Donationetails();
                donationetails.donationreguestid=String.valueOf(donationdatalist.get(position).getId());
                HelperMethod.replac(donationetails,((AppCompatActivity) context).getSupportFragmentManager(), R.id.activity_cycle_navigationdrawer_fl_container, null, null);
            }
        });

    }

    @Override
    public int getItemCount() {
        return donationdatalist.size();
    }


    public static class viewholder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.DonationFragment_rellay_bloodtype)
        RelativeLayout DonationFragmentRellayBloodtype;
        @BindView(R.id.DonationFragment_tv_name)
        TextView DonationFragmentTvName;
        @BindView(R.id.DonationFragment_tv_hospital)
        TextView DonationFragmentTvHospital;
        @BindView(R.id.DonationFragment_tv_city)
        TextView DonationFragmentTvCity;
        @BindView(R.id.DonationFragment_tv_nameget)
        TextView DonationFragmentTvNameget;
        @BindView(R.id.DonationFragment_tv_hospitalget)
        TextView DonationFragmentTvHospitalget;
        @BindView(R.id.DonationFragment_tv_cityget)
        TextView DonationFragmentTvCityget;
        @BindView(R.id.DonationFragment_bt_descr)
        Button DonationFragmentBtDescr;
        @BindView(R.id.DonationFragment_bt_call)
        Button DonationFragmentBtCall;
        @BindView(R.id.DonationFragment_tv_bloodtype)
        TextView DonationFragmentTvBloodtype;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }
    }


}
