package com.tranetech.gandhinagar.plshah.gst.salesbillplshah.GstAdapter;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.tranetech.gandhinagar.plshah.Config;
import com.tranetech.gandhinagar.plshah.R;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.SetterGetter.BillData;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.utilis.DeleteRefresh;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.utilis.ErrorAlert;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by HIREN AMALIYAR on 20-06-2017.
 */

public class AdapterGstBillHistory extends RecyclerView.Adapter<AdapterGstBillHistory.GstBillHistory> {
    ArrayList<HashMap<String, String>> getdata;
    private Context applicationContext;
    private List<BillData> billDatas;
    public ArrayList<BillData> mDataset;
    private DeleteRefresh deleteRefresh;
    private ProgressDialog mProgressDialog;
    private boolean admin_boolean;
    //  private DownloadTask downloadTask;

    public AdapterGstBillHistory(List<BillData> billDatas, Context applicationContext, DeleteRefresh deleteRefresh, boolean admin_boolean) {
        this.applicationContext = applicationContext;
        this.billDatas = billDatas;
        this.mDataset = new ArrayList<>();
        this.mDataset.addAll(billDatas);
        this.deleteRefresh = deleteRefresh;
        this.admin_boolean = admin_boolean;
    }

    @Override
    public GstBillHistory onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_gst_history, parent, false);
        //     downloadTask = new DownloadTask(applicationContext);
        return new GstBillHistory(itemview);
    }

    @Override
    public void onBindViewHolder(GstBillHistory holder, final int position) {
        mProgressDialog = new ProgressDialog(applicationContext);
        mProgressDialog.setMessage("A message");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

      /*  mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true);
            }
        });*/

        final String a_id = billDatas.get(position).getsId();
        final String b_billcode = billDatas.get(position).getsBillCode();

        final String pdfFileName = a_id + b_billcode + ".pdf";
        final String wordFileName = a_id + b_billcode + ".doc";
        final String xlsFileName = a_id + b_billcode + ".xls";


        Log.e("onBindViewHolder: ", a_id);
        Log.e("onBindViewHolder2: ", b_billcode);
        final boolean pdfExists = new File("/sdcard/P L Shah/P L Shah File/" + pdfFileName).isFile();
        final boolean wordExists = new File("/sdcard/P L Shah/P L Shah File/" + wordFileName).isFile();
        final boolean xlsExists = new File("/sdcard/P L Shah/P L Shah File/" + xlsFileName).isFile();

        holder.txt_inv_num.setText(billDatas.get(position).getsInvNum());
        holder.txt_inv_date.setText(billDatas.get(position).getsInvDate());
        holder.txt_inv_party_name.setText(billDatas.get(position).getsInvParty());
        holder.txt_inv_total.setText(billDatas.get(position).getsInvTotal());


        if (!admin_boolean) {
            holder.btn_gst_delete.setVisibility(View.GONE);
        }


        holder.btn_gst_pdf_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pdfExists) {
                    Open(pdfFileName, "pdf");
                } else {
                    new DownloadTask(applicationContext).execute("http://plshahco.com/User/Bill_Print_PDF?Code=" + b_billcode + "&Id=" + a_id, pdfFileName);
                }
            }
        });

        holder.btn_gst_word_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wordExists) {
                    Open(wordFileName, "doc");
                } else {
                    new DownloadTask(applicationContext).execute("http://plshahco.com/User/Bill_Print_DOC?Code=" + b_billcode + "&Id=" + a_id, wordFileName);
                }
            }
        });

        holder.btn_gst_xls_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (xlsExists) {
                    Open(xlsFileName, "xls");
                } else {
                    new DownloadTask(applicationContext).execute("http://plshahco.com/User/Bill_Print_XLS?Code=" + b_billcode + "&Id=" + a_id, xlsFileName);
                }

            }
        });

        holder.btn_gst_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete(b_billcode, pdfFileName, wordFileName, xlsFileName, pdfExists, wordExists, xlsExists, position);


            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return billDatas.size();
    }

    public void Open(String filename, String type) {
        String typ = type;
        if (typ.equals("pdf")) {
            typ = "pdf";
        } else if (typ.equals("doc")) {
            typ = "msword";
        } else {
            typ = "vnd.ms-excel";
        }

        File file = new File("/sdcard/P L Shah/P L Shah File/" + filename);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT > M) {
            Uri uri = FileProvider.getUriForFile(applicationContext, applicationContext.getApplicationContext().getPackageName() + ".provider", file);
            intent.setDataAndType(uri, "application/" + typ);
            List<ResolveInfo> resInfoList = applicationContext.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                applicationContext.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/" + typ);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        try {
            applicationContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(applicationContext, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
            NoApplicationAvailable();
        }
    }


    private void NoApplicationAvailable() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(applicationContext);
        builder.setTitle("Warning");
        builder.setMessage("No Application available to view this file");
        builder.setIcon(R.drawable.error);
        builder.setPositiveButton("Download",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String appPackageName = applicationContext.getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            applicationContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=cn.wps.moffice_eng&hl=en" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            applicationContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=cn.wps.moffice_eng&hl=en" + appPackageName)));
                        }
                    }
                });
        builder.setNegativeButton("Not Now",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        android.support.v7.app.AlertDialog dialog = builder.create();
        // display dialog
        try {
            dialog.show();
        } catch (Exception ed) {
            ed.printStackTrace();
        }
    }


    public void Delete(final String code, final String pdffilename, final String wordfilename, final String xlsfilename, final boolean pdfexist, final boolean wordexist, final boolean xlsexist, final int position) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(applicationContext);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure ?");
        builder.setIcon(R.drawable.delete);
        builder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteData(code, pdffilename, wordfilename, xlsfilename, pdfexist, wordexist, xlsexist, position);
                    }
                });

        // display dialog
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // user doesn't want to logout
            }
        });


        android.support.v7.app.AlertDialog dialog = builder.create();

        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DeleteFiles(final String pdffilename, final String wordfilename, final String xlsfilename, boolean pdfexist, boolean wordexist, boolean xlsexist) {
        if (pdfexist) {
            String selectedFilePath = "/sdcard/P L Shah/P L Shah File/" + pdffilename;
            File file = new File(selectedFilePath);
            file.delete();
        } else if (wordexist) {
            String selectedFilePath = "/sdcard/P L Shah/P L Shah File/" + wordfilename;
            File file = new File(selectedFilePath);
            file.delete();
        } else if (xlsexist) {
            String selectedFilePath = "/sdcard/P L Shah/P L Shah File/" + xlsfilename;
            File file = new File(selectedFilePath);
            file.delete();
        } else {
            Toast.makeText(applicationContext, "Successfully Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public void DeleteData(final String S_CODE, final String pdffilename, final String wordfilename, final String xlsfilename, final boolean pdfexist, final boolean wordexist, final boolean xlsexist, final int position) {
        final ProgressDialog loading = ProgressDialog.show(applicationContext, "Loading data...", "Please wait...", false, false);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Config.DELETE_BILL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response Delete : ", response);
                        loading.dismiss();
                        try {
                            getjson(response, pdffilename, wordfilename, xlsfilename, pdfexist, wordexist, xlsexist, position);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                        ErrorAlert.error(message, applicationContext);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("S_CODE", S_CODE);
                return params;
            }
        };
        postRequest.setShouldCache(false);
        RequestQueue queue = Volley.newRequestQueue(applicationContext);
        queue.add(postRequest);
    }

    public void getjson(String response, final String pdffilename, final String wordfilename, final String xlsfilename, boolean pdfexist, boolean wordexist, boolean xlsexist, int position) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject(response);
        String res = jsonObject.getString("result");
        if (res.equals("SUCCESS")) {

            DeleteFiles(pdffilename, wordfilename, xlsfilename, pdfexist, wordexist, xlsexist);
            //     notifyItemChanged(position);
            deleteRefresh.onDeleteRefresh();
            Toast.makeText(applicationContext, "Delete Successfully.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(applicationContext, "Try again after sometime.", Toast.LENGTH_SHORT).show();
        }
    }

    public void clear() {
        billDatas.clear();
        notifyDataSetChanged();
    }

    public void addALL(List<BillData> alHWData) {
        this.billDatas.addAll(billDatas);
        notifyDataSetChanged();
    }

    public class GstBillHistory extends RecyclerView.ViewHolder {
        TextView txt_inv_num, txt_inv_date, txt_inv_party_name, txt_inv_total;
        Button btn_gst_pdf_download, btn_gst_word_download, btn_gst_xls_download, btn_gst_delete;

        public GstBillHistory(View itemView) {
            super(itemView);

            txt_inv_num = (TextView) itemView.findViewById(R.id.txt_inv_num);
            txt_inv_date = (TextView) itemView.findViewById(R.id.txt_inv_date);
            txt_inv_party_name = (TextView) itemView.findViewById(R.id.txt_inv_party_name);
            txt_inv_total = (TextView) itemView.findViewById(R.id.txt_inv_total);
            btn_gst_pdf_download = (Button) itemView.findViewById(R.id.btn_gst_pdf_download);
            btn_gst_word_download = (Button) itemView.findViewById(R.id.btn_gst_word_download);
            btn_gst_xls_download = (Button) itemView.findViewById(R.id.btn_gst_xls_download);
            btn_gst_delete = (Button) itemView.findViewById(R.id.btn_gst_delete);

        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        billDatas.clear();
        Log.e("filter:1 ", billDatas + "");
        if (charText.length() == 0) {
            billDatas.addAll(mDataset);
            Log.e("filter:2 ", billDatas + "");
            Log.e("filter:3 ", mDataset + "");
        } else {
            for (BillData wp : mDataset) {
                if (wp.getsInvNum().toLowerCase(Locale.getDefault()).contains(charText) || wp.getsInvDate().toLowerCase(Locale.getDefault()).contains(charText) || wp.getsInvParty().toLowerCase(Locale.getDefault()).contains(charText)) {
                    Log.e("filter:4 ", wp + "");
                    billDatas.add(wp);
                    Log.e("filter:5 ", billDatas + "");
                }
            }
        }
        notifyDataSetChanged();
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(values[0]);
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                String sdcard_path = Environment.getExternalStorageDirectory().getPath();


                // for samsung device external sd detection hiren
                if (android.os.Build.DEVICE.contains("Samsung") || android.os.Build.MANUFACTURER.contains("Samsung")) {
                    sdcard_path = sdcard_path + "/external_sd/";
                }

                // create a File object for the parent directory
                File file = new File(sdcard_path + "/P L Shah/P L Shah File/");
                // have the object build the directory structure, if needed.
                if (!file.exists()) { // if file folder not exists
                    file.mkdirs();    // create folder directory 29-11-16 hiren
                }

                // file = new File(sdcard_path + "/P L Shah/P L Shah File/" + filename);


                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream("/sdcard/P L Shah/P L Shah File/" + sUrl[1]);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (s != null) {
                Toast.makeText(context, "Download error: " + s, Toast.LENGTH_LONG).show();
                Log.e("Download", s);
            } else {
                Toast.makeText(context, "File downloaded\nClick Again to open.", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        }
    }
}
