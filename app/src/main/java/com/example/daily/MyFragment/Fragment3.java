package com.example.daily.MyFragment;

//import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daily.R;


public class Fragment3 extends Fragment {

    private static final String ARG_PARAM1 = "param1";


    private String mParam1;
    View rootview;




    public Fragment3() {

    }


    public static Fragment3 newInstance(String param1) {
        Fragment3 fragment = new Fragment3();
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

            rootview = inflater.inflate(R.layout.fragment_fragment3, container, false);
        }
        initview();
        return rootview;
    }

    private void initview() {
        TextView tv = rootview.findViewById(R.id.text_3);
        tv.setText(mParam1);
    }

}
