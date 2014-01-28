package com.sample.codeswarm.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.text.format.DateFormat;

import com.sample.codeswarm.DeveloperListActivity;

/**
 * Created by Kedar on 1/27/14.
 */
public class SimpleIntentService extends IntentService {
    public static final String PARAM_IN_MSG = "imsg";
    public static final String PARAM_OUT_MSG = "omsg";
    public SimpleIntentService() {
        super("SimpleIntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        String msg = intent.getStringExtra(PARAM_IN_MSG);
        SystemClock.sleep(3000); // 30 seconds
        String resultTxt = msg + " "
                + DateFormat.format("MM/dd/yy h:mmaa", System.currentTimeMillis());

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(DeveloperListActivity.ResponseReceiver.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(PARAM_OUT_MSG, resultTxt);
        sendBroadcast(broadcastIntent);
    }
}
