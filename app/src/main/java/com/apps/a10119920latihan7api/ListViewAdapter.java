package com.apps.a10119920latihan7api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<NamaKota> namaKotaList = null;
    private ArrayList<NamaKota> arraylist;

    public ListViewAdapter(Context context, List<NamaKota> namaKotaList) {
        mContext = context;
        this.namaKotaList = namaKotaList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<NamaKota>();
        this.arraylist.addAll(namaKotaList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return namaKotaList.size();
    }

    @Override
    public NamaKota getItem(int position) {
        return namaKotaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_style, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(namaKotaList.get(position).getNamaKota());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        namaKotaList.clear();
        if (charText.length() == 0) {
            namaKotaList.addAll(arraylist);
        } else {
            for (NamaKota wp : arraylist) {
                if (wp.getNamaKota().toLowerCase(Locale.getDefault()).contains(charText)) {
                    namaKotaList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}