package com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pagers_tabs;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tranetech.gandhinagar.plshah.R;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.GSTMainActivity;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.utilis.DatePickerDialogTheme;
import com.tranetech.gandhinagar.plshah.utilis.SharedPreferenceManager;


public class TabOneInvoiceInfo extends Fragment {
    private Button bt_next, bt_date;
    private View v;
    private EditText et_i_serial_no, et_i_date;
    /* private EditText et_i_gs_no;*/
    //invoice data
    private String i_gstno, RadioBTStatus, RadioBillStutus, i_serial, i_date;
    private RadioGroup rb_group, rb_group_invoice;
    private TextView txt_i_payable;
    private RadioButton rb_yes, rb_no, rb_txt_invoice, rb_bill_supply;

    private SharedPreferenceManager sharedPreferenceManager;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab_one_invoice_info, container, false);
        sharedPreferenceManager = new SharedPreferenceManager();

        // Intialization of data
        loaduielements();
        // Loding saved data..
        //   LoadPriviousData();

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_i_serial_no.getText().toString().isEmpty() || et_i_serial_no.getText().toString().length() == 0) {
                    //  et_i_serial_no.setError("Please enter invoice serial number");
                    i_serial = "-";
                }

                if (rb_group_invoice.getCheckedRadioButtonId() == -1) {
                    //   txt_i_payable.setError("Please select Tax Invoice or Bill of supply");
                    Toast.makeText(getActivity(), "Please select Tax Invoice or Bill of supply", Toast.LENGTH_SHORT).show();
                } else if (rb_group.getCheckedRadioButtonId() == -1) {
                    //txt_i_payable.setError("Please select Tax of Receiver charge");
                    Toast.makeText(getActivity(), "Tax is Payable On Receive Charge Yes/No ?", Toast.LENGTH_SHORT).show();
                } else if (et_i_date.getText().toString().isEmpty() || et_i_date.getText().length() == 0) {
                    Toast.makeText(getActivity(), "Please Select Invoice date", Toast.LENGTH_SHORT).show();
                } else {
                    SetTextData();
                }
            }
        });


        try {
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            et_i_date.setText(formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }


        bt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DatePickerDialogTheme(et_i_date);
                dialogFragment.show(getFragmentManager(), "Theme 5");
            }
        });


        return v;
    }

 /*   @RequiresApi(api = Build.VERSION_CODES.N)
    private void LoadPriviousData() {

      *//*  et_i_gs_no.setText(sharedPreferenceManager.getDefaults("i_gstno", getActivity().getApplicationContext()));*//*
        et_i_serial_no.setText(sharedPreferenceManager.getDefaults("i_serial", getActivity().getApplicationContext()));
        //et_i_date.setText(sharedPreferenceManager.getDefaults("i_date", getActivity().getApplicationContext()));

    }*/


    private void SetTextData() {

        i_serial = et_i_serial_no.getText().toString();
        i_date = et_i_date.getText().toString();


        if (et_i_serial_no.getText().toString().isEmpty()) {
            i_serial = "-";
        }

        if (rb_yes.isChecked()) {
            RadioBTStatus = "Yes";
        } else {
            RadioBTStatus = "No";
        }

        if (rb_txt_invoice.isChecked()) {
            RadioBillStutus = "Tax Invoice";
        } else {
            RadioBillStutus = "Bill of supply";
        }

        Log.e("RadioBTStatus: ", RadioBTStatus);
        i_gstno = "-";

        sharedPreferenceManager.setDefaults("s_type", RadioBillStutus, getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("i_gstno", i_gstno, getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("i_payble", RadioBTStatus, getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("i_serial", i_serial, getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("i_date", i_date, getActivity().getApplicationContext());

        GSTMainActivity.setfrgmnt(1);

    }

    private void loaduielements() {
        bt_next = (Button) v.findViewById(R.id.bt_next);
        bt_date = (Button) v.findViewById(R.id.bt_date);
      /*  et_i_gs_no = (EditText) v.findViewById(et_i_gs_no);*/
       /* et_i_payable = (EditText) v.findViewById(R.id.et_i_payable);*/
        txt_i_payable = (TextView) v.findViewById(R.id.txt_i_payable);
        et_i_serial_no = (EditText) v.findViewById(R.id.et_i_serial_no);
        et_i_date = (EditText) v.findViewById(R.id.et_i_date);

        rb_group = (RadioGroup) v.findViewById(R.id.rb_group);
        rb_no = (RadioButton) v.findViewById(R.id.rb_no);
        rb_yes = (RadioButton) v.findViewById(R.id.rb_yes);

        rb_group_invoice = (RadioGroup) v.findViewById(R.id.rb_group_invoice);
        rb_txt_invoice = (RadioButton) v.findViewById(R.id.rb_txt_invoice);
        rb_bill_supply = (RadioButton) v.findViewById(R.id.rb_bill_supply);

    }


}