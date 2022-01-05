package com.fyp.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.R;
import com.google.android.material.snackbar.Snackbar;

public class ToastUtils {
    public static Boolean IS_ENABLED = true;

    @SuppressLint("WrongConstant")
    public static void showToastWith(Context context, String message) {
//        if (IS_ENABLED)
//        Toast.makeText(context,message,1000).show();
        showToast(context, message, 1500);
    }
    @SuppressLint("WrongConstant")
    public static void showToastWithDelay(Context context, String message) {
//        if (IS_ENABLED)
//        Toast.makeText(context,message,1000).show();
        showToast(context, message, 60000);
    }
    @SuppressLint("WrongConstant")
    public static void showToastWith(Context context, String message, String color) {
//        if (IS_ENABLED)
//        Toast.makeText(context,message,1000).show();
        showToast(1, context, message);
    }
    @SuppressLint("WrongConstant")
    public static void showToastWith(Context context, String message, String color,int time) {
//        if (IS_ENABLED)
//        Toast.makeText(context,message,1000).show();
        showToast(1, context, message,time);
    }

    public static void showToastWith(Context context, String message, int timeDuration) {
//        if (IS_ENABLED)
//        Toast.makeText(context,message,1000).show();
        showToast(context, message, timeDuration);
    }

    @SuppressLint("WrongConstant")
    public static void normalShowToast(Context context, String message) {
        try {
            Toast.makeText(context, message, 2500).show();
        } catch (Exception e) {
        }
    }

    @SuppressLint("WrongConstant")
    public static void normalShowToast(Context context, String message, int timeDuration) {
        Toast.makeText(context, message, timeDuration).show();
    }

    @SuppressLint("WrongConstant")
    public static void showToast(Context context, String message, int timeDuration) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) ((Activity) context).findViewById(R.id.custom_toast_layout));
        /*TextView tv = (TextView) layout.findViewById(R.id.txtvw);
        tv.setText(message);
        Toast toast = new Toast(((Activity) context).getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 400);
        toast.setDuration(2000);
        toast.setView(layout);
        toast.show();*/


        View parentLayout = ((Activity) context).findViewById(android.R.id.content);        // Create the Snackbar
        Snackbar snackbar = Snackbar.make(parentLayout, "",timeDuration);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout sk = (Snackbar.SnackbarLayout) snackbar.getView();
        // Inflate our custom view
        View snackView = inflater.inflate(R.layout.custom_toast, null);
        // Configure the view
        TextView txtvw = snackView.findViewById(R.id.txtvw);
        txtvw.setText(message);
        //If the view is not covering the whole snackbar layout, add this line
        layout.setPadding(5, 5, 5, 5);
        // Add the view to the Snackbar's layout
        sk.addView(snackView, 0);
        View view = snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP | Gravity.CENTER;
        params.topMargin = 50;
        view.setLayoutParams(params);
//        playSound(context, 1);
        // Show the Snackbar
        snackbar.show();

    }

    @SuppressLint("WrongConstant")
    public static void showToast(int color, Context context, String message) {
        try {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast_error, (ViewGroup) ((Activity) context).findViewById(R.id.custom_toast_layout));
     /*   TextView tv = (TextView) layout.findViewById(R.id.txtvw);
        tv.setText(message);
        Toast toast = new Toast(((Activity) context).getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 400);
        toast.setDuration(1500);
        toast.setView(layout);
        toast.show();
*/
            View parentLayout = ((Activity) context).findViewById(android.R.id.content);        // Create the Snackbar
            Snackbar snackbar = Snackbar.make(parentLayout, "", Snackbar.LENGTH_LONG);
            // Get the Snackbar's layout view
            Snackbar.SnackbarLayout sk = (Snackbar.SnackbarLayout) snackbar.getView();
            // Inflate our custom view
            View snackView = inflater.inflate(R.layout.custom_toast_error, null);
            // Configure the view
            //If the view is not covering the whole snackbar layout, add this line
            TextView txtvw = snackView.findViewById(R.id.txtvw);
            txtvw.setText(message);
            layout.setPadding(5, 5, 5, 5);
            // Add the view to the Snackbar's layout
            sk.addView(snackView, 0);
            View view = snackbar.getView();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.TOP | Gravity.CENTER;
            params.topMargin = 50;
            view.setLayoutParams(params);
//            playSound(context, 0);
            // Show the Snackbar
            snackbar.show();
        } catch (Exception e) {
            ToastUtils.normalShowToast(context, e.getMessage());
        }
    }
    @SuppressLint("WrongConstant")
    public static void showToast(int color, Context context, String message,int time) {
        try {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast_error, (ViewGroup) ((Activity) context).findViewById(R.id.custom_toast_layout));
     /*   TextView tv = (TextView) layout.findViewById(R.id.txtvw);
        tv.setText(message);
        Toast toast = new Toast(((Activity) context).getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 400);
        toast.setDuration(1500);
        toast.setView(layout);
        toast.show();
*/
            View parentLayout = ((Activity) context).findViewById(android.R.id.content);        // Create the Snackbar
            Snackbar snackbar = Snackbar.make(parentLayout, "", Snackbar.LENGTH_LONG);
            // Get the Snackbar's layout view
            Snackbar.SnackbarLayout sk = (Snackbar.SnackbarLayout) snackbar.getView();
            // Inflate our custom view
            View snackView = inflater.inflate(R.layout.custom_toast_error, null);
            // Configure the view
            //If the view is not covering the whole snackbar layout, add this line
            TextView txtvw = snackView.findViewById(R.id.txtvw);
            txtvw.setText(message);
            layout.setPadding(5, 5, 5, 5);
            // Add the view to the Snackbar's layout
            sk.addView(snackView, 0);
            View view = snackbar.getView();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.TOP | Gravity.CENTER;
            params.topMargin = 50;
            view.setLayoutParams(params);
//            playSound(context, 0);
            // Show the Snackbar
            snackbar.setDuration(time);
            snackbar.show();
        } catch (Exception e) {
            ToastUtils.normalShowToast(context, e.getMessage());
        }
    }

    /*public static void playSound(Context context, int type) {
        if (type == 0) {
            MediaPlayer mediaplayer = MediaPlayer.create(context, R.raw.alert);
            //You Can Put Your File Name Instead Of abc
            mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.reset();
                    mediaPlayer.release();
                }
            });
            mediaplayer.start();
        } else {
            MediaPlayer mediaplayer = MediaPlayer.create(context, R.raw.done);
            //You Can Put Your File Name Instead Of abc
            mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.reset();
                    mediaPlayer.release();
                }
            });
            mediaplayer.start();
        }

    }*/
}
