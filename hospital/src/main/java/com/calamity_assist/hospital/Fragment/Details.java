package com.calamity_assist.hospital.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.calamity_assist.hospital.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Details extends Fragment {

String name,address,phone;
    public Details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_details, container, false);

        name=getArguments().getString("Name");
        address=getArguments().getString("address");
        phone=getArguments().getString("phone");

        Toast.makeText(getActivity(), "Name"+name+"Address"+address+"phone"+phone, Toast.LENGTH_SHORT).show();
        return view;
    }

}
