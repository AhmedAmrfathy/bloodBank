package com.ahmed.bank.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ahmed.bank.ui.fragment.navigationcycle.DonationFragment;
import com.ahmed.bank.ui.fragment.navigationcycle.PostFragment;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment>fragmentlist=new ArrayList<>();
    private final List<String>titlelist=new ArrayList<>();

    public PagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int i) {
   return  fragmentlist.get(i);
    }

    @Override
    public int getCount() {
        return fragmentlist.size();

    }
    public void addfragment(Fragment fragment,String title){
        fragmentlist.add(fragment);
        titlelist.add(title);


    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titlelist.get(position);
    }
}
