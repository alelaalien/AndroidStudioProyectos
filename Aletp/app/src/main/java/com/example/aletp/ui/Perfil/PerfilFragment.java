package com.example.aletp.ui.Perfil;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.aletp.Contratos;
import com.example.aletp.ListaAdapterContratos;
import com.example.aletp.Perfil;
import com.example.aletp.R;

import java.util.ArrayList;
import java.util.Objects;

public class PerfilFragment extends Fragment {
    ArrayList<Perfil> perfilArrayList= new ArrayList<>();
    private PerfilViewModel shareViewModel;
    private String[] perfil;
    private String[] perfil1;
private Button btn;
private EditText nombre, apellido, telefono, pass, email, dni;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(PerfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);

Configuraciones(root);

LlenarVacio(root);

btn=root.findViewById(R.id.btnp);
btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View root) {
        if (btn.getText().equals("Editar")){


        nombre.setEnabled(true);
        apellido.setEnabled(true);
        pass.setEnabled(true);
        email.setEnabled(true);
        telefono.setEnabled(true);
        dni.setEnabled(true);
        btn.setText("Guardar");
    }


    }
});
        return root;
    }

    public void Configuraciones(View view){
          nombre=view.findViewById(R.id.tvnombrep);
         apellido=view.findViewById(R.id.tvapellidop);
          dni=view.findViewById(R.id.tvdnip);
         email=view.findViewById(R.id.tvcorreop);
         telefono=view.findViewById(R.id.tvtelefonop);
         pass=view.findViewById(R.id.tvpassp);


    }

    public void  CargarDatos(String nombre, String apellido, String email, String pass, String telefono, String dni){

           perfil= new String[]{nombre, apellido,   dni, telefono,email, pass};
        }
      public void Llenar(View view){



          perfil= new String[]{nombre.getText().toString(), apellido.getText().toString(),   dni.getText().toString(), telefono.getText().toString(),email.getText().toString(), pass.getText().toString()};
          pass.setText(perfil[5]);
          nombre.setText(perfil[0]);
          apellido.setText(perfil[1]);
          telefono.setText(perfil[3]);
          email.setText(perfil[4]);
          dni.setText(perfil[2]);
      }



    public void LlenarVacio(View view){
        perfil1= new String[]{"Lorena", "landa", "454356", "56564556","lore@mail.com", "pass123"};

        pass.setText(perfil1[5]);
        nombre.setText(perfil1[0]);
        apellido.setText(perfil1[1]);
        telefono.setText(perfil1[3]);
        email.setText(perfil1[4]);
        dni.setText(perfil1[2]);

    }
}