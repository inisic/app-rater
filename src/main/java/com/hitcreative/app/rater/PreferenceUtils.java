package com.hitcreative.app.rater;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Date;

/**
 * Created by applabdev2 on 16.12.2015.
 */
public class PreferenceUtils {
    private static final String PREF_LIB_NAME = "AppRater";
    private static final String PREF_INSTALL_DATE = "install_date";
    private static final String PREF_LAUNCH_TIME = "launch_time";
    private static final String PREF_RATER_STATUS = "rater_status";
    private static SharedPreferences.Editor editor;
    private static SharedPreferences sharedPreference;
    private Context context;

    public PreferenceUtils(Context context) {
        sharedPreference = context.getSharedPreferences(PREF_LIB_NAME, Context.MODE_PRIVATE);
        editor = context.getSharedPreferences(PREF_LIB_NAME, Context.MODE_PRIVATE).edit();
    }

    public static void updateState() {
        if (getLaunchTime() == 0) setInstallDate();
        setLaunchTime();
    }

    public static void setInstallDate() {
        editor.putLong(PREF_INSTALL_DATE, new Date().getTime());
        editor.apply();
    }

    public static long getInstallDate() {
        return sharedPreference.getLong(PREF_INSTALL_DATE, 0);
    }

    public static void setLaunchTime() {
        setLaunchTime(getLaunchTime() + 1);
    }

    public static void setLaunchTime(int launchTime) {
        editor.putInt(PREF_LAUNCH_TIME, launchTime);
        editor.apply();
    }

    public static int getLaunchTime() {
        return sharedPreference.getInt(PREF_LAUNCH_TIME, 0);
    }

    public static void setRaterStatus(boolean status) {
        editor.putBoolean(PREF_RATER_STATUS, status);
        editor.apply();
    }

    public static boolean getRaterStatus() {
        return sharedPreference.getBoolean(PREF_RATER_STATUS, true);
    }
}
