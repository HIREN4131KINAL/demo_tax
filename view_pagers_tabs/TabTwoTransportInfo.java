package com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pagers_tabs;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tranetech.gandhinagar.plshah.R;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.GSTMainActivity;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.utilis.DatePickerDialogTheme;
import com.tranetech.gandhinagar.plshah.utilis.SharedPreferenceManager;


public class TabTwoTransportInfo extends Fragment {
    private Button bt_next, bt_previous, bt_date_tr;
    private View v;
    private EditText et_t_mode, et_t_veh_no, et_t_date_time, et_t_place;
    private String t_mode, t_veh_no, t_date_time, t_place;
    private SharedPreferenceManager sharedPreferenceManager;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab_two_transport_info, container, false);
        sharedPreferenceManager = new SharedPreferenceManager();

        loaduielements();
        // Loding saved data..
        //  LoadPriviousData();

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetTextData();
            }
        });

        bt_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GSTMainActivity.setfrgmnt(0);
            }
        });


        try {
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            et_t_date_time.setText(formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        bt_date_tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DatePickerDialogTheme(et_t_date_time);
                dialogFragment.show(getFragmentManager(), "Theme 5");
            }
        });

        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
  /*  private void LoadPriviousData() {

        et_t_mode.setText(sharedPreferenceManager.getDefaults("t_mode", getActivity().getApplicationContext()));
        et_t_veh_no.setText(sharedPreferenceManager.getDefaults("t_veh_no", getActivity().getApplicationContext()));
        et_t_place.setText(sharedPreferenceManager.getDefaults("t_place", getActivity().getApplicationContext()));
        //et_t_date_time.setText(sharedPreferenceManager.getDefaults("t_date_time", getActivity().getApplicationContext()));
    }*/

    private void GetTextData() {
        // validation

        t_mode = et_t_mode.getText().toString();
        t_veh_no = et_t_veh_no.getText().toString();
        t_date_time = et_t_date_time.getText().toString();
        t_place = et_t_place.getText().toString();

        if (et_t_mode.getText().toString().isEmpty()) {
            t_mode = "-";
        }
        if (et_t_veh_no.getText().toString().isEmpty()) {
            t_veh_no = "-";
        }
        if (et_t_date_time.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Please select date of supply", Toast.LENGTH_SHORT).show();
        }
        if (et_t_place.getText().toString().isEmpty()) {
            t_place = "-";
        }



        sharedPreferenceManager.setDefaults("t_mode", t_mode, getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("t_veh_no", t_veh_no, getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("t_date_time", t_date_time, getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("t_place", t_place, getActivity().getApplicationContext());

        GSTMainActivity.setfrgmnt(2);


    }


    private void loaduielements() {
        bt_next = (Button) v.findViewById(R.id.bt_next);
        bt_previous = (Button) v.findViewById(R.id.bt_previous);
        et_t_mode = (EditText) v.findViewById(R.id.et_t_mode);
        et_t_veh_no = (EditText) v.findViewById(R.id.et_t_veh_no);
        et_t_date_time = (EditText) v.findViewById(R.id.et_t_date_time);
        et_t_place = (EditText) v.findViewById(R.id.et_t_place);
        bt_date_tr = (Button) v.findViewById(R.id.bt_date_tr);
    }


}
