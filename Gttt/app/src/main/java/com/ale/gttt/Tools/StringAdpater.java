package com.ale.gttt.Tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ale.gttt.R;
import com.ale.gttt.entities.Subjet;

import java.util.ArrayList;

import static android.graphics.Color.BLACK;

public class StringAdpater  extends BaseAdapter implements View.OnClickListener{

    private Context context;
    private ArrayList<String> list;
    private View.OnClickListener listener;


    public StringAdpater(Context context, ArrayList<String> list  ) {
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

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(R.layout.simple_subjet_item, null);
        }
        TextView name, hour, id;
        name= convertView.findViewById(R.id.tv_simple_subjet_item);
        String sname, sid ;
        sname= list.get(position);

        name.setText(sname);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }

    }

    public void setOnClickListenet(View.OnClickListener listener){
        this.listener=listener;
    }

}
