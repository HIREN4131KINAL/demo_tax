package com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pagers_tabs;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tranetech.gandhinagar.plshah.R;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.BillHistoryActivity;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.GSTMainActivity;
import com.tranetech.gandhinagar.plshah.utilis.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;

import static com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pagers_tabs.TabSixTaxes.txt_after_discount;

public class TabFiveSalesCalc extends android.support.v4.app.Fragment {
    private View root_v;
    private Button bt_previous, bt_add, bt_next;
    private EditText Et_description, Et_hsn_code, Et_qty, Et_uom, Et_rate;
    private String f_description, f_hsn_code, f_qty, f_uom, f_rate, f_amount;
    private TextView txt_sr_no, Txt_amount;

    public static ArrayList<HashMap<String, String>> addFinalData = new ArrayList<>();
    HashMap<String, String> map;
    BillHistoryActivity billHistoryActivity;
    int i = 1;
    public static Double Amount = Double.valueOf(0);
    private SharedPreferenceManager sharedPreferenceManager;
    // Creating JSON Parser object


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root_v = inflater.inflate(R.layout.tab_five_sales_calc, container, false);

        loaduielements();


        bt_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GSTMainActivity.setfrgmnt(3);
            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetTextData();
            }
        });

        bt_next.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {

                if (addFinalData.size() > 0) {
                    sharedPreferenceManager = new SharedPreferenceManager();
                    i = 1;
                    txt_sr_no.setText(String.valueOf(i));


                    for (int i = 0; i < addFinalData.size(); i++) {

                        double getAMount = Double.parseDouble(addFinalData.get(i).get("f_amount"));
                        Log.e("getAMount:\n ", String.valueOf(getAMount));
                        Amount = Amount + getAMount;
                    }

                    Log.e("Amount:->> ", String.valueOf(Amount));

                    txt_after_discount.setText("" + String.valueOf(Amount));

                    GSTMainActivity.setfrgmnt(5);

                } else {
                    Warning();
                }
            }
        });


        Et_rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {

                    double qty = Double.parseDouble(Et_qty.getText().toString());
                    double rate = Double.parseDouble(Et_rate.getText().toString());
                    if (!Et_qty.getText().toString().isEmpty() || !Et_rate.getText().toString().isEmpty()) {
                        double calc = qty * rate;
                        Txt_amount.setText("" + calc);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Et_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {

                    double qty = Double.parseDouble(Et_qty.getText().toString());
                    double rate = Double.parseDouble(Et_rate.getText().toString());

                    if (!Et_qty.getText().toString().isEmpty() || !Et_rate.getText().toString().isEmpty()) {
                        double calc = qty * rate;
                        Txt_amount.setText("" + calc);
                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return root_v;
    }

    public void Warning() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Warning !");
        builder.setMessage("Please add at least one entry before submit this form.\n Please press + button to add entry");
        builder.setIcon(R.drawable.logo);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        android.support.v7.app.AlertDialog dialog = builder.create();

        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void loaduielements() {
        bt_previous = (Button) root_v.findViewById(R.id.bt_previous);
        bt_add = (Button) root_v.findViewById(R.id.bt_add);
        bt_next = (Button) root_v.findViewById(R.id.bt_next);

        Et_description = (EditText) root_v.findViewById(R.id.Et_description);
        Et_hsn_code = (EditText) root_v.findViewById(R.id.Et_hsn_code);
        Et_qty = (EditText) root_v.findViewById(R.id.Et_qty);
        Et_rate = (EditText) root_v.findViewById(R.id.Et_rate);
        Txt_amount = (TextView) root_v.findViewById(R.id.Txt_amount);
        txt_sr_no = (TextView) root_v.findViewById(R.id.txt_sr_no);
        Et_uom = (EditText) root_v.findViewById(R.id.Et_uom);
    }


    private void GetTextData() {

        f_description = Et_description.getText().toString();
        f_hsn_code = Et_hsn_code.getText().toString();
        f_qty = Et_qty.getText().toString();
        f_uom = Et_uom.getText().toString();
        f_rate = Et_rate.getText().toString();
        f_amount = Txt_amount.getText().toString();

        if (Et_description.getText().toString().isEmpty()) {
            //Et_description.setError("Please enter description");
            f_description = "-";

        }
        if (Et_hsn_code.getText().toString().isEmpty()) {
            //Et_hsn_code.setError("Please enter HSN code");
            f_hsn_code = "-";
        }
        if (Et_uom.getText().toString().isEmpty()) {
            //  Et_uom.setError("Please enter description");
            f_uom = "-";
        }

        if (Et_qty.getText().toString().isEmpty()) {
            Et_qty.setError("Please enter quantity");
        } else if (Et_rate.getText().toString().isEmpty()) {
            Et_rate.setError("Please enter rate(price per unit)");
        } else {
            i++;
            txt_sr_no.setText(String.valueOf(i));
            Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();

            map = new HashMap<>();
            map.put("f_description", f_description);
            map.put("f_hsn_code", f_hsn_code);
            map.put("f_qty", f_qty);
            map.put("f_uom", f_uom);
            map.put("f_rate", f_rate);
            map.put("f_amount", f_amount);
            addFinalData.add(map);

            Et_description.setText("");
            Et_hsn_code.setText("");
            Et_qty.setText("");
            Et_uom.setText("");
            Et_rate.setText("");
            Txt_amount.setText("");
        }
    }

}
