package com.daka.servicedesk.modules.tasks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.daka.sdk.models.Task;
import com.daka.servicedesk.R;
import com.daka.servicedesk.base.components.LabelTaskView;

import java.util.List;

/**
 * Created by Dana on 23-Sep-17.
 */

public class TasksAdapter extends BaseAdapter {

    private Context context;
    private List<Task> tasks;

    public TasksAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tasks.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(R.layout.adapter_task_item, parent, false);
            holder = new ViewHolder();
            holder.title = (LabelTaskView) row.findViewById(R.id.task_title);
            holder.location = (LabelTaskView) row.findViewById(R.id.task_location);
            holder.notes = (LabelTaskView) row.findViewById(R.id.task_notes);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

//        Task task = tasks.get(position);
//        holder.title.setText(context.getString(R.string.title), task.getTitle());
//        holder.location.setText(context.getString(R.string.location), task.getBuilding().getLocationName());
//        holder.notes.setText(context.getString(R.string.notes), task.getNotes());
        return row;
    }

    static class ViewHolder {
        LabelTaskView title, location, notes;
    }
}
