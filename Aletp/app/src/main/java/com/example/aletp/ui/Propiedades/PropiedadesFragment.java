package com.example.aletp.ui.Propiedades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.aletp.PropiedadesVistaFragment;
import com.example.aletp.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PropiedadesFragment extends Fragment {
private ViewPager viewPager;
private TabLayout tabLayout;
private AppBarLayout appBarLayout;

    private PropiedadesViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(PropiedadesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_propiedades, container, false);
viewPager=root.findViewById(R.id.viewPage);
ViewGroup padre= (ViewGroup) container.getParent();
appBarLayout=((ViewGroup) padre.getParent()).findViewById(R.id.appBarT);

tabLayout=new TabLayout(getActivity());
appBarLayout.addView(tabLayout);
ViewPageAdapter viewPageAdapter=new ViewPageAdapter(getFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

viewPageAdapter.addFragment(new PropiedadesVistaFragment(), "Propiedad1" );
viewPageAdapter.addFragment(new PropiedadesVistaFragment(), "Propiedad2" );

viewPager.setAdapter(viewPageAdapter);

tabLayout.setupWithViewPager(viewPager);


        return root;
    }


    public class ViewPageAdapter extends FragmentPagerAdapter {
private List<Fragment>fragmentList= new ArrayList<>();
private List<String> titulos=new ArrayList<>();

        public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
          return  titulos.get(position);
        }

        public void addFragment(Fragment fragment, String titulo){
            fragmentList.add(fragment);
            titulos.add(titulo);

        }

    }
}