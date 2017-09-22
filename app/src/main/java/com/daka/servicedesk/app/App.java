package com.daka.servicedesk.app;

import android.app.Application;
import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;
import com.daka.sdk.Config;
import com.daka.sdk.auth.TokenAuthenticationProvider;
import com.daka.sdk.services.Daka;
import com.daka.servicedesk.BuildConfig;
import com.daka.servicedesk.jobs.DakaJobCreator;
import com.evernote.android.job.JobManager;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.orhanobut.hawk.GsonParser;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;
import com.squareup.picasso.Cache;
import com.squareup.picasso.Picasso;

import net.danlew.android.joda.JodaTimeAndroid;

import io.fabric.sdk.android.Fabric;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import timber.log.Timber;

/**
 * Created by Dana on 22-Sep-17.
 */

public class App extends Application {

    public static final String FOLDER_NAME = "DakaLift";

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        ContextProvider.init(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        JodaTimeAndroid.init(this);

        Picasso picasso = new Picasso.Builder(this)
                .memoryCache(Cache.NONE)
                .downloader(new OkHttp3Downloader(OkHttpFactory.provideOkHttpClient()))
                .build();
        Picasso.setSingletonInstance(picasso);

        // SDK init
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setParser(new GsonParser(Daka.gson()))
                .setStorage(HawkBuilder.newSharedPrefStorage(this))
                .setLogLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE)
                .build();

        Config config = new Config();
        config.setLogEnabled(BuildConfig.DEBUG);
        Daka.init(config);

        TokenAuthenticationProvider.init(this);

        Nammu.init(this);
        EasyImage.configuration(this)
                .setImagesFolderName(FOLDER_NAME)
                .saveInAppExternalFilesDir();

        JobManager.create(this).addJobCreator(new DakaJobCreator());

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }
}
