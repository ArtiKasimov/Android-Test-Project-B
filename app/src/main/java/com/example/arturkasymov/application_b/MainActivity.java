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
    private final String EXTRA_IMAGE_URL = "com.example.arturkasymov.application_a.image_URL";
    private final String URL_KEY = "com.example.arturkasymov.application_a.image_URL";
    private final String ID_KEY = "com.example.arturkasymov.application_a.FRAGMENT_ID";
    private String mImage_URL;
    private String mFragmentID;
    private File myDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1){
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1000);

        myDir = new File("/sdcard/BIGDIG/test/B");
        }else{
            myDir = new File(this.getFilesDir() + "/BIGDIG/test/B/");
        }

        if (!myDir.exists()) {
        myDir.mkdirs();
        }

        Fragment fragment;
        if (getIntent().hasExtra(EXTRA_FRAGMENT_ID)){
            fragment=new ImageFragment();
            mImage_URL = getIntent().getExtras().getString(EXTRA_IMAGE_URL);

            mFragmentID= getIntent().getExtras().getString(EXTRA_FRAGMENT_ID);

            String namber = getIntent().getStringExtra("ID");

            Bundle bundle = new Bundle();
            bundle.putString(URL_KEY, mImage_URL);
            bundle.putString(ID_KEY, mFragmentID);
            bundle.putString("namber", namber);
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
