package com.mahfa.dnswitch;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;


/**
 * Created by mohsen.falahi on 7/16/2017.
 */

public class DayNightSwitch extends View implements Animator.AnimatorListener {

    private boolean isAnimating = false;

    private final GradientDrawable lightBackDrawable;
    private final BitmapDrawable darkBackBitmap;
    private final BitmapDrawable sunBitmap;
    private final BitmapDrawable moonBitmap;
    private final BitmapDrawable cloudsBitmap;

    private float value;
    private boolean isNight;
    private int duration;


    private DayNightSwitchListener listener;

    private DayNightSwitchAnimListener animListener;


    public DayNightSwitch(Context context) {
        this(context, null, 0);
    }

    public DayNightSwitch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayNightSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);

        value = 0;
        isNight = false;
        duration = 500;

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        lightBackDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT
                , new int[]{Color.parseColor("#21b5e7"), Color.parseColor("#59ccda")});
        lightBackDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);


        darkBackBitmap = (BitmapDrawable) getContext().getResources().getDrawable(R.drawable.dark_background);
        sunBitmap = (BitmapDrawable) getContext().getResources().getDrawable(R.drawable.img_sun);
        moonBitmap = (BitmapDrawable) getContext().getResources().getDrawable(R.drawable.img_moon);
        cloudsBitmap = (BitmapDrawable) getContext().getResources().getDrawable(R.drawable.img_clouds);
    }

    public void toggle() {
        if (!isAnimating) {
            isAnimating = true;
            isNight = !isNight;
            if (listener != null)
                listener.onSwitch(isNight);
            startAnimation();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int space = getWidth() - getHeight();

        darkBackBitmap.setBounds(0, 0, getWidth(), getHeight());
        darkBackBitmap.setAlpha((int) (value * 255));
        darkBackBitmap.draw(canvas);

        lightBackDrawable.setCornerRadius((float) getHeight() / 2);
        lightBackDrawable.setBounds(0, 0, getWidth(), getHeight());
        lightBackDrawable.setAlpha(255 - ((int) (value * 255)));
        lightBackDrawable.draw(canvas);


        moonBitmap.setBounds((space) - (int) (value * space)
                , 0
                , getWidth() - (int) (value * space)
                , getHeight());
        moonBitmap.setAlpha((int) (value * 255));
        moonBitmap.getBitmap();

        sunBitmap.setBounds((space) - (int) (value * space)
                , 0
                , getWidth() - (int) (value * space)
                , getHeight());
        sunBitmap.setAlpha(255 - ((int) (value * 255)));

        moonBitmap.draw(canvas);
        sunBitmap.draw(canvas);

        int clouds_bitmap_left = (int) ((getHeight() / 2) - (value * (getHeight() / 2)));
        cloudsBitmap.setBounds(clouds_bitmap_left
                , 0
                , clouds_bitmap_left + getHeight()
                , getHeight());

        cloudsBitmap.setAlpha(cloudBitmapAlpha());

        cloudsBitmap.draw(canvas);
    }

    private int cloudBitmapAlpha() {
        if (value <= 0.5) {
            double a = (value - 0.5) * 2 * 255;
            return 255 - Math.min(Math.max((int) a, 0), 255);
        }
        return 0;
    }

    private void startAnimation() {
        final ValueAnimator va = ValueAnimator.ofFloat(0, 1);
        if (value == 1)
            va.setFloatValues(1, 0);

        va.setDuration(duration);
        va.addListener(this);
        va.setInterpolator(new DecelerateInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                value = (float) animation.getAnimatedValue();
                if (animListener != null)
                    animListener.onAnimValueChanged(value);
                invalidate();
            }
        });
        va.start();
    }

    @Override
    public void onAnimationStart(Animator animation) {
        if (animListener != null)
            animListener.onAnimStart();
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        isAnimating = false;
        if (animListener != null)
            animListener.onAnimEnd();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    public void setIsNight(boolean is_night, boolean trigger_listener) {
        this.isNight = is_night;
        value = is_night ? 1 : 0;
        invalidate();
        if (listener != null && trigger_listener)
            listener.onSwitch(is_night);

    }

    public boolean isNight() {
        return isNight;
    }

    public void setListener(DayNightSwitchListener listener) {
        this.listener = listener;
    }


    public void setAnimListener(DayNightSwitchAnimListener animListener) {
        this.animListener = animListener;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
