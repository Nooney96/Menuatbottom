package rossnoonan.menuatbottom.main;


import android.app.Application;

import com.sendbird.android.SendBird;

import com.sendbird.syncmanager.SendBirdSyncManager;

import rossnoonan.menuatbottom.utils.PreferenceUtils;

public class BaseApplication extends Application {

    private static final String APP_ID = "02D1DFBF-43D2-4417-819C-7AFEB422D772"; // US-1 Demo
    public static final String VERSION = "3.0.40";

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceUtils.init(getApplicationContext());

        SendBird.init(APP_ID, getApplicationContext());
        SendBirdSyncManager.setLoggerLevel(98765);
    }
}
