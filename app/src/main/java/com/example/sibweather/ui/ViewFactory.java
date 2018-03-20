package com.example.sibweather.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sibweather.R;

import org.jetbrains.annotations.NotNull;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * Created by Olga
 * on 30.09.2017.
 */

class ViewFactory {

    static View getProgressView(@NotNull Context context) {
        return LayoutInflater.from(context).inflate(R.layout.progress_view, null, false);
    }

    static View getMessageView(@NotNull Context context, @NotNull String message) {
        final FrameLayout layout = new FrameLayout(context);
        layout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final TextView messageView = new TextView(context);
        messageView.setText(message);
        messageView.setTextSize(COMPLEX_UNIT_SP, context.getResources().getDimension(R.dimen.default_text_size));
        layout.addView(messageView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER));

        return layout;
    }
}
