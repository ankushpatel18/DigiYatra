package com.example.digi_yatra_12;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.util.CustomProgressDialog;
import com.example.util.DatabaseManager;
import com.example.util.MyHandler;
import com.example.util.StoreProvider;

import org.hyperledger.aries.api.AriesController;
import org.hyperledger.aries.api.Handler;
import org.hyperledger.aries.api.Provider;
import org.hyperledger.aries.ariesagent.Ariesagent;
import org.hyperledger.aries.config.Options;

public class GlobalApplication extends Application {
    public static AriesController agent;
    public static Handler handler = null;
    public static String boardingPassId = null;
    public static CustomProgressDialog customProgressDialog = null;
    @Override
    public void onCreate() {
        super.onCreate();
        customProgressDialog = new CustomProgressDialog(this);
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.initCouchbaseLite(getApplicationContext());
        Provider store = new StoreProvider(databaseManager,getApplicationContext());
        Options opts = new Options();
        opts.setUseLocalAgent(true);
        //opts.setStorage(store);
        opts.addOutboundTransport("ws");
        opts.setLoadRemoteDocuments(true);
        opts.setLabel("user1");
        opts.setTransportReturnRoute("all");
        opts.addHTTPResolver("sidetree@https://44.234.85.195:32656/sidetree/v1/identifiers");
        try {
            agent = Ariesagent.new_(opts);
            // register handler

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("check my status", e.getMessage());
        }
    }
}
