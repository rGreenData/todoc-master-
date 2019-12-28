package com.cleanup.todoc.Util;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /*
        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder = Stetho.newInitializerBuilder(this);

        // Enable Chrome DevTools initializerBuilder.enableWebKitInspector
        Stetho.defaultInspectorModulesProvider(this) ;

        // Enable command line interface initializerBuilder.enableDumpapp
        Context context = getBaseContext();
        initializerBuilder.enableDumpapp(Stetho.defaultDumperPluginsProvider(context));

        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);

         */
        Stetho.initializeWithDefaults(this);
    }
}
