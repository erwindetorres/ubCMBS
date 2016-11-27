package project.weabank.com.ubcmbs.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import project.weabank.com.ubcmbs.AppController;
import project.weabank.com.ubcmbs.MainActivity;
import project.weabank.com.ubcmbs.R;
import project.weabank.com.ubcmbs.helpers.ConfigHelper;
import project.weabank.com.ubcmbs.helpers.PreferenceHelper;
import project.weabank.com.ubcmbs.model.TransferModel;

/**
 * Created by Kira on 11/27/2016.
 */

public class SendMoneyFragment extends Fragment {

    public static String TAG = ConfigHelper.PACKAGE_NAME + ".SendMoneyFragment";
    ProgressDialog progressDialog;
    Button btnSendPayment;


    public SendMoneyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sendmoney, container, false);

         btnSendPayment = (Button) view.findViewById(R.id.btnSendPayment);

         final TextView destinationAccount = (TextView) view.findViewById(R.id.sendDestinationAccount);
         final TextView amnt = (TextView) view.findViewById(R.id.sendAmount);
         final TextView pin = (TextView) view.findViewById(R.id.sendMobilePin);
         final TextView confirmPin = (TextView) view.findViewById(R.id.sendConfirmPin);



         btnSendPayment.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 final String targetAccount = String.valueOf(destinationAccount.getText().toString());
                 final Double targetAmount = Double.parseDouble(String.valueOf(amnt.getText().toString()));
                 final String sourceAccount = PreferenceHelper.getTempLoginID();




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

                             //RequestBody body = RequestBody.create(mediaType, "{\"channel_id\":\""+ ConfigHelper.URL_CHANNEL_ID+"\",\"transaction_id\":\""+transID+"\",\"source_account\":\"000000014909\",\"source_currency\":\"php\",\"biller_id\":\""+targetAccount+"\",\"reference1\":\""+targetReference+"\",\"reference2\":\"000000000B\",\"reference3\":\"000000000C\",\"amount\":"+targetAmount+"}");
                             RequestBody body = RequestBody.create(mediaType,"{\"channel_id\":\""+ ConfigHelper.URL_CHANNEL_ID+"\",\"transaction_id\":\""+transID+"\",\"source_account\":\""+ sourceAccount +"\",\"source_currency\":\"php\",\"target_account\":\""+targetAccount+"\",\"target_currency\":\"php\",\"amount\":"+targetAmount+"}");
                             Request request = new Request.Builder()
                                     .url(ConfigHelper.URL_TRANSFER)
                                     .post(body)
                                     .addHeader(ConfigHelper.BORED_ID, ConfigHelper.VOLDEMORT)
                                     .addHeader(ConfigHelper.BORED_CLIENT, ConfigHelper.BORED_CODER)
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
                         progressDialog.setMessage("Processing Transfer...Please wait...");
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

                                 String transactionID = "";
                                 String confirmation = "";
                                 String status = "";
                                 String channelID = "";
                                 String errorMessage = "";

                                 JSONObject jsonObject = new JSONObject(responseData);
                                 channelID = jsonObject.getString("channel_id");
                                 transactionID = jsonObject.getString("transaction_id");
                                 confirmation = jsonObject.getString("confirmation_no");
                                 status = jsonObject.getString("status");
                                 errorMessage = jsonObject.getString("error_message");

                                 String message ="";
                                 if(status.equals("S")){
                                     message ="TransID: "+transactionID+" You have successfully transferred "+ targetAmount+" amount to "+targetAccount+". \nConfirmation No:" + confirmation;
                                 }else{
                                     message ="TransID: "+transactionID+" Failed to process transfer. Please try again later.";
                                 }
                                 TransferModel transferModel = new TransferModel(channelID, transactionID, status, confirmation, errorMessage,message);
                                 transferModel.save();

                                 final String finalMessage = message;
                                 new Handler().post(new Runnable() {
                                     @Override
                                     public void run() {
                                         AlertDialog.Builder builder =
                                                 new AlertDialog.Builder(MainActivity.getInstance(), R.style.AppCompatAlertDialogStyle);
                                         builder.setTitle("Fund Transfer");
                                         builder.setMessage(finalMessage);
                                         builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                             @Override
                                             public void onClick(DialogInterface dialog, int which) {
                                                 destinationAccount.setText("");
                                                 amnt.setText("");
                                                 pin.setText("");
                                                 confirmPin.setText("");
                                                 dialog.dismiss();
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
         });


        return view;

    }

}
