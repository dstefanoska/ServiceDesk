package com.daka.servicedesk.base.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daka.servicedesk.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dana on 23-Sep-17.
 */

public class LabelTaskView extends RelativeLayout{
    @BindView(R.id.text1)
    TextView textView1;
    @BindView(R.id.text2)
    TextView textView2;

    public LabelTaskView(Context context) {
        super(context);
    }

    public LabelTaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.view_task_label, this, true);
        ButterKnife.bind(this);
    }

    public void setText(String label, String value) {
        textView1.setText(label);
        textView2.setText(value);
    }

    public void setLabel(int resource_label) {
        textView1.setText(getContext().getString(resource_label));
    }

    public void setValue(String value) {
        textView2.setText(value);
    }
}
