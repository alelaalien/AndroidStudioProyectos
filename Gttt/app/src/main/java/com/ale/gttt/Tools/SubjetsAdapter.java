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

public class SubjetsAdapter extends BaseAdapter implements View.OnClickListener  {
    private Context context;
    private ArrayList<Subjet> list;
    private View.OnClickListener listener;

    public SubjetsAdapter(Context context, ArrayList<Subjet> list) {
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
            convertView= inflater.inflate(R.layout.subjets_list_items, null);
        }
        TextView name, hour, id;
        name= convertView.findViewById(R.id.tv_sub_name_item);
        hour= convertView.findViewById(R.id.tv_h_item);
        id= convertView.findViewById(R.id.tv_id_item);


        String sname, sid, shour;
        sname= list.get(position).getName();
        sid= String.valueOf(list.get(position).getId());
        shour= list.get(position).getClass_();
        name.setText(sname);
        hour.setText(shour);
        id.setText(sid);


        if  (position % 2 == 0){
            name.setTextColor(BLACK);
            name.setBackgroundColor(Color.parseColor("#FFFFFF"));
   }

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
