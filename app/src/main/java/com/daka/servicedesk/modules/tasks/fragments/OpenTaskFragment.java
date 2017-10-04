package com.daka.servicedesk.modules.tasks.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.daka.sdk.models.Building;
import com.daka.sdk.models.Assignee;
import com.daka.sdk.models.Task;
import com.daka.servicedesk.R;
import com.daka.servicedesk.base.components.LabelTaskView;
import com.daka.servicedesk.base.fragments.BaseFragment;
import com.daka.servicedesk.modules.tasks.MapsActivity;
import com.daka.servicedesk.utils.KeyboardUtil;
import com.daka.servicedesk.utils.Store;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import xdroid.toaster.Toaster;

/**
 * Created by Dana on 22-Sep-17.
 */

public class OpenTaskFragment extends BaseFragment {

    private Unbinder binding;
    @BindView(R.id.no_open_task)
    TextView noOpenTask;

    @BindView(R.id.label_components_layout)
    LinearLayout componentsLayout;
    @BindView(R.id.task_title)
    LabelTaskView title;
    @BindView(R.id.task_location)
    LabelTaskView location;
    @BindView(R.id.task_building)
    LabelTaskView building;
    @BindView(R.id.task_elevator)
    LabelTaskView elevator;
    @BindView(R.id.task_notes)
    LabelTaskView notes;

    @BindView(R.id.map_container)
    RelativeLayout mapContainer;
    @BindView(R.id.btn_directions)
    Button directions;
    @BindView(R.id.map_thumbnail)
    ImageView map;

    @BindView(R.id.container_end_task)
    RelativeLayout containerEndTask;
    @BindView(R.id.end_task)
    FloatingActionButton btnEndTask;
    @BindView(R.id.add_task)
    FloatingActionButton btnNewTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_task, container, false);
        binding = ButterKnife.bind(this, view);

        setupViews();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        map.setOnClickListener( v -> {
            Intent maps = new Intent(getContext(), MapsActivity.class);
            startActivity(maps);
        });

        directions.setOnClickListener( v -> {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("google.navigation:q=Dimitar+Vlahov+32+Ohrid+Macedonia"));
            startActivity(intent);
        });

        btnEndTask.setOnClickListener(v -> {
            //code to send task when it ended
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(getString(R.string.confirmation)).setPositiveButton(R.string.yes, dialogClickListener)
                    .setNegativeButton(R.string.no, dialogClickListener).show();
        });

        btnNewTask.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = getLayoutInflater(savedInstanceState);
            View dialogView = inflater.inflate(R.layout.dialog_new_task, null);
            dialog.setView(dialogView);

            TextInputEditText title = (TextInputEditText) dialogView.findViewById(R.id.input_task_title);
            AutoCompleteTextView city = (AutoCompleteTextView) dialogView.findViewById(R.id.input_task_city);
            AutoCompleteTextView location = (AutoCompleteTextView) dialogView.findViewById(R.id.input_task_location);
            AutoCompleteTextView servicer = (AutoCompleteTextView) dialogView.findViewById(R.id.input_servicer);
            ToggleButton searchToggle = (ToggleButton) dialogView.findViewById(R.id.search_toggle);
            searchToggle.setHint(getString(R.string.building_name));
            searchToggle.setOnClickListener(toggleView -> {
                location.setHint(searchToggle.isChecked() ? getString(R.string.address) : getString(R.string.building_name));
            });


            dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if(isTaskValid(title, city, location, servicer)) {
                        //send data to api method
                    }
                }
            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    KeyboardUtil.closeKeyboard(view);
                }
            });
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        });
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    Toaster.toast(getString(R.string.toast_end_task));
                    Store.openTask(null);
                    setupViews();
                    dialog.dismiss();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    dialog.dismiss();
                    break;
            }
        }
    };

    private void setupViews() {
        if(Store.openTask() == null) {
            noOpenTask.setVisibility(View.VISIBLE);
            containerEndTask.setVisibility(View.GONE);
            componentsLayout.setVisibility(View.GONE);
            mapContainer.setVisibility(View.GONE);
        } else {
            noOpenTask.setVisibility(View.GONE);
            containerEndTask.setVisibility(View.VISIBLE);
            componentsLayout.setVisibility(View.VISIBLE);
            mapContainer.setVisibility(View.VISIBLE);

            String url = "http://maps.google.com/maps/api/staticmap?center=Dimitar+Vlahov+32+Ohrid+Macedonia&zoom=12&markers=size:mid|color:red|Dimitar+Vlahov+32+Ohrid+Macedonia&size=250x188";
            Picasso.with(getContext()).load(url).centerCrop().fit().into(map);
        }
    }

    private boolean isTaskValid(TextInputEditText title, AutoCompleteTextView city, AutoCompleteTextView location, AutoCompleteTextView servicer) {
        boolean valid = true;

        if(TextUtils.isEmpty(title.getText().toString())) {
            title.setText(getString(R.string.error_field_empty));
            valid = false;
        }

        if(TextUtils.isEmpty(city.getText().toString())) {
            city.setText(getString(R.string.error_field_empty));
            valid = false;
        }

        if(TextUtils.isEmpty(location.getText().toString())) {
            location.setText(getString(R.string.error_field_empty));
            valid = false;
        }

        if(TextUtils.isEmpty(servicer.getText().toString())) {
            servicer.setText(getString(R.string.error_field_empty));
            valid = false;
        }

        return valid;
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
