package com.daka.servicedesk.base.intents;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Dana on 22-Sep-17.
 */

public class BaseIntentBuilder {

    private final Intent intent;
    private Context context;
    private Class cls;

    public BaseIntentBuilder(Context context, Class cls) {
        this.context = context;
        this.cls = cls;
        this.intent = new Intent(context, cls);
    }

    public void start() {
        this.context.startActivity(intent);
    }

    public Intent getIntent() {
        return intent;
    }

    public BaseIntentBuilder addFlagClearTop() {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return this;
    }
}
