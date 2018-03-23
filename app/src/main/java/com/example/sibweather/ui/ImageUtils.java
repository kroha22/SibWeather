package com.example.sibweather.ui;

import android.content.Context;
import android.widget.ImageView;

import com.example.sibweather.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Olga
 * on 21.03.2018.
 */

class ImageUtils {

    static void loadSmallIconInto(@NotNull ImageView view, @NotNull String iconPath)  {
        loadIconInto(view, iconPath, R.dimen.weather_pic_width, R.dimen.weather_pic_height);
    }

    static void loadBigIconInto(@NotNull ImageView view, @NotNull String iconPath)  {
        loadIconInto(view, iconPath, R.dimen.weather_pic_width_big, R.dimen.weather_pic_height_big);
    }

    private static void loadIconInto(@NotNull ImageView view, @NotNull String iconPath, int widthResourceId, int heightResourceId)  {
        final Context context = view.getContext();
        Picasso.with(context)
                .load("http:" + iconPath)
                .resize(getDimension(context, widthResourceId), getDimension(context, heightResourceId))
                .into(view);
    }

    private static int getDimension(@NotNull Context context, int resourceId)  {
        return (int) context.getResources().getDimension(resourceId);
    }


}
