package com.sample.codeswarm.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.sample.codeswarm.model.ManagerContent;

import java.util.ArrayList;
import java.util.List;

import static com.sample.codeswarm.model.ManagerContent.DeveloperModel;

public class ManagerService extends Service {

    private List<DeveloperModel> managerModels = new ArrayList<DeveloperModel>();
    private IBinder serviceBinder = new ManagerServiceBinder();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        managerModels.addAll(ManagerContent.ITEMS);

        if ( managerModels.size() > 10 ){
            managerModels.subList(0, 10);
        }
        return Service.START_NOT_STICKY;
    }

    public List<DeveloperModel> getManagerModels() {
        return managerModels;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return serviceBinder;
    }

    public class ManagerServiceBinder extends Binder {
        public ManagerService getService() {
            return ManagerService.this;
        }
    }
}
