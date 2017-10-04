package com.daka.servicedesk.modules.services;

import android.content.Context;

import com.daka.servicedesk.base.intents.BaseIntentBuilder;
import com.daka.servicedesk.modules.sos.SosActivity;

/**
 * Created by Dana on 22-Sep-17.
 */

public class ServicesActivityIntentBuilder extends BaseIntentBuilder {
    public ServicesActivityIntentBuilder(Context context) {
        super(context, SosActivity.class);
    }
}
