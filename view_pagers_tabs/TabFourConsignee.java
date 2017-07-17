package com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pagers_tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tranetech.gandhinagar.plshah.R;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.GSTMainActivity;
import com.tranetech.gandhinagar.plshah.utilis.SharedPreferenceManager;

public class TabFourConsignee extends Fragment {
    private View v;
    private Button bt_next, bt_previous;
    private EditText et_c_name, et_c_address, et_c_state, et_c_state_code, et_c_gst_in_no;
    private String c_name, c_address, c_state, c_state_code, c_gst_in_no;
    private SharedPreferenceManager sharedPreferenceManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab_four_consignee, container, false);
        sharedPreferenceManager = new SharedPreferenceManager();

        loaduielements();
        // Loding saved data..
        LoadPriviousData();

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetTextData();
            }
        });


        bt_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GSTMainActivity.setfrgmnt(2);
            }
        });

        return v;
    }


    private void loaduielements() {
        bt_next = (Button) v.findViewById(R.id.bt_next);
        bt_previous = (Button) v.findViewById(R.id.bt_previous);

        et_c_name = (EditText) v.findViewById(R.id.et_c_name);
        et_c_address = (EditText) v.findViewById(R.id.et_c_address);
        et_c_state = (EditText) v.findViewById(R.id.et_c_state);
        et_c_state_code = (EditText) v.findViewById(R.id.et_c_state_code);
        et_c_gst_in_no = (EditText) v.findViewById(R.id.et_c_gst_in_no);


    }

    private void LoadPriviousData() {

        et_c_name.setText("");
        et_c_address.setText("");
        et_c_state.setText("Gujarat");
        et_c_state_code.setText("");
        et_c_gst_in_no.setText("");

    }


    private void GetTextData() {

        c_name = et_c_name.getText().toString();
        c_address = et_c_address.getText().toString();
        c_state = et_c_state.getText().toString();
        c_state_code = et_c_state_code.getText().toString();
        c_gst_in_no = et_c_gst_in_no.getText().toString();

        if (et_c_name.getText().toString().isEmpty()) {
            //  et_c_name.setError("Please enter consignee name");
            c_name = "-";
        }
        if (et_c_address.getText().toString().isEmpty()) {
            //  et_c_address.setError("Please enter consignee address");
            c_address = "-";
        }
        if (et_c_state.getText().toString().isEmpty()) {
            //et_c_state.setError("Please enter consignee state");
            c_state = "Gujarat";
        }
        if (et_c_state_code.getText().toString().isEmpty()) {
            // et_c_state_code.setError("Please enter consignee state code");
            c_state_code = "0";
        }
        if (et_c_gst_in_no.getText().toString().isEmpty()) {
            //et_c_gst_in_no.setError("Please enter consignee GSTIN number");
            c_gst_in_no = "-";
        }


        sharedPreferenceManager.setDefaults("c_name", c_name, getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("c_address", c_address, getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("c_state", c_state, getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("c_state_code", c_state_code, getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("c_gst_in_no", c_gst_in_no, getActivity().getApplicationContext());

        GSTMainActivity.setfrgmnt(4);


    }

}
