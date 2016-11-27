package project.weabank.com.ubcmbs;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import project.weabank.com.ubcmbs.helpers.ConfigHelper;
import project.weabank.com.ubcmbs.helpers.PreferenceHelper;
import project.weabank.com.ubcmbs.helpers.SoundManager;

/**
 * Created by Kira on 11/27/2016.
 */

public class QRActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {

    private static final String TAG = ConfigHelper.PACKAGE_NAME + ".QRScan";
    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private static final String SELECTED_FORMATS = "SELECTED_FORMATS";
    private static final String CAMERA_ID = "CAMERA_ID";
    private ZBarScannerView scannerView;
    private boolean mFlash;
    private boolean mAutoFocus;
    private ArrayList<Integer> mSelectedIndices;
    private int mCameraId = -1;

    private LinearLayout linearLayoutScanner;

    ProgressDialog progressDialog;

    public static String DATA_AMOUNT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_layout);
        Bundle b = getIntent().getExtras();
        String requestAmount = "";
        if(b!=null){
            requestAmount = b.getString(DATA_AMOUNT);
        }

        Toast.makeText(MainActivity.getInstance(), "Bundle: " + requestAmount, Toast.LENGTH_SHORT).show();

        linearLayoutScanner = (LinearLayout) findViewById(R.id.llScanner);
        scannerView = new ZBarScannerView(this);
        linearLayoutScanner.addView(scannerView);

    }

    @Override
    public void handleResult(Result rawResult) {

        if(rawResult!=null){

            if(rawResult.getContents()!=null && rawResult.getBarcodeFormat().getName()!=null){
                System.out.println("QRSCANNED: " + rawResult.getContents() + " " + rawResult.getBarcodeFormat());
                Toast.makeText(this, "RECORDED: [" + rawResult.getContents() + "]", Toast.LENGTH_SHORT).show();

                try{
                    //SOUND
                    SoundManager.playSound(QRActivity.this, SoundManager.COUNTER_BEEP);
                    //SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss MMM dd, yyyy");

                    StringTokenizer tokens = new StringTokenizer(rawResult.getContents().toString(), ":");
                    final String atmTerminalId = tokens.nextToken();
                    final String atmAmount = "";
                    final String accountNumber = PreferenceHelper.getTempLoginID();
                    final String mobileNumber = PreferenceHelper.getCurrentNo();

                    Toast.makeText(MainActivity.getInstance(), "" + atmTerminalId, Toast.LENGTH_SHORT).show();
                    //terminal_id
                    //mobile_number
                    //account_number
                    //amount

                    //initWithdraw(atmTerminalId, mobileNumber, accountNumber, atmAmount);
                    //https://cws.mybluemix.net/api/f/remit

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder(QRActivity.this, R.style.AppCompatAlertDialogStyle);
                            builder.setTitle("Withdraw Money");
                            builder.setMessage("Please wait. Transaction is being processed.");
                            builder.setCancelable(false);
                            builder.show();
                        }
                    },4000);



                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder(QRActivity.this, R.style.AppCompatAlertDialogStyle);
                            builder.setTitle("Withdraw Money");
                            builder.setMessage("Your OTP password: 893190");
                            builder.setCancelable(true);
                            builder.show();
                        }
                    },4000);

                    /*builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //scannerView.startCamera();

                            initPayment(packageAmount, packageReference, "BILL_4");
                        }
                    });*/
                    //builder.setPositiveButton(android.R.string.ok, null);



                }catch (Exception e){
                    SoundManager.playSound(QRActivity.this, SoundManager.COUNTER_ERROR);
                    finish();
                }

            }else{
                SoundManager.playSound(QRActivity.this, SoundManager.COUNTER_ERROR);
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(QRActivity.this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("uCredit Scanner");
                builder.setMessage("Unable to scan item. Please try again.");
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scannerView.startCamera();
                    }
                });
                //builder.setPositiveButton(android.R.string.ok, null);
                builder.show();
            }

        }

    }



    private void initWithdraw(final String terminalId, final String mobileNumber, final String accountNumber, final String atmAmount){
        new AsyncTask<Void, Void, Response>(){

            @Override
            protected Response doInBackground(Void... voids) {
                try {
                    OkHttpClient okHttpClient = ((AppController) MainActivity.getInstance().getApplication()).getMyHttpClient();

                    //TODO: CHANGE PARAM
                    //TODO: IF UNBANKED, MOBILE NUMBER ELSE BANK ACCOUNT
                    MediaType mediaType = MediaType.parse("application/json");

                    int min = 11111111;
                    int max = 888888888;

                    Random r = new Random();
                    int transID = r.nextInt(max - min + 1) + min;

                    RequestBody body = RequestBody.create(mediaType, "{\"terminal_id\":\""+terminalId+"\",\"mobile_number\":\""+mobileNumber+"\",\"account_number\":\""+ConfigHelper.ACCOUNT_KIRA+"\",\"amount\":"+atmAmount+"}");
                    Request request = new Request.Builder()
                            .url(ConfigHelper.URL_PAYMENT)
                            .post(body)
                            .addHeader("content-type", "application/json")
                            .addHeader("accept", "application/json")
                            .build();

                    Response response = okHttpClient.newCall(request).execute();

                    if(response!=null){
                        return  response;
                    }

                    return null;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                progressDialog = new ProgressDialog(MainActivity.getInstance());
                progressDialog.setMessage("Processing Payment...Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Response response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if(response!=null){
                    try {
                        String responseData = response.body().string();
                        //JSONArray jsonArray = new JSONArray(responseData);
                        String channelID = "";
                        String transactionID = "";
                        String confirmation = "";
                        String status = "";
                        String errorMessage = "";
                        /*for(int x=0;x<jsonArray.length();x++){
                            JSONObject jsonObject = jsonArray.getJSONObject(x);
                            transactionID = jsonObject.getString("transaction_id");
                            confirmation = jsonObject.getString("confirmation_no");
                            status = jsonObject.getString("status");
                        }*/
                        JSONObject jsonObject = new JSONObject(responseData);
                        channelID = jsonObject.getString("channel_id");
                        transactionID = jsonObject.getString("transaction_id");
                        confirmation = jsonObject.getString("confirmation_no");
                        status = jsonObject.getString("status");
                        errorMessage = jsonObject.getString("error_message");

                        String message ="";
                        if(status.equals("S")){
                            message ="TransID: "+transactionID+" Payment successfully completed. \nConfirmation No:" + confirmation;
                        }else{
                            message ="TransID: "+transactionID+" Failed to process Payment. Please try again later.";
                        }
                        //PaymentResponseModel prm = new PaymentResponseModel(channelID, transactionID, status, confirmation, errorMessage,message);
                        //prm.save();

                        final String finalMessage = message;
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder =
                                        new AlertDialog.Builder(QRActivity.this, R.style.AppCompatAlertDialogStyle);
                                builder.setTitle("uCredit Scanner");
                                builder.setMessage(finalMessage);
                                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                //builder.setPositiveButton(android.R.string.ok, null);
                                builder.show();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }catch (IOException io){
                        io.printStackTrace();
                    }
                }
                super.onPostExecute(response);
            }
        }.execute();
    }


    @Override
    public void onResume(){
        super.onResume();
        scannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        scannerView.startCamera();          // Start camera on resume
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }
}
