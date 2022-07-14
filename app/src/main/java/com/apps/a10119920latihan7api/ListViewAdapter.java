package com.apps.a10119920latihan7api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<NamaKota> kotaList;
    private ArrayList<NamaKota> arrayList;

    public ListViewAdapter(Context context, List<NamaKota> kotaList) {
        mContext = context;
        this.kotaList = kotaList;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(kotaList);
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

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        //kotaList.clear();
        if (charText.length() == 0) {
            kotaList.addAll(arrayList);
        } else {
            for (NamaKota wp : arrayList) {
                if (wp.getNamaKota().toLowerCase(Locale.getDefault()).contains(charText)) {
                    kotaList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}