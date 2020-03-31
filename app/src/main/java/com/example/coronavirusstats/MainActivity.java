package com.example.coronavirusstats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
public ListView coronaListView;
public List<CoronaItem>coronaList=new ArrayList<CoronaItem>();

    ListView list;
    SearchView editsearch;
    CoronaAdapter ca;
    ProgressDialog prpDialogg;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView lstUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.coronaListView);
        mSwipeRefreshLayout = findViewById(R.id.swiperefresh_items);
        try
        {

            editsearch = findViewById(R.id.search);

            editsearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editsearch.setIconified(false);
                }
            });
            editsearch.setOnQueryTextListener(this);
        }
        catch (Exception ex)
        {
             ex.printStackTrace();
        };

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetCoronaInfoAll ci=new GetCoronaInfoAll();
                ci.execute("https://coronavirus-19-api.herokuapp.com/countries");
                lstUpdate=findViewById(R.id.txtLastUpdate);
                SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                String strDate = formatter.format(new Date());
                lstUpdate.setText("Last Update: "+strDate);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 500);
            }
        });


    }

    @Override
    protected void onStart() {
            super.onStart();
            GetCoronaInfoAll ci=new GetCoronaInfoAll();
            ci.execute("https://coronavirus-19-api.herokuapp.com/countries");
        lstUpdate=findViewById(R.id.txtLastUpdate);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(new Date());
        lstUpdate.setText("Last Update: "+strDate);

    }



    class GetCoronaInfoAll extends AsyncTask<String,Void,String>
    {
        String result;
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            prpDialogg = new ProgressDialog(MainActivity.this);
            prpDialogg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prpDialogg.setTitle("Loading...");
            prpDialogg.setMessage("Please wait ...");
            prpDialogg.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url=new URL(strings[0]);
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                BufferedReader reader=new BufferedReader(new InputStreamReader(
                        conn.getInputStream()
                ));

                StringBuilder strBuilder = new StringBuilder();
                String line="";

                while((line= reader.readLine())!=null){

                    strBuilder.append(line);
                }
                reader.close();
                //Log.i( "DEV1",strBuilder.toString());
                result= strBuilder.toString();
            }
            catch(IOException e){
                e.printStackTrace();
                result = null;
            }
            return  result;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                coronaList.removeAll(coronaList);
                JSONArray newsjsonarr=new JSONArray(s);
               // JSONObject obj=new JSONObject(s);


                for(int i=0;i<newsjsonarr.length();i++) {

                    JSONObject obj =newsjsonarr.getJSONObject(i);
                    CoronaItem coronaItem = new CoronaItem();

                    coronaItem.setCountry(obj.getString("country"));
                    coronaItem.setCases(obj.getInt("cases"));
                    coronaItem.setDeaths(obj.getInt("deaths"));
                    coronaItem.setActive(obj.getInt("active"));
                    coronaItem.setCasesPerMillion(obj.getInt("casesPerOneMillion"));
                    coronaItem.setCritical(obj.getInt("critical"));
                    coronaItem.setRecovered(obj.getInt("recovered"));
                    coronaItem.setTodayCases(obj.getInt("todayCases"));
                    coronaItem.setTodayDeaths(obj.getInt("todayDeaths"));

                    coronaList.add(coronaItem);
                }

                try
                {
                    ca=new CoronaAdapter(MainActivity.this,coronaList);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                coronaListView=findViewById(R.id.coronaListView);
                coronaListView.setAdapter(ca);



                prpDialogg.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();

            }

        }

    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        ca.filter(text);
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.mainmenu,menu);

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        switch (id){
            case R.id.mainMenu:
                Intent i=new Intent(MainActivity.this,TotalStats.class);

                //Toast.makeText(this,lstUpdate.getText(),Toast.LENGTH_LONG).show();

                startActivity(i);
                break;
            default:
                break;
        }

        return true;

    }


}
