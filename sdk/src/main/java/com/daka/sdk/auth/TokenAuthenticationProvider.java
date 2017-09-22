package com.daka.sdk.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Dana on 22-Sep-17.
 */


public class TokenAuthenticationProvider implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (TokenAuthenticationProvider.getInstance().isTokenValid()) {
            Request request = chain.request();
            request = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + TokenAuthenticationProvider.getInstance().getToken())
                    .build();
            return chain.proceed(request);
        }
        return chain.proceed(chain.request());
    }

    private static final String TOKEN_KEY = "api_key";
    private static final String TAG = TokenAuthenticationProvider.class.getSimpleName();

    private static TokenAuthenticationProvider account;
    private final Context context;
    private String token;

    public static synchronized TokenAuthenticationProvider getInstance() {
        if (account == null) {
            throw new RuntimeException("Initialize the Provider first");
        }
        return account;
    }

    public static synchronized void init(Context context) {
        if (account == null) {
            account = new TokenAuthenticationProvider(context);
        }
    }

    private TokenAuthenticationProvider(final Context context) {
        this.context = context.getApplicationContext();
        initializeToken();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        saveApiKey(token);
    }

    public boolean isTokenValid() {
        return !TextUtils.isEmpty(token);
    }

    public boolean clearAuth() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.remove(TOKEN_KEY);
        boolean commit = editor.commit();
        token = null;
        return commit;
    }

    private boolean saveApiKey(final String apiKey) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(TOKEN_KEY, apiKey);
        return editor.commit();
    }

    private String restoreApiKey() {
        SharedPreferences savedSession = PreferenceManager
                .getDefaultSharedPreferences(context);
        return savedSession.getString(TOKEN_KEY, "");
    }

    private void initializeToken() {
        String apiKey = restoreApiKey();
        if (!TextUtils.isEmpty(apiKey)) {
            this.setToken(apiKey);
        }
    }

}
