package com.example.coronavirusstats;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TotalStats extends AppCompatActivity {
    TextView txtTotalCases;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_stats);

        String[] totalCases=getIntent().getStringArrayExtra("totalInfo");

        txtTotalCases=findViewById(R.id.txtTotalCasesAll);

        txtTotalCases.setText(totalCases[0]);

    }
}
