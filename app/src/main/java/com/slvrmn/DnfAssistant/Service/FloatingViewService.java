package com.slvrmn.DnfAssistant.Service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.slvrmn.DnfAssistant.R;
import com.slvrmn.DnfAssistant.Tools.MLog;
import com.slvrmn.DnfAssistant.Tools.Toast;

public class FloatingViewService extends Service implements View.OnClickListener {

    public static boolean isStarted = false;
    private WindowManager windowManager;
    private View floatingView;
    private View expandedView;
    private boolean dragging;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatingView != null) windowManager.removeView(floatingView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startAssistant();
                break;
            case R.id.btn_stop:
                stopAssistant();
                break;
            case R.id.btn_close:
                destroy();
                break;
        }
    }

    private void startAssistant() {
        //TODO
    }

    private void stopAssistant() {
        //TODO
    }

    private void destroy() {
        stopAssistant();
        //closing the widget
        stopSelf();
    }

    private class mainOnTouchListener implements View.OnTouchListener {
        private int initialX;
        private int initialY;
        private float initialTouchX;
        private float initialTouchY;
        private final WindowManager.LayoutParams params;

        public mainOnTouchListener(WindowManager.LayoutParams params) {
            super();
            this.params = params;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    initialX = params.x;
                    initialY = params.y;
                    initialTouchX = event.getRawX();
                    initialTouchY = event.getRawY();
                    return true;

                case MotionEvent.ACTION_UP:
                    if (!dragging) {
                        if (expandedView.getVisibility() == View.VISIBLE) {
                            expandedView.setVisibility(View.INVISIBLE);
                        } else {
                            expandedView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        dragging = false;
                    }
                    return true;

                case MotionEvent.ACTION_MOVE:
                    //this code is helping the widget to move around the screen with fingers
                    if (!dragging) {
                        dragging = true;
                    }
                    params.x = initialX + (int) (event.getRawX() - initialTouchX);
                    params.y = initialY + (int) (event.getRawY() - initialTouchY);
                    windowManager.updateViewLayout(floatingView, params);
                    return true;
            }
            return false;
        }
    }

    @SuppressLint("WrongConstant")
    private void initialize() {

        //getting the widget layout from xml using layout inflater
        floatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);

        //setting the layout parameters
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);


        //getting window services and adding the floating view to it
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(floatingView, params);

        //getting the collapsed and expanded view from the floating view
        expandedView = floatingView.findViewById(R.id.layoutExpanded);

        //adding click listener to close button and expanded view
        floatingView.findViewById(R.id.btn_start).setOnClickListener(this);
        floatingView.findViewById(R.id.btn_stop).setOnClickListener(this);
        floatingView.findViewById(R.id.btn_close).setOnClickListener(this);

        //adding a touch listener to make drag movement of the floating widget
        floatingView.findViewById(R.id.ic_main).setOnTouchListener(new mainOnTouchListener(params));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        System.out.println("initialized");

        Toast.show("Floating dialog initialized.");
        MLog.info("Floating dialog", "Floating dialog initialized.");
    }
}