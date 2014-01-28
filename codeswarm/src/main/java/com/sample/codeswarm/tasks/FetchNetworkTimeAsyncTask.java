package com.sample.codeswarm.tasks;

import android.os.AsyncTask;
import com.sample.codeswarm.utils.SntpClient;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Kedar on 1/28/14.
 */
public class FetchNetworkTimeAsyncTask extends AsyncTask<Void, Void, String> {

    private static final String NTP_SERVER = "0.pool.ntp.org";
    private FetchNetworkTimeCallable callable = new FetchNetworkTimeCallable(){

        @Override
        public void onSuccess(String time) {
            // dummy impl;
        }
    };

    public interface FetchNetworkTimeCallable{
        void onSuccess(String time);
    }

    public FetchNetworkTimeAsyncTask(FetchNetworkTimeCallable callable){
        this.callable = callable;
    }

    @Override
    protected String doInBackground(Void... params) {
        SntpClient client = new SntpClient();
        client.requestTime(NTP_SERVER, 5000);
        return DateFormat.getInstance().format(new Date(client.getNtpTime()));
    }

    @Override
    protected void onPostExecute(String ntpTime) {
        super.onPostExecute(ntpTime);
        callable.onSuccess(ntpTime);
    }
}
