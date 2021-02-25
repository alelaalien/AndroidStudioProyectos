package com.ale.gttt.Tools;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ale.gttt.Interfaces.ISSubjet;
import com.ale.gttt.R;
import com.ale.gttt.entities.Event;
import com.ale.gttt.entities.Subjet;
import com.ale.gttt.io.ServiceBA;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsAdapter extends BaseAdapter implements View.OnClickListener  {
    private Context context;
    private ArrayList<Event> list;
    private ArrayList<Subjet> sublist;
    private View.OnClickListener listener;
    private String name;



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(R.layout.event_items, null);
        }
        TextView tvtitle, tvhour, tvdate, tvtypeof, tvcolor;
        tvtitle= convertView.findViewById(R.id.tv_event_title);
        tvdate= convertView.findViewById(R.id.tv_event_date);
        tvhour= convertView.findViewById(R.id.tv_event_hour);
        tvtypeof= convertView.findViewById(R.id.tv_event_typesub);
        tvcolor= convertView.findViewById(R.id.tv_event_color);

        String  title, hour, date, typeof, color, sub, first, second, third, fourth;
        int priority,  idsubjet, idse;


        sub=name;
        priority=list.get(position).getPriority();
        title=list.get(position).getTitle();
        sub="";
        date=list.get(position).getDate();
        hour="Hora: "+date.substring(date.indexOf("T")+1, date.length());
        date="Fecha: "+date.substring(0, date.indexOf("T"));

        if (priority==0){
            tvcolor.setBackgroundColor(Color.parseColor("#C8F90E12"));
        }else   if (priority==1){
            tvcolor.setBackgroundColor(Color.parseColor("#DAFFEB3B"));
        }else {
            tvcolor.setBackgroundColor(Color.parseColor("#C48BE91F"));
        }

        if (list.get(position).getTypeOf()==0){
            typeof="Exámen";
        }else{
            typeof="Trabajo Práctico";
        }
        for (int i=0;i<sublist.size();i++){
            if (list.get(position).getIdSubjet()==sublist.get(i).getId()){
               sub=sublist.get(i).getName();
            }
        }


         second= typeof+" de "+sub;


       tvtypeof.setText(title);
        tvtitle.setText(second);
        tvhour.setText(hour);
        tvdate.setText(date);

        return convertView;
    }


    public EventsAdapter(Context context, ArrayList<Event> list, ArrayList<Subjet> sublist) {
        this.context = context;
        this.list = list;
        this.sublist=sublist;
    }

    @Override
    public int getCount() {
        return list.size() ;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnClickListenet(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

}
