package com.daka.servicedesk.base.intents;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Dana on 22-Sep-17.
 */

public abstract class IntentExtras {

    private final Bundle extras;

    public IntentExtras(Intent intent) {
        this(intent.getExtras());
    }

    public IntentExtras(Bundle bundle) {
        extras = bundle;
        check();
        if (extras != null) {
            read(extras);
        }
    }

    public void require(String key) {
        if (extras == null || !extras.containsKey(key)) {
            throw new IllegalArgumentException("Extras needs to contain " + key);
        }
    }

    protected void check() {
    }

    protected abstract void read(Bundle extras);
}

