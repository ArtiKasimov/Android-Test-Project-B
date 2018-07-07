package com.example.arturkasymov.application_b;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;


public class ImageFragment extends Fragment {

    private final String URL_KEY = "com.example.arturkasymov.application_a.image_URL";
    private final String ID_KEY = "com.example.arturkasymov.application_a.FRAGMENT_ID";
    private final String TEST_FRAGMENT_ID= "1";
    private final String HISTORY_FRAGMENT_ID= "2";
    private String mImage_URL;
    private String mFragmentID;
    private static final String KEY_REFERENCE = "reference";
    private static final String KEY_STATUS = "status";
    private static final String KEY_TIME = "time";
    private static final String CONTENT_URI = "content://com.misha.database.provider.MyContentProvider/refs";
    ImageView imageView;

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

        imageView = v.findViewById(R.id.imageView);
        loadImageFromUrl(mImage_URL);


        ///// for testing data
        TextView tv= (TextView) v.findViewById(R.id.textViewForTesting);
        tv.setText("URL= "+ mImage_URL+ "\n"+"FragmentID="+ mFragmentID );
        /////

        //deleteAllRows();
        addRow();

        return v;
    }

    private void loadImageFromUrl(String url) {
        Picasso.with(getContext()).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView,new  com.squareup.picasso.Callback(){

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    public void  deleteAllRows(){
        try {
            ContentResolver contentResolver = getContext().getContentResolver();
            String selections = null;
            String[] selectionsArgs = null;
            int numberRowsDeleted = contentResolver.delete(Uri.parse(CONTENT_URI), selections,
                    selectionsArgs);
           // mTimer.setText(""+ numberRowsDeleted);
        }catch (Exception ex){
           // mTimer.setText("exeption");
        }

    }

    public void  addRow(){
        try {
            ContentResolver contentResolver = getContext().getContentResolver();
            ContentValues values = new ContentValues();
            //values.put(KEY_ID, re_cord.getId());
            values.put(KEY_REFERENCE, mImage_URL);
            values.put(KEY_STATUS, 1);
            values.put(KEY_TIME,0);

            contentResolver.insert(Uri.parse(CONTENT_URI),values);
            //mTimer.setText("successful");
        }catch (Exception ex){
           // mTimer.setText("exeption");
        }
    }

}
