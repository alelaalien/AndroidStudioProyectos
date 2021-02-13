package com.example.aletp.ui.Contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.aletp.Contratos;
import com.example.aletp.Inquilinos;
import com.example.aletp.ListaAdapterContratos;
import com.example.aletp.ListaAdapterInquilinos;
import com.example.aletp.R;

import java.util.ArrayList;
import java.util.Objects;

public class ContratosFragment extends Fragment {
private Button btn;
    private ContratosViewModel slideshowViewModel;
    ArrayList<Contratos> contratosArrayList= new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(ContratosViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_contratos, container, false);
        Spinner spinner=root.findViewById(R.id.spinner);
btn=root.findViewById(R.id.button3);
       int i= spinner.getSelectedItemPosition();


btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        ejemplo(root);
        Limpiar(root);

        CargarDatos(ejemplo(root));GenerarListView(root);
    }
});



        return root;
    }
    public int ejemplo(View view){
        Spinner spinner = view.findViewById(R.id.spinner);
        int i= spinner.getSelectedItemPosition();
return i;
    }
    public void GenerarListView(View view){
        ArrayAdapter<Contratos> adapter=new ListaAdapterContratos(Objects.requireNonNull(getActivity()), R.layout.contratositem, contratosArrayList,getLayoutInflater());
        ListView listView=view.findViewById(R.id.listaContratos);
        listView.setAdapter(adapter);
    }

public void Limpiar(View view){
 contratosArrayList.clear();



}

    public void  CargarDatos(int n){
        if (n==0){
            contratosArrayList.add(new Contratos("20/03/2019", "02/07/2021", 14000));
        }
        if (n==1){
            contratosArrayList.add(new Contratos("31/03/2018", "25/07/2020", 30000));
        }
        if (n==2){
            contratosArrayList.add(new Contratos("02/05/2019", "03/10/2020", 8000));
        }
    }
}