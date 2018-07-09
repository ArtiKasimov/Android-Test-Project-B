package com.example.arturkasymov.application_b;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class LauncherFragment extends Fragment {
    private TextView mTimer;

    public LauncherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_launcher, container, false);
        mTimer= (TextView) v.findViewById(R.id.textView);
        new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                mTimer.setText( getString(R.string.forTimerpart_1)
                        + " "+ millisUntilFinished / 1000 +" " +getString(R.string.forTimerpart_2));
            }
            public void onFinish() {
                getActivity().finish();
            }
        } .start();
        return  v;
    }
}
