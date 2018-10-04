package com.example.admin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Korn on 09/11/2017.
 */

public class History extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {


  private ListView patientInformationtListView;
  private ProgressBar historyProgressBar;

  private String ServerURL = "https://neuropterous-object.000webhostapp.com/src/php/process_history.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        patientInformationtListView = (ListView) findViewById(R.id.measureList);

        historyProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        new History.GetHttpResponse(History.this).execute();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.filter:
                // Log.i(TAG, "start settings activity");
                Intent intent = new Intent(History.this, Filter.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.MainActivity) {
            Intent searchIntent = new Intent(History.this, MainActivity.class);
            startActivity(searchIntent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

        } else if (id == R.id.nav_graph) {
            Intent searchIntent = new Intent(History.this, Graph.class);
            startActivity(searchIntent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

        }else if (id==R.id.nav_setting){
            Intent searchIntent = new Intent(History.this, Setting.class);
            startActivity(searchIntent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

        } else if (id == R.id.nav_help) {
            Intent searchIntent = new Intent(History.this, Help.class);
            startActivity(searchIntent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class GetHttpResponse extends AsyncTask<Void, Void, Void> {
        public Context context;

        String ResultHolder;

        List<PatientInformation> patientInformationList;

        String data = "";
        String dataParsed = "";
        String singleDataParsed = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        public GetHttpResponse(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpServicesClass httpServiceObject = new HttpServicesClass(ServerURL);
            try {
                httpServiceObject.AddParam("actionId", "disp_pat_history_json");
                httpServiceObject.AddParam("CPRNo", "3754375");
                httpServiceObject.ExecutePostRequest();

                if (httpServiceObject.getResponseCode() == 200) {
                    ResultHolder = httpServiceObject.getResponse();

                    Log.d("JSON is: ", ResultHolder);

                    if (ResultHolder != null) {
                        JSONArray jsonArray = null;
                        JSONObject jsonObject = null;


                        try {
                            String resultEscaped = ResultHolder.replaceAll("\\\\", "");
                            resultEscaped = resultEscaped.substring(1, resultEscaped.length() - 1);
                            Log.d("EscapedString: ", resultEscaped);

                            jsonArray = new JSONArray(resultEscaped);
                            Log.d("jsonArray is: ", jsonArray.toString());

                            patientInformationList = new ArrayList<PatientInformation>();
                            PatientInformation patientInformation;

                            for (int i = 0; i < jsonArray.length(); i++) {
                                patientInformation = new PatientInformation();

                                jsonObject = jsonArray.getJSONObject(i);

                                patientInformation.setMeasuredLevel(jsonObject.getDouble("Measured_Level"));
                                patientInformation.setDate(dateFormat.parse(jsonObject.getString("Date")));

                                patientInformationList.add(patientInformation);
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(context, httpServiceObject.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)

        {
            historyProgressBar.setVisibility(View.GONE);


            patientInformationtListView.setVisibility(View.VISIBLE);

            if (patientInformationList != null) {
                ListAdapterClass adapter = new ListAdapterClass(context, patientInformationList);

                patientInformationtListView.setAdapter(adapter);
            }
        }
    }
}
