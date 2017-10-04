package com.daka.servicedesk.modules.services;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.daka.sdk.models.Building;
import com.daka.sdk.models.Elevator;
import com.daka.sdk.services.Daka;
import com.daka.servicedesk.R;
import com.daka.servicedesk.base.activities.BaseActivity;
import com.daka.servicedesk.utils.NetworkUtil;
import com.daka.servicedesk.utils.SnackBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Dana on 22-Sep-17.
 */

public class ServicesActivity extends BaseActivity {

    @BindView(R.id.input_city)
    AutoCompleteTextView inputCity;
    @BindView(R.id.table)
    TableLayout table;
    @BindView(R.id.progress)
    ProgressBar progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_services);
        ButterKnife.bind(this);

        table.setVisibility(View.GONE);
        getElevators();
    }

    private void getElevators() {
        if(!NetworkUtil.hasNetwork()) {
            SnackBarUtil.showError(this.findViewById(android.R.id.content), getString(R.string.error_may_no_work_properly));
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getElevatorsApiCall()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe(() -> {
                                progress.setVisibility(View.VISIBLE);
                            })
                            .doOnTerminate(() -> {
                                progress.setVisibility(View.GONE);
                            })
                            .subscribe(response -> {
                                showElevators(response);
                            }, throwable -> {
                                Timber.d("Get all elevators", throwable);
                            });
                }
            });
        }
    }

    private void showElevators(List<Elevator> elevators) {
        if(elevators != null && elevators.size() > 0) {
            table.setVisibility(View.VISIBLE);
            for(int i=0 ; i < elevators.size() ; i++) {
                final TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.table_row, null);
                TextView tv;
                Context context = ServicesActivity.this;

                tv = (TextView) tableRow.findViewById(R.id.tableCell1);
                tv.setText("Skopje");

                tv = (TextView) tableRow.findViewById(R.id.tableCell2);
                tv.setText(elevators.get(i).getBuildingName() != null ? elevators.get(i).getBuildingName() : "N/A");
                tv.setGravity(Gravity.CENTER);

                tv = (TextView) tableRow.findViewById(R.id.tableCell3);
                tv.setText(elevators.get(i).getCode() != 0 ? String.valueOf(elevators.get(i).getCode()) : "0");
                tv.setGravity(Gravity.CENTER);

                tv = (TextView) tableRow.findViewById(R.id.tableCell4);
                tv.setText(elevators.get(i).getMachine() != null ? elevators.get(i).getMachine() : "N/A");
                tv.setGravity(Gravity.CENTER);

                tableRow.setBackgroundColor((i+1)%2 == 0 ? ContextCompat.getColor(context, R.color.tableDarkRow) : ContextCompat.getColor(context, R.color.tableLightRow));
                table.addView(tableRow);
            }
        } else {
            table.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Observable<List<Elevator>> getElevatorsApiCall() {
        return Daka.getApiService().getElevators(1);
    }
}
