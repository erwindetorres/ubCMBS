package project.weabank.com.ubcmbs;

import android.app.Application;

import com.orm.SugarContext;

import okhttp3.OkHttpClient;

/**
 * Created by Kira on 11/27/2016.
 */

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private static AppController appControllerInstance;
    private static OkHttpClient myHttpClient;

    public static synchronized AppController getInstance() {
        return appControllerInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appControllerInstance = this;
        myHttpClient = new OkHttpClient();
        SugarContext.init(this);
    }

    public OkHttpClient getMyHttpClient(){
        return  myHttpClient;
    }
}
