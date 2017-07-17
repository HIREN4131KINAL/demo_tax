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


public class TabThreeReceiver extends Fragment {
    private View v;
    private Button bt_next, bt_previous;
    private EditText et_r_name, et_r_address, et_r_state, et_r_state_code, et_r_gst_in_no;
    private String r_name, r_address, r_state, r_state_code, r_gst_in_no;
    private SharedPreferenceManager sharedPreferenceManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab_three_receiver, container, false);
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
                GSTMainActivity.setfrgmnt(1);
            }
        });

        return v;
    }

    private void LoadPriviousData() {
        et_r_name.setText("");
        et_r_address.setText("");
        et_r_state.setText("Gujarat");
        et_r_state_code.setText("");
        et_r_gst_in_no.setText("");
    }

    private void GetTextData() {
        r_name = et_r_name.getText().toString();
        r_address = et_r_address.getText().toString();
        r_state = et_r_state.getText().toString();
        r_state_code = et_r_state_code.getText().toString();
        r_gst_in_no = et_r_gst_in_no.getText().toString();

        if (et_r_name.getText().toString().isEmpty()) {
            r_name = "-";
        }
        if (et_r_address.getText().toString().isEmpty()) {
            r_address = "-";
        }
        if (et_r_state.getText().toString().isEmpty()) {
            r_state = "Gujarat";
        }
        if (et_r_state_code.getText().toString().isEmpty()) {
            r_state_code = "0";
        }
        if (et_r_gst_in_no.getText().toString().isEmpty()) {
            r_gst_in_no = "-";
        }


        sharedPreferenceManager.setDefaults("r_name", r_name, getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("r_address", r_address, getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("r_state", r_state, getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("r_state_code", r_state_code, getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("r_gst_in_no", r_gst_in_no, getActivity().getApplicationContext());

        GSTMainActivity.setfrgmnt(3);


    }


    private void loaduielements() {
        bt_next = (Button) v.findViewById(R.id.bt_next);
        bt_previous = (Button) v.findViewById(R.id.bt_previous);

        et_r_name = (EditText) v.findViewById(R.id.et_r_name);
        et_r_address = (EditText) v.findViewById(R.id.et_r_address);
        et_r_state = (EditText) v.findViewById(R.id.et_r_state);
        et_r_state_code = (EditText) v.findViewById(R.id.et_r_state_code);
        et_r_gst_in_no = (EditText) v.findViewById(R.id.et_r_gst_in_no);


    }

}
