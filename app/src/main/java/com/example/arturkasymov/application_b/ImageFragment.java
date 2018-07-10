package com.example.arturkasymov.application_b;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;


public class ImageFragment extends Fragment {

    private final String URL_KEY = "com.example.arturkasymov.application_a.image_URL";
    private final String ID_KEY = "com.example.arturkasymov.application_a.FRAGMENT_ID";
    private String mImage_URL;
    private int id;
    private String data;
    private String mFragmentID;
    private final String KEY_ID = "id";
    private final String KEY_REFERENCE = "reference";
    private final String KEY_STATUS = "status";
    private final String KEY_TIME = "time";
    private final String KEY_ROW_ID = "namber";
    private final String CONTENT_URI = "content://com.misha.database.provider.MyContentProvider/refs";

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

        } else{
            id = Integer.parseInt(bundle.getString(KEY_ROW_ID));
            int inputStatus = getRow(id);
            loadImageFromUrl(mImage_URL);
            if (inputStatus == 1) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    myDir = new File("/sdcard/BIGDIG/test/B");
                } else {
                    myDir = new File(getContext().getFilesDir() + "/BIGDIG/test/B/");
                }
                if (!myDir.exists()) {
                    myDir.mkdirs();
                }
                v.postDelayed(new Runnable() {
                    public void run() {
                        loadPicture2();
                    }
                }, 500);



                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        delete(id);
                    }
                }).start();


            }
        }
        return v;
    }

    private void loadImageFromUrl(String url) {
        Picasso.with(getContext()).load(url)
        .error(R.mipmap.ic_launcher)
        .into(imageView,new  com.squareup.picasso.Callback(){

            @Override
            public void onSuccess() {
                updateRow(id,1);
            }

            @Override
            public void onError() {
                updateRow(id, 2);
            }
        });
    }

    public void delete(int id){
        ContentResolver contentResolver = getContext().getContentResolver();
        String selection = KEY_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        contentResolver.delete(Uri.parse(CONTENT_URI),selection,selectionArgs);
    }

    public void updateRow(int id, int status){
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

    public int getRow(int id){
        ContentResolver contentResolver = getContext().getContentResolver();
        String selection = KEY_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        Cursor cursor = contentResolver.query(Uri.parse(CONTENT_URI),
                new String[]{KEY_ID,
                KEY_REFERENCE, KEY_STATUS, KEY_TIME},
                selection,selectionArgs, null);

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
            //ex.printStackTrace();
        }
    }

    public void loadPicture2(){
        Picasso picasso =
        Picasso.with(getContext());
        final RequestCreator requestCreator = picasso.load(mImage_URL);
        requestCreator.fetch(new Callback() {
            @Override
            public void onSuccess() {
                requestCreator.into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        try {
                            String name;
                            boolean isPNG = mImage_URL.length() - mImage_URL.lastIndexOf(".png") == 4;
                            FileOutputStream out;
                            if (isPNG){
                                name = new Date().toString() + ".png";
                                myDir = new File(myDir, name);
                                out = new FileOutputStream(myDir);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                            }else {
                                name = new Date().toString() + ".jpg";
                                myDir = new File(myDir, name);
                                out = new FileOutputStream(myDir);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            }
                            out.flush();
                            out.close();
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
            }

            @Override
            public void onError() {
            }
        });

    }
}
