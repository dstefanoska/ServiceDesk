package com.daka.servicedesk.utils.observables;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Dana on 22-Sep-17.
 */

public class PermissionsObservable implements Observable.OnSubscribe<Void> {


    private final Activity activity;
    private final String[] permissions;

    public PermissionsObservable(Activity activity, String[] permissions) {
        this.activity = activity;
        this.permissions = permissions;
    }

    public static Observable<Void> cameraPermissions(Activity activity) {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        return permissions(activity, permissions);
    }

    public static Observable<Void> storagePermissions(Activity activity) {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        return permissions(activity, permissions);
    }

    public static Observable<Void> wifiPermissions(Activity activity) {
        String[] permissions = {Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE};
        return permissions(activity, permissions);
    }

    public static Observable<Void> permissions(Activity activity, String... permissions) {
        return Observable.create(new PermissionsObservable(activity, permissions));
    }


    @Override
    public void call(Subscriber<? super Void> observer) {
        final ObservableCallback callback = new ObservableCallback(observer);
        List<String> missingPermissions = new ArrayList<>();
        for (String permission : permissions) {
            int permissionCheck = ContextCompat.checkSelfPermission(activity, permission);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (missingPermissions.size() == 0) {
            callback.permissionGranted();
        } else {
            Nammu.askForPermission(activity, missingPermissions.toArray(new String[missingPermissions.size()]), callback);
        }

    }

    private static final class ObservableCallback implements PermissionCallback {
        private final Subscriber<? super Void> observer;

        private ObservableCallback(Subscriber<? super Void> observer) {
            this.observer = observer;
        }

        @Override
        public void permissionGranted() {
            if (!observer.isUnsubscribed()) {
                observer.onNext(null);
                observer.onCompleted();
            }
        }

        @Override
        public void permissionRefused() {
            if (!observer.isUnsubscribed()) {
                observer.onError(new PermissionsException());
            }
        }
    }

    public static class PermissionsException extends Exception {
        public PermissionsException() {
            super("permissions refused");
        }
    }
}
