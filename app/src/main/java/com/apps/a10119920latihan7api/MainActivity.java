package com.apps.a10119920latihan7api;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView list;
    ListViewAdapter adapter;
    SearchView search;
    //String[] kotaList;
    ArrayList<String> kotaList = new ArrayList<>();
    ArrayList<NamaKota> arrayList = new ArrayList<>();

    private RequestQueue myQueue3;
    private String getNamaKota, getIdKota;

    public static final String ID_EXTRA_MSG1 = "getKota";
    public static final String ID_EXTRA_MSG2 = "getID";

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

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                NamaKota item = adapter.getItem(i);
                getNamaKota = item.getNamaKota();

                for(int j = 0; j < kotaList.size(); j++){
                    if(kotaList.get(j).equals(getNamaKota)){
                        getIdKota = String.valueOf(j+1);
                    }
                }


                Intent intent = new Intent(MainActivity.this,JadwalActivity.class);
                intent.putExtra(ID_EXTRA_MSG1, getNamaKota);
                intent.putExtra(ID_EXTRA_MSG2, getIdKota);
                //based on item add info to intent
                startActivity(intent);
            }
        });

    }

    //public static String idKota;

    private void getRequestKota(){

        String url = "https://jadwal-shalat-api.herokuapp.com/cities";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String namaKotaJSON = null;
                            JSONArray array = response.getJSONArray("data");

                            for(int i=0; i<array.length(); i++){
                                JSONObject data = array.getJSONObject(i);
                                namaKotaJSON = data.getString("cityName");

                                kotaList.add(namaKotaJSON);

                                Log.d("debugggg kotaJSON", String.valueOf(kotaList));
                                //kotaList[i] = namaKotaJSON;
                                //Log.d("debugggg kotaJSON", String.valueOf(namaKotaJSON));
                                //Log.d("debugggg kotaJSON", String.valueOf(kotaList));

                                for (int j = 0; j < kotaList.size(); j++) {

                                    NamaKota namaKota = new NamaKota(kotaList.get(j));
                                    // Binds all strings into an array
                                    //Log.d("debugggg kotaJSON", namaKota.getNamaKota());

                                    arrayList.add(namaKota);
                                    //Log.d("debugggg kotaJSON", String.valueOf(namaKota));
                                }

                                /**if(namaKotaJSON.equals(search)){
                                    String idKota = data.getString("cityId");
                                    String namaKota = namaKotaJSON;
                                }*/

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
        //String text = newText;
        //adapter.filter(text);
        if (TextUtils.isEmpty(newText)) {
            adapter.filter("");
            list.clearTextFilter();
        } else {
            adapter.filter(newText);
        }
        return true;
    }
}