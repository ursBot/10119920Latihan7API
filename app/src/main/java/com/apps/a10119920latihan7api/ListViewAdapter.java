package com.apps.a10119920latihan7api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ListViewAdapter extends BaseAdapter implements Filterable {

    LayoutInflater inflater;
    private List<NamaKota> kotaList;
    private final List<NamaKota> kotaList2;
    private ArrayList<NamaKota> arrayList;

    public ListViewAdapter(Context context, List<NamaKota> kotaList) {
        kotaList2 = kotaList;
        this.kotaList = kotaList;
        inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(kotaList);
    }

    public void RefreshList() {
        kotaList = kotaList2;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults result = new FilterResults();
                result.values = kotaList.stream().filter(it -> it.getNamaKota().contains(charSequence)).collect(Collectors.toList());
                return result;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults.values != null)
                    kotaList = (List<NamaKota>) filterResults.values;
            }
        };
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return kotaList.size();
    }

    @Override
    public NamaKota getItem(int position) {
        return kotaList.get(position);
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
        // Set the results into TextViews
        holder.name.setText(kotaList.get(position).getNamaKota());
        return view;
    }

}