package com.daka.servicedesk.login;

import android.app.Activity;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;
import com.daka.sdk.auth.TokenAuthenticationProvider;
import com.daka.sdk.models.User;
import com.daka.sdk.models.wrappers.UserModel;
import com.daka.sdk.services.Daka;
import com.daka.servicedesk.R;
import com.daka.servicedesk.modules.tasks.TasksActivityIntentBulder;
import com.daka.servicedesk.utils.NetworkUtil;
import com.daka.servicedesk.utils.SnackBarUtil;
import com.daka.servicedesk.utils.Store;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class LoginActivity extends Activity {

    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.input_username)
    TextInputEditText username;
    @BindView(R.id.input_password)
    TextInputEditText password;
    @BindView(R.id.button_login)
    Button login;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if(Store.user() != null) {
            startTaskActivity();
            return;
        }

        login.setOnClickListener(view -> {
            if(!NetworkUtil.hasNetwork()) {
                SnackBarUtil.showError(linearLayout, R.string.error_no_network);
            } else if(isValid(username, password)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UserModel model = new UserModel();
                        model.setUsername(username.getText().toString());
                        model.setPassword(password.getText().toString());

                        loginUser(model)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnSubscribe(() -> {
                                    progress.setVisibility(View.VISIBLE);
                                })
                                .doOnTerminate(() -> {
                                    progress.setVisibility(View.GONE);
                                })
                                .subscribe(response -> {
                                    Store.user(response);
                                    TokenAuthenticationProvider.getInstance().setToken(response.getToken());
                                    startTaskActivity();
                                }, throwable -> {
                                    Timber.d("Login user throwable", throwable);
                                    SnackBarUtil.showError(linearLayout, R.string.error_wrong_userName_password);
                                });
                    }
                });
            }
        });
    }

    private boolean isValid (TextInputEditText username, TextInputEditText password) {
        boolean valid = true;

        if(TextUtils.isEmpty(username.getText().toString())){
            username.setError(getString(R.string.error_field_empty));
            valid = false;
        }

        if(TextUtils.isEmpty(password.getText().toString())){
            password.setError(getString(R.string.error_field_empty));
            valid = false;
        }

        return valid;
    }

    private void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    private void startTaskActivity() {
        new TasksActivityIntentBulder(this).start();
        finish();
    }

    private Observable<User> loginUser (UserModel model) {
        return Daka.getApiService().login(model);
    }
}
