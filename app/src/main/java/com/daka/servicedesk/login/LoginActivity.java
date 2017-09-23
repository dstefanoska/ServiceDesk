package com.daka.servicedesk.login;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;
import com.daka.sdk.models.User;
import com.daka.servicedesk.R;
import com.daka.servicedesk.base.activities.BaseActivity;
import com.daka.servicedesk.home.HomeActivityIntentBuilder;
import com.daka.servicedesk.utils.NetworkUtil;
import com.daka.servicedesk.utils.SnackBarUtil;
import com.daka.servicedesk.utils.Store;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.input_user_name)
    TextInputEditText userName;
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

        login.setOnClickListener(view -> {
            if(!NetworkUtil.hasNetwork()) {
                SnackBarUtil.showError(linearLayout, R.string.error_no_network);
            } else if(!TextUtils.isEmpty(userName.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())){
                User user = new User();
                user.setUserName(userName.getText().toString());
                user.setPassword(password.getText().toString());

                Store.user(user);
                startHomeActivity();
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

    private void startHomeActivity() {
        new HomeActivityIntentBuilder(this).start();
        finish();
    }
}
