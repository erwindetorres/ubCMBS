package project.weabank.com.ubcmbs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.weabank.com.ubcmbs.R;
import project.weabank.com.ubcmbs.helpers.ConfigHelper;

/**
 * Created by Kira on 11/27/2016.
 */

public class MyTransactionsFragment extends Fragment {
    public static String TAG = ConfigHelper.PACKAGE_NAME + ".MyTransactionsFragment";
    public MyTransactionsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transactions, container, false);
        return view;

    }
}
