package project.weabank.com.ubcmbs.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import project.weabank.com.ubcmbs.AppController;
import project.weabank.com.ubcmbs.MainActivity;
import project.weabank.com.ubcmbs.R;
import project.weabank.com.ubcmbs.adapters.AccountsAdapter;
import project.weabank.com.ubcmbs.adapters.HistoryAdapter;
import project.weabank.com.ubcmbs.helpers.ConfigHelper;
import project.weabank.com.ubcmbs.helpers.PreferenceHelper;
import project.weabank.com.ubcmbs.model.AccountModel;
import project.weabank.com.ubcmbs.model.HistoryModel;

/**
 * Created by Kira on 11/26/2016.
 */

public class HomeFragment extends Fragment {
    public static String TAG = ConfigHelper.PACKAGE_NAME + ".HomeFragment";
    public static String LOGTAG = "HomeFragment";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    SwipeRefreshLayout swipeRefreshLayout;
    private List<AccountModel> myAccounts;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(MainActivity.getInstance());
        mRecyclerView.setLayoutManager(mLayoutManager);

        myAccounts = new ArrayList<>();
        setList(myAccounts);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initBalance();
            }
        });

        initBalance();

        return view;

    }

    private void setList(List<AccountModel> list) {
        mAdapter = new AccountsAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AccountsAdapter) mAdapter).setOnItemClickListener(new AccountsAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOGTAG, " Clicked on Item " + position);
            }
        });
    }


    private ArrayList<HistoryModel> getDataSet() {
        ArrayList results = new ArrayList<HistoryModel>();
        for (int index = 0; index < 1; index++) {
            HistoryModel obj = new HistoryModel("Current Balance: 10,000.00",
                    "Total available balance: 10,000.00 " + index);
            results.add(index, obj);
        }
        return results;
    }

    public void initBalance(){
        new AsyncTask<Void,Void, List<AccountModel>>(){

            List<AccountModel> accounts;

            @Override
            protected List<AccountModel> doInBackground(Void... voids) {

                if (accounts == null) {
                    accounts = new ArrayList<AccountModel>();
                }

                try {
                    OkHttpClient okHttpClient = ((AppController) MainActivity.getInstance().getApplication()).getMyHttpClient();

                    //TODO: CHANGE PARAM
                    //TODO: IF UNBANKED, MOBILE NUMBER ELSE BANK ACCOUNT
                    String mappedAccount = PreferenceHelper.getTempLoginID();
                    Request request = new Request.Builder()
                            .url(ConfigHelper.URL_ACCOUNT +  mappedAccount)
                            .get()
                            .addHeader(ConfigHelper.BORED_ID, ConfigHelper.VOLDEMORT)
                            .addHeader(ConfigHelper.BORED_CLIENT, ConfigHelper.BORED_CODER)
                            .addHeader("content-type", "application/json")
                            .addHeader("accept", "application/json")
                            .build();

                    Response response = okHttpClient.newCall(request).execute();

                    if(response!=null){
                        try {
                            String responseData = response.body().string();
                            Log.d(LOGTAG, "RESPONSE: " + responseData);

                            JSONArray jsonArray = new JSONArray(responseData);

                            for(int x=0;x<jsonArray.length();x++){


                                JSONObject jsonObject = jsonArray.getJSONObject(x);
                                Log.d(LOGTAG, "ACCOUNT NO:" + jsonObject.get(AccountModel.JSON_ACCOUNT_NO));
                                Log.d(LOGTAG, "ACCOUNT NAME:" + jsonObject.get(AccountModel.JSON_ACCOUNT_NAME));
                                Log.d(LOGTAG, "ACCOUNT BALANCE:" + jsonObject.get(AccountModel.JSON_AVAIL_BALANCE));

                                AccountModel retAccount = new AccountModel();
                                retAccount.setAccountNo(jsonObject.getString(AccountModel.JSON_ACCOUNT_NO));
                                retAccount.setAccountName(jsonObject.getString(AccountModel.JSON_ACCOUNT_NAME));
                                retAccount.setAvailableBalance(jsonObject.getString(AccountModel.JSON_AVAIL_BALANCE));
                                retAccount.setCurrency(jsonObject.getString(AccountModel.JSON_CURRENCY));
                                retAccount.setCurrentBalance(jsonObject.getString(AccountModel.JSON_CURRENT_BALANCE));
                                retAccount.setStatus(jsonObject.getString(AccountModel.JSON_STATUS));

                                accounts.add(retAccount);
                            }

                            return accounts;

                        } catch (JSONException je) {
                            je.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }

                    return null;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(List<AccountModel> response) {
                if (response != null) {
                    if (response.size() > 0) {
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.getInstance()));
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        //mAdapter = new AccountsAdapter(MainActivity.getInstance(), response, HomeFragment.this);
                        mAdapter = new AccountsAdapter(response);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                        Toast.makeText(MainActivity.getInstance(), "Balance refreshed.", Toast.LENGTH_LONG).show();
                    }

                }
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                super.onPostExecute(response);
            }
        }.execute();
    }
}
