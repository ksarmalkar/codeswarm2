package com.sample.codeswarm.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Kedar on 1/27/14.
 */
public class ScreenBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.i("Check", "Screen went OFF");
            Toast.makeText(context, "Code Swarm : Screen OFF", Toast.LENGTH_LONG).show();
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Log.i("Check", "Screen went ON");
            Toast.makeText(context, "Code Swarm : Screen is ON", Toast.LENGTH_LONG).show();
        }
    }
}