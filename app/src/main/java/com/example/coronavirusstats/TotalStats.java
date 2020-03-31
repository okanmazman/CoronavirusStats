package com.example.coronavirusstats;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TotalStats extends AppCompatActivity {
    TextView txtTotalCases;
    TextView txtTotalDeaths;
    TextView txtTotalRecovered;
    List<CoronaItem>totalList=new ArrayList<CoronaItem>();
    ProgressDialog prpDialogTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_stats);

    }

    @Override
    protected void onStart() {
        super.onStart();
        GetTotalCoronaInfo tci=new GetTotalCoronaInfo();
        tci.execute("https://coronavirus-19-api.herokuapp.com/all");




    }
    class GetTotalCoronaInfo  extends AsyncTask<String,Void,String>
    {
        String result;
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            prpDialogTotal = new ProgressDialog(TotalStats.this);
            prpDialogTotal.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prpDialogTotal.setTitle("Loading...");
            prpDialogTotal.setMessage("Fetching Data ...Please Wait...");
            prpDialogTotal.show();
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

                //JSONArray newsjsonarr=new JSONArray(tci.result);
                JSONObject obj=new JSONObject(s);

                CoronaItem coronaItem = new CoronaItem();
                coronaItem.setCases(obj.getInt("cases"));
                coronaItem.setDeaths(obj.getInt("deaths"));
                coronaItem.setRecovered(obj.getInt("recovered"));
                totalList.add(coronaItem);
                prpDialogTotal.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(TotalStats.this,e.getMessage(),Toast.LENGTH_LONG).show();

            }
            txtTotalCases=findViewById(R.id.txtTotalCasesAll);
            txtTotalDeaths=findViewById(R.id.txtTotalDeaths);
            txtTotalRecovered=findViewById(R.id.txtTotalRecovered);

            if(totalList.size()>0)
            {
                txtTotalCases.setText(String.valueOf(totalList.get(0).getCases()));
                txtTotalDeaths.setText(String.valueOf(totalList.get(0).getDeaths()));
                txtTotalRecovered.setText(String.valueOf(totalList.get(0).getRecovered()));
            }
            prpDialogTotal.hide();
        }
    }
}
