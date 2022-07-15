package com.apps.a10119920latihan7api;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.*;

import java.util.*;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ListViewAdapter listViewAdapter;
    private final ArrayList<Kota> kotaList = new ArrayList<>();

    public static final String ID_CITY_KEY = "ID";
    public static final String CITY_NAME_KEY = "KOTA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HideActionBar();
        FetchKota();

        InitListView();
        InitSearchView();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        listViewAdapter.getFilter().filter(s);
        listViewAdapter.RefreshList();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        listViewAdapter.getFilter().filter(s);
        listViewAdapter.RefreshList();
        return false;
    }

    private void HideActionBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }

    private void InitListView() {
        ListView listView = findViewById(R.id.ListView);
        listViewAdapter = new ListViewAdapter(this, kotaList);
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Kota kota = listViewAdapter.getItem(i);
            Intent intent = new Intent(MainActivity.this, JadwalActivity.class);
            intent.putExtra(ID_CITY_KEY, String.valueOf(kota.GetId()));
            intent.putExtra(CITY_NAME_KEY, kota.GetNama());
            startActivity(intent);
        });
    }

    private void InitSearchView() {
        SearchView search = findViewById(R.id.SearchView);
        search.setOnQueryTextListener(this);
    }

    private void FetchKota() {
        RequestQueue myQueue = Volley.newRequestQueue(this);
        String url = "https://jadwal-shalat-api.herokuapp.com/cities";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray array = response.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject objectKota = array.getJSONObject(i);
                            kotaList.add(new Kota(objectKota.getInt("cityId"), objectKota.getString("cityName")));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);
        myQueue.add(request);
    }
}