package com.daka.servicedesk.app;

import android.content.Context;

/**
 * Created by Dana on 22-Sep-17.
 */

public class ContextProvider {


    private static ContextProvider instance;
    private final Context context;

    public ContextProvider(Context applicationContext) {
        context = applicationContext;
    }

    public static void init(Context context) {
        instance = new ContextProvider(context.getApplicationContext());
    }

    public static ContextProvider get() {
        return instance;
    }

    public static Context getContext() {
        return get().context;
    }

}