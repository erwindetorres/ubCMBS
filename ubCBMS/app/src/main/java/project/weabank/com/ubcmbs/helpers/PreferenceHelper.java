package project.weabank.com.ubcmbs.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import project.weabank.com.ubcmbs.SplashActivity;

/**
 * Created by Kira on 11/27/2016.
 */

public class PreferenceHelper {

    private static final String PACKAGE_NAME = "project.weabank.com.ubcmbs";
    private static SharedPreferences sharedPrefs = SplashActivity.SplashContext().getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);

    private static final String tmpLoginID = PACKAGE_NAME + ".LoginIDTemp";
    private static final String tmpSessionID = PACKAGE_NAME + ".SessionIDTemp";
    private static final String tmpCurrentNo = PACKAGE_NAME + ".CurrentNo";


    public static void setTempLoginID(String logID){
        sharedPrefs.edit().putString(tmpLoginID, logID).commit();
    }
    public static String getTempLoginID(){
        return sharedPrefs.getString(tmpLoginID, "");
    }

    public static void setTempSessionID(String sessID){
        sharedPrefs.edit().putString(tmpSessionID, sessID).commit();
    }
    public static String getTempSessionID(){
        return sharedPrefs.getString(tmpSessionID, "");
    }

    public static void setCurrentNo(String currentNo){
        sharedPrefs.edit().putString(tmpCurrentNo, currentNo).commit();
    }
    public static String getCurrentNo(){
        return sharedPrefs.getString(tmpCurrentNo, "0");
    }
}
