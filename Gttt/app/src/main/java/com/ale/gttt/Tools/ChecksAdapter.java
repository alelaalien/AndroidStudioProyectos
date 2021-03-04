package com.ale.gttt.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.ale.gttt.R;
import com.ale.gttt.entities.Subjet;

import java.util.ArrayList;

public class ChecksAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private ArrayList<Subjet> list;
    private View.OnClickListener listener;
    private String name;

    public ChecksAdapter(Context context, ArrayList<Subjet> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
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

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(R.layout.subchecks,  null);
        }
        CheckBox c=convertView.findViewById(R.id.checkBox);
        c.setText(list.get(position).getName());

        return convertView;
    }
}
