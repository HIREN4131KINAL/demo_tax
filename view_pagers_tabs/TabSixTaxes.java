package com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pagers_tabs;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tranetech.gandhinagar.plshah.R;
import com.tranetech.gandhinagar.plshah.utilis.SharedPreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pagers_tabs.TabFiveSalesCalc.Amount;
import static com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pagers_tabs.TabFiveSalesCalc.addFinalData;


/**
 * Created by HIREN AMALIYAR on 20-06-2017.
 */


@SuppressLint("ValidFragment")
public class TabSixTaxes extends Fragment {
    public static ArrayList<HashMap<String, String>> adsCharges = new ArrayList<>();
    HashMap<String, String> map;
    private View vsix;
    private Button Submit_All, bt_additional_charges;
    /*  private Button bt_previous;*/
    private EditText et_cg_rate, et_cg_amount, et_sg_rate,
            et_sg_amount, et_ig_rate, et_ig_amount, et_tt_discount,
            et_tt_taxable, et_charges_lbl, et_rateAdditional;

    private String cg_rate, cg_amount, sg_rate, sg_amount, ig_rate, b_billcode, a_id,
            ig_amount, tt_discount_manually, tt_taxable, AdsChargelable, AdsRate, AdsResult;
    private SharedPreferenceManager sharedPreferenceManager;
    double CGAmount = 0.0, SGAmount = 0.0, IGAmount = 0.0, Final_total_With_All_Discount = 0.0, cgRate = 0.0, sgRate = 0.0, igRate = 0.0, adsRatetotal = 0.0;
    public static TextView txt_adi_result, txt_after_discount;
    double Ads_rate = 0.0, FinalAmntWithDiscount = 0.0, getDiscount = 0.0;
    private String PhonNumber;

    @SuppressLint("ValidFragment")
    public TabSixTaxes(String phone_number) {
        PhonNumber = phone_number;
    }

    //empty Constructor
    public TabSixTaxes() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        vsix = inflater.inflate(R.layout.tab_six_taxes, container, false);

        sharedPreferenceManager = new SharedPreferenceManager();

        loaduielements();
        //Loding saved data..
        LoadPriviousData();


        LoadLisners();

        return vsix;
    }

    private void LoadLisners() {


        // adding additional charges
        bt_additional_charges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdditionalCharges();

            }
        });


        // submit all data to server
        Submit_All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetTextData();
            }
        });


      /*  bt_cgst_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cg_rate = et_cg_rate.getText().toString();
                CGAmount = get_cgAmount(cg_rate);

                Log.e("onClick:cgst ", String.valueOf(CGAmount));
                et_cg_amount.setText("" + CGAmount);

            }
        });*/


   /*     bt_sgst_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sg_rate = et_sg_rate.getText().toString();
                SGAmount = get_sgAmount(sg_rate);

                Log.e("onClick:sgst ", String.valueOf(SGAmount));
                et_sg_amount.setText("" + SGAmount);
            }
        });

        bt_igst_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ig_rate = et_ig_rate.getText().toString();
                IGAmount = get_igAmount(ig_rate);

                Log.e("onClick:igst ", String.valueOf(IGAmount));
                et_ig_amount.setText("" + IGAmount);
            }
        });
*/

        et_tt_discount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FianalTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        et_cg_rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cg_rate = et_cg_rate.getText().toString();
                CGAmount = get_cgAmount(cg_rate);

                Log.e("onClick:cgst ", String.valueOf(CGAmount));
                et_cg_amount.setText("" + CGAmount);

                FianalTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        et_sg_rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sg_rate = et_sg_rate.getText().toString();
                SGAmount = get_sgAmount(sg_rate);

                Log.e("onClick:sgst ", String.valueOf(SGAmount));
                et_sg_amount.setText("" + SGAmount);

                FianalTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        et_ig_rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ig_rate = et_ig_rate.getText().toString();
                IGAmount = get_igAmount(ig_rate);

                Log.e("onClick:igst ", String.valueOf(IGAmount));
                et_ig_amount.setText("" + IGAmount);

                FianalTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



