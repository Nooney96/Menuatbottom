package rossnoonan.menuatbottom.utils;

import android.content.Context;

import com.google.firebase.iid.FirebaseInstanceId;
import com.sendbird.android.SendBird;

//Taking from https://github.com/smilefam/sendbird-syncmanager-android
//Part of send Bird API

public class PushUtils {

    public static void registerPushTokenForCurrentUser(final Context context, SendBird.RegisterPushTokenWithStatusHandler handler) {
        SendBird.registerPushTokenForCurrentUser(FirebaseInstanceId.getInstance().getToken(), handler);
    }

    public static void unregisterPushTokenForCurrentUser(final Context context, SendBird.UnregisterPushTokenHandler handler) {
        SendBird.unregisterPushTokenForCurrentUser(FirebaseInstanceId.getInstance().getToken(), handler);
    }

}
