package com.example.myapplication;

import android.app.Application;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Map config = new HashMap();
        config.put("cloud_name", "dsjuckdxu");
        config.put("api_key", "973371356842627");
        config.put("api_secret", "zJ5bMJgfkw3XBdyBocwO8Kgs1us");
        config.put("secure", true);
        MediaManager.init(this, config);
    }
}
