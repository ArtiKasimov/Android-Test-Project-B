package com.example.arturkasymov.application_b;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;


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
    TextView tv;
    File myDir;

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
        addRow();
        loadImageFromUrl(mImage_URL);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            //String root = Environment.getExternalStorageDirectory().getAbsolutePath().toString();

            myDir = new File("/sdcard/BIGDIG/test/B");
        }else{
            myDir = new File(getContext().getFilesDir() + "/BIGDIG/test/B/");
        }

        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        
        loadPicture2();

        ///// for testing data
        tv= (TextView) v.findViewById(R.id.textViewForTesting);
        tv.setText("URL= "+ mImage_URL+ "\n"+"FragmentID="+ mFragmentID );

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

    public void loadPicture2(){
        Picasso.with(getContext())
                .load(mImage_URL)
                .into(new Target() {
                          @Override
                          public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                              try {

                                  String name = new Date().toString() + ".jpg";
                                  myDir = new File(myDir, name);
                                  FileOutputStream out = new FileOutputStream(myDir);
                                  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                                  out.flush();
                                  out.close();
                                  mImage_URL = "succesful";
                              } catch(Exception e){
                                  mImage_URL = "1";
                              }
                          }

                          @Override
                          public void onBitmapFailed(Drawable errorDrawable) {
                          }

                          @Override
                          public void onPrepareLoad(Drawable placeHolderDrawable) {
                          }
                      }
                );
        //tv.setText("SUCCESFUL");
    }
}
