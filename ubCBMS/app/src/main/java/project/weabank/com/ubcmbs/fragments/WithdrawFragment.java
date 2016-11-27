package project.weabank.com.ubcmbs.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import project.weabank.com.ubcmbs.MainActivity;
import project.weabank.com.ubcmbs.QRActivity;
import project.weabank.com.ubcmbs.R;
import project.weabank.com.ubcmbs.helpers.ConfigHelper;

/**
 * Created by Kira on 11/27/2016.
 */

public class WithdrawFragment extends Fragment {
    public static String TAG = ConfigHelper.PACKAGE_NAME + ".WithdrawFragment";
    public WithdrawFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_withdraw, container, false);
        final TextView amount = (TextView) view.findViewById(R.id.withdrawAmount);
        TextView mobilePin = (TextView) view.findViewById(R.id.withdrawPin);
        TextView confirmPin = (TextView) view.findViewById(R.id.confirmWithdrawPin);

        Button requestWithdraw = (Button) view.findViewById(R.id.btnRequest);
        requestWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String withdrawAmount = String.valueOf(amount.getText().toString());
                //TODO: ADD MOBILE PIN
                Intent intent = new Intent(MainActivity.getInstance(), QRActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString(QRActivity.DATA_AMOUNT, withdrawAmount);
                intent.putExtras(mBundle);
                startActivity(intent);

            }
        });

        return view;

    }
}
