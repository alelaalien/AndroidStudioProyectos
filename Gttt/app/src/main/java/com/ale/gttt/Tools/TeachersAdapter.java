package com.ale.gttt.Tools;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ale.gttt.R;
import com.ale.gttt.entities.Subjet;
import com.ale.gttt.entities.Teacher;

import java.util.ArrayList;

import static android.graphics.Color.BLACK;

public class TeachersAdapter extends BaseAdapter implements View.OnClickListener  {
    private Context context;
    private ArrayList<Teacher> list;
    private View.OnClickListener listener;
    private Utilities u;

    public TeachersAdapter(Context context, ArrayList<Teacher> list) {
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
            convertView= inflater.inflate(R.layout.teachers_list_items, null);
        }
        TextView name, cel , subjets, id, email;
        name= convertView.findViewById(R.id.tvnamesteacher);
        cel= convertView.findViewById(R.id.tvcelt);
        email= convertView.findViewById(R.id.tvemailt);



        String sname, sid, ssubjets, scel, semail, snick, ssurname, snames, stel, smail;
        snick= list.get(position).getNick();
        ssurname= list.get(position).getSurname();
        sname= list.get(position).getName();
        sid= String.valueOf(list.get(position).getId());
        scel=String.valueOf(list.get(position).getCelphone());
        semail=list.get(position).getEmail();
        ssubjets=String.valueOf(list.get(position).getSubjet());
        u=new Utilities();
        snames=  u.ToUpper(sname)+" '" +u.ToUpper(snick)+"' "+u.ToUpper(ssurname);
        stel="Contacto: "+scel;
        smail="Email: "+semail;

        name.setText(snames);
        email.setText(smail);
        cel.setText(stel);

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
