package com.ahmed.bank.ui.fragment.usercycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmed.bank.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeUserFragment extends Fragment {


    public HomeUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_user, container, false);
    }

}
