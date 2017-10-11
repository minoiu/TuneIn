package com.qmul.nminoiu.tunein;

import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationDisplayedResult;
import com.onesignal.OSNotificationReceivedResult;
import com.onesignal.OSNotificationPayload;

import java.math.BigInteger;


/**
 * Created by nicoleta on 11/10/2017.
 */

public class NotificationData extends NotificationExtenderService {
        @Override
        protected boolean onNotificationProcessing(OSNotificationReceivedResult receivedResult) {
            OverrideSettings overrideSettings = new OverrideSettings();
            overrideSettings.extender = new NotificationCompat.Extender() {
                @Override
                public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
                    // Sets the background notification color to Green on Android 5.0+ devices.
                    return builder.setColor(new BigInteger("FF00FF00", 16).intValue());
                }
            };

            OSNotificationDisplayedResult displayedResult = displayNotification(overrideSettings);
            Log.d("OneSignalExample", "Notification displayed with id: " + displayedResult.androidNotificationId);

            return true;
    }
}
