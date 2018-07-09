package com.example.arturkasymov.application_b;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    private String mImage_URL;
    private int id;
    private int status = 3;
    private String data;
    private String mFragmentID;
    public static final String KEY_ID = "id";
    private static final String KEY_REFERENCE = "reference";
    private static final String KEY_STATUS = "status";
    private static final String KEY_TIME = "time";
    private static final String CONTENT_URI = "content://com.misha.database.provider.MyContentProvider/refs";

    private ImageView imageView;
    private File myDir;



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
        imageView = v.findViewById(R.id.imageView);

        if(mFragmentID.equals("1")){
            addRow();
            loadImageFromUrl(mImage_URL);
            updateRow();


        } else{
            id = Integer.parseInt(bundle.getString("namber"));
            getRow(id);
            loadImageFromUrl(mImage_URL);
            updateRow();

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
                myDir = new File("/sdcard/BIGDIG/test/B");
            }else{
                myDir = new File(getContext().getFilesDir() + "/BIGDIG/test/B/");
            }
            if (!myDir.exists()) {
                myDir.mkdirs();
            }
            loadPicture2();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    delete(id);
                }
            }).start();
            v.postDelayed(new Runnable() {
                public void run() {
                    Toast toast;
                    toast = Toast.makeText(getContext(),"delated",10);
                    toast.show();
                }
            }, 5000);

        }

        return v;
    }

    private void loadImageFromUrl(String url) {
        Picasso.Listener p = new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                status = 2;
            }
        };
        Picasso.with(getContext()).load(url)
                //.placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView,new  com.squareup.picasso.Callback(){

                    @Override
                    public void onSuccess() {
                        status = 1;
                    }

                    @Override
                    public void onError() {
                        status = 2;
                    }
                });
    }

    public void delete(int id){
        ContentResolver contentResolver = getContext().getContentResolver();
        String selection = KEY_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        contentResolver.delete(Uri.parse(CONTENT_URI),selection,selectionArgs);
    }

    public void updateRow(){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Toast toast;
                toast = Toast.makeText(getContext(),"qqq" + status,10);
                toast.show();
                getRow(id);
                ContentResolver contentResolver = getContext().getContentResolver();
                ContentValues values = new ContentValues();
                values.put(KEY_REFERENCE, mImage_URL);
                values.put(KEY_STATUS, status);
                values.put(KEY_TIME, data);
                String selection = KEY_ID + "=?";
                String[] selectionArgs = new String[]{String.valueOf(id)};
                contentResolver.update(Uri.parse(CONTENT_URI),values,selection,selectionArgs);
            }
        }, 1700);// if set less delay, row update before method "onError" will be performed!!


    }

    public int getRow(int id){
        ContentResolver contentResolver = getContext().getContentResolver();
        String selection = KEY_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        Cursor cursor = contentResolver.query(Uri.parse(CONTENT_URI),new String[]{KEY_ID,
                KEY_REFERENCE, KEY_STATUS, KEY_TIME}, selection,selectionArgs, null);

        if (cursor != null)
            cursor.moveToFirst();

        mImage_URL = cursor.getString(1);
        int status = Integer.parseInt(cursor.getString(2));
        data = cursor.getString(3);
        return status;
    }

    public void  addRow(){
        try {
            ContentResolver contentResolver = getContext().getContentResolver();
            ContentValues values = new ContentValues();
            values.put(KEY_REFERENCE, mImage_URL);
            values.put(KEY_STATUS, 3);
            values.put(KEY_TIME, new Date().toString());

            Uri uri = contentResolver.insert(Uri.parse(CONTENT_URI),values);
            id = Integer.parseInt(uri.getLastPathSegment());


        }catch (Exception ex){

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

                              } catch(Exception e){
                                  Log.e("Downloading", ""+e);
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
    }
}
