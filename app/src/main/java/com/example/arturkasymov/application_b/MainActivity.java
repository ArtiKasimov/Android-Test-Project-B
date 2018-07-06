package com.example.arturkasymov.application_b;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.os.CountDownTimer;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private final String EXTRA_FRAGMENT_ID = "com.example.arturkasymov.application_a.FRAGMENT_ID";
    private String mFragmentID;
    private final String EXTRA_IMAGE_URL = "com.example.arturkasymov.application_a.image_URL";
    private String mImage_URL;

    private final String URL_KEY = "com.example.arturkasymov.application_a.image_URL";
    private final String ID_KEY = "com.example.arturkasymov.application_a.FRAGMENT_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment=null;
        if (getIntent().hasExtra(EXTRA_FRAGMENT_ID)){
            fragment=new ImageFragment();
            mImage_URL = getIntent().getExtras().getString(EXTRA_IMAGE_URL);
            mFragmentID= getIntent().getExtras().getString(EXTRA_FRAGMENT_ID);
            Bundle bundle = new Bundle();
            bundle.putString(URL_KEY, mImage_URL);
            bundle.putString(ID_KEY, mFragmentID);
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
