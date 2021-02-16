package com.ale.gttt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ale.gttt.Session.SharedPreferenceManager;
import com.ale.gttt.fragments.EventFragment;
import com.ale.gttt.fragments.SubjetFragment;
import com.ale.gttt.fragments.TeacherFragment;
import com.ale.gttt.fragments.UserFragment;
import com.ale.gttt.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MenuTabActivity extends AppCompatActivity
        implements EventFragment.OnFragmentInteractionListener,
        SubjetFragment.OnFragmentInteractionListener,
        TeacherFragment.OnFragmentInteractionListener,
        UserFragment.OnFragmentInteractionListener
{
private String idTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tab);

        idTab = getIntent().getStringExtra("idTab");


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        Init();
    }

    private void Init() {
        Button btnlout = findViewById(R.id.btnlout);
        btnlout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });
    }

    public void LogOut() {
        Intent i= new Intent(new Intent(getApplicationContext(), Main2Activity.class));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        SharedPreferenceManager.getInstance(getApplicationContext()).LogOut();
        startActivity(i);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {}

    @Override
    public void onStart(){
        super.onStart();
        if (!SharedPreferenceManager.getInstance(this).IsLoged()){
            Intent i=new Intent(getApplicationContext(), Main2Activity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }


}