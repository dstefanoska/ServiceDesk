package com.daka.sdk.core;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dana on 22-Sep-17.
 */

public abstract class ApiCallback<T> implements Callback<T> {

    public abstract void onSuccess(T response);
    public abstract void onError();

    public void onComplete() {
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()) {
            onSuccess(response.body());
            onComplete();
            return;
        }

        onError();
        onComplete();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onError();
        onComplete();
    }
}