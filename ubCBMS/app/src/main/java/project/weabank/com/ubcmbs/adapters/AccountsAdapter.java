package project.weabank.com.ubcmbs.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import project.weabank.com.ubcmbs.R;
import project.weabank.com.ubcmbs.model.AccountModel;

/**
 * Created by Kira on 11/27/2016.
 */

public class AccountsAdapter extends RecyclerView
        .Adapter<AccountsAdapter
        .DataObjectHolder> {

    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<AccountModel> mDataset;
    private List<AccountModel> accountModelList;

    private static AccountsAdapter.MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;
        TextView dateTime;

        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.textView);
            dateTime = (TextView) itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(AccountsAdapter.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    /*public AccountsAdapter(ArrayList<HistoryModel> myDataset) {
        mDataset = myDataset;
    }*/

    public AccountsAdapter(List<AccountModel> accountItem) {
        this.accountModelList = accountItem;
    }

    @Override
    public AccountsAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_account_view, parent, false);

        AccountsAdapter.DataObjectHolder dataObjectHolder = new AccountsAdapter.DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(AccountsAdapter.DataObjectHolder holder, int position) {
        holder.label.setText(accountModelList.get(position).getAccountNo());

        double amount = Double.parseDouble(accountModelList.get(position).getAvailableBalance());
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        holder.dateTime.setText(String.valueOf(formatter.format(amount)));
    }

    public void addItem(AccountModel dataObj, int index) {
        //mDataset.add(index, dataObj);
        accountModelList.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        //mDataset.remove(index);
        accountModelList.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        //return mDataset.size();
        return  accountModelList.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
