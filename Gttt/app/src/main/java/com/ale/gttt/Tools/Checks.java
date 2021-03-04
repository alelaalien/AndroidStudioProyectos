package com.ale.gttt.Tools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ale.gttt.AddEventActivity;
import com.ale.gttt.AddType;
import com.ale.gttt.Interfaces.ISType;
import com.ale.gttt.R;
import com.ale.gttt.Session.SharedPreferenceManager;
import com.ale.gttt.entities.TypeOf;
import com.ale.gttt.io.ServiceBA;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Checks extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<String> values;
    private ArrayList<TypeOf> types;
    private RadioGroup r;
    private Button btnacept, btnaddnew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.simple_layout);

         Call();
        Init();

}
private void Init(){
        btnacept=findViewById(R.id.btnselecttype);
        btnaddnew=findViewById(R.id.btnaddnewtype);
        btnaddnew.setOnClickListener(this);
        btnacept.setOnClickListener(this);
//    Intent dataintent= getIntent();
//
//     values=dataintent.getStringArrayListExtra("dataaddevent");
//    Log.d("array", values.size()+"");


}

private void GetChecks(ArrayList<TypeOf> types){
    LinearLayout l=  findViewById(R.id.lay_check);
     r=new RadioGroup(getApplicationContext());
    r.setOrientation(RadioGroup.VERTICAL);
    RadioGroup.LayoutParams rl2;
    for (int i=0; i<types.size();i++){
        RadioButton r1=new RadioButton(getApplicationContext());
        r1.setText(types.get(i).getDescription());

        r1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        rl2=new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,RadioGroup.LayoutParams.MATCH_PARENT);
        r.addView(r1, rl2);

        if (r.getParent()!=null){
            ((ViewGroup)r.getParent()).removeView(r);
        }l.addView(r);


    }
}

private void Call(){
        types=new ArrayList<>();
        int idU= SharedPreferenceManager.getInstance(getApplicationContext()).GetUser().getId();
    Call<ArrayList<TypeOf>> call= ServiceBA.getInstance().createService(ISType.class).GetAll(idU);
    call.enqueue(new Callback<ArrayList<TypeOf>>() {
        @Override
        public void onResponse(Call<ArrayList<TypeOf>> call, Response<ArrayList<TypeOf>> response) {
            if (response.code()==200){
                types.addAll(response.body());
                GetChecks(types);

            }else if(response.code()==500){
                Toast.makeText(getApplicationContext(), "Error 500", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<ArrayList<TypeOf>> call, Throwable t) {
            Toast.makeText(getApplicationContext(), "Error "+t.getMessage(), Toast.LENGTH_LONG).show();

        }
    });
}

    @Override
    public void onClick(View v) {

        if (v==btnacept){
            if (r!=null){

                int id= r.getCheckedRadioButtonId();
                String text, data;
                RadioButton rb=findViewById(id);
                if (rb!=null){

                     text= rb.getText().toString();

                     Intent i=new Intent(getApplicationContext(), AddEventActivity.class);
                     i.putExtra("types", text);
                     i.putStringArrayListExtra("backval", values);
                     startActivity(i);

                }else{
                    Toast.makeText(getApplicationContext(), "Selecciona una opcion para continuar", Toast.LENGTH_LONG).show();

                }


            }else{
                Log.d("tag", "r   es null");

            }

        }
        if (v==btnaddnew){

            Intent i=new Intent(getApplicationContext(), AddType.class);
           // i.putStringArrayListExtra("arrayE", values);
            startActivity(i);

        }
    }
}
