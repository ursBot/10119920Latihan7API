package com.apps.a10119920latihan7api;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
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

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;

import static java.lang.String.format;


public class MainActivity extends AppCompatActivity{


    private TextView jamSubuh, jamDzuhur, jamAshar, jamMaghrib, jamIsya;
    private TextView textSubuh, textDzuhur, textAshar, textMaghrib, textIsya;
    private TextView textSolat, textJam, jamKecil;
    private TextView hariIni, textKota;

    private RequestQueue myQueue, myQueue2, myQueue3;

    private EditText search;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        jamSubuh = findViewById(R.id.JamSubuh);
        jamDzuhur = findViewById(R.id.JamDzuhur);
        jamAshar = findViewById(R.id.JamAshar);
        jamMaghrib = findViewById(R.id.JamMaghrib);
        jamIsya = findViewById(R.id.JamIsya);

        textSubuh = findViewById(R.id.TextSubuh);
        textDzuhur = findViewById(R.id.TextDzuhur);
        textAshar = findViewById(R.id.TextAshar);
        textMaghrib = findViewById(R.id.TextMaghrib);
        textIsya = findViewById(R.id.TextIsya);

        textSolat = findViewById(R.id.TextSolat);
        textJam = findViewById(R.id.TextJam);
        jamKecil = findViewById(R.id.JamKecil);

        hariIni = findViewById(R.id.TextHariDetail);
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z"); output 2022.07.11 M at 15:27:33 WIB
        String currentDateandTime = new SimpleDateFormat("EEEE, dd MMMM yyyy").format(new Date());
        hariIni.setText(currentDateandTime);

        textKota = findViewById(R.id.TextKotaWaktu);

        myQueue = Volley.newRequestQueue(this);
        myQueue2 = Volley.newRequestQueue(this);
        myQueue3 = Volley.newRequestQueue(this);

        search = findViewById(R.id.SearchView);

