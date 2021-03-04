package com.ale.gttt.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ale.gttt.R;
import com.ale.gttt.entities.TypeOf;

import java.util.ArrayList;

public class TypeAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private ArrayList<TypeOf> list;
    private View.OnClickListener listener;
    private Utilities u;

    public TypeAdapter(Context context, ArrayList<TypeOf> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onClick(View v) {

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
            convertView= inflater.inflate(R.layout.item_type, null);
        }
        TextView name;
        name= convertView.findViewById(R.id.tv_item_type);
        String desc=list.get(position).getDescription();
        name.setText(desc);

        return convertView;
    }
}


