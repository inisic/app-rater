package com.hitcreative.app.rater;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;

import java.util.Date;

/**
 * Created by applabdev2 on 15.12.2015..
 */
public class AppRater {
    private static final String TAG = AppRater.class.getSimpleName();

    private static AppRater instance;
    private static PreferenceUtils preferenceUtils;
    private static Context context;
    private static AppRaterConfig config;
    private boolean exceededLaunchTimes;
    private static boolean raterActive;

    private AppRater(Context context) {
        this.context = context;
    }

    public static AppRater initialize(Context context, AppRaterConfig raterConfig) {
        if (instance == null) {
            instance = new AppRater(context);
            config = raterConfig == null ? new AppRaterConfig.Builder().build() : raterConfig;
            preferenceUtils = new PreferenceUtils(context);
            preferenceUtils.updateState();
        }
        return instance;
    }

    public static AppRater initialize(Context context) {
        return initialize(context, null);
    }

    public static boolean show(final Context context) {
        boolean dialogVisibilityState = shouldShowDialog();
        if (dialogVisibilityState) {
            showRateDialog(context);
        }
        return dialogVisibilityState;
    }

    public static boolean shouldShowDialog() {
        return isExceededLaunchTimes() && isRaterActive();
    }

    /*
    private static boolean shouldShowRateDialog() {
        if (mOptOut) {
            return false;
        } else {
            if (mLaunchTimes >= sConfig.mCriteriaLaunchTimes) {
                return true;
            }
            long threshold = sConfig.mCriteriaInstallDays * 24 * 60 * 60 * 1000L;    // msec
            if (new Date().getTime() - mInstallDate.getTime() >= threshold) {
                return true;
            }
            return false;
        }
    }
*/

    public static void showRateDialog(final Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(config.getHeaderResourceId()));
        builder.setMessage(context.getResources().getString(config.getMessageResourceId()));

        builder.setPositiveButton(context.getResources().getString(config.getPositiveButtonId()), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                registerListener(dialog,which);
                preferenceUtils.setRaterStatus(false);
                String packageName = context.getPackageName();
                Uri storeUri = Uri.parse(context.getString(R.string.play_store_url) + packageName);
                if (config.getStoreType().equals(StoreType.AMAZON)) {
                    storeUri = Uri.parse(context.getString(R.string.amazon_store_url) + packageName);
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, storeUri);
                context.startActivity(intent);
            }
        });

        builder.setNeutralButton(context.getResources().getString(config.getNeutralButtonId()), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                registerListener(dialog,which);
                preferenceUtils.setLaunchTime(0);
            }
        });

        builder.setNegativeButton(context.getResources().getString(config.getNegativeButtonId()), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                registerListener(dialog,which);
                preferenceUtils.setRaterStatus(false);
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                preferenceUtils.setLaunchTime(0);
            }
        });

        builder.create().show();
    }

    private static void registerListener(DialogInterface dialog, int which) {
        Button button = ((AlertDialog) dialog).getButton(which);
        if (config.getButtonListener() != null) config.getButtonListener().onClick(button);
    }


    public static boolean isExceededLaunchTimes() {
        int launchTimes = config.getLaunchTimes();
        if (config.getLaunchTimes() == R.integer.defaultLaunchTimes) {
            launchTimes = context.getResources().getInteger(R.integer.defaultLaunchTimes);
        }
        return preferenceUtils.getLaunchTime() >= launchTimes;
    }

    public static boolean isRaterActive() {
        return preferenceUtils.getRaterStatus();
    }

    public static AppRaterConfig getConfig() {
        return config;
    }
}
