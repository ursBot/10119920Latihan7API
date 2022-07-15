package com.apps.a10119920latihan7api;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.*;
import android.icu.text.SimpleDateFormat;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.*;

import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;

import static java.lang.String.format;


public class JadwalActivity extends AppCompatActivity{

    private String subuhToday, dzuhurToday, asharToday, maghribToday, isyaToday;
    private Date waktuSubuhToday, waktuDzuhurToday, waktuAsharToday, waktuMaghribToday, waktuIsyaToday;

    private String subuhTomorrow, dzuhurTomorrow, asharTomorrow, maghribTomorrow, isyaTomorrow;
    //private Date waktuSubuhTomorrow, waktuDzuhurTomorrow, waktuAsharTomorrow, waktuMaghribTomorrow, waktuIsyaTomorrow;

    @SuppressLint("SimpleDateFormat")
    private final String currentYear = new SimpleDateFormat("yyyy").format(new Date());
    @SuppressLint("SimpleDateFormat")
    private final String currentMonth = new SimpleDateFormat("MM").format(new Date());
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    private String id;

    Button button;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HideActionBar();

        BindExtra();
        FetchToday();
        FetchTomorrow();

        try {
            InitContent();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void HideActionBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_jadwal);
    }

    private void FetchToday(){
        RequestQueue myQueue = Volley.newRequestQueue(this);
        @SuppressLint("SimpleDateFormat") String today = new SimpleDateFormat("dd").format(new Date());
        String url = "https://jadwal-shalat-api.herokuapp.com/daily?date="+currentYear+"-"+currentMonth+"-"+today+"&cityId="+id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject obj = response.getJSONObject("data");
                        JSONArray array = obj.getJSONArray("data");

                        JSONObject indSubuh = array.getJSONObject(0);
                        subuhToday = indSubuh.getString("time");
                        waktuSubuhToday = sdf.parse(subuhToday);
                        Log.d("debugggg",""+subuhToday);

                        JSONObject indDzuhur = array.getJSONObject(1);
                        dzuhurToday = indDzuhur.getString("time");
                        waktuDzuhurToday = sdf.parse(dzuhurToday);

                        JSONObject indAshar = array.getJSONObject(2);
                        asharToday = indAshar.getString("time");
                        waktuAsharToday = sdf.parse(asharToday);

                        JSONObject indMaghrib = array.getJSONObject(3);
                        maghribToday = indMaghrib.getString("time");
                        waktuMaghribToday = sdf.parse(maghribToday);

                        JSONObject indIsya = array.getJSONObject(4);
                        isyaToday = indIsya.getString("time");
                        waktuIsyaToday = sdf.parse(isyaToday);
                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);
        myQueue.add(request);
    }

    private void FetchTomorrow(){
        RequestQueue myQueue = Volley.newRequestQueue(this);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        @SuppressLint("SimpleDateFormat") String today = new SimpleDateFormat("dd").format(tomorrow);
        String url = "https://jadwal-shalat-api.herokuapp.com/daily?date="+currentYear+"-"+currentMonth+"-"+today+"&cityId="+id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject obj = response.getJSONObject("data");
                        JSONArray array = obj.getJSONArray("data");

                        JSONObject indSubuh = array.getJSONObject(0);
                        subuhTomorrow = indSubuh.getString("time");

                        JSONObject indDzuhur = array.getJSONObject(1);
                        dzuhurTomorrow = indDzuhur.getString("time");

                        JSONObject indAshar = array.getJSONObject(2);
                        asharTomorrow = indAshar.getString("time");

                        JSONObject indMaghrib = array.getJSONObject(3);
                        maghribTomorrow = indMaghrib.getString("time");

                        JSONObject indIsya = array.getJSONObject(4);
                        isyaTomorrow = indIsya.getString("time");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);
        myQueue.add(request);
    }

    private void BindExtra(){
        TextView textKota = findViewById(R.id.TextKotaWaktu);
        Intent intent = getIntent();
        String kota = intent.getStringExtra(MainActivity.CITY_NAME_KEY);
        textKota.setText(kota);

        id = intent.getStringExtra(MainActivity.ID_CITY_KEY);
    }

    @SuppressLint("SetTextI18n")
    private void InitContent() throws ParseException {
        @SuppressLint("SimpleDateFormat") String jamSekarang = new SimpleDateFormat("HH:mm").format(new Date());
        @SuppressLint("SimpleDateFormat") String tanggalFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Date jamSekarangFormat = sdf.parse(jamSekarang);

        TextView textSolat = findViewById(R.id.TextSolat);
        TextView textJam = findViewById(R.id.TextJam);
        TextView jamKecil = findViewById(R.id.JamKecil);

        TextView jamSubuh = findViewById(R.id.JamSubuh);
        TextView textSubuh = findViewById(R.id.TextSubuh);

        if(jamSekarangFormat.before(waktuSubuhToday)) {
            jamSubuh.setText(subuhToday);
            jamSubuh.setTextColor(Color.parseColor("#FF018786"));
            jamSubuh.setTypeface(null, Typeface.BOLD);
            textSubuh.setTextColor(Color.parseColor("#FF018786"));
            textSubuh.setTypeface(null, Typeface.BOLD);

            textSolat.setText("Subuh");
            textJam.setText(subuhToday);
            DateTime targetDateTime = DateTime.parse(format("%s %s", tanggalFormat, subuhToday), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));
            DateTime now = DateTime.now();
            Period period = new Period(now, targetDateTime);
            int jam = period.getHours();
            int menit = period.getMinutes();
            jamKecil.setText(jam+" Jam dan "+menit+" menit lagi");
        }else{
            jamSubuh.setText(subuhTomorrow);
        }

        TextView jamDzuhur = findViewById(R.id.JamDzuhur);
        TextView textDzuhur = findViewById(R.id.TextDzuhur);

        if(jamSekarangFormat.before(waktuDzuhurToday) && jamSekarangFormat.after(waktuSubuhToday)) {
            jamDzuhur.setText(dzuhurToday);
            jamDzuhur.setTextColor(Color.parseColor("#FF018786"));
            jamDzuhur.setTypeface(null, Typeface.BOLD);
            textDzuhur.setTextColor(Color.parseColor("#FF018786"));
            textDzuhur.setTypeface(null, Typeface.BOLD);

            textSolat.setText("Dzuhur");
            textJam.setText(dzuhurToday);
            DateTime targetDateTime = DateTime.parse(format("%s %s", tanggalFormat, dzuhurToday), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));
            DateTime now = DateTime.now();
            Period period = new Period(now, targetDateTime);
            int jam = period.getHours();
            int menit = period.getMinutes();
            jamKecil.setText(jam+" Jam dan "+menit+" menit lagi");
        }else{
            jamDzuhur.setText(dzuhurTomorrow);
        }

        TextView jamAshar = findViewById(R.id.JamAshar);
        TextView textAshar = findViewById(R.id.TextAshar);

        if(jamSekarangFormat.before(waktuAsharToday) && jamSekarangFormat.after(waktuDzuhurToday)) {
            jamAshar.setText(asharToday);
            jamAshar.setTextColor(Color.parseColor("#FF018786"));
            jamAshar.setTypeface(null, Typeface.BOLD);
            textAshar.setTextColor(Color.parseColor("#FF018786"));
            textAshar.setTypeface(null, Typeface.BOLD);

            textSolat.setText("Ashar");
            textJam.setText(asharToday);
            DateTime targetDateTime = DateTime.parse(format("%s %s", tanggalFormat, asharToday), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));
            DateTime now = DateTime.now();
            Period period = new Period(now, targetDateTime);
            int jam = period.getHours();
            int menit = period.getMinutes();
            jamKecil.setText(jam+" Jam dan "+menit+" menit lagi");
        }else{
            jamAshar.setText(asharTomorrow);
        }

        TextView jamMaghrib = findViewById(R.id.JamMaghrib);
        TextView textMaghrib = findViewById(R.id.TextMaghrib);

        if(jamSekarangFormat.before(waktuMaghribToday) && jamSekarangFormat.after(waktuAsharToday)) {
            jamMaghrib.setText(maghribToday);
            jamMaghrib.setTextColor(Color.parseColor("#FF018786"));
            jamMaghrib.setTypeface(null, Typeface.BOLD);
            textMaghrib.setTextColor(Color.parseColor("#FF018786"));
            textMaghrib.setTypeface(null, Typeface.BOLD);

            textSolat.setText("Maghrib");
            textJam.setText(maghribToday);
            DateTime targetDateTime = DateTime.parse(format("%s %s", tanggalFormat, maghribToday), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));
            DateTime now = DateTime.now();
            Period period = new Period(now, targetDateTime);
            int jam = period.getHours();
            int menit = period.getMinutes();
            jamKecil.setText(jam+" Jam dan "+menit+" menit lagi");
        }else{
            jamMaghrib.setText(maghribTomorrow);
        }

        TextView jamIsya = findViewById(R.id.JamIsya);
        TextView textIsya = findViewById(R.id.TextIsya);

        if(jamSekarangFormat.before(waktuIsyaToday) && jamSekarangFormat.after(waktuMaghribToday)) {
            jamIsya.setText(isyaToday);
            jamIsya.setTextColor(Color.parseColor("#FF018786"));
            jamIsya.setTypeface(null, Typeface.BOLD);
            textIsya.setTextColor(Color.parseColor("#FF018786"));
            textIsya.setTypeface(null, Typeface.BOLD);

            textSolat.setText("Isya");
            textJam.setText(isyaToday);
            DateTime targetDateTime = DateTime.parse(format("%s %s", tanggalFormat, isyaToday), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));
            DateTime now = DateTime.now();
            Period period = new Period(now, targetDateTime);
            int jam = period.getHours();
            int menit = period.getMinutes();
            jamKecil.setText(jam+" Jam dan "+menit+" menit lagi");
        }else if(jamSekarangFormat.after(waktuIsyaToday)){
            jamIsya.setText(isyaTomorrow);

            jamSubuh.setText(subuhTomorrow);
            jamSubuh.setTextColor(Color.parseColor("#FF018786"));
            jamSubuh.setTypeface(null, Typeface.BOLD);
            textSubuh.setTextColor(Color.parseColor("#FF018786"));
            textSubuh.setTypeface(null, Typeface.BOLD);

            textSolat.setText("Subuh");
            textJam.setText(subuhTomorrow);
            DateTime targetDateTime = DateTime.parse(format("%s %s", tanggalFormat, subuhTomorrow), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));
            DateTime now = DateTime.now();
            Period period = new Period(now, targetDateTime);
            int jam = period.getHours();
            int menit = period.getMinutes();
            jamKecil.setText(jam+" Jam dan "+menit+" menit lagi");
        }

        @SuppressLint("SimpleDateFormat") String tanggal = new SimpleDateFormat("EEEE, dd MMMM yyyy").format(new Date());
        TextView hariIni = findViewById(R.id.TextHariDetail);
        hariIni.setText(tanggal);

        button = findViewById(R.id.ButtonGantiKota);
        button.setOnClickListener(view -> startActivity(new Intent(JadwalActivity.this,MainActivity.class)));
    }

}