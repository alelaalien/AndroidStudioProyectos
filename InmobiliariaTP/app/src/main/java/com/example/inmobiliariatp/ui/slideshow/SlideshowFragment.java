package com.example.inmobiliariatp.ui.slideshow;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ListAdapter;

import com.example.inmobiliariatp.Contrato;
import com.example.inmobiliariatp.ListaAdapter;
import com.example.inmobiliariatp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.R.layout.simple_list_item_1;

public class SlideshowFragment extends Fragment {
private Spinner spinner;

    private SlideshowViewModel slideshowViewModel;
    private ArrayList<Contrato> lista;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
spinner=(Spinner) root.findViewById(R.id.spinnerContratos);
        ArrayAdapter<Contrato> adapter=new ListaAdapter(getContext(), R.layout.item_contratos, lista, getLayoutInflater() );
       ListView listView=(ListView) root.findViewById(R.id.listac);

       listView.setAdapter(adapter);



spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


    }
});

        return root;
    }

    public void Agregar(){
        lista.add(new Contrato("02/12/2019" , "03-04-2021", "$25000"));
        lista.add(new Contrato("02/12/2017" , "01-12-2019", "$20000"));

    }

}