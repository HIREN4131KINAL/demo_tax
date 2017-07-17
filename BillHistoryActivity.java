package com.tranetech.gandhinagar.plshah.gst.salesbillplshah;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
import com.tranetech.gandhinagar.plshah.Config;
import com.tranetech.gandhinagar.plshah.R;
import com.tranetech.gandhinagar.plshah.activities.CompanyDetailActivity;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.GstAdapter.AdapterGstBillHistory;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.SetterGetter.BillData;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.utilis.DeleteRefresh;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.utilis.ErrorAlert;
import com.tranetech.gandhinagar.plshah.utilis.SharedPreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class BillHistoryActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, DeleteRefresh, SwipeRefreshLayout.OnRefreshListener {
    String b_billcode, a_id;
    View parentLayout;
    private RecyclerView rv_gstbill;
    private AdapterGstBillHistory adapterGST;
    private DeleteRefresh deleteRefresh;
    private List<BillData> billDatas = new ArrayList<>();
    TextView txtEmpty;
    private SharedPreferenceManager sharedPreferenceManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String PhoneNumUser;
    private boolean company_boolean = false;
    private boolean Admin_boolean = false;
    //  private FloatingActionButton faAddNewBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv_gstbill = (RecyclerView) findViewById(R.id.rv_gstbill);
        parentLayout = findViewById(android.R.id.content);
        deleteRefresh = this;
        txtEmpty = (TextView) findViewById(R.id.txt_billhistory_empty);
        sharedPreferenceManager = new SharedPreferenceManager();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sr_gstbill);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapterGST != null) {
            adapterGST.clear();
        }

        Intent getPhonnum = getIntent();

        PhoneNumUser = getPhonnum.getStringExtra("phone");

        if (PhoneNumUser == null || PhoneNumUser.isEmpty()) {
            PhoneNumUser = sharedPreferenceManager.getDefaults("phone", this);
            Admin_boolean = true;
        }


        getData();
    }


    @Override
    public void onRefresh() {
        if (adapterGST != null) {
            adapterGST.clear();
            getData();
            adapterGST.addALL(billDatas);
            swipeRefreshLayout.setRefreshing(false);

         /*   swipeRefreshLayout.setVisibility(View.VISIBLE);
            txtEmpty.setVisibility(View.GONE);*/
        } else {
            ErrorAlert.error("No data available", BillHistoryActivity.this);
            swipeRefreshLayout.setRefreshing(false);
            /*swipeRefreshLayout.setVisibility(View.GONE);
            txtEmpty.setVisibility(View.VISIBLE);*/
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_in_bill_history, menu);

        final MenuItem searchItem = menu.findItem(R.id.search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if (adapterGST != null) {
                adapterGST.clear();
            }

            //  onRefresh();
            Log.e("onActivityResult: ", "Onrefresh called");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_add_bill:

                if (company_boolean) {
                    Intent intent = new Intent(this, GSTMainActivity.class);
                    intent.putExtra("phone", PhoneNumUser);
                    startActivityForResult(intent, 1);
                } else {
                    CompanyWarning();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText.toLowerCase(Locale.getDefault());
        adapterGST.filter(text);
        return true;
    }

    public void getData() {
        final ProgressDialog loading = ProgressDialog.show(this, "Loading data...", "Please wait...", false, false);

        RequestQueue queue = Volley.newRequestQueue(this);


        String url = Config.BILL_LIST + PhoneNumUser;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Log.e("onResponse: ", response);
                        try {
                            getJson(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                loading.dismiss();
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
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
                ErrorAlert.error(message, BillHistoryActivity.this);
            }
        });
// Add the request to the RequestQueue.

        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }

    private void getJson(String response) throws JSONException, IOException {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String str = jsonObject.getString("Result");
            String Company = jsonObject.getString("Company");


            if (Company.equals("NOTFOUND")) {
                company_boolean = false;

            } else {
                company_boolean = true;
            }

            if (str.equals("SUCCESS")) {
                a_id = jsonObject.getString("a_id");
                JSONArray jsonArray = jsonObject.getJSONArray("Data");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jinner = jsonArray.getJSONObject(i);
                    BillData billData = new BillData();
                    b_billcode = jinner.getString("s_code");

                    Log.e("b_billcode: ", b_billcode);
                    Log.e("a_id: ", a_id);

                    billData.setsBillCode(b_billcode);
                    billData.setsId(a_id);
                    billData.setsInvNum(jinner.getString("s_inv_no"));
                    billData.setsInvDate(jinner.getString("s_inv_date"));
                    billData.setsInvParty(jinner.getString("s_party_name"));
                    billData.setsInvTotal(jinner.getString("s_gross_total"));
                    billDatas.add(billData);

                }
                rv_gstbill.setVisibility(View.VISIBLE);
                txtEmpty.setVisibility(View.GONE);

            } else {
                rv_gstbill.setVisibility(View.GONE);
                txtEmpty.setVisibility(View.VISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        IntialAdapter();
    }


    public void IntialAdapter() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        rv_gstbill.setLayoutManager(layoutManager);
        // allows for optimizations if all item views are of the same size:
        rv_gstbill.setHasFixedSize(false);
        adapterGST = new AdapterGstBillHistory(billDatas, BillHistoryActivity.this, deleteRefresh, Admin_boolean);
        adapterGST.notifyDataSetChanged();
        rv_gstbill.setAdapter(adapterGST);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        billDatas.clear();
    }


    @Override
    public void onDeleteRefresh() {
        if (adapterGST != null) {
            adapterGST.clear();
            getData();
            adapterGST.addALL(billDatas);
        }
    }

    private void CompanyWarning() {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(BillHistoryActivity.this);
        builder.setTitle("Company not found");
        builder.setMessage("Please add company details otherwise you will not able to create GST Bill");
        builder.setIcon(R.drawable.logo);
        builder.setCancelable(true);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent cmpDt = new Intent(BillHistoryActivity.this, CompanyDetailActivity.class);
                        startActivity(cmpDt);

                    }
                });

             /*   // display dialog
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // user doesn't want to logout
                    }
                });*/


        android.support.v7.app.AlertDialog dialog = builder.create();

        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
