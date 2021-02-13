package com.example.inmobiliariatp.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.example.inmobiliariatp.Detalle;
import com.example.inmobiliariatp.PagosFragment;
import com.example.inmobiliariatp.R;
import com.example.inmobiliariatp.contratosFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class PropiedadesFragment extends Fragment implements Detalle.OnFragmentInteractionListener, PagosFragment.OnFragmentInteractionListener, contratosFragment.OnFragmentInteractionListener{
    private Button button;

    private Spinner spinner;

    private String[] strings;


    private PropiedadesViewModel propiedadesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        propiedadesViewModel =
                ViewModelProviders.of(this).get(PropiedadesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
spinner= (Spinner) root.findViewById(R.id.spinner);


        button= (Button) root.findViewById(R.id.btnVer);


button.setOnClickListener(new View.OnClickListener() {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        String valor= spinner.getSelectedItem().toString();

        bundle.putString("dato", valor);
        Fragment fragmento = new Detalle();
        fragmento.setArguments(bundle);
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragmento);
        fragmentTransaction.addToBackStack(null);


        fragmentTransaction.commit();
     //   Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.nav_host_fragment)
      //          .navigate(R.id.detalle);
    }
});


        return root;
    }
    public void onCreate(Bundle savedInstanceStated){
        super.onCreate(savedInstanceStated);

    }
@RequiresApi(api = Build.VERSION_CODES.KITKAT)


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}