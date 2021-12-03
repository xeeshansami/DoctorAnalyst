package com.fyp.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

import androidx.annotation.RequiresApi;

public class AppLang {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void AppLang(Context contex, String lng) {
        Resources activityRes = contex.getResources();
        Configuration activityConf = activityRes.getConfiguration();
        Locale newLocale = new Locale(lng);
        activityConf.setLocale(newLocale);

        activityRes.updateConfiguration(activityConf, activityRes.getDisplayMetrics());

        Resources applicationRes = contex.getApplicationContext().getResources();
        Configuration applicationConf = applicationRes.getConfiguration();
        applicationConf.setLocale(newLocale);
        applicationRes.updateConfiguration(applicationConf,
                applicationRes.getDisplayMetrics());
       /* Locale locale = new Locale(lng);
        Configuration config = getBaseContext().getResources().getConfiguration();
        Locale.setDefault(locale);
        config.locale = locale;
        config.setLayoutDirection(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
  */
    }

    public static int countOfNotification = 0;
    public static int countOfMessages = 0;
    public static String currentLocation = "";
    public static int imagesSizeRemaining = 0;
    public static int imagesSizeSelected = 0;
}