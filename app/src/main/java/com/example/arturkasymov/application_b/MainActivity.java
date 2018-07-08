package com.example.arturkasymov.application_b;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private final String EXTRA_FRAGMENT_ID = "com.example.arturkasymov.application_a.FRAGMENT_ID";
    private String mFragmentID;
    private final String EXTRA_IMAGE_URL = "com.example.arturkasymov.application_a.image_URL";
    private String mImage_URL;

    private final String URL_KEY = "com.example.arturkasymov.application_a.image_URL";
    private final String ID_KEY = "com.example.arturkasymov.application_a.FRAGMENT_ID";
    private final String CASE = "case";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        File myDir;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1){
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1000);
            String root = Environment.getExternalStorageDirectory().getAbsolutePath().toString();

        myDir = new File("/sdcard/BIGDIG/test/B");
        }else{
            myDir = new File(this.getFilesDir() + "/BIGDIG/test/B/");
        }

        if (!myDir.exists()) {
        myDir.mkdirs();
        }
        Toast toast;

        Fragment fragment=null;
        if (getIntent().hasExtra(EXTRA_FRAGMENT_ID)){
            fragment=new ImageFragment();
            mImage_URL = getIntent().getExtras().getString(EXTRA_IMAGE_URL);
            //toast = Toast.makeText(this,mImage_URL,5);
            //toast.show();
            mFragmentID= getIntent().getExtras().getString(EXTRA_FRAGMENT_ID);
            //toast = Toast.makeText(this,mFragmentID,5);
            //toast.show();
            String namber = getIntent().getStringExtra("ID");
            //toast = Toast.makeText(this,namber,5);
            //toast.show();
            Bundle bundle = new Bundle();
            bundle.putString(URL_KEY, mImage_URL);
            bundle.putString(ID_KEY, mFragmentID);
            bundle.putString("namber", namber);
            if (mFragmentID.equals("1")){
                bundle.putInt(CASE,1);
            }else {
                bundle.putInt(CASE,2);
            }
            fragment.setArguments(bundle);

        } else
        {
            fragment= new LauncherFragment();
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

    }

}
