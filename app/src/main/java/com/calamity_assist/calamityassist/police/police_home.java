package com.calamity_assist.calamityassist.police;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calamity_assist.calamityassist.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class police_home extends Fragment {


    public police_home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_police_home, container, false);
    }

}
