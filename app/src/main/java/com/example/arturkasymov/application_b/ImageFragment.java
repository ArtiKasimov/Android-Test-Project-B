package com.example.arturkasymov.application_b;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ImageFragment extends Fragment {

    private final String URL_KEY = "com.example.arturkasymov.application_a.image_URL";
    private final String ID_KEY = "com.example.arturkasymov.application_a.FRAGMENT_ID";
    private final String TEST_FRAGMENT_ID= "1";
    private final String HISTORY_FRAGMENT_ID= "2";
    private String mImage_URL;
    private String mFragmentID;

    public ImageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_image, container, false);

        Bundle bundle = this.getArguments();
        mImage_URL = bundle.getString(URL_KEY);
        mFragmentID= bundle.getString(ID_KEY);

        if( mFragmentID==TEST_FRAGMENT_ID){


        } else  if( mFragmentID==HISTORY_FRAGMENT_ID)
        {

        }

        ///// for testing data
        TextView tv= (TextView) v.findViewById(R.id.textViewForTesting);
        tv.setText("URL= "+ mImage_URL+ "\n"+"FragmentID="+ mFragmentID );
        /////

        return v;
    }



}
