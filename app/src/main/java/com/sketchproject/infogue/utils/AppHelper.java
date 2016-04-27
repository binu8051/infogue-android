package com.sketchproject.infogue.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sketchproject.infogue.R;

import java.io.ByteArrayOutputStream;

/**
 * Sketch Project Studio
 * Created by Angga on 12/04/2016 14.27.
 */
public class AppHelper {

    @SuppressWarnings("deprecation")
    public static AlertDialog dialogButtonTheme(Context context, AlertDialog dialog){
        Button mButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (mButton != null) {
            mButton.setTextColor(context.getResources().getColor(R.color.primary));
        }
        Button mButton2 = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (mButton2 != null) {
            mButton2.setTextColor(context.getResources().getColor(R.color.gray));
        }
        Button mButton3 = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        if (mButton3 != null) {
            mButton3.setTextColor(context.getResources().getColor(R.color.gray));
        }

        return dialog;
    }

    @SuppressWarnings("deprecation")
    public static Toast toastColored(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundColor(Color.parseColor("#dd00bcd4"));
        view.setPadding(20, 20, 20, 20);
        TextView tv = (TextView) view.findViewById(android.R.id.message);
        tv.setShadowLayer(0, 0, 0, context.getResources().getColor(R.color.transparent));
        tv.setGravity(Gravity.CENTER);
        toast.setView(view);
        toast.show();

        return toast;
    }

    @SuppressWarnings("deprecation")
    public static Toast toastColored(Context context, String message, int color) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundColor(color);
        view.setPadding(20, 20, 20, 20);
        TextView tv = (TextView) view.findViewById(android.R.id.message);
        tv.setShadowLayer(0, 0, 0, context.getResources().getColor(R.color.transparent));
        tv.setGravity(Gravity.CENTER);
        toast.setView(view);
        toast.show();

        return toast;
    }

    public static String createSlug(String title) {
        String trimmed = title.trim();
        String slug = trimmed
                .replaceAll("[^a-zA-Z0-9-]", "-")
                .replaceAll("-+", "-")
                .replaceAll("^-|-$", "");
        return slug.toLowerCase();
    }

    public static String wrapHtmlString(String html) {
        return "<html><head><style>img{display: inline; height: auto; max-width: 100%;}</style></head><body>" + html + "</body></html>";
    }

    public static byte[] getFileDataFromDrawable(Context context, int id) {
        Drawable drawable = ContextCompat.getDrawable(context, id);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] getFileDataFromDrawable(Context context, Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
