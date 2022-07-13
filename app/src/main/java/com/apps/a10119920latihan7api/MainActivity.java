package com.apps.a10119920latihan7api;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.String.format;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView list;
    ListViewAdapter adapter;
    SearchView search;
    String[] kotaList;
    ArrayList<NamaKota> arrayList = new ArrayList<NamaKota>();

    private String namaKota;
    private RequestQueue myQueue3;

    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.ListView);

        // Locate the EditText in listview_main.xml
        search = findViewById(R.id.SearchView);
        search.setOnQueryTextListener(this);

        myQueue3 = Volley.newRequestQueue(this);

        getRequestKota();

        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(this, arrayList);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

    }

    public static String idKota, namaKota;

    private void getRequestKota(){

        String url = "https://jadwal-shalat-api.herokuapp.com/cities";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray array = response.getJSONArray("data");

                            for(int i=0; i<array.length(); i++){
                                JSONObject data = array.getJSONObject(i);
                                String namaKotaJSON = data.getString("cityName");
                                kotaList = new String[]{namaKotaJSON};

                                for (int j = 0; j < kotaList.length; j++) {
                                    NamaKota namaKota = new NamaKota(kotaList[j]);
                                    // Binds all strings into an array
                                    arrayList.add(namaKota);
                                }

                                if(namaKotaJSON.equals(search)){
                                    String idKota = data.getString("cityId");
                                    String namaKota = namaKotaJSON;
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        myQueue3.add(request);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }

    @OnClick(R.id.list)
    void selanjutnya() {
        namaKota = list.getText().toString();

        if (isStringEmpty(nameBiodata)) {
            showWarningMessage();
        } else {
            Intent intent = new Intent(this, DoneActivity.class);
            intent.putExtra(ID_EXTRA_MSG, nameBiodata);
            startActivity(intent);
        }
    }
}