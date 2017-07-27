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
public class policestation_report extends Fragment {

String value;
    public policestation_report() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_policestation_report, container, false);
        value=getArguments().getString("h");
        return view;
    }

}
