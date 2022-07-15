package com.apps.a10119920latihan7api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ListViewAdapter extends BaseAdapter implements Filterable {

    private List<Kota> mutableKotaList;
    private final List<Kota> staticKotaList;

    private final LayoutInflater inflater;

    public ListViewAdapter(Context context, List<Kota> kotaList) {
        staticKotaList = kotaList;
        this.mutableKotaList = kotaList;
        inflater = LayoutInflater.from(context);
    }

    public void RefreshList() {
        mutableKotaList = staticKotaList;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults result = new FilterResults();
                result.values = mutableKotaList.stream().filter(it -> it.GetNama().toLowerCase().contains(charSequence.toString().toLowerCase())).collect(Collectors.toList());
                return result;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults.values != null)
                    mutableKotaList = (List<Kota>) filterResults.values;
            }
        };
    }

    @Override
    public int getCount() {
        return mutableKotaList.size();
    }

    @Override
    public Kota getItem(int position) {
        return mutableKotaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_style, null);
            holder.name = view.findViewById(R.id.Name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(mutableKotaList.get(position).GetNama());
        return view;
    }

    private static class ViewHolder {
        TextView name;
    }
}