package com.example.coronavirusstats;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CoronaAdapter extends BaseAdapter {
    List<CoronaItem> clist=null;
    List<CoronaItem>filteredClist=new ArrayList<CoronaItem>();
    Context mContext;
    LayoutInflater inflater;
    public CoronaAdapter(Context context,  List<CoronaItem> objects) {
        mContext = context;
        this.clist = objects;
        inflater = LayoutInflater.from(mContext);
        this.filteredClist.addAll(clist);
    }

    @Override
    public int getCount() {
       return clist.size();
    }

    @Override
    public Object getItem(int position) {
        return clist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //@Override
    public View getView(int position,View convertView, ViewGroup parent) {

        View row=convertView;
        ViewHolder holder=null;
        String imagePath;


        row=inflater.inflate(R.layout.listview_row_layout, null);
        //row=((Activity)getContext()).getLayoutInflater().inflate(R.layout.listview_row_layout,parent,false);
        holder=new ViewHolder(row);
        row.setTag(holder);

        try {
            holder.getTxtCountry().setText(clist.get(position).getCountry().toString());
            holder.getTxtCases().setText(String.valueOf(clist.get(position).getCases()));
            holder.getTxtDeaths().setText(String.valueOf(clist.get(position).getDeaths()));
            holder.getTxtRecovered().setText(String.valueOf(clist.get(position).getRecovered()));
            holder.getTxtTodayCases().setText(String.valueOf(clist.get(position).getTodayCases()));
            /*holder.getTxtCountry().setText(getItem(position).getCountry().toString());
            holder.getTxtCases().setText(String.valueOf(getItem(position).getCases()));
            holder.getTxtDeaths().setText(String.valueOf(getItem(position).getDeaths()));
            holder.getTxtRecovered().setText(String.valueOf(getItem(position).getRecovered()));
            holder.getTxtTodayCases().setText(String.valueOf(getItem(position).getTodayCases()));*/
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        return row;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        clist.clear();
        if (charText.length() == 0) {
            clist.addAll(filteredClist);
        } else {
            for (CoronaItem wp : filteredClist) {
                if (wp.getCountry().toLowerCase(Locale.getDefault()).contains(charText)) {
                    clist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
    public class ViewHolder
    {

        TextView txtCountry;
        TextView txtCases;
        TextView txtDeaths;
        TextView txtRecovered;
        TextView txtTodayCases;
        View base; //tüm layout

        public ViewHolder(View base)
        {
            this.base=base;
        }




        public TextView getTxtCountry() {
            if(txtCountry==null){
                txtCountry=base.findViewById(R.id.txtCountry);
            }
            return txtCountry;
        }

        public TextView getTxtCases() {
            if(txtCases==null){
                txtCases=base.findViewById(R.id.txtCases);
            }
            return txtCases;
        }
        public TextView getTxtDeaths() {
            if(txtDeaths==null){
                txtDeaths=base.findViewById(R.id.txtDeaths);
            }
            return txtDeaths;
        }
        public TextView getTxtRecovered() {
            if(txtRecovered==null){
                txtRecovered=base.findViewById(R.id.txtRecovered);
            }
            return txtRecovered;
        }
        public TextView getTxtTodayCases() {
            if(txtTodayCases==null){
                txtTodayCases=base.findViewById(R.id.txtTodayCases);
            }
            return txtTodayCases;
        }


    }
}
