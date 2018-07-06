package com.example.arturkasymov.application_b;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.os.CountDownTimer;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private TextView mTimer;

    private final String EXTRA_IMAGE_URL = "com.example.arturkasymov.application_a.image_URL";
    private String image_URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimer= (TextView) findViewById(R.id.textView);
        if (getIntent().hasExtra(EXTRA_IMAGE_URL)) {
            image_URL = getIntent().getExtras().getString(EXTRA_IMAGE_URL);
            

        } else {

            ////////////////////////////  Впихнуть в фрагмент
            new CountDownTimer(10000, 1000) {
                public void onTick(long millisUntilFinished) {
                    mTimer.setText( getString(R.string.forTimerpart_1)+ " "
                            + millisUntilFinished / 1000 +" " +getString(R.string.forTimerpart_2));
                }
                public void onFinish() {
                    finish();
                }
            } .start();
           /////////////////////////////
        }
    }
}