        getRequest();
        getRequest2();
        getRequest3();

    }

    public static String idKota="14";
    public static String namaKota="bandung";

    private void getRequest(){
        String currentYear = new SimpleDateFormat("yyyy").format(new Date());
        String currentMonth = new SimpleDateFormat("MM").format(new Date());
        String currentDay = new SimpleDateFormat("dd").format(new Date());
        String url = "https://jadwal-shalat-api.herokuapp.com/daily?date="+currentYear+"-"+currentMonth+"-"+currentDay+"&cityId="+idKota;
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String currentDateandTime = sdf.format(new Date());
        String current = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response.getJSONObject("data");
                            JSONArray array = obj.getJSONArray("data");

                            JSONObject indSubuh = array.getJSONObject(0);
                            String dataJamSubuh = indSubuh.getString("time");
                            jamSubuh.setText(dataJamSubuh);
                            Date waktuSubuh = sdf.parse(dataJamSubuh);

                            JSONObject indDzuhur = array.getJSONObject(1);
                            String dataJamDzuhur = indDzuhur.getString("time");
                            jamDzuhur.setText(dataJamDzuhur);
                            Date waktuDzuhur = sdf.parse(dataJamDzuhur);

                            JSONObject indAshar = array.getJSONObject(2);
                            String dataJamAshar = indAshar.getString("time");
                            jamAshar.setText(dataJamAshar);
                            Date waktuAshar = sdf.parse(dataJamAshar);

                            JSONObject indMaghrib = array.getJSONObject(3);
                            String dataJamMaghrib = indMaghrib.getString("time");
                            jamMaghrib.setText(dataJamMaghrib);
                            Date waktuMaghrib = sdf.parse(dataJamMaghrib);

                            JSONObject indIsya = array.getJSONObject(4);
                            String dataJamIsya = indIsya.getString("time");
                            jamIsya.setText(dataJamIsya);
                            Date waktuIsya = sdf.parse(dataJamIsya);

                            Date currentHoursandMinute = sdf.parse(currentDateandTime);
                            if(currentHoursandMinute.before(waktuSubuh)) {
                                textSubuh.setTextColor(Color.parseColor("#FF018786"));
                                textSubuh.setTypeface(null, Typeface.BOLD);
                                jamSubuh.setTextColor(Color.parseColor("#FF018786"));
                                jamSubuh.setTypeface(null, Typeface.BOLD);
                                textSolat.setText("Subuh");
                                textJam.setText(dataJamSubuh);
                                DateTime targetDateTime = DateTime.parse(format("%s %s", current, dataJamSubuh), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));
                                DateTime now = DateTime.now();
                                Period period = new Period(now, targetDateTime);
                                int jam = period.getHours();
                                int menit = period.getMinutes();
                                jamKecil.setText(jam+" Jam dan "+menit+" menit lagi");
                            } else if (currentHoursandMinute.before(waktuDzuhur)){
                                textDzuhur.setTextColor(Color.parseColor("#FF018786"));
                                textDzuhur.setTypeface(null, Typeface.BOLD);
                                jamDzuhur.setTextColor(Color.parseColor("#FF018786"));
                                jamDzuhur.setTypeface(null, Typeface.BOLD);
                                textSolat.setText("Dzuhur");
                                textJam.setText(dataJamDzuhur);
                                DateTime targetDateTime = DateTime.parse(format("%s %s", current, dataJamDzuhur), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));
                                DateTime now = DateTime.now();
                                Period period = new Period(now, targetDateTime);
                                int jam = period.getHours();
                                int menit = period.getMinutes();
                                jamKecil.setText(jam+" Jam dan "+menit+" menit lagi");
                            } else if (currentHoursandMinute.before(waktuAshar)){
                                textAshar.setTextColor(Color.parseColor("#FF018786"));
                                textAshar.setTypeface(null, Typeface.BOLD);
                                jamAshar.setTextColor(Color.parseColor("#FF018786"));
                                jamAshar.setTypeface(null, Typeface.BOLD);
                                textSolat.setText("Ashar");
                                textJam.setText(dataJamAshar);
                                DateTime targetDateTime = DateTime.parse(format("%s %s", current, dataJamAshar), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));
                                DateTime now = DateTime.now();
                                Period period = new Period(now, targetDateTime);
                                int jam = period.getHours();
                                int menit = period.getMinutes();
                                jamKecil.setText(jam+" Jam dan "+menit+" menit lagi");
                            } else if (currentHoursandMinute.before(waktuMaghrib)){
                                textMaghrib.setTextColor(Color.parseColor("#FF018786"));
                                textMaghrib.setTypeface(null, Typeface.BOLD);
                                jamMaghrib.setTextColor(Color.parseColor("#FF018786"));
                                jamMaghrib.setTypeface(null, Typeface.BOLD);
                                textSolat.setText("Maghrib");
                                textJam.setText(dataJamMaghrib);
                                DateTime targetDateTime = DateTime.parse(format("%s %s", current, dataJamMaghrib), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));
                                DateTime now = DateTime.now();
                                Period period = new Period(now, targetDateTime);
                                int jam = period.getHours();
                                int menit = period.getMinutes();
                                jamKecil.setText(jam+" Jam dan "+menit+" menit lagi");
                            } else if (currentHoursandMinute.before(waktuIsya)){
                                textIsya.setTextColor(Color.parseColor("#FF018786"));
                                textIsya.setTypeface(null, Typeface.BOLD);
                                jamIsya.setTextColor(Color.parseColor("#FF018786"));
                                jamIsya.setTypeface(null, Typeface.BOLD);
                                textSolat.setText("Isya");
                                textJam.setText(dataJamIsya);
                                DateTime targetDateTime = DateTime.parse(format("%s %s", current, dataJamIsya), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));
                                DateTime now = DateTime.parse(format("%s", currentDateandTime), DateTimeFormat.forPattern("HH:mm"));
                                Period period = new Period(now, targetDateTime);
                                int jam = period.getHours();
                                int menit = period.getMinutes();
                                jamKecil.setText(jam+" Jam dan "+menit+" menit lagi");
                            } else {

                            }

                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        myQueue.add(request);
    }

    private void getRequest2(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        String currentYear = new SimpleDateFormat("yyyy").format(tomorrow);
        String currentMonth = new SimpleDateFormat("MM").format(tomorrow);
        String currentDay = new SimpleDateFormat("dd").format(tomorrow);
        String url = "https://jadwal-shalat-api.herokuapp.com/daily?date="+currentYear+"-"+currentMonth+"-"+currentDay+"&cityId="+idKota;
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String currentDateandTime = sdf.format(tomorrow);
        String current = new SimpleDateFormat("yyyy-MM-dd").format(tomorrow);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response.getJSONObject("data");
                            JSONArray array = obj.getJSONArray("data");

                            JSONObject indSubuh = array.getJSONObject(0);
                            String dataJamSubuh = indSubuh.getString("time");
                            jamSubuh.setText(dataJamSubuh);
                            Date waktuSubuh = sdf.parse(dataJamSubuh);

                            JSONObject indIsya = array.getJSONObject(4);
                            String dataJamIsya = indIsya.getString("time");
                            jamIsya.setText(dataJamIsya);
                            Date waktuIsya = sdf.parse(dataJamIsya);

                            Date currentHoursandMinute = sdf.parse(currentDateandTime);
                            if (currentHoursandMinute.after(waktuIsya)){
                                textSubuh.setTextColor(Color.parseColor("#FF018786"));
                                textSubuh.setTypeface(null, Typeface.BOLD);
                                jamSubuh.setTextColor(Color.parseColor("#FF018786"));
                                jamSubuh.setTypeface(null, Typeface.BOLD);
                                textSolat.setText("Subuh");
                                textJam.setText(dataJamSubuh);
                                DateTime targetDateTime = DateTime.parse(format("%s %s", current, dataJamSubuh), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));
                                DateTime now = DateTime.now();
                                Period period = new Period(now, targetDateTime);
                                int jam = period.getHours();
                                int menit = period.getMinutes();
                                jamKecil.setText(jam+" Jam dan "+menit+" menit lagi");
                            } else {

                            }

                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        myQueue2.add(request);
    }

    private void getRequest3(){
        String kota = search.getText().toString();
        String url = "https://jadwal-shalat-api.herokuapp.com/cities";
        textKota.setText(namaKota);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray array = response.getJSONArray("data");

                            for(int i=0; i<array.length(); i++){
                                JSONObject data = array.getJSONObject(i);
                                String namaKota = data.getString("cityName");

                                if(namaKota.equals(kota)){
                                    String idKota = data.getString("cityId");
                                    textKota.setText(namaKota);
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
}