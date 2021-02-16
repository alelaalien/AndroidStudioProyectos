package com.ale.gttt.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.ale.gttt.Interfaces.ISEvent;
import com.ale.gttt.Interfaces.ISSubjet;
import com.ale.gttt.Interfaces.ISTeacher;
import com.ale.gttt.Interfaces.ISUser;
import com.ale.gttt.Main2Activity;
import com.ale.gttt.R;
import com.ale.gttt.Session.SharedPreferenceManager;
import com.ale.gttt.entities.User;
import com.ale.gttt.io.ServiceBA;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int idUser;
    private String mParam1;
    private String mParam;

    private OnFragmentInteractionListener mListener;

    private EditText etnickperfil, etemailperfil, etpass1, etpass2;
    private Button btnaceptar, btncancelar, btneliminar;


    public UserFragment() {

    }


    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam1 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_user, container, false);
        Init(v);
        Config(v);
        Listeners();
        return v;
    }

    private void Init(View v) {
        etnickperfil=v.findViewById(R.id.etnickperfil);
        etemailperfil=v.findViewById(R.id.etmailperfil);
        etpass1=v.findViewById(R.id.etpassperfil1);
        etpass2=v.findViewById(R.id.etpass2perfil);
        btnaceptar=v.findViewById(R.id.btneditarperfil);
        btncancelar=v.findViewById(R.id.btncancelarperfil);
        btneliminar=v.findViewById(R.id.btndelete);
        idUser=SharedPreferenceManager.getInstance(getContext()).GetUser().getId();


    }

    private void Config(View v) {
        User u = SharedPreferenceManager.getInstance(getContext()).GetUser();
        etnickperfil.setText(u.getNick());
        etnickperfil.setSelectAllOnFocus(true);
        etpass1.setText(u.getPassword());
        etpass1.setSelectAllOnFocus(true);
        etpass2.setText(u.getPassword());
        etpass2.setSelectAllOnFocus(true);
        etemailperfil.setText(u.getEmail());
        etemailperfil.setSelectAllOnFocus(true);

        Enable(false);

    }

    private void Listeners() {
        btnaceptar.setOnClickListener(this);
        btncancelar.setOnClickListener(this);
        btneliminar.setOnClickListener(this);
    }

    public void onClick(View v){
        String action, acept, edit;
        action=btnaceptar.getText().toString();
        acept="Aceptar";
        edit="Editar";


        if (v==btnaceptar){
            if(action.equals("Editar")){
                Enable(true);
                btnaceptar.setText(acept);
            }else if(action.equals("Aceptar")){

                GetValues();
                btnaceptar.setText(edit);
            }
        }
        if (v==btncancelar){
            Restart(v);
        }
        if (v==btneliminar){
            Alert();
        }
    }

    private void Delete() {


       Call<Void> call= ServiceBA.getInstance().createService(ISUser.class).Delete(idUser);
       call.enqueue(new Callback<Void>() {
           @Override
           public void onResponse(Call<Void> call, Response<Void> response) {
            if(response.code()==200){
                Main();
             }else if(response.code()==404){
               Toast.makeText(getContext(), "Recursos no encontrados", Toast.LENGTH_LONG).show();
               Log.d("borrar", "404");
           }else if(response.code()==500){
               Toast.makeText(getContext(), "Hubo un error en el servidor. Reintentar", Toast.LENGTH_LONG).show();
               Log.d("borrare", "500");
           }
           }
           @Override
           public void onFailure(Call<Void> call, Throwable t) {
Toast.makeText(getContext(), "error: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
    }

    private void Restart(View v) {
        String text="Editar";
        Config(v);
        Enable(false);
        btnaceptar.setText(text);
    }

    private void Enable(boolean i) {
        etemailperfil.setEnabled(i);
        etpass2.setEnabled(i);
        etpass1.setEnabled(i);
        etnickperfil.setEnabled(i);
    }


    private void Update(User u) {
if (u!=null){


            Call<User> call= ServiceBA.getInstance().createService(ISUser.class).Update(idUser, u);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code()==200){
                Main();

                    }else if(response.code()==404){
                        Toast.makeText(getContext(), "Recursos no encontrados", Toast.LENGTH_LONG).show();
                        Log.d("2user update", "404");
                    }else if(response.code()==500){
                        Toast.makeText(getContext(), "Hubo un error en el servidor. Reintentar", Toast.LENGTH_LONG).show();
                        Log.d("2user update", "500");
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getContext(), "Hubo un error: "+t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("2user t update", t.getMessage() );

                }
            });

        }
    }

    private void GetValues() {
        User u= new User();

        String nick, pass, pass2, email;
        nick=etnickperfil.getText().toString();
        Toast.makeText(getContext(), "nick "+ nick, Toast.LENGTH_SHORT).show();
        pass=etpass1.getText().toString();
        pass2=etpass2.getText().toString();
        email=etemailperfil.getText().toString();
        if (nick.equals("")){
           etnickperfil.requestFocus();
            Toast.makeText(getContext(), "Campo vacío", Toast.LENGTH_SHORT).show();

            return;

        }
        if (pass.trim().equals("")){
            etpass1.requestFocus();

            return;
        }
        if (email.trim().equals("")){
            etemailperfil.requestFocus();

            return;
        }
        if (pass2.equals("")){
            etpass2.requestFocus();
            return;
        }
        u.setEmail(email);
        u.setPassword(pass);
        u.setNick(nick);

        PasswordVerification(u);

    }

    private void PasswordVerification(User u) {
        String pass1, pass2;
        pass1=etpass1.getText().toString();
        pass2=etpass2.getText().toString();

        if (pass1.equals(pass2)){
            Update(u);
        }else{
            Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();

        }

    }
    public void Alert(){
     AlertDialog.Builder dialog= new AlertDialog.Builder(getContext());
        dialog.setMessage("¿Eliminar?").setCancelable(true)
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteEvents();
                        DeleteTeachers();
                        DeleteSubjets();
                        Delete();


                    }
                }).setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog title= dialog.create();
        title.setTitle("Todos los registros se perderán");
        title.show();

    }
    private void Main(){
        Toast.makeText(getContext(), "Hecho!", Toast.LENGTH_LONG).show();
        Intent i= new Intent(new Intent(getContext(), Main2Activity.class));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        SharedPreferenceManager.getInstance(getContext()).LogOut();
    }
    private void DeleteSubjets(){
        Call<Void> call= ServiceBA.getInstance().createService(ISSubjet.class).DeleteAll(idUser);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code()==200){
                    Log.d("subjet:", "200");
                }else if(response.code()==404){
                    Toast.makeText(getContext(), "Recursos no encontrados", Toast.LENGTH_LONG).show();
                    Log.d("mat", "404");
                }else if(response.code()==500){
                    Toast.makeText(getContext(), "Hubo un error en el servidor. Reintentar", Toast.LENGTH_LONG).show();
                    Log.d("mat", "500");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("mat", t.getMessage());

            }
        });
    }
    private void DeleteEvents(){
        Call<Void> call= ServiceBA.getInstance().createService(ISEvent.class).DeleteAll(idUser);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code()==200){

                    Log.d("rvrnt:", "200");
                }else if(response.code()==404){
                    Toast.makeText(getContext(), "Recursos no encontrados", Toast.LENGTH_LONG).show();
                    Log.d("event", "404");
                }else if(response.code()==500){
                    Toast.makeText(getContext(), "Hubo un error en el servidor. Reintentar", Toast.LENGTH_LONG).show();
                    Log.d("event", "500");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("events:", t.getMessage());

            }
        });
    }
    private void DeleteTeachers(){
        Call<Void> call= ServiceBA.getInstance().createService(ISTeacher.class).DeleteAll(idUser);
        Log.d("teacher:", " "+idUser);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code()==200){

                    Log.d("teacher:", "200");
                   }else if(response.code()==404){
                Toast.makeText(getContext(), "Recursos no encontrados", Toast.LENGTH_LONG).show();
                Log.d("teacher", "404");
            }else if(response.code()==500){
                Toast.makeText(getContext(), "Hubo un error en el servidor. Reintentar", Toast.LENGTH_LONG).show();
                Log.d("teacher", "500");
            }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("teachers:", t.getMessage());
            }
        });
    }










    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
