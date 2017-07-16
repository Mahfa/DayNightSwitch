package com.mahfa.dnswitch.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchAnimListener;
import com.mahfa.dnswitch.DayNightSwitchListener;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DayNightSwitch day_night_switch = (DayNightSwitch) findViewById(R.id.day_night_switch);
        final View background_view = findViewById(R.id.background_view);

        day_night_switch.setListener(new DayNightSwitchListener() {
            @Override
            public void onSwitch(boolean is_night) {
                Log.d(TAG, "onSwitch() called with: is_night = [" + is_night + "]");
            }
        });

        day_night_switch.setAnimListener(new DayNightSwitchAnimListener() {
            @Override
            public void onAnimStart() {
                Log.d(TAG, "onAnimStart() called");
            }

            @Override
            public void onAnimEnd() {
                Log.d(TAG, "onAnimEnd() called");
            }

            @Override
            public void onAnimValueChanged(float value) {
                background_view.setAlpha(value);
                Log.d(TAG, "onAnimValueChanged() called with: value = [" + value + "]");
            }
        });
    }
}
