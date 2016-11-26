package project.weabank.com.ubcmbs.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.weabank.com.ubcmbs.MainActivity;
import project.weabank.com.ubcmbs.R;

/**
 * Created by Kira on 11/26/2016.
 */

public class Commons {

    private static ProgressDialog pDialog;

    private static int mobileLength = 11;

    public static int convertDpToPixel(float dp){
        DisplayMetrics metrics = MainActivity.getInstance().getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi/160f);
        return (int) px;
    }

    public static void  displayAlertDialog(Context context, String dialogTitle, String dialogMessage, boolean isYesOnly){

        AlertDialog.Builder builder =
                new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(dialogTitle);
        builder.setMessage(dialogMessage);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        if(!isYesOnly){
            builder.setNegativeButton(android.R.string.cancel, null);
        }
        builder.show();

    }


    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 4) {
            return true;
        }
        return false;
    }

    public static boolean isValidMobileNumber(String pass) {
        if (pass != null && pass.length() == mobileLength) {
            return isDigitOrLetter(pass);
        }
        return false;
    }

    public static boolean isDigitOrLetter(String mobile){
        boolean digitFound = false;
        boolean letterFound = false;
        for (char ch : mobile.toCharArray()) {
            if (Character.isDigit(ch)) {
                digitFound = true;
            }
            if (Character.isLetter(ch)) {
                letterFound = true;
            }
            if (digitFound && letterFound) {
                return false;
            }
        }
        return true;
    }

    public static void initProgressDialog(Context context, String progressMessage){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(progressMessage);
        pDialog.setCancelable(false);
        showProgressDialog();
    }
    public static void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public static void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
