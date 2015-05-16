package com.fingers.six.elarm.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by marxen68 on 2015/05/15.
 */
public class UserPresentBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent intent) {

        String action = intent.getAction();

        /**
         * Sent when the user is present after
         * device wakes up (e.g when the keyguard is gone)
         * */
        if (action.equals(Intent.ACTION_USER_PRESENT)){
            Log.d("Receiver", "Unlocked");
        }
        /**
         * Device is shutting down. This is broadcast when the device
         * is being shut down (completely turned off, not sleeping)
         * */
        else if (action.equals(Intent.ACTION_SHUTDOWN)) {

        }
    }

}