/*        bt_total_ok.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                try {

                    if (et_tt_discount.getText().toString().isEmpty()) {
                        tt_discount_manually = "0";
                    } else {
                        tt_discount_manually = et_tt_discount.getText().toString();
                    }

                    FinalAmntWithDiscount = Double.parseDouble(txt_after_discount.getText().toString());


                    double Amount_After_All_Tax = 0, discountManually = 0;


                    double TotalRates = cgRate + sgRate + igRate;
                    discountManually = Double.parseDouble(tt_discount_manually);

                    Amount_After_All_Tax = FinalAmntWithDiscount + ((FinalAmntWithDiscount * TotalRates) / 100);


                    et_tt_taxable.setText("" + (Amount_After_All_Tax - discountManually));


                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });*/

    }

    // amount after Additional charges including either discount or not.
    private void GetCurrentAmount() {

        double ADSTotal = Double.parseDouble(AdsResult);

        if (et_tt_discount.getText().toString().isEmpty()) {
            getDiscount = 0;
        }

        FinalAmntWithDiscount = ((ADSTotal + Amount) - getDiscount);
        txt_after_discount.setText("" + FinalAmntWithDiscount);

    }

    private void AdditionalCharges() {

        try {

            AdsChargelable = et_charges_lbl.getText().toString();
            AdsRate = et_rateAdditional.getText().toString();
            Ads_rate = Double.parseDouble(AdsRate);

            if (et_charges_lbl.getText().toString().isEmpty()) {
                AdsChargelable = "-";
            }

            if (et_rateAdditional.getText().toString().isEmpty()) {
                AdsRate = "0";
            }


            adsRatetotal += Ads_rate;


            if (adsRatetotal > 0) {
                Ads_rate = 0;
                et_charges_lbl.setText("");
                et_rateAdditional.setText("");
                et_charges_lbl.setFocusable(true);

                txt_adi_result.setText("Rs. " + "" + adsRatetotal);

                if (txt_adi_result.getText().toString().isEmpty()) {
                    adsRatetotal = 0;
                }

                AdsResult = String.valueOf(adsRatetotal);


                map = new HashMap<>();
                map.put("AdsChargelable", AdsChargelable);
                map.put("AdsRate", AdsRate);

                adsCharges.add(map);

                // amount after Additional charges including either discount or not.
                GetCurrentAmount();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    private void loaduielements() {
        Submit_All = (Button) vsix.findViewById(R.id.Submit_All);
        bt_additional_charges = (Button) vsix.findViewById(R.id.bt_additional_charges);

        et_cg_rate = (EditText) vsix.findViewById(R.id.et_cg_rate);
        et_cg_amount = (EditText) vsix.findViewById
                (R.id.et_cg_amount);
        et_sg_rate = (EditText) vsix.findViewById(R.id.et_sg_rate);
        et_sg_amount = (EditText) vsix.findViewById
                (R.id.et_sg_amount);
        et_ig_rate = (EditText) vsix.findViewById(R.id.et_ig_rate);
        et_ig_amount = (EditText) vsix.findViewById
                (R.id.et_ig_amount);
        et_tt_discount = (EditText) vsix.findViewById
                (R.id.et_tt_discount);
        et_tt_taxable = (EditText) vsix.findViewById(R.id.et_tt_taxable);
        et_rateAdditional = (EditText) vsix.findViewById(R.id.et_rateAdditional);
        et_charges_lbl = (EditText) vsix.findViewById(R.id.et_charges_lbl);

        txt_adi_result = (TextView) vsix.findViewById(R.id.txt_adi_result);
        txt_after_discount = (TextView) vsix.findViewById(R.id.txt_after_discount);

       /* bt_cgst_ok = (Button) vsix.findViewById(R.id.bt_cgst_ok);
        bt_sgst_ok = (Button) vsix.findViewById(R.id.bt_sgst_ok);
        bt_igst_ok = (Button) vsix.findViewById(R.id.bt_igst_ok);
        bt_total_ok = (Button) vsix.findViewById(R.id.bt_total_ok);*/
    }

    private void LoadPriviousData() {
        Amount = Double.valueOf(0);

        CGAmount = 0;
        SGAmount = 0;
        IGAmount = 0;
        Final_total_With_All_Discount = 0;
        cgRate = 0;
        sgRate = 0;
        igRate = 0;
        adsRatetotal = 0;

        tt_discount_manually = "0";
        et_rateAdditional.setText("0");
        txt_adi_result.setText("0");


    }


    private void SetTextData() {

        if (et_cg_rate.getText().toString().isEmpty()) {
            et_cg_rate.setText("0");
        }
        if (et_cg_amount.getText().toString().isEmpty()) {
            et_cg_amount.setText("0");
        }
        if (et_sg_rate.getText().toString().isEmpty()) {
            et_sg_rate.setText("0");
        }
        if (et_sg_amount.getText().toString().isEmpty()) {
            et_sg_amount.setText("0");
        }
        if (et_ig_rate.getText().toString().isEmpty()) {
            et_ig_rate.setText("0");
        }
        if (et_ig_amount.getText().toString().isEmpty()) {
            et_ig_amount.setText("0");
        }
        if (et_tt_discount.getText().toString().isEmpty()) {
            tt_discount_manually = "0";
            et_tt_discount.setText("0");
        }
        if (et_tt_taxable.getText().toString().isEmpty()) {
            et_tt_taxable.setError("Grand total ? ");
        }


        cg_amount = et_cg_amount.getText().toString();
        sg_amount = et_sg_amount.getText().toString();
        ig_amount = et_ig_amount.getText().toString();
        tt_taxable = et_tt_taxable.getText().toString();


        //  sharedPreferenceManager.setDefaults("AdsResult", AdsResult, getActivity().getApplicationContext());

        sharedPreferenceManager.setDefaults("cg_rate", et_cg_rate.getText().toString(), getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("cg_amount", cg_amount, getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("sg_rate", et_sg_rate.getText().toString(), getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("sg_amount", sg_amount, getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("ig_rate", et_ig_rate.getText().toString(), getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("ig_amount", ig_amount, getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("tt_discount_manually", et_tt_discount.getText().toString(), getActivity().getApplicationContext());
        sharedPreferenceManager.setDefaults("tt_taxable", tt_taxable, getActivity().getApplicationContext());

        GetData();

    }


    public void GetData() {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Loading data...", "Please wait...", false, false);
        final String strUrl = "http://www.plshahco.com/API/save_bill.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, strUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response save_bill : ", response);
                        loading.dismiss();
                        getjson(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        loading.dismiss();
                        String message = null;
                        if (volleyError instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please reset your connection!";
                        } else if (volleyError instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                        } else if (volleyError instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                        } else if (volleyError instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                        }
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {

                JSONArray cartItemsArray = null;
                try {
                    cartItemsArray = new JSONArray();
                    for (int i = 0; i < addFinalData.size(); i++) {
                        JSONObject cartItemsObjedct = new JSONObject();

                        cartItemsObjedct.putOpt("SB1", addFinalData.get(i).get("f_description"));
                        cartItemsObjedct.putOpt("SB2", addFinalData.get(i).get("f_hsn_code"));
                        cartItemsObjedct.putOpt("SB3", addFinalData.get(i).get("f_qty"));
                        cartItemsObjedct.putOpt("SB4", addFinalData.get(i).get("f_uom"));
                        cartItemsObjedct.putOpt("SB5", addFinalData.get(i).get("f_rate"));
                        cartItemsObjedct.putOpt("SB6", addFinalData.get(i).get("f_amount"));
                        cartItemsArray.put(cartItemsObjedct);
                    }
                    String str = String.valueOf(cartItemsArray);
                    Log.e("JSON Array", str);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JSONArray AdsCharges = null;
                try {
                    AdsCharges = new JSONArray();
                    for (int i = 0; i < adsCharges.size(); i++) {
                        JSONObject AdsChargesObjedct = new JSONObject();

                        AdsChargesObjedct.putOpt("SF1", adsCharges.get(i).get("AdsChargelable"));
                        AdsChargesObjedct.putOpt("SF2", adsCharges.get(i).get("AdsRate"));

                        AdsCharges.put(AdsChargesObjedct);
                    }
                    String strSF = String.valueOf(AdsCharges);
                    Log.e("JSON Array SF", strSF);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (PhonNumber.isEmpty() || PhonNumber == null) {
                    PhonNumber = sharedPreferenceManager.getDefaults("phone", getActivity());
                }




                JSONObject cartItemsObjedct1 = new JSONObject();

                try {
                    cartItemsObjedct1.putOpt("A_ID", PhonNumber);
                    cartItemsObjedct1.putOpt("S_TYPE", sharedPreferenceManager.getDefaults("s_type", getActivity()));
                    cartItemsObjedct1.putOpt("S1", sharedPreferenceManager.getDefaults("i_gstno", getActivity()));
                    cartItemsObjedct1.putOpt("S2", sharedPreferenceManager.getDefaults("i_payble", getActivity()));
                    cartItemsObjedct1.putOpt("S3", sharedPreferenceManager.getDefaults("i_serial", getActivity()));
                    cartItemsObjedct1.putOpt("S4", sharedPreferenceManager.getDefaults("i_date", getActivity()));
                    cartItemsObjedct1.putOpt("S5", sharedPreferenceManager.getDefaults("t_mode", getActivity()));
                    cartItemsObjedct1.putOpt("S6", sharedPreferenceManager.getDefaults("t_veh_no", getActivity()));
                    cartItemsObjedct1.putOpt("S7", sharedPreferenceManager.getDefaults("t_date_time", getActivity()));
                    cartItemsObjedct1.putOpt("S8", sharedPreferenceManager.getDefaults("t_place", getActivity()));
                    cartItemsObjedct1.putOpt("S9", sharedPreferenceManager.getDefaults("r_name", getActivity()));
                    cartItemsObjedct1.putOpt("S10", sharedPreferenceManager.getDefaults("r_address", getActivity()));
                    cartItemsObjedct1.putOpt("S11", sharedPreferenceManager.getDefaults("r_state", getActivity()));
                    cartItemsObjedct1.putOpt("S12", sharedPreferenceManager.getDefaults("r_state_code", getActivity()));
                    cartItemsObjedct1.putOpt("S13", sharedPreferenceManager.getDefaults("r_gst_in_no", getActivity()));
                    cartItemsObjedct1.putOpt("S14", sharedPreferenceManager.getDefaults("c_name", getActivity()));
                    cartItemsObjedct1.putOpt("S15", sharedPreferenceManager.getDefaults("c_address", getActivity()));
                    cartItemsObjedct1.putOpt("S16", sharedPreferenceManager.getDefaults("c_state", getActivity()));
                    cartItemsObjedct1.putOpt("S17", sharedPreferenceManager.getDefaults("c_state_code", getActivity()));
                    cartItemsObjedct1.putOpt("S18", sharedPreferenceManager.getDefaults("c_gst_in_no", getActivity()));
                    cartItemsObjedct1.putOpt("S19", sharedPreferenceManager.getDefaults("cg_rate", getActivity()));
                    cartItemsObjedct1.putOpt("S20", sharedPreferenceManager.getDefaults("cg_amount", getActivity()));
                    cartItemsObjedct1.putOpt("S21", sharedPreferenceManager.getDefaults("sg_rate", getActivity()));
                    cartItemsObjedct1.putOpt("S22", sharedPreferenceManager.getDefaults("sg_amount", getActivity()));
                    cartItemsObjedct1.putOpt("S23", sharedPreferenceManager.getDefaults("ig_rate", getActivity()));
                    cartItemsObjedct1.putOpt("S24", sharedPreferenceManager.getDefaults("ig_amount", getActivity()));
                    cartItemsObjedct1.putOpt("S25", sharedPreferenceManager.getDefaults("tt_discount_manually", getActivity()));
                    cartItemsObjedct1.putOpt("S26", sharedPreferenceManager.getDefaults("tt_taxable", getActivity()));
                    cartItemsObjedct1.putOpt("SB", cartItemsArray);
                    cartItemsObjedct1.putOpt("SF", AdsCharges);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Map<String, String> params = new HashMap<String, String>();
                params.put("DATA", String.valueOf(cartItemsObjedct1));
                String str = String.valueOf(params);
                Log.e("Sending data:-", str);
                return params;
            }
        };
        postRequest.setShouldCache(false);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(postRequest);
    }


    public double get_cgAmount(String cg_rate) {
        double total = 0, GrandTotal = 0;
        try {
            cgRate = Double.parseDouble(cg_rate);
            if (cg_rate.isEmpty()) {
                cgRate = 0;
            }
            double s_WithDiscount = Double.parseDouble(txt_after_discount.getText().toString());
            total = s_WithDiscount + ((s_WithDiscount * cgRate) / 100);
            GrandTotal = total - s_WithDiscount;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return GrandTotal;
    }

    private double get_igAmount(String ig_rate) {
        double total = 0, GrandTotal = 0;
        try {
            igRate = Double.parseDouble(ig_rate);
            if (ig_rate.isEmpty()) {
                igRate = 0;
            }
            double s_WithDiscount = Double.parseDouble(txt_after_discount.getText().toString());
            total = s_WithDiscount + ((s_WithDiscount * igRate) / 100);
            GrandTotal = total - s_WithDiscount;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return GrandTotal;
    }

    private double get_sgAmount(String sg_rate) {
        double total = 0, GrandTotal = 0;
        try {
            sgRate = Double.parseDouble(sg_rate);
            if (sg_rate.isEmpty()) {
                sgRate = 0;

            }
            double s_WithDiscount = Double.parseDouble(txt_after_discount.getText().toString());
            total = s_WithDiscount + ((s_WithDiscount * sgRate) / 100);
            GrandTotal = total - s_WithDiscount;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return GrandTotal;
    }


    public void getjson(String response) {
        String res = response;


        try {
            JSONObject jsonObjectMain = new JSONObject(res);

            JSONArray jsonArray = jsonObjectMain.getJSONArray("Data");
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jbj = jsonArray.getJSONObject(i);

                b_billcode = jbj.get("b_billcode").toString();
                a_id = jbj.get("a_id").toString();

                String Success = jbj.getString("result");

                if (Success.equals("SUCCESS")) {
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                }
            }

            Log.e("getjson Response: .", b_billcode);
            Log.e("getjson Response: .", a_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!addFinalData.isEmpty()) {
            addFinalData.clear();
        }
    }

    public void FianalTotal() {

        try {

            if (et_tt_discount.getText().toString().isEmpty()) {
                tt_discount_manually = "0";
            } else {
                tt_discount_manually = et_tt_discount.getText().toString();
            }

            FinalAmntWithDiscount = Double.parseDouble(txt_after_discount.getText().toString());


            double Amount_After_All_Tax = 0, discountManually = 0;


            double TotalRates = cgRate + sgRate + igRate;
            discountManually = Double.parseDouble(tt_discount_manually);

            Amount_After_All_Tax = FinalAmntWithDiscount + ((FinalAmntWithDiscount * TotalRates) / 100);


            et_tt_taxable.setText("" + (Amount_After_All_Tax - discountManually));


        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

}
