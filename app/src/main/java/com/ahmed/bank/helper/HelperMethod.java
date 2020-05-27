package com.ahmed.bank.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmed.bank.data.model.DateModel;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class HelperMethod {
    private static Snackbar snackbar;
    private static ProgressDialog checkDialog;
    public static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    public static void replac(Fragment fragment, FragmentManager supportFragmentManager, int id, TextView Tool_Bar_Title, String Title) {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        if (Tool_Bar_Title != null) {
            Tool_Bar_Title.setText(Title);
        }
    }

//    علشان تعمل
//    SnackBar
    public static void createSnackBar(View view, String message, Context context, View.OnClickListener action, String Title) {
        snackbar = Snackbar.make(view, message, 50000);
        snackbar.setAction(Title, action).setActionTextColor(context.getResources().getColor(android.R.color.holo_red_light)).show();
    }


//    علشان تعمل
//    ProgressDialog

    public static void showProgressDialog(Activity activity, String title) {
        try {
            checkDialog = new ProgressDialog(activity);
            checkDialog.setMessage(title);
            checkDialog.setIndeterminate(false);
            checkDialog.setCancelable(false);
            checkDialog.show();
        } catch (Exception e) {
        }
    }

    public static void dismissProgressDialog() {
        try {
            if (checkDialog != null && checkDialog.isShowing()) {
                checkDialog.dismiss();
            }
        } catch (Exception e) {
        }
    }

//    علشان لعمل
//    ReplaceFragment

    public static void ReplaceFragment(FragmentManager supportFragmentManager, Fragment fragment, int container_id, TextView toolbarTitle, String title) {
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.commit();
        if (toolbarTitle != null) {
            toolbarTitle.setText(title);
        }
    }

//    علشان اخفي
//    Keypad

    public static void disappearKeypad(Activity activity, View v) {
        try {
            if (v != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (Exception e) {
        }
    }
    public static void showCalender(Context context, String title, final TextView text_view_data, final DateModel data1) {

        DatePickerDialog mDatePicker = new DatePickerDialog(context, AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {

                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
                DecimalFormat mFormat = new DecimalFormat("00", symbols);
                String data = selectedYear + "-" + String.format(new Locale("en"), mFormat.format(Double.valueOf((selectedMonth + 1)))) + "-"
                        + mFormat.format(Double.valueOf(selectedDay));
                data1.setDate_txt(data);
                data1.setDay(mFormat.format(Double.valueOf(selectedDay)));
                data1.setMonth(mFormat.format(Double.valueOf(selectedMonth + 1)));
                data1.setYear(String.valueOf(selectedYear));
                if (text_view_data != null) {
                    text_view_data.setText(data);
                }
            }
        }, Integer.parseInt(data1.getYear()), Integer.parseInt(data1.getMonth()) - 1, Integer.parseInt(data1.getDay()));
        mDatePicker.setTitle(title);
        mDatePicker.show();
    }
    public static void onLoadImageFromUrl(ImageView imageView, String URl, Context context) {
        Glide.with(context)
                .load(URl)
                .into(imageView);
    }


}
