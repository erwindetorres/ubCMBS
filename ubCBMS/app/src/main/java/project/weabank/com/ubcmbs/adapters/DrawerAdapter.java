package project.weabank.com.ubcmbs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import project.weabank.com.ubcmbs.R;
import project.weabank.com.ubcmbs.helpers.DummyContent;
import project.weabank.com.ubcmbs.model.NavModel;

/**
 * Created by Kira on 11/26/2016.
 */

public class DrawerAdapter extends BaseAdapter {

    private Context mContext;
    private List<NavModel> mDrawerItems;
    private LayoutInflater mInflater;

    public DrawerAdapter(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDrawerItems = DummyContent.getDrawerListIcons();
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mDrawerItems.get(position);
    }

    public String getTitle(int position){
        return  mDrawerItems.get(position).getText();
    }

    @Override
    public long getItemId(int position) {
        return mDrawerItems.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.nav_row_item, parent,
                    false);
            holder = new ViewHolder();

            holder.icon = (ImageView) convertView
                    .findViewById(R.id.nav_row_item_icon);
            holder.title = (TextView) convertView.findViewById(R.id.nav_row_item_title);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NavModel item = mDrawerItems.get(position);
        holder.icon.setImageDrawable(DummyContent.getNavDrawerDrawable(mContext,position));
        holder.title.setText(item.getText());
        return convertView;
    }

    private static class ViewHolder {
        public ImageView icon;
        public TextView title;
    }
}
