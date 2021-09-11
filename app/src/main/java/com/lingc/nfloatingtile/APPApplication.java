package com.lingc.nfloatingtile;

import android.app.Application;

public class APPApplication extends Application {
    private static APPApplication appApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        appApplication = this;
    }

    public static APPApplication getApplication() {
        return appApplication;
    }
}
