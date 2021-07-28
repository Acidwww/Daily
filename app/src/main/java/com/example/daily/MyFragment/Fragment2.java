package com.example.daily.MyFragment;

//import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daily.R;


public class Fragment2 extends Fragment {

    private static final String ARG_PARAM1 = "param1";


    private String mParam1;
    View rootview;




    public Fragment2() {

    }


    public static Fragment2 newInstance(String param1) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootview == null) {

            rootview = inflater.inflate(R.layout.fragment_fragment2, container, false);
        }
        initview();
        return rootview;
    }

    private void initview() {
        TextView tv = rootview.findViewById(R.id.text_2);
        tv.setText(mParam1);
    }

}
