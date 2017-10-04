package com.daka.servicedesk.modules.tasks;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;
import com.daka.sdk.models.Building;
import com.daka.sdk.models.Elevator;
import com.daka.sdk.models.Task;
import com.daka.sdk.services.Daka;
import com.daka.servicedesk.R;
import com.daka.servicedesk.base.activities.BaseActivity;
import com.daka.servicedesk.modules.tasks.fragments.ArchivedTasksFragment;
import com.daka.servicedesk.modules.tasks.fragments.DelayedTasksFragment;
import com.daka.servicedesk.modules.tasks.fragments.OpenTaskFragment;
import com.daka.servicedesk.utils.NetworkUtil;
import com.daka.servicedesk.utils.SnackBarUtil;
import com.daka.servicedesk.utils.Store;
import com.daka.servicedesk.utils.observables.CacheObservables;

import java.util.ArrayList;
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

public class TasksActivity extends BaseActivity {

    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.btn_open_task)
    Button btnOpenTask;
    @BindView(R.id.btn_delayed_tasks)
    Button btnDelayedTasks;
    @BindView(R.id.btn_archived_tasks)
    Button btnArchivedTasks;

    public enum Tabs {
        OpenTask,
        DelayedTasks,
        ArchivedTasks;
    }
    Tabs currentTab;

    @Override
    protected void onResume() {
        super.onResume();
        resolveTabColors();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_tasks);
        ButterKnife.bind(this);

        CacheObservables.init();

        getTasks();
        openTask();

        btnOpenTask.setOnClickListener(view -> {
            openTask();
        });

        btnDelayedTasks.setOnClickListener(view -> {
            delayedTasks();
        });

        btnArchivedTasks.setOnClickListener(view -> {
            archivedTasks();
        });

    }

    private void openTask() {
        currentTab = Tabs.OpenTask;
        resolveTabColors();

        OpenTaskFragment fragment = new OpenTaskFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.tasks_frame, fragment)
                .commit();
    }

    private void delayedTasks() {
        currentTab = Tabs.DelayedTasks;
        resolveTabColors();

        DelayedTasksFragment fragment = new DelayedTasksFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.tasks_frame, fragment)
                .commit();
    }

    private void archivedTasks() {
        currentTab = Tabs.ArchivedTasks;
        resolveTabColors();

        ArchivedTasksFragment fragment = new ArchivedTasksFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.tasks_frame, fragment)
                .commit();
    }

    private void resolveTabColors() {
        btnOpenTask.setBackgroundResource(currentTab == Tabs.OpenTask ? R.color.primaryDark : R.color.primary);
        btnDelayedTasks.setBackgroundResource(currentTab == Tabs.DelayedTasks ?  R.color.primaryDark : R.color.primary);
        btnArchivedTasks.setBackgroundResource(currentTab == Tabs.ArchivedTasks ? R.color.primaryDark : R.color.primary);
    }

    private void getTasks() {
        if(!NetworkUtil.hasNetwork()) {
            SnackBarUtil.showError(this.findViewById(android.R.id.content), getString(R.string.error_may_no_work_properly));
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getAllTasks()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe(() -> {
                                progress.setVisibility(View.VISIBLE);
                            })
                            .doOnTerminate(() -> {
                                progress.setVisibility(View.GONE);
                            })
                            .subscribe(response -> {
                                sortTasks(response);
                            }, throwable -> {
                                Timber.d("Get all tasks", throwable);
                            });
                }
            });
        }
    }

    private void sortTasks(List<Task> tasks) {
        List<Task> archivedTasks = new ArrayList<>();
        List<Task> delayedTasks = new ArrayList<>();

        if(tasks != null && tasks.size() > 0) {
            for (Task task : tasks) {
                switch (task.getId()) {
                    case 4:
                        Store.openTask(task);
                    case 5:
                        archivedTasks.add(task);
                    case 3:
                        delayedTasks.add(task);
                }
            }
            Store.archivedTasks(archivedTasks);
            Store.delayedTasks(delayedTasks);
        }
    }

    private Observable<List<Task>> getAllTasks() {
        return Daka.getApiService().getAllTasks();
    }
}
