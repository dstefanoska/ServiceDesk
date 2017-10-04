package com.daka.servicedesk.modules.sos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.daka.servicedesk.R;
import com.daka.servicedesk.base.activities.BaseActivity;
import com.daka.servicedesk.utils.Store;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import xdroid.toaster.Toaster;

/**
 * Created by Dana on 22-Sep-17.
 */

public class SosActivity extends BaseActivity {
    @BindView(R.id.send_sos)
    Button sendSos;
    @BindView(R.id.location)
    TextInputEditText location;
    @BindView(R.id.building)
    TextInputEditText building;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_sos);
        ButterKnife.bind(this);

        sendSos.setOnClickListener( v -> {
            if(isValid(location, building)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SosActivity.this);
                builder.setMessage(getString(R.string.confirmation)).setPositiveButton(R.string.yes, dialogClickListener)
                        .setNegativeButton(R.string.no, dialogClickListener).show();
            }
        });
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    //Send sms to sos
                    Toaster.toast(getString(R.string.sos_sending));
                    dialog.dismiss();
                    location.setText("");
                    building.setText("");
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    dialog.dismiss();
                    break;
            }
        }
    };

    private boolean isValid (TextInputEditText location, TextInputEditText building) {
        if(TextUtils.isEmpty(location.getText().toString())) {
            location.setText(getString(R.string.error_field_empty));
            return false;
        }

        if(TextUtils.isEmpty(building.getText().toString())) {
            building.setText(getString(R.string.error_field_empty));
            return false;
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
