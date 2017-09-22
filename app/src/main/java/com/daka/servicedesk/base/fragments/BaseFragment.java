package com.daka.servicedesk.base.fragments;

import android.support.v4.app.Fragment;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Dana on 22-Sep-17.
 */

public class BaseFragment extends Fragment {

    private CompositeSubscription subscriptionDestroy;
    private CompositeSubscription subscriptionStop;
    private CompositeSubscription subscriptionPause;

    @Override
    public void onStop() {
        super.onStop();
        if (subscriptionStop != null) {
            subscriptionStop.unsubscribe();
            subscriptionStop = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (subscriptionPause != null) {
            subscriptionPause.unsubscribe();
            subscriptionPause = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
}
