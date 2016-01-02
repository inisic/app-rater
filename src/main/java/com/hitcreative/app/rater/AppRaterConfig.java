package com.hitcreative.app.rater;

import android.content.Context;
import android.util.Log;
import android.view.View;

/**
 * Created by applabdev2 on 16.12.2015.
 */
public class AppRaterConfig {
    private static final String TAG = AppRaterConfig.class.getSimpleName();
    private int launchTimes;
    private int launchDate;
    private int headerResourceId;
    private int messageResourceId;
    private int positiveButtonId;
    private int negativeButtonId;
    private int neutralButtonId;
    private StoreType storeType;
    private View.OnClickListener buttonListener;

    public static class Builder {
        private int launchDate;
        private int launchTimes = 0;
        private int headerResourceId;
        private int messageResourceId;
        private int positiveButtonId = 0;
        private int negativeButtonId = 0;
        private int neutralButtonId = 0;
        private StoreType storeType = StoreType.GOOGLE_PLAY;
        private View.OnClickListener buttonListener;

        public Builder launchTimes(int launchTimes) {
            this.launchTimes = launchTimes;
            return this;
        }

        public Builder launchDate(int launchDate) {
            this.launchDate = launchDate;
            return this;
        }

        public Builder headerResourceId(int headerResourceId) {
            this.headerResourceId = headerResourceId;
            return this;
        }

        public Builder messageId(int messageResourceId) {
            this.messageResourceId = messageResourceId;
            return this;
        }

        public Builder positiveButtonId(int positiveButtonId) {
            this.positiveButtonId = positiveButtonId;
            return this;
        }

        public Builder negativeButtonId(int negativeButtonId) {
            this.negativeButtonId = negativeButtonId;
            return this;
        }

        public Builder neutralButtonId(int neutralButtonId) {
            this.neutralButtonId = neutralButtonId;
            return this;
        }

        public Builder storeType(StoreType storeType) {
            this.storeType = storeType;
            return this;
        }

        public Builder onClickListener(View.OnClickListener listener) {
            this.buttonListener = listener;
            return this;
        }

        public AppRaterConfig build() {
            return new AppRaterConfig(this);
        }

    }

    private AppRaterConfig(Builder builder) {
        this.launchDate = builder.launchDate;
        this.launchTimes = builder.launchTimes != 0 ? builder.launchTimes : R.integer.defaultLaunchTimes;
        this.headerResourceId = builder.headerResourceId != 0 ? builder.headerResourceId : R.string.apprater_dialog_header;
        this.messageResourceId = builder.messageResourceId != 0 ? builder.messageResourceId : R.string.apprater_dialog_message;
        this.positiveButtonId = builder.positiveButtonId!= 0 ? builder.positiveButtonId : R.string.apprater_dialog_ok;
        this.negativeButtonId = builder.negativeButtonId!= 0 ? builder.negativeButtonId : R.string.apprater_dialog_no;
        this.neutralButtonId= builder.neutralButtonId!= 0 ? builder.neutralButtonId : R.string.apprater_dialog_cancel;
        this.storeType = builder.storeType;
        this.buttonListener = builder.buttonListener;
    }

    public int getLaunchTimes() {
        return launchTimes;
    }

    public int getLaunchDate() {
        return launchDate;
    }

    public int getHeaderResourceId() {
        return headerResourceId;
    }

    public int getMessageResourceId() {
        return messageResourceId;
    }

    public int getPositiveButtonId() {
        if (positiveButtonId == 0) positiveButtonId = R.string.apprater_dialog_ok;
        return positiveButtonId;
    }

    public int getNegativeButtonId() {
        if (negativeButtonId == 0) negativeButtonId = R.string.apprater_dialog_cancel;
        return negativeButtonId;
    }

    public int getNeutralButtonId() {
        if (neutralButtonId == 0) neutralButtonId = R.string.apprater_dialog_no;
        return neutralButtonId;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public View.OnClickListener getButtonListener() {
        return buttonListener;
    }
}
