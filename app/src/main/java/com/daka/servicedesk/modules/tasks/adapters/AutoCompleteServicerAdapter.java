package com.daka.servicedesk.modules.tasks.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.daka.sdk.models.Servicer;
import com.daka.sdk.services.Daka;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Dana on 01-Oct-17.
 */

public class AutoCompleteServicerAdapter extends BaseAdapter implements Filterable {
    List<Servicer> data = new ArrayList<>();

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Servicer getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        Servicer item = getItem(position);
        view.setText(item.getName());
        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                try {
                    List<Servicer> first = Daka.getApiService().getServicers(Integer.valueOf(constraint.toString())).toBlocking().firstOrDefault(new ArrayList<>());
                    filterResults.values = first;
                    filterResults.count = first.size();
                } catch (Exception e) {
                    Timber.d(e.getMessage());
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                if (results.values != null) {
                    addAll((List<Servicer>) results.values);
                }
                notifyDataSetChanged();
            }
        };
    }

    public void clear() {
        this.data.clear();
    }

    public void addAll(List<Servicer> servicers) {
        this.data.addAll(servicers);
    }
}
