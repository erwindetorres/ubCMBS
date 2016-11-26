package project.weabank.com.ubcmbs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import project.weabank.com.ubcmbs.MainActivity;
import project.weabank.com.ubcmbs.R;
import project.weabank.com.ubcmbs.adapters.HistoryAdapter;
import project.weabank.com.ubcmbs.helpers.ConfigHelper;
import project.weabank.com.ubcmbs.model.HistoryModel;

/**
 * Created by Kira on 11/26/2016.
 */

public class HomeFragment extends Fragment {
    public static String TAG = ConfigHelper.PACKAGE_NAME + ".HomeFragment";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(MainActivity.getInstance());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HistoryAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        ((HistoryAdapter) mAdapter).setOnItemClickListener(new HistoryAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
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
}
