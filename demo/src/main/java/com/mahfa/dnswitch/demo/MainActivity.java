package com.mahfa.dnswitch.demo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchAnimListener;
import com.mahfa.dnswitch.DayNightSwitchListener;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final String KEY_DAY_NIGHT_SWITCH_STATE = "day_night_switch_state";

    private DayNightSwitch day_night_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View background_view = findViewById(R.id.background_view);

        day_night_switch = (DayNightSwitch) findViewById(R.id.day_night_switch);
        day_night_switch.setDuration(450);

        day_night_switch.setListener(new DayNightSwitchListener() {
            @Override
            public void onSwitch(boolean is_night) {
                Log.d(TAG, "onSwitch() called with: is_night = [" + is_night + "]");
                if (is_night)
                    background_view.setAlpha(1f);

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

        if (savedInstanceState != null
                && savedInstanceState.containsKey(KEY_DAY_NIGHT_SWITCH_STATE))
            day_night_switch.setIsNight(savedInstanceState.getBoolean(KEY_DAY_NIGHT_SWITCH_STATE) , true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_DAY_NIGHT_SWITCH_STATE, day_night_switch.isNight());
    }
}
