package com.daka.sdk.core;

/**
 * Created by Dana on 22-Sep-17.
 */

public class WrappedApiCallback<T> extends ApiCallback<T>{

    private ApiCallback<T> callback;

    public WrappedApiCallback(ApiCallback<T> callback) {
        this.callback = callback;
    }

    @Override
    public void onComplete() {
        super.onComplete();
        callback.onComplete();
    }

    @Override
    public void onSuccess(T response) {
        callback.onSuccess(response);
    }

    @Override
    public void onError() {
        callback.onError();
    }
}