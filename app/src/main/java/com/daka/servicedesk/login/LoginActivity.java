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
import com.daka.sdk.models.User;
import com.daka.servicedesk.R;
import com.daka.servicedesk.modules.tasks.TasksActivityIntentBulder;
import com.daka.servicedesk.utils.NetworkUtil;
import com.daka.servicedesk.utils.SnackBarUtil;
import com.daka.servicedesk.utils.Store;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

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
            } else if(!TextUtils.isEmpty(username.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())){
                //change dummy call with API call
                Store.user(dummyUser());

                startTaskActivity();
            } else {
                SnackBarUtil.showError(linearLayout, R.string.error_wrong_userName_password);
            }
        });
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

    private User dummyUser() {
        User user = new User();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        user.setName("Dana Stefanoska");

        return user;
    }

}
