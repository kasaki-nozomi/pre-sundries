package com.baidu.ardemo;

import com.baidu.ar.bean.DuMixARConfig;
import com.baidu.ar.util.Res;

import android.app.Application;

public class ARApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Res.addResource(this);
        // 设置App Id
        DuMixARConfig.setAppId("11349491");
        // 设置API Key
        DuMixARConfig.setAPIKey("SzV9HAisGlgmjo2OXStYfu6v");
        // 设置Secret Key
        DuMixARConfig.setSecretKey("lisz6CIuSjrcDKkowit8lTXmbfwaHqh7");
    }
}
