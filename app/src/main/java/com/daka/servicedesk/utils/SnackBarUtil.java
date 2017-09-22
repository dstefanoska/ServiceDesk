package com.daka.servicedesk.utils;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.daka.servicedesk.R;

/**
 * Created by Dana on 22-Sep-17.
 */

public class SnackBarUtil {
    public static void showError(View view, int resId) {
        showError(view, view.getContext().getString(resId));
    }
    public static void showError(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.error));
        snackbar.show();
    }
}
