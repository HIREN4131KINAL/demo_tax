package com.tranetech.gandhinagar.plshah.gst.salesbillplshah.utilis;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;


import java.util.Calendar;

/**
 * Created by Markand on 21-Sep-16.
 */
@SuppressLint("ValidFragment")
public class DatePickerDialogTheme extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private EditText editText;
    Context context;

    public DatePickerDialogTheme(EditText editText) {
        this.editText = editText;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
        datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        return datepickerdialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        editText.setText(year + "-" + (month + 1) + "-" + day);

    }
}