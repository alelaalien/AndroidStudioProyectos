package com.ale.gttt.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ale.gttt.R;
import com.ale.gttt.entities.AuxiliarSch;

import java.util.ArrayList;

public class AuxAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AuxiliarSch> list;

    public AuxAdapter(Context context, ArrayList<AuxiliarSch> list) {
        this.context = context;
        this.list = list;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       if (convertView==null){
           LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
           convertView= inflater.inflate(R.layout.subjet_item, null);
       }
        TextView name, notes;
        name= convertView.findViewById(R.id.i_title);

String text= list.get(position).getType()+": "+list.get(position).getDay()+" de "+list.get(position).getStart()+" a "+list.get(position).getEnd();
        name.setText(text);




        return convertView;
    }
}
