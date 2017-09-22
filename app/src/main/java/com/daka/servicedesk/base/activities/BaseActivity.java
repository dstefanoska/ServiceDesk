package com.daka.servicedesk.base.activities;

import android.support.v7.app.AppCompatActivity;

import pl.tajchert.nammu.Nammu;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Dana on 22-Sep-17.
 */

public class BaseActivity extends AppCompatActivity {

    private CompositeSubscription subscriptionDestroy;
    private CompositeSubscription subscriptionStop;
    private CompositeSubscription subscriptionPause;

    @Override
    protected void onStop() {
        super.onStop();
        if (subscriptionStop != null) {
            subscriptionStop.unsubscribe();
            subscriptionStop = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (subscriptionPause != null) {
            subscriptionPause.unsubscribe();
            subscriptionPause = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscriptionDestroy != null) {
            subscriptionDestroy.unsubscribe();
            subscriptionDestroy = null;
        }
    }

    public CompositeSubscription subscriptionDestroy() {
        if(subscriptionDestroy == null) {
            subscriptionDestroy = new CompositeSubscription();
        }
        return subscriptionDestroy;
    }

    public CompositeSubscription subscriptionStop() {
        if(subscriptionStop == null) {
            subscriptionStop = new CompositeSubscription();
        }
        return subscriptionStop;
    }

    public CompositeSubscription subscriptionPause() {
        if(subscriptionPause == null) {
            subscriptionPause = new CompositeSubscription();
        }
        return subscriptionPause;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}