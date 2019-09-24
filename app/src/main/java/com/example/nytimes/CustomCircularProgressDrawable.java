package com.example.nytimes;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

public class CustomCircularProgressDrawable extends CircularProgressDrawable {
    public CustomCircularProgressDrawable(@NonNull Context context, int strokeWidth, int radius) {
        super(context);
        super.setStrokeWidth(strokeWidth);
        super.setCenterRadius(radius);
        super.setColorFilter(new PorterDuffColorFilter(context.getResources().getColor(R.color.icons), PorterDuff.Mode.ADD));
        super.start();
    }
}
