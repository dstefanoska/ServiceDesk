package com.daka.servicedesk.modules.tasks.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.daka.servicedesk.R;
import com.daka.servicedesk.base.fragments.BaseFragment;
import com.daka.servicedesk.modules.tasks.adapters.TasksAdapter;
import com.daka.servicedesk.utils.Store;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Dana on 22-Sep-17.
 */

public class DelayedTasksFragment extends BaseFragment{

    private Unbinder binding;
    @BindView(R.id.list_delayed_tasks)
    ListView listDelayedTasks;
    @BindView(R.id.no_tasks)
    TextView noTasks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delayed_tasks, container, false);
        binding = ButterKnife.bind(this, view);

        if(Store.delayedTasks() == null || Store.delayedTasks().size() == 0) {
            listDelayedTasks.setVisibility(View.GONE);
            noTasks.setVisibility(View.VISIBLE);
        } else {
            noTasks.setVisibility(View.GONE);
            listDelayedTasks.setVisibility(View.VISIBLE);

            TasksAdapter adapter = new TasksAdapter(getContext(), Store.delayedTasks());
            listDelayedTasks.setAdapter(adapter);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.unbind();
    }
}
